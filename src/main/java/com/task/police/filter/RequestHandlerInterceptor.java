package com.task.police.filter;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * H.L.Wan 595710104@qq.com
 * Suzhou
 */
@Service
public class RequestHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String contextPath=httpServletRequest.getContextPath();
        String servletPath=httpServletRequest.getServletPath();
        httpServletRequest.setAttribute("_servletPath",servletPath);
        servletPath=servletPath+"/dashboard";
        String uri=httpServletRequest.getRequestURI();
        uri=uri.substring(contextPath.length()+servletPath.length());
        if(uri.length()>1){
            int index=uri.indexOf("/",1);
            if(index!=-1){
                uri=uri.substring(1,index);
            }
        }
        httpServletRequest.setAttribute("_path",uri);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
