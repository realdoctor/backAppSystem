package com.kanglian.healthcare.authorization.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import org.apache.commons.codec.binary.Base64;
import io.jsonwebtoken.Claims;

public class TokenUtil {

    /**
     * 生成Token
     * 
     * @param str
     * @return
     */
    public static String generToken(String str) {
        return JwtUtil.generToken(JwtUtil.JWT_ID, str, JwtUtil.JWT_TTL);
//        return DESUtil.encode(str);
    }

    /**
     * 解析Token
     * 
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        Claims claims = JwtUtil.parseToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
//        return DESUtil.decode(token);
    }

    static class DESUtil {
        public static final String SECRET_KEY  = "1qaz2wsx3edc4rfv5tgb^YHN&UJM*IK<(OL>)P:?";
        public static final String DES         = "DES";
        public static Key          DEFAULT_KEY = null;

        static {
            DEFAULT_KEY = obtainKey(SECRET_KEY);
        }

        /**
         * 获得key
         **/
        public static Key obtainKey(String key) {
            if (key == null) {
                return DEFAULT_KEY;
            }
            KeyGenerator generator = null;
            try {
                generator = KeyGenerator.getInstance(DES);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            generator.init(new SecureRandom(key.getBytes()));
            Key key1 = generator.generateKey();
            generator = null;
            return key1;
        }

        /**
         * 加密<br>
         * String明文输入,String密文输出
         */
        public static String encode(String str) {
            return encode64(null, str);
        }

        /**
         * 加密<br>
         * String明文输入,String密文输出
         */
        public static String encode64(String key, String str) {
            return Base64.encodeBase64URLSafeString(obtainEncode(key, str.getBytes()));
        }

        /**
         * 解密<br>
         * 以String密文输入,String明文输出
         */
        public static String decode(String str) {
            return decode64(null, str);
        }

        /**
         * 解密<br>
         * 以String密文输入,String明文输出
         */
        public static String decode64(String key, String str) {
            byte[] sb = obtainDecode(key, Base64.decodeBase64(str));
            if (sb != null && sb.length > 0) {
                return new String(sb);
            }
            return null;
        }

        /**
         * 加密<br>
         * 以byte[]明文输入,byte[]密文输出
         */
        private static byte[] obtainEncode(String key, byte[] str) {
            byte[] byteFina = null;
            Cipher cipher;
            try {
                Key key1 = obtainKey(key);
                cipher = Cipher.getInstance(DES);
                cipher.init(Cipher.ENCRYPT_MODE, key1);
                byteFina = cipher.doFinal(str);
            } catch (Exception e) {
            } finally {
                cipher = null;
            }
            return byteFina;
        }

        /**
         * 解密<br>
         * 以byte[]密文输入,以byte[]明文输出
         */
        private static byte[] obtainDecode(String key, byte[] str) {
            Cipher cipher;
            byte[] byteFina = null;
            try {
                Key key1 = obtainKey(key);
                cipher = Cipher.getInstance(DES);
                cipher.init(Cipher.DECRYPT_MODE, key1);
                byteFina = cipher.doFinal(str);
            } catch (Exception e) {
            } finally {
                cipher = null;
            }
            return byteFina;
        }
    }
}
