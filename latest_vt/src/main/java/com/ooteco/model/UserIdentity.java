package com.ooteco.model;

import java.util.Date;

public class UserIdentity {
    private Integer id;

    private Integer userid;

    private String realname;

    private String idcard;

    private Byte state;

    private Date inputtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getInputtime() {
        return inputtime;
    }

    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }
    private String imgId1;

    private String imgId2;

    private String imgId3;

    private String imgSign;

    public String getImgId1() {
        return imgId1;
    }

    public void setImgId1(String imgId1) {
        this.imgId1 = imgId1 == null ? null : imgId1.trim();
    }

    public String getImgId2() {
        return imgId2;
    }

    public void setImgId2(String imgId2) {
        this.imgId2 = imgId2 == null ? null : imgId2.trim();
    }

    public String getImgId3() {
        return imgId3;
    }

    public void setImgId3(String imgId3) {
        this.imgId3 = imgId3 == null ? null : imgId3.trim();
    }

    public String getImgSign() {
        return imgSign;
    }

    public void setImgSign(String imgSign) {
        this.imgSign = imgSign == null ? null : imgSign.trim();
    }
}