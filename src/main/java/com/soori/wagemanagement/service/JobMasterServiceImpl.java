package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.*;
import com.soori.wagemanagement.entity.ItemRegistration;
import com.soori.wagemanagement.entity.JobMaster;
import com.soori.wagemanagement.entity.OrderDetail;
import com.soori.wagemanagement.repository.ComponentRepository;
import com.soori.wagemanagement.repository.ItemRegistrationRepository;
import com.soori.wagemanagement.repository.JobMasterRepository;
import com.soori.wagemanagement.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class JobMasterServiceImpl implements JobMasterService {

    @Autowired
    private ItemRegistrationRepository itemRegistrationRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private JobMasterRepository jobMasterRepository;


    @Override
    @Transactional
    public JobMasterResponseDto createJobMaster(JobMasterDto jobMasterDto) {
        //Fetch the other details
        OrderDetail orderDetail = orderDetailRepository.findById(jobMasterDto.getOrderDetailId())
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with id: " + jobMasterDto.getOrderDetailId()));

        //Create and associate JobMaster
        JobMaster jobMaster = JobMaster.builder()
                .clientName(jobMasterDto.getClientName())
                .address(jobMasterDto.getAddress())
                .orderDetail(orderDetail) //associate order detail
                .build();

        JobMaster savedJobMaster = jobMasterRepository.save(jobMaster);
        return mapToJobMasterResponseDto(savedJobMaster);

    }

    private JobMasterResponseDto mapToJobMasterResponseDto(JobMaster jobMaster) {
        JobMasterResponseDto dto = new JobMasterResponseDto();
        dto.setJobMasterId(jobMaster.getJobMasterId());
        dto.setClientName(jobMaster.getClientName());
        dto.setAddress(jobMaster.getAddress());

        //Convert OrderDetail to DTO
        if (jobMaster.getOrderDetail() != null) {
            dto.setOrderDetail(mapToOrderDetailResponseDto(jobMaster.getOrderDetail()));
        } else {
            dto.setOrderDetail(null);
        }

        return dto;
    }

    private OrderDetailResponseDto mapToOrderDetailResponseDto(OrderDetail orderDetail) {
        OrderDetailResponseDto dto = new OrderDetailResponseDto();
        dto.setDetailId(orderDetail.getDetailId());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setRate(orderDetail.getRate());

        //Prevent NullPointerException for items list
        if (orderDetail.getItems() != null) {
            dto.setItems(orderDetail.getItems().stream().map(this::mapToItemDto).collect(Collectors.toList()));
        } else {
            dto.setItems(new ArrayList<>());
        }

        return dto;
    }

    private ItemDto mapToItemDto(ItemRegistration itemRegistration) {
        ItemDto dto = new ItemDto();
        dto.setItemId(itemRegistration.getItemRegistrationId());
        dto.setItemName(itemRegistration.getItemName());
        return dto;
    }

    /*
    public JobMasterDto placeOrder(JobMaster jobMaster) {
        OrderDetail orderDetail = jobMaster.getOrderDetail();
        List<ItemRegistration> items = orderDetail.getItems();

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order detail items is empty");
        }

        //Validate that all items exist and have sufficient quantity
        for (ItemRegistration item : items) {
            Optional<ItemRegistration> existingItem = itemRegistrationRepository.findById(item.getItemRegistrationId());

            if(!existingItem.isPresent()) {
                throw new IllegalArgumentException("Item not found with id " + item.getItemRegistrationId());
            }

            //Check component inventory
            ItemRegistration registeredItem = existingItem.get();
            checkComponentAvailability(registeredItem.getComponents(),orderDetail.getQuantity());
        }

        //After validation, update component quantities
        for (ItemRegistration item : items) {
            ItemRegistration registeredItem = itemRegistrationRepository.findById(item.getItemRegistrationId()).get();
            updateComponentQuantities(registeredItem.getComponents(),orderDetail.getQuantity());
        }

        //Save the order and the job master
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        jobMaster.setOrderDetail(savedOrderDetail);
        JobMaster savedJobMaster = jobMasterRepository.save(jobMaster);

        return mapToJobMasterDto(savedJobMaster);

    }
    */

    /*

    //Check if all component have sufficient quantity available
    private void checkComponentAvailability(List<Component> components, Integer orderQuantity) {
        for(Component component : components) {
            Component currentComponent = componentRepository.findById(component.getComponentId()).get();

            if(currentComponent.getUnit() < orderQuantity) {
                throw new IllegalArgumentException("Insufficient component.");
            }
        }
    }

    //Update component quantities based on order
    private void updateComponentQuantities(List<Component> components, Integer orderQuantity) {
        for (Component component : components) {
            Component currentComponent = componentRepository.findById(component.getComponentId()).get();
            currentComponent.setUnit(currentComponent.getUnit() - orderQuantity);
            componentRepository.save(currentComponent);
        }
    }

    private JobMasterDto mapToJobMasterDto(JobMaster jobMaster) {
        OrderDetail orderDetail = jobMaster.getOrderDetail();
        List<ItemRegistration> items = orderDetail.getItems();

        List<ComponentDto> itemDetailsDTOs = new ArrayList<>();
        Double totalPrice = 0.0;

        for (ItemRegistration item : items) {
            Double itemTotal = orderDetail.getQuantity() * orderDetail.getRate();
            totalPrice += itemTotal;

            ComponentDto itemDetailsDTO = ComponentDto.builder()
                    .componentName(item.getItemName())
                    .unit(orderDetail.getQuantity())
                    .rate(orderDetail.getRate())
                    .build();

            itemDetailsDTOs.add(itemDetailsDTO);
        }

        return JobMasterDto.builder()
                .clientName(jobMaster.getClientName())
                .items(jobMaster.getComponents().get().setComponentId())
                .totalAmount(totalPrice)
                .build();
    }


    //Get order details by job master ID
    @Override
    public JobMasterDto getOrderDetails(String jobMasterId) {
        Optional<JobMaster> jobMaster = jobMasterRepository.findById(jobMasterId);

        return mapToJobMasterDto(jobMaster.get());
    }

    }
     */
}