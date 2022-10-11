package com.lemon.violet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemon.violet.dao.SysUserMapper;
import com.lemon.violet.pojo.entry.SysUser;
import com.lemon.violet.pojo.vo.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //条件构造： 1.根据用户名username查询
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);

        //数据校验: 1.用户名对应的用户不能不存在
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if(ObjectUtils.isEmpty(sysUser)){
            throw new RuntimeException("没有此用户");
        }

        //数据整理: 1.封装UserDetail类返回
        return new LoginUser(sysUser);
    }
}
