package com.itheima.reggie.dto;

import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/15 16:21
 */

@Data
public class OrdersDto extends Orders {
    private List<OrderDetail> orderDetails;
}
