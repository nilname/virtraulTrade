package com.ooteco.model;

public class UserAccount {
    private Integer id;

    private String mobile;

    private String password;

    private String nickname;

    private String avatar;

    private Integer inviter;

    private Integer invitecode;

    private Long registtime;

    private Byte state;

    private Long edittime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Integer getInviter() {
        return inviter;
    }

    public void setInviter(Integer inviter) {
        this.inviter = inviter;
    }

    public Integer getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(Integer invitecode) {
        this.invitecode = invitecode;
    }

    public Long getRegisttime() {
        return registtime;
    }

    public void setRegisttime(Long registtime) {
        this.registtime = registtime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getEdittime() {
        return edittime;
    }

    public void setEdittime(Long edittime) {
        this.edittime = edittime;
    }
}