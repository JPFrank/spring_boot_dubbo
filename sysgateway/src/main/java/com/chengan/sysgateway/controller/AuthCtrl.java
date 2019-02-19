package com.chengan.sysgateway.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import com.chengan.syspermissionapi.DubboRoleService;
import com.chengan.syspermissionapi.DubboSysPermissionService;
import com.chengan.syspermissionapi.DubboUserService;
import com.chengan.syspermissionapi.common.ReplyBizStatus;
import com.chengan.syspermissionapi.common.ReqAttrKey;
import com.chengan.syspermissionapi.common.Reply;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.dto.*;
import com.chengan.syspermissionapi.exception.BadRequestException;
import com.chengan.syspermissionapi.exception.DuplicateRecordException;
import com.chengan.syspermissionapi.exception.InteralException;
import com.chengan.syspermissionapi.exception.NotFoundException;
import com.chengan.syspermissionapi.exception.UnauthorizedException;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;
import com.chengan.syspermissionapi.query.filter.RoleFilter;
import com.chengan.syspermissionapi.utils.JwtUtil;
import com.chengan.syspermissionapi.utils.SignatureUtil;
import com.chengan.syspermissionapi.vo.Gender;
import com.chengan.syspermissionapi.vo.UserStatus;
import com.google.common.base.Strings;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping()
public class AuthCtrl {
    private Logger logger = LoggerFactory.getLogger(AuthCtrl.class);

    @Resource
    private RedisTemplate<String, UserAuthDTO> redisTemplate;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private DubboUserService userService;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private DubboSysPermissionService permissionService;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private DubboRoleService roleService;

    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.prefix}")
    private String jwtHeaderPrefix;
    @Value("${jwt.sceret}")
    private String jwtSceret;
    @Value("${jwt.expired-time}")
    private Long jwtExpiredTime;
    @Value("${jwt.expired-week-time}")
    private Long jwtExpiredWeekTime;


    @ApiOperation(value="登录", notes="登录")
    @RequestMapping(value="/auth/signin", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<TokenRespDTO> login(
            @ApiParam @RequestBody final LoginDTO loginReq,
            HttpServletRequest request) throws BadRequestException, NotFoundException, InteralException {
        if (Strings.isNullOrEmpty(loginReq.getCode())) {
            throw new BadRequestException("Invalid login");
        }
        UserDTO userdto = userService.getByCode(loginReq.getCode());
        User u = userdto.toConvertEntity();
        String psw = u.getPassword();
        if (!BCrypt.checkpw(loginReq.getPassword(), psw)) {
            throw new BadRequestException("密码错误");
        }
        UserAuthDTO userAuth = new UserAuthDTO();
        // set userDTO
        userAuth.setUser(userdto);
        // TODO roleDTO list builder
        RoleFilter roleFilter = new RoleFilter();
        roleFilter.setUserId(u.getId());
        Query<RoleFilter> roleQuery = new Query<>();
        roleQuery.setFilter(roleFilter);
        List<RoleDTO> roledtos = roleService.list(roleQuery);
        userAuth.setRoleList(roledtos);
        // TODO permissionDTO list builder
        Set<PermissionDTO> permissionSet = new HashSet<>();
        for (RoleDTO roledto : roledtos) {
            PermissionFilter pFilter = new PermissionFilter();
            pFilter.setRoleId(roledto.toConvertEntity().getId());
            Query<PermissionFilter> permissionQuery = new Query<>();
            permissionQuery.setFilter(pFilter);
            List<PermissionDTO> permissiondtos = permissionService.list(permissionQuery);
//            permissionSet.addAll(permissiondtos);
            userAuth.setPermissionList(permissiondtos);
        }

        String sessionId = SignatureUtil.sha256(u.getId().toString());
        redisTemplate.opsForValue().set(sessionId, userAuth, jwtExpiredTime, TimeUnit.MILLISECONDS);
        String token = JwtUtil.createToken(sessionId, jwtSceret, jwtExpiredTime);
        String refreshToken = JwtUtil.getRefreshToken(sessionId, jwtSceret, jwtExpiredWeekTime);
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(refreshToken)) {
            throw new InteralException();
        }

        return new Reply<>(ReplyBizStatus.OK, "success", new TokenRespDTO(token, refreshToken, jwtExpiredTime/1000));
    }

    @ApiOperation(value="刷新Token", notes="刷新Token")
    @RequestMapping(value="/auth/token/refresh", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<TokenRespDTO> refreshtoken(
            @ApiParam @RequestParam("refreshToken") final String refreshToken) throws Exception {
        String newToken = JwtUtil.refreshToken(refreshToken, jwtSceret, jwtExpiredTime);
        if (Strings.isNullOrEmpty(newToken)) {
            throw new UnauthorizedException("token 过期，请重新登录");
        }
        String sessionId = JwtUtil.getSessionId(newToken, jwtSceret);
        redisTemplate.expire(sessionId, jwtExpiredTime, TimeUnit.MILLISECONDS);
        return new Reply<>(ReplyBizStatus.OK, "success", new TokenRespDTO(newToken, refreshToken, jwtExpiredTime/1000));
    }

    @ApiOperation(value="注册", notes="注册")
    @RequestMapping(value="/auth/signup", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<UserDTO> signup(
            @ApiParam @RequestBody final LoginDTO loginDTO)
            throws DuplicateRecordException, BadRequestException {
        String scretedPass = BCrypt.hashpw(loginDTO.getPassword(), BCrypt.gensalt());
        User u = new User();
        u.setName(loginDTO.getCode());
        u.setCode(loginDTO.getCode());
        u.setPassword(scretedPass);
        u.setGender(Gender.MALE);
        u.setStatus(UserStatus.AVAILABLE);
        System.out.println(u);
        UserDTO userdto = u.toConvertDTO();

        userService.create(userdto);
        userdto.setPassword("-.-");
        return new Reply<>(ReplyBizStatus.OK, "success", userdto);
    }

    @ApiOperation(value="登出", notes="登出")
    @RequestMapping(value="/api/v1/logout", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<?> logout(@RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth)
            throws BadRequestException {
        String sessionId = SignatureUtil.sha256(userAuth.getUser().getId().toString());
        redisTemplate.delete(sessionId);
        return new Reply<>(ReplyBizStatus.OK, "success");
    }
}