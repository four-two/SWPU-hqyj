package com.mhw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhw.dao.UserInfoDao;
import com.mhw.pojo.UserInfo;
import com.mhw.util.MdFive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @version 1.0
 * @Description UserInfoService实现类
 * @Author MR
 * @Date 2021/1/19 10:58
 */
// UserInfoServiceImpL对象创建交给spring处理
@Service
public class UserInfoServiceImpL implements UserInfoService {
    // 创建一个UserInfo对象
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    MdFive mdFive;
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String sendEmail;

    @Override
    public String login(UserInfo user) {
        user.setUserPwd(mdFive.encrypt(user.getUserPwd(), user.getUserName()));
        UserInfo userInfo = userInfoDao.login(user);
        if (userInfo != null) {
            return "登录成功";
        }
        return "登录失败";
    }

    @Override
    public String registered(UserInfo user) {
        user.setUserPwd(mdFive.encrypt(user.getUserPwd(), user.getUserName()));
        int valName = userInfoDao.valName(user.getUserName());
        int valEmail = userInfoDao.valEmail(user.getUserEmail());
        if (valName != 0) {
            return "注册失败,用户名重复";
        } else if (valEmail != 0) {
            return "注册失败,邮箱已注册";
        } else {
            int userRegist = userInfoDao.registered(user);
            if (userRegist != 0) {
                return "注册成功";
            } else {
                return "注册失败";
            }
        }
    }

    @Override
    public String resetPwd(UserInfo user) {
        user.setUserPwd(mdFive.encrypt(user.getUserPwd(), user.getUserName()));
        int valPwd = userInfoDao.updatePwd(user);
        if (valPwd == 0) {
            return "修改失败";
        }
        return "修改成功";
    }

    @Override
    public HashMap<String, Object> sendCode(String email, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            // 从session中获取当前用户信息
            HttpSession session = request.getSession();
            // 创建普通邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            // 设置发件人邮箱
            message.setFrom(sendEmail);
            // 设置收件人邮箱
            message.setTo(email);
            // 设置邮件主题
            message.setSubject("验证码");
            // 生成随机验证码
            Random rd = new Random();
            String valCode = rd.nextInt(9999) + "";
            // 设置邮件正文
            message.setText("你的验证码是:" + valCode);
            // 发送邮件
            javaMailSender.send(message);
            // 发送成功
            // 把验证码存入session中
            session.setAttribute("valCode", valCode);
            map.put("info", "验证码发送成功");
            return map;

        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！");
            e.printStackTrace();
        }
        map.put("info", "发送邮件异常");
        return map;
    }

    @Override
    public HashMap<String, Object> select(UserInfo user) {
        HashMap<String, Object> map =  new HashMap<String, Object>();
        //1 设置分页参数
        PageHelper.startPage(user.getPage(),user.getRow());
        //2 查询数据库表数据
        List<UserInfo> list = userInfoDao.select();
        //3.把查询的数据转换成分页对象
        PageInfo<UserInfo> page = new PageInfo<UserInfo>(list);

        //获取分页的当前页的集合
        map.put("list",page.getList());
        //总条数
        map.put("total",page.getTotal());
        //总页数
        map.put("totalPage",page.getPages());
        //上一页
        if(page.getPrePage()==0){
            map.put("pre",1);
        }else{
            map.put("pre",page.getPrePage());
        }

        //下一页
        //保持在最后一页
        if(page.getNextPage()==0){
            map.put("next",page.getPages());
        }else{
            map.put("next",page.getNextPage());
        }
        //当前页
        map.put("cur",page.getPageNum());

        return map;
    }
}
