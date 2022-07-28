package com.itheima.reggie.common;

/**
 * @Description: 自定义业务异常
 * @Author: Li jinxiao
 * @Date: 2022/7/11 9:13
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
