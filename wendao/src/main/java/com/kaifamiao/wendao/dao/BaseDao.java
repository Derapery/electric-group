package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.utils.DataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public abstract class BaseDao<T,P> implements Dao<T,P>{
   //专门为子类创造的runner
    protected QueryRunner runner;
   //在构造方法中初始化
   protected BaseDao(){
       DataSourceFactory factory=DataSourceFactory.getInstance();
       runner=new QueryRunner(factory.getDataSource());
    }
}
