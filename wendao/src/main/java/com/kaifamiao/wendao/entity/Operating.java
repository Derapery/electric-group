package com.kaifamiao.wendao.entity;

public class Operating {
    private Long id;
    private Long handler_id;
    private Long user_id;
    private Integer type;
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHandler_id() {
        return handler_id;
    }

    public void setHandler_id(Long handler_id) {
        this.handler_id = handler_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
