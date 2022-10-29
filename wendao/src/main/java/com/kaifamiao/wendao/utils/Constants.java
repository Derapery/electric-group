package com.kaifamiao.wendao.utils;

public enum Constants {

    CUSTOMER_LOGINED("customer"),
    MANAGEMENT_ID(0),
    CUSTOMER_ID(1);
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
