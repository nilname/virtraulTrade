package com.ooteco.service;

import com.ooteco.model.UserLogin;
import com.ooteco.page.PageBean;
import com.ooteco.swagger.JsonResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shenyu on 2018/02/12.
 */
public interface UserAccountService {

    JsonResult sendRegistSms(String mobile);

    JsonResult sendResetPwdSms(String mobile);

    @Transactional
    JsonResult regist(String mobile, String password, String smsCode, String ip);

    @Transactional
    JsonResult login(String mobile, String password, String ip);

    @Transactional
    JsonResult resetPassword(String mobile, String password, String smsCode, String ip);

    @Transactional
    JsonResult editPassword(int userId, String password, String newPassword, String ip);

    @Transactional
    JsonResult editInviter(int userId, int invitecode);

    PageBean<UserLogin> loginLog(int userId, int pageNum, int pageSize);

    @Transactional
    JsonResult editNickname(int userId, String nickName);

    @Transactional
    JsonResult editAvatar(int userId, String avatar);
}
