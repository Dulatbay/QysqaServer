package com.example.qysqaserver.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueConstants {
    public static final ZoneId ZONE_ID = ZoneId.of("UTC+00:00");
    public static final String ANONYMOUS_USERNAME = "anonymousUser";
    // KAFKA
    public static final String QUESTIONS_ANSWERS_TOPIC = "questions-answers-topic";
    public static final String QUESTIONS_VIEWED_TOPIC = "questions-viewed-topic";
    public static final String QUESTION_GROUP = "questions-group";

    // modules
    public static final String MOBILE_WIDTH = "300px";

    // REDIS
    public static final String MODULES_KEY = "modules";
    public static final String MODULES_ALL_VALUE = "'all'";
    public static final String MODULES_COUNT = "'count'";


    public static final String TOPICS_KEY = "topics";
    public static final String FILES_KEY = "files";

}
