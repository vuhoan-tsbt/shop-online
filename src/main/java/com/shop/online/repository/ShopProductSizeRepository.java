package com.shop.online.repository;

import com.shop.online.entity.ShopProductSize;
import com.shop.online.model.dto.ShopProductSizeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopProductSizeRepository extends JpaRepository<ShopProductSize, Integer> {

    @Query("select new com.shop.online.model.dto.ShopProductSizeDto(pss.id, pss.size, pss.quantity,pss.inventory) from ShopProductSize pss " +
            " where pss.shopProduct.id = :productShopId")
    List<ShopProductSizeDto> getListSizeProductHBN(Integer productShopId);

    @Query("select coalesce(sum(pss.inventory),0) from ShopProductSize pss " +
            " where pss.shopProduct.id = :productShopId")
    Integer sumInventoryProductHBN(Integer productShopId);

    @Query(value = "select pss from ShopProductSize pss  where pss.id=:sizeShopId and pss.shopProduct.id = :productShopId")
    Optional<ShopProductSize> getByProductId(Integer sizeShopId, Integer productShopId);

    @Query(value = "select pss from ShopProductSize pss where pss.size=:size and pss.shopProduct.id = :productId")
    Optional<ShopProductSize> getBySizeAndProductId(String size, Integer productId);
}
