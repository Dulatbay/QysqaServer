package com.example.qysqaserver.services;

import com.example.qysqaserver.dto.request.ModuleCreateRequest;
import com.example.qysqaserver.dto.request.TopicCreateRequest;
import com.example.qysqaserver.dto.response.ModuleDetailResponse;
import com.example.qysqaserver.dto.response.ModuleResponse;
import com.example.qysqaserver.dto.response.TopicDetailResponse;
import com.example.qysqaserver.entities.User;
import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ModuleService {
    List<ModuleResponse> getAllUsersCurrentModules();
    ModuleDetailResponse getModuleDetail(String moduleId);
    List<ModuleResponse> getLinkedModules();
    void moduleCreate(ModuleCreateRequest moduleCreateRequest);
    void addTopicToModule(String moduleId, int index, TopicCreateRequest t);
    TopicDetailResponse getTopicDetail(String topicId);
    void generate(String moduleId, int index, MultipartFile file);
    void updateTopic(String topicId, BaseNode content);

    void passTopic(String topicId, User currentUser);

    Object generateQuiz(String topicId);
}
