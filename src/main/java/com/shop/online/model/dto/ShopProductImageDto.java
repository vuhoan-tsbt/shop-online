package com.shop.online.model.dto;

import lombok.Data;

@Data
public class ShopProductImageDto {


    private Integer id;
    private String url;
    public ShopProductImageDto(Integer id, String url) {
        this.id = id;
        this.url = url;
    }
}
