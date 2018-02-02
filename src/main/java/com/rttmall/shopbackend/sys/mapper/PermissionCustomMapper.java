package com.rttmall.shopbackend.sys.mapper;

import com.rttmall.shopbackend.sys.pojo.PermissionCustom;
import com.rttmall.shopbackend.sys.pojo.UserCustom;

import java.util.List;

public interface PermissionCustomMapper {
    //查询权限
    public List<PermissionCustom> queryPermission(PermissionCustom permissionCustom);

    //查询总数
    public int queryPermissionCount(PermissionCustom permissionCustom);

    //判断是否有角色绑定该权限
    public int isExistRolePermission(List permissionIdList);

    //批量删除权限
    public int batchDeletePermission(List permissionIdList);

    //查询所有权限
    public List<PermissionCustom> selectAllPermission();

    public int isExistPermission(String requestPath);

    public int isHavingPermission(UserCustom userCustom);

    public String selectPermissionByUrl(String requestPath);
}