package com.soori.wagemanagement.service;


import com.soori.wagemanagement.dto.ItemRegistrationDto;

public interface ItemRegistrationService {
    public ItemRegistrationDto registerNewItem(ItemRegistrationDto itemRegistrationDto, Long orderDetailId);
}
