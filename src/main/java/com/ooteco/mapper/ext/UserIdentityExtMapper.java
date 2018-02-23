package com.ooteco.mapper.ext;

import com.ooteco.model.UserIdentity;
import com.ooteco.model.UserLogin;

import java.util.List;

public interface UserIdentityExtMapper {

    UserIdentity selectByUserId(int userId);

    int updateStateByUserId(UserIdentity userIdentity);
}