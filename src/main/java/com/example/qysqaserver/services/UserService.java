package com.example.qysqaserver.services;

import com.example.qysqaserver.dto.request.FullNameChangeRequest;
import com.example.qysqaserver.dto.response.UserResponse;
import com.example.qysqaserver.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponse getUserById(String id, boolean showEmail);

    UserResponse setFullName(FullNameChangeRequest fullNameChangeRequest, User user);

    String setImageUrl(MultipartFile image, User user);
}
