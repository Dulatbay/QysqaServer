package com.example.qysqaserver.controllers;


import com.example.qysqaserver.constants.Utils;
import com.example.qysqaserver.dto.request.ModuleCreateRequest;
import com.example.qysqaserver.dto.request.TopicCreateRequest;
import com.example.qysqaserver.dto.response.ModuleDetailResponse;
import com.example.qysqaserver.dto.response.ModuleResponse;
import com.example.qysqaserver.dto.response.TopicDetailResponse;
import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.initializers.BaseModuleInitializer;
import com.example.qysqaserver.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/modules")
@Slf4j
public class ModuleController {
    public final ModuleService moduleService;
    private final BaseModuleInitializer baseModuleInitializer;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-linked")
    public ResponseEntity<List<ModuleResponse>> getMyLinkedModule() {
        return ResponseEntity.ok(moduleService.getAllUsersCurrentModules());
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/linked")
    public ResponseEntity<List<ModuleResponse>> getLinkedModule() {
        return ResponseEntity.ok(moduleService.getLinkedModules());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/")
    public ResponseEntity<Void> createLinkedModule(@RequestBody ModuleCreateRequest moduleCreateRequest) {
        moduleService.moduleCreate(moduleCreateRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/topics/{topicId}")
    public ResponseEntity<TopicDetailResponse> getTopicContent(@PathVariable String topicId) {
        return ResponseEntity.ok(moduleService.getTopicDetail(topicId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{moduleId}/topics")
    public ResponseEntity<Void> addTopicToModule(@PathVariable("moduleId") String moduleId, @RequestParam(required = false) int index, @RequestBody TopicCreateRequest topicCreateRequest) {
        moduleService.addTopicToModule(moduleId, index, topicCreateRequest);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/test-create/{moduleId}")
    public ResponseEntity<BaseNode> test(@PathVariable String moduleId) {
        return ResponseEntity.ok(baseModuleInitializer.defaultTopic());
    }

    @PostMapping("/topics/{topicId}/pass")
    public ResponseEntity<Void> passTopic(@PathVariable String topicId) {
        moduleService.passTopic(topicId, Utils.getCurrentUser());
        return ResponseEntity.status(201).build();
    }

    @PostMapping(value = "/{moduleId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> uploadLargeFile(@PathVariable("moduleId") String moduleId,
                                                  @RequestParam(required = false) int index,
                                                  @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        moduleService.generate(moduleId, index, file);
        return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
    }

    @GetMapping("/details/{moduleId}")
    public ResponseEntity<ModuleDetailResponse> getModuleDetails(@PathVariable("moduleId") String moduleId) {
        return ResponseEntity.ok(moduleService.getModuleDetail(moduleId));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/topics/{topicId}")
    public ResponseEntity<Void> updateTopic(@PathVariable("topicId") String topicId, @RequestBody BaseNode content) {
        moduleService.updateTopic(topicId, content);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/topics/{topicId}/generate-quiz")
    public ResponseEntity<Object> generateQuiz(@PathVariable("topicId") String topicId) {
        return ResponseEntity.ok(moduleService.generateQuiz(topicId));
    }

}
