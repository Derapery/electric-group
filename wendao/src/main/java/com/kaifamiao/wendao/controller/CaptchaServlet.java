package com.kaifamiao.wendao.controller;

import com.kaifamiao.wendao.utils.CaptchaHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/captcha/*")
public class CaptchaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType( "image/jpg" );
        OutputStream output = response.getOutputStream();

        CaptchaHelper h = CaptchaHelper.getInstance();
        // 创建验证码图片并返回验证码字符串
        final String code = h.create(6, false, 180, 50, output, 50);

        HttpSession session = request.getSession();
        session.setAttribute("CAPTCHA", code );
    }

}
