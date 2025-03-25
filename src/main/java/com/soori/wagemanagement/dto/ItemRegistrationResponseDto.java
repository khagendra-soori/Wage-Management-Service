package com.soori.wagemanagement.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ItemRegistrationResponseDto {
    private Long id;
    private String itemName;
    private Double rate;
    private List<ComponentDto> components;

    public ItemRegistrationResponseDto(Long itemRegistrationId, String itemName, Double rate, List<ComponentDto> componentDtos) {
        this.id = itemRegistrationId;
        this.itemName = itemName;
        this.rate = rate;
        this.components = componentDtos;
    }


}
