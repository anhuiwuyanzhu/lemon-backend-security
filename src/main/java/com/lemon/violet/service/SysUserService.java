package com.lemon.violet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.violet.pojo.entry.SysUser;
import com.lemon.violet.pojo.vo.ResponseResult;

public interface SysUserService extends IService<SysUser> {

    ResponseResult login(SysUser sysUser);
}
