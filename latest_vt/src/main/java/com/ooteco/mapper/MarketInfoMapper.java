package com.ooteco.mapper;

import com.ooteco.model.DepthInfo;
import com.ooteco.model.MarketInfo;
import com.ooteco.model.TradeHistoryInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangqing on 2/25/18.
 */
@Repository
public interface MarketInfoMapper {

    public List<MarketInfo> getMarket(@Param("platform") String platform, @Param("symbol") String symbol);

    public List<MarketInfo> getMarkets(@Param("platform") String platform);

    public List<DepthInfo> getDepthInfo(@Param("platform") String platform, @Param("symbol") String symbol);
    public List<TradeHistoryInfo> getTradeHistoryInfo(@Param("platform") String platform, @Param("symbol") String symbol);

}
