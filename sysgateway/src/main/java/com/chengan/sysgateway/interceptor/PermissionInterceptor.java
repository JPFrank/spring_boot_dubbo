package com.chengan.sysgateway.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengan.syspermissionapi.common.ReqAttrKey;
import com.chengan.syspermissionapi.dto.UserAuthDTO;
import com.chengan.syspermissionapi.exception.AccessForbiddenException;
import com.chengan.syspermissionapi.vo.PermissionAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.pattern.PathPatternParser;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Resource
    private RedisTemplate<String, UserAuthDTO> redisTemplate;

    private PermissionAction getPermissionAction(String url, String httpMethod) {
        if (HttpMethod.GET.matches(httpMethod)) {
            return PermissionAction.READ;
        } else if (HttpMethod.PUT.matches(httpMethod)) {
            return PermissionAction.WRITE;
        } else if (HttpMethod.DELETE.matches(httpMethod)) {
            return PermissionAction.REMOVE;
        } else if (HttpMethod.POST.matches(httpMethod)) {
            String[] urlItems = url.split("/");
            if ("list".equals(urlItems[urlItems.length - 1])) {
                return PermissionAction.READ;
            } else {
                return PermissionAction.WRITE;
            }
        } else {
            return PermissionAction.UNKNOW;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object handle) throws Exception {
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        String path = request.getServletPath();
        UserAuthDTO userAuthDTO = (UserAuthDTO) request.getAttribute(ReqAttrKey.REQ_USER_AUTH_KEY);
        PathContainer pathContainer = PathContainer.parsePath(path);
        PathPatternParser ppp = new PathPatternParser();
        long matchCount = userAuthDTO.getPermissionList()
                .stream()
                .filter(
                        permission -> ppp.parse(permission.getResource()).matches(pathContainer) &&
                                getPermissionAction(path, request.getMethod()) == permission.getAction())
                .count();
        if (matchCount == 0) {
            throw new AccessForbiddenException();
        }
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