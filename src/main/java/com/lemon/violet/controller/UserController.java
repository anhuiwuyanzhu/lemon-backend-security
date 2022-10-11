package com.lemon.violet.controller;


import com.lemon.violet.pojo.entry.SysUser;
import com.lemon.violet.pojo.vo.ResponseResult;
import com.lemon.violet.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody SysUser sysUser){
        return sysUserService.login(sysUser);
    }
}
