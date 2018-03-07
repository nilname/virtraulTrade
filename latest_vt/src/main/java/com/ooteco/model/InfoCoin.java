package com.ooteco.model;

import java.util.Date;
import java.util.List;

public class InfoCoin {
    private Integer id;

    private String nameCn;

    private String nameEn;

    private String desc;

    private Date inputtime;

    private List<InfoCoinParam> paramList;

    private List<InfoCoinLink> linkList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getInputtime() {
        return inputtime;
    }

    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public List<InfoCoinParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<InfoCoinParam> paramList) {
        this.paramList = paramList;
    }

    public List<InfoCoinLink> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<InfoCoinLink> linkList) {
        this.linkList = linkList;
    }

}