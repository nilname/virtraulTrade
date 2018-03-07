package com.ooteco.validator;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAccountEditInviterValidator {

    @NotBlank(message = "token is empty")
    private String token;

    @NotNull(message = "邀请码不能为空")
    @Max(value = 9999999, message = "邀请码输入错误")
    @Min(value = 1000000, message = "邀请码输入错误")
    private int invitecode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(int invitecode) {
        this.invitecode = invitecode;
    }
}
