package com.ooteco.validator;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class UserIdentityEditStateValidator {

    @NotNull(message = "用户唯一标识不能为空")
    private int userId;

    @NotNull(message = "状态码不能为空")
    private Byte state;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}
