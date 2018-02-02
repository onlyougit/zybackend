package com.rttmall.shopbackend.sys.pojo;

import com.rttmall.shopbackend.pojo.Pagination;

public class PermissionCustom extends Permission{

    private String menuName;

    private Pagination pagination;

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {

        return pagination;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {

        return menuName;
    }

}