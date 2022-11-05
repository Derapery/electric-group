package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.entity.*;
import com.kaifamiao.wendao.service.ManagerService;
import com.kaifamiao.wendao.utils.Constants;
import com.kaifamiao.wendao.utils.Paging;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/manager/*")
public class ManagerServlet extends HttpServlet {
    private ManagerService managerService;

    @Override
    public void init() throws ServletException {
        managerService = new ManagerService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method = req.getMethod();
        String uri = req.getRequestURI();
        if ("GET".equals(method) && uri.endsWith("/list")) {
            this.mangerPage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/operating")) {
            this.operatingPage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/topic")) {
            this.topicPage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/badlog")) {
            this.badlogPage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/top")) {
            this.topAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/badlogOne")) {
            this.badlogOnePage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/category")) {
            this.changeCategoryPage(req, resp);
            return;
        }
        if ("POST".equals(method) && uri.endsWith("/category")) {
            this.changeCategoryAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/edit")) {
            this.editTopicPage(req, resp);
            return;
        }
        if ("POST".equals(method) && uri.endsWith("/edit")) {
            this.editTopicAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/editinfo")) {
            this.changeInfoPage(req, resp);
            return;
        }
        if ("POST".equals(method) && uri.endsWith("/editinfo")) {
            this.changeInfoAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/deleteTopic")) {
            this.deleteTopicAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/changePwd")) {
            this.changePwdPage(req, resp);
            return;
        }
        if ("POST".equals(method) && uri.endsWith("/changePwd")) {
            this.changePwdAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/changemanager")) {
            this.changeMaPage(req, resp);
            return;
        }
        if ("POST".equals(method) && uri.endsWith("/changemanager")) {
            this.changeMaAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/quckChange")) {
            this.quckChangeAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/categoryList")) {
            this.categoryListPage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/addCategory")) {
            this.addCategoryPage(req, resp);
            return;
        }
        if ("POST".equals(method) && uri.endsWith("/addCategory")) {
            this.addCategoryAction(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/changeCategory")) {
            this.CategoryPage(req, resp);
            return;
        }
        if ("GET".equals(method) && uri.endsWith("/operation")) {
            this.operationAction(req, resp);
            return;
        }
    }

    private void operationAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Customer manager = (Customer) session.getAttribute("manager");
        Integer management = manager.getManagement();
        if(!management.equals(Constants.Manager_Level_2.getValue())){
            session.setAttribute("message","权限不足");
            resp.sendRedirect(req.getContextPath()+"/manager/topic");
            return;
        }
        else {
            String op = req.getParameter("operation_id");
            if (op==null){
                session.setAttribute("message","未获取到记录");
                resp.sendRedirect(req.getContextPath()+"/manager/topic");
                return;
            }
            Operating operating =  managerService.findOperating(Long.parseLong(op));
            managerService.doOperating(operating);
            resp.sendRedirect(req.getContextPath()+"/manager/topic");
        }
    }

    private void operatingPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        Paging<Operating> paging =managerService.findPage((Integer)map.get("size"),(Integer)map.get("current"),1, Constants.TYPE_OPERATING.getValue());
        req.setAttribute("paging",paging);
        req.setAttribute("operating",0);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void addCategoryAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String category_name = req.getParameter("category_name");
        if(StringUtils.isBlank(category_name)||StringUtils.isBlank(category_name)){
            session.setAttribute("message","分类名不得为空");
        }
        if (!managerService.addCategory(category_name)){
            session.setAttribute("message","添加分类失败");
        }
        resp.sendRedirect(req.getContextPath()+"/manager/topic");
    }

    private void addCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Category category = managerService.findAllCategory().get(0);
        req.setAttribute("newcategory",category);
        List<Category> categoryList = managerService.findAllCategory();
        req.setAttribute("categoryList",categoryList);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void CategoryPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String category_id = req.getParameter("id");
        if (StringUtils.isEmpty(category_id)||StringUtils.isBlank(category_id)){
            session.setAttribute("message","获取分类失败");
            resp.sendRedirect(req.getContextPath()+"/manager/topic");
            return;
        }
        Category category = managerService.findCategory(Long.parseLong(category_id));
        req.setAttribute("category",category);
        List<Category> categoryList = managerService.findAllCategory();
        req.setAttribute("categoryList",categoryList);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }


    private void categoryListPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        List<Category> categoryList = managerService.findAllCategory();
        req.setAttribute("categoryList",categoryList);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void quckChangeAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String customer_id = req.getParameter("customer_id");
        String management = req.getParameter("management");
        if (StringUtils.isEmpty(customer_id)||StringUtils.isBlank(customer_id)){
            session.setAttribute("message","获取用户权限失败");
            resp.sendRedirect(req.getContextPath()+"/manager/list");
            return;
        }
        if(!managerService.changeManagement(Long.parseLong(customer_id),Long.parseLong(management))){
            session.setAttribute("message","修改用户权限失败");
            resp.sendRedirect(req.getContextPath()+"/manager/list");
            return;
        }
        Customer manager = (Customer) session.getAttribute("manager");
        String management4 = Constants.Manager_Level_6.getValue()+"";
        if(management4.equals(management4)){
            managerService.addOperating(manager.getId(),Long.parseLong(customer_id),Constants.STATE_REQUEST.getValue(),Constants.EDIT_USER.getValue());

        }else {
            managerService.addOperating(manager.getId(),Long.parseLong(customer_id),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
        }
        mangerPage(req, resp);
    }

    private void topAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String topic_id = req.getParameter("topic_id");
        if (StringUtils.isEmpty(topic_id)||StringUtils.isBlank(topic_id)){
            session.setAttribute("message","获取话题失败");
        }
        ServletContext app = session.getServletContext();
        Topic topTopic = managerService.findTopic(Long.parseLong(topic_id));
        app.setAttribute("topTopic",topTopic);
        resp.sendRedirect(req.getContextPath()+"/manager/list");
    }

    private void changeMaAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String customer_id = req.getParameter("customer_id");
        String management = req.getParameter("management");
        if (StringUtils.isEmpty(customer_id)||StringUtils.isBlank(customer_id)){
            session.setAttribute("message","获取用户权限失败");
            resp.sendRedirect(req.getContextPath()+"/manager/list");
            return;
        }
        if(!managerService.changeManagement(Long.parseLong(customer_id),Long.parseLong(management))){
            session.setAttribute("message","修改用户权限失败");
            resp.sendRedirect(req.getContextPath()+"/manager/list");
            return;
        }
        Customer manager = (Customer) session.getAttribute("manager");
        if(manager.getManagement().equals(Long.parseLong(management))){
            managerService.addOperating(manager.getId(),Long.parseLong(customer_id),Constants.STATE_REQUEST.getValue(),Constants.EDIT_USER.getValue());
            mangerPage(req, resp);
            return;
        }
        managerService.addOperating(manager.getId(),Long.parseLong(customer_id),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
        mangerPage(req, resp);
    }

    private void changeMaPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String customer_id = req.getParameter("customer_id");
        if (StringUtils.isEmpty(customer_id)||StringUtils.isBlank(customer_id)){
            session.setAttribute("message","获取用户失败");
            resp.sendRedirect(req.getContextPath()+"/manager/list");
            return;
        }
        Customer customer = managerService.findCustomerById(Long.parseLong(customer_id));
        req.setAttribute("customerMa",customer);
        Customer manager = (Customer) session.getAttribute("manager");
        managerService.addOperating(manager.getId(),customer.getId(),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
        mangerPage(req,resp);
    }

    private void badlogOnePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        String customer_id = req.getParameter("customer_id");
        Paging<BadLog> paging =managerService.findPageByOne((Integer)map.get("size"),(Integer)map.get("current"),1, Long.parseLong(customer_id));
        req.setAttribute("paging",paging);
        req.setAttribute("badlog",0);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void badlogPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        Paging<BadLog> paging =managerService.findPage((Integer)map.get("size"),(Integer)map.get("current"),1, Constants.TYPE_BADLOG.getValue());
        req.setAttribute("paging",paging);
        req.setAttribute("badlog",0);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void changePwdAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession();
        Customer manager = (Customer) session.getAttribute("manager");

        String customer_id = req.getParameter("customer_id");
        if (customer_id==null){
            session.setAttribute("message", "获取用户信息失败");
            resp.sendRedirect(req.getContextPath() + "/manager/list");
            return;
        }
        Customer customer = managerService.findCustomerById(Long.parseLong(customer_id));
        String newPassword=req.getParameter("newPassword");
        customer.setPassword(newPassword);
        if (managerService.changePwd(customer)){
            session.setAttribute("message", "修改成功！");
        }else {
            session.setAttribute("message", "修改失败！");
        }
        managerService.addOperating(manager.getId(),customer.getId(),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
        resp.sendRedirect(req.getContextPath()+"/manager/list");
    }

    private void changePwdPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String customer_id = req.getParameter("customer_id");
        if (customer_id==null){
            session.setAttribute("message", "获取用户信息失败");
            resp.sendRedirect(req.getContextPath() + "/manager/list");
            return;
        }
        Customer customer = managerService.findCustomerById(Long.parseLong(customer_id));
        req.setAttribute("customer",customer);
        req.setAttribute("type", "manager");
        String path="/WEB-INF/pages/customer/password.jsp";
        RequestDispatcher db=req.getRequestDispatcher(path);
        db.forward(req,resp);
    }

    private void deleteTopicAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession();
        Customer manager = (Customer) session.getAttribute("manager");
        String customer_name = req.getParameter("customer_name");
        Customer customer = managerService.findCustomer(customer_name);
        String topic=req.getParameter("topic_id");
        Long topic_id =Long.valueOf(topic);
        //删除话题
        if(!managerService.deleteTopic(customer.getId(),topic_id)){
            session.setAttribute("message", "删除失败！");
        }
        managerService.addOperating(manager.getId(),customer.getId(),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
        resp.sendRedirect(req.getContextPath() + "/manager/list");
    }

    private void changeInfoAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Customer manager = (Customer) session.getAttribute("manager");
        String nickname = req.getParameter("nickname");
        String introduction = req.getParameter("introduction");
        Customer customer = (Customer) session.getAttribute("customer");
        customer.setNickname(nickname);
        customer.setIntroduction(introduction);
        try {
            if (!managerService.editCustomer(customer)) {
                session.setAttribute("message", "修改昵称错误！");
            }
        } catch (Exception cause) {
            session.setAttribute("message", "修改昵称错误！");
            session.setAttribute("nickname", nickname);
            session.setAttribute("introduction", introduction);
            resp.sendRedirect(req.getContextPath() + "/manager/list");
            return;
        }
        managerService.addOperating(manager.getId(),customer.getId(),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
        resp.sendRedirect(req.getContextPath() + "/manager/list");
    }

    private void changeInfoPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String customer_id = req.getParameter("customer_id");
        if (customer_id==null){
            session.setAttribute("message", "获取用户信息失败");
            resp.sendRedirect(req.getContextPath() + "/manager/list");
            return;
        }
        Customer customer = managerService.findCustomerById(Long.parseLong(customer_id));
        req.setAttribute("customer",customer);
        req.setAttribute("type", "manager");
        if (session.getAttribute("categoryList") == null) {
            List<Category> categoryList = managerService.loadCategoryList();
            session.setAttribute("categoryList", categoryList);
        }
        String path = "/WEB-INF/pages/customer/nickname.jsp";
        RequestDispatcher RB = req.getRequestDispatcher(path);
        RB.forward(req, resp);
    }

    private void editTopicAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Customer manager = (Customer) session.getAttribute("manager");
        String topic_id = req.getParameter("topic_id");
        String topic_title = req.getParameter("title");
        String topic_content = req.getParameter("content");
        if (topic_id == null) {
            session.setAttribute("message", "获取话题失败");
            resp.sendRedirect(req.getContextPath() + "/manager/list");
            return;
        }
        Topic topic = managerService.findTopic(Long.parseLong(topic_id));
        topic.setTitle(topic_title);
        topic.setContent(topic_content);
        try {
            if (managerService.editTopic(topic)) {
                resp.sendRedirect(req.getContextPath() + "/manager/list");
                managerService.addOperating(manager.getId(),topic.getAuthor().getId(),Constants.STATE_DO.getValue(),Constants.EDIT_USER.getValue());
                return;
            }
            session.setAttribute("message", "错误话题发布失败");
            session.setAttribute("title", topic.getTitle());
            session.setAttribute("content", topic.getContent());
        } catch (Exception e) {
            session.setAttribute("message", "话题发布失败");
            session.setAttribute("title", topic.getTitle());
            session.setAttribute("content", topic.getContent());
            resp.sendRedirect(req.getContextPath() + "/manager/list");
        }
    }

    private void editTopicPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String topic_id = req.getParameter("topic_id");
        if(StringUtils.isBlank(topic_id)||StringUtils.isBlank(topic_id)){
            session.setAttribute("message","获取话题失败");
            resp.sendRedirect(req.getContextPath()+"/manager/topic");
            return;
        }
        if (session.getAttribute("categoryList")==null){
            List<Category> categoryList = managerService.loadCategoryList();
            session.setAttribute("categoryList",categoryList);
        }
        Topic t = managerService.FindTopic(Long.parseLong(topic_id));
        req.setAttribute("category_ID",t.getCategory_id());
        req.setAttribute("topic",t);
        String path="/WEB-INF/pages/topic/publish.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void changeCategoryAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String category_id = req.getParameter("category_id");
        String category_name = req.getParameter("category_name");
        if(StringUtils.isBlank(category_name)||StringUtils.isBlank(category_id)){
            session.setAttribute("message","获取分类失败");
        }
        if(StringUtils.isBlank(category_name)||StringUtils.isBlank(category_id)){
            session.setAttribute("message","分类名不得为空");
        }
        if (!managerService.changeCategory(category_id,category_name)){
            session.setAttribute("message","修改分类失败");
        }
        resp.sendRedirect(req.getContextPath()+"/manager/topic");
    }

    private void changeCategoryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String category_id = req.getParameter("id");
        if (StringUtils.isEmpty(category_id)||StringUtils.isBlank(category_id)){
            session.setAttribute("message","获取分类失败");
            resp.sendRedirect(req.getContextPath()+"/manager/topic");
            return;
        }
        Category category = managerService.findCategory(Long.parseLong(category_id));
        req.setAttribute("category",category);
        topicPage(req,resp);
    }

    private void topicPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        Paging<Topic> paging =managerService.findPage((Integer)map.get("size"),(Integer)map.get("current"),1, Constants.TYPE_TOPIC.getValue());
        req.setAttribute("paging",paging);
        req.setAttribute("top",0);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    private void mangerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        Paging<Customer> paging=managerService.findPage((Integer)map.get("size"),(Integer)map.get("current"),2,Constants.TYPE_CUSTOMER.getValue());
        req.setAttribute("paging",paging);
        req.setAttribute("cus",0);
        String path="/WEB-INF/pages/manager/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
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

}
