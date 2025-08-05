package com.shop.online.service;

import java.io.IOException;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
   private final S3Client s3Client;
   @Value("${aws.s3.bucket}")
   private String bucketName;
   @Value("${aws.region}")
   private String region;
   @Value("${app.s3.aws.cloudfront-image}")
   private String cloudfront;

   public String uploadFile(MultipartFile file, String folderName) throws IOException {
      String key = folderName + "/" + file.getOriginalFilename();
      PutObjectRequest putObjectRequest = (PutObjectRequest)PutObjectRequest.builder().bucket(this.bucketName).key(key).contentType(file.getContentType()).build();
      this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
      return "https://" + this.cloudfront + "/" + key;
   }

   @Generated
   public S3Service(final S3Client s3Client) {
      this.s3Client = s3Client;
   }
}
