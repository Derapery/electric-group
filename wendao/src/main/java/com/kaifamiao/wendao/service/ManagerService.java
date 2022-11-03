package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.*;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.util.List;

public class ManagerService {

    private CustomerDao customerDao;
    private ExplainDao explainDao;
    private TopicsDao topicsDao;
    private AttentionDao attentionDao;
    private TopicLikeDao topicLikeDao;
    private ExplainLikeDao explainLikeDao;
    private SnowflakeIdGenerator snowId= SnowflakeIdGenerator.getInstance();

    public ManagerService(){
        customerDao=new CustomerDao();
        explainDao=new ExplainDao();
        topicsDao=new TopicsDao();
        attentionDao=new AttentionDao();
    }

    public List<Customer> findAllCustomer() {
        return customerDao.finaAll();
    }
}
