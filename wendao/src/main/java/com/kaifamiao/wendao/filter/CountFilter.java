package com.kaifamiao.wendao.filter;

import com.kaifamiao.wendao.dao.TopicsDao;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountFilter implements Filter {

    private String uri = "/topic/detail";
    private TopicsDao topicsDao;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        topicsDao = new TopicsDao();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        topicsDao = new TopicsDao();
        String url = req.getRequestURI();
        if (url.contains(uri)){
            topicsDao.addcount(Long.parseLong(request.getParameter("id")));
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
