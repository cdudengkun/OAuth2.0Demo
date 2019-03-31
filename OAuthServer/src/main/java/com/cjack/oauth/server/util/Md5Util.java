package com.cjack.oauth.server.util;

import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: yanglei
 * Date: 8/29/13
 * Time: 5:02 PM
 */
public class Md5Util {

    /**
     * @auhor michael
     * MD5验证：http://md5calculator.chromefans.org
     * 参考资料：
     * http://www.cnblogs.com/renchunxiao/p/3411370.html
     * http://blog.csdn.net/wungmc/article/details/17713521
     * MD5 加密
     */
    public static String encrypt(String str) throws Exception {
        try {
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            return Hex.encodeHexString(byteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("NoSuchAlgorithmException", e);
        }
    }
}
