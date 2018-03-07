package com.ooteco.service.impl;

import com.ooteco.mapper.MarketTickMapper;
import com.ooteco.mapper.ext.MarketTickExtMapper;
import com.ooteco.model.MarketTick;
import com.ooteco.model.MarketTickExample;
import com.ooteco.service.MarketTickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xule on 2018/3/2.
 */
@Service
public class MarketTickServiceImpl implements MarketTickService{

    @Autowired
    MarketTickExtMapper marketTickExtMapper;
    @Autowired
    private MarketTickMapper marketTickMapper;

    private static Date maxDate;

    @Override
    public MarketTick get(Integer id){
        MarketTickExample example = new MarketTickExample();
        MarketTickExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<MarketTick> marketTicks = marketTickMapper.selectByExample(example);
        return marketTicks.isEmpty() ? null : marketTicks.get(0);
    }

    //数据初始化的时候第一次拿到的所有的数据
    @Override
    public List<MarketTick> selectAllCurrentData() {
        List<MarketTick> tickList = marketTickExtMapper.selectAllData();
        return tickList;
    }

    //选出数据表里最晚的时间并保存
    @Override
    public void marketMaxTimeData() {
        Date date = marketTickExtMapper.selectMarketMaxTimeData();
        if(date!=null){
            maxDate = date;
        }
    }


    //拿到最近更新的数据
    @Override
    @Scheduled(fixedRate = 5000*100)
    public List<MarketTick> marketCurrentData() {
        if(maxDate!=null){
            List<MarketTick> marketTicks = marketTickExtMapper.selectMarketCurrentData(maxDate);
            return marketTicks;
        }
        marketMaxTimeData();
        return null;

    }

}
