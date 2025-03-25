package com.soori.wagemanagement.controller;


import com.soori.wagemanagement.dto.JobMasterDto;
import com.soori.wagemanagement.dto.JobMasterResponseDto;
import com.soori.wagemanagement.service.JobMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class JobMasterController {

    @Autowired
    private JobMasterService jobMasterService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody JobMasterDto jobMasterDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobMasterService.createJobMaster(jobMasterDto));
    }

    @GetMapping("/get")
    public ResponseEntity<JobMasterResponseDto> getJobMaster(@RequestParam(required = false) String jobMasterId,
                                                             @RequestParam(required = false) String orderId,
                                                             @RequestParam(required = false) String clientName) {
        return ResponseEntity.status(HttpStatus.OK).body(jobMasterService.getJobMasterDetails(jobMasterId, orderId, clientName));
    }

    @PutMapping("/update/{jobMasterId}")
    public ResponseEntity<JobMasterResponseDto> updateJobMaster(@PathVariable String jobMasterId, @RequestBody JobMasterDto jobMasterDto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(jobMasterService.updateJobMaster(jobMasterId, jobMasterDto));
    }

    @DeleteMapping("/delete/{jobMasterId}")
    public ResponseEntity<String> deleteJobMaster(@PathVariable String jobMasterId) throws Exception {
        jobMasterService.deleteJobMaster(jobMasterId);
        return ResponseEntity.status(HttpStatus.OK).body("Job Master " + jobMasterId + " deleted.");
    }

}
