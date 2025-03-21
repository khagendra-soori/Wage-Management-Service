package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.JobMasterDto;
import com.soori.wagemanagement.dto.JobMasterResponseDto;
import com.soori.wagemanagement.entity.JobMaster;

public interface JobMasterService {
    public JobMasterResponseDto createJobMaster(JobMasterDto jobMasterDto);
   // public JobMasterDto getOrderDetails(String jobMasterId);
}
