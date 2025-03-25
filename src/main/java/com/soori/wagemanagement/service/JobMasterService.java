package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.JobMasterDto;
import com.soori.wagemanagement.dto.JobMasterResponseDto;
import com.soori.wagemanagement.entity.JobMaster;

public interface JobMasterService {
    public JobMasterResponseDto createJobMaster(JobMasterDto jobMasterDto);
    public JobMasterResponseDto getJobMasterDetails(String jobMasterId, String clientName, String orderId);
    public JobMasterResponseDto updateJobMaster(String jobMasterId, JobMasterDto updatedJobMasterDto);
    public void deleteJobMaster(String jobMasterId);

}
