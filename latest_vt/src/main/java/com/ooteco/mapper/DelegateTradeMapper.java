package com.ooteco.mapper;

import com.ooteco.model.DelegateTrade;

public interface DelegateTradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DelegateTrade record);

    int insertSelective(DelegateTrade record);

    DelegateTrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DelegateTrade record);

    int updateByPrimaryKey(DelegateTrade record);
}