package com.rttmall.shopbackend.httpModel;

import com.rttmall.shopbackend.sys.pojo.User;

/**
 * session
 * @author huangheurn
 *
 */
public class SessionInfo implements java.io.Serializable {


	private User user;//用户信息
	
	//private Role role;//角色信息

	private boolean isDefaultPwd = false;

	/*public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}*/

	public boolean isDefaultPwd() {
		return isDefaultPwd;
	}

	public void setDefaultPwd(boolean isDefaultPwd) {
		this.isDefaultPwd = isDefaultPwd;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	

}