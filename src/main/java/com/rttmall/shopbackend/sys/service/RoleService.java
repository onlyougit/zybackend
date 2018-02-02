package com.rttmall.shopbackend.sys.service;


import com.rttmall.shopbackend.sys.pojo.Role;
import com.rttmall.shopbackend.sys.pojo.RoleCustom;

import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/2/22.
 */
public interface RoleService {

    //查询所有角色
    public List<Role> queryAllRole();
    //查询角色列表
    public Map queryRole(RoleCustom roleCustom);
    //添加角色
    public void insertRole(RoleCustom roleCustom)throws Exception;
    //角色更新查询
    public Role editQueryRole(Integer roleId);
    //更新角色
    public void updateRole(RoleCustom roleCustom) throws Exception;
    //批量删除角色
    public void batchDeleteRole(List roleIdList)throws Exception;
    //授权
    public List<RoleCustom> getMenuPermissionTree(String roleId) throws Exception;
    //授权提交
    public void insertRolePermission(Integer roleId,List<RoleCustom> roleCustomList)throws Exception;
}
