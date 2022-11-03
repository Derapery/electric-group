package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.TopicLikeDao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.Praise;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.util.List;

public class TopicLikeService {
    private TopicLikeDao topicLikeDao;
    private SnowflakeIdGenerator snowflakeIdGenerator;
    public TopicLikeService(){
        topicLikeDao=new TopicLikeDao();
        snowflakeIdGenerator=SnowflakeIdGenerator.getInstance();
    }
    //查看用户和话题是否有信息存储在表中
    public Praise find(Long customer_id, Long topic_id){
        return topicLikeDao.find(customer_id,topic_id);
    }
    //保存用户的点赞或者踩的信息
    public void save(Long customer_id,Long topic_id,Integer state){
        Praise praise = new Praise();
        Customer customer =new Customer();
        customer.setId(customer_id);
        praise.setCustomer(customer);
        Topic topic=new Topic();
        topic.setId(topic_id);
        praise.setId(snowflakeIdGenerator.generate());
        praise.setTopic(topic);
        praise.setState(state);
        if(topicLikeDao.find(customer_id,topic_id)==null){
            topicLikeDao.save(praise);
        }else {
            topicLikeDao.modify(praise);
        }
    }
    //删除用户的点赞的记录
    public void delete(Long customer_id,Long topic_id){
        topicLikeDao.delete(customer_id,topic_id);
    }

}