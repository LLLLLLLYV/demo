package com.example.demoamqp.WebIntercepterConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class UrlIntercepter implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(UrlIntercepter.class);

    //请求前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*StringBuffer requestURL = request.getRequestURL();
        String contextPath = request.getContextPath();
        logger.info("================requestURL:"+requestURL.toString());
        logger.info("================contextPath:"+contextPath);*/
        return true;
    }

    //请求时
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    //请求后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
