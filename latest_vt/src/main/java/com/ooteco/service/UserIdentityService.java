package com.ooteco.service;

import com.ooteco.model.UserIdentity;
import com.ooteco.swagger.JsonResult;

/**
 * Created by shenyu on 2018/02/13.
 */
public interface UserIdentityService {

    JsonResult findByUserId(int userId);

    JsonResult add(UserIdentity userIdentity);

    JsonResult editState(int userId, Byte state);

}
