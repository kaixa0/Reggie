package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/10 23:49
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
