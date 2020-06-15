package com.imooc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 项目工具类包
 */
public class HelpUtil {
    /**
     * md5
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        String md5Password = DigestUtils.md5DigestAsHex(str.getBytes());//加密参数
        return md5Password;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        else if (obj instanceof CharSequence) return ((CharSequence) obj).length() == 0;
        else if (obj instanceof Collection) return ((Collection) obj).isEmpty();
        else if (obj instanceof Map) return ((Map) obj).isEmpty();
        else if (obj.getClass().isArray()) return Array.getLength(obj) == 0;

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 获取jwt token
     *
     * @param id
     * @return
     */
    public static String getToken(String  id) {
        Algorithm algorithm = Algorithm.HMAC256("3333");
        String token = JWT.create()
                .withIssuer(id)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证JWT token
     *
     * @param token
     * @return
     */
    public static String verifyToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuer();
        } catch (JWTDecodeException exception) {
            //Invalid token
            return "";
        }
    }

    /**
     * 集成融云的例子
     * @throws Exception
     */
    public static void sendMsg() throws Exception{
        String appKey = "appKey";
        String appSecret = "appSecret";

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        User user = rongCloud.user;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel userModel = new UserModel()
                .setId("hHjap87")
                .setName("RongCloud")
                .setPortrait("http://www.rongcloud.cn/images/logo.png");
        TokenResult result = user.register(userModel);
        System.out.println("getToken:  " + result.toString());
    }
}
