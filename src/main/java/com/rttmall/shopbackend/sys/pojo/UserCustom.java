package com.rttmall.shopbackend.sys.pojo;

import com.rttmall.shopbackend.pojo.Pagination;

/**
 * Created by wangweibin on 2017/3/29.
 */
public class UserCustom extends User {

    //验证码
    private String authCode;
    //新密码
    private String userNewPw;
    //密码确认
    private String userNewPwConfirm;
    private RoleCustom roleCustom;
    private Pagination pagination;
    private String requestPath;
    private String roleName;
    private Integer identity;

    
    public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {

        return roleName;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestPath() {

        return requestPath;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {

        return pagination;
    }

    public void setRoleCustom(RoleCustom roleCustom) {
        this.roleCustom = roleCustom;
    }

    public RoleCustom getRoleCustom() {
        return roleCustom;
    }

    public void setUserNewPw(String userNewPw) {
        this.userNewPw = userNewPw;
    }

    public void setUserNewPwConfirm(String userNewPwConfirm) {
        this.userNewPwConfirm = userNewPwConfirm;
    }

    public String getUserNewPw() {
        return userNewPw;
    }

    public String getUserNewPwConfirm() {
        return userNewPwConfirm;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {

        return authCode;
    }
}
