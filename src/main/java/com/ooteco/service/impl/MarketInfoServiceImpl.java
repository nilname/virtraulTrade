package com.ooteco.service.impl;

import com.ooteco.service.MarketInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by fangqing on 2/23/18.
 */
@Service
public class MarketInfoServiceImpl implements MarketInfoService {
    @Override
    public List<?> getMarketInfo(String symbol) {
        ArrayList res = new ArrayList<ArrayList<Long>>();
        ArrayList<Long> tmp = new ArrayList<>();
        Random r = new Random();
        tmp.add(r.nextLong());
        tmp.add(r.nextLong());
        res.add(tmp);
        return res;
    }

    @Override
    public List<?> getDepthInfo(String symbol) {
        return new ArrayList<Object>();
    }
}
