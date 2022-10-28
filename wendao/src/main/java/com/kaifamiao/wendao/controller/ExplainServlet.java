package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.service.ExplainService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("explain/*")
public class ExplainServlet extends HttpServlet {
    private ExplainService explainService;
    @Override
    public void init() throws ServletException {
        explainService=new ExplainService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method= req.getMethod();
        String uri=req.getRequestURI();
        if("POST".equals(method) && uri.endsWith("/publish")){
            this.publish(req,resp);
            return;
        }
    }

    private void publish(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        String topicID=req.getParameter("topicId");
        if(StringUtils.isBlank(topicID)||StringUtils.isEmpty(topicID)){
            session.setAttribute("message","获取话题参数ID失败!");
            resp.sendRedirect(req.getContextPath()+"/topic/list");
            return;
        }
        String content=req.getParameter("content");
        if(StringUtils.isEmpty(content)||StringUtils.isBlank(content)){
            session.setAttribute("message","请输入评论内容");
            resp.sendRedirect(req.getContextPath()+"/topic/publishExplain?id="+topicID);
            return;
        }
        Explain explain=new Explain();
        Topic topic=new Topic();
        topic.setId(Long.valueOf(topicID));
        explain.setTopic(topic);
        explain.setContent(content);
        explain.setPublishAddress(req.getRemoteAddr());
        Customer customer=(Customer) session.getAttribute("customer");
        explain.setAuthor(customer);
        explainService.save(explain);
        resp.sendRedirect(req.getContextPath()+"/topic/detail?id="+topicID);
    }
}
