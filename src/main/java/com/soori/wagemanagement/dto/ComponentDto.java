package com.soori.wagemanagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComponentDto {
    private String componentName;
    private Double rate;
    private Integer unit;
}
