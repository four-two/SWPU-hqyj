package com.mhw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @Description TODO
 * @Author MR
 * @Date 2021/1/20 16:20
 */
@Controller
public class indexController {
    //访问 index页面
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
