package com.ooteco.service;

import java.util.List;

/**
 * Created by fangqing on 2/23/18.
 */
public interface MarketInfoService {
    public List<?> getMarketInfo(String symbol);

    public List<?> getDepthInfo(String symbol);
}
