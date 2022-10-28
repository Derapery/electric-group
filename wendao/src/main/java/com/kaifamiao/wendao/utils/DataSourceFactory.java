package com.kaifamiao.wendao.utils;

import com.alibaba.druid.pool.DruidDataSource;

public class DataSourceFactory {
    static  DataSourceFactory factory;
    DruidDataSource dataSource;
    private  DataSourceFactory(){
        super();
    }
    //使用懒汉单例来获得DataSourceFactory实例
    public static DataSourceFactory getInstance(){
        factory= factory==null? new DataSourceFactory():factory;
        return factory;
    }
    //通过实例方法来获得DataSource实例
    public DruidDataSource getDataSource(){
        return dataSource;
    }
    //初始化dataSource
    public void init(){
        dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/wendao?serverTimezone=Asia/Chongqing");
        dataSource.setUsername("king");
        dataSource.setPassword("king");
    }
    //注销dataSource
    public void destroy(){
        dataSource.isClosed();
    }

}
