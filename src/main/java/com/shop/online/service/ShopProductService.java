package com.shop.online.service;

import com.shop.online.entity.Category;
import com.shop.online.entity.ShopProduct;
import com.shop.online.entity.ShopProductImage;
import com.shop.online.entity.ShopProductSize;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.PageInfo;
import com.shop.online.model.dto.ProductShoppingDto;
import com.shop.online.model.dto.ShopProductImageDto;
import com.shop.online.model.request.ImageProductShoppingRequest;
import com.shop.online.model.request.ProductCreateRequest;
import com.shop.online.model.request.SizeProductShoppingRequest;
import com.shop.online.model.response.IdResponse;
import com.shop.online.repository.CategoryRepository;
import com.shop.online.repository.ShopProductImageRepository;
import com.shop.online.repository.ShopProductRepository;
import com.shop.online.repository.ShopProductSizeRepository;
import com.shop.online.utils.PageUtils;
import com.shop.online.utils.StringRanDom;
import com.shop.online.utils.enums.ProductEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopProductService extends BaseService {

    private final ShopProductRepository shopProductRepository;
    private final ShopProductImageRepository shopProductImageRepository;
    private final ShopProductSizeRepository shopProductSizeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = ServiceApiException.class)
    public Object createProductShopping(@Valid ProductCreateRequest input) {

        Optional<ShopProduct> opt = shopProductRepository.getByNameProductShopping(input.getName());
        if (opt.isPresent()) {
            throw new ServiceApiException(MessageCode.ERRORS_NAME_PRODUCT_SHOPPING.getCode(), MessageCode.ERRORS_NAME_PRODUCT_SHOPPING.getDisplay());
        }
        Optional<Category> optCategory = categoryRepository.getByIdCategory(input.getCategoryId());
        if (optCategory.isEmpty()) {
            throw new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay());
        }

        ShopProduct shopping = new ShopProduct();
        shopping.setName(input.getName());
        shopping.setShopCategory(optCategory.get());
        shopping.setDescription(input.getDescription());
        shopping.setPrice(input.getPrice());
        shopping.setStatus(ProductEnum.StatusShopping.ACTIVE);
        List<ShopProductSize> sizes = new ArrayList<>();
        for (SizeProductShoppingRequest item : input.getSizeProductShoppingRequests()) {
            ShopProductSize shoppingSize = new ShopProductSize();
            shoppingSize.setShopProduct(shopping);
            shoppingSize.setInventory(item.getQuantity());
            shoppingSize.setSize(item.getSize());
            shoppingSize.setQuantity(item.getQuantity());
            sizes.add(shoppingSize);
        }
        List<ShopProductImage> images = new ArrayList<>();
        for (ImageProductShoppingRequest request : input.getImageProductShoppingRequests()) {
            ShopProductImage image = new ShopProductImage();
            image.setUrl(this.convertAwsUrlToUri(request.getUrl()));
            image.setShopProduct(shopping);
            images.add(image);
        }
        shopProductRepository.save(shopping);
        shopProductSizeRepository.saveAll(sizes);
        shopProductImageRepository.saveAll(images);
        return new IdResponse(shopping.getId());
    }

    public PageInfo<ProductShoppingDto> getListProductShopping(Integer page, Integer limit, String keyword, Float minAmount, Float maxAmount, Integer categoryShopId, String sortColumn, String sortType) {
        Pageable pageRequest = this.buildPageRequest(page, limit, this.buildSortJPA(sortColumn, sortType));
        if (keyword != null) {
            keyword = this.convertKeyword(keyword);
        }
        Page<ProductShoppingDto> result = shopProductRepository.getListProduct(pageRequest, StringRanDom.isNullOrEmpty(keyword) ? null : keyword.trim(), minAmount, maxAmount, categoryShopId);
        result.forEach(p -> {
            ShopProductImageDto dto = shopProductImageRepository.getListUrlImageProductShopping(p.getId()).get(0);
            p.setImage(this.buildCloudFrontImageUri(dto.getUrl()));
            long countInventory = shopProductSizeRepository.getListSizeProductHBN(p.getId()).stream().filter(
                    f -> f.getInventory() == 0).count();
            p.setIsSoldOut(countInventory > 0);
            Integer totalInventory = shopProductSizeRepository.sumInventoryProductHBN(p.getId());
            p.setTotalInventory(totalInventory);
        });

        return PageUtils.pagingResponse(result);
    }
}
