package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.service.ManagerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ManagerServlet extends HttpServlet {
    private ManagerService managerService;

    @Override
    public void init() throws ServletException {
        managerService=new ManagerService();
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
    }


    private void mangerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customerList = managerService.findAllCustomer();

        String path="/WEB-INF/pages/manger/list.jsp";
        RequestDispatcher dis= req.getRequestDispatcher(path);
        dis.forward(req,resp);
    }


}
