package com.chengan.syspermissionservice.service;

import java.util.List;

import com.chengan.syspermissionapi.domain.Permission;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;

public interface SysPermissionService {
    Long create(Permission permission, User operator) throws DuplicateRecordException;
    void update(Permission permission, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException;
    void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException;
    Permission getById(Long id)  throws NotFoundException;
    Permission getByCode(String code) throws NotFoundException;
    List<Permission> list(Query<PermissionFilter> query);
}