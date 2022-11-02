package com.kaifamiao.wendao.entity;

import java.time.LocalDateTime;

public class Explain {
    // 对象标识符字段(用于存储对象标识符)
    private Long id;
    private String content;
    private LocalDateTime publishTime;
    private String publishAddress;
    private int praise; // 赞美数量
    private int despise; // 鄙视数量

    // 维护从 Explain 到 Customer 之间的 多对一 关联关系
    private Customer author; // 当前解答是哪个用户发布的
    // 维护从 Explain 到 Topic 之间的 多对一 关联关系
    private Topic topic; // 当前解答为哪个话题解答
    //用来记录话题的点赞状态
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    // 对象标识符属性
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishAddress() {
        return publishAddress;
    }

    public void setPublishAddress(String publishAddress) {
        this.publishAddress = publishAddress;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getDespise() {
        return despise;
    }

    public void setDespise(int despise) {
        this.despise = despise;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
