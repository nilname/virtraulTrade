package com.ooteco.validator;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class AppFeedbackAddValidator {

    @NotBlank(message = "token is empty")
    private String token;

    @NotBlank(message = "问题内容不能为空")
    @Length(max = 300, message = "问题内容请输入1~300个字符")
    private String content;

    @NotBlank(message = "联系方式不能为空")
    private String callway;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCallway() {
        return callway;
    }

    public void setCallway(String callway) {
        this.callway = callway;
    }
}
