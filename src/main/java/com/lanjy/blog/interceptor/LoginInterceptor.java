package com.lanjy.blog.interceptor;

import com.lanjy.blog.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.interceptor
 * @类描述： 过滤器
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginInterceptor拦截器____开始拦截");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            //用户没有登录，请求重定向
            response.sendRedirect("/");
            log.info("LoginInterceptor拦截器____您还没有登录");
            return false;
        }
        return true;
    }
}
