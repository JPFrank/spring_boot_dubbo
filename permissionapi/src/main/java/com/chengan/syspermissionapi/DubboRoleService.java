package com.chengan.syspermissionapi;

import java.util.List;

import com.chengan.syspermissionapi.dto.RoleDTO;
import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.filter.RoleFilter;
import com.chengan.syspermissionapi.query.Query;

public interface DubboRoleService {
    Long create(RoleDTO roledto, UserDTO operator) throws DuplicateRecordException;
    void update(RoleDTO roledto, UserDTO operator) throws DuplicateRecordException, NotFoundException, DataConflictException;
    void delete(Long id, Integer version, UserDTO operator) throws NotFoundException, DataConflictException;
    RoleDTO getById(Long id) throws NotFoundException;
    List<RoleDTO> list(Query<RoleFilter> query);
    void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, UserDTO operator) throws DataConflictException;
}