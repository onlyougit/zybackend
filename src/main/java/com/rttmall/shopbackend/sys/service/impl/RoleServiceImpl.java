package com.rttmall.shopbackend.sys.service.impl;

import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.sys.mapper.*;
import com.rttmall.shopbackend.sys.pojo.*;
import com.rttmall.shopbackend.sys.service.RoleService;
import com.rttmall.shopbackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/2/22.
 */
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleCustomMapper roleCustomMapper;
    @Autowired
    private MenuCustomMapper menuCustomlMapper;
    @Autowired
    private PermissionCustomMapper permissionCustomMapper;


    @Override
    public List<Role> queryAllRole() {

        List<Role> roleList = roleCustomMapper.queryAllRole();
        return roleList;
    }

    @Override
    public Map queryRole(RoleCustom roleCustom) {
        Map map = new HashMap();
        List<RoleCustom> roleCustomList = roleCustomMapper.queryRole(roleCustom);
        int sum = roleCustomMapper.queryRoleCount();
        map.put("data", roleCustomList);
        map.put("total", sum);
        return map;
    }

    @Override
    public void insertRole(RoleCustom roleCustom) throws Exception {
        if (null != roleCustom) {
            int count = roleCustomMapper.isExistRoleName(roleCustom.getRoleName());
            if (count != 0) {
                throw new ServiceException(Constants.ROLENAME_EXIST);
            }
            roleMapper.insertSelective((Role) roleCustom);
        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @Override
    public Role editQueryRole(Integer roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        return role;
    }

    @Override
    public void updateRole(RoleCustom roleCustom) throws Exception {
        if (null != roleCustom) {
            int count = roleCustomMapper.isExistUpdateRoleName(roleCustom);
            if (count != 0) {
                throw new ServiceException(Constants.ROLENAME_EXIST);
            }
            roleMapper.updateByPrimaryKeySelective((Role) roleCustom);
        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @Override
    public void batchDeleteRole(List roleIdList) throws Exception {
        //判断角色是否在用户角色表
        int count = roleCustomMapper.isExistUserRole(roleIdList);
        if (count > 0) {
            throw new ServiceException(Constants.USING_THIS_ROLE);
        }
        //删除角色权限表数据
        roleCustomMapper.batchDeleteRolePermission(roleIdList);
        //删除角色
        roleCustomMapper.batchDeleteRole(roleIdList);
    }

    @Override
    public List<RoleCustom> getMenuPermissionTree(String roleId)throws Exception {
        if(!"".equals(roleId) && null != roleId){
            //先查询菜单表
            List<MenuCustom> menuList = menuCustomlMapper.selectAllMenu();
            //在查询权限表
            List<PermissionCustom> permissionCustomList = permissionCustomMapper.selectAllPermission();
            //最后查询角色权限表，显示该角色已有的权限
            List<PermissionCustom> list = roleCustomMapper.selectRolePermissionByRoleId(Integer.parseInt(roleId));
            List<RoleCustom> roleCustomList = new ArrayList<>();
            menuList.forEach(s->{
                RoleCustom roleCustom = new RoleCustom();
                roleCustom.setId(s.getMenuId());
                roleCustom.setText(s.getMenuName());
                roleCustom.setPid(s.getMenuParentId());
                roleCustom.setAsyncLoad(false);
                roleCustomList.add(roleCustom);
            });
            permissionCustomList.forEach(s->{
                RoleCustom roleCustom = new RoleCustom();
                roleCustom.setId(s.getPermissionId());
                roleCustom.setText(s.getPermissionName());
                roleCustom.setPid(s.getMenuId());
                roleCustom.setIsLeaf(true);
                roleCustomList.add(roleCustom);
            });
            //优化
            roleCustomList.forEach(s->{
                list.forEach(h->{
                    if(s.getId().equals(h.getPermissionId())){
                        s.setChecked(true);
                    }
                });
            });
            return roleCustomList;
        }else{
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @Override
    public void insertRolePermission(Integer roleId,List<RoleCustom> roleCustomList) throws Exception {
        //先删除该角色拥有的权限
        roleCustomMapper.deleteRolePermission(roleId);
        if(null!=roleCustomList && roleCustomList.size()>0){
            roleCustomMapper.insertRolePermission(roleCustomList);
        }
    }
}
