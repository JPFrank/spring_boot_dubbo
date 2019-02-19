package com.chengan.syspermissionservice.service.impl;

import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.domain.Role;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.filter.UserFilter;
import com.chengan.syspermissionapi.query.filter.RoleFilter;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionservice.mapper.UserMapper;
import com.chengan.syspermissionservice.mapper.RoleMapper;
import com.chengan.syspermissionservice.mapper.UserRoleMapper;
import com.chengan.syspermissionapi.vo.Gender;
import com.chengan.syspermissionapi.vo.UserStatus;

import com.chengan.syspermissionservice.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    private void isDuplicatedCode(Long id, String code) throws DuplicateRecordException {
        User user = userMapper.getByCode(code);
        if (user != null && user.getId() != id) {
            throw new DuplicateRecordException("用户编码重复");
        }
    }

    private void isDuplicatedName(Long id, String name) throws DuplicateRecordException {
        UserFilter userFilter = new UserFilter();
        userFilter.setName(name);
        Query<UserFilter> userQuery = new Query<>();
        userQuery.setFilter(userFilter);
        List<User> users = userMapper.list(userQuery);
        if (users.size() > 1) {
            throw new DuplicateRecordException("用户名称重复");
        }
        if (users.size() > 0 && users.get(1).getId() != id) {
            throw new DuplicateRecordException("用户名称重复");
        }
    }

    @Override
    @Transactional
    public Long create(User user) throws DuplicateRecordException {
        isDuplicatedCode(user.getId(), user.getCode());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        System.out.println(user);
        return userMapper.add(user);
    }

    @Override
    @Transactional
    public Long create(User user, User operator) throws DuplicateRecordException {
        isDuplicatedCode(user.getId(), user.getCode());
        isDuplicatedName(user.getId(), user.getName());
        user.setStatus(UserStatus.AVAILABLE);
        user.setCreatedBy(operator.getId());
        user.setUpdatedBy(operator.getId());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userMapper.add(user);
    }

    @Override
    @Transactional
    public void update(User user)
            throws DuplicateRecordException, NotFoundException, DataConflictException {
        User updatingUser = userMapper.getById(user.getId());
        if (updatingUser == null) {
            throw new NotFoundException();
        }
        updatingUser.setCode(user.getCode());
        updatingUser.setName(user.getName());
        updatingUser.setGender(user.getGender());
        updatingUser.setDepartmentId(user.getDepartmentId());
        updatingUser.setIsDepartmentMaster(user.getIsDepartmentMaster());
        updatingUser.setPosition(user.getPosition());
        updatingUser.setUpdatedBy(user.getId());
        updatingUser.setUpdatedAt(LocalDateTime.now());
        updatingUser.setVersion(user.getVersion());
        isDuplicatedCode(updatingUser.getId(), updatingUser.getCode());
        isDuplicatedName(updatingUser.getId(), updatingUser.getName());
        if (userMapper.update(updatingUser) == 0) {
            throw new DataConflictException();
        }
    }

    @Override
    @Transactional
    public void update(User user, User operator)
            throws DuplicateRecordException, NotFoundException, DataConflictException {
        User updatingUser = userMapper.getById(user.getId());
        if (updatingUser == null) {
            throw new NotFoundException();
        }
        updatingUser.setCode(user.getCode());
        updatingUser.setName(user.getName());
        updatingUser.setGender(user.getGender());
        updatingUser.setDepartmentId(user.getDepartmentId());
        updatingUser.setIsDepartmentMaster(user.getIsDepartmentMaster());
        updatingUser.setPosition(user.getPosition());
        updatingUser.setUpdatedBy(operator.getId());
        updatingUser.setUpdatedAt(LocalDateTime.now());
        updatingUser.setVersion(user.getVersion());
        isDuplicatedCode(updatingUser.getId(), updatingUser.getCode());
        isDuplicatedName(updatingUser.getId(), updatingUser.getName());
        if (userMapper.update(updatingUser) == 0) {
            throw new DataConflictException();
        }
    }

    @Override
    public User getById(Long id) throws NotFoundException {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    public User getByCode(String code) throws NotFoundException {
        User user = userMapper.getByCode(code);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    public List<User> list(Query<UserFilter> query) {
        return userMapper.list(query);
    }

    @Override
    public void freeze(Long id, User operator) throws NotFoundException, DataConflictException {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        user.freeze();
        if (userMapper.update(user) == 0) {
            throw new DataConflictException();
        }
    }

    @Override
    public void unfreeze(Long id, User operator) throws NotFoundException, DataConflictException {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        user.unfreeze();
        if (userMapper.update(user) == 0) {
            throw new DataConflictException();
        }
    }

    @Override
    public void disable(Long id, User operator) throws NotFoundException, DataConflictException {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        user.disable();
        if (userMapper.update(user) == 0) {
            throw new DataConflictException();
        }
        userRoleMapper.clear(id);
    }

    @Override
    @Transactional
    public void dispatchRoles(Long id, List<Long> roleIdList, Integer dispatchVersion, User operator)
            throws DataConflictException {
        RoleFilter roleFilter = new RoleFilter();
        roleFilter.setUserId(id);
        Query<RoleFilter> query = new Query<>();
        query.setFilter(roleFilter);
        List<Role> roles = roleMapper.list(query);
        List<Long> dispatchedRoleIdList = roles.stream().map(role -> role.getId()).collect(Collectors.toList());
        dispatchedRoleIdList.stream()
                .filter(storedId -> !roleIdList.contains(storedId))
                .forEach(deletingId -> userRoleMapper.delete(id, deletingId));
        roleIdList.stream()
                .filter(roleId -> !dispatchedRoleIdList.contains(roleId))
                .forEach(addingId -> userRoleMapper.add(id, addingId, operator.getId(), LocalDateTime.now()));
        if (userMapper.incDispatchRoleVersion(dispatchVersion) == 0) {
            throw new DataConflictException();
        }
    }

}