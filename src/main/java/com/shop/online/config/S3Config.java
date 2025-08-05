package com.shop.online.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class S3Config {
   @Value("${aws.region}")
   private String region;
   @Value("${aws.credentials.access-key}")
   private String accessKey;
   @Value("${aws.credentials.secret-key}")
   private String secretKey;

   @Bean
   public S3Client s3Client() {
      AwsBasicCredentials credentials = AwsBasicCredentials.create(this.accessKey, this.secretKey);
      return (S3Client)((S3ClientBuilder)((S3ClientBuilder)S3Client.builder().region(Region.of(this.region))).credentialsProvider(StaticCredentialsProvider.create(credentials))).build();
   }
}
