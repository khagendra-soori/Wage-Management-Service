package com.soori.wagemanagement.controller;


import com.soori.wagemanagement.dto.JobMasterDto;
import com.soori.wagemanagement.entity.JobMaster;
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

//    @GetMapping("/{jobMasterId}")
//    public ResponseEntity<?> getOrderDetails(@PathVariable("jobMasterId") String jobMasterId) throws Exception {
//        return ResponseEntity.status(HttpStatus.OK).body(jobMasterService.getOrderDetails(jobMasterId));
//
//    }
}
