package com.cjack.oauth.server.util;

/**
 * 生成授权码和授权令牌的工具类
 * 这里直接给当前时间戳做md5之后再截取16位即可
 * Created by root on 3/30/19.
 */
public class OauthUtil {

    /**
     * 生成授权码
     * @return
     * @throws Exception
     */
    public static String generateOauthCode() throws Exception {
        String curr = String.valueOf( System.currentTimeMillis());
        return Md5Util.encrypt( curr).substring( 0, 15);
    }

    /**
     * 生成授权token
     * @return
     */
    public static String generateAccessToken() throws Exception {
        String curr = String.valueOf( System.currentTimeMillis());
        return Md5Util.encrypt( curr).substring( 0, 15);
    }
}
