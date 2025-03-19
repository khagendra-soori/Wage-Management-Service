package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.OrderDetailDto;
import com.soori.wagemanagement.entity.ItemRegistration;
import com.soori.wagemanagement.entity.OrderDetail;
import com.soori.wagemanagement.repository.ItemRegistrationRepository;
import com.soori.wagemanagement.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private ItemRegistrationRepository itemRegistrationRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetailDto addItemDetails(OrderDetailDto orderDetailDto) {

        OrderDetail orderDetail = mapToOrderDetailEntity(orderDetailDto);

        OrderDetail saveOrderDetail = orderDetailRepository.save(orderDetail);

        return mapToOrderDetailDto(saveOrderDetail);
    }

    @Override
    public List<OrderDetailDto> itemAvailability() {

        List<OrderDetail> orderDetails = findAllOrderDetail();
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDtos.add(mapToOrderDetailDto(orderDetail));
        }

        return orderDetailDtos;

    }

    @Override
    public String removeItem(Long orderId) {
        Optional<OrderDetail> orderDetailByOrder = Optional.of(findOrderDetailByOrderId(orderId));

        if(orderDetailByOrder.isPresent()) {
            deleteOrderDetailByOrderId(orderId);
            return "Order detail with ID " + orderId + " has been removed successfully";
        }else {
            return "Order detail with ID " + orderId + " not found.";
        }
    }

    @Override
    public OrderDetailDto updatedItem(Long orderId, OrderDetailDto orderDetailDto) {
        Optional<OrderDetail> orderDetailById = Optional.of(findOrderDetailByOrderId(orderId));
        if(orderDetailById.isPresent()) {
            OrderDetail existingOrderDetail = orderDetailById.get();

            existingOrderDetail.setQuantity(orderDetailDto.getQuantity());
            existingOrderDetail.setRate(orderDetailDto.getRate());

            List<ItemRegistration> items = orderDetailDto.getItemNumber();
            if(items != null && !items.isEmpty()) {
                existingOrderDetail.setItems(items);
            }

            OrderDetail updateOrderDetail = orderDetailRepository.save(existingOrderDetail);

            return mapToOrderDetailDto(updateOrderDetail);

        }else {
            throw new RuntimeException("Order detail with ID " + orderId + " not found.");
        }
    }

    private OrderDetailDto mapToOrderDetailDto(OrderDetail orderDetail) {
        return OrderDetailDto.builder()
                        .quantity(orderDetail.getQuantity())
                        .rate(orderDetail.getRate())
                        .itemNumber(orderDetail.getItems())
                .build();
    }

    private OrderDetail mapToOrderDetailEntity(OrderDetailDto orderDetailDto) {
        // Fetch or validate the list of ItemRegistrations
        List<ItemRegistration> items = orderDetailDto.getItemNumber();
        if (items != null && !items.isEmpty()) {
            // Ensure each ItemRegistration is properly persisted
            items = items.stream()
                    .map(item -> {
                        if (item.getItemRegistrationId() != null) {
                            // Fetch the existing ItemRegistration from the database
                            return itemRegistrationRepository.findById(item.getItemRegistrationId())
                                    .orElseThrow(() -> new RuntimeException("ItemRegistration with ID " + item.getItemRegistrationId() + " not found."));
                        } else {
                            // Save the new ItemRegistration if it doesn't have an ID
                            return itemRegistrationRepository.save(item);
                        }
                    })
                    .collect(Collectors.toList());
        }

        // Build the OrderDetail entity
        return OrderDetail.builder()
                .quantity(orderDetailDto.getQuantity())
                .rate(orderDetailDto.getRate())
                .items(items) // Set the list of ItemRegistrations
                .build();
    }

    private List<OrderDetail> findAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    private OrderDetail findOrderDetailByOrderId(Long orderId) {
        return orderDetailRepository.findById(orderId).get();
    }

    private void deleteOrderDetailByOrderId(Long orderId) {
        orderDetailRepository.deleteById(orderId);
    }

}
