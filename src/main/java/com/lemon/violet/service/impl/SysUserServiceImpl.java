package com.lemon.violet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.violet.config.RedisCache;
import com.lemon.violet.constant.KeyConstant;
import com.lemon.violet.dao.SysUserMapper;
import com.lemon.violet.pojo.entry.SysUser;
import com.lemon.violet.pojo.vo.LoginUser;
import com.lemon.violet.pojo.vo.ResponseResult;
import com.lemon.violet.service.SysUserService;
import com.lemon.violet.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;


    @Override
    public ResponseResult login(SysUser user) {
        //认证: 1.usernamePasswordAuthenticationFilter调用AuthenticationManager的authentication方法获取Authentication对象
        //     2.过滤链执行最后调用userDetailService的loadUserByUserName方法,封装userDetail对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(ObjectUtils.isEmpty(authenticate)){
            System.err.println("错误");
            throw new RuntimeException("用户名或密码错误！");
        }

        //数据处理:1、存储用户id->user 到redis中。2、返回jwt,荷载信息为userId
        LoginUser principal = (LoginUser)authenticate.getPrincipal();
        Long userId = principal.getSysUser().getId();
        redisCache.setCacheObject(KeyConstant.LOGIN_KEY_PREFIX+userId,principal);
        String jwt = JwtUtil.createJwt(UUID.randomUUID().toString(), String.valueOf(userId), 60*60*24);

        return ResponseResult.success(jwt);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getSysUser().getId();
        redisCache.deleteObject(KeyConstant.LOGIN_KEY_PREFIX+userid);
        return ResponseResult.customize(200,"退出成功",null);
    }
}
