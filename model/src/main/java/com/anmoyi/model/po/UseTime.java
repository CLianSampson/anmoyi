package com.anmoyi.model.po;

import java.util.Date;

public class UseTime {
    private Integer id;

    private Integer userId;

    private Date useTime;

    private Integer duration;

    private int pointType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UseTime{" +
                "id=" + id +
                ", userId=" + userId +
                ", useTime=" + useTime +
                ", pointType=" + pointType +
                ", createTime=" + createTime +
                '}';
    }
}