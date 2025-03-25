package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.ComponentDto;
import com.soori.wagemanagement.dto.ItemRegistrationRequestDto;
import com.soori.wagemanagement.dto.ItemRegistrationResponseDto;
import com.soori.wagemanagement.entity.Component;
import com.soori.wagemanagement.entity.ItemRegistration;
import com.soori.wagemanagement.repository.ComponentRepository;
import com.soori.wagemanagement.repository.ItemRegistrationRepository;
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

    /*
    @Transactional
    @Override
    public ItemRegistrationResponseDto registerNewItem(ItemRegistrationRequestDto itemRegistrationDto) {
        //Fetch Components from the database
        List<Component> components = componentRepository.findAllById(
                itemRegistrationDto.getComponentIds()
        );

        if(components.isEmpty()) {
            throw new RuntimeException("Invalid Component id..");
        }

        //Reduce the quantity of each component by 1
        for(Component component : components){
            if(component.getUnit()<=0){
                throw new RuntimeException("Not enough Stock for component: "+component.getComponentName());
            }
            component.setUnit(component.getUnit()-1);
        }
        //Save the updated quantities
        componentRepository.saveAll(components);

        //Create and save Item Registration
        ItemRegistration item = ItemRegistration.builder()
                .itemName(itemRegistrationDto.getItemName())
                .rate(itemRegistrationDto.getRate())
                .components(components)
                .build();

        item = itemRegistrationRepository.save(item);

        //Convert to Response Dto
        List<ComponentDto> componentDtos = components.stream()
                .map(component -> new ComponentDto(
                        component.getComponentId(),
                        component.getComponentName(),
                        component.getUnit()
                )).collect(Collectors.toList());

        return new ItemRegistrationResponseDto(item.getItemRegistrationId(), item.getItemName(),item.getRate(),componentDtos);
    }


    private Optional<ItemRegistration> findItemByName(String departmentName){
        return itemRegistrationRepository.findByItemName(departmentName);
    }
    */

    @Transactional
    @Override
    public ItemRegistrationResponseDto registerNewItem(ItemRegistrationRequestDto itemRegistrationDto) {
        List<Component> components = fetchAndValidateComponents(itemRegistrationDto.getComponentIds());
        updateComponentStock(components);
        ItemRegistration item = saveNewItem(itemRegistrationDto,components);
        return convertToDto(item);
    }

    @Override
    public List<ItemRegistrationResponseDto> getAllItems() {
        List<ItemRegistration> items = itemRegistrationRepository.findAll();
        return items.stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ItemRegistrationResponseDto getItemById(Long id) {
        ItemRegistration item = itemRegistrationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No such item id " + id)
        );

        return convertToDto(item);
    }

    @Override
    public ItemRegistrationResponseDto getItemByName(String itemName) {
        ItemRegistration item = itemRegistrationRepository.findByItemName(itemName).orElseThrow(
                () -> new IllegalArgumentException("No such item name " + itemName)
        );
        return convertToDto(item);
    }

    @Override
    public ItemRegistrationResponseDto updateItem(Long itemId, ItemRegistrationRequestDto itemRegistrationDto) {
        ItemRegistration item = itemRegistrationRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("No such item id " + itemId)
        );

        List<Component> updateComponents = fetchAndValidateComponents(itemRegistrationDto.getComponentIds());
        updateComponentStock(updateComponents);

        item.setItemName(itemRegistrationDto.getItemName());
        item.setRate(itemRegistrationDto.getRate());
        item.setComponents(updateComponents);

        item = itemRegistrationRepository.save(item);
        return convertToDto(item);
    }

    @Transactional
    @Override
    public void deleteItem(Long id) {
        if(!itemRegistrationRepository.existsById(id)) {
            throw new RuntimeException("No such item id " + id);
        }
        itemRegistrationRepository.deleteById(id);
    }

    private List<Component> fetchAndValidateComponents(List<Long> componentIds) {
        List<Component> components = componentRepository.findAllById(componentIds);
        if(components.isEmpty()) {
            throw new RuntimeException("Invalid componentIds");
        }
        return components;
    }


    private void updateComponentStock(List<Component> components) {
        for (Component component : components) {
            if(component.getUnit() <=0) {
                throw new RuntimeException("Not enough unit for component: "+component.getComponentName());
            }
            component.setUnit(component.getUnit() - 1);
        }
        componentRepository.saveAll(components);
    }

    private ItemRegistration saveNewItem(ItemRegistrationRequestDto dto, List<Component> components) {
        ItemRegistration item = ItemRegistration.builder()
                .itemName(dto.getItemName())
                .rate(dto.getRate())
                .components(components)
                .build();

        return itemRegistrationRepository.save(item);
    }

    private ItemRegistrationResponseDto convertToDto(ItemRegistration itemRegistration) {
        List<ComponentDto> componentDtos = itemRegistration.getComponents().stream()
                .map(component -> new ComponentDto(
                        component.getComponentId(),
                        component.getComponentName(),
                        component.getUnit()
                )).collect(Collectors.toList());

        return new ItemRegistrationResponseDto(itemRegistration.getItemRegistrationId(),itemRegistration.getItemName(),itemRegistration.getRate(),componentDtos);
    }
}
