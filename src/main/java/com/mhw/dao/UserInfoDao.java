package com.mhw.dao;

import com.mhw.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper// 接口执行mybatis数据库操作
public interface UserInfoDao {

    @Select("SELECT * FROM userInfo WHERE userName=#{userName} AND userPwd=#{userPwd}")
    UserInfo login(UserInfo user);

    @Select("SELECT COUNT(*) FROM userInfo WHERE userName=#{userName}")
    int valName(String userName);

    @Select("SELECT COUNT(*) FROM userInfo WHERE userEmail=#{userEmail}")
    int valEmail(String userName);

    @Insert("INSERT INTO userInfo(userName,userPwd,userEmail) VALUE (#{userName},#{userPwd},#{userEmail})")
    int registered(UserInfo user);

    @Update("UPDATE userInfo SET userPwd = #{userPwd} WHERE userName = #{userName}")
    int updatePwd(UserInfo user);

    @Select("SELECT * FROM userInfo")
    List<UserInfo> select();
}
