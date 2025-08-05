package com.shop.online.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shop.online.entity.ImageS3;
import com.shop.online.model.response.ImageUploadResponse;
import com.shop.online.repository.ImageS3Repository;
import com.shop.online.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Value("${app.s3.aws.cloudfront-image}")
    private String cloudfront;

    private final ImageS3Repository imageS3Repository;

    public ImageUploadResponse uploadFile(MultipartFile file, String folderName) throws IOException {
        ImageUploadResponse response = new ImageUploadResponse();
        String key = folderName + "/" + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = (PutObjectRequest) PutObjectRequest
                .builder()
                .bucket(this.bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        ImageS3 imageS3 = new ImageS3();
        imageS3.setUrl(key);
        imageS3Repository.save(imageS3);
        response.setImageS3Id(imageS3.getId());
        response.setImageUrl(Constants.HTTPS_STRING + this.cloudfront + Constants.SLASH + key);
        return response;
    }

    public List<String> uploadFileList(List<MultipartFile> files, String folder) throws IOException {
        List<ImageS3> imageS3List = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String key = folder + "/" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            ImageS3 imageS3 = new ImageS3();
            imageS3.setUrl(key);
            imageS3List.add(imageS3);
            String imageUrl = Constants.HTTPS_STRING + this.cloudfront + Constants.SLASH + key;
            imageUrls.add(imageUrl);

        }
        imageS3Repository.saveAll(imageS3List);
        return imageUrls;
    }
}
