package com.phycholee.blog.authorization.interceptor;

import com.phycholee.blog.authorization.config.Constants;
import com.phycholee.blog.authorization.model.TokenModel;
import com.phycholee.blog.authorization.service.TokenService;
import com.phycholee.blog.model.Admin;
import com.phycholee.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从header中得到token
        String token = request.getHeader(Constants.TOKEN);

        if (StringUtils.isEmpty(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //获取token和userId
        String[] split = token.split("-");
        if (split.length <2){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        token = split[0];
        Integer userId = Integer.parseInt(split[1]);

        TokenModel tokenModel = new TokenModel(userId, token);

        boolean checkToken = tokenService.checkToken(tokenModel);

        if (checkToken){
            Admin admin = adminService.findById(userId);
            request.getSession().setAttribute(Constants.CURRENT_USER, admin);

            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
