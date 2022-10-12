package com.lemon.violet.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.violet.pojo.enums.CodeEnum;
import com.lemon.violet.pojo.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){
            log.info("认证异常:{}",authException.getMessage());
            result = ResponseResult.fail(CodeEnum.LOGIN_ERROR);
        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.fail(CodeEnum.NO_OPERATOR_AUTH);
            log.info("授权异常:{}",authException.getMessage());
        }else{
            result = ResponseResult.fail(CodeEnum.SYSTEM_ERROR);
        }


        response.setStatus(200);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
