package com.kaifamiao.wendao.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class ManagerFilter implements Filter {
    private Set<String> urls;

    @Override
    public void init(FilterConfig config) throws ServletException {
        urls = Set.of("/manager/list","/manager/publish","/manager/badlog","/manager/top","/manager/badlogOne","/manager/edit","/manager/editinfo","/manager/changePwd","/manager/changemanager","/manager/quckChange");
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String uri = request.getRequestURI();
        // 确定当前被访问的资源是否属于需要被拦截
        if(urls.contains(uri)) {
            HttpSession session = request.getSession();
            Object o = session.getAttribute("manager");
            if( o == null ) {
                session.setAttribute("message", "您尚未登录,不能进行该操作");
                response.sendRedirect(request.getContextPath() + "/customer/sign/in");
                return;
            }
        }
        // 继续向后传递请求和响应对象
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
