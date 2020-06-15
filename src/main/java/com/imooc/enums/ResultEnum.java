package com.imooc.enums;

/**
 * 枚举管理错误码
 */
public enum ResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    PRIMARY_SCHOOL(1, "你在上小学吧？"),
    MIDDLE_SCHOOL(2, "你在中学吧"),
    EMPTY_TIMESTAMP(3, "header中timestamp参数必传"),
    EMPTY_SIGN(4, "header中sign参数必传"),
    CHECK_SIGN(5, "sign参数校验失败"),
    CHECK_TOKEN(6, "token无效"),
    MUST_LOGIN(7, "token必须传递"),
    ;

    private Integer code;
    private String  msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
