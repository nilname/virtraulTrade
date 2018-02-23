package com.ooteco.mapper.ext;

import com.ooteco.model.UserLogin;

import java.util.List;

public interface UserLoginExtMapper {

    List<UserLogin> selectAllByUserId(int userid);
}