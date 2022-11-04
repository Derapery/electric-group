package com.kaifamiao.wendao.utils;

public enum Constants {

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
