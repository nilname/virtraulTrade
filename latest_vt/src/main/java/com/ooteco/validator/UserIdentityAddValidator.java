package com.ooteco.validator;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserIdentityAddValidator {

    @NotBlank(message = "token is empty")
    private String token;

    @NotBlank(message = "姓名不能为空")
    private String realname;

    @NotBlank(message = "身份证号不能为空")
    @Length(min=18,max=18, message="身份证号输入错误")
    private String idcard;

    @NotBlank(message = "上传照片不符合要求")
    private String img_id1;

    @NotBlank(message = "上传照片不符合要求")
    private String img_id2;

    @NotBlank(message = "上传照片不符合要求")
    private String img_id3;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getImg_id1() {
        return img_id1;
    }

    public void setImg_id1(String img_id1) {
        this.img_id1 = img_id1;
    }

    public String getImg_id2() {
        return img_id2;
    }

    public void setImg_id2(String img_id2) {
        this.img_id2 = img_id2;
    }

    public String getImg_id3() {
        return img_id3;
    }

    public void setImg_id3(String img_id3) {
        this.img_id3 = img_id3;
    }
}
