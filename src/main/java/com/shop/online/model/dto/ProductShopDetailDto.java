package com.shop.online.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProductShopDetailDto {

    private Integer productShoppingId;

    private String description;

    private Float price;
    private String nameProductShopping;
    private Integer categoryId;
    private String categoryName;
    private Integer ticketNumber;
    private List<ShopProductImageDto> shopProductImages;
    private List<ShopProductSizeDto> shopProductSizes;

    public ProductShopDetailDto(ProductShoppingDto psDto) {
        this.productShoppingId = psDto.getId();
        this.description = psDto.getDescription();
        this.price = psDto.getPrice();
        this.categoryId = psDto.getCategoryId();
        this.categoryName = psDto.getCategoryName();

    }
}
