package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.ExplainLikeDao;
import com.kaifamiao.wendao.utils.LikeExplain;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

public class ExplainLikeService {
    private ExplainLikeDao explainLikeDao;
    private SnowflakeIdGenerator snowflakeIdGenerator;
    public ExplainLikeService(){
        explainLikeDao=new ExplainLikeDao();
        snowflakeIdGenerator=SnowflakeIdGenerator.getInstance();
    }
    //往数据库中存储评论点赞的信息
    public boolean save(Long customer_id,Long explain_id,Integer state){
        LikeExplain likeExplain = new LikeExplain();
        likeExplain.setExplain_id(explain_id);
        likeExplain.setCustomer_id(customer_id);
        likeExplain.setState(state);
        likeExplain.setId(snowflakeIdGenerator.generate());
        return explainLikeDao.save(likeExplain);
    }
    //删除数据库中的评论点赞的信息记录
    public boolean delete(Long customer_id,Long explain_id){
        return explainLikeDao.delete(customer_id,explain_id);
    }
    //查找信息
    public LikeExplain find(Long customer_id,Long explain_id){
        return explainLikeDao.find(customer_id,explain_id);
    }
}
