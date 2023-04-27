package com.starry.utils;

import com.starry.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JWTUtil {


    /**
     * 主题
     */
    private static final String SUBJECT = "shortLink";

    /**
     * 加密密钥
     */
    private static final String SECRET = "shortLink123@";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "cloud-link";


    /**
     * token过期时间，7天
     */
    private static final long EXPIRED = 1000 * 60 * 60 * 24 * 7;


    /**
     * 生成token
     *
     * @param loginUser
     * @return
     */
    public static String genJsonWebTokne(LoginUser loginUser) {

        if (loginUser == null) {
            throw new NullPointerException("对象为空");
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                //配置payload
                .claim("head_img", loginUser.getHeadImg())
                .claim("account_no", loginUser.getAccountNo())
                .claim("username", loginUser.getUsername())
                .claim("mail", loginUser.getMail())
                .claim("phone", loginUser.getPhone())
                .claim("auth", loginUser.getAuth())
                .setIssuedAt(new Date())
                .setExpiration(new Date(CommonUtil.getCurrentTimestamp() + EXPIRED))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        return token;
    }


    /**
     * 解密jwt
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {

        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
            return claims;
        } catch (Exception e) {

            log.error("jwt 解密失败");
            return null;
        }

    }


}
