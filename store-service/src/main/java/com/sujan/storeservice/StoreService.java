package com.sujan.storeservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class StoreService {
    private final S3Client s3Client;
    @Value("${bucket.name}")
    private String bucketName;
    public StoreService(S3Client s3Client){
        this.s3Client = s3Client;
    }
    public void upload(MultipartFile file) throws IOException {
        Path tempFilePath = Files.createTempFile(file.getName(), file.getName());
        Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getOriginalFilename())
                .build();
        s3Client.putObject(request, tempFilePath);
        Files.delete(tempFilePath);
    }

    public S3DownloadFile download(String filename) throws IOException {
        Path tempFilePath = Files.createTempFile(filename, filename);
        Files.deleteIfExists(tempFilePath);
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        GetObjectResponse response =s3Client.getObject(request, tempFilePath);
        try(FileInputStream stream = new FileInputStream(tempFilePath.toString())){
            return new S3DownloadFile(
                    "application/"+filename.substring(filename.length()-3),
                    response.contentLength(),
                    new ByteArrayResource(stream.readAllBytes()));
        }
    }
}
