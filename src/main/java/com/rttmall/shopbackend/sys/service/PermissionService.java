package com.rttmall.shopbackend.sys.service;

import com.rttmall.shopbackend.sys.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/4/6.
 */
public interface PermissionService {
    //查询菜单树
    public List<MenuCustom> getMenuTree();
    //查询权限
    public Map queryPermission(PermissionCustom permissionCustom);
    //添加权限
    public void insertPermission(Permission permission)throws Exception;
    //权限更新查询
    public Permission editQueryPermission(String permissionId);
    //权限更新
    public void updatePermission(Permission permission)throws Exception;
    //批量删除权限
    public void batchDeletePermission(List permissionIdList)throws Exception;
    //判断此URL是否需要权限验证
    public boolean isExistPermission(String requestPath);
    //判断是否有权限
    public boolean isHavingPermission(User user, String requestPath);
    //查询出权限名称
    public String selectPermissionByUrl(String requestPath);
}
