package com.sola.contentcenter.filter;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.sola.usercenter.annotation.Authentication;
import com.sola.usercenter.annotation.AuthenticationRole;
import com.sola.usercenter.util.JwtOperator;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AuthenticationFilterConfig {

    @Autowired
    private JwtOperator jwtOperator;

    /**
     * 拦截Authentication注解 检查token是否合法
     *
     * @param point
     * @return
     */
    @Around("@annotation(com.sola.usercenter.annotation.Authentication)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        tokenHandle();
        return point.proceed();
    }

    /**
     * 检查token role权限是否匹配
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.sola.usercenter.annotation.AuthenticationRole)")
    public Object aroundRole(ProceedingJoinPoint point) throws Throwable {
        tokenHandle();

        // 获取token中role信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String role = request.getAttribute("role").toString();

        // 获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        AuthenticationRole annotation = method.getAnnotation(AuthenticationRole.class);
        String roleValue = annotation.value();

        // 检查role是否匹配
        if (!Objects.equals(role, roleValue)) {
            throw new SecurityException("权限不匹配无法访问");
        }

        return point.proceed();
    }

    /**
     * 处理token信息
     */
    public void tokenHandle() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();

            String token = request.getHeader("X-Token");
            // 检查token
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                // token不合法抛出异常
                throw new SecurityException("token不合法");
            }

            // 获取用户信息
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));
            request.setAttribute("wxNickName", claims.get("wxNickName"));
            request.setAttribute("role", claims.get("role"));
        } catch (Exception e) {
            throw new SecurityException("token不合法");
        }
    }

    // 弃用
    // @Before("execution(* com.sola.usercenter.controller..*.*(..))")
    public void before(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        if (params.length == 0) {
            return;
        }

        // 获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        /*Annotation[][] parameterAnnotations = method.getParameterAnnotations();*/

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Authentication annotation = method.getAnnotation(Authentication.class);
        log.info("鉴权注解值 : {}", JSONObject.toJSONString(annotation));
        if (annotation != null) {
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);

                log.info("头信息 {} {}", headerName, headerValue);
                if ("".equals(headerName)) {

                }
            }
        } else {

        }

        try {
            // point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
