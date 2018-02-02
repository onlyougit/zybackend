package com.rttmall.shopbackend.app.service.impl;


import javax.servlet.http.HttpSession;

import com.rttmall.shopbackend.app.service.SessionService;
import com.rttmall.shopbackend.httpModel.SessionInfo;
import com.rttmall.shopbackend.sys.pojo.User;
import com.rttmall.shopbackend.utils.Constants;

public class SessionServiceImpl implements SessionService {

	@Override
	public int getUserId(HttpSession session) {
		int userId = 0;
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute(Constants.SESSION_BEAN);
        if(null != sessionInfo){
            User user = sessionInfo.getUser();
            if(null != user){
                userId = user.getUserId();
            }
        }
        return userId;
	}
}
