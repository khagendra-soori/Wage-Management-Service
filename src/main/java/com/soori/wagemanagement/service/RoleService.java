package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.RoleDto;

import java.util.List;

public interface RoleService {
    public List<RoleDto> getAllaRoles();
    public RoleDto getRoleById(Long roleId);
    public RoleDto createRole(RoleDto roleDto);
    public RoleDto updateRole(Long roleId, RoleDto roleDto);
    public void deleteRole(Long roleId);
}
