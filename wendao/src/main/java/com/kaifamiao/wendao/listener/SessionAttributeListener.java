package com.kaifamiao.wendao.listener;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.utils.Constants;
import org.tinylog.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {
    private static HashMap<HttpSession,Customer> list = new HashMap<>();
    // 向当前请求关联的会话对象中成功添加新属性时调用此方法
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String name = event.getName();
        Object value = event.getValue();
        ServletContext app=session.getServletContext();
        if (Constants.CUSTOMER_LOGINED.getName().equals(name)&&value instanceof Customer){
            Customer customer = (Customer) session.getAttribute(Constants.CUSTOMER_LOGINED.getName());
            list.put(session,customer);
        }
        app.setAttribute("onlineCustomer",list.size());
    }

    // 成功替换当前请求关联的会话对象中的已有属性时调用此方法
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String name = event.getName();
        Object value = event.getValue();
        ServletContext app=session.getServletContext();
        if (Constants.CUSTOMER_LOGINED.getName().equals(name)&&value instanceof Customer){
            Customer customer = (Customer) session.getAttribute(Constants.CUSTOMER_LOGINED.getName());
            list.put(session,customer);
        }
        app.setAttribute("onlineCustomer",list.size());
    }

    // 从当前请求关联的会话对象中成功删除属性时调用此方法
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        String name = event.getName();
        Object value = event.getValue();
        ServletContext app=session.getServletContext();
        if (Constants.CUSTOMER_LOGINED.getName().equals(name)&&value instanceof Customer){
            list.remove(session);
        }
        app.setAttribute("onlineCustomer",list.size());
    }

}
