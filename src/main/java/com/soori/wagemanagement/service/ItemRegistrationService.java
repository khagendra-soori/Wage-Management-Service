package com.soori.wagemanagement.service;


import com.soori.wagemanagement.dto.ItemRegistrationRequestDto;
import com.soori.wagemanagement.dto.ItemRegistrationResponseDto;

import java.util.List;

public interface ItemRegistrationService {
    public ItemRegistrationResponseDto registerNewItem(ItemRegistrationRequestDto itemRegistrationDto);
    public List<ItemRegistrationResponseDto> getAllItems();
    public ItemRegistrationResponseDto getItemById(Long id);
    public ItemRegistrationResponseDto getItemByName(String itemName);
    public ItemRegistrationResponseDto updateItem(Long itemId, ItemRegistrationRequestDto itemRegistrationDto);
    public void deleteItem(Long id);
}
