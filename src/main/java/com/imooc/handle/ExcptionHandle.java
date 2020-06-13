package com.imooc.handle;

import com.imooc.domain.Result;
import com.imooc.excption.GirlExcption;
import com.imooc.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕获
 */
@ControllerAdvice
public class ExcptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    /**
     * 注解声明要捕获的异常类和json
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof GirlExcption) {
            GirlExcption girlExcption = (GirlExcption) e;
            return ResultUtil.error(girlExcption.getCode(), girlExcption.getMessage());
        } else {
            logger.error("【系统异常】{}");
            logger.error(e.getMessage());
            return ResultUtil.error(-1, "未知错误");
        }
    }
}
