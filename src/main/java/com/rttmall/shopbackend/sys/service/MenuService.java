package com.rttmall.shopbackend.sys.service;


import com.rttmall.shopbackend.sys.pojo.Menu;
import com.rttmall.shopbackend.sys.pojo.MenuCustom;

import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/2/22.
 */
public interface MenuService {


    //查询菜单列表
    public Map queryMenu(Menu menu);

    //查询所有父菜单
    public List<MenuCustom> queryParentMenu();

    //菜单添加
    public void insertMenu(Menu menu)throws Exception;

    //更新菜单查询
    public Menu editQueryMenu(String id);

    //更新菜单
    public void updateMenu(Menu menu)throws Exception;

    //批量删除菜单
    public void batchDeleteMenu(List menuIdList)throws Exception;

    //查询左导航菜单
    public List<MenuCustom> getLeftMenuTree(Integer userId);
}
