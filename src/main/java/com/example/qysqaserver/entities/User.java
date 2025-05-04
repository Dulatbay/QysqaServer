package com.example.qysqaserver.entities;


import com.example.qysqaserver.entities.base.BaseEntity;
import com.example.qysqaserver.security.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;

@Document(collection = "users")
@Accessors(chain = true)
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    @Serial
    private static final long serialVersionUID = -9120132419668516214L;

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    @Indexed
    @Field(targetType = FieldType.STRING)
    private Role role;

    private String password;

    private String firstName;
    private String lastName;
    private String imageUrl;
    private int maxFireDays = 0;
    private int fireDays = 0;
    private int answeredQuestionsCount = 0;
    private int rightAnswers = 0;
    private double score; // если fullPassed = true, то score = 100
    private boolean previousQuestionCorrect = false;
    //    private int currentQuestionStreak = 0;
//    private int maxQuestionStreak = 0;
    private LocalDateTime lastPlayedAt;
    private LocalDateTime fireDaysUpdatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
