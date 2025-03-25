package com.soori.wagemanagement.repository;

import com.soori.wagemanagement.entity.JobMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobMasterRepository extends JpaRepository<JobMaster, String> {
    Optional<JobMaster> findByClientName(String clientName);
    Optional<JobMaster> findByOrderId(String orderId);
    Optional<JobMaster> findByJobMasterId(String jobMasterId);


}
