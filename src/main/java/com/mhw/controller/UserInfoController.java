package com.mhw.controller;

import com.mhw.pojo.UserInfo;
import com.mhw.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @version 1.0
 * @Description TODO
 * @Author MR
 * @Date 2021/1/20 11:41
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("/loginAjax")
    @ResponseBody// 返回数据并转换为json格式
    public HashMap<String, Object> loginAjax(UserInfo userInfo) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        String info = userInfoService.login(userInfo);
        hashMap.put("info", info);
        return hashMap;
    }

    @RequestMapping("/registeredAjax")
    @ResponseBody
    public HashMap<String, Object> registeredAjax(UserInfo userInfo, @RequestParam("userCode") String code, HttpServletRequest request) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        //获取session对象
        HttpSession session = request.getSession();
        //取出存到session中的验证码的值
        String valCode = session.getAttribute("valCode") + "";
        //判断用户输入的验证码和邮箱接收的验证码是否一致
        if (!code.equals(valCode)) {
            hashMap.put("info", "验证码输入错误");
        } else {
            String info = userInfoService.registered(userInfo);
            hashMap.put("info", info);
        }
        return hashMap;
    }

    //处理邮件发送的请求
    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String, Object> sendEmail(String email, HttpServletRequest request) {
        return userInfoService.sendCode(email, request);
    }

    @RequestMapping("/resetPwd")
    @ResponseBody
    public HashMap<String, Object> resetPwd(UserInfo userInfo, @RequestParam("userCode") String code, HttpServletRequest request) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        //获取session对象
        HttpSession session = request.getSession();
        //取出存到session中的验证码的值
        String valCode = session.getAttribute("valCode") + "";
        //判断用户输入的验证码和邮箱接收的验证码是否一致
        if (!code.equals(valCode)) {
            hashMap.put("info", "验证码输入错误");
        } else {
            String info = userInfoService.resetPwd(userInfo);
            hashMap.put("info", info);
        }
        return hashMap;
    }

    @RequestMapping("/myLogin")
    public String myLogin() {
        return "myLogin";
    }

    //访问user-list页面
    @RequestMapping("/list")
    public String list(UserInfo user, ModelMap m){
        //查询分页数据
        HashMap<String, Object> map=userInfoService.select(user);
        //把数据传到前端
        m.put("info",map);

        return "user/user-list";
    }
}
