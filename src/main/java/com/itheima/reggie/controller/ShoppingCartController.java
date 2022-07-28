package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/14 16:45
 */

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * 购物车添加
     * @param shoppingCart
     * @return
     */
    @RequestMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart" +shoppingCart);
        //获取使用者id
        Long userid = BaseContext.getThreadLocal();
        shoppingCart.setUserId(userid);
        //查询当前菜品是否在购物车中
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userid);
        //判断添加是菜品还是套餐
        if (shoppingCart.getDishId() != null) {
            //这是菜品
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            //这是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart serviceOne = shoppingCartService.getOne(queryWrapper);
        if (serviceOne != null) {
            serviceOne.setNumber(serviceOne.getNumber()+1);
            shoppingCartService.updateById(serviceOne);
            shoppingCart = serviceOne;
        } else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
        }
        return R.success(shoppingCart);
    }

    /**
     * 购物车减少
     * @param shoppingCart
     * @return
     */
    @RequestMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("shoppingCart" +shoppingCart);
        //获取使用者id
        Long userid = BaseContext.getThreadLocal();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userid);
        //防止出现负的数量
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart serviceOne = shoppingCartService.getOne(queryWrapper);
        if (serviceOne.getNumber() >= 1) {
            serviceOne.setNumber(serviceOne.getNumber()-1);
        } else {
            serviceOne.setNumber(0);
        }

        shoppingCartService.updateById(serviceOne);
        //如果购物车里面存在数量减为0的菜品或套餐，那么就把它从购物车删除
        queryWrapper.eq(ShoppingCart::getNumber, 0);
        shoppingCartService.remove(queryWrapper);
        return R.success(serviceOne);
    }


    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long threadLocal = BaseContext.getThreadLocal();
        queryWrapper.eq( ShoppingCart::getUserId, threadLocal);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }


    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        Long threadLocal = BaseContext.getThreadLocal();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, threadLocal);
        shoppingCartService.remove(queryWrapper);
        return R.success("清空成功");
    }
}
