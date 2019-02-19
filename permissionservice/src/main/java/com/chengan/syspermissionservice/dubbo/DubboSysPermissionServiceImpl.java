package com.chengan.syspermissionservice.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.chengan.syspermissionapi.DubboSysPermissionService;
import com.chengan.syspermissionapi.domain.Permission;
import com.chengan.syspermissionapi.dto.PermissionDTO;
import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.exception.DataConflictException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionservice.service.SysPermissionService;
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
public class DubboSysPermissionServiceImpl implements DubboSysPermissionService {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    @Transactional
    public Long create(PermissionDTO permissiondto, UserDTO operator) throws DuplicateRecordException {
       return sysPermissionService.create(permissiondto.toConvertEntity(), operator.toConvertEntity());
    }

    @Override
    @Transactional
    public void update(PermissionDTO permissiondto, UserDTO operator) throws DataConflictException, DuplicateRecordException, NotFoundException {
        sysPermissionService.update(permissiondto.toConvertEntity(), operator.toConvertEntity());
    }

    @Override
    @Transactional
    public void delete(Long id, Integer version, UserDTO operator) throws NotFoundException, DataConflictException {
        sysPermissionService.delete(id, version, operator.toConvertEntity());
    }

    @Override
    public PermissionDTO getById(Long id) throws NotFoundException {
        Permission permission = sysPermissionService.getById(id);
        return permission.toConvertDTO();
    }

    @Override
    public PermissionDTO getByCode(String code) throws NotFoundException {
        Permission permission = sysPermissionService.getByCode(code);
        return permission.toConvertDTO();
    }

    @Override
    public List<PermissionDTO> list(Query<PermissionFilter> query){
        List<Permission> permissionsList = sysPermissionService.list(query);
        return permissionsList.stream().map(ep -> ep.toConvertDTO()).collect(Collectors.toList());
    }

}