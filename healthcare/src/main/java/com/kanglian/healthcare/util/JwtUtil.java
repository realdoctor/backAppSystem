package com.kanglian.healthcare.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.Base64Codec;

public class JwtUtil {

    public final static String sercetKey = "realdoctor";// 私钥
    public final static long   KEEPTIME  = 1800000;     // 30分钟

    // 解析Token，同时也能验证Token，当验证失败返回null
    public static Claims verifyToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(sercetKey)).parseClaimsJws(token).getBody();
            return claims;
        } catch (SignatureException | ExpiredJwtException ex) {
            return null;
        }
    }

    public static String generToken(String userId, String subject, long TTLMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥，就是一个base64加密后的字符串。
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(sercetKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setId(userId).setIssuedAt(now) // 创建时间
                                 .setSubject(subject) // 主题
                                 .setIssuer("Online JWT Builder") // 发送人
                                 .setAudience(null) // 个人签名
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

    public static String deserializeKey(String key) {
        return Base64Codec.BASE64URL.decodeToString(key);
    }

    public static String serializeKey(String key) {
        return Base64Codec.BASE64URL.encode(key.getBytes());
    }

    /**
     * iss(Issuser)：代表这个JWT的签发主体； 
     * sub(Subject)：代表这个JWT的主体，即它的所有人；
     * aud(Audience)：代表这个JWT的接收对象； 
     * exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间； 
     * nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的； 
     * iat(Issued at)：是一个时间戳，代表这个JWT的签发时间； jti(JWT ID)：是JWT的唯一标识。
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Map<String, Object> map = new HashMap<String, Object>();
        // map.put("userId", 1001);
        // map.put("pwd", 12345);
        // String ss = generToken("1001", JSON.toJSONString(map), KEEPTIME);
        // System.err.println(ss);

        String ss = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MjMzNDA3ODcsInN1YiI6IntcInB3ZFwiOjEyMzQ1LFwidXNlcklkXCI6MTAwMX0iLCJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJleHAiOjE1MjMzNDA3OTIsIm5iZiI6MTUyMzM0MDc4N30.ekE1vhSBbuaGoGX7m06HNNsBlH4A61apjssA2rINFeg";
        ss = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MjMzNDA5NjksInN1YiI6IntcInB3ZFwiOjEyMzQ1LFwidXNlcklkXCI6MTAwMX0iLCJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJleHAiOjE1MjMzNDI3NjksIm5iZiI6MTUyMzM0MDk2OX0.xJh-LaH85__dyGaegcYt-aiRJ_6Od5yU8fheFH0V43Y";
        Map<String, Object> map1 = verifyToken(ss);

        System.err.println("----" + JSON.toJSONString(map1));
    }
}
