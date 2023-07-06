package com.example.aws_s3.controller;

import com.example.aws_s3.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/amazonS3/")
public class AmazonS3Controller {
    private AmazonS3Service amazonS3Service;

    @Autowired
    public AmazonS3Controller(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(amazonS3Service.uploadFile(file), HttpStatus.OK);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<?> deleteFile(@RequestPart(value = "url") String fileUrl) {
        return new ResponseEntity<>(amazonS3Service.deleteFile(fileUrl), HttpStatus.OK);
    }
}
