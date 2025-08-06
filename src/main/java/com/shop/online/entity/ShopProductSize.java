package com.shop.online.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "product_size")
public class ShopProductSize extends BaseEntity {

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "inventory")
    private Long inventory;

    @ManyToOne
    @JoinColumn(name = "shop_product_id")
    private ShopProduct shopProduct;
}
