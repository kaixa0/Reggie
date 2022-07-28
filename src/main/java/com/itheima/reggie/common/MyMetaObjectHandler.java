package com.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description: 元数据对象处理器
 * @Author: Li jinxiao
 * @Date: 2022/7/8 9:32
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long threadLocal = BaseContext.getThreadLocal();
        metaObject.setValue("createUser", threadLocal);
        metaObject.setValue("updateUser", threadLocal);
    }

    /**
     * 更新操作，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());
        long id = Thread.currentThread().getId();
        log.info("updateFill id: {}", id);
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long threadLocal = BaseContext.getThreadLocal();
        metaObject.setValue("updateUser", threadLocal);
        System.out.println("threadLocal: "+threadLocal);

    }
}
