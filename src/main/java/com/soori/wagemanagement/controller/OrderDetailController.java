package com.soori.wagemanagement.controller;

import com.soori.wagemanagement.dto.OrderDetailDto;
import com.soori.wagemanagement.dto.OrderDetailResponseDto;
import com.soori.wagemanagement.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/create")
    public ResponseEntity<OrderDetailResponseDto> createOrderDetail(@RequestBody OrderDetailDto orderDetailDto) {
        OrderDetailResponseDto savedOrderDetail = orderDetailService.saveOrderDetail(orderDetailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetail);
    }


//    @PostMapping("/addItem")
//    public ResponseEntity<OrderDetailDto> addOrderDetail(@RequestBody OrderDetailDto orderDetailDto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailService.addItemDetails(orderDetailDto));
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<OrderDetailDto>> getAllOrderDetails() {
//        return ResponseEntity.status(HttpStatus.OK).body(orderDetailService.itemAvailability());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteOrderDetailById(@PathVariable("id") Long id) {
//        return ResponseEntity.status(HttpStatus.OK).body(orderDetailService.removeItem(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDetailDto> updateOrderDetailById(@PathVariable("id") Long id, @RequestBody OrderDetailDto orderDetailDto) {
//        return ResponseEntity.status(HttpStatus.OK).body(orderDetailService.updatedItem(id, orderDetailDto));
//    }


}
