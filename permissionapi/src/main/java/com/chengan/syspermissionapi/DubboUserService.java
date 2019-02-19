package com.chengan.syspermissionapi;

import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.UserFilter;

import java.util.List;

public interface DubboUserService {
    Long create(UserDTO user) throws DuplicateRecordException;
    Long create(UserDTO user, UserDTO operator) throws DuplicateRecordException;
    void update(UserDTO user) throws DataConflictException, DuplicateRecordException, NotFoundException;
    void update(UserDTO user, UserDTO operator) throws DataConflictException, DuplicateRecordException, NotFoundException;
    UserDTO getById(Long id) throws NotFoundException;
    UserDTO getByCode(String Code) throws NotFoundException;
    List<UserDTO> listuser(Query<UserFilter> query);
    void freeze(Long id, UserDTO Operator) throws NotFoundException, DataConflictException;
    void unfreeze(Long id, UserDTO Operator) throws NotFoundException, DataConflictException;
    void disable(Long id, UserDTO Operator) throws NotFoundException, DataConflictException;
    void dispatchRoles(Long id, List<Long> roleIdList, Integer dispatchVersion, UserDTO Operator) throws DataConflictException;
}