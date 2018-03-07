package com.ooteco.mapper.ext;

import com.ooteco.model.UserKnockdownTrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xule on 2018/3/5.
 */
public interface UserKnockdownTradeExtMapper {
    List<UserKnockdownTrade> knockdownTradeInOrder(@Param("userId")Integer userId, @Param("askTradeId")Integer askTradeId);

    List<UserKnockdownTrade> knockdownTradeOutOrder(@Param("userId")Integer userId,@Param("bidTradeId")Integer bidTradeId);
}
