package com.chengan.sysgateway.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chengan.syspermissionapi.DubboUserService;
import com.chengan.syspermissionapi.common.ReplyBizStatus;
import com.chengan.syspermissionapi.common.Reply;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.dto.DispatchUserRoleDTO;
import com.chengan.syspermissionapi.dto.UserAuthDTO;
import com.chengan.syspermissionapi.dto.UserDTO;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.UserFilter;

import java.util.List;

import javax.websocket.server.PathParam;

import com.chengan.syspermissionapi.common.ReqAttrKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/users")
public class UserCtrl {
  private static Logger logger = LoggerFactory.getLogger(UserCtrl.class);

  @Reference(version = "${demo.service.version}",
          application = "${dubbo.application.id}")
  private DubboUserService userService;

  @ApiOperation(value="获取用户自己的信息", notes="获取用户自己的信息")
  @RequestMapping(value="/me", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> getUser(
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
          throws Exception {
    return new Reply<UserDTO>(ReplyBizStatus.OK, "success", userAuth.getUser());
  }

  @ApiOperation(value = "创建用户", notes = "创建用户")
  @RequestMapping(value="", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> createUser(
          @ApiParam @RequestBody UserDTO userDTO,
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    Long id = userService.create(userDTO, userAuth.getUser());
    return new Reply<>(ReplyBizStatus.OK, "success", userDTO);
  }

  @ApiOperation(value="修改用户信息", notes="修改用户信息")
  @RequestMapping(value="/{userId}/update", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> updateUser(
          @ApiParam @PathParam("userId") Long userId,
          @ApiParam @RequestBody UserDTO userDTO,
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    logger.trace("userAuthDTO is \n {} \n", userAuth.getUser().toString());
    userDTO.setId(userId);
    logger.trace("operatory is \n {} \n", userDTO.toString());
    userService.update(userDTO, userAuth.getUser());
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="用户ID查询用户详情", notes="用户ID查询用户详情")
  @RequestMapping(value="/{userId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> getUserById(@ApiParam @PathParam("userId") Long userId) throws Exception {
    UserDTO userdto = userService.getById(userId);
    return new Reply<UserDTO>(ReplyBizStatus.OK, "success", userdto);
  }

  @ApiOperation(value="用户编码查询用户详情", notes="用户编码查询用户详情")
  @RequestMapping(value="/code/{userCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<UserDTO> getUserByCode(@ApiParam @PathParam("userCode") String userCode) throws Exception {
    UserDTO userdto = userService.getByCode(userCode);
    return new Reply<UserDTO>(ReplyBizStatus.OK, "success", userdto);
  }

  @ApiOperation(value="查询用户列表", notes="查询用户列表")
  @RequestMapping(value="/query", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<List<UserDTO>> listUser(@ApiParam @RequestBody Query<UserFilter> query) throws Exception {
    List<UserDTO> userdtos = userService.listuser(query);
    return new Reply<List<UserDTO>>(ReplyBizStatus.OK, "success", userdtos);
  }


  @ApiOperation(value="冻结用户", notes="冻结用户")
  @RequestMapping(value="/{userId}/freeze", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> freezeUser(
          @ApiParam @PathParam("userId") Long userId,
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    userService.freeze(userId, userAuth.getUser());
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="解冻用户", notes="解冻用户")
  @RequestMapping(value="/{userId}/unfreeze", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<String> unfreezeUser(
          @ApiParam @PathParam("userId") Long userId,
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    userService.unfreeze(userId, userAuth.getUser());
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="禁用用户", notes="禁用用户")
  @RequestMapping(value="/{userId}/disable", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> udisableUser(
          @ApiParam @PathParam("userId") Long userId,
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
          throws Exception {
    userService.disable(userId, userAuth.getUser());
    return new Reply<>(ReplyBizStatus.OK, "success");
  }

  @ApiOperation(value="分配角色", notes="分配角色")
  @RequestMapping(value="/{userId}/roles/dispatch", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
  public Reply<?> dispatchRoles(
          @ApiParam @PathParam("userId") Long userId,
          @ApiParam @RequestBody DispatchUserRoleDTO userRoleDTO,
          @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {
    userService.dispatchRoles(userId, userRoleDTO.getRoleIdList(), userRoleDTO.getVersion(), userAuth.getUser());
    return new Reply<>(ReplyBizStatus.OK, "success");
  }
}