package com.chengan.sysgateway.controller;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chengan.syspermissionapi.DubboRoleService;
import com.chengan.syspermissionapi.common.ReplyBizStatus;
import com.chengan.syspermissionapi.common.Reply;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.dto.DispatchRolePermissionDTO;
import com.chengan.syspermissionapi.dto.RoleDTO;
import com.chengan.syspermissionapi.dto.UserAuthDTO;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.RoleFilter;
import com.chengan.syspermissionapi.common.ReqAttrKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleCtrl {
    private static Logger logger = LoggerFactory.getLogger(RoleCtrl.class);

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private DubboRoleService roleService;

    @ApiOperation(value="新建角色", notes="新建角色")
    @RequestMapping(value="", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<RoleDTO> createRole(
            @ApiParam @RequestBody RoleDTO roleDTO,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
//        User operator = userAuth.getUser().toConvertEntity();
//        logger.trace("roleDTO is \n {} \n", roleDTO.toString());
//        Role role = roleDTO.toConvertEntity();
//        logger.trace("role is \n {} \n", role.toString());
        Long id = roleService.create(roleDTO, userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success", roleDTO);
    }

    @ApiOperation(value="更新角色", notes="更新角色")
    @RequestMapping(value="/{roleId}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<?> updateRole(
            @ApiParam @PathVariable("roleId") Long roleId,
            @ApiParam @RequestBody RoleDTO roleDTO,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
//        User operator = userAuth.getUser().toConvertEntity();
//        logger.trace("roleId is {}, roleDTO is \n {} \n",roleId.toString() , roleDTO.toString());
//        Role role = roleDTO.toConvertEntity();
//        role.setId(roleId);
//        logger.trace("role is \n {} \n", role.toString());
        roleService.update(roleDTO, userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success");
    }

    @ApiOperation(value="删除角色", notes="删除角色")
    @RequestMapping(value="/{roleId}/version/{version}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<?> deleteRole(
            @ApiParam @PathVariable("roleId") Long roleId,
            @ApiParam @PathVariable("version") Integer version,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
//        User operator = userAuth.getUser().toConvertEntity();
//        logger.trace("roleId is {}\n", roleId.toString());
        roleService.delete(roleId, version, userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success");
    }

    @ApiOperation(value="按ID获取角色", notes="按ID获取角色")
    @RequestMapping(value="/{roleId}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<RoleDTO> getRole(
            @ApiParam @PathVariable("roleId") Long roleId) throws Exception {
        logger.trace("roleId is {} \n", roleId.toString());
        RoleDTO roledto = roleService.getById(roleId);
//        logger.trace("role is \n {} \n", role.toString());
//        RoleDTO roleDTO = role.toConvertDTO();
//        logger.trace("roleDTO is \n {} \n", roleDTO.toString());
        return new Reply<>(ReplyBizStatus.OK, "success", roledto);
    }

    @ApiOperation(value="查询角色列表", notes="查询角色列表")
    @RequestMapping(value="/query", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<List<RoleDTO>> listRole(
            @ApiParam @RequestBody() Query<RoleFilter> query) throws Exception {
        logger.trace("role query is {} \n", query.toString());
        List<RoleDTO> roledtos = roleService.list(query);
//        logger.trace("role list is [\n {} \n]", Joiner.on("\t\n").join(roles));
        return new Reply<>(ReplyBizStatus.OK, "success", roledtos);
    }

    @ApiOperation(value="分配权限", notes="分配权限")
    @RequestMapping(value="/{roleId}/permissions/dispatch", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<?> dispatchPermission(
            @ApiParam @PathVariable("roleId") Long roleId,
            @ApiParam @RequestBody DispatchRolePermissionDTO rolePermissionDTO,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)throws Exception {
        User operator = userAuth.getUser().toConvertEntity();
        roleService.dispatchPermission(
                roleId,
                rolePermissionDTO.getPermissionIdList(),
                rolePermissionDTO.getVersion(), userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success");
    }
}