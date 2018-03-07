package com.ooteco.mapper.ext;


import com.ooteco.model.InfoCoinParam;

import java.util.List;

public interface InfoCoinParamExtMapper {

    List<InfoCoinParam> selectByCoinid(int coinid);
}