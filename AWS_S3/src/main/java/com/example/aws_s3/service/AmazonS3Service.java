package com.example.aws_s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonS3Service {

    @Value("${endpointUrl}")
    private String endpointUrl;

    @Value("${bucketName}")
    private String bucketName;
    private AmazonS3 amazonS3;

    @Autowired
    public AmazonS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileUrl = "";
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        fileUrl = this.endpointUrl + "/" + this.bucketName + "/" + fileName;
        amazonS3.putObject(new PutObjectRequest(this.bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        file.delete();
        return fileUrl;
    }

    public String deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(this.bucketName + "/", fileName));
        return "Successfully deleted";
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File tempFile = new File(file.getOriginalFilename());
        FileOutputStream newFile = new FileOutputStream(tempFile);
        newFile.write(file.getBytes());
        newFile.close();
        return tempFile;
    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
