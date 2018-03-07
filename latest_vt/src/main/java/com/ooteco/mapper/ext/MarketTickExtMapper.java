package com.ooteco.mapper.ext;

import com.ooteco.model.MarketTick;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by xule on 2018/3/2.
 */
public interface MarketTickExtMapper {
    List<MarketTick> selectMarketCurrentData(@Param("date")Date date);

    Date selectMarketMaxTimeData();

    List<MarketTick> selectAllData();

    String selectSymbol(@Param("id")Integer id);
}
