package com.kaifamiao.wendao.utils;

public class LikeExplain {
    // 信息记录的ID
    private  Long id;
    //用户的ID
    private  Long customer_id;
    //评论的ID
    private  Long explain_id;
    //点赞或踩的状态
    private    Integer state;
    public  Long getId() {
        return id;
    }

    public  void setId(Long id) {
        this.id = id;
    }

    public  Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public  Long getExplain_id() {
        return explain_id;
    }

    public  void setExplain_id(Long explain_id) {
        this.explain_id = explain_id;
    }

    public  Integer getState() {
        return state;
    }

    public  void setState(Integer state) {
        this.state = state;
    }
}
