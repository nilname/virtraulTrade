package com.ooteco.service.impl;

import com.ooteco.mapper.MarketInfoMapper;
import com.ooteco.service.MarketInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fangqing on 2/23/18.
 */
@Service
public class MarketInfoServiceImpl implements MarketInfoService {
    @Autowired

    MarketInfoMapper marketinfomapper;

    private Logger logger = Logger.getLogger(MarketInfoServiceImpl.class);

    @Override
    public List<?> getMarketInfo(String platform, String symbol) {
        return marketinfomapper.getMarket(platform, symbol);
    }

    @Override
    public List<?> getMarketInfos(String platform) {
        return marketinfomapper.getMarkets(platform);
    }

    @Override
    public List<?> getDepthInfo(String platform, String symbol) {
        return marketinfomapper.getDepthInfo(platform, symbol);
    }

    public List<?> getTradeHistory(String platform, String symbol) {
        return marketinfomapper.getTradeHistoryInfo(platform, symbol);
    }


}
