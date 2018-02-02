package com.rttmall.shopbackend.sys.controller;

import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.sys.pojo.MenuCustom;
import com.rttmall.shopbackend.sys.pojo.Permission;
import com.rttmall.shopbackend.sys.pojo.PermissionCustom;
import com.rttmall.shopbackend.sys.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by wangweibin on 2017/3/31.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    Logger logger = LoggerFactory.getLogger(PermissionController.class);

    /**
     * 权限页面显示
     */
    @RequestMapping(method = RequestMethod.GET)
    public String findPermission() {
        return "sys/permission/permissionList";
    }

    /**
     * 权限添加更新页面
     * @return
     */
    @RequestMapping(value = "addPermissionPage")
    public String addPermissionPage() {
        return "sys/permission/permissionAddEdit";
    }

    /**
     * 获取菜单树
     * @return
     */
    @RequestMapping("/getMenuTree")
    public @ResponseBody
    List getMenuTree(){
        List<MenuCustom> menuCustomList = permissionService.getMenuTree();
        return menuCustomList;
    }

    /**
     * 查询权限
     * @param menuId
     * @param grid
     * @return
     */
    @RequestMapping(value = "/queryPermission")
    public
    @ResponseBody
    Map queryPermission(String menuId, Pagination grid) {
        PermissionCustom permissionCustom = new PermissionCustom();
        grid.setStartIndex(grid.getPageIndex() * grid.getPageSize());
        permissionCustom.setPagination(grid);
        if(!"".equals(menuId) && null!=menuId){
            permissionCustom.setMenuId(menuId);
        }
        Map map = permissionService.queryPermission(permissionCustom);
        return map;
    }

    /**
     * 权限添加
     * @param permission
     * @throws Exception
     */
    @RequestMapping("/insertPermissionSubmit")
    public
    @ResponseBody
    void insertPermissionSubmit(Permission permission) throws Exception {
        permissionService.insertPermission(permission);
    }

    @RequestMapping(value = "/editQueryPermission")
    public
    @ResponseBody
    Permission editQueryPermission(@RequestParam String permissionId) {
        return permissionService.editQueryPermission(permissionId);
    }
    @RequestMapping(value = "/updatePermissionSubmit", method = RequestMethod.POST)
    public
    @ResponseBody
    void updatePermissionSubmit(Permission permission) throws Exception {
        permissionService.updatePermission(permission);
    }
    @RequestMapping("/batchDeletePermission")
    public
    @ResponseBody
    void batchDeletePermission(@RequestParam(value = "permissionId") String permissionIds) throws Exception {
        List permissionIdList = Arrays.asList(permissionIds.split(","));
        permissionService.batchDeletePermission(permissionIdList);
    }
}
