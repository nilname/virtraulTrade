package com.ooteco.model;

import java.util.Date;

public class InfoIco {
    private Integer id;

    private String title;

    private String coinType;

    private Double coinTarget;

    private Double coinGet;

    private Date endtime;

    private Date inputtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType == null ? null : coinType.trim();
    }

    public Double getCoinTarget() {
        return coinTarget;
    }

    public void setCoinTarget(Double coinTarget) {
        this.coinTarget = coinTarget;
    }

    public Double getCoinGet() {
        return coinGet;
    }

    public void setCoinGet(Double coinGet) {
        this.coinGet = coinGet;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getInputtime() {
        return inputtime;
    }

    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    private String cover;

    private String content;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}