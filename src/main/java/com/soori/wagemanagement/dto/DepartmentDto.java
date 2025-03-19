package com.soori.wagemanagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {
    private Long departmentId;
    private String departmentName;
}
