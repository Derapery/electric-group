package com.kaifamiao.wendao.utils;

public enum Constants {
    STATE_DO(555,"已操作"),
    STATE_REQUEST(666,"待处理操作"),
    STATE_REFUSE(888,"已拒绝"),
    EDIT_USER(101,"编辑用户信息"),
    CHANGE_PWD(102,"修改用户密码"),
    DELETE_USE(103,"删除用户"),
    Manager_Level_0(0,"用户"),
    Manager_Level_1(1,"管理员"),
    Manager_Level_2(2,"超级管理员"),
    Manager_Level_3(-1,"禁止评论"),
    Manager_Level_4(-2,"禁止发布话题"),
    Manager_Level_5(-3,"禁止登陆"),
    Manager_Level_6(-4,"待删除"),
    CUSTOMER_LOGINED("customer"),
    MANAGEMENT_ID(1),
    CUSTOMER_ID(0),
    SUPER_MANAGER(2),
    FANS_LIST("fans"),
    TYPE_CUSTOMER(1,"管理员用户界面"),
    TYPE_TOPIC(2,"管理员话题界面"),
    TYPE_BADLOG(3,"管理员不良言论界面"),
    TYPE_OPERATING(4,"管理员操作记录界面"),
    BADLOG_TYPE(1,"不良言论"),
    ATTENTION_LIST("attentions");
    private Integer value;
    private String name;

    Constants(String name){
        this.name = name;
    }

    Constants(Integer value){
        this.value = value;
    }

    Constants(Integer value,String name){
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }
    public Integer getValue(){return value;}
}
