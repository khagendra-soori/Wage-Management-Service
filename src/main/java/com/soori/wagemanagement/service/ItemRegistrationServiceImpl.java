package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.ItemRegistrationDto;
import com.soori.wagemanagement.entity.ItemRegistration;
import com.soori.wagemanagement.repository.ItemRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemRegistrationServiceImpl implements ItemRegistrationService {

    @Autowired
    private ItemRegistrationRepository itemRegistrationRepository;

    @Override
    public ItemRegistrationDto registerNewItem(ItemRegistrationDto itemRegistrationDto) {
        boolean itemExists = findItemByName(itemRegistrationDto.getItemName()).isPresent();

        if (itemExists) {
            return itemRegistrationDto;
        }

        ItemRegistration item = convertDtoToEntity(itemRegistrationDto);
        ItemRegistration savedItem = itemRegistrationRepository.save(item);

        return convertEntityToDto(savedItem);
    }

    private ItemRegistration convertDtoToEntity(ItemRegistrationDto itemRegistrationDto) {
        return ItemRegistration.builder()
                .itemName(itemRegistrationDto.getItemName())
                .components(itemRegistrationDto.getComponents())
                .build();
    }

    private Optional<ItemRegistration> findItemByName(String departmentName){
        return itemRegistrationRepository.findByItemName(departmentName);
    }

    private ItemRegistrationDto convertEntityToDto(ItemRegistration itemRegistration) {
        return ItemRegistrationDto.builder()
                .itemName(itemRegistration.getItemName())
                .components(itemRegistration.getComponents())
                .build();
    }
}
