package com.imooc.aspect;

import com.imooc.enums.ResultEnum;
import com.imooc.excption.GirlExcption;
import com.imooc.utils.HelpUtil;
import com.imooc.utils.ResultUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * http统一处理请求
 */
@Aspect
@Component
@RestController
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //获取配置文件中的key
    @Value("${key}")
    private String key;

    //获取配置文件中的key
    @Value("${not_need_login_method}")
    private String not_need_login_method;


    /**
     * 拦截GirlController/girlList随后修改为*实现全部拦截目的
     */
    @Pointcut("execution(public * com.imooc.controller.GirlController.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("before 111");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        //url
        logger.info("url={}", httpServletRequest.getRequestURL());
        logger.info("uri={}", httpServletRequest.getRequestURI());
        logger.info("method={}", httpServletRequest.getMethod());
        logger.info("ip={}", httpServletRequest.getRemoteAddr());
        //类名
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数
        logger.info("args={}", joinPoint.getArgs());

        //获取header中timestamp数据
        String timestamp = httpServletRequest.getHeader("timestamp");
        logger.info("header.timestamp={}", timestamp);

        //禁止timestamp是null或者空字符串
        if (HelpUtil.isNull(timestamp) || HelpUtil.isEmpty(timestamp)) {
            throw new GirlExcption(ResultEnum.EMPTY_TIMESTAMP);
        }

        //获取header中sign数据，签名（KEY+uri+timestamp）比如 KEY/girls1592021248
        String sign = httpServletRequest.getHeader("sign");
        logger.info("header.sign={}", sign);

        //禁止timestamp是null或者空字符串
        if (HelpUtil.isNull(sign) || HelpUtil.isEmpty(sign)) {
            throw new GirlExcption(ResultEnum.EMPTY_SIGN);
        }
        if (!sign.equals(HelpUtil.md5(this.key + httpServletRequest.getRequestURI() + (new String(timestamp))))) {
            throw new GirlExcption(ResultEnum.CHECK_SIGN);
        }

        // todo 请求时间校验

        // token校验(jwt) 除了几个指定的uri不用传递token，其他的都需要传递token
        String token = httpServletRequest.getHeader("token");
        // 根据not_need_login_method跳过校验
        String[] not_need_login_method = this.not_need_login_method.split(",");

        // uri在数组not_need_login_method的路由不必检查token是否传递是否为真值
        if (!Arrays.asList(not_need_login_method).contains(httpServletRequest.getRequestURI())) {
            if (HelpUtil.isNull(token) || HelpUtil.isEmpty(token)) {
                throw new GirlExcption(ResultEnum.MUST_LOGIN);//必须登录之后传递token
            }
        }


        // token不为null并且不为空
        if (!HelpUtil.isNull(token) && !HelpUtil.isEmpty(token)) {
            String id = HelpUtil.verifyToken(token);
            if (id.equals("")) {
                throw new GirlExcption(ResultEnum.CHECK_TOKEN);
            } else {
                logger.info("解析token得到的id是{}", id);
            }
        }
    }

    @After("log()")
    public void doAfter() {
        //方法执行之后执行
        logger.info("after error!");
    }

    //处理返回值
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        //注意此处调用了toString方法所以要求domain中的类必须有toString方法，否则异常
        logger.info("response={}", object.toString());
    }
}
