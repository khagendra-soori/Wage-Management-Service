package com.soori.wagemanagement.controller;

import com.soori.wagemanagement.dto.DepartmentDto;
import com.soori.wagemanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody  DepartmentDto departmentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllaDepartments());
    }

    @GetMapping("/{deptId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartmentById(id));
    }

    @PutMapping("/{deptId}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long deptId, @RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.updateDepartment(deptId,departmentDto));
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long deptId) {
        departmentService.deleteDepartment(deptId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
