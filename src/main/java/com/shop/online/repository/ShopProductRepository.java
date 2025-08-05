package com.shop.online.repository;

import com.shop.online.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {

    @Query("select u from ShopProduct u where u.name = :name")
    Optional<ShopProduct> getByNameProductShopping(String name);
}
