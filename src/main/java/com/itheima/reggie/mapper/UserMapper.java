package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/14 10:46
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
