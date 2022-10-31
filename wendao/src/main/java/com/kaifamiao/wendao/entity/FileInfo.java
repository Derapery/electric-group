package com.kaifamiao.wendao.entity;

import java.time.LocalDateTime;

public class FileInfo {

    private Long id; // 对象标识(zhi)符
    private String path; // 存储路径
    private String name; // 文件名称
    private String checksum; // 校验和
    private long size; // 文件大小
    private LocalDateTime uploadTime; // 上传时间
    // 维护从 FileInfo 到 Customer 的 多对一 关联关系
    private Customer owner; // 当前文件是哪个用户上传的

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
}
