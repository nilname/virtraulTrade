package com.ooteco.mapper;

import com.ooteco.model.UserCoin;
import com.ooteco.model.UserCoinExample;
import com.ooteco.model.UserCoinKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCoinMapper {
    int countByExample(UserCoinExample example);

    int deleteByExample(UserCoinExample example);

    int deleteByPrimaryKey(UserCoinKey key);

    int insert(UserCoin record);

    int insertSelective(UserCoin record);

    List<UserCoin> selectByExample(UserCoinExample example);

    UserCoin selectByPrimaryKey(UserCoinKey key);

    int updateByExampleSelective(@Param("record") UserCoin record, @Param("example") UserCoinExample example);

    int updateByExample(@Param("record") UserCoin record, @Param("example") UserCoinExample example);

    int updateByPrimaryKeySelective(UserCoin record);

    int updateByPrimaryKey(UserCoin record);
}