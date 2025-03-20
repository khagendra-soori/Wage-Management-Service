package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.ComponentDto;
import com.soori.wagemanagement.dto.ItemRegistrationDto;
import com.soori.wagemanagement.entity.Component;
import com.soori.wagemanagement.entity.ItemRegistration;
import com.soori.wagemanagement.entity.OrderDetail;
import com.soori.wagemanagement.repository.ComponentRepository;
import com.soori.wagemanagement.repository.ItemRegistrationRepository;
import com.soori.wagemanagement.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemRegistrationServiceImpl implements ItemRegistrationService {

    @Autowired
    private ItemRegistrationRepository itemRegistrationRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Transactional
    @Override
    public ItemRegistrationDto registerNewItem(ItemRegistrationDto itemRegistrationDto, Long orderDetailId) {
        //Fetch the orderDetails from the details
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(()->new RuntimeException("OrderDetail not found for ID: " + orderDetailId));

        //1. Create and save ItemRegistration
        ItemRegistration item = ItemRegistration.builder()
                .itemName(itemRegistrationDto.getItemName())
                .orderDetail(orderDetail)
                .build();
        item = itemRegistrationRepository.save(item);

        //2. Map DTO Component to Entity
        ItemRegistration finalItem = item;
        List<Component> components = itemRegistrationDto.getComponents().stream()
                .map(componentDto -> Component.builder()
                        .componentName(componentDto.getComponentName())
                        .rate(componentDto.getRate())
                        .unit(componentDto.getUnit())
                        .itemRegistration(finalItem)
                        .build()
                ).collect(Collectors.toList());
        componentRepository.saveAll(components);

        //3. Convert to DTO and return
        List<ComponentDto> componentDtos = components.stream()
                .map(component -> new ComponentDto(
                        component.getComponentId(),
                        component.getComponentName(),
                        component.getRate(),
                        component.getUnit()
                )).collect(Collectors.toList());
        return new ItemRegistrationDto(item.getItemName(), componentDtos);
    }

//    private ItemRegistration convertDtoToEntity(ItemRegistrationDto itemRegistrationDto) {
//        return ItemRegistration.builder()
//                .itemName(itemRegistrationDto.getItemName())
//                .components(itemRegistrationDto.getComponents())
//                .build();
//    }
//
//    private ItemRegistrationDto convertEntityToDto(ItemRegistration itemRegistration) {
//        return ItemRegistrationDto.builder()
//                .itemName(itemRegistration.getItemName())
//                .components(itemRegistration.getComponents())
//                .build();
//    }

    private Optional<ItemRegistration> findItemByName(String departmentName){
        return itemRegistrationRepository.findByItemName(departmentName);
    }
}
