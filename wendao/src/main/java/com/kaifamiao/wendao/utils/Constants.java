package com.kaifamiao.wendao.utils;

public enum Constants {

    CUSTOMER_LOGINED("customer"),
    MANAGEMENT_ID(1),
    CUSTOMER_ID(0),
    SUPER_MANAGER(2),
    FANS_LIST("fans"),
    TYPE_CUSTOMER(1),
    TYPE_TOPIC(0),
    ATTENTION_LIST("attentions");
    private Integer value;
    private String name;

    Constants(String name){
        this.name = name;
    }

    Constants(Integer value){
        this.value = value;
    }


    public String getName() {
        return name;
    }
    public Integer getValue(){return value;}
}
