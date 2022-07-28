package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description: 文件的上传和下载
 * @Author: Li jinxiao
 * @Date: 2022/7/11 10:47
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info("file: " + file.toString());

        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("suffix" + suffix);

        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);

        if (!dir.exists()) {
            dir.mkdirs();
        }


        try {
            // 临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len = 0;
            byte [] bytes = new byte[1024];
            while ( (len = inputStream.read(bytes)) != -1 ) {
                outputStream.write(bytes, 0 ,len);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
