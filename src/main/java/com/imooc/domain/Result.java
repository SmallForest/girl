package com.imooc.domain;

/**
 * http返回的最外层对象
 */
public class Result<T> {
    /**
     * 错误码 error code
     */
    private Integer code;

    /**
     * 文字描述
     */
    private String msg;

    /**
     * 具体的内容 data数据 可能是list 数组
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
