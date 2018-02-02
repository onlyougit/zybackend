package com.rttmall.shopbackend.sys.mapper;

import com.rttmall.shopbackend.sys.pojo.PermissionCustom;
import com.rttmall.shopbackend.sys.pojo.Role;
import com.rttmall.shopbackend.sys.pojo.RoleCustom;

import java.util.List;

public interface RoleCustomMapper {

    //查询所有角色
    public List<Role> queryAllRole();
    //查询角色列表
    public List<RoleCustom> queryRole(RoleCustom roleCustom);
    //查询用户总数
    public int queryRoleCount();
    //查看角色名称是否存在
    public int isExistRoleName(String roleName);
    //查看更新角色名称是否存在
    public int isExistUpdateRoleName(RoleCustom roleCustom);
    //批量删除角色
    public int batchDeleteRole(List roleIdList);
    //查看是否有用户在使用该角色
    public int isExistUserRole(List roleIdList);
    //删除角色权限表数据
    public int batchDeleteRolePermission(List roleIdList);
    //授权
    public List<PermissionCustom> selectRolePermissionByRoleId(int roleId);
    //授权提交
    public void insertRolePermission(List<RoleCustom> roleCustomList);
    //删除角色权限
    public void deleteRolePermission(Integer roleId);

}