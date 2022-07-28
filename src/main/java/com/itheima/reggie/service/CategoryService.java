package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/8 11:02
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
