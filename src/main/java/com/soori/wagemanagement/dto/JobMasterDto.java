package com.soori.wagemanagement.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobMasterDto {
    private String clientName;
    private String address;
    private Integer quantity;
    private List<Long> itemIds;
}
