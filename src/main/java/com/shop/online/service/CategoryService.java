package com.shop.online.service;

import com.shop.online.entity.Category;
import com.shop.online.entity.ShopProduct;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.PageInfo;
import com.shop.online.model.dto.AdminListCategoryDto;
import com.shop.online.model.request.CategoryRequest;
import com.shop.online.model.response.IdResponse;
import com.shop.online.repository.CategoryRepository;
import com.shop.online.repository.ShopProductRepository;
import com.shop.online.utils.PageUtils;
import com.shop.online.utils.StringRanDom;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService {

    private final CategoryRepository categoryRepository;
    private final ShopProductRepository shopProductRepository;

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Object editCategoryShopOnline(CategoryRequest input) {
        if (ObjectUtils.isEmpty(input.getId())) {
            Optional<Category> otpCategory = categoryRepository.getByNameCategoryShopOnline(input.getName());
            if (otpCategory.isPresent()) {
                throw new ServiceApiException(MessageCode.ERRORS_CATEGORY_NAME_EXIST.getCode(), MessageCode.ERRORS_CATEGORY_NAME_EXIST.getDisplay());
            }
            Category category = new Category();
            category.setName(input.getName());
            category.setDescription(input.getDescription());
            category.setHeaderUrl(input.getHeaderUrl());
            if (input.getDescription() != null && input.getDescription().length() > 3000) {
                throw new ServiceApiException(MessageCode.ERRORS_CATEGORY_SHOP_03.getCode(), MessageCode.ERRORS_CATEGORY_SHOP_03.getDisplay());
            }
            categoryRepository.save(category);
            return new IdResponse(category.getId());
        } else {
            Category category = this.categoryRepository.findById(input.getId()).orElseThrow(()
                    -> new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));
            Optional<Category> otpCategory = categoryRepository.getByNameCategoryShopOnline(input.getName());
            if (otpCategory.isPresent() && !Objects.equals(category.getId(), otpCategory.get().getId())) {
                throw new ServiceApiException(MessageCode.ERRORS_CATEGORY_NAME_EXIST.getCode(), MessageCode.ERRORS_CATEGORY_NAME_EXIST.getDisplay());
            }
            category.setName(input.getName());
            category.setDescription(input.getDescription());
            if (input.getDescription() != null && input.getDescription().length() > 3000) {
                throw new ServiceApiException(MessageCode.ERRORS_CATEGORY_SHOP_03.getCode(), MessageCode.ERRORS_CATEGORY_SHOP_03.getDisplay());
            }
            categoryRepository.save(category);
            return new IdResponse(category.getId());
        }
    }

    public PageInfo<AdminListCategoryDto> getListCategory(Integer page, String query, Integer limit) {
        Pageable pageable = this.buildPageRequest(page, limit);
        Page<AdminListCategoryDto> data = this.categoryRepository.getListCategoryByCondition(StringRanDom.isNullOrEmpty(query) ? null : query.trim(),
                pageable);
        data.forEach(item -> item.setHeaderUrl(this.buildCloudFrontImageUri(item.getHeaderUrl())));
        return PageUtils.pagingResponse(data);
    }

    public void deleteCategoryById(Integer id) {

        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));
        List<ShopProduct> listSp = shopProductRepository.getByIdShopCategory(id);
        if (!listSp.isEmpty()) {
            throw new ServiceApiException(MessageCode.ERRORS_CATEGORY_DELETE.getCode(), MessageCode.ERRORS_CATEGORY_DELETE.getDisplay());
        }
        category.setDeleteFlg(true);
        this.categoryRepository.save(category);
    }

    public AdminListCategoryDto getDetailCategoryById(Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));
        AdminListCategoryDto dto = new AdminListCategoryDto();
        if (Objects.nonNull(category)) {
            BeanUtils.copyProperties(category, dto);
            dto.setHeaderUrl(this.buildCloudFrontImageUri(dto.getHeaderUrl()));
        }
        return dto;
    }
}
