package com.rttmall.shopbackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FundRemark {

    ENTRYMONEY(0, "充入盘中资金"),
    DRAWING(1, "提款"),
    EXITMONEY(2, "结算盘中资金"),
    RECHARGE(3, "充值"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    FundRemark(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public static FundRemark getEnums(Integer code) {
        for (FundRemark enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }

}
