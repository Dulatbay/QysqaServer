package com.example.qysqaserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse implements Serializable {
    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private int questionNumbers;
    private int duration;
    private int passedUsersCount;
    private int number;
    private int difficulty;
    private List<TopicResponse> topics;

    @AllArgsConstructor
    @Data
    public static class TopicResponse implements Serializable {
        private String topicId;
        private String topicName;
    }
}
