package com.example.qysqaserver.entities;

import com.example.qysqaserver.entities.base.BaseEntity;
import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "topics")
@Accessors(chain = true)
@Getter
@Setter
public class Topic extends BaseEntity {
    @Serial
    @Transient
    private static final long serialVersionUID = -8651217297032314446L;

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String title;
    private String description;
    private String location;

    @Indexed
    private int number;

    @DBRef
    private LearnModule learnModule;

    @Field("content")
    private BaseNode content;

    @DBRef
    private User user;

    @DBRef
    private List<User> passedUsers = new ArrayList<>();
}