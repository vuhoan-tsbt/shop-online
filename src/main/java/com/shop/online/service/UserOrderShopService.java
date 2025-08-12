package com.shop.online.service;

import com.shop.online.entity.*;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.PageInfo;
import com.shop.online.model.dto.HistoryOrderDetailsUserDto;
import com.shop.online.model.dto.HistoryOrderUserDto;
import com.shop.online.model.dto.ListProductOrderDetailDto;
import com.shop.online.model.request.UserOrderShoppingRequest;
import com.shop.online.model.request.UserProductOrderRequest;
import com.shop.online.model.response.IdResponse;
import com.shop.online.repository.*;
import com.shop.online.utils.PageUtils;
import com.shop.online.utils.enums.ProductEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrderShopService extends BaseService {

    private final ShoppingOrderRepository shoppingOrderRepository;
    private final ShoppingOrderDetailRepository shoppingOrderDetailRepository;
    private final ShopProductRepository productShoppingRepository;
    private final ShopProductSizeRepository productShoppingSizeRepository;
    private final UserRepository userRepository;
    private final ShopProductImageRepository shopProductImageRepository;

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public IdResponse userOrder(UserOrderShoppingRequest input) {

//        User currentUser = userRepository.findById(this.getCurrentUserLogged().getId())
//                .orElseThrow(() -> new ServiceApiException(MessageCode.ERRORS_AUTH_USER_02.getCode(), MessageCode.ERRORS_AUTH_USER_02.getDisplay()));

        // Insert into ShopOrder
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setTotalAmount(input.getTotalAmount());
        shopOrder.setStatus(ProductEnum.ShopOrderStatus.NEW);
        shopOrder.setUser(this.getCurrentUserLogged());
        shopOrder.setEmail(input.getEmail());
        shopOrder.setUserName(input.getUserName());

        shopOrder.setDeliveryAddress(input.getDeliveryAddress());
        shopOrder.setDescription(input.getDescription());
        // Insert into ShopOrderDetail
        List<ShoppingOrderDetail> shoppOrderDetails = new ArrayList<>();
        List<ShopProductSize> shopSizeOrderDetails = new ArrayList<>();
        for (UserProductOrderRequest orderRequest : input.getOrderRequests()) {
            ShopProduct shopProduct = productShoppingRepository.getByIdProductAndStatus(orderRequest.getProductId()).orElseThrow(()
                    -> new ServiceApiException(MessageCode.ERRORS_COMMON_001.getCode(), MessageCode.ERRORS_COMMON_001.getDisplay()));

            // Check inventory of shop product
            ShopProductSize inventoryOrder = productShoppingSizeRepository.getBySizeAndProductId(orderRequest.getSize(), orderRequest.getProductId()).orElse(null);
            if (inventoryOrder != null) {
                long countQuantity = inventoryOrder.getInventory() - Long.valueOf(orderRequest.getQuantity());
                if (countQuantity < 0) {
                    throw new ServiceApiException(MessageCode.ERRORS_SHOPPING_ORDER_01.getCode(), MessageCode.ERRORS_SHOPPING_ORDER_01.getDisplay());
                }
                inventoryOrder.setInventory(countQuantity);
                shopSizeOrderDetails.add(inventoryOrder);
            }

            ShoppingOrderDetail detail = new ShoppingOrderDetail();
            detail.setQuantity(orderRequest.getQuantity());
            detail.setProductSizeShip(orderRequest.getSize());
            detail.setShopProduct(shopProduct);
            detail.setProductNameShip(orderRequest.getProductName());
            detail.setProductPrice(orderRequest.getAmount());
            detail.setShopOrder(shopOrder);
            shoppOrderDetails.add(detail);

        }
        shoppingOrderRepository.save(shopOrder);
        shoppingOrderDetailRepository.saveAll(shoppOrderDetails);
        // Handle subtract ProductShop inventory
        productShoppingSizeRepository.saveAll(shopSizeOrderDetails);


        return new IdResponse(shopOrder.getId());
    }

    public PageInfo<HistoryOrderUserDto> userHistoryOrder(Integer page, Integer limit, List<ProductEnum.ShopOrderStatus> status) {
        User user = this.getCurrentUserLogged();
        Pageable pageable = this.buildPageRequest(page, limit);
        var response = shoppingOrderRepository.getPageHistoryOrderUser(pageable, user.getId(), status).map(p -> {
            Integer countOrder = shoppingOrderRepository.countOrderUser(user.getId(), status);
            p.setMenuNumberByStatus(countOrder);
            return p;
        });
        return PageUtils.pagingResponse(response);
    }

    public HistoryOrderDetailsUserDto userHistoryOrderDetails(Integer id) {
        User user = this.getCurrentUserLogged();
        HistoryOrderDetailsUserDto detailsUserDto = new HistoryOrderDetailsUserDto();
        var response = shoppingOrderRepository.getDetailHistoryOrderUser(id, user.getId());
        detailsUserDto.setHistoryOrderUserDto(response);
        List<ListProductOrderDetailDto> product = shoppingOrderDetailRepository.getListProductOrderDetailsUser(id);
        product.forEach(p -> shopProductImageRepository.getListUrlImageProductShopping(p.getProductId()).stream().limit(1).peek(s ->
                p.setImage(this.buildCloudFrontImageUri(s.getUrl()))).toList());
        detailsUserDto.setListProductOrderDetailDtoList(product);
        return detailsUserDto;

    }
}
