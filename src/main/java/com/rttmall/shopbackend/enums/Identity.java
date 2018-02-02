package com.rttmall.shopbackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Identity {
    ADMIN(1, "超级用户"),
    
	HEADQUARTERS(6, "总部人员"),

    BUSINESS(7, "营业部"),

    AGENT(8, "代理商"),
    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setText(String text) {
		this.text = text;
	}
	Identity(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public static Identity getEnums(Integer code) {
        for (Identity enums : values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
