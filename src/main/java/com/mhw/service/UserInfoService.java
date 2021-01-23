package com.mhw.service;

import com.mhw.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserInfoService {
    String login(UserInfo user);
    String registered(UserInfo user);
    String resetPwd(UserInfo user);
    HashMap<String, Object> sendCode(String email, HttpServletRequest request);
    HashMap<String,Object> select(UserInfo user);
}
