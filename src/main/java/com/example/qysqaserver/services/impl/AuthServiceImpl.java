package com.example.qysqaserver.services.impl;

import com.example.qysqaserver.dto.request.AuthRequest;
import com.example.qysqaserver.dto.request.PasswordChangeRequest;
import com.example.qysqaserver.dto.request.RegisterUserRequestDto;
import com.example.qysqaserver.dto.response.AuthResponse;
import com.example.qysqaserver.dto.response.DeviceResponse;
import com.example.qysqaserver.entities.Token;
import com.example.qysqaserver.entities.User;
import com.example.qysqaserver.entities.enums.TokenType;
import com.example.qysqaserver.exceptions.DbNotFoundException;
import com.example.qysqaserver.repositories.TokenRepository;
import com.example.qysqaserver.repositories.TopicRepository;
import com.example.qysqaserver.repositories.UserRepository;
import com.example.qysqaserver.security.JwtService;
import com.example.qysqaserver.security.Role;
import com.example.qysqaserver.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.qysqaserver.constants.Utils.isMobileUserAgent;
import static com.example.qysqaserver.constants.Utils.prettifyUserAgent;
import static com.example.qysqaserver.constants.ValueConstants.ANONYMOUS_USERNAME;
import static com.example.qysqaserver.constants.ValueConstants.ZONE_ID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    @Value("${application.env}")
    private String environment;


    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private static User anonymousUser;

    @Override
    public void registerUser(RegisterUserRequestDto registerUserRequestDto) {
        userRepository.findByEmail(registerUserRequestDto.getEmail().trim())
                .ifPresent(usr -> {
                    throw new IllegalArgumentException("auth.email-already-exists-error");
                });

        if (!registerUserRequestDto.getPassword().trim().equals(registerUserRequestDto.getConfirmPassword().trim())) {
            throw new IllegalArgumentException("auth.confirm-password-error");
        }

        User user = new User();
        user.setEmail(registerUserRequestDto.getEmail());
        user.setUsername(registerUserRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setRole(Role.USER);

        log.info("Registered user: {}", user);
        userRepository.save(user);
    }

    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> userOptional = userRepository.findByEmail(authRequest.getEmailOrUsername())
                .or(() -> userRepository.findByUsername(authRequest.getEmailOrUsername()));

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("auth.invalid-email-or-username-error");
        }

        var user = userOptional.get();

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("auth.invalid-password-error");
        }


        // todo: for test week
        revokeAllUserTokens(user);

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String xRealIp = request.getHeader("X-Real-IP");
        String userAgent = request.getHeader("User-Agent");

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        setCookie(response, refreshToken);
        saveUserToken(user, refreshToken, xForwardedFor, xRealIp, userAgent);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole().name())
                .build();
    }


    private void saveUserToken(User user, String jwtToken, String remoteAddress, String remoteHost, String userAgent) {
        LocalDateTime expiredAt = LocalDateTime.now(ZONE_ID).plusSeconds(jwtService.getRefreshExpiration() / 1000);

        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .expiredAt(expiredAt)
                .remoteAddress(remoteAddress)
                .remoteHost(remoteHost)
                .userAgent(userAgent)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void revokeCurrentToken(String refreshToken) {
        var token = tokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("auth.invalid-token-error"));

        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String refreshTokenFromRequest = getRefreshTokenFromCookies(request);
        if (refreshTokenFromRequest == null)
            refreshTokenFromRequest = getRefreshTokenFromHeader(request);


        final String username;

        username = jwtService.extractUsername(refreshTokenFromRequest);

        log.info("Refreshing token for user: {}", username);

        if (username != null) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST, "auth.invalid-token-error", "auth.invalid-token-description"));

            if (jwtService.isTokenValid(refreshTokenFromRequest, user) && !jwtService.isTokenExpired(refreshTokenFromRequest)) {

                String newAccessToken = jwtService.generateToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);

                log.info("Refresh token from cookies: {}", getRefreshTokenFromCookies(request));

                String remoteAddress = request.getRemoteAddr();
                String remoteHost = request.getRemoteHost();
                String userAgent = request.getHeader("User-Agent");

                revokeCurrentToken(refreshTokenFromRequest);
                saveUserToken(user, newRefreshToken, remoteAddress, remoteHost, userAgent);

                setCookie(response, newRefreshToken);

                var authResponse = AuthResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .role(user.getRole().name())
                        .build();

                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                throw new IllegalArgumentException("auth.invalid-token-error");
            }
        } else {
            throw new IllegalArgumentException("auth.invalid-token-error");
        }
    }

    @SneakyThrows
    private String getRefreshTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        throw new AccessDeniedException("forbidden");
    }

    private void setCookie(HttpServletResponse response, String newRefreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
        refreshTokenCookie.setHttpOnly(true);

        if ("prod".equals(environment)) {
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setAttribute("SameSite", "None");
        } else {
            refreshTokenCookie.setSecure(false);
            refreshTokenCookie.setAttribute("SameSite", "Lax");
        }

        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
    }


    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest, User currentUser) {
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), currentUser.getPassword())) {
            throw new IllegalArgumentException("auth.invalid-old-password-error");
        }

        if (passwordChangeRequest.getOldPassword().equals(passwordChangeRequest.getNewPassword())) {
            throw new IllegalArgumentException("auth.same-password-error");
        }

        currentUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(currentUser);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookies(request);
        if (refreshToken == null) {
            refreshToken = getRefreshTokenFromHeader(request);
        }

        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new IllegalArgumentException("auth.invalid-token-error");
        }

        revokeCurrentToken(refreshToken);

        setCookie(response, null);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public DeviceResponse getActiveDevices(User currentUser, HttpServletRequest request) {
        final String currentRefreshToken = getRefreshTokenFromCookies(request);
        log.info("Current refresh token: {}", currentRefreshToken);
        var tokens = tokenRepository.findAllValidTokenByUser(currentUser.getId());
        var mobile = new ArrayList<DeviceResponse.Device>();
        var web = new ArrayList<DeviceResponse.Device>();

        tokens
                .forEach(t -> {
                            if (t.getExpiredAt().isBefore(LocalDateTime.now(ZONE_ID))) {
                                t.setExpired(true);
                                t.setRevoked(true);
                                tokenRepository.save(t);
                            }

                            var device = DeviceResponse.Device.builder()
                                    .tokenId(t.getId())
                                    .remoteAddress(t.getRemoteAddress())
                                    .remoteHost(t.getRemoteHost())
                                    .userAgent(prettifyUserAgent(t.getUserAgent()))
                                    .expiredAt(t.getExpiredAt())
                                    .createdDate(t.getCreatedDate())
                                    .isCurrentSession(t.getToken().equals(currentRefreshToken))
                                    .build();

                            if (isMobileUserAgent(t.getUserAgent())) {
                                mobile.add(device);
                            } else {
                                web.add(device);
                            }
                        }
                );


        return DeviceResponse.builder()
                .webSessions(web)
                .mobileSessions(mobile)
                .build();
    }

    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String currentRefreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("Cookie: {}, {}", cookie.getName(), cookie.getValue());
                if ("refreshToken".equals(cookie.getName())) {
                    currentRefreshToken = cookie.getValue();
                    break;
                }
            }
        }

        return currentRefreshToken;
    }

    @Override
    public void terminateSession(String tokenId, User currentUser) {
        var token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new IllegalArgumentException("auth.invalid-token-error"));

        if (!token.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("auth.invalid-token-error");
        }

        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
    }

    @Override
    public User getAnonymousUser() {
        if (anonymousUser == null) {
            anonymousUser = userRepository.findByUsername(ANONYMOUS_USERNAME)
                    .orElseGet(() -> {
                        User user = new User();
                        user.setUsername(ANONYMOUS_USERNAME);
                        user.setEmail(ANONYMOUS_USERNAME);
                        user.setRole(Role.USER);
                        user.setPassword(passwordEncoder.encode(ANONYMOUS_USERNAME));
                        return userRepository.save(user);
                    });
        }

        return anonymousUser;
    }
}




