package com.ooteco.mapper;

import com.ooteco.model.InfoCoin;

public interface InfoCoinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoCoin record);

    int insertSelective(InfoCoin record);

    InfoCoin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoCoin record);

    int updateByPrimaryKey(InfoCoin record);
}