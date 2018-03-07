package com.ooteco.service.impl;

import com.github.pagehelper.PageHelper;
import com.ooteco.mapper.ext.DelegateTradeExtMapper;
import com.ooteco.mapper.ext.UserKnockdownTradeExtMapper;
import com.ooteco.model.DelegateTrade;
import com.ooteco.model.UserKnockdownTrade;
import com.ooteco.model.ext.UserKnockdownTradeExt;
import com.ooteco.page.PageBean;
import com.ooteco.service.KnockdownTradeService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xule on 2018/3/6.
 */
@Service("knockdownTradeService")
public class KnockdownTradeServiceImpl implements KnockdownTradeService{

    private static Logger log = Logger.getLogger(UserIdentityServiceImpl.class);

    @Autowired
    private UserKnockdownTradeExtMapper knockdownTradeExtMapper;
    @Autowired
    private DelegateTradeExtMapper delegateTradeExtMapper;


    @Override
    public PageBean<UserKnockdownTradeExt> selectTradeDetail(int pageNum, int pageSize, Integer userId, Integer id) {
        List<UserKnockdownTradeExt> list = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        if(id==1){
            List<UserKnockdownTrade> knockdownTrades = knockdownTradeExtMapper.knockdownTradeInOrder(userId, id);
            List<DelegateTrade> trades = delegateTradeExtMapper.selectById(id);
            if(!knockdownTrades.isEmpty()&&!trades.isEmpty()){
                BigDecimal averagePrice = averagePrice(knockdownTrades);
                BigDecimal totalNumber = totalNumber(knockdownTrades);
                BigDecimal price = trades.get(0).getPrice();
                BigDecimal quantity = trades.get(0).getQuantity();
                Date modifyTime = trades.get(0).getModifyTime();
                for (UserKnockdownTrade record: knockdownTrades) {
                    UserKnockdownTradeExt userKnockdownTrade = new UserKnockdownTradeExt();
                    BeanUtils.copyProperties(record, userKnockdownTrade);
                    userKnockdownTrade.setAveragePrice(averagePrice);
                    userKnockdownTrade.setReverseTime(modifyTime);
                    userKnockdownTrade.setDelegatePrice(price);
                    userKnockdownTrade.setDelegateQuantity(quantity);
                    userKnockdownTrade.setTotalNumber(totalNumber);
                    list.add(userKnockdownTrade);
                }
            }
            return new PageBean<>(list);
        }else if(id==2){
            List<UserKnockdownTrade> tradeList = knockdownTradeExtMapper.knockdownTradeOutOrder(userId, id);
            List<DelegateTrade> trades = delegateTradeExtMapper.selectById(id);
            if(!tradeList.isEmpty()&&!trades.isEmpty()){
                BigDecimal averagePrice = averagePrice(tradeList);
                BigDecimal totalNumber = totalNumber(tradeList);
                BigDecimal price = trades.get(0).getPrice();
                BigDecimal quantity = trades.get(0).getQuantity();
                Date modifyTime = trades.get(0).getModifyTime();
                for (UserKnockdownTrade record: tradeList) {
                    UserKnockdownTradeExt userKnockdownTrade = new UserKnockdownTradeExt();
                    BeanUtils.copyProperties(record, userKnockdownTrade);
                    userKnockdownTrade.setAveragePrice(averagePrice);
                    userKnockdownTrade.setReverseTime(modifyTime);
                    userKnockdownTrade.setDelegatePrice(price);
                    userKnockdownTrade.setDelegateQuantity(quantity);
                    userKnockdownTrade.setTotalNumber(totalNumber);
                    list.add(userKnockdownTrade);
                }
            }

            return new PageBean<>(list);
        }
        return null;
    }

    public BigDecimal averagePrice(List<UserKnockdownTrade> list){
        BigDecimal totalPrcie=new BigDecimal(0);
        BigDecimal totalNumber=new BigDecimal(0);
        if(!list.isEmpty()){
            for (UserKnockdownTrade numberData: list) {
                BigDecimal price = numberData.getPrice();
                BigDecimal quantity = numberData.getQuantity();
                BigDecimal multiply = price.multiply(quantity);
                totalPrcie.add(multiply);
                totalNumber.add(quantity);
            }
        }
        if(totalPrcie!=null && totalNumber.compareTo(BigDecimal.ZERO)!=0){
            BigDecimal average = totalPrcie.divide(totalNumber, 9, BigDecimal.ROUND_HALF_UP);
            return average;
        }
        return null;
    }

    public BigDecimal totalNumber(List<UserKnockdownTrade> list){
        BigDecimal totalNumber=new BigDecimal(0);
        if(!list.isEmpty()){
            for (UserKnockdownTrade numberData: list) {
                BigDecimal price = numberData.getPrice();
                BigDecimal quantity = numberData.getQuantity();
                BigDecimal multiply = price.multiply(quantity);
                totalNumber.add(quantity);
            }
        }
        return totalNumber;
    }
}
