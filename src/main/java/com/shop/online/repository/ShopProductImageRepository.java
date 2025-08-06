package com.shop.online.repository;

import com.shop.online.entity.ShopProductImage;
import com.shop.online.model.dto.ShopProductImageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopProductImageRepository extends JpaRepository<ShopProductImage, Integer> {

    @Query(value = "SELECT new com.shop.online.model.dto.ShopProductImageDto(spi.id, spi.url ) " +
            " FROM ShopProductImage spi where spi.shopProduct.id=:productShoppingId")
    List<ShopProductImageDto> getListUrlImageProductShopping(Integer productShoppingId);
}
