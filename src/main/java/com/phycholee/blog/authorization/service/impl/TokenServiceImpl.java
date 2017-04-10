package com.phycholee.blog.authorization.service.impl;

import com.phycholee.blog.authorization.config.Constants;
import com.phycholee.blog.authorization.model.TokenModel;
import com.phycholee.blog.authorization.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by PhychoLee on 2017/4/7 22:46.
 * Description: token管理实现类
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public TokenModel createToken(Integer userId) {

        String str1 = UUID.randomUUID().toString().replace("-", "");
        String str2 = UUID.randomUUID().toString().replace("-", "");
        String token = str1 + str2;
        TokenModel tokenModel = new TokenModel(userId, token);

        redis.boundValueOps(userId.toString()).set(token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);

        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null)
            return false;

        String token = redis.boundValueOps(model.getUserId().toString()).get();

        if (token == null || !token.equals(model.getToken()))
            return false;

        //延长token时长
        redis.boundValueOps(model.getUserId().toString()).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    @Override
    public TokenModel getToken(String authentication) {
        return null;
    }

    @Override
    public void deleteToken(Integer userId) {
        redis.delete(userId+"");
    }
}
