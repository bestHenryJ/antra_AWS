package com.example.aws_s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3config {
    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Bean
    public AmazonS3 getS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        return new AmazonS3Client(credentials);
    }
}
