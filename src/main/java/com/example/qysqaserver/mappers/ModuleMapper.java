package com.example.qysqaserver.mappers;

import com.example.qysqaserver.constants.Utils;
import com.example.qysqaserver.dto.request.ModuleCreateRequest;
import com.example.qysqaserver.dto.request.TopicCreateRequest;
import com.example.qysqaserver.dto.response.ModuleDetailResponse;
import com.example.qysqaserver.dto.response.ModuleResponse;
import com.example.qysqaserver.dto.response.TopicDetailResponse;
import com.example.qysqaserver.entities.LearnModule;
import com.example.qysqaserver.entities.Topic;
import com.example.qysqaserver.entities.User;
import com.example.qysqaserver.exceptions.DbNotFoundException;
import com.example.qysqaserver.repositories.ModuleRepository;
import com.example.qysqaserver.repositories.TopicRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ModuleMapper {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Mapping(target = "name", expression = "java(module.getTitle())")
    @Mapping(target = "description", expression = "java(module.getDescription())")
    @Mapping(target = "questionNumbers", constant = "0")
    @Mapping(target = "passedUsersCount", constant = "0")
    @Mapping(target = "difficulty", source = "level")
    abstract public ModuleResponse toModuleResponse(LearnModule module);

    @Mapping(source = "id", target = "topicId")
    @Mapping(target = "topicName", expression = "java(topic.getTitle())")
    abstract public ModuleResponse.TopicResponse toTopicResponse(Topic topic);

    abstract public List<ModuleResponse> toModuleResponseList(List<LearnModule> modules);


    @Mapping(target = "id", source = "module.id")
    @Mapping(target = "name", expression = "java(module.getTitle())")
    @Mapping(target = "topicsCount", expression = "java(module.getTopics().size())")
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "firstActive", ignore = true)
    @Mapping(target = "nextModuleNumber", ignore = true)
    @Mapping(target = "prevModuleNumber", ignore = true)
    @Mapping(target = "lastModule", ignore = true)
    @Mapping(target = "currentActiveTopicNumber", ignore = true)
    abstract public ModuleDetailResponse toDetailResponse(LearnModule module, User currentUser);

    abstract public LearnModule toEntity(ModuleCreateRequest moduleCreateRequest);

    abstract public Topic toEntity(TopicCreateRequest moduleCreateRequest);

    public TopicDetailResponse toDetailResponse(Topic topic) {
        var module = topic.getLearnModule();
        int topicIndex = -1;
        List<Topic> topics = module.getTopics();

        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getId().equals(topic.getId())) {
                topicIndex = i;
                break;
            }
        }

        TopicDetailResponse topicDetailResponse = new TopicDetailResponse();
        topicDetailResponse.setContent(topic.getContent());

        topicDetailResponse.setNext(create(module, topicIndex + 1));
        topicDetailResponse.setPrev(create(module, topicIndex - 1));
        topicDetailResponse.setCurrent(create(module, topicIndex));
        topicDetailResponse.setContent(topic.getContent());

        return topicDetailResponse;
    }

    private TopicDetailResponse.TopicLink create(LearnModule module, int topicIndex) {
        var topics = module.getTopics();

        if(topicIndex < 0) return null;
        if(topicIndex >= topics.size()) return null;
        var topic = module.getTopics().get(topicIndex);

        if (topic == null) return null;



        var user = Utils.getCurrentUser();
        return TopicDetailResponse.TopicLink.builder()
                .moduleId(module.getId())
                .moduleName(module.getTitle())
                .topicId(topic.getId())
                .topicName(module.getTitle())
                .passed(user != null && topic.getPassedUsers().stream()
                        .anyMatch(u -> u.getId().equals(user.getId())))
                .availableToPass(true)
                .build();
    }
}

