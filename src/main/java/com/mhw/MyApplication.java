package com.mhw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @Description 大三寒假实习第一天创建和启动服务器
 * @Author MR
 * @Date 2021/1/18 14:41
 */

@SpringBootApplication
// 服务器启动类
public class MyApplication {
    // 启动方法
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
