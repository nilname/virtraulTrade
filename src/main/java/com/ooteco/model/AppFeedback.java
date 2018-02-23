package com.ooteco.model;

public class AppFeedback {
    private Integer id;

    private String content;

    private String callway;

    private String ip;

    private Long inputtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCallway() {
        return callway;
    }

    public void setCallway(String callway) {
        this.callway = callway == null ? null : callway.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Long getInputtime() {
        return inputtime;
    }

    public void setInputtime(Long inputtime) {
        this.inputtime = inputtime;
    }
}