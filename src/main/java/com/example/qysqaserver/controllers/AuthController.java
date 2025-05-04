package com.example.qysqaserver.controllers;


import com.example.qysqaserver.constants.Utils;
import com.example.qysqaserver.dto.request.AuthRequest;
import com.example.qysqaserver.dto.request.PasswordChangeRequest;
import com.example.qysqaserver.dto.request.RegisterUserRequestDto;
import com.example.qysqaserver.dto.response.AuthResponse;
import com.example.qysqaserver.dto.response.DeviceResponse;
import com.example.qysqaserver.dto.response.UserResponse;
import com.example.qysqaserver.entities.User;
import com.example.qysqaserver.services.AuthService;
import com.example.qysqaserver.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/devices")
    @PreAuthorize("isAuthenticated()")
    @CrossOrigin(originPatterns = "*", allowCredentials = "true")
    public ResponseEntity<DeviceResponse> getActiveDevices(HttpServletRequest request) {
        User currentUser = Utils.getCurrentUser();

        DeviceResponse devices = authService.getActiveDevices(currentUser, request);

        return ResponseEntity.ok(devices);
    }

    @DeleteMapping("/devices/{tokenId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> terminateSession(@PathVariable String tokenId) {
        User currentUser = Utils.getCurrentUser();

        authService.terminateSession(tokenId, currentUser);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        authService.registerUser(registerUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid
                                                  @RequestBody
                                                  AuthRequest authRequest,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
        AuthResponse authResponse = authService.authenticateUser(authRequest, request, response);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/me")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<UserResponse> me() {
        var currentUser = Utils.getCurrentUser();

        assert currentUser != null;
        UserResponse userResponse = userService.getUserById(currentUser.getId(), true);

        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/change-password")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        var currentUser = Utils.getCurrentUser();

        authService.changePassword(passwordChangeRequest, currentUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}