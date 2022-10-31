package com.kaifamiao.wendao.controller;

import com.alibaba.fastjson2.JSONObject;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.FileInfo;
import com.kaifamiao.wendao.service.FileInfoService;
import com.kaifamiao.wendao.utils.Constants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.tinylog.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/file/*")
@MultipartConfig
public class FileServlet extends HttpServlet {

    public static final String CONTEXT_PARAM_STORE_PATH = "storePath";
    public static final String CONTEXT_DIRECTORY_NAME = "uploads";

    private FileInfoService fiService = new FileInfoService();

    private String path;

    @Override
    public void init() throws ServletException {
        ServletContext application = this.getServletContext();
        path = application.getInitParameter(CONTEXT_PARAM_STORE_PATH);
        if(StringUtils.isEmpty(path) || StringUtils.isBlank(path)) {
            String appRealPath = application.getRealPath("/");
            File dir = new File(appRealPath, CONTEXT_DIRECTORY_NAME);
            if( !dir.exists() ) {
                dir.mkdir();
            }
            try {
                path = dir.getCanonicalPath();
            } catch (IOException e) {
                Logger.error(e);
                path = appRealPath;
            }
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        if("POST".equals(method) && uri.endsWith("/simditor/upload")){
            this.simditorUpload(request, response);
            return;
        }
        if("GET".equals(method) && uri.endsWith("/simditor/show")){
            this.simditorShow(request, response);
            return;
        }
    }

    protected void simditorUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        Customer me = (Customer)session.getAttribute(Constants.CUSTOMER_LOGINED.getName());
        Part part = request.getPart("image");
        if (part==null){
            map.put("success", true);
            map.put("msg", "获取控件失败");
            return;
        }
        final String original = part.getSubmittedFileName();
        InputStream in = part.getInputStream();
        byte[] bytes = in.readAllBytes();
        String checksum = DigestUtils.sha1Hex( bytes );
        FileInfo fi = new FileInfo();
        fi.setPath(path);
        fi.setName(original);
        fi.setChecksum(checksum);
        fi.setSize(bytes.length);
        fi.setOwner(me);

        final String pathname = path + File.separator + fiService.naming( original, checksum );
        File target = new File( pathname );

        if( !target.exists() ) {
            fiService.store(pathname, bytes);
        }

        fiService.store( fi );

        map.put("success", true);
        map.put("msg", "上传成功");
        map.put("file_path", request.getContextPath() + "/file/simditor/show?id=" + fi.getId() );

        String json = JSONObject.toJSONString(map);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        out.println(json);
    }

    protected void simditorShow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("id");
        if(!StringUtils.isEmpty(sid) && !StringUtils.isBlank(sid)) {
            FileInfo fi = fiService.load(Long.valueOf(sid));
            String suffix = "";
            String original = fi.getName();
            int index = original.lastIndexOf(".");
            if( index != -1 ) {
                suffix = original.substring(index);
            }
            File f = new File(fi.getPath() , fi.getChecksum() + suffix );
            if( f.exists() ) {
                InputStream in = new FileInputStream(f);
                OutputStream out = response.getOutputStream();
                in.transferTo(out);
                out.close();
                in.close();
            }
        }
    }
}
