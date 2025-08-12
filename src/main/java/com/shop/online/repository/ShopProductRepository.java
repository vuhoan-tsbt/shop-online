package com.shop.online.repository;

import com.shop.online.entity.ShopProduct;
import com.shop.online.model.dto.GetProductShoppingUser;
import com.shop.online.model.dto.ProductShoppingDto;
import com.shop.online.utils.enums.ProductEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {

    @Query("select u from ShopProduct u where u.name = :name")
    Optional<ShopProduct> getByNameProductShopping(String name);

    @Query(value = " SELECT  " +
            "        `sp`.id as id" +
            "       ,`sp`.price as price" +
            "       ,`sp`.name as name " +
            "       ,`c1`.name as categoryName " +
            " FROM product `sp`  " +
            " left join category `c1` on `sp`.category_id = `c1`.id  " +
            " where  `c1`.delete_flg = 0 " +
            " and `sp`.status='ACTIVE' " +
            " and (:categoryId is null OR `sp`.category_id = :categoryId) " +
            " and (:productShoppingName is null OR `sp`.name like %:productShoppingName%) " +
            " group by `sp`.id " +
            " having (:fromPriceProduct is null OR `sp`.price  >=:fromPriceProduct) " +
            " and (:toPriceProduct is null OR `sp`.price  <=:toPriceProduct) " +
            " order by case when (:sortBy is null) then `sp`.id end desc" +
            " ,case when (:sortBy ='SORT_UPDATED_AT') then `sp`.updated_at end desc " +
            " ,case when (:sortBy ='SORT_PRICE_LOW') then `sp`.price  end asc  " +
            " ,case when (:sortBy ='SORT_PRICE_HIGH') then `sp`.price  end desc, `sp`.created_at desc ", nativeQuery = true)
    Page<GetProductShoppingUser> getListProductShoppingUser(Pageable pageable,
                                                            Integer categoryId,
                                                            Double fromPriceProduct,
                                                            Double toPriceProduct,
                                                            String productShoppingName,
                                                            String sortBy);

    @Query(value = "SELECT new com.shop.online.model.dto.ProductShoppingDto(" +
            "         sp.id " +
            "        ,sp.name " +
            "        ,sp.shopCategory.id " +
            "        ,sp.shopCategory.name " +
            "        ,sp.price " +
            "        ,sp.description " +
            "        ,sp.status ) " +
            " FROM ShopProduct sp " +
            " where (:keyword is null OR lower(sp.name) like lower(concat('%', :keyword, '%'))) " +
            " and (:fromPriceProduct is null OR sp.price >=:fromPriceProduct) " +
            " and (:toPriceProduct is null OR sp.price <=:toPriceProduct) " +
            " and (:categoryShopId is null OR sp.shopCategory.id=:categoryShopId)")
    Page<ProductShoppingDto> getListProduct(Pageable pageable, String keyword, Float fromPriceProduct,
                                            Float toPriceProduct, Integer categoryShopId);

    @Query(value = "SELECT ps FROM ShopProduct ps where ps.shopCategory.id=:shopCategoryId")
    List<ShopProduct> getByIdShopCategory(Integer shopCategoryId);

    @Query(value = "SELECT ps FROM ShopProduct ps where ps.id = :productShoppingId ")
    Optional<ShopProduct> getByIdProductAndStatusAdmin(Integer productShoppingId);

    @Query(value = "SELECT new com.shop.online.model.dto.ProductShoppingDto(" +
            "         sp.id " +
            "        ,sp.name " +
            "        ,sp.shopCategory.name " +
            "        ,sp.price " +
            "        ,sp.description " +
            "        ,sp.shopCategory.id ) " +
            " FROM ShopProduct sp " +
            " where sp.id = :productShoppingId and sp.status = :status")
    ProductShoppingDto getDetailsProductShoppingAdmin(Integer productShoppingId, ProductEnum.StatusShopping status);

    @Query(value = "SELECT new com.shop.online.model.dto.ProductShoppingDto(" +
            "         sp.id " +
            "        ,sp.name " +
            "        ,sp.shopCategory.name " +
            "        ,sp.price " +
            "        ,sp.description " +
            "        ,sp.shopCategory.id )" +
            " FROM ShopProduct sp  " +
            " where sp.id = :productShoppingId " +
            " and sp.status = 'ACTIVE' ")
    ProductShoppingDto getDetailsProductShoppingUser(Integer productShoppingId);

    @Query(value = "SELECT ps FROM ShopProduct ps where ps.id = :productShoppingId and ps.status='ACTIVE'")
    Optional<ShopProduct> getByIdProductAndStatus(Integer productShoppingId);
}
