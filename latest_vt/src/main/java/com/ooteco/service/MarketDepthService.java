package com.ooteco.service;

import com.github.pagehelper.PageHelper;
import com.ooteco.constant.MarketDepthType;
import com.ooteco.mapper.MarketDepthMapper;
import com.ooteco.model.MarketDepth;
import com.ooteco.model.MarketDepthExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketDepthService {

    @Autowired
    private MarketDepthMapper marketDepthMapper;

    public List<MarketDepth> getMatchPriceRecords(MarketDepth param){
        PageHelper.startPage(1, 50);
        MarketDepthExample example = new MarketDepthExample();
        MarketDepthExample.Criteria criteria = example.createCriteria();
        criteria.andPlatformEqualTo(param.getPlatform())
                .andSymbolEqualTo(param.getSymbol());
        if(param.getType().equals(MarketDepthType.asks.name())){
            criteria.andTypeEqualTo(MarketDepthType.bids.name())
                    .andPriceLessThanOrEqualTo(param.getPrice());
        }else if(param.getType().equals(MarketDepthType.bids.name())){
            criteria.andTypeEqualTo(MarketDepthType.asks.name())
                    .andPriceGreaterThanOrEqualTo(param.getPrice());
        }
        return marketDepthMapper.selectByExample(example);
    }
}
