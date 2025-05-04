package com.example.qysqaserver.config.validators;

import com.example.qysqaserver.config.validators.impl.ObjectIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ObjectIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidObjectId {
    String message() default "invalid-object-id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
