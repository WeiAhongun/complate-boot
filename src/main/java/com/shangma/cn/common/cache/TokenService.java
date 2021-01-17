package com.shangma.cn.common.cache;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shangma.cn.common.constant.RedisKeyConstant;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.common.utils.JsonUtils;
import com.shangma.cn.common.utils.ServletUtils;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.LoginAdmin;
import com.shangma.cn.exception.LoginTimeExprieException;
import com.shangma.cn.exception.TokenVerifyAndAuthorizationException;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenService {
    //Token秘钥
    public static final String secret = "alkshdzv8qhwknjnvbosaadqw";
    //登录失效时间
    public static final long exprieTime = 1000 * 60 * 60 * 24 * 7;

    /**
     * 生成登入用户的Token
     */
    public String createToken(Admin admin) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        LoginAdmin loginAdmin = new LoginAdmin();
        loginAdmin.setAdmin(admin);
        loginAdmin.setUuid(UUID.randomUUID().toString());
        loginAdmin.setLoginTime(System.currentTimeMillis());
        loginAdmin.setExpireTime(System.currentTimeMillis() + exprieTime);
        //登入用户的信息存入Redis
        RedisCacheUtils.setRedisCache(RedisKeyConstant.LOGIN_ADMIN + loginAdmin.getUuid(), JsonUtils.objToStr(loginAdmin));
        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("uuid", loginAdmin.getUuid())
                .sign(algorithm);
        return token;
    }

    /**
     * 获得前端Token
     */
    public String getTokenStrFromRequest() {
        String authorization = ServletUtils.getHttpServletRequest().getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            try {
                return authorization.split(" ")[1];
            } catch (Exception e) {
                throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
            }
        }
        throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
    }

    /**
     * 解析前端传来的Token
     */
    public DecodedJWT verifyToken() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(getTokenStrFromRequest());
            return jwt;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
        }
    }

    /**
     * 获得Token中的uuid
     */
    public String getUuidFromToken() {
        DecodedJWT jwt = verifyToken();
        if (jwt != null) {
            String uuid = jwt.getClaim("uuid").asString();
            return uuid;
        }
        throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
    }

    /**
     * 获得登录用户
     */
    public LoginAdmin getLoginAdmin() {
        String uuidFromToken = getUuidFromToken();
        if (StringUtils.isEmpty(uuidFromToken)) {
            throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
        }
        String redisCache = RedisCacheUtils.getRedisCache(RedisKeyConstant.LOGIN_ADMIN + uuidFromToken);
        return JsonUtils.strToObj(redisCache, LoginAdmin.class);
    }

    /**
     * 获得登录用户id
     */
    public Long getLoginAdminId() {
        LoginAdmin loginAdmin = getLoginAdmin();
        return loginAdmin.getAdmin().getId();
    }

    /**
     * 刷新登入时间
     */
    public void refreshLoginTime() {
        LoginAdmin loginAdmin = getLoginAdmin();
        long now = System.currentTimeMillis();
        long oldLoginTime = loginAdmin.getLoginTime();
        //上一次请求距现在超过了5min
        if ((now - oldLoginTime) / 1000 / 60 > 20) {
            throw new LoginTimeExprieException(AxiosStatus.LOGINTIME_EXPRIE);
        }
        //当前请求时间超过了登录过期时间
        if (now > loginAdmin.getExpireTime()) {
            throw new LoginTimeExprieException(AxiosStatus.LOGINTIME_EXPRIE);
        }
        //当前请求距离过期时间小于半小时
        if ((now - loginAdmin.getExpireTime()) / 1000 / 60 < 30) {
            loginAdmin.setExpireTime(now + exprieTime);
        }
        loginAdmin.setLoginTime(now);
        RedisCacheUtils.setRedisCache(RedisKeyConstant.LOGIN_ADMIN+loginAdmin.getUuid(), JsonUtils.objToStr(loginAdmin));
    }

    /**
     * 解析和认证Token
     */
    public boolean verifyAndAuthorizationToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
        }
        if (jwt != null) {
            refreshLoginTime();
            return true;
        }
        throw new TokenVerifyAndAuthorizationException(AxiosStatus.TOKEN_ERROR);
    }
}
































