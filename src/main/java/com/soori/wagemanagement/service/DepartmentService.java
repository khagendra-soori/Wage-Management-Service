package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    public List<DepartmentDto> getAllaDepartments();
    public DepartmentDto getDepartmentById(Long departmentId);
    public DepartmentDto createDepartment(DepartmentDto departmentDto);
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto);
    public void deleteDepartment(Long departmentId);
}
