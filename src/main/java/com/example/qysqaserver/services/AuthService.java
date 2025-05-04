package com.example.qysqaserver.services;


import com.example.qysqaserver.dto.request.AuthRequest;
import com.example.qysqaserver.dto.request.PasswordChangeRequest;
import com.example.qysqaserver.dto.request.RegisterUserRequestDto;
import com.example.qysqaserver.dto.response.AuthResponse;
import com.example.qysqaserver.dto.response.DeviceResponse;
import com.example.qysqaserver.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;

public interface AuthService {
    void registerUser(RegisterUserRequestDto user);

    AuthResponse authenticateUser(AuthRequest auth, HttpServletRequest request, HttpServletResponse response);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User getAnonymousUser();

    void changePassword(@Valid PasswordChangeRequest passwordChangeRequest, User currentUser);

    void logout(HttpServletRequest request, HttpServletResponse response);

    DeviceResponse getActiveDevices(User currentUser, HttpServletRequest request);

    void terminateSession(String tokenId, User currentUser);
}
