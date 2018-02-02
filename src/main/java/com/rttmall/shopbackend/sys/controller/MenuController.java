package com.rttmall.shopbackend.sys.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.sys.enums.MenuIcon;
import com.rttmall.shopbackend.sys.pojo.Menu;
import com.rttmall.shopbackend.sys.pojo.MenuCustom;
import com.rttmall.shopbackend.sys.service.MenuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/3/31.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    Logger logger = LoggerFactory.getLogger(MenuController.class);

    /**
     * 菜单页面显示
     */
    @RequestMapping(method = RequestMethod.GET)
    public String findMenu() {
        return "sys/menu/menuList";
    }

    /**
     * 用户添加页面
     *
     * @return
     */
    @RequestMapping(value = "addMenuPage")
    public String addMenuPage() {
        return "sys/menu/menuAddEdit";
    }

    /**
     * 菜单查询功能
     *
     * @param json
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryMenu")
    public
    @ResponseBody
    Map queryMenu(@RequestParam(defaultValue = "") String json, Pagination grid) {
        MenuCustom menuCustom = JSON.parseObject(json, MenuCustom.class);
        if (null == menuCustom) {
            menuCustom = new MenuCustom();
        }
        grid.setStartIndex(grid.getPageIndex() * grid.getPageSize());
        menuCustom.setPagination(grid);
        Map map = menuService.queryMenu(menuCustom);
        return map;
    }

    /**
     * 添加菜单
     *
     * @param menu
     * @throws Exception
     */
    @RequestMapping("/insertMenuSubmit")
    public
    @ResponseBody
    void insertMenuSubmit(Menu menu) throws Exception {
        menuService.insertMenu(menu);
    }

    /**
     * 获取所有上一级菜单
     *
     * @return
     */
    @RequestMapping("/getParentMenu")
    public
    @ResponseBody
    List getParentMenu() {
        List<MenuCustom> list = menuService.queryParentMenu();
        return list;
    }

    /**
     * 菜单更新查询
     *
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/editQueryMenu")
    public
    @ResponseBody
    Menu editQueryMenu(@RequestParam String menuId) {
        return menuService.editQueryMenu(menuId);
    }

    /**
     * 菜单更新
     *
     * @param menu
     * @throws Exception
     */
    @RequestMapping(value = "/updateMenuSubmit", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateMenuSubmit(Menu menu) throws Exception {
        menuService.updateMenu(menu);
    }

    /**
     * 批量删除菜单
     *
     * @param menuIds
     */
    @RequestMapping("/batchDeleteMenu")
    public
    @ResponseBody
    void batchDeleteMenu(@RequestParam(value = "menuId") String menuIds) throws Exception {
        List menuIdList = Arrays.asList(menuIds.split(","));
        menuService.batchDeleteMenu(menuIdList);
    }
    
    @RequestMapping("/getMenuIcon")
    public
    @ResponseBody
    List getMenuIcon() {
        List list = new ArrayList<>();
        for (MenuIcon e : MenuIcon.values()) {
            list.add(e);
        }
        return list;
    }
}
