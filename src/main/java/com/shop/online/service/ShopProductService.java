package com.shop.online.service;

import com.shop.online.entity.Category;
import com.shop.online.entity.ShopProduct;
import com.shop.online.entity.ShopProductImage;
import com.shop.online.entity.ShopProductSize;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.PageInfo;
import com.shop.online.model.dto.ProductShopDetailDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        if (result == null) {
            return new PageInfo<>();
        }
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

    @Transactional(rollbackFor = ServiceApiException.class)
    public Object updateProductShopping(Integer id, @Valid ProductCreateRequest input) {
        Optional<ShopProduct> opt = shopProductRepository.getByIdProductAndStatusAdmin(id);
        if (opt.isEmpty()) {
            throw new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay());
        }
        Optional<Category> optCategory = categoryRepository.getByIdCategory(input.getCategoryId());
        if (optCategory.isEmpty()) {
            throw new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay());
        }
        opt.get().setName(input.getName());
        Optional<ShopProduct> optUd = shopProductRepository.getByNameProductShopping(input.getName());
        if (optUd.isPresent() && !Objects.equals(opt.get().getId(), optUd.get().getId())) {
            throw new ServiceApiException(MessageCode.ERRORS_NAME_UPDATE_PRODUCT_SHOPPING.getCode(), MessageCode.ERRORS_NAME_UPDATE_PRODUCT_SHOPPING.getDisplay());
        }
        opt.get().setShopCategory(optCategory.get());
        opt.get().setDescription(input.getDescription());
        opt.get().setPrice(input.getPrice());
        List<ShopProductSize> sizeList = new ArrayList<>();
        for (SizeProductShoppingRequest item : input.getSizeProductShoppingRequests()) {
            Optional<ShopProductSize> sizes = shopProductSizeRepository.getByProductId(item.getId(), opt.get().getId());
            if (sizes.isPresent()) {
                if (item.getSize() == null || item.getSize().isBlank()) {
                    shopProductSizeRepository.deleteAllById(Collections.singletonList(item.getId()));
                } else {
                    if (item.getQuantity() != null && sizes.get().getQuantity() != null) {
                        long inventory = item.getQuantity() - sizes.get().getQuantity();
                        if (sizes.get().getInventory() + inventory < 0) {
                            throw new ServiceApiException(MessageCode.ERRORS_UPDATE_INVENTORY_PRODUCT_SHOPPING.getCode(), MessageCode.ERRORS_UPDATE_INVENTORY_PRODUCT_SHOPPING.getDisplay());
                        } else {
                            sizes.get().setInventory(sizes.get().getInventory() + inventory);
                        }
                        sizeList.add(sizes.get());
                    }
                    sizes.get().setSize(item.getSize());
                    sizes.get().setQuantity(item.getQuantity());
                    sizeList.add(sizes.get());
                }
            } else {
                Optional<ShopProductSize> optSize = shopProductSizeRepository.getBySizeAndProductId(item.getSize(), opt.get().getId());
                if (optSize.isPresent()) {
                    throw new ServiceApiException(MessageCode.ERRORS_NAME_AND_SIZE_PRODUCT_SHOPPING.getCode(), MessageCode.ERRORS_NAME_AND_SIZE_PRODUCT_SHOPPING.getDisplay());
                }
                ShopProductSize productShoppingSize = new ShopProductSize();
                productShoppingSize.setSize(item.getSize());
                productShoppingSize.setQuantity(item.getQuantity());
                productShoppingSize.setInventory(item.getQuantity());
                productShoppingSize.setShopProduct(opt.get());
                sizeList.add(productShoppingSize);
            }
        }
        List<ShopProductImage> images = new ArrayList<>();
        for (ImageProductShoppingRequest request : input.getImageProductShoppingRequests()) {
            Optional<ShopProductImage> image = shopProductImageRepository.getByImageProductShopping(request.getId(), opt.get().getId());
            if (image.isPresent()) {
                if (request.getUrl() == null || request.getUrl().isEmpty()) {
                    shopProductImageRepository.deleteAllById(Collections.singletonList(image.get().getId()));
                } else {
                    image.get().setUrl(this.convertAwsUrlToUri(request.getUrl()));
                    images.add(image.get());
                }
            } else {
                ShopProductImage shoppingImage = new ShopProductImage();
                shoppingImage.setShopProduct(opt.get());
                shoppingImage.setUrl(this.convertAwsUrlToUri(request.getUrl()));
                images.add(shoppingImage);
            }
        }
        shopProductRepository.save(opt.get());
        shopProductSizeRepository.saveAll(sizeList);
        shopProductImageRepository.saveAll(images);
        return new IdResponse(opt.get().getId());
    }

    public Object editStatusProductShopping(Integer id) {

        ShopProduct opt = shopProductRepository.findById(id).orElseThrow(() ->
                new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));
        if (opt.getStatus().equals(ProductEnum.StatusShopping.ACTIVE)) {
            opt.setStatus(ProductEnum.StatusShopping.DELETED);
        } else {
            opt.setStatus(ProductEnum.StatusShopping.ACTIVE);
        }
        shopProductRepository.save(opt);
        return new IdResponse(opt.getId());
    }

    public Object getByIdProductShopping(Integer id) {

        var response = shopProductRepository.getDetailsProductShoppingAdmin(id, ProductEnum.StatusShopping.ACTIVE);
        if (response == null) {
            throw new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay());
        }
        ProductShopDetailDto shopDetail = new ProductShopDetailDto(response);
        var imageResponse = shopProductImageRepository.getListUrlImageProductShopping(id);
        List<ShopProductImageDto> listImageProductDto = imageResponse.stream().peek(p -> p.setUrl(this.buildCloudFrontImageUri(p.getUrl()))).collect(Collectors.toList());
        shopDetail.setShopProductSizes(shopProductSizeRepository.getListSizeProductHBN(id));
        shopDetail.setShopProductImages(listImageProductDto);
        return shopDetail;
    }

}
