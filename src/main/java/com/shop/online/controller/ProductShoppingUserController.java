package com.shop.online.controller;


import com.shop.online.model.APIResponse;
import com.shop.online.model.dto.AdminCategoryShoppingResponseDto;
import com.shop.online.service.ProductShoppingUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user-product-shopping-controller")
@RestController
@RequestMapping("/api/v1/user/product-shopping")
@RequiredArgsConstructor
public class ProductShoppingUserController {
    private final ProductShoppingUserService productShoppingUserService;

    private static final String GET_LIST_PRODUCT_SHOPPING_USER = "/list";
    private static final String GET_DETAILS_PRODUCT_SHOPPING_USER = "/by-id/{id}";
    private static final String GET_LIST_CATEGORY_SHOPPING_USER = "/list-category-shopping";
    private static final String GET_DETAILS_CATEGORY_SHOPPING_USER = "/details-category-shopping/{id}";

    @GetMapping(GET_LIST_PRODUCT_SHOPPING_USER)
    public APIResponse<?> getListProductShopping(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer limit,
                                                 @RequestParam(required = false) Integer categoryId,
                                                 @RequestParam(required = false) Double fromPriceProduct,
                                                 @RequestParam(required = false) Double toPriceProduct,
                                                 @RequestParam(required = false) String productShoppingName,
                                                 @RequestParam(required = false) String sortBy) {
        return APIResponse.okStatus(productShoppingUserService.getListShopProductUser(page, limit, categoryId,
                fromPriceProduct, toPriceProduct, productShoppingName, sortBy));
    }

    @GetMapping(GET_DETAILS_PRODUCT_SHOPPING_USER)
    public APIResponse<?> getByIdProductShopping(@PathVariable Integer id) {
        return APIResponse.okStatus(productShoppingUserService.getByIdProductShopping(id));
    }

    @GetMapping(GET_LIST_CATEGORY_SHOPPING_USER)
    public APIResponse<?> getListCategoryShopping() {
        return APIResponse.okStatus(productShoppingUserService.getListCategoryShopping());
    }

    @Operation(summary = "Get detail category shopping by id for user")
    @GetMapping(GET_DETAILS_CATEGORY_SHOPPING_USER)
    public APIResponse<AdminCategoryShoppingResponseDto> getDetailCategoryById(@PathVariable(value = "id") Integer id) {
        return APIResponse.okStatus(this.productShoppingUserService.getDetailCategoryById(id));
    }
}
