package com.chengan.syspermissionservice.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.chengan.syspermissionapi.DubboRoleService;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.domain.Role;
import com.chengan.syspermissionapi.dto.RoleDTO;
import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.filter.RoleFilter;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionservice.service.RoleService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
public class DubboRoleServiceImpl implements DubboRoleService {

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public Long create(RoleDTO roledto, UserDTO operator) throws DuplicateRecordException {
        Role role = roledto.toConvertEntity();
        User user = operator.toConvertEntity();
        return roleService.create(role, user);
    }

    @Override
    @Transactional
    public void update(RoleDTO roledto, UserDTO operator) throws DuplicateRecordException, NotFoundException, DataConflictException {
        Role role = roledto.toConvertEntity();
        User user = operator.toConvertEntity();
        roleService.update(role, user);
    }

    @Override
    @Transactional
    public void delete(Long id, Integer version, UserDTO operator) throws NotFoundException, DataConflictException {
        User user = operator.toConvertEntity();
        roleService.delete(id, version, user);
    }

    @Override
    public RoleDTO getById(Long id) throws NotFoundException{
        Role role = roleService.getById(id);
        return role.toConvertDTO();
    }

    @Override
    public List<RoleDTO> list(Query<RoleFilter> query){
        List<Role> roleList = roleService.list(query);
        return roleList.stream().map(ep -> ep.toConvertDTO()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void dispatchPermission(Long id, List<Long> permissionIdList, Integer dispatchVersion, UserDTO operator) throws DataConflictException {
        User user = operator.toConvertEntity();
        roleService.dispatchPermission(id, permissionIdList, dispatchVersion, user);
    }
}