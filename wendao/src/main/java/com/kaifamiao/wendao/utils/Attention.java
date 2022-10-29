package com.kaifamiao.wendao.utils;

public class Attention {

    private Long id;
    private Long fans_id;//粉丝
    private Long attention_id;//关注的人

    public Long getId() {
        return id;
    }

    public Long getFans_id() {
        return fans_id;
    }

    public void setFans_id(Long fans_id) {
        this.fans_id = fans_id;
    }

    public Long getAttention_id() {
        return attention_id;
    }

    public void setAttention_id(Long attention_id) {
        this.attention_id = attention_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
