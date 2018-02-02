package com.rttmall.shopbackend.sys.service;


import com.rttmall.shopbackend.sys.pojo.UserCustom;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/2/22.
 */
public interface UserService {

    //登录校验【根据用户和密码】
    public void loginCheck(UserCustom userCustom, HttpSession session) throws Exception;

    //密码修改
    public void changePassword(UserCustom userCustom) throws Exception;

    //查询用户列表
    public Map queryUser(UserCustom userCustom);

    //用户添加
    public void insertUser(UserCustom userCustom) throws Exception;

    //用户更新
    public void updateUser(UserCustom userCustom) throws Exception;

    //批量删除用户
    public void batchDeleteUser(List userIdList);

    //用户更新查询
    public UserCustom editQueryUser(int id);
}
