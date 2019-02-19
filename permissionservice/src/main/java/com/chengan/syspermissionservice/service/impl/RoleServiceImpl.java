package com.chengan.syspermissionservice.service.impl;

import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.domain.Role;
import com.chengan.syspermissionapi.domain.Permission;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.filter.RoleFilter;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionservice.mapper.RoleMapper;
import com.chengan.syspermissionservice.mapper.PermissionMapper;
import com.chengan.syspermissionservice.mapper.RolePermissionMapper;
import com.chengan.syspermissionservice.service.RoleService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    private void isDuplicatedName(Long id, String name) throws DuplicateRecordException {
        RoleFilter filter = new RoleFilter();
        filter.setName(name);
        Query<RoleFilter> query = new Query<>();
        List<Role> roles = roleMapper.list(query);
        if (roles.size() > 1) {
            throw new DuplicateRecordException("名称重复");
        }
        if (roles.size() > 0 && roles.get(1).getId() != id) {
            throw new DuplicateRecordException("名称重复");
        }
    }

    @Override
    @Transactional
    public Long create(Role role, User operator) throws DuplicateRecordException {
        isDuplicatedName(role.getId(), role.getName());
        role.setCreatedBy(operator.getId());
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedBy(operator.getId());
        role.setUpdatedAt(LocalDateTime.now());
        role.setRemovedBy(null);
        role.setRemovedAt(null);
        return roleMapper.add(role);
    }

    @Override
    @Transactional
    public void update(Role role, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException {
        Role updatingRole = roleMapper.getById(role.getId());
        if (updatingRole == null) {
            throw new NotFoundException();
        }
        updatingRole.setName(role.getName());
        updatingRole.setVersion(role.getVersion());
        updatingRole.setUpdatedBy(operator.getId());
        updatingRole.setUpdatedAt(LocalDateTime.now());
        isDuplicatedName(updatingRole.getId(), updatingRole.getName());
        if (roleMapper.update(updatingRole) == 0) {
            throw new DataConflictException();
        }
    }

    @Override
    @Transactional
    public void delete(Long id, Integer version, User operator) throws NotFoundException, DataConflictException {
        if (roleMapper.getById(id) == null) {
            throw new NotFoundException();
        }
        if (roleMapper.delete(id, operator.getId(), LocalDateTime.now(), version) == 0) {
            throw new DataConflictException();
        }
        rolePermissionMapper.clear(id);
    }

    @Override
    public Role getById(Long id) throws NotFoundException {
        Role role = roleMapper.getById(id);
        if (role == null) {
            throw new NotFoundException();
        }
        return role;
    }

    @Override
    public List<Role> list(Query<RoleFilter> query) {
        return roleMapper.list(query);
    }

    @Override
    @Transactional
    public void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, User operator)
            throws DataConflictException{
        PermissionFilter pFilter = new PermissionFilter();
        pFilter.setRoleId(id);
        Query<PermissionFilter> pQuery = new Query<>();
        pQuery.setFilter(pFilter);
        List<Permission> permissions = permissionMapper.list(pQuery);
        List<Long> dispatchedPIdList = permissions.stream().map(p -> p.getId()).collect(Collectors.toList());
        dispatchedPIdList.stream()
                .filter(dispatchedId -> !permissionIdList.contains(dispatchedId))
                .forEach(deletingId -> rolePermissionMapper.delete(id, deletingId));
        permissionIdList.stream()
                .filter(pId -> !dispatchedPIdList.contains(pId))
                .forEach(addingId -> rolePermissionMapper.add(id, addingId, operator.getId(), LocalDateTime.now()));
        if (roleMapper.incDispatchPermissionVersion(dispatchVersion) == 0) {
            throw new DataConflictException();
        }
    }

}