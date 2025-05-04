package com.example.qysqaserver.entities;

import com.example.qysqaserver.entities.base.BaseEntity;
import com.example.qysqaserver.entities.enums.TokenType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serial;
import java.time.LocalDateTime;

@Document(collection = "tokens")
@Accessors(chain = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token extends BaseEntity {
    @Serial
    @Transient
    private static final long serialVersionUID = -2747988670759026770L;


    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private String token;

    @Field(targetType = FieldType.STRING)
    private TokenType tokenType;

    private boolean revoked;
    private boolean expired;
    private LocalDateTime expiredAt;

    @DBRef
    private User user;

    private String remoteAddress;
    private String remoteHost;
    private String userAgent;
}
