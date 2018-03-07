package com.ooteco.validator;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserAccountLoginValidator {

    @NotBlank(message = "手机号不能为空")
    @Length(min=11,max=11, message="手机号输入错误")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,10}$", message="密码输入错误")
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
