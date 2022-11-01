package com.kaifamiao.wendao.filter;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class OnlineFilter implements Filter {

    private static HashMap<Customer,HttpSession> list = new HashMap<>();
    private Set<String> urls;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        urls = Set.of("/sign/out");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpSession session = req.getSession();
        ServletContext app=session.getServletContext();
        String uri = req.getRequestURI();
        Customer customer = (Customer) session.getAttribute(Constants.CUSTOMER_LOGINED.getName());
        if (customer!=null){
            list.put(customer,session);
            if(urls.contains(uri)) {
                list.remove(customer);
            }
        }
        app.setAttribute("onlineCustomer",list.size());
        filterChain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
