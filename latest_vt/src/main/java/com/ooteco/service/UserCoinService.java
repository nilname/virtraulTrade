package com.ooteco.service;

import com.ooteco.mapper.UserCoinMapper;
import com.ooteco.mapper.ext.UserCoinExtMapper;
import com.ooteco.model.UserCoin;
import com.ooteco.model.UserCoinExample;
import com.ooteco.model.param.UserCoinParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCoinService {

    @Autowired
    private UserCoinExtMapper userCoinExtMapper;
    @Autowired
    private UserCoinMapper userCoinMapper;

    public int unfreezeUserCoin(UserCoinParam param){
        return userCoinExtMapper.unfreezeUserCoin(param);
    }

    public int freezeUserCoin(UserCoinParam param){
        return userCoinExtMapper.freezeUserCoin(param);
    }

    public int tradeSuccess(UserCoinParam param){
        return userCoinExtMapper.tradeSuccess(param);
    }

    public int addUserCoin(UserCoinParam param){
        return userCoinExtMapper.addUserCoin(param);
    }

    public List<UserCoin> getUserCoins(Integer id){
        UserCoinExample example = new UserCoinExample();
        UserCoinExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(id);
        return userCoinMapper.selectByExample(example);
    }

}
