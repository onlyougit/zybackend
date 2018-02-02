package com.rttmall.shopbackend.sys.service.impl;

import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.sys.mapper.MenuCustomMapper;
import com.rttmall.shopbackend.sys.mapper.MenuMapper;
import com.rttmall.shopbackend.sys.pojo.Menu;
import com.rttmall.shopbackend.sys.pojo.MenuCustom;
import com.rttmall.shopbackend.sys.service.MenuService;
import com.rttmall.shopbackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/2/22.
 */
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuCustomMapper menuCustomMapper;

    @Override
    public Map queryMenu(Menu menu) {
        Map map = new HashMap();
        List<MenuCustom> menuList = menuCustomMapper.queryMenu(menu);
        int sum = menuCustomMapper.queryMenuCount(menu);
        map.put("data", menuList);
        map.put("total", sum);
        return map;
    }

    @Override
    public List<MenuCustom> queryParentMenu() {
        List<MenuCustom> menuList = menuCustomMapper.queryParentMenu();
        return menuList;
    }

    @Override
    public void insertMenu(Menu menu)throws Exception {
        if(null != menu){
            //判断菜单名称是否存在
            /*int count = menuCustomMapper.isExistMenu(menu.getMenuName());
            if (count != 0) {
                throw new ServiceException(Constants.MENUNAME_EXIST);
            }*/
            menuMapper.insertSelective(menu);
        }else{
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }
    @Override
    public Menu editQueryMenu(String id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        return menu;
    }

    @Override
    public void updateMenu(Menu menu) throws Exception {
        //判断菜单名称是否存在
        /*int count = menuCustomMapper.isExistUpdateMenu(menu);
        if (count != 0) {
            throw new ServiceException(Constants.MENUNAME_EXIST);
        }*/
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void batchDeleteMenu(List menuIdList) throws Exception  {
        //判断有没有角色在用此菜单
        int count = menuCustomMapper.isExistRolePermission(menuIdList);
        if(count>0){
            throw new ServiceException(Constants.USING_THIS_MENU);
        }
        //再判断一下该菜单有没有子菜单
        int count2 = menuCustomMapper.isExistChildMenu(menuIdList);
        if(count2>0){
            throw new ServiceException(Constants.USING_THIS_MENU);
        }
        //批量删除权限表数据
        menuCustomMapper.batchDeletePermission(menuIdList);
        //批量删除菜单
        menuCustomMapper.batchDeleteMenu(menuIdList);
    }

    @Override
    public List<MenuCustom> getLeftMenuTree(Integer userId) {
        List<MenuCustom> menuList = menuCustomMapper.getLeftMenuTree(userId);
        return menuList;
    }
}