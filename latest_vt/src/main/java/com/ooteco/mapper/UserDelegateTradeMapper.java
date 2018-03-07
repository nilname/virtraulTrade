package com.ooteco.mapper;

import com.ooteco.model.UserDelegateTrade;
import com.ooteco.model.UserDelegateTradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDelegateTradeMapper {
    int countByExample(UserDelegateTradeExample example);

    int deleteByExample(UserDelegateTradeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserDelegateTrade record);

    int insertSelective(UserDelegateTrade record);

    List<UserDelegateTrade> selectByExample(UserDelegateTradeExample example);

    UserDelegateTrade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserDelegateTrade record, @Param("example") UserDelegateTradeExample example);

    int updateByExample(@Param("record") UserDelegateTrade record, @Param("example") UserDelegateTradeExample example);

    int updateByPrimaryKeySelective(UserDelegateTrade record);

    int updateByPrimaryKey(UserDelegateTrade record);
}