package com.rttmall.shopbackend.interceptor;

import com.rttmall.shopbackend.exception.ServiceException;
import com.rttmall.shopbackend.httpModel.SessionInfo;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.sys.service.PermissionService;
import com.rttmall.shopbackend.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wangweibin on 2017/2/23.
 */
public class AuthorInterceptor implements HandlerInterceptor {
    @Autowired
    private PermissionService permissionService;
    Logger logger = LoggerFactory.getLogger(AuthorInterceptor.class);
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        String requestPath = url.substring(httpServletRequest.getContextPath().length());
        //StringBuffer url = httpServletRequest.getRequestURL();
        HttpSession session = httpServletRequest.getSession();
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute(Constants.SESSION_BEAN);
        if(null != sessionInfo){
            logger.debug("进入权限拦截器->访问的资源为：[" + requestPath + "]");
            User user = sessionInfo.getUser();
            if("admin".equals(user.getUserName())){
                return true;
            }else {
                if(permissionService.isExistPermission(requestPath)){//判断此URL是否需要权限验证,需要
                    if(!permissionService.isHavingPermission(user,requestPath)){//判断是否有权限，否
                        //查询出权限名称
                        String permissionName = permissionService.selectPermissionByUrl(requestPath);
                        throw new ServiceException("您没有["+permissionName+"]的权限,请联系管理员给您赋予相应权限");
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }
        }
        return true;
    }
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
