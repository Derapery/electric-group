package com.kaifamiao.wendao.utils;

import java.util.List;

public class Paging<T> {
    // 控制每页显示的记录数目
    private Integer size ;
    // 保存总页数
    private Integer total ;
    // 保存当前页码
    private Integer current ;
    // 保存当前页面显示的数据
    private List<T> dataList ;
    //显示每页开始的序号
    private Integer begin;
    public Paging(){

    }
    public Paging(Integer size,Integer total,Integer current,List<T> dataList){
        this.size = size;
        this.total = total;
        this.current = current;
        this.dataList = dataList;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }
}
