package com.ooteco.mapper.ext;

import com.ooteco.model.InfoCoin;

import java.util.List;

public interface InfoCoinExtMapper {

    List<InfoCoin> selectAll();

    int insertSelective(InfoCoin record);
}