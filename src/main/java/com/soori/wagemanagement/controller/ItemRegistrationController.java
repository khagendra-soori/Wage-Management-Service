package com.soori.wagemanagement.controller;

import com.soori.wagemanagement.dto.ItemRegistrationRequestDto;
import com.soori.wagemanagement.dto.ItemRegistrationResponseDto;
import com.soori.wagemanagement.service.ItemRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemRegistrationController {

    @Autowired
    private ItemRegistrationService itemRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<ItemRegistrationResponseDto> addNewItem(@RequestBody ItemRegistrationRequestDto itemRegistrationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRegistrationService.registerNewItem(itemRegistrationDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRegistrationResponseDto>> getAllItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemRegistrationService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRegistrationResponseDto> getItemById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemRegistrationService.getItemById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ItemRegistrationResponseDto> getByName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(itemRegistrationService.getItemByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemRegistrationResponseDto> updateItem(@PathVariable("id") Long id, @RequestBody ItemRegistrationRequestDto itemRegistrationRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(itemRegistrationService.updateItem(id, itemRegistrationRequestDto));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id){
        itemRegistrationService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body("Item deleted successfully");
    }

}
