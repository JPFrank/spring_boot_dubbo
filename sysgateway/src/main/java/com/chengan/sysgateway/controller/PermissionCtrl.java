package com.chengan.sysgateway.controller;

import java.util.List;
import java.util.ArrayList;

import com.chengan.syspermissionapi.DubboSysPermissionService;
import com.chengan.syspermissionapi.common.ReplyBizStatus;
import com.chengan.syspermissionapi.common.ReqAttrKey;
import com.chengan.syspermissionapi.common.Reply;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.dto.PermissionDTO;
import com.chengan.syspermissionapi.dto.UserAuthDTO;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;
import com.alibaba.dubbo.config.annotation.Reference;
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
@RequestMapping("/api/v1/permissions")
public class PermissionCtrl {

    private static Logger logger = LoggerFactory.getLogger(PermissionCtrl.class);

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private DubboSysPermissionService permissionService;

    @ApiOperation(value="新建权限", notes="新建权限")
    @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<PermissionDTO> createPermission(
            @ApiParam @RequestBody PermissionDTO permissionDTO,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
//        User operator = userAuth.getUser().toConvertEntity();
//        logger.trace("permissionDTO is \n {} \n", permissionDTO.toString());
//        Permission permission = permissionDTO.toConvertEntity();
//        logger.trace("permission is \n {} \n", permission.toString());
        Long id = permissionService.create(permissionDTO, userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success", permissionDTO);
    }

    @ApiOperation(value="更新权限", notes="更新权限")
    @RequestMapping(value="/{permissionId}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<?> updatePermission(
            @ApiParam @PathVariable("permissionId") Long permissionId,
            @ApiParam @RequestBody PermissionDTO permissionDTO,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
        User operator = userAuth.getUser().toConvertEntity();
//        logger.trace("permissionId is {}, permissionDTO is \n {} \n", permissionId.toString(), permissionDTO.toString());
//        Permission permission = permissionDTO.toConvertEntity();
//        permission.setId(permissionId);
//        logger.trace("permission is \n {} \n", permission.toString());
        permissionService.update(permissionDTO, userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success");
    }

    @ApiOperation(value="删除权限", notes="删除权限")
    @RequestMapping(value="/{permissionId}/version/{version}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<?> deletePermission(
            @ApiParam @PathVariable("permissionId") Long permissionId,
            @ApiParam @PathVariable("version") Integer version,
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
        User operator = userAuth.getUser().toConvertEntity();
        logger.trace("permissionId is {}\n", permissionId.toString());
        permissionService.delete(permissionId, version, userAuth.getUser());
        return new Reply<>(ReplyBizStatus.OK, "success");
    }

    @ApiOperation(value="按ID获取权限", notes="按ID获取权限")
    @RequestMapping(value="/{permissionId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<PermissionDTO> getPermissionById(
            @ApiParam @PathVariable("permissionId") Long permissionId) throws Exception {
        logger.trace("permissionId is {} \n", permissionId.toString());
        PermissionDTO permissionDTO = permissionService.getById(permissionId);
//        logger.trace("permission is \n {} \n", permission.toString());
//        PermissionDTO permissionDTO = permission.toConvertDTO();
        logger.trace("permissionDTO is \n {} \n", permissionDTO.toString());
        return new Reply<>(ReplyBizStatus.OK, "success", permissionDTO);
    }

    @ApiOperation(value="按code获取权限", notes="按code获取权限")
    @RequestMapping(value="/code/{permissionCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<PermissionDTO> getPermissionByCode(
            @ApiParam @PathVariable("permissionCode") String permissionCode) throws Exception {
        logger.trace("permissionCode is {} \n", permissionCode.toString());
        PermissionDTO permissionDTO = permissionService.getByCode(permissionCode);
//        logger.trace("permission is \n {} \n", permission.toString());
//        PermissionDTO permissionDTO = permission.toConvertDTO();
        logger.trace("permissionDTO is \n {} \n", permissionDTO.toString());
        return new Reply<>(ReplyBizStatus.OK, "success", permissionDTO);
    }

    @ApiOperation(value="查询权限列表", notes="查询权限列表")
    @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<List<PermissionDTO>> listPermission(
            @ApiParam @RequestBody Query<PermissionFilter> query) throws Exception {
        logger.trace("permission query is {} \n", query.toString());
        List<PermissionDTO> permissiondtos = permissionService.list(query);
//        logger.trace("permission list is [\n {} \n]", Joiner.on("\t\n").join(permissions));
        return new Reply<>(ReplyBizStatus.OK, "success", permissiondtos);
    }

    @ApiOperation(value="权限列表", notes="权限列表")
    @RequestMapping(value="/querybyuser/list", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<List<PermissionDTO>> permissionList(
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {

        List<PermissionDTO> permissionDTOList = userAuth.getPermissionList();
        List<PermissionDTO> tmpPermissionAllList = new ArrayList<>();
        if (permissionDTOList != null){
            tmpPermissionAllList.addAll(permissionDTOList);
        }
        return new Reply<>(ReplyBizStatus.OK, "success", tmpPermissionAllList);
    }
}