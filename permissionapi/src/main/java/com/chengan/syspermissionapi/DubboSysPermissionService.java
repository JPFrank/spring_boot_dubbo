package com.chengan.syspermissionapi;

import com.chengan.syspermissionapi.dto.PermissionDTO;
import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;
import java.util.List;


public interface DubboSysPermissionService {
    Long create(PermissionDTO permissiondto, UserDTO operator) throws DuplicateRecordException;
    void update(PermissionDTO permissiondto, UserDTO operator) throws DataConflictException, DuplicateRecordException, NotFoundException;
    void delete(Long id, Integer version, UserDTO operator) throws NotFoundException, DataConflictException;
    PermissionDTO getById(Long id) throws NotFoundException;
    PermissionDTO getByCode(String code) throws NotFoundException;
    List<PermissionDTO> list(Query<PermissionFilter> query);
}