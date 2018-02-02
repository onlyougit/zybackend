package com.rttmall.shopbackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BankCardStatus {
	WAITAUDIT(0, "处理中"),

	AUDITPASS(1, "通过"),
	
	AUDITREJECT(2, "不通过"),

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
	BankCardStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public static BankCardStatus getEnums(Integer code) {
        for (BankCardStatus enums : values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
