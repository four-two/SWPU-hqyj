package com.mhw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Description 配置类：配置默认打开自定义的浏览器
 * @Author MR
 * @Date 2021/1/18 16:26
 */
@Configuration
public class MyRunner implements CommandLineRunner {

    // 对应配置文件的参数
    @Value("${myurl}")
    private String myurl;

    @Value("${googleexcute}")
    private String googleExcutePath;

    @Value("${openurl}")
    private boolean isOpen;

    @Value("${server.port}")
    private String port;

    @Override
    public void run(String... args) throws Exception {
        if(isOpen){
            String cmd = googleExcutePath +" http://"+ myurl+":"+port;
            Runtime run = Runtime.getRuntime();
            try{
                run.exec(cmd);
                System.out.println("启动浏览器程序成功");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

