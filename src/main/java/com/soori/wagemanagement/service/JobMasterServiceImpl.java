package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.*;
import com.soori.wagemanagement.entity.Component;
import com.soori.wagemanagement.entity.ItemRegistration;
import com.soori.wagemanagement.entity.JobMaster;
import com.soori.wagemanagement.repository.ComponentRepository;
import com.soori.wagemanagement.repository.ItemRegistrationRepository;
import com.soori.wagemanagement.repository.JobMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobMasterServiceImpl implements JobMasterService {

    @Autowired
    private ItemRegistrationRepository itemRegistrationRepository;

    @Autowired
    private JobMasterRepository jobMasterRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Override
    @Transactional
    public JobMasterResponseDto createJobMaster(JobMasterDto jobMasterDto) {
        //1. Fetch items from the database
        List<ItemRegistration> items = fetchItems(jobMasterDto.getItemIds());

        //2. Calculate the total price for the order
        Double totalPrice = calculateTotalPrice(items, jobMasterDto.getQuantity());

        //3. Reduce the stock for each component in the items
        reduceComponentStock(items, jobMasterDto.getQuantity());

        //4. Create and save the JobMaster order
        JobMaster jobMaster = buildJobMaster(jobMasterDto, items, totalPrice);
        JobMaster savedJobMaster = jobMasterRepository.save(jobMaster);

        //5. Map the saved order to a response DTO and return it
        return mapToJobMasterResponseDto(savedJobMaster);
    }

    @Override
    public JobMasterResponseDto getJobMasterDetails(String jobMasterId, String clientName, String orderId) {
        JobMaster jobMaster = findJobMaster(jobMasterId,clientName,orderId);
        return mapToJobMasterResponseDto(jobMaster);
    }

    private JobMaster findJobMaster(String jobMasterId, String clientName, String orderId) {
        if (jobMasterId != null) {
            return jobMasterRepository.findByJobMasterId(jobMasterId).orElseThrow(
                    () -> new RuntimeException("JobMaster not found with id: " + jobMasterId)
            );
        }else if (clientName != null) {
            return jobMasterRepository.findByClientName(clientName).orElseThrow(
                    () -> new RuntimeException("Client not found with name: " + clientName)
            );
        }
        else if (orderId != null) {
            return jobMasterRepository.findByOrderId(orderId).orElseThrow(
                    () -> new RuntimeException("Order not found with id: " + orderId)
            );
        }

        throw new RuntimeException("Invalid Parameter");
    }

    @Override
    @Transactional
    public JobMasterResponseDto updateJobMaster(String jobMasterId, JobMasterDto updatedJobMasterDto) {
        JobMaster existingJobMaster = jobMasterRepository.findByJobMasterId(jobMasterId).orElseThrow(
                () -> new RuntimeException("JobMaster not found with id: " + jobMasterId)
        );

        //Update only allowed field
        if(updatedJobMasterDto.getClientName() != null){
            existingJobMaster.setClientName(updatedJobMasterDto.getClientName());
        }
        if (updatedJobMasterDto.getAddress() != null) {
            existingJobMaster.setAddress(updatedJobMasterDto.getAddress());
        }
        if (updatedJobMasterDto.getQuantity() != null && updatedJobMasterDto.getQuantity() > 0) {
            existingJobMaster.setQuantity(updatedJobMasterDto.getQuantity());
        }

        JobMaster updatedJobMaster = jobMasterRepository.save(existingJobMaster);
        return mapToJobMasterResponseDto(updatedJobMaster);
    }

    @Override
    @Transactional
    public void deleteJobMaster(String jobMasterId) {
        JobMaster jobMaster = jobMasterRepository.findByJobMasterId(jobMasterId).orElseThrow(
                () -> new RuntimeException("JobMaster not found with id: " + jobMasterId)
        );
        jobMasterRepository.delete(jobMaster);
    }


    private List<ItemRegistration> fetchItems(List<Long> itemIds){
        List<ItemRegistration> items = itemRegistrationRepository.findAllById(itemIds);
        if(items.isEmpty()){
            throw new RuntimeException("No items found");
        }
        return items;
    }

    private Double calculateTotalPrice(List<ItemRegistration> items, int quantity){
        double totalPrice = 0.0;
        for (ItemRegistration item : items) {
            if(item.getRate() != null){
                totalPrice += item.getRate();
            }
        }
        return totalPrice * quantity;
    }

    private void reduceComponentStock(List<ItemRegistration> items, int orderQuantity){
        for (ItemRegistration item : items) {
            for (Component component : item.getComponents()) {
                if (component.getUnit() < orderQuantity) {
                    throw new RuntimeException("Component "+component.getComponentName()+" not enough");
                }

                //Reduce stock and save component update
                component.setUnit(component.getUnit()-orderQuantity);
                componentRepository.save(component);
            }
        }
    }

    private JobMaster buildJobMaster(JobMasterDto jobMasterDto, List<ItemRegistration> items, Double totalPrice) {
        return JobMaster.builder()
                .clientName(jobMasterDto.getClientName())
                .address(jobMasterDto.getAddress())
                .quantity(jobMasterDto.getQuantity())
                .totalPrice(totalPrice)
                .items(items)
                .build();
    }

    private JobMasterResponseDto mapToJobMasterResponseDto(JobMaster jobMaster){
        //Map ItemRegistration to their Response DTO
        List<ItemRegistrationResponseDto> itemDtos = jobMaster.getItems().stream()
                .map(item -> new ItemRegistrationResponseDto(
                        item.getItemRegistrationId(),
                        item.getItemName(),
                        item.getRate(),
                        mapComponentsToDto(item.getComponents())
                )).collect(Collectors.toList());

        return JobMasterResponseDto.builder()
                .jobMasterId(jobMaster.getJobMasterId())
                .orderId(jobMaster.getOrderId())
                .clientName(jobMaster.getClientName())
                .address(jobMaster.getAddress())
                .quantity(jobMaster.getQuantity())
                .totalPrice(jobMaster.getTotalPrice())
                .items(itemDtos)
                .build();
    }

    private List<ComponentDto> mapComponentsToDto(List<Component> components){
        return components.stream()
                .map(component -> new ComponentDto(
                        component.getComponentId(),
                        component.getComponentName(),
                        null
                )).collect(Collectors.toList());
    }

}