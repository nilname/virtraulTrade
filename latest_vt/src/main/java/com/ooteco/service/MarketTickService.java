package com.ooteco.service;

import com.ooteco.model.MarketTick;

import java.util.List;

/**
 * Created by xule on 2018/3/2.
 */
public interface MarketTickService {

    MarketTick get(Integer id);

    List<MarketTick> marketCurrentData();

    void marketMaxTimeData();

    List<MarketTick> selectAllCurrentData();
}