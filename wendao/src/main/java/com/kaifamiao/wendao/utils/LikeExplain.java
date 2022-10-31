package com.kaifamiao.wendao.utils;

public class LikeExplain {
    // 信息记录的ID
    private static  Long id;
    //用户的ID
    private static Long customer_id;
    //评论的ID
    private static Long explain_id;
    //点赞或踩的状态
    private  static  Integer state;
    public static Long getId() {
        return id;
    }

    public static void setId(Long id) {
        LikeExplain.id = id;
    }

    public static Long getCustomer_id() {
        return customer_id;
    }

    public static void setCustomer_id(Long customer_id) {
        LikeExplain.customer_id = customer_id;
    }

    public static Long getExplain_id() {
        return explain_id;
    }

    public static void setExplain_id(Long explain_id) {
        LikeExplain.explain_id = explain_id;
    }

    public static Integer getState() {
        return state;
    }

    public static void setState(Integer state) {
        LikeExplain.state = state;
    }
}
