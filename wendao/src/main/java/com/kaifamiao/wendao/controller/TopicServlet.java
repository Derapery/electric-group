package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.dao.CategoryDao;
import com.kaifamiao.wendao.entity.Category;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.service.AttentionService;
import com.kaifamiao.wendao.service.CustomerService;
import com.kaifamiao.wendao.service.TopicLikeService;
import com.kaifamiao.wendao.service.TopicService;
import com.kaifamiao.wendao.utils.Constants;
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
    private AttentionService attentionService;
    private CustomerService customerService;
    @Override
    public void init() throws ServletException {
        topicService=new TopicService();
        topicLikeService=new TopicLikeService();
        attentionService=new AttentionService();
        customerService=new CustomerService();
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
        //"GET" "delete"
        if("GET".equals(method) && uri.endsWith("/delete")){
            this.delete(req,resp);
            return;
        }
        //???GET??? ???likeTopic???
        if("GET".equals(method) && uri.endsWith("/likeTopic")){
            this.likeTopic(req,resp);
            return;
        }

        //"GET" ???classify???
        if("GET".equals(method) && uri.endsWith("/classify")){
            this.classify(req,resp);
            return;
        }
    }
    private Map<String,Object> havPaging(HttpServletRequest request){
        //???????????????????????????
        Integer DEFAULT_SIZE=5;
        //?????????????????????
        Integer DEFAULT_CURRENT=1;
        HttpSession session=request.getSession();
        //??????????????????SIZE
        Integer size=(Integer)session.getAttribute("size");
        //???????????????????????????null????????????????????????
        size= size==null? DEFAULT_SIZE:size;
        //??????????????????size
        String realSize=request.getParameter("size");
        if(!StringUtils.isEmpty(realSize) && !StringUtils.isBlank(realSize)){
            //??????????????????size?????????????????????????????????size
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
    //??????????????????
    private void listPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri=req.getRequestURI();
        req.setAttribute("path",uri);
        Map<String,Object> map=havPaging(req);
        HttpSession session=req.getSession();
        Customer customer=(Customer) session.getAttribute("customer");
        //???????????????????????????????????????
        Paging<Topic> paging =topicService.findPage((Integer)map.get("size"),(Integer)map.get("current"),customer,1);
        List<Topic> hotTopicList=topicService.hotTopic();
        for (Topic c:hotTopicList) {
            if(c.getTitle().length()>10){
                c.setTitle(c.getTitle().substring(0,10)+"...");
            }
        }
        List<Category> categories=customerService.getCategory();
        session.setAttribute("categoryList",categories);
        session.setAttribute("hotTopicList",hotTopicList);
        session.setAttribute("paging",paging);
        String path="/WEB-INF/pages/topic/list.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }
    //???GET??? "publish"
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
    //?????????????????????????????????
    private boolean validatePublish(HttpServletRequest req){
        HttpSession session=req.getSession();
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String category = req.getParameter("category");
        if (!StringUtils.isEmpty(category) && StringUtils.isBlank(content) &&StringUtils.isEmpty(content)){
            Category c = topicService.loadCategory(Long.parseLong(category));
            req.setAttribute("category",c);
            req.setAttribute("title",title);
            return false;
        }
            if(StringUtils.isEmpty(title) || StringUtils.isBlank(title)){
            session.setAttribute("message","?????????????????????????????????");
            session.setAttribute("content",content);
            return false;
        }
        if(StringUtils.isEmpty(content) || StringUtils.isBlank(content)){
            session.setAttribute("message","?????????????????????????????????");
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
                session.setAttribute("message","??????????????????????????????????????????");
                resp.sendRedirect(req.getContextPath()+"/topic/publish");
            }
            Customer customer = (Customer) session.getAttribute("customer");
            topic.setAuthor(customer);
            String addr = req.getRemoteAddr();
            topic.setPublishAddress(addr);
            String categoryID=req.getParameter("categoryID");
            Long StrcategoryID = Long.valueOf(categoryID);
            topic.setCategory_id(StrcategoryID);
            if(req.getParameter("category")==null&&req.getParameter("categoryID")==null){
                session.setAttribute("message", "?????????");
                resp.sendRedirect(req.getContextPath()+"/topic/publish");
                return;
            }
            if (customer.getManagement()<= Constants.Manager_Level_3.getValue()){
                session.setAttribute("message","???????????????????????????");
                resp.sendRedirect(req.getContextPath()+"/topic/list");
                return;
            }
            try {
                if (topicService.save(topic)) {
                    resp.sendRedirect(req.getContextPath() + "/topic/list");
                    return;
                }
                session.setAttribute("message", "??????????????????");
                session.setAttribute("title", topic.getTitle());
                session.setAttribute("content", topic.getContent());
            } catch (Exception e){
                session.setAttribute("message", "??????????????????");
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
            session.setAttribute("message","???????????????????????????");
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
            session.setAttribute("message","????????????????????????");
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
    public void delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        Integer size=Integer.valueOf(req.getParameter("size"));
        String current=req.getParameter("current");
        String topic=req.getParameter("topicId");
        Long topic_id =Long.valueOf(topic);
        //????????????
        topicService.delete(customer.getId(),topic_id);
        resp.sendRedirect(req.getContextPath()+"/topic/list?size="+size+"&current="+Integer.parseInt(current.trim()));

    }
    public void likeTopic(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Long customer_id=Long.valueOf(req.getParameter("id"));
        Customer cus=customerService.find(customer_id);
        req.setAttribute("customer",cus);
        System.out.println(cus.getLikeList().get(0).getTitle());
        session.setAttribute("state",1);
        String path="/WEB-INF/pages/customer/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }
    private void classify(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        HttpSession session=req.getSession();
        Long ID= Long.valueOf(req.getParameter("id"));
        Customer customer=(Customer) session.getAttribute("customer");
        Long customer_id;
        if(customer==null){
            customer_id=null;
        }else{
            customer_id=customer.getId();
        }
        Paging<Topic> paging=topicService.search((Integer)map.get("size"),(Integer)map.get("current"),ID,customer_id);
        session.setAttribute("paging",paging);
        String path="/WEB-INF/pages/topic/list.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
