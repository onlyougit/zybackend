package com.rttmall.shopbackend.sys.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MenuIcon {
	
	CHILD("icon-node", "子菜单图标"),

    PARENT("icon-expand", "父菜单图标"),

    ;

    /* 编码 */
    private String code;

    /* 描述 */
    private String text;

    MenuIcon(String code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getCode() {
        return code;
    }


    public void setCode(String code) {
		this.code = code;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
        return text;
    }
    public static MenuIcon getEnums(String code) {
        for (MenuIcon enums : values()) {
            if (code.equalsIgnoreCase(enums.getCode())) {
                return enums;
            }
        }
        return null;
    }

}
