package com.soori.wagemanagement.controller;

import com.soori.wagemanagement.dto.ItemRegistrationDto;
import com.soori.wagemanagement.service.ItemRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class ItemRegistrationController {

    @Autowired
    private ItemRegistrationService itemRegistrationService;

    @PostMapping("/registerItem")
    public ResponseEntity<ItemRegistrationDto> addNewItem(@RequestBody ItemRegistrationDto itemRegistrationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRegistrationService.registerNewItem(itemRegistrationDto));
    }
}
