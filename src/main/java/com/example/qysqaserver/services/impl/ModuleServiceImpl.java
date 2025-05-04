package com.example.qysqaserver.services.impl;

import com.example.qysqaserver.constants.Utils;
import com.example.qysqaserver.dto.request.ModuleCreateRequest;
import com.example.qysqaserver.dto.request.TopicCreateRequest;
import com.example.qysqaserver.dto.response.ModuleDetailResponse;
import com.example.qysqaserver.dto.response.ModuleResponse;
import com.example.qysqaserver.dto.response.TopicDetailResponse;
import com.example.qysqaserver.entities.Topic;
import com.example.qysqaserver.entities.User;
import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.exceptions.DbNotFoundException;
import com.example.qysqaserver.feign.ExternalAIApi;
import com.example.qysqaserver.initializers.BaseModuleInitializer;
import com.example.qysqaserver.mappers.ModuleMapper;
import com.example.qysqaserver.repositories.ModuleRepository;
import com.example.qysqaserver.repositories.TopicRepository;
import com.example.qysqaserver.services.ModuleService;
import com.example.qysqaserver.services.S3FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final TopicRepository topicRepository;
    private final ExternalAIApi externalAIApi;
    private final BaseModuleInitializer baseModuleInitializer;
    private final S3FileStorageService fileStorageService;



    @Override
    public List<ModuleResponse> getAllUsersCurrentModules() {
        var users = moduleRepository.findAllByUser(Utils.getCurrentUser());
        return moduleMapper.toModuleResponseList(users);
    }

    @Override
    public ModuleDetailResponse getModuleDetail(String moduleId) {
        var module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Module not found", ""));
        return moduleMapper.toDetailResponse(module, Utils.getCurrentUser());
    }

    @Override
    public List<ModuleResponse> getLinkedModules() {
        var users = moduleRepository.findAllByUser(Utils.getCurrentUser());
        return moduleMapper.toModuleResponseList(users);
    }

    @Override
    public void moduleCreate(ModuleCreateRequest moduleCreateRequest) {
        var module = moduleMapper.toEntity(moduleCreateRequest);
        module.setUser(Utils.getCurrentUser());
        moduleRepository.save(module);
    }

    @Override
    public void addTopicToModule(String moduleId, int index, TopicCreateRequest topicCreateRequest) {
        var module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Module not found", ""));
        var topic = moduleMapper.toEntity(topicCreateRequest);
        topic.setUser(Utils.getCurrentUser());
        topic.setLearnModule(module);
        topic = topicRepository.save(topic);

        if (module.getTopics().size() > index && index >= 0) {
            module.getTopics().add(index, topic);
        } else {
            module.getTopics().addLast(topic);
        }

        moduleRepository.save(module);
    }

    @Override
    public TopicDetailResponse getTopicDetail(String topicId) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Topic not found", ""));
        return moduleMapper.toDetailResponse(topic);
    }

    @Override
    public void generate(String moduleId, int index, MultipartFile file) {
        var module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Module not found", ""));

        var str = fileStorageService.uploadFile(file);

        HashMap<String, String> body = new HashMap<>();
        body.put("file_key", str);
        body.put("folder_path", "presentation_bucket");
        var response = externalAIApi.uploadFiles(body);

        if(!response.getStatusCode().is2xxSuccessful()) throw new IllegalArgumentException("Api");


        var topic = new Topic();
        topic.setContent(response.getBody());
        topic.setTitle("Title");
        topic.setDescription("");
        topic.setLocation("");
        topic.setLearnModule(module);
        topic.setUser(Utils.getCurrentUser());
        topic.setLearnModule(module);
        topic = topicRepository.save(topic);

        if (module.getTopics().size() > index) {
            module.getTopics().add(index, topic);
        } else {
            module.getTopics().addLast(topic);
        }
    }

    @Override
    public void updateTopic(String topicId, BaseNode content) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Topic not found", ""));
        topic.setContent(content);
        topicRepository.save(topic);
    }

    @Override
    public void passTopic(String topicId, User currentUser) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Topic not found", ""));
        topic.getPassedUsers().add(currentUser);
        topicRepository.save(topic);
    }

    @Override
    public Object generateQuiz(String topicId) {
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND, "Topic not found", ""));
        return null;
    }


}
