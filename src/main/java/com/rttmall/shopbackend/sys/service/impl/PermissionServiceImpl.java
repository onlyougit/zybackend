package com.rttmall.shopbackend.sys.service.impl;

import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.sys.mapper.MenuCustomMapper;
import com.rttmall.shopbackend.sys.mapper.PermissionCustomMapper;
import com.rttmall.shopbackend.sys.mapper.PermissionMapper;
import com.rttmall.shopbackend.sys.pojo.*;
import com.rttmall.shopbackend.sys.service.PermissionService;
import com.rttmall.shopbackend.utils.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangweibin on 2017/4/6.
 */
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    MenuCustomMapper menuCustomlMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    PermissionCustomMapper permissionCustomMapper;

    @Override
    public List<MenuCustom> getMenuTree() {
        List<MenuCustom> menuCustom = menuCustomlMapper.getMenuTree();
        return menuCustom;
    }

    @Override
    public Map queryPermission(PermissionCustom permissionCustom) {
        Map map = new HashMap();
        List<PermissionCustom> permissionCustomList = permissionCustomMapper.queryPermission(permissionCustom);
        int sum = permissionCustomMapper.queryPermissionCount(permissionCustom);
        map.put("data", permissionCustomList);
        map.put("total", sum);
        return map;
    }

    @Override
    public void insertPermission(Permission permission) throws Exception {
        if (null != permission) {
            permissionMapper.insertSelective(permission);
        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @Override
    public Permission editQueryPermission(String permissionId) {
        Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
        return permission;
    }

    @Override
    public void updatePermission(Permission permission) throws Exception {
        if (null != permission) {
            permissionMapper.updateByPrimaryKeySelective(permission);
        } else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @Override
    public void batchDeletePermission(List permissionIdList) throws Exception {
        //判断是否有角色绑定该权限
        int count = permissionCustomMapper.isExistRolePermission(permissionIdList);
        if(count>0){
            throw new ServiceException(Constants.USING_THIS_PERMISSION);
        }
        //批量删除权限
        permissionCustomMapper.batchDeletePermission(permissionIdList);
    }

    @Override
    public boolean isExistPermission(String requestPath) {
        int count = permissionCustomMapper.isExistPermission(requestPath);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean isHavingPermission(User user, String requestPath) {
        UserCustom userCustom = new UserCustom();
        BeanUtils.copyProperties(user,userCustom);
        userCustom.setRequestPath(requestPath);
        int count = permissionCustomMapper.isHavingPermission(userCustom);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String selectPermissionByUrl(String requestPath) {
        String permissionName = permissionCustomMapper.selectPermissionByUrl(requestPath);
        return permissionName;
    }


}
