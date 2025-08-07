package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.request.ProductCreateRequest;
import com.shop.online.service.ShopProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin-product-shopping-controller")
@RestController
@RequestMapping("/api/v1/admin/product-shopping")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'SUB_ADMIN')")
public class ShopProductController {
    private static final String GET_LIST_PRODUCT_SHOPPING = "/list";
    private static final String GET_BY_ID_PRODUCT_SHOPPING = "/by_id/{id}";
    private static final String CREATE_PRODUCT_SHOPPING = "/create";
    private static final String UPDATE_PRODUCT_SHOPPING = "/update/{id}";
    private static final String EDIT_STATUS_PRODUCT_SHOPPING = "/edit-status/{id}";
    private final ShopProductService productService;

    @PostMapping(CREATE_PRODUCT_SHOPPING)
    public APIResponse<?> createProductShopping(@RequestBody @Valid ProductCreateRequest input) {
        return APIResponse.okStatus(productService.createProductShopping(input));
    }

    @GetMapping(GET_LIST_PRODUCT_SHOPPING)
    public APIResponse<?> getListProductShopping(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer limit,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) Float minAmount,
                                                 @RequestParam(required = false) Float maxAmount,
                                                 @RequestParam(required = false) Integer categoryShopId,
                                                 @RequestParam(required = false) String sortColumn,
                                                 @RequestParam(required = false) String sortType) {
        return APIResponse.okStatus(productService.getListProductShopping(page, limit, keyword, minAmount, maxAmount, categoryShopId, sortColumn, sortType));
    }

    @PutMapping(UPDATE_PRODUCT_SHOPPING)
    public APIResponse<?> updateProductShopping(@PathVariable Integer id, @RequestBody @Valid ProductCreateRequest input) {
        return APIResponse.okStatus(productService.updateProductShopping(id, input));
    }

    @PostMapping(EDIT_STATUS_PRODUCT_SHOPPING)
    public APIResponse<?> editStatusProductShopping(@PathVariable Integer id) {
        return APIResponse.okStatus(productService.editStatusProductShopping(id));
    }

    @GetMapping(GET_BY_ID_PRODUCT_SHOPPING)
    public APIResponse<?> getByIdProductShopping(@PathVariable Integer id) {
        return APIResponse.okStatus(productService.getByIdProductShopping(id));
    }
}
