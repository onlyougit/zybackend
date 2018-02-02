package com.rttmall.shopbackend.sys.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.sys.pojo.Role;
import com.rttmall.shopbackend.sys.pojo.RoleCustom;
import com.rttmall.shopbackend.sys.service.RoleService;
import com.rttmall.shopbackend.utils.Constants;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * 角色页面显示
     */
    @RequestMapping(method = RequestMethod.GET)
    public String findRole() {
        return "sys/role/roleList";
    }

    /**
     * 用户添加页面
     *
     * @return
     */
    @RequestMapping(value = "addRolePage")
    public String addRolePage() {
        return "sys/role/roleAddEdit";
    }

    @RequestMapping(value = "addAuthority")
    public String addAuthority() {
        return "sys/role/roleAuthority";
    }

    /**
     * 角色查询功能
     *
     * @param grid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRole")
    public
    @ResponseBody
    Map queryRole(Pagination grid) throws Exception {
        RoleCustom roleCustom = new RoleCustom();
        grid.setStartIndex(grid.getPageIndex() * grid.getPageSize());
        roleCustom.setPagination(grid);
        Map map = roleService.queryRole(roleCustom);
        return map;
    }

    /**
     * 添加角色功能
     *
     * @param roleCustom
     * @throws Exception
     */
    @RequestMapping("/insertRoleSubmit")
    public
    @ResponseBody
    void insertRoleSubmit(RoleCustom roleCustom) throws Exception {
        roleService.insertRole(roleCustom);
    }

    /**
     * 角色更新查询
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/editQueryRole")
    public
    @ResponseBody
    Role editQueryRole(@RequestParam Integer roleId) {
        return roleService.editQueryRole(roleId);
    }

    /**
     * 角色更新
     *
     * @param roleCustom
     * @throws Exception
     */
    @RequestMapping(value = "/updateRoleSubmit", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateRoleSubmit(RoleCustom roleCustom) throws Exception {
        roleService.updateRole(roleCustom);
    }

    /**
     * 批量删除角色
     *
     * @param roleIds
     * @throws Exception
     */
    @RequestMapping("/batchDeleteRole")
    public
    @ResponseBody
    void batchDeleteRole(@RequestParam(value = "roleId") String roleIds) throws Exception {
        List roleIdList = Arrays.asList(roleIds.split(","));
        roleService.batchDeleteRole(roleIdList);
    }

    @RequestMapping("/getMenuPermissionTree")
    public
    @ResponseBody
    List<RoleCustom> getMenuPermissionTree(String roleId) throws Exception {
        return roleService.getMenuPermissionTree(roleId);
    }

    @RequestMapping(value = "/saveAuthority", method = RequestMethod.POST)
    public
    @ResponseBody
    void saveAuthority(String roleId,@RequestParam(value = "permissionIds") String permissionIds)throws Exception {
        if(!"".equals(roleId)&&null!=roleId&&!"".equals(permissionIds)&&null!=permissionIds){
            int roleId2 = Integer.parseInt(roleId);
            List<String> permissionList = JSON.parseArray(permissionIds,String.class);
            List<RoleCustom> roleCustomList = new ArrayList<>();
            permissionList.forEach(s->{
                RoleCustom roleCustom = new RoleCustom();
                roleCustom.setRoleId(roleId2);
                roleCustom.setId(s);
                roleCustomList.add(roleCustom);
            });
            roleService.insertRolePermission(roleId2,roleCustomList);
        }else {
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }
}
