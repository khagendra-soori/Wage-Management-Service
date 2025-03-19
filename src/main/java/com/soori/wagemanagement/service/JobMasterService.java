package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.JobMasterDto;
import com.soori.wagemanagement.entity.JobMaster;

public interface JobMasterService {
    public JobMasterDto placeOrder(JobMaster jobMaster) throws Exception;
    public JobMasterDto getOrderDetails(String jobMasterId);
}
