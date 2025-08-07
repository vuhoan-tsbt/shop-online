package com.shop.online.model.request;

import lombok.Data;

@Data
public class CategoryRequest {

    private Integer id;

    private String name;

    private String description;

    private String headerUrl;
}
