package com.shop.online.repository;

import com.shop.online.entity.ShoppingOrderDetail;
import com.shop.online.model.dto.ListProductOrderDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingOrderDetailRepository extends JpaRepository<ShoppingOrderDetail, Integer> {


    @Query("select new com.shop.online.model.dto.ListProductOrderDetailDto(" +
            " sod.shopProduct.id" +
            " ,sod.productNameShip " +
            " ,sod.productSizeShip" +
            " ,sod.productPrice" +
            " ,sod.quantity ) " +
            " from  ShoppingOrderDetail sod left join ShopProduct sp on sod.shopProduct.id = sp.id" +
            " where sod.shopOrder.id =:shoppingOrderId")
    List<ListProductOrderDetailDto> getListProductOrderDetailsUser(Integer shoppingOrderId);

    @Query("select sum(sod.quantity) from ShoppingOrderDetail sod where sod.shopOrder.id=:shoppingOrderId")
    Integer getSumProductOrder(Integer shoppingOrderId);
}
