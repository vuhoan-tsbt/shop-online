package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.request.ProductCreateRequest;
import com.shop.online.service.ShopProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin-product-shopping-controller")
@RestController
@RequestMapping("/api/v1/admin/product_shopping")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ROLE_SUB_ADMIN')")
public class ShopProductController {
    private static final String GET_LIST_PRODUCT_SHOPPING = "/list";
    private static final String GET_BY_ID_PRODUCT_SHOPPING = "/by_id/{id}";
    private static final String CREATE_PRODUCT_SHOPPING = "/create";
    private static final String UPDATE_PRODUCT_SHOPPING = "/update/{id}";
    private static final String EDIT_STATUS_PRODUCT_SHOPPING = "/edit-status/{id}";
    private static final String RECOMMEND_PRODUCT_SHOPPING = "/recommend/{id}";

    private final ShopProductService productService;

    @PostMapping(CREATE_PRODUCT_SHOPPING)
    public APIResponse<?> createProductShopping(@RequestBody @Valid ProductCreateRequest input) {
        return APIResponse.okStatus(productService.createProductShopping(input));
    }
}
