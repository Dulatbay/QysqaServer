package com.example.qysqaserver.config.validators.impl;

import com.example.qysqaserver.config.validators.ValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileListValidator implements ConstraintValidator<ValidFile, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return false;
        }

        boolean result = true;

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty() || !isSupportedContentType(file.getContentType())) {
                result = false;
                break;
            }
        }

        return result;
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType != null && (contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg"));
    }
}