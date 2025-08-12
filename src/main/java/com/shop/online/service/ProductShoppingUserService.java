package com.shop.online.service;

import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.dto.*;
import com.shop.online.repository.CategoryRepository;
import com.shop.online.repository.ShopProductImageRepository;
import com.shop.online.repository.ShopProductRepository;
import com.shop.online.repository.ShopProductSizeRepository;
import com.shop.online.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductShoppingUserService extends BaseService {
    private final ShopProductRepository shopProductRepository;
    private final ShopProductSizeRepository shopProductSizeRepository;
    private final ShopProductImageRepository shopProductImageRepository;
    private final CategoryRepository categoryRepository;

    public Object getListShopProductUser(Integer page, Integer limit, Integer categoryId,
                                         Double fromPriceProduct, Double toPriceProduct, String productShoppingName, String sortBy) {

        Pageable pageable = this.buildPageRequest(page, limit);
        if (productShoppingName != null) {
            productShoppingName = this.convertKeyword(productShoppingName);
        }
        Page<ProductShoppingUserDto> shopProductDto = shopProductRepository.getListProductShoppingUser(pageable, categoryId, fromPriceProduct, toPriceProduct, productShoppingName, sortBy).map(ProductShoppingUserDto::new);
        shopProductDto.forEach(p -> shopProductImageRepository.getListUrlImageProductShopping(p.getId()).stream().limit(1).peek(s ->
                p.setImage(this.buildCloudFrontImageUri(s.getUrl()))).toList());
        shopProductDto.forEach(p -> {
            var size = shopProductSizeRepository.getListSizeProductHBN(p.getId());
            p.setShopProductSizes(size);
        });
        return PageUtils.pagingResponse(shopProductDto);
    }


    public DetailsProductShoppingUser getByIdProductShopping(Integer id) {
        var response = shopProductRepository.getDetailsProductShoppingUser(id);
        if (response == null) {
            throw new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay());
        }
        DetailsProductShoppingUser shopping = new DetailsProductShoppingUser(response);
        var imageResponse = shopProductImageRepository.getListUrlImageProductShopping(id);
        List<ShopProductImageDto> requests = imageResponse.stream().peek(
                p -> p.setUrl(this.buildCloudFrontImageUri(p.getUrl()))).collect(Collectors.toList());
        var sizeResponse = shopProductSizeRepository.getListSizeProductUser(id);
        shopping.setShopProductSizes(sizeResponse);
        shopping.setShopProductImages(requests);
        return shopping;
    }

    public Object getListCategoryShopping() {

        CategoryShoppingUserDto dto = new CategoryShoppingUserDto();
        List<Limit1CategoryShoppingUserDto> limit1 = categoryRepository.getListCategoryShoppingUser();
        limit1.forEach(p -> p.setImage(this.buildCloudFrontImageUri(p.getImage())));
        if (!limit1.isEmpty()) {
            var response = categoryRepository.getListCategoryShoppingUser().stream().
                    peek(p -> p.setImage(this.buildCloudFrontImageUri(p.getImage()))).collect(Collectors.toList());
            dto.setShoppingUserDto(limit1.get(0));
            dto.setLimit1CategoryShoppingUserDto(response);
        }
        return dto;
    }

    public AdminCategoryShoppingResponseDto getDetailCategoryById(Integer id) {
        AdminCategoryShoppingResponseDto category = this.categoryRepository.getByIdShoppingCategory(id).orElseThrow(()
                -> new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));
        category.setLogoUrl(this.buildCloudFrontImageUri(category.getLogoUrl()));
        return category;
    }
}
