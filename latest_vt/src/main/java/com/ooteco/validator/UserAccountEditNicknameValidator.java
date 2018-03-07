package com.ooteco.validator;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UserAccountEditNicknameValidator {

    @NotBlank(message = "token is empty")
    private String token;

    @NotBlank(message = "昵称不能为空")
    @Length(min=1,max=24, message = "昵称请设置为1~24个字符")
    private String nickName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
