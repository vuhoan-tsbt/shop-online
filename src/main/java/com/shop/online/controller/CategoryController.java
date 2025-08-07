package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.request.CategoryRequest;
import com.shop.online.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "category-controller")
@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private static final String GET_CATEGORY_BY_ID_END_POINT = "/{id}";
    private static final String EDIT_CATEGORY_SHOP = "edit-category";

    private static final String GET_FULL_CATEGORY = "get-full";

    @Operation(summary = "Create category Shop Online by admin")
    @PostMapping(EDIT_CATEGORY_SHOP)
    public APIResponse<?> editCategoryShopOnline( @RequestBody CategoryRequest input) {
        return APIResponse.okStatus(this.categoryService.editCategoryShopOnline(input));
    }

    @Operation(summary = "Get list category for admin")
    @GetMapping()
    public APIResponse<?> getListCategory(
            @Parameter(description = "Find by category name") @RequestParam(value = "query", required = false) String query,
            @Parameter(description = "Page") @RequestParam(value = "page", required = false) Integer page,
            @Parameter(description = "limit") @RequestParam(required = false) Integer limit) {
        return APIResponse.okStatus(this.categoryService.getListCategory(page, query, limit));
    }

    @Operation(summary = "Delete category for admin")
    @DeleteMapping(GET_CATEGORY_BY_ID_END_POINT)
    public APIResponse<?> deleteCategoryById(@PathVariable(value = "id") Integer id) {
        this.categoryService.deleteCategoryById(id);
        return APIResponse.okStatus(true);
    }

    @Operation(summary = "Get detail category by id for admin")
    @GetMapping(GET_CATEGORY_BY_ID_END_POINT)
    public APIResponse<?> getDetailCategoryById(@PathVariable(value = "id") Integer id) {
        return APIResponse.okStatus(this.categoryService.getDetailCategoryById(id));
    }
}
