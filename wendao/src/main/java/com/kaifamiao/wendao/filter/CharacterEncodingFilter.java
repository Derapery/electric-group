package com.kaifamiao.wendao.filter;

import org.tinylog.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.Charset;

public class CharacterEncodingFilter implements Filter {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String CHARSET_INIT_PARAM_NAME = "encoding";
    private String charsetName;
    private String filterName;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName=filterConfig.getFilterName();
        Logger.trace("初始化过滤器{}",filterName);
        charsetName= filterConfig.getInitParameter(CHARSET_INIT_PARAM_NAME);
        charsetName=charsetName.isEmpty()||charsetName.isBlank()?DEFAULT_CHARSET:charsetName;
        charsetName= Charset.isSupported(charsetName)?charsetName:DEFAULT_CHARSET;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Logger.trace("过滤器{}正在执行过滤",filterName);
        servletRequest.setCharacterEncoding(charsetName);
        servletResponse.setCharacterEncoding(charsetName);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Logger.trace("释放过滤器{}占用的资源",filterName);
    }
}
