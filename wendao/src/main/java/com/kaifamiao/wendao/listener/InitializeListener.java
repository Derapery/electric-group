package com.kaifamiao.wendao.listener;

import com.kaifamiao.wendao.utils.DataSourceFactory;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;
import org.tinylog.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

@WebListener
public class InitializeListener implements ServletContextListener {
    //监听到应用启动就初始化数据源
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext app =sce.getServletContext();
        Logger.trace("初始化{}",this);
        //初始化数据源
        DataSourceFactory factory=DataSourceFactory.getInstance();
        factory.init();
        //将路径存储到application中
        app.setAttribute("ctxPath",app.getContextPath());
        //获得雪花算法实例
        String dateBase=app.getInitParameter("datacenterId");
        Long date;
        if(dateBase.isBlank()||dateBase.isEmpty()){
            date=1L;
        }else{
            date=Long.valueOf(dateBase);
        }
        String workId=app.getInitParameter("workerId");
        Long work;
        if(workId.isBlank()||workId.isEmpty()){
            work=1L;
        }else{
            work=Long.valueOf(workId);
        }
        // 创建基于雪花算法的标识符生成器(用类变量予以缓存)
        SnowflakeIdGenerator.build( work, date );

    }
    //监听到应用关闭就关闭数据源
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSourceFactory.getInstance().destroy();
    }
}
