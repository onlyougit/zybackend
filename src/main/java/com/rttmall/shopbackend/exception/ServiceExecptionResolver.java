package com.rttmall.shopbackend.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangweibin on 2017/2/22.
 */
public class ServiceExecptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        //如果是json请求
        ServiceException serviceException = null;
        if (e instanceof ServiceException) {
            serviceException = (ServiceException) e;
        } else {
            serviceException = new ServiceException("未知错误");
        }
        String message = serviceException.getMessage();
        httpServletResponse.setStatus(500);
        httpServletResponse.setContentType("text/plain;charset=utf-8");
        ServletOutputStream  outputStream = null;
        try {
        	outputStream = httpServletResponse.getOutputStream();
        	outputStream.write(message.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;

    }
}
