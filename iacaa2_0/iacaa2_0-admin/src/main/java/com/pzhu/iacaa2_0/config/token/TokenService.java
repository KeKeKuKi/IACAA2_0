package com.pzhu.iacaa2_0.config.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pzhu.iacaa2_0.entity.User;
import com.pzhu.iacaa2_0.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    IUserService userService;
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    private static final String EXPIRE = "sjhdf%*&#%^Dhdsg82645sfk";

    public String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getId()))
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(EXPIRE));
        return token;
    }


    public boolean checkToken(String token){
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(EXPIRE)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token已过期！");
        }
        return false;
    }

    public User getUser(String token){
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return null;
        }
        User user = userService.getById(userId);
        return user;
    }

}
