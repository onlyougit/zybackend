package com.rttmall.shopbackend.sys.controller;

import com.alibaba.fastjson.JSON;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.rttmall.shopbackend.enums.Identity;
import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.httpModel.SessionInfo;
import com.rttmall.shopbackend.sys.pojo.MenuCustom;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.sys.pojo.UserCustom;
import com.rttmall.shopbackend.sys.service.MenuService;
import com.rttmall.shopbackend.sys.service.UserService;
import com.rttmall.shopbackend.utils.Constants;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangweibin on 2017/2/8.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private GenericManageableCaptchaService captchaService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 显示主页面
     *
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return "main";
    }

    /**
     * 退出系统
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Login.jsp";
    }

    /**
     * 身份验证
     *
     * @param json
     * @return
     */
    @RequestMapping("/loginCheck")
    public
    @ResponseBody
    void loginCheck(HttpServletRequest request, HttpSession session, @RequestParam(defaultValue = "") String json) throws Exception {
        UserCustom userCustom = JSON.parseObject(json, UserCustom.class);
        if (null != userCustom) {
            //验证验证码
            boolean pass = checkAuthCode(userCustom, request);
            if (!pass) {
                throw new ServiceException(Constants.AUTH_CODE_ERROR);
            }
            userService.loginCheck(userCustom, session);
        }else{
            throw new ServiceException(Constants.REQUEST_DATA_ERROR);
        }
    }

    @RequestMapping("/showLeftTab")
    public void showLeftTab(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(Constants.SESSION_BEAN);
        User user = sessionInfo.getUser();
        List<MenuCustom> menuCustomList = menuService.getLeftMenuTree(user.getUserId());
        String jsonString = JSON.toJSONString(menuCustomList);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("applicatin/json;charset=UTF-8");
        try {
            response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkAuthCode(UserCustom userCustom, HttpServletRequest request) throws Exception {
        String code = userCustom.getAuthCode();
        if (StringUtils.isBlank(code)) {
            throw new ServiceException(Constants.AUTH_CODE_ERROR);
        }
        //检查验证码
        String captchaId = request.getSession().getId();
        try {
            return captchaService.validateResponseForID(captchaId, code);
        } catch (CaptchaServiceException ex) {
            throw new ServiceException(Constants.AUTH_CODE_ERROR);
        }
    }

	@RequestMapping("/getIdentity")
    public
    @ResponseBody
    List getPayType() {
        List list = new ArrayList<>();
        for (Identity e : Identity.values()) {
            list.add(e);
        }
        return list;
    }
}
