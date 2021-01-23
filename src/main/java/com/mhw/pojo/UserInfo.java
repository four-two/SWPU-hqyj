package com.mhw.pojo;

/**
 * @version 1.0
 * @Description loginfrom对应
 * @Author MR
 * @Date 2021/1/19 10:36
 */
public class UserInfo extends MyPage {
    private Integer userId;
    private String userName;
    private String userPwd;
    private String userEmail;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
