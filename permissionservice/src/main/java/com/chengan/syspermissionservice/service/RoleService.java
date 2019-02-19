package com.chengan.syspermissionservice.service;

import java.util.List;

import com.chengan.syspermissionapi.domain.Role;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.RoleFilter;

public interface RoleService {
    Long create(Role role, User operator) throws DuplicateRecordException;
    void update(Role role, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException;
    void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException;
    Role getById(Long id) throws NotFoundException;
    List<Role> list(Query<RoleFilter> query);
    void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, User operator)
            throws DataConflictException;
}