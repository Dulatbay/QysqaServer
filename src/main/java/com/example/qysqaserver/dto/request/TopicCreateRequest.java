package com.example.qysqaserver.dto.request;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import lombok.Data;

@Data
public class TopicCreateRequest {
    private String title;
    private String description;
    private String location;
    private BaseNode content;
}
