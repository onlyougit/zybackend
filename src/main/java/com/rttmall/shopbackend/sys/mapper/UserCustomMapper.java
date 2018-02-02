package com.rttmall.shopbackend.sys.mapper;

import com.rttmall.shopbackend.sys.pojo.Role;
import com.rttmall.shopbackend.sys.pojo.RoleCustom;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.sys.pojo.UserCustom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserCustomMapper {

    //登录校验【根据用户和密码】
    public List<UserCustom> loginCheck(UserCustom userCustom);
    //更新用户表登录信息
    public void updateLoginInfo(User user);
    //查询用户列表
    public List<UserCustom> queryUser(UserCustom userCustom);
    //查询用户总数
    public int queryUserCount(UserCustom userCustom);
    //添加用户角色表
    public int insertUserRole(List<RoleCustom> roleCustomList);
    //删除用户角色
    public int deleteUserRole(Integer userId);
    //判断用户是否存在
    public int isExistUser(String userName);
    //批量更新用户状态
    public int batchUpdateUser(List userIdList);
    //批量删除用户角色
    public int batchDeleteUserRole(List userIdList);
    //根据user_id查询用户角色
    public List<Role> selectUserRoleByUserId(Integer userId);
    //更新判断用户是否存在
    public int isExistUpdateUser(UserCustom userCustom);
	public String queryMaxUserNameByRole(@Param("shortName")String string, @Param("roleId")Integer code);

}