package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/10 23:52
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐和相关的菜品
     * @param ids
     */
    public void moveWithDish(Long ...ids);


    public void startSelling(Long ...ids);

    public void stopSelling(List<Long> ids);


    /**
     * 根据套餐id修改售卖状态
     * @param status
     * @param ids
     */
    void updateSetmealStatusById(Integer status,List<Long> ids);

    public SetmealDto get(Long id);
}
