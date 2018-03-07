package com.ooteco.mapper;

import com.ooteco.model.MarketTick;
import com.ooteco.model.MarketTickExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketTickMapper {
    int countByExample(MarketTickExample example);

    int deleteByExample(MarketTickExample example);

    int insert(MarketTick record);

    int insertSelective(MarketTick record);

    List<MarketTick> selectByExample(MarketTickExample example);

    int updateByExampleSelective(@Param("record") MarketTick record, @Param("example") MarketTickExample example);

    int updateByExample(@Param("record") MarketTick record, @Param("example") MarketTickExample example);
}