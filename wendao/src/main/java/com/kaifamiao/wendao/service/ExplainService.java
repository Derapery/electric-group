package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.ExplainDao;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.time.LocalDateTime;

public class ExplainService {
    private SnowflakeIdGenerator snowflakeIdGenerator;
    private ExplainDao explainDao;
    public ExplainService(){
        super();
        snowflakeIdGenerator=SnowflakeIdGenerator.getInstance();
        explainDao=new ExplainDao();
    }
//保存评论
    public boolean save(Explain explain){
        LocalDateTime localDateTime=LocalDateTime.now();
        explain.setPublishTime(localDateTime);
        explain.setId(snowflakeIdGenerator.generate());
        return explainDao.save(explain);
    }
}
