package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.DepartmentDto;
import com.soori.wagemanagement.entity.Department;
import com.soori.wagemanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllaDepartments() {
        List<Department> departments = findAllDepartments();
        List<DepartmentDto> departmentDtos = new ArrayList<>();

        for (Department department : departments) {
            departmentDtos.add(mapToDepartmentDTO(department));
        }

        return departmentDtos;
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = findDepartmentById(departmentId);
        return mapToDepartmentDTO(department);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        boolean departmentExists = findDepartmentByName(departmentDto.getDepartmentName()).isPresent();
        if (departmentExists) {
            return departmentDto;
        }

        Department department = mapToDepartmentEntity(departmentDto);
        Department savedDepartment = departmentRepository.save(department);

        return mapToDepartmentDTO(savedDepartment);
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
       Department existingDepartment = findDepartmentById(departmentId);
       updateExistingDepartment(existingDepartment, departmentDto);
       Department updatedDepartment = mapToDepartmentEntity(departmentDto);
       return mapToDepartmentDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        deleteDepartmentById(departmentId);
    }

    private DepartmentDto mapToDepartmentDTO(Department department){
        return DepartmentDto.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .build();
    }

    private Department mapToDepartmentEntity(DepartmentDto departmentDto){
        return Department.builder()
                .departmentId(departmentDto.getDepartmentId())
                .departmentName(departmentDto.getDepartmentName())
                .build();
    }

    private void updateExistingDepartment(Department existingDepartment, DepartmentDto departmentDto){
        existingDepartment.setDepartmentName(departmentDto.getDepartmentName());
    }

    private List<Department> findAllDepartments() {
        return departmentRepository.findAll();

    }

    private Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).get();
    }

    private void deleteDepartmentById(Long userId){
        departmentRepository.deleteById(userId);
    }

    private Optional<Department> findDepartmentByName(String departmentName){
        return departmentRepository.findByDepartmentName(departmentName);
    }


}
