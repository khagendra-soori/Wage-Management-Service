package com.soori.wagemanagement.dto;

import com.soori.wagemanagement.entity.ItemRegistration;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDto {
    private Integer quantity;
    private Double rate;
    private List<Long> itemIds;
}
