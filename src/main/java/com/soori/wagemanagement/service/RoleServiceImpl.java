package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.RoleDto;
import com.soori.wagemanagement.entity.Role;
import com.soori.wagemanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDto> getAllaRoles() {
        List<Role> roles = findAllRoles();
        List<RoleDto> roleDtos = new ArrayList<>();

        for (Role role : roles) {
           roleDtos.add(mapToRoleDTO(role));
        }

        return roleDtos;
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        Role role = findRoleById(roleId);
        return mapToRoleDTO(role);
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        boolean roleExist = findRoleName(roleDto.getRoleName()).isPresent();

        if (roleExist) {
            return roleDto;
        }

        Role role = mapToRoleEntity(roleDto);
        Role savedRole = roleRepository.save(role);

        return mapToRoleDTO(savedRole);
    }

    @Override
    public RoleDto updateRole(Long roleId, RoleDto roleDto) {
        Role existingRole = findRoleById(roleId);
        updateExistingRole(existingRole,roleDto);
        Role updatedRole = mapToRoleEntity(roleDto);
        return mapToRoleDTO(updatedRole);
    }

    private void updateExistingRole(Role existingRole, RoleDto roleDto) {
        existingRole.setRoleName(roleDto.getRoleName());
    }

    @Override
    public void deleteRole(Long roleId) {
        deleteRoleById(roleId);
    }


    private RoleDto mapToRoleDTO(Role role) {
        return RoleDto.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .userId(role.getUser().getUserId())
                .build();
    }

    private Role mapToRoleEntity(RoleDto roleDto) {
        return Role.builder()
                .roleId(roleDto.getRoleId())
                .roleName(roleDto.getRoleName())
                .build();
    }

    private List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    private Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    private void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    private Optional<Role> findRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }


}
