package com.kaifamiao.wendao.dao;

import java.util.List;

public interface Dao<T,P> {
    //添加
    boolean save(T t);
    //修改
    boolean modify(T t);
    //删除
    boolean delete(P p);
    //查看
    T find(P p);
    //查看所有的
    List<T> finaAll();
}
