package com.soori.wagemanagement.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemRegistrationRequestDto {
    private String itemName;
    private Double rate;
    private List<Long> componentIds;
}
