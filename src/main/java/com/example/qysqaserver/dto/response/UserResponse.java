package com.example.qysqaserver.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String imageUrl;
    private String email;
    private int fireDays;
    private boolean wasPlayedYesterday;
    private boolean wasPlayedToday;
    private int answeredQuestionsCount;
    private double score;
    private double accuracy;
    private LocalDate joinedDate;
}
