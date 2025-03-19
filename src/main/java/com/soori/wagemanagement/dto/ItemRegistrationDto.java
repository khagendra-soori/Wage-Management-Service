package com.soori.wagemanagement.dto;

import com.soori.wagemanagement.entity.Component;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemRegistrationDto {
    private String itemName;
    private List<Component> components;
}
