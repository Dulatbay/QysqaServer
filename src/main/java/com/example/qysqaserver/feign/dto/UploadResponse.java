package com.example.qysqaserver.feign.dto;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import lombok.Data;

@Data
public class UploadResponse {
    private String title;
    private String description;
    private String location;
    private BaseNode content;
}
