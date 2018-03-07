package com.ooteco.mapper;

import com.ooteco.model.UserKnockdownTrade;
import com.ooteco.model.UserKnockdownTradeExample;
import com.ooteco.model.UserKnockdownTradeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserKnockdownTradeMapper {
    int countByExample(UserKnockdownTradeExample example);

    int deleteByExample(UserKnockdownTradeExample example);

    int deleteByPrimaryKey(UserKnockdownTradeKey key);

    int insert(UserKnockdownTrade record);

    int insertSelective(UserKnockdownTrade record);

    List<UserKnockdownTrade> selectByExample(UserKnockdownTradeExample example);

    UserKnockdownTrade selectByPrimaryKey(UserKnockdownTradeKey key);

    int updateByExampleSelective(@Param("record") UserKnockdownTrade record, @Param("example") UserKnockdownTradeExample example);

    int updateByExample(@Param("record") UserKnockdownTrade record, @Param("example") UserKnockdownTradeExample example);

    int updateByPrimaryKeySelective(UserKnockdownTrade record);

    int updateByPrimaryKey(UserKnockdownTrade record);
}