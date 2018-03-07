package com.ooteco.mapper.ext;

import com.ooteco.model.UserIdentity;

public interface UserIdentityExtMapper {

    UserIdentity selectByUserId(int userId);

    int updateStateByUserId(UserIdentity userIdentity);
}