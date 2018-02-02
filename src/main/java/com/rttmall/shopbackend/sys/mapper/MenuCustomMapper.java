package com.rttmall.shopbackend.sys.mapper;

import com.rttmall.shopbackend.sys.pojo.Menu;
import com.rttmall.shopbackend.sys.pojo.MenuCustom;

import java.util.List;

public interface MenuCustomMapper {
    //查询菜单
    public List<MenuCustom> queryMenu(Menu menu);
    //查询总记录数
    public int queryMenuCount(Menu menu);
    //查询所有父菜单
    public List<MenuCustom> queryParentMenu();
    //判断菜单名称是否存在
    public int isExistMenu(String menuName);
    //更新时判断菜单名称是否存在
    public int isExistUpdateMenu(Menu menu);
    //判断一下该菜单有没有子菜单
    public int isExistChildMenu(List roleIdList);
    //查看是否有功能在使用该角色
    public int isExistRolePermission(List roleIdList);
    //
    public int batchDeletePermission(List roleIdList);
    //批量删除菜单
    public int batchDeleteMenu(List roleIdList);
    //查询菜单树
    public List<MenuCustom> getMenuTree();
    //查询所有菜单
    public List<MenuCustom> selectAllMenu();
    //查询左导航菜单
    public List<MenuCustom>getLeftMenuTree(Integer userId);
}