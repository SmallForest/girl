package com.imooc.excption;

import com.imooc.enums.ResultEnum;

/**
 * 自定义捕获异常类
 */
public class GirlExcption extends RuntimeException {
    private Integer code;

    /**
     * 构造方法
     * @param resultEnum
     */
    public GirlExcption(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
