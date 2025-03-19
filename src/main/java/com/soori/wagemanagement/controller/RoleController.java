package com.soori.wagemanagement.controller;

import com.soori.wagemanagement.dto.RoleDto;
import com.soori.wagemanagement.service.DepartmentService;
import com.soori.wagemanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllaRoles());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(roleId));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(roleId, roleDto));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId){
        departmentService.deleteDepartment(roleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
