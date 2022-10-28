package com.kaifamiao.wendao.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Topic {
    // 对象标识符字段
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publishTime;
    private String publishAddress;
    private int priority;
    // 黑话: 维护从 Topic 到 Customer 的 多对一 关联关系
    // 白话: 当前话题的作者是哪个用户
    private Customer author;
    // 维护从 Topic 到 Explain 之间的 一对多 关联关系
    private List<Explain> explains; // 当前话题下的所有解答

    // 对象标识符属性
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public List<Explain> getExplains() {
        return explains;
    }

    public void setExplains(List<Explain> explains) {
        this.explains = explains;
    }
}
