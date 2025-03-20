package com.soori.wagemanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailResponseDto {
    private Long detailId;
    private Integer quantity;
    private Double rate;
    private List<ItemDto> items;
}
