package com.example.qysqaserver.security;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public enum Permission {

    QUIZ_PLAY("quiz:play"),
    QUIZ_READ("quiz:read"),
    QUIZ_UPDATE("quiz:update"),
    QUIZ_DELETE("quiz:delete"),
    QUIZ_CREATE("quiz:create"),
    QUIZ_VERIFY("quiz:verify"),
    QUIZ_VIEW_DETAIL("quiz:view-detail"),

    QUESTION_ANSWER("question:answer"),

    MODULE_CREATE("module:create"),

    KZH_MAP_CREATE_RANGE("kzh-map:create-range"),
    KZH_MAP_ADD_MAP("kzh-map:add-map"),
    KZH_MAP_VIEW("kzh-map:view"),
    KZH_MAP_EDIT("kzh-map:edit"),

    TOPIC_CONTENT_UPDATE("topic-content:update"),

    HISTORY_CREATE("history-date:create"),
    HISTORY_DELETE("history-date:delete"),

    QUESTION_DELETE("question:delete"),

    FILE_UPLOAD("file:upload"),
    FILE_DELETE("file:delete"),
    FILE_DELETE_FOLDER("file:delete-folder");

    public final String permission;

    public static final Set<Permission> COMMON_USER_PERMISSIONS = Set.of(
            QUIZ_UPDATE,
            QUIZ_DELETE,
            KZH_MAP_VIEW,
            QUIZ_CREATE,
            QUIZ_PLAY,
            QUIZ_READ,
            QUIZ_VIEW_DETAIL,
            QUESTION_ANSWER
    );
}
