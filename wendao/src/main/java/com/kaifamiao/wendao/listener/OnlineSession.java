package com.kaifamiao.wendao.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class OnlineSession implements HttpSessionListener {

    private static Integer onlineCount;


    @Override
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext app=event.getSession().getServletContext();
        onlineCount=(Integer) app.getAttribute("onlineCount");
        HttpSession session=event.getSession();
        System.out.println(onlineCount);
        if(onlineCount==null) {
            onlineCount=0;
        }
        app.setAttribute("onlineCount",onlineCount+1);
    }
    // 每成功销毁一个HttpSession实例就调用一次该方法
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext app = event.getSession().getServletContext();
        onlineCount=(Integer) app.getAttribute("onlineCount");
        onlineCount= onlineCount-1;
        System.out.println(onlineCount);
        app.setAttribute("onlineCount",onlineCount);
    }

}
