package com.shop.online.controller;

import com.shop.online.kafka.OrderPublisher;
import com.shop.online.model.APIResponse;
import com.shop.online.model.PageInfo;
import com.shop.online.model.dto.HistoryOrderUserDto;
import com.shop.online.model.request.UserOrderShoppingRequest;
import com.shop.online.service.UserOrderShopService;
import com.shop.online.utils.enums.ProductEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "user-shopping-order-controller")
@RestController
@RequestMapping("/api/v1/user/shopping-order")
@RequiredArgsConstructor
public class UserOrderShopController {

    private final UserOrderShopService userOrderShopService;
    private final OrderPublisher orderPublisher;

    private static final String USER_ORDER_SHOPPING = "/order";
    private static final String USER_HISTORY_ORDER_SHOPPING = "/history-order";
    private static final String USER_HISTORY_ORDER_DETAILS_SHOPPING = "/history-details-order/{id}";

    @PostMapping(USER_ORDER_SHOPPING)
    public APIResponse<?> userOrder(@RequestBody UserOrderShoppingRequest input) {
        Integer userId = userOrderShopService.getCurrentUserLogged().getId();
        input.setUserId(userId);
        orderPublisher.publishOrder(input);
        return APIResponse.okStatus(userOrderShopService.userOrder(input));
    }

    @GetMapping(USER_HISTORY_ORDER_SHOPPING)
    public APIResponse<PageInfo<HistoryOrderUserDto>> userHistoryOrder(@RequestParam(required = false) Integer page,
                                                                       @RequestParam(required = false) Integer limit,
                                                                       @RequestParam List<ProductEnum.ShopOrderStatus> status) {
        return APIResponse.okStatus(userOrderShopService.userHistoryOrder(page, limit, status));
    }

    @GetMapping(USER_HISTORY_ORDER_DETAILS_SHOPPING)
    public APIResponse<?> userHistoryOrderDetails(@PathVariable Integer id) {
        return APIResponse.okStatus(userOrderShopService.userHistoryOrderDetails(id));
    }
}
