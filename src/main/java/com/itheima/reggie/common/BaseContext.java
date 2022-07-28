package com.itheima.reggie.common;

/**
 * @Description: 基于ThreadLocal封装工具类，保存和获取当前用户ID
 * @Author: Li jinxiao
 * @Date: 2022/7/8 10:14
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setThreadLocal(Long id) {
        threadLocal.set(id);
    }

    public static Long getThreadLocal() {
        return threadLocal.get();
    }


}
