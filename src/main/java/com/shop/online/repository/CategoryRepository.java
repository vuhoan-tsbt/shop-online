package com.shop.online.repository;

import com.shop.online.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select sc " +
            " from Category sc " +
            " where  sc.id =:categoryId " +
            "   and sc.deleteFlg = false " )
    Optional<Category> getByIdCategory(Integer categoryId);
}
