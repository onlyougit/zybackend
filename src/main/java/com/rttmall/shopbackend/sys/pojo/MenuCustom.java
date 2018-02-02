package com.rttmall.shopbackend.sys.pojo;

import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;

public class MenuCustom extends Menu{

    private Pagination pagination;

    private List<MenuCustom> children;

    private String id;

    private String pid;

    private String text;

    private String url;

    private String iconCls;

    private String menuParentName;

    public void setMenuParentName(String menuParentName) {
        this.menuParentName = menuParentName;
    }

    public String getMenuParentName() {

        return menuParentName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getId() {

        return id;
    }

    public String getPid() {
        return pid;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setChildren(List<MenuCustom> children) {
        this.children = children;
    }

    public List<MenuCustom> getChildren() {

        return children;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {

        return pagination;
    }

}