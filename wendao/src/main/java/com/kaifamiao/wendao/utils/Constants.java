package com.kaifamiao.wendao.utils;

public enum Constants {

    CUSTOMER_LOGINED("customer");

    private String name;

    Constants(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
