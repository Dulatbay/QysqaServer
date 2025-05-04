package com.example.qysqaserver.entities;

import com.example.qysqaserver.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "modules")
@Accessors(chain = true)
@Setter
@Getter
public class LearnModule extends BaseEntity  {
    @Serial
    @Transient
    private static final long serialVersionUID = -2747988670759026700L;


    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String title;
    private String description;
    private int level;
    private int hours;

    private int duration = 0;
    private String imageUrl;

    @DBRef
    private User user;

    @DBRef
    private List<User> linkedUsers = new ArrayList<>();

    @DBRef
    private List<Topic> topics = new ArrayList<>();
}
