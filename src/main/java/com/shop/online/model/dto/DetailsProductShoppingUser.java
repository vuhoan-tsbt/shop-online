package com.shop.online.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DetailsProductShoppingUser {

    private Integer productShoppingId;
    private Float price;
    private String productShoppingName;
    private String categoryName;
    private List<ShopProductImageDto> shopProductImages;
    private List<ShopProductSizeDto> shopProductSizes;

    public DetailsProductShoppingUser(ProductShoppingDto sp) {
        this.productShoppingId = sp.getId();
        this.price = sp.getPrice();
        this.productShoppingName = sp.getName();
        this.categoryName = sp.getCategoryName();

    }
}
