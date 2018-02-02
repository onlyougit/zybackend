package com.rttmall.shopbackend.sys.pojo;

import com.rttmall.shopbackend.pojo.Pagination;

/**
 * Created by wangweibin on 2017/4/2.
 */
public class RoleCustom extends Role {
    private User user;
    String roleGroup;
    private Pagination pagination;
    private String id;
    private String text;
    private String pid;
    private boolean checked;
    private boolean isLeaf;
    private boolean asyncLoad=true;

    public void setAsyncLoad(boolean asyncLoad){
        this.asyncLoad = asyncLoad;
    }
    public boolean getAsyncLoad() {
        return asyncLoad;
    }

    public void setIsLeaf(boolean isleaf) {
        isLeaf = isleaf;
    }

    public boolean getIsLeaf() {

        return isLeaf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {

        return id;
    }

    public String getText() {
        return text;
    }

    public String getPid() {
        return pid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {

        return pagination;
    }

    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }

    public String getRoleGroup() {

        return roleGroup;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {

        return user;
    }
}
