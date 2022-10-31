package com.kaifamiao.wendao.utils;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;

import java.util.List;

public class Praise {
    //喜欢列表
    private List<Topic> likeTopics;
    //用户
    private Customer customer;
    //话题
    private  Topic topic;
    //状态
    private Integer state;

    public List<Topic> getLikeTopics() {
        return likeTopics;
    }

    public void setLikeTopics(List<Topic> likeTopics) {
        this.likeTopics = likeTopics;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
