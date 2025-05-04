package com.example.qysqaserver.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileStorageService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    // Upload file to AWS S3
    public String uploadFile(MultipartFile file)  {
        // Generate a unique file name using UUID and the original file's extension
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new RuntimeException("File name is null");
        }

        // Extract the file extension
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        // Generate a unique file name with the extension
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(bucketName, "presentation_bucket/" + uniqueFileName, inputStream, null);
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieve file from AWS S3 by file name
    public S3Object getFileByName(String fileName) {
        return amazonS3.getObject(bucketName, fileName);
    }

    public BaseNode downloadFileAndConvertToBaseNode(String fileKey)  {
        // Fetch the file from S3 using its key
        S3Object s3Object = amazonS3.getObject(bucketName, fileKey);
        try (InputStream inputStream = s3Object.getObjectContent()) {
            // Assuming the file content is in JSON format and we need to convert it to BaseNode
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, BaseNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

