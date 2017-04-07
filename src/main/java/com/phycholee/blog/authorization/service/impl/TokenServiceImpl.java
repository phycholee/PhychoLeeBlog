package com.phycholee.blog.authorization.service.impl;

import com.phycholee.blog.authorization.model.TokenModel;
import com.phycholee.blog.authorization.service.TokenService;

import java.util.Date;
import java.util.UUID;

/**
 * Created by PhychoLee on 2017/4/7 22:46.
 * Description: token管理实现类
 */

public class TokenServiceImpl implements TokenService {
    @Override
    public TokenModel createToken(Integer userId) {

        String token = UUID.fromString(userId.toString() + new Date()).toString().replace("-", "");
        TokenModel tokenModel = new TokenModel(userId, token);



        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel token) {
        return false;
    }

    @Override
    public TokenModel getToken(String authentication) {
        return null;
    }

    @Override
    public void deleteToken(String userId) {

    }
}
