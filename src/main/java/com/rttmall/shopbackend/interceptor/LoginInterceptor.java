package com.rttmall.shopbackend.interceptor;

import com.rttmall.shopbackend.httpModel.SessionInfo;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.utils.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wangweibin on 2017/2/23.
 */
public class LoginInterceptor implements HandlerInterceptor {
    //进入Controller方法之前执行
//    主要用于身份认证、身份授权
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //判断是否是公开地址，一般会在配置文件中配置
        //String url = httpServletRequest.getRequestURI();
        StringBuffer url = httpServletRequest.getRequestURL();
        if(url.indexOf("/login/loginCheck.action")>0 || url.indexOf("/Captcah/index.action")>0){
            return true;
        }
        //判断session是否有值
        HttpSession session = httpServletRequest.getSession();
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute(Constants.SESSION_BEAN);
        if(null != sessionInfo){
            User user = sessionInfo.getUser();
            if(null != user){
                String userName = user.getUserName();
                if(null != userName){
                    return true;
                }
            }
        }
        httpServletRequest.getRequestDispatcher("/Login.jsp").forward(httpServletRequest,httpServletResponse);
        return false;
    }
    //进入Controller方法之后，返回ModelAndView之前执行
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    //执行完Controller方法之后执行
//    统一异常处理、统一日志处理
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
