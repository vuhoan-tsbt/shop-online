package com.shop.online.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductShoppingUserDto {

    private Integer id;
    private Float price;
    private String shopProductName;
    private String categoryName;
    private String image;
    private List<ShopProductSizeDto> shopProductSizes;

    public ProductShoppingUserDto(Integer id, Float price, String shopProductName, String categoryName) {
        this.id = id;
        this.price = price;
        this.shopProductName = shopProductName;
        this.categoryName = categoryName;
    }

    public ProductShoppingUserDto(GetProductShoppingUser user) {
        this.id = user.getId();
        this.price = user.getPrice();
        this.shopProductName = user.getName();
        this.categoryName = user.getCategoryName();
    }
}
