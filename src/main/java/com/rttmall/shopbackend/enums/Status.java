package com.rttmall.shopbackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
    /* 有效 */
    VALID("VALID", "有效"),

    /* 无效 */
    INVALID("INVALID", "无效"),

    ;

    /* 编码 */
    private String code;

    /* 描述 */
    private String text;

    public void setCode(String code) {
		this.code = code;
	}
	public void setText(String text) {
		this.text = text;
	}
	Status(String code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static Status getEnums(String code) {
        for (Status enums : values()) {
            if (StringUtils.equalsIgnoreCase(code, enums.getCode())) {
                return enums;
            }
        }
        return null;
    }
}
