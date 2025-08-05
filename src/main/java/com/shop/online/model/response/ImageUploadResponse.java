package com.shop.online.model.response;

import lombok.Data;

@Data
public class ImageUploadResponse {

    private Integer imageS3Id;

    private String imageUrl;
}
