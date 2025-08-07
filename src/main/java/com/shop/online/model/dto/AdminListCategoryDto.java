package com.shop.online.model.dto;

import lombok.Data;

@Data
public class AdminListCategoryDto {

    private Integer id;
    private String name;
    private String description;
    private String headerUrl;


    public AdminListCategoryDto(Integer id, String name, String description, String headerUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.headerUrl = headerUrl;
    }
    public AdminListCategoryDto(){

    }
}
