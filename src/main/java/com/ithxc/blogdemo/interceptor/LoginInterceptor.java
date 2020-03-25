package com.ithxc.blogdemo.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hxc
 * @create 2020-03-09 4:23
 * 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    //预处理，在请求未到达目的地之前预处理
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getSession().getAttribute("user") ==null){
            //当user为空即未登录时，重定向到登录页面
            response.sendRedirect("/admin");
            return false;
        }
        //登录了的话就继续执行
        return true;
    }
}
