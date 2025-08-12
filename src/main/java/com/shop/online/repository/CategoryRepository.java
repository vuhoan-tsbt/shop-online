package com.shop.online.repository;

import com.shop.online.entity.Category;
import com.shop.online.model.dto.AdminCategoryShoppingResponseDto;
import com.shop.online.model.dto.AdminListCategoryDto;
import com.shop.online.model.dto.Limit1CategoryShoppingUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select sc " +
            " from Category sc " +
            " where  sc.id =:categoryId " +
            "   and sc.deleteFlg = false " )
    Optional<Category> getByIdCategory(Integer categoryId);

    @Query("select p from Category p where p.name=:name and p.deleteFlg = false")
    Optional<Category> getByNameCategoryShopOnline(String name);

    @Query("select new com.shop.online.model.dto.AdminListCategoryDto(" +
            "  c.id" +
            ", c.name " +
            ", c.description " +
            ", c.headerUrl ) from Category c " +
            " where (:query IS NULL OR (c.name LIKE %:query% OR c.description LIKE %:query%)) " +
            " AND c.deleteFlg = false ORDER BY c.createdAt desc")
    Page<AdminListCategoryDto> getListCategoryByCondition(@Param("query") String query, Pageable pageable);

    @Query("select new com.shop.online.model.dto.Limit1CategoryShoppingUserDto(" +
            "  sc.id" +
            " ,sc.name" +
            " ,sc.headerUrl) " +
            " from Category sc " +
            " where sc.deleteFlg = false " +
            " order by sc.id desc ")
    List<Limit1CategoryShoppingUserDto> getListCategoryShoppingUser();

    @Query("select new com.shop.online.model.dto.AdminCategoryShoppingResponseDto(" +
            "    sc.id" +
            "   ,sc.name " +
            "   ,sc.description" +
            "   ,sc.headerUrl) " +
            " from Category sc " +
            " where sc.id=:id " +
            "   and sc.deleteFlg = false " )
    Optional<AdminCategoryShoppingResponseDto> getByIdShoppingCategory(Integer id);

}
