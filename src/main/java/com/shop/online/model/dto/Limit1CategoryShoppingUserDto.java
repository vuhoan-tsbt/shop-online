package com.shop.online.model.dto;

import lombok.Data;

@Data
public class Limit1CategoryShoppingUserDto {

    private Integer id;
    private String name;
    private String image;

    public Limit1CategoryShoppingUserDto(Integer categoryShoppingId, String nameCategoryShopping, String image) {
        this.id = categoryShoppingId;
        this.name = nameCategoryShopping;
        this.image = image;
    }
}
