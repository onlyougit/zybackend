package com.rttmall.shopbackend.app.service;

import javax.servlet.http.HttpSession;


public interface SessionService {

	public int getUserId(HttpSession session);
}
