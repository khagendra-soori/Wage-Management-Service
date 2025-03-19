package com.soori.wagemanagement.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobMasterDto {
    private String jobMasterId;
    private String clientName;
    private List<ComponentDto> items;
    private Double totalAmount;
}
