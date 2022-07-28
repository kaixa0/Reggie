package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 检查用户是否完成登录
 * @Author: Li jinxiao
 * @Date: 2022/7/5 11:10
 */

@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();

        log.info("拦截到请求：{}",requestURI);

        String [] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        boolean check = check(requestURI, urls);

        if (check) {
            log.info("本次不需要处理的请求：{}", requestURI);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //判断登录状态，若已登陆，则直接放行 员工和管理
        if(httpServletRequest.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id为：{}", httpServletRequest.getSession().getAttribute("employee"));
            long id = Thread.currentThread().getId();
            log.info("doFilter id: {}", id);
            BaseContext.setThreadLocal((Long) httpServletRequest.getSession().getAttribute("employee"));
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //判断登录状态，若已登陆，则直接放行  用户
        if(httpServletRequest.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户id为：{}", httpServletRequest.getSession().getAttribute("user"));
            long id = Thread.currentThread().getId();
            log.info("doFilter id: {}", id);
            BaseContext.setThreadLocal((Long) httpServletRequest.getSession().getAttribute("user"));
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        log.info("用户未登录");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }


    public boolean check(String requestURI,String [] strings){
        for (String string : strings) {
            boolean match = PATH_MATCHER.match(string, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
