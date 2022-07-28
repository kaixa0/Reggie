package com.itheima.test;

import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: Li jinxiao
 * @Date: 2022/7/11 11:12
 */
public class UploadFileTest {

    @Test
    public void test1() {
        String fileName = "qeqeq.jpg";

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
