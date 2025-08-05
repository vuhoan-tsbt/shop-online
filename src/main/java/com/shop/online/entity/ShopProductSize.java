package com.shop.online.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_size")
public class ShopProductSize extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shop_product_id")
    private ShopProduct shopProduct;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "inventory")
    private Long inventory;
}
