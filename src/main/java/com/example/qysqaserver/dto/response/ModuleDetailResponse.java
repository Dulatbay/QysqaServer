package com.example.qysqaserver.dto.response;

import lombok.Data;

@Data
public class ModuleDetailResponse {
    private String id;
    private String name;
    private int topicsCount;
    private int number;
    private boolean active;
    private boolean lastModule;
    private int firstActive;
    private Integer currentActiveTopicNumber;

    private int nextModuleNumber;
    private int prevModuleNumber;
}
