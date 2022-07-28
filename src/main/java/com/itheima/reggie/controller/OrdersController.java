package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/15 9:10
 */

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    /**
     * 用户下单提交
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("提交成功");
    }


    /**
     * 订单明细
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    private R<Page> page(Integer page, Integer pageSize, String name, String beginTime, String endTime) {
        log.info("page"+page);
        log.info("pageSize"+pageSize);

        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Orders::getNumber, name)
                .le(StringUtils.isNotEmpty(endTime), Orders::getOrderTime, endTime)
                .ge(StringUtils.isNotEmpty(beginTime), Orders::getOrderTime, beginTime);

        Page<Orders> page1 = ordersService.page(ordersPage, wrapper);

        return R.success(page1);
    }

    /**
     * 用户查看自己订单
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(Integer page, Integer pageSize){
        return ordersService.userPage(page, pageSize);
    }


    /**
     * 再来一单
     * @param map
     * @return
     */
    @PostMapping("/again")
    public R<String> againSubmit(@RequestBody Map<String,String> map){
        return ordersService.againSubmit(map);
    }



    @PutMapping
    public R<String> orderStatusChange(@RequestBody Map<String,String> map){

        String id = map.get("id");
        Long orderId = Long.parseLong(id);
        Integer status = Integer.parseInt(map.get("status"));

        if(orderId == null || status==null){
            return R.error("传入信息不合法");
        }
        Orders orders = ordersService.getById(orderId);
        orders.setStatus(status);
        ordersService.updateById(orders);

        return R.success("订单状态修改成功");

    }

}
