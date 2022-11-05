package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.service.ExplainLikeService;
import com.kaifamiao.wendao.service.ExplainService;
import com.kaifamiao.wendao.service.TopicService;
import com.kaifamiao.wendao.utils.LikeExplain;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("explain/*")
public class ExplainServlet extends HttpServlet {
    private ExplainService explainService;
    private ExplainLikeService explainLikeService;
    private TopicService topicService;
    @Override
    public void init() throws ServletException {
        explainService=new ExplainService();
        explainLikeService=new ExplainLikeService();
        topicService=new TopicService();
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
        //"GET" "thumbsState"
        if("GET".equals(method) && uri.endsWith("/thumbsState")){
            this.thumbsState(req,resp);
            return;
        }
        //“GET” “/delete”
        if("GET".equals(method) && uri.endsWith("/delete")){
            this.delete(req,resp);
            return;
        }
        //“get” "/mine"
        if("GET".equals(method) && uri.endsWith("/mine")){
            this.myExplain(req,resp);
            return;
        }
    }
    public void delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id= (String)req.getParameter("id");
        explainService.delete(Long.valueOf(id));
        String topicID=req.getParameter("topicId");
        resp.sendRedirect(req.getContextPath()+"/topic/detail?id="+topicID);
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
    public void thumbsState(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        String id=req.getParameter("id");
        String praise=req.getParameter("state");
        String topic=req.getParameter("topic_id");
        Long topicID=Long.valueOf(topic.trim());
        Integer state=Integer.valueOf(praise.trim());
        Long ID=Long.valueOf(id.trim());
        Customer customer = (Customer) session.getAttribute("customer");
        LikeExplain likeExplain=explainLikeService.find(customer.getId(), ID);
        Explain explain=explainService.find(ID);
        Integer thumb_up=explain.getPraise();
        Integer thumb_down=explain.getDespise();
        if(likeExplain != null){
            Integer tage=likeExplain.getState();
             if(tage==1){
                 explainService.modify(thumb_up-1,ID,1);
             }else{
                 explainService.modify(thumb_down-1,ID,0);
             }
            explainLikeService.delete(customer.getId(),ID);
        }else{
            explainLikeService.save(customer.getId(), ID,state);
            if(state==1){
                explainService.modify(thumb_up+1,ID,1);
            }else{
                explainService.modify(thumb_down+1,ID,0);
            }
        }
        resp.sendRedirect(req.getContextPath()+"/topic/detail?id="+topicID);
    }
    private void myExplain(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session =req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        List<Explain> explains =explainService.explainMy(customer.getId());
        session.setAttribute("explains",explains);
        String path="/WEB-INF/pages/topic/myExplain.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }
}
