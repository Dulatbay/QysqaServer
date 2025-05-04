package com.example.qysqaserver.dto.request;

import lombok.Data;

@Data
public class ModuleCreateRequest {
    private String title;
    private String description;
    private int level;
    private int hours;

    private int duration = 0;
}
