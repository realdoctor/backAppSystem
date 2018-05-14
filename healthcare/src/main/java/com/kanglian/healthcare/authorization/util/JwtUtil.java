package com.kanglian.healthcare.authorization.util;

import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;

public class JwtUtil {

    /**
     * jwt
     */
    public static final String JWT_ID               = "jwt";
    public static final String JWT_SECRET           = "9ff28bfa80b1beaca18e167df071db6e";
    public static final int    JWT_TTL              = 604800000; // token有效时间7t,单位毫秒
    public static final int    JWT_REFRESH_INTERVAL = 60*60*1000;
    public static final int    JWT_REFRESH_TTL      = 30*24*60*60*1000;

    /**
     * 签发JWT
     * 
     * @param id jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击。
     * @param iss jwt签发者
     * @param subject jwt所面向的用户
     * @param ttlMillis 有效期，单位毫秒
     * @return token
     * @throws Exception
     */
    public static String generToken(String id, String subject, long TTLMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥，就是一个base64加密后的字符串。
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setId(id).setIssuedAt(now) // 创建时间
                .setSubject(subject) // 主题
                .setIssuer("Online JWT Builder") // 发送人
                .setAudience("kanglian") // 个人签名
                // .claim("key","vaule")// 自定义key
                .signWith(signatureAlgorithm, signingKey);
        // 添加Token过期时间
        if (TTLMillis >= 0) {
            // 过期时间
            long expMillis = nowMillis + TTLMillis;
            // 现在是什么时间
            Date exp = new Date(expMillis);
            // 系统时间之前的token都是不可以被承认的
            builder.setExpiration(exp).setNotBefore(now);
        }
        // 生成JWT
        return builder.compact();
    }

    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     * 
     * @param token
     * @return
     */
    public static Claims verifyToken(String token) {
        try {
            Claims claims =
                    Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(JWT_SECRET)).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String deserializeKey(String key) {
        return Base64Codec.BASE64URL.decodeToString(key);
    }

    public static String serializeKey(String key) {
        return Base64Codec.BASE64URL.encode(key.getBytes());
    }

}
