package com.ooteco.mapper.ext;

import com.ooteco.model.ext.UserDelegateTradeExt;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserDelegateTradeExtMapper {

    int updateStatusAndRemainQuantity(@Param("id")Integer id,
                                      @Param("status")Integer status,
                                      @Param("remainQuantity")BigDecimal remainQuantity);

    List<UserDelegateTradeExt> getUserAsksDelegateTrades(@Param("userId") Integer userId);

    List<UserDelegateTradeExt> getUserBidsDelegateTrades(@Param("userId") Integer userId);
}