package com.soori.wagemanagement.dto;

import com.soori.wagemanagement.entity.Department;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String email;
    private String userImage;
    private String lineManager;
    private String phoneNumber;
    private Long departmentId;
    private List<Long> roleIds;
}
