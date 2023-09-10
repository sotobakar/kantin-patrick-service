package com.sotobakar.kantinpatrickservice.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    MinioClient minioClient(@Value("${minio.url}") String url, @Value("${minio.accesskey}") String accessKey, @Value("${minio.secretkey}") String secretKey) {
        // Create a minioClient with the MinIO server playground, its access key and secret key.
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
