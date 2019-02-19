package com.chengan.sysgateway.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengan.syspermissionapi.dto.UserAuthDTO;
import com.chengan.syspermissionapi.exception.UnauthorizedException;
import com.chengan.syspermissionapi.common.ReqAttrKey;
import com.chengan.syspermissionapi.utils.JwtUtil;
import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class JwtInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    @Resource
    private RedisTemplate<String, UserAuthDTO> redisTemplate;
    @Value("${jwt.header.token.key}")
    private String JWT_TOKEN_KEY;
    @Value("${jwt.prefix}")
    private String JWT_HEADER_PREFIX;
    @Value("${jwt.sceret}")
    private String JWT_SECRET;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object handle) throws Exception {
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        logger.debug("拦截器1执行-----preHandle");
        String authHeader = request.getHeader(JWT_TOKEN_KEY);
        System.out.println(authHeader);
        if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(JWT_HEADER_PREFIX)) {
            throw new UnauthorizedException();
        }
        String token = authHeader.substring(JWT_HEADER_PREFIX.length() + 1);
        if (!JwtUtil.validateToken(token, JWT_SECRET)) {
            throw new UnauthorizedException("token 过期");
        }
        String sessionId = JwtUtil.getSessionId(token, JWT_SECRET);
        System.out.println(redisTemplate.opsForValue().get(sessionId));
        UserAuthDTO userAuth = (UserAuthDTO) redisTemplate.opsForValue().get(sessionId);
        System.out.println(userAuth);

        if (userAuth == null) {
            redisTemplate.delete(sessionId);
            throw new UnauthorizedException("token 过期");
        }
        request.setAttribute(ReqAttrKey.REQ_USER_AUTH_KEY, userAuth);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.debug("拦截器1执行-----postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.debug("拦截器1执行-----afterCompletion");
    }

}