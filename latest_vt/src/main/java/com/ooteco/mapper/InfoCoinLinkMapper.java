package com.ooteco.mapper;

import com.ooteco.model.InfoCoinLink;

public interface InfoCoinLinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoCoinLink record);

    int insertSelective(InfoCoinLink record);

    InfoCoinLink selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoCoinLink record);

    int updateByPrimaryKey(InfoCoinLink record);
}