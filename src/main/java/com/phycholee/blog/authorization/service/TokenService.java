package com.phycholee.blog.authorization.service;

import com.phycholee.blog.authorization.model.TokenModel;

/**
 * Created by PhychoLee on 2017/4/7 22:35.
 * Description: Token管理
 */
public interface TokenService {

    /**
     * 创建token对象
     * @param userId
     * @return
     */
    TokenModel createToken(Integer userId);

    /**
     * 检查token是否有效
     * @param token
     * @return
     */
    boolean checkToken(TokenModel model);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    TokenModel getToken(String authentication);

    /**
     * 删除token
     * @param userId
     */
    void deleteToken(Integer userId);
}
