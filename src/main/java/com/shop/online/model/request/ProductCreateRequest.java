package com.shop.online.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductCreateRequest {

    private String name;

    private Integer categoryId;

    private Float price;

    private String description;


    private List<ImageProductShoppingRequest> imageProductShoppingRequests;

    private List<SizeProductShoppingRequest> sizeProductShoppingRequests;
}
