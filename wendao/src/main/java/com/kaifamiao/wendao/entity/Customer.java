package com.kaifamiao.wendao.entity;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    // 对象标识符字段
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    private LocalDate registerDate;
    // 黑话: 维护从 Customer 到 Topic 的 一对多 关联关系
    // 白话: 当前用户发表了哪些话题
    private List<Topic> topics;
    // 维护从 Customer 到 Explain 的 一对多 关联关系
    private List<Explain> explains; // 当前用户所有的解答

    // 对象标识符属性
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Explain> getExplains() {
        return explains;
    }

    public void setExplains(List<Explain> explains) {
        this.explains = explains;
    }
}
