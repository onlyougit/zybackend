package com.rttmall.shopbackend.sys.controller;

import com.alibaba.fastjson.JSON;
import com.rttmall.shopbackend.enums.Status;
import com.rttmall.shopbackend.pojo.Pagination;
import com.rttmall.shopbackend.sys.pojo.Role;
import com.rttmall.shopbackend.sys.pojo.UserCustom;
import com.rttmall.shopbackend.sys.service.RoleService;
import com.rttmall.shopbackend.sys.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户页面显示
     */
    @RequestMapping(method = RequestMethod.GET)
    public String findUser() {
        return "sys/user/userList";
    }

    /**
     * 用户添加页面
     *
     * @return
     */
    @RequestMapping(value = "addUserPage")
    public String addUserPage() {
        return "sys/user/userAddEdit";
    }

    /**
     * 用户查询功能
     *
     * @param json
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryUser")
    public
    @ResponseBody
    Map queryUser(@RequestParam(defaultValue = "") String json, Pagination grid) throws Exception {
        UserCustom userCustom = JSON.parseObject(json, UserCustom.class);
        if (null == userCustom) {
            userCustom = new UserCustom();
        }
        grid.setStartIndex(grid.getPageIndex() * grid.getPageSize());
        userCustom.setPagination(grid);
        Map map = userService.queryUser(userCustom);
        return map;
    }

    /**
     * 密码修改
     *
     * @param json
     * @return
     * @throws Exception
     */
    @RequestMapping("/changePassword")
    public
    @ResponseBody
    void changePassword(@RequestParam(defaultValue = "") String json) throws Exception {
        UserCustom userCustom = JSON.parseObject(json, UserCustom.class);
        userService.changePassword(userCustom);
    }


    /**
     * 用户添加功能
     *
     * @param userCustom
     * @return
     */
    @RequestMapping("/insertUserSubmit")
    public
    @ResponseBody
    void insertUserSubmit(UserCustom userCustom) throws Exception {
        userService.insertUser(userCustom);
    }

    @RequestMapping("/getStatus")
    public
    @ResponseBody
    List getStatus() {
        List list = new ArrayList<>();
        for (Status e : Status.values()) {
            list.add(e);
        }
        return list;
    }

    @RequestMapping("/getRole")
    public
    @ResponseBody
    List getRole() {
        List<Role> list = roleService.queryAllRole();
        return list;
    }

    /**
     * 用户更新查询
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/editQueryUser")
    public
    @ResponseBody
    UserCustom editQueryUser(@RequestParam Integer userId) {
        return userService.editQueryUser(userId);
    }

    /**
     * 用户更新功能
     *
     * @param userCustom
     * @return
     */
    @RequestMapping(value = "/updateUserSubmit", method = RequestMethod.POST)
    public
    @ResponseBody
    void updateUserSubmit(UserCustom userCustom) throws Exception {
        userService.updateUser(userCustom);
    }

    /**
     * 批量删除用户
     *
     * @param userIds
     * @return
     */
    @RequestMapping("/batchDeleteUser")
    public
    @ResponseBody
    void batchDeleteUser(@RequestParam(value = "userId") String userIds) {
        List userIdList = Arrays.asList(userIds.split(","));
        userService.batchDeleteUser(userIdList);
    }
}
