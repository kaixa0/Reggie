package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Orders;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/15 9:04
 */


public interface OrdersService extends IService<Orders> {

    /**
     * 用户下单提交
     * @param orders
     */
    public void submit(Orders orders);


    public R<Page> userPage(Integer page, Integer pageSize);

    public R<String> againSubmit(Map<String,String> map);
}
