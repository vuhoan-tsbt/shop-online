package com.shop.online.service;

import com.shop.online.entity.Category;
import com.shop.online.entity.ShopProduct;
import com.shop.online.entity.ShopProductImage;
import com.shop.online.entity.ShopProductSize;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.request.ImageProductShoppingRequest;
import com.shop.online.model.request.ProductCreateRequest;
import com.shop.online.model.request.SizeProductShoppingRequest;
import com.shop.online.model.response.IdResponse;
import com.shop.online.repository.CategoryRepository;
import com.shop.online.repository.ShopProductImageRepository;
import com.shop.online.repository.ShopProductRepository;
import com.shop.online.repository.ShopProductSizeRepository;
import com.shop.online.utils.enums.ProductEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopProductService extends BaseService{

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
        List<ShopProductSize> sizes = new LinkedList<>();
        for (SizeProductShoppingRequest item : input.getSizeProductShoppingRequests()) {
            ShopProductSize shoppingSize = new ShopProductSize();
            shoppingSize.setShopProduct(shopping);
            shoppingSize.setInventory(item.getQuantity());
            BeanUtils.copyProperties(item, shoppingSize);
            sizes.add(shoppingSize);
        }
        List<ShopProductImage> images = new LinkedList<>();
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
}
