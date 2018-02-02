package com.rttmall.shopbackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerStatus {
    /* 有效 */
    VALID(0, "禁止交易"),

    /* 无效 */
    INVALID(1, "允许交易"),

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
	CustomerStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public static CustomerStatus getEnums(Integer code) {
        for (CustomerStatus enums : values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
