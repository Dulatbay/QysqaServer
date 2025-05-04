package com.example.qysqaserver.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceResponse {
    private List<Device> webSessions;
    private List<Device> mobileSessions;

    @Builder
    @Data
    public static class Device {
        private String tokenId;
        private String remoteAddress;
        private String remoteHost;
        private String userAgent;
        private LocalDateTime expiredAt;
        private LocalDateTime createdDate;
        private boolean isCurrentSession;
    }
}