package com.example.qysqaserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class DbNotFoundException extends RuntimeException {
    private HttpStatus status;
    private String messageKey;
    private String descriptionKey;
}
