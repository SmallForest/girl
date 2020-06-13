package com.imooc.utils;

import com.imooc.domain.Result;
import com.imooc.enums.ResultEnum;

public class ResultUtil {
    /**
     * 返回object
     *
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }


    /**
     * 不包含object的成功
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 返回失败
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 重载一下
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(null);
        return result;
    }
}
