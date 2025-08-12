package com.shop.online.model.dto;

import lombok.Data;

@Data
public class AdminCategoryShoppingResponseDto {

    private Integer id;
    private String nameCategory;
    private String description;
    private String logoUrl;

    public AdminCategoryShoppingResponseDto(Integer id, String nameCategory, String description, String logoUrl) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.description = description;
        this.logoUrl = logoUrl;
    }
}
