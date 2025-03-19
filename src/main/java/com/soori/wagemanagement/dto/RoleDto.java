package com.soori.wagemanagement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private Long roleId;
    private String roleName;
    private Long userId;
}
