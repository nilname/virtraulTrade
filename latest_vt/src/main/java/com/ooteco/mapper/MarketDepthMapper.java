package com.ooteco.mapper;

import com.ooteco.model.MarketDepth;
import com.ooteco.model.MarketDepthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MarketDepthMapper {
    int countByExample(MarketDepthExample example);

    int deleteByExample(MarketDepthExample example);

    int insert(MarketDepth record);

    int insertSelective(MarketDepth record);

    List<MarketDepth> selectByExample(MarketDepthExample example);

    int updateByExampleSelective(@Param("record") MarketDepth record, @Param("example") MarketDepthExample example);

    int updateByExample(@Param("record") MarketDepth record, @Param("example") MarketDepthExample example);
}