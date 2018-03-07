package com.ooteco.validator;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserAccountEditPwdValidator {

    @NotBlank(message = "token is empty")
    private String token;

    @NotBlank(message = "旧密码不能为空")
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,10}$", message="旧密码输入错误")
    private String password;

    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,10}$", message="密码请设置为6~10个任意字母与数字的组合")
    private String newPassword;

    @NotBlank(message = "验证码不能为空")
    @Length(min=6,max=6, message="验证码输入错误")
    private String smsCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
