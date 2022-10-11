package com.lemon.violet.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.violet.pojo.entry.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 14:13:58
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


}

