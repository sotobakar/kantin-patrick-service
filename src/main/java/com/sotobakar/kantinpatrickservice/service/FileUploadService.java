package com.sotobakar.kantinpatrickservice.service;

import com.sotobakar.kantinpatrickservice.exception.MinioUploadErrorException;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    HttpServletRequest request;

    @Value("${minio.bucket.name}")
    private String fileBucketName;

    private final MinioClient minioClient;

    public FileUploadService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public static String getMimeTypeFromFilename(String filename) {
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        if ("jpeg".equals(extension) || "jpg".equals(extension)) {
            return "image/jpeg";
        } else if ("png".equals(extension)) {
            return "image/png";
        } else {
            // Handle unrecognized extensions, or return a default value like "application/octet-stream"
            return "application/octet-stream";
        }
    }

    public String uploadFile(MultipartFile file, String path) {
        var requestUri = request.getRequestURI();

        // Generate random name + extension as filename
        String fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

        try {
            Path tempDir = Files.createTempDirectory("uploads");
            Path tempFilePath = tempDir.resolve(fileName);

            // Copy the contents of the MultipartFile to the temporary file
            Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Save to Minio (menu folder)
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(this.fileBucketName)
                            .object((path != null ? (path + "/") : "") + fileName)
                            .filename(tempFilePath.toAbsolutePath().toString())
                            .contentType(FileUploadService.getMimeTypeFromFilename(fileName))
                            .build());
            return fileName;
        } catch (Exception e) {
            log.error(requestUri + ": " + e.getMessage());
            throw new MinioUploadErrorException();
        }
    }

    public void deleteFile(String fileName, String path) {
        var requestUri = request.getRequestURI();

        try {
            // Delete photo from minio
            this.minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(this.fileBucketName)
                            .object((path != null ? (path + "/") : "") + fileName)
                            .build()
            );
        } catch (Exception e) {
            log.error(requestUri + ": " + e.getMessage());
            throw new MinioUploadErrorException();
        }
    }
}
