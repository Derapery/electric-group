package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.service.AttentionService;
import com.kaifamiao.wendao.service.CustomerService;
import com.kaifamiao.wendao.service.TopicService;
import com.kaifamiao.wendao.utils.Paging;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
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
import java.util.Objects;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {
    private CustomerService cusSer;
    private AttentionService attentionService;
    private TopicService topicService;
    @Override
    public void init() throws ServletException {
        cusSer=new CustomerService();
        attentionService=new AttentionService();
        topicService=new TopicService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method= req.getMethod();
        String uri=req.getRequestURI();
        if("GET".equals(method)&& uri.endsWith("/sign/in")){
            this.singPage(req,resp);
            return;
        }
        if("POST".equals(method) && uri.endsWith("/sign/in")){
            this.singAction(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/login/up")){
            this.loginPage(req,resp);
            return;
        }
        if("POST".equals(method) && uri.endsWith("/login/up")){
            this.loginAction(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/sign/out")){
            this.signOut(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/change")){
            this.changePage(req,resp);
            return;
        }
        if("POST".equals(method) && uri.endsWith("/change")){
            this.changeAction(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/edit")){
            this.editPage(req,resp);
            return ;
        }
        if("POST".equals(method) && uri.endsWith("/edit")){
            this.editAction(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/delete")){
            this.delete(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/mine")){
            this.mineAction(req,resp);
            return;
        }

        if("GET".equals(method) && uri.endsWith("/fans")){
            this.fans(req,resp);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/fansAction")){
            this.fansAction(req,resp);
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

    private void mineAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> map=havPaging(req);
        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        Customer customer;
        if(!StringUtils.isBlank(id) && !StringUtils.isEmpty(id)){
            customer=cusSer.find(Long.valueOf(id));
        }else{
             Customer cus=(Customer)session.getAttribute("customer");
            customer=cusSer.find(cus.getId());
        }
        if(customer == null){
            session.setAttribute("message","请先登录，再查看个人主页");
            resp.sendRedirect(req.getContextPath()+"/customer/sign/in");
        }
        Paging<Topic> paging=topicService.findPage((Integer)map.get("size"),(Integer)map.get("current"),customer,2);
        req.setAttribute("paging",paging);
        req.setAttribute("customer",customer);
        req.setAttribute("state",2);
        String path="/WEB-INF/pages/customer/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }

    //"GET" "/sing/in"
    private void singPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path="/WEB-INF/pages/customer/sign-in.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }
    //对登录信息进行校验
    private int validateSgin(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
       String username=req.getParameter("username");
       if(StringUtils.isBlank(username)||StringUtils.isEmpty(username)){
           session.setAttribute("message","用户名不能为空！");
           return -1;
       }
       String password=req.getParameter("password");
       if(StringUtils.isEmpty(password)||StringUtils.isBlank(password)){
           session.setAttribute("message","密码不能为空！");
           session.setAttribute("username",username);
           return -1;

       }
       String captcha=req.getParameter("captcha");
       if(captcha.isBlank()||captcha.isEmpty()){
           session.setAttribute("username",username);
           session.setAttribute("password",password);
           session.setAttribute("message","输入的验证码不能为空！");
           return -1;
       }
       if(!StringUtils.equalsIgnoreCase(captcha,(String)session.getAttribute("CAPTCHA"))){
           session.setAttribute("username",username);
           session.setAttribute("password",password);
           session.setAttribute("message","输入的验证码不正确！");
           return -1;
        }
       //判断用户是否存在
        Customer customer= cusSer.find(username);
       if(customer.getId()==0){
           session.setAttribute("message",customer.getUsername());
           return -1;
       }
       if(customer==null){
           session.setAttribute("message","输入的用户不存在！");
           return -1;
       }
       if(!StringUtils.equals(customer.getPassword(),cusSer.encrypt(password,customer.getSalt()))){
           session.setAttribute("message","输入的密码不正确！");
           return -1;
       }
       if(customer.getManagement()==1){
           session.setAttribute( "manager",customer);
           return 1;
       }
        session.setAttribute( "customer",customer);
       return 0;
    }
    //"POST" "/sing/in"
    private void singAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if(validateSgin(req,resp)==0){
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        if(validateSgin(req,resp)==1){
            String path = req.getContextPath()+"/manager/list";
            bridgeAction(path,resp);
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/customer/sign/in");
    }
    //"GET" "/login/in"
    private void loginPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path="/WEB-INF/pages/customer/login-up.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }
    //对注册的信息进行检查
    private boolean validateLogin(HttpServletRequest req){
        HttpSession session=req.getSession();
        String nickname=req.getParameter("nickname");
        if(nickname.isBlank()||nickname.isBlank()){
            session.setAttribute("message","昵称不能为空");
            return false;
        }
        String username=req.getParameter("username");
        if(username.isBlank()||username.isBlank()){
            session.setAttribute("nickname",nickname);
            session.setAttribute("message","用户名不能为空");
            return false;
        }
        Customer customer=cusSer.exist(username);
        if(customer !=null){
            session.setAttribute("nickname",nickname);
            session.setAttribute("message","用户名已存在");
            return false;
        }
        String password=req.getParameter("password");
        if(password.isBlank()||password.isBlank()){
            session.setAttribute("nickname",nickname);
            session.setAttribute("username",username);
            session.setAttribute("message","密码不能为空");
            return false;
        }
        String confirm=req.getParameter("confirm");
        if(!Objects.equals(password,confirm)){
            session.setAttribute("nickname",nickname);
            session.setAttribute("username",username);
            session.setAttribute("message","输入的两次密码不一致");
            return false;
        }
        String captcha=req.getParameter("captcha");
        if(captcha.isEmpty()||captcha.isBlank()){
            session.setAttribute("nickname",nickname);
            session.setAttribute("username",username);
            session.setAttribute("message","验证码不能为空！");
            return false;
        }
        if(!StringUtils.equalsIgnoreCase(captcha,(String)session.getAttribute("CAPTCHA"))){
            session.setAttribute("nickname",nickname);
            session.setAttribute("username",username);
            session.setAttribute("message","输入的验证码不正确！");
            return false;
        }
        return true;
    }
    //"POST" "/login/in"
    private void loginAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if(validateLogin(req)){
            Customer customer=new Customer();
            try {
                BeanUtils.populate(customer,req.getParameterMap());
            } catch (Exception cause) {
               throw new RuntimeException("转换时发生错误！",cause);
            }
            cusSer.save(customer);
            HttpSession session=req.getSession();
            session.setAttribute("message","注册成功");
            resp.sendRedirect(req.getContextPath() + "/customer/sign/in" );
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/customer/login/up" );
    }
    //“GET” “/sign/out”
    private void signOut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        session.removeAttribute("customer");
        resp.sendRedirect(req.getContextPath()+"/customer/sign/in");
    }
        //“GET” “/change”
        private void changePage(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String path="/WEB-INF/pages/customer/password.jsp";
            RequestDispatcher db=req.getRequestDispatcher(path);
            db.forward(req,resp);
        }
        //修改密码时的数据校验
        private boolean validateChange(HttpServletRequest req){
            HttpSession session=req.getSession();
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            if(StringUtils.isBlank(password) ||StringUtils.isEmpty(password)){
                session.setAttribute("message","输入的原始密码不能为空！");
                session.setAttribute("username",username);
                return false;
            }
            String newPassword=req.getParameter("newPassword");
            if(StringUtils.isEmpty(newPassword)||StringUtils.isBlank(newPassword)){
                session.setAttribute("message","输入的新密码不能为空！");
                session.setAttribute("username",username);
                return false;
            }
            String confirm=req.getParameter("confirm");
            if(!StringUtils.equals(confirm,newPassword)){
                session.setAttribute("message","两次输入的密码不一致");
                session.setAttribute("username",username);
                return false;
            }
            Customer customer=(Customer)session.getAttribute("customer");
            if(!StringUtils.equals(customer.getPassword(),cusSer.encrypt(password,customer.getSalt()))){
                session.setAttribute("message","输入的原始密码不正确");
                session.setAttribute("username",username);
                return false;
            }
            return true;
        }
        //"POST" "/change"
        private void changeAction(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            HttpSession session=req.getSession();
            Customer customer=(Customer) session.getAttribute("customer");
            if(validateChange(req)) {
                try {
                    String newPassword=req.getParameter("newPassword");
                    customer.setPassword(newPassword);
                    cusSer.modify(customer,true);
                    session.setAttribute("message", "修改成功！");
                    resp.sendRedirect(req.getContextPath()+"/customer/sign/out");
                } catch (Exception cause) {
                    session.setAttribute("message", "修改失败！");
                    resp.sendRedirect(req.getContextPath()+"/customer/change");
                }
            }
            resp.sendRedirect(req.getContextPath()+"/customer/change");
        }
    //”GET“ ”edit“
    private void editPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path="/WEB-INF/pages/customer/nickname.jsp";
        RequestDispatcher RB=req.getRequestDispatcher(path);
        RB.forward(req,resp);
    }
    //”POST“ ”edit“
    private void editAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session =req.getSession();
        String nickname=req.getParameter("nickname");
        if(StringUtils.isBlank(nickname) || StringUtils.isEmpty(nickname)){
            session.setAttribute("message","昵称不能为空！");
            resp.sendRedirect(req.getContextPath()+"/customer/edit");
        }
        String introduction=req.getParameter("introduction");
        if(StringUtils.isBlank(introduction) || StringUtils.isBlank(introduction)){
            session.setAttribute("message","简介不能为空！");
            session.setAttribute("nickname",nickname);
            resp.sendRedirect(req.getContextPath()+"/customer/edit");
        }
        Customer customer=(Customer) session.getAttribute("customer");
        customer.setNickname(nickname);
        customer.setIntroduction(introduction);
        try {
            cusSer.modify(customer,false);
        }catch (Exception cause){
            session.setAttribute("message","修改昵称错误！");
            session.setAttribute("nickname",nickname);
            session.setAttribute("introduction",introduction);
            resp.sendRedirect(req.getContextPath()+"/customer/edit");
        }
        resp.sendRedirect("/index.jsp");
    }
    //”GET“ "DELETE"
    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Customer customer=(Customer) session.getAttribute("customer");
        Long id=customer.getId();
        if(cusSer.delete(id)){
            session.removeAttribute("customer");
            session.setAttribute("message","用户已经注销");
            resp.sendRedirect("/index.jsp");
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/topic/list");

    }
    private void fans(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Long customer_id=Long.valueOf(req.getParameter("id"));
        Customer cus=cusSer.find(customer_id);
        Integer ID=Integer.valueOf(req.getParameter("ID"));
        int state= ID==1?1:2;
        session.setAttribute("state",state);
        session.setAttribute("cus",cus);
        String path="/WEB-INF/pages/customer/fans.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }
    private void fansAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session=req.getSession();
        Customer customer=(Customer)session.getAttribute("customer");
        int concern=Integer.valueOf(req.getParameter("concern"));
        Long attention_id=Long.valueOf(req.getParameter("customer_id"));
        Integer size=Integer.valueOf(req.getParameter("size"));
        String current=req.getParameter("current");
        if(concern==1){
            attentionService.delete(customer.getId(),attention_id,1);
        }else{
            attentionService.save(customer.getId(), attention_id);
        }
        resp.sendRedirect(req.getContextPath()+"/topic/list?size="+size+"&current="+Integer.parseInt(current.trim()));
    }

    private void bridgeAction(String path, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(path);
    }
}
