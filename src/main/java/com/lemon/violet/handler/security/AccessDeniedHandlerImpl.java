package com.lemon.violet.handler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.violet.pojo.enums.CodeEnum;
import com.lemon.violet.pojo.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info("授权异常:{}",accessDeniedException.getMessage());
        //响应给前端
        ResponseResult result = ResponseResult.fail(CodeEnum.NO_OPERATOR_AUTH);

        response.setStatus(200);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
