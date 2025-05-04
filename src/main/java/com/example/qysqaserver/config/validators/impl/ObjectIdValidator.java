package com.example.qysqaserver.config.validators.impl;

import com.example.qysqaserver.config.validators.ValidObjectId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ObjectIdValidator implements ConstraintValidator<ValidObjectId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^[a-fA-F0-9]{24}$");
    }
}
