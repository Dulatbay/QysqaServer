package com.example.qysqaserver.services.impl;

import com.example.qysqaserver.dto.request.FullNameChangeRequest;
import com.example.qysqaserver.dto.response.UserResponse;
import com.example.qysqaserver.entities.User;
import com.example.qysqaserver.exceptions.DbNotFoundException;
import com.example.qysqaserver.mappers.UserMapper;
import com.example.qysqaserver.repositories.UserRepository;
import com.example.qysqaserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public UserResponse getUserById(String id, boolean showEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "user.not-found-error", ""));

        var userResponse = userMapper.toResponse(user);

        if (!showEmail)
            user.setEmail(null);

        return userResponse;
    }

    @Override
    public UserResponse setFullName(FullNameChangeRequest fullNameChangeRequest, User user) {
        user.setFirstName(fullNameChangeRequest.firstName());
        user.setLastName(fullNameChangeRequest.lastName());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public String setImageUrl(MultipartFile image, User user) {
        return "filename";
    }
}
