package com.shop.online.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_image")
@Entity
public class ShopProductImage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shop_product_id")
    private ShopProduct shopProduct;

    private String url;
}
