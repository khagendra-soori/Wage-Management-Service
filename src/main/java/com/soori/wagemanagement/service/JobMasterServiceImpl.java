package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.ComponentDto;
import com.soori.wagemanagement.dto.JobMasterDto;
import com.soori.wagemanagement.entity.Component;
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
import java.util.List;
import java.util.Optional;

@Service
public class JobMasterServiceImpl implements JobMasterService{

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
                .jobMasterId(jobMaster.getJobMasterId())
                .clientName(jobMaster.getClientName())
                .items(itemDetailsDTOs)
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
