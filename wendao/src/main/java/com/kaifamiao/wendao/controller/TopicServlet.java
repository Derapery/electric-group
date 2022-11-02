package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.dao.CategoryDao;
import com.kaifamiao.wendao.entity.Category;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.service.TopicLikeService;
import com.kaifamiao.wendao.service.TopicService;
import com.kaifamiao.wendao.utils.Paging;
import com.kaifamiao.wendao.utils.Praise;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.tinylog.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("topic/*")
public class TopicServlet extends HttpServlet {
    private TopicService topicService;
    private TopicLikeService topicLikeService;
    @Override
    public void init() throws ServletException {
        topicService=new TopicService();
        topicLikeService=new TopicLikeService();
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       String method=req.getMethod();
       String uri=req.getRequestURI();
       if("GET".equals(method) && uri.endsWith("/list")){
           this.listPage(req,resp);
           return;
       }
       if("GET".equals(method) && uri.endsWith("/publish")){
            this.publishPage(req,resp);
            return;
       }
       if("POST".equals(method) && uri.endsWith("/publish")){
           this.publishAction(req,resp);
           return;
       }
       if("GET".equals(method) && uri.endsWith("/mine")){
           this.myTopic(req,resp);
           return;
       }
       //"GET" "/DETAIL"
       if("GET".equals(method) && uri.endsWith("/detail")){
           this.detail(req,resp);
           return;
       }
       //"GET" "publishExplain"
        if("GET".equals(method) && uri.endsWith("/publishExplain")){
            this.publishExplain(req,resp);
            return;
        }
        //"GET" "search"
        if("GET".equals(method) && uri.endsWith("/search")){
            this.search(req,resp);
            return;
        }
        //"GET" "thumbsState"
        if("GET".equals(method) && uri.endsWith("/thumbsState")){
            this.thumbsState(req,resp);
            return;
        }
    }
    private Map<String,Object> havPaging(HttpServletRequest request){
        //默认的显示的话题数
        Integer DEFAULT_SIZE=5;
        //默认的当前页数
        Integer DEFAULT_CURRENT=1;
        HttpSession session=request.getSession();
        //获取会话中的SIZE
        Integer size=(Integer)session.getAttribute("size");
        //判断如果获取到的是null，那么就是默认的
        size= size==null? DEFAULT_SIZE:size;
        //获取请求中的size
        String realSize=request.getParameter("size");
        if(!StringUtils.isEmpty(realSize) && !StringUtils.isBlank(realSize)){
            //如果请求中的size不为空，那么取请求中的size
            size=Integer.valueOf(realSize);
            session.setAttribute("size",size);
        }
        String currentPage=request.getParameter("current");
        Integer current=DEFAULT_CURRENT;
        if(!StringUtils.isBlank(currentPage) && !StringUtils.isEmpty(currentPage)){
            current=Integer.valueOf(currentPage);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("size",size);
        map.put("current",current);
        return map;
    }
    //显示话题列表
    private void listPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri=req.getRequestURI();
        req.setAttribute("path",uri);
        Map<String,Object> map=havPaging(req);
        HttpSession session=req.getSession();
        Customer customer=(Customer) session.getAttribute("customer");
        //根据分页查询来得到话题列表
        Paging<Topic> paging =topicService.findPage((Integer)map.get("size"),(Integer)map.get("current"),customer,1);
        session.setAttribute("paging",paging);
        String path="/WEB-INF/pages/topic/list.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }
    //”GET“ "publish"
    private void publishPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("categoryList")==null){
            List<Category> categoryList = topicService.loadCategoryList();
            session.setAttribute("categoryList",categoryList);
        }
        String path="/WEB-INF/pages/topic/publish.jsp";
        RequestDispatcher rd=req.getRequestDispatcher(path);
        rd.forward(req,resp);
    }
    //对发布内容进行数据较验
    private boolean validatePublish(HttpServletRequest req){
        HttpSession session=req.getSession();
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String category = req.getParameter("category");
        if (!StringUtils.isEmpty(category) && StringUtils.isBlank(content) &&StringUtils.isEmpty(content)){
            Category c = topicService.loadCategory(Long.parseLong(category));
            req.setAttribute("category",c);
            return false;
        }
            if(StringUtils.isEmpty(title) || StringUtils.isBlank(title)){
            session.setAttribute("message","发布话题题目不能为空！");
            session.setAttribute("content",content);
            return false;
        }
        if(StringUtils.isEmpty(content) || StringUtils.isBlank(content)){
            session.setAttribute("message","发布话题内容不能为空！");
            session.setAttribute("title",title);
            return false;
        }
        return true;
    }
    //"POST" "publish"
    private void publishAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        if(validatePublish(req)){
            Topic topic= new Topic();
            try {
                BeanUtils.populate(topic,req.getParameterMap());
            } catch (Exception e) {
                session.setAttribute("message","数据转换发生异常，发布失败！");
                resp.sendRedirect(req.getContextPath()+"/topic/publish");
            }
            topic.setAuthor(((Customer)session.getAttribute("customer")));
            String addr = req.getRemoteAddr();
            topic.setPublishAddress(addr);
            String categoryID=req.getParameter("categoryID");
            System.out.println(categoryID);
            topic.setCategory_id(Long.valueOf(categoryID));
            if(req.getParameter("category")==null&&req.getParameter("categoryID")==null){
                session.setAttribute("message", "未分类");
                resp.sendRedirect(req.getContextPath()+"/topic/publish");
                return;
            }
            try {
                if (topicService.save(topic)) {
                    resp.sendRedirect(req.getContextPath() + "/topic/list");
                    return;
                }
                session.setAttribute("message", "话题发布失败");
                session.setAttribute("title", topic.getTitle());
                session.setAttribute("content", topic.getContent());
            } catch (Exception e){
                Logger.error(e);
                session.setAttribute("message", "话题发布失败");
                session.setAttribute("title", topic.getTitle());
                session.setAttribute("content", topic.getContent());
            }
        }
        String path="/WEB-INF/pages/topic/publish.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }
    private void myTopic(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        HttpSession session=req.getSession();
        Customer customer=(Customer) session.getAttribute("customer");
        if(customer==null){
            session.setAttribute("message","获取话题参数失败！");
            resp.sendRedirect(req.getContextPath()+"/topic/list");
        }
        Paging<Topic> paging=topicService.findPage((Integer)map.get("size"),(Integer)map.get("current"),customer,2);
        session.setAttribute("paging",paging);
        String path="/WEB-INF/pages/topic/list.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }
    private void detail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        String id=req.getParameter("id");
        Customer customer=(Customer) session.getAttribute("customer");
        Long customer_id;
        if(customer==null){
            customer_id=null;
        }else{
            customer_id=customer.getId();
        }
        Topic topic=topicService.find(Long.valueOf(id),customer_id);
        session.setAttribute("topic",topic);
        String path="/WEB-INF/pages/topic/detail.jsp";
        RequestDispatcher BD=req.getRequestDispatcher(path);
        BD.forward(req,resp);
    }
    private void publishExplain(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Customer customer=(Customer) session.getAttribute("customer");
        if(customer ==null){
            session.setAttribute("message","请登陆后发表解答");
            resp.sendRedirect(req.getContextPath()+"/customer/sign/in");
        }
        String id=req.getParameter("id");
        Topic topic=topicService.find(Long.valueOf(id),customer.getId());
        session.setAttribute("topic",topic);
        String path="/WEB-INF/pages/topic/explainPublish.jsp";
        RequestDispatcher RB=req.getRequestDispatcher(path);
        RB.forward(req,resp);
    }
    //"GET" "search"
    private void search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        String keyWord=(String)session.getAttribute("key");
        String key=req.getParameter("keyworkd");
        if(!StringUtils.isEmpty(key)||!StringUtils.isBlank(key)){
            keyWord=key;
            session.setAttribute("key",keyWord);
        }
        Map<String,Object> map=havPaging(req);
        Paging<Topic> paging=topicService.findPageLike(keyWord,(Integer)map.get("size"),(Integer)map.get("current"));
        session.setAttribute("paging",paging);
        String path="/WEB-INF/pages/topic/list.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }

    public void thumbsState(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        Integer size=Integer.valueOf(req.getParameter("size"));
        String current=req.getParameter("current");
        String topic=req.getParameter("topic_id");
        Long topic_id =Long.valueOf(topic);
        String praise1=req.getParameter("praise");
        Integer state=Integer.valueOf(praise1.trim());
        Praise praise=topicLikeService.find(customer.getId(),topic_id);
        if(praise != null){
            topicLikeService.delete(customer.getId(),topic_id);
        }else{
            topicLikeService.save(customer.getId(), topic_id,state);
        }
        resp.sendRedirect(req.getContextPath()+"/topic/list?size="+size+"&current="+Integer.parseInt(current.trim()));
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
