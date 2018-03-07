package com.ooteco.service;

import java.util.List;

/**
 * Created by fangqing on 2/23/18.
 */
public interface MarketInfoService {
    public List<?> getMarketInfos(String platform);

    public List<?> getMarketInfo(String platform, String symbol);

    public List<?> getDepthInfo(String platform, String symbol);
}
