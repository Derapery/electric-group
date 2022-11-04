package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.AttentionDao;
import com.kaifamiao.wendao.utils.Attention;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

public class AttentionService {
    private AttentionDao attentionDao;
    private SnowflakeIdGenerator snowflakeIdGenerator;
    public AttentionService(){
        attentionDao=new AttentionDao();
        snowflakeIdGenerator=SnowflakeIdGenerator.getInstance();
    }
    //查找关注的信息
    public Attention find(Long fans,Long attention){
        return attentionDao.findSecond(fans,attention);
    }
    //保存关注的信息
    public boolean save(Long fans,Long attention){
        Attention attention1=new Attention();
            attention1.setFans_id(fans);
            attention1.setAttention_id(attention);
        attention1.setId(snowflakeIdGenerator.generate());
        return attentionDao.save(attention1);
    }
    //删除关注的信息
    public boolean delete(Long fans,Long attention,int tage){
       return attentionDao.delete(fans,attention,tage);
    }
}
