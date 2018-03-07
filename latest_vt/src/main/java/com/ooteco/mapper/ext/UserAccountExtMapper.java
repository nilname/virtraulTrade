package com.ooteco.mapper.ext;

import com.ooteco.model.UserAccount;
import org.apache.ibatis.annotations.Param;

public interface UserAccountExtMapper {

    UserAccount selectByMobileAndPassword(@Param("mobile")String mobile, @Param("password")String password);

    UserAccount selectByIdAndPassword(@Param("id")Integer id, @Param("password")String password);

    int updatePasswordByMobile(@Param("mobile")String mobile, @Param("password")String password);

    Integer isExistByMobile(String mobile);

    Integer selectIdByMobile(String mobile);

    Integer selectIdByInvitecode(int invitecode);
}