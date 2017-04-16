package com.phycholee.blog.authorization.interceptor;

import com.phycholee.blog.authorization.config.Constants;
import com.phycholee.blog.authorization.model.TokenModel;
import com.phycholee.blog.authorization.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by PhychoLee on 2017/4/7 22:24.
 * Description: 权限验证拦截器
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从header中得到token
        String token = request.getHeader(Constants.TOKEN);

        Object userIdO = request.getSession().getAttribute(Constants.CURRENT_USER_ID);
        Integer userId = null;
        if (userIdO != null){
            userId = (Integer) userIdO;
        }

        TokenModel tokenModel = new TokenModel(userId, token);

        boolean checkToken = tokenService.checkToken(tokenModel);

        if (checkToken){
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
