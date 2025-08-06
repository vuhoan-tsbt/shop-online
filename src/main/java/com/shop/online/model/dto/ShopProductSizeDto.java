package com.shop.online.model.dto;

import lombok.Data;

@Data
public class ShopProductSizeDto {

    private Integer id;

    private String size;

    private Long quantity;

    private Long inventory;


    public ShopProductSizeDto(Integer id, String size, Long quantity, Long inventory) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;
        this.inventory = inventory;
    }
    public ShopProductSizeDto(Integer id, String size, Long quantity) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;

    }
}
