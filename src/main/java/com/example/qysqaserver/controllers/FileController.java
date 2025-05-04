package com.example.qysqaserver.controllers;

import com.amazonaws.services.s3.model.S3Object;
import com.example.qysqaserver.services.S3FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final S3FileStorageService s3FileStorageService;

    // Endpoint to upload files
    @PostMapping( value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(s3FileStorageService.uploadFile(file));
    }

    // Endpoint to download files by name
    @GetMapping("/download/{fileName}")
    public ResponseEntity<S3Object> downloadFile(@PathVariable String fileName) {
        S3Object file = s3FileStorageService.getFileByName(fileName);
        if (file != null) {
            return ResponseEntity.ok(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

