package com.kaifamiao.wendao.entity;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    // 对象标识符字段
    private Long id;
    private Integer management;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    //用来存储用户的性别
    private String gender;
    //用来存储用户的喜欢的话题列表
    private List<Topic> likeList;
    //用来存储对于用户的简介
    private String introduction;
    private LocalDate registerDate;
    // 黑话: 维护从 Customer 到 Topic 的 一对多 关联关系
    // 白话: 当前用户发表了哪些话题
    private List<Topic> topics;
    // 维护从 Customer 到 Explain 的 一对多 关联关系
    private List<Explain> explains; // 当前用户所有的解答
    //维护用户到关注的一对多关系


    private List<Customer> attention;

    private List<Customer> fans;

    public List<Topic> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Topic> likeList) {
        this.likeList = likeList;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Customer> getAttention() {
        return attention;
    }

    public void setAttention(List<Customer> attention) {
        this.attention = attention;
    }

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

    public Integer getManagement() {
        return management;
    }

    public void setManagement(Integer management) {
        this.management = management;
    }

    public List<Customer> getFans() {
        return fans;
    }

    public void setFans(List<Customer> fans) {
        this.fans = fans;
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
