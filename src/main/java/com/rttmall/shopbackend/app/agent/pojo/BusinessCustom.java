package com.rttmall.shopbackend.app.agent.pojo;

import com.rttmall.shopbackend.sys.pojo.User;

public class BusinessCustom extends Business{

	private User user;

	private String userName;

	private String resetPw;

	public void setResetPw(String resetPw) {
		this.resetPw = resetPw;
	}

	public String getResetPw() {

		return resetPw;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {

		return userName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
