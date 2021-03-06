package com.chengan.syspermissionservice.service;

import java.util.List;

import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.UserFilter;

public interface UserService {
    Long create(User user) throws DuplicateRecordException;
    Long create(User user, User operator) throws DuplicateRecordException;
    void update(User user) throws DuplicateRecordException, NotFoundException, DataConflictException;
    void update(User user, User operator) throws DuplicateRecordException, NotFoundException, DataConflictException;
    User getById(Long id) throws NotFoundException;
    User getByCode(String Code) throws NotFoundException;
    List<User> list(Query<UserFilter> query);
    void freeze(Long id, User Operator) throws NotFoundException, DataConflictException;
    void unfreeze(Long id, User Operator) throws NotFoundException, DataConflictException;
    void disable(Long id, User Operator) throws NotFoundException, DataConflictException;
    void dispatchRoles(Long id, List<Long> roleIdList, Integer dispatchVersion, User Operator)
            throws DataConflictException;
}