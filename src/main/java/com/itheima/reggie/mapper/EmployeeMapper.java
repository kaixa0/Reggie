package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/4 15:41
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
