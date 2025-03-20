package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.OrderDetailDto;
import com.soori.wagemanagement.dto.OrderDetailResponseDto;

import java.util.List;

public interface OrderDetailService {
    public OrderDetailResponseDto saveOrderDetail(OrderDetailDto orderDetailDto); // (Create)
//    public List<OrderDetailDto> itemAvailability(); //Get the details of the available quantity before proceeding with order(Read)
//    public String removeItem(Long orderId); //reduce the number of item after they are sell(delete)
//    OrderDetailDto updatedItem(Long orderId, OrderDetailDto orderDetailDto); //If we are adding new item(Update)
}
