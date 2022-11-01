package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.TopicLikeDao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.Praise;

public class TopicLikeService {
    private TopicLikeDao topicLikeDao;
    public TopicLikeService(){
        topicLikeDao=new TopicLikeDao();
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
        praise.setTopic(topic);
        praise.setState(state);
       topicLikeDao.save(praise);
    }
    //删除用户的点赞的记录
    public void delete(Long customer_id,Long topic_id){
        topicLikeDao.delete(customer_id,topic_id);
    }
}