package com.ooteco.mapper;

import com.ooteco.model.InfoCoinParam;

public interface InfoCoinParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoCoinParam record);

    int insertSelective(InfoCoinParam record);

    InfoCoinParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoCoinParam record);

    int updateByPrimaryKey(InfoCoinParam record);
}