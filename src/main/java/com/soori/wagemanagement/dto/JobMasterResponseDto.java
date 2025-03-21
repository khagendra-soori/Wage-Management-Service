package com.soori.wagemanagement.dto;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobMasterResponseDto {
    private String jobMasterId;
    private String clientName;
    private String address;
    private OrderDetailResponseDto orderDetail; //Nested DTO for order detail
}
