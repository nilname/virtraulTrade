package com.ooteco.service;

import com.ooteco.mapper.UserKnockdownTradeMapper;
import com.ooteco.model.UserKnockdownTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserKnockdownTradeService {

    @Autowired
    private UserKnockdownTradeMapper userKnockdownTradeMapper;

    public int insert(UserKnockdownTrade knockdownTrade){
        return userKnockdownTradeMapper.insertSelective(knockdownTrade);
    }
}
