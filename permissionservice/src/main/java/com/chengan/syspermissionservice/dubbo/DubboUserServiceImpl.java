package com.chengan.syspermissionservice.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.chengan.syspermissionapi.DubboUserService;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.filter.UserFilter;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;

import com.chengan.syspermissionservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
@Slf4j
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Long create(UserDTO userDTO) throws DuplicateRecordException {
        User user = userDTO.toConvertEntity();
        return userService.create(user);
    }

    @Override
    @Transactional
    public Long create(UserDTO userDTO, UserDTO operator) throws DuplicateRecordException {
        User user = userDTO.toConvertEntity();
        return userService.create(user, operator.toConvertEntity());
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) throws DataConflictException, DuplicateRecordException, NotFoundException {
        userService.update(userDTO.toConvertEntity());
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO, UserDTO operator) throws DataConflictException, DuplicateRecordException, NotFoundException {
        userService.update(userDTO.toConvertEntity(), operator.toConvertEntity());
    }

    @Override
    public UserDTO getById(Long id) throws NotFoundException {
        User user = userService.getById(id);
        return user.toConvertDTO();
    }

    @Override
    public UserDTO getByCode(String Code) throws NotFoundException {
        User user = userService.getByCode(Code);
        return user.toConvertDTO();
    }

    @Override
    public List<UserDTO> listuser(Query<UserFilter> query){
        List<UserDTO> userdtoList = new ArrayList<>();
        List<User> UserList = userService.list(query);
        return UserList.stream().map(ep -> ep.toConvertDTO()).collect(Collectors.toList());
    }

    @Override
    public void freeze(Long id, UserDTO Operator) throws NotFoundException, DataConflictException {
        userService.freeze(id, Operator.toConvertEntity());
    }

    @Override
    public void unfreeze(Long id, UserDTO Operator) throws NotFoundException, DataConflictException {
        userService.unfreeze(id, Operator.toConvertEntity());
    }

    @Override
    public void disable(Long id, UserDTO Operator) throws NotFoundException, DataConflictException {
        userService.unfreeze(id, Operator.toConvertEntity());
    }

    @Override
    public void dispatchRoles(Long id, List<Long> roleIdList, Integer dispatchVersion, UserDTO Operator) throws DataConflictException {
        userService.dispatchRoles(id, roleIdList, dispatchVersion, Operator.toConvertEntity());
    }

}