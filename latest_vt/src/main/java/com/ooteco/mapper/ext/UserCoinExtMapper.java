package com.ooteco.mapper.ext;

import com.ooteco.model.param.UserCoinParam;

public interface UserCoinExtMapper {

    int freezeUserCoin(UserCoinParam param);

    int tradeSuccess(UserCoinParam param);

    int addUserCoin(UserCoinParam param);

    int unfreezeUserCoin(UserCoinParam param);
}