package com.example.qysqaserver.dto.response;

import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TopicDetailResponse {
    private BaseNode content;
    private TopicLink current;
    private TopicLink next;
    private TopicLink prev;

    @Getter
    @Setter
    @Builder
    public static class TopicLink {
        private String topicId;
        private int topicNumber;
        private String moduleId;
        private String topicName;
        private String moduleName;
        private boolean passed;
        private boolean availableToPass;
    }
}
