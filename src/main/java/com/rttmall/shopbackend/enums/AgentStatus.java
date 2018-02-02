package com.rttmall.shopbackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgentStatus {
    /* 有效 */
	INVALID(0, "无效"),

    /* 无效 */
	VALID(1, "有效"),

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
	AgentStatus(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public static AgentStatus getEnums(Integer code) {
        for (AgentStatus enums : values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
