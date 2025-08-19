package com.shop.online.repository;

import com.shop.online.entity.ShopOrder;
import com.shop.online.model.dto.HistoryOrderUserDto;
import com.shop.online.utils.enums.ProductEnum;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingOrderRepository extends JpaRepository<ShopOrder, Integer> {

    @Query("select new com.shop.online.model.dto.HistoryOrderUserDto(" +
            " so.id " +
            " ,so.totalAmount " +
            " ,so.createdAt " +
            " ,so.status) " +
            " from ShopOrder so " +
            " where so.status in (:status) " +
            " and so.id=:userId order by so.createdAt desc ")
    Page<HistoryOrderUserDto> getPageHistoryOrderUser(Pageable pageable, Integer userId,
                                                      List<ProductEnum.ShopOrderStatus> status);

    @Query("select count(*) from ShopOrder so where so.id=:userId and  so.status in (:status)")
    Integer countOrderUser(Integer userId, List<ProductEnum.ShopOrderStatus> status);

    @Query("select new com.shop.online.model.dto.HistoryOrderUserDto(" +
            " so.id" +
            " ,so.totalAmount" +
            " ,so.createdAt" +
            " ,so.status) " +
            " from ShopOrder so " +
            " where so.id =:shoppingOrderId " +
            " and so.id=:userId  ")
    HistoryOrderUserDto getDetailHistoryOrderUser(Integer shoppingOrderId, Integer userId);

    @Modifying
    @Query(value = "INSERT INTO shop_order_seq VALUES (NULL)", nativeQuery = true)
    void insertSeq();

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer getLastInsertId();
}
