package com.shop.online.model.dto;

import com.shop.online.utils.enums.ProductEnum;
import lombok.Data;

@Data
public class ProductShoppingDto {

    private Integer id;
    private String name;
    private String categoryName;
    private Integer categoryId;
    private Float price;
    private String description;
    private ProductEnum.StatusShopping status;
    private String image;
    private Boolean isSoldOut;
    private Integer totalInventory;

    public ProductShoppingDto(Integer id, String name, Integer categoryId,
                              String categoryName, Float price,
                              String  description, ProductEnum.StatusShopping status) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    public ProductShoppingDto(Integer id, String name, String categoryName,
                              Float price,  String description, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }
}
