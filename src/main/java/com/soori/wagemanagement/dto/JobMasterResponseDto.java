package com.soori.wagemanagement.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
public class JobMasterResponseDto {
    private String jobMasterId;
    private String orderId;
    private String clientName;
    private String address;
    private Integer quantity;
    private Double totalPrice;
    private List<ItemRegistrationResponseDto> items;

    public JobMasterResponseDto(String jobMasterId, String orderId, String clientName, String address, Integer quantity, Double totalPrice, List<ItemRegistrationResponseDto> itemDtos) {
        this.jobMasterId = jobMasterId;
        this.orderId = orderId;
        this.clientName = clientName;
        this.address = address;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.items = itemDtos;
    }
}
