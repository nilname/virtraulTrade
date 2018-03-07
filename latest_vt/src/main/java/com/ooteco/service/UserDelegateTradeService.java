package com.ooteco.service;

import com.github.pagehelper.PageHelper;
import com.ooteco.constant.DelegateTradeStatus;
import com.ooteco.mapper.UserDelegateTradeMapper;
import com.ooteco.mapper.ext.DelegateTradeExtMapper;
import com.ooteco.mapper.ext.MarketTickExtMapper;
import com.ooteco.mapper.ext.UserDelegateTradeExtMapper;
import com.ooteco.mapper.ext.UserKnockdownTradeExtMapper;
import com.ooteco.model.DelegateTrade;
import com.ooteco.model.UserDelegateTrade;
import com.ooteco.model.UserDelegateTradeExample;
import com.ooteco.model.UserKnockdownTrade;
import com.ooteco.model.ext.UserDelegateTradeExt;
import com.ooteco.page.PageBean;
import com.ooteco.service.impl.UserIdentityServiceImpl;
import com.ooteco.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserDelegateTradeService {

    private static Logger log = Logger.getLogger(UserIdentityServiceImpl.class);

    @Autowired private DelegateTradeExtMapper delegateTradeExtMapper;
    @Autowired private UserKnockdownTradeExtMapper knockdownTradeExtMapper;
    @Autowired private MarketTickExtMapper marketTickExtMapper;

    @Autowired
    private UserDelegateTradeMapper userDelegateTradeMapper;
    @Autowired
    private UserDelegateTradeExtMapper userDelegateTradeExtMapper;

    public List<UserDelegateTradeExt> getUserAsksDelegateTrades(Integer userId){
        return userDelegateTradeExtMapper.getUserAsksDelegateTrades(userId);
    }

    public List<UserDelegateTradeExt> getUserBidsDelegateTrades(Integer userId){
        return userDelegateTradeExtMapper.getUserBidsDelegateTrades(userId);
    }

    public int cancelDelegate(Integer id){
        UserDelegateTrade delegateTrade = new UserDelegateTrade();
        delegateTrade.setId(id);
        delegateTrade.setIsCanceled(true);
        return userDelegateTradeMapper.updateByPrimaryKeySelective(delegateTrade);
    }

    public int insert(UserDelegateTrade userDelegateTrade){
        if(userDelegateTrade.getRemainQuantity() == null){
            userDelegateTrade.setRemainQuantity(userDelegateTrade.getQuantity());
        }
        return userDelegateTradeMapper.insertSelective(userDelegateTrade);
    }

    public int updateStatusAndRemainQuantity(Integer id, Integer status, BigDecimal matchedQuantity){
        return userDelegateTradeExtMapper.updateStatusAndRemainQuantity(id, status, matchedQuantity);
    }

    public UserDelegateTrade get(Integer id){
        return userDelegateTradeMapper.selectByPrimaryKey(id);
    }

    public List<UserDelegateTrade> getUncomplicatedRecords(){
        PageHelper.startPage(1, 20);
        UserDelegateTradeExample example = new UserDelegateTradeExample();
        UserDelegateTradeExample.Criteria criteria1 = example.createCriteria();
        criteria1.andIsCanceledEqualTo(false)
                .andStatusEqualTo(DelegateTradeStatus.unhandled.ordinal());
        UserDelegateTradeExample.Criteria criteria2 = example.createCriteria();
        criteria1.andIsCanceledEqualTo(false)
                .andStatusEqualTo(DelegateTradeStatus.processing.ordinal());
        example.or(criteria2);
        example.setOrderByClause(" create_time ");
        return userDelegateTradeMapper.selectByExample(example);
    }

    public PageBean<DelegateTrade> findDelegateTradeData(int pageNum, int pageSize, DelegateTrade delegateTrade) {
        PageHelper.startPage(pageNum,pageSize);
        String data = new TokenService().decryptToken(delegateTrade.getToken());
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,login log,token error"));
            return null;
        }
        int userId = Integer.parseInt(data);
        List<DelegateTrade> trades = delegateTradeExtMapper.delegateTradeCurrentOrder(userId, delegateTrade.getIsCanceled(), delegateTrade.getStatuIds(), delegateTrade.getTradeType());
        for (DelegateTrade list:trades) {
            Integer id = list.getId();
            Integer userIdData = list.getUserId();
            Integer operateType = list.getOperateType();
            String name = findTradeName(list.getTradeType());
            list.setSymbol(name);
            if(operateType==1){
                List<UserKnockdownTrade> tradeList = knockdownTradeExtMapper.knockdownTradeInOrder(userIdData, id);
                BigDecimal averagePrice = averagePrice(tradeList);
                BigDecimal totalNumber = totalNumber(tradeList);
                list.setTotalNumber(totalNumber);
                list.setAveragePrice(averagePrice);
            }else if(operateType==2){
                List<UserKnockdownTrade> knockdownTrades = knockdownTradeExtMapper.knockdownTradeOutOrder(userIdData, id);
                BigDecimal decimal = averagePrice(knockdownTrades);
                BigDecimal totalNumber = totalNumber(knockdownTrades);
                list.setTotalNumber(totalNumber);
                list.setAveragePrice(decimal);
            }
        }
        return new PageBean<>(trades);
    }

    public PageBean<DelegateTrade> findHistoryDelegateTrade(int pageNum, int pageSize, DelegateTrade delegateTrade) {
        PageHelper.startPage(pageNum,pageSize);
        String data = new TokenService().decryptToken(delegateTrade.getToken());
        if(StringUtil.isEmpty(data)) {
            log.error(String.format("user account,login log,token error"));
            return null;
        }
        int userId = Integer.parseInt(data);
        List<DelegateTrade> historyOrder = delegateTradeExtMapper.delegateTradeHistoryOrder(userId, delegateTrade.getIsCanceled(), delegateTrade.getStatuIds(), delegateTrade.getTradeType(), delegateTrade.getOperateType());
        for (DelegateTrade historyData:historyOrder) {
            Integer id = historyData.getId();
            Integer userIdData = historyData.getUserId();
            Integer operateType = historyData.getOperateType();
            String name = findTradeName(historyData.getTradeType());
            historyData.setSymbol(name);
            if(operateType==1){
                List<UserKnockdownTrade> tradeList = knockdownTradeExtMapper.knockdownTradeInOrder(userIdData, id);
                BigDecimal averagePrice = averagePrice(tradeList);
                BigDecimal totalNumber = totalNumber(tradeList);
                historyData.setTotalNumber(totalNumber);
                historyData.setAveragePrice(averagePrice);
            }else if(operateType==2){
                List<UserKnockdownTrade> knockdownTrades = knockdownTradeExtMapper.knockdownTradeOutOrder(userIdData, id);
                BigDecimal decimal = averagePrice(knockdownTrades);
                BigDecimal totalNumber = totalNumber(knockdownTrades);
                historyData.setTotalNumber(totalNumber);
                historyData.setAveragePrice(decimal);
            }
        }
        return new PageBean<>(historyOrder);
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
                BigDecimal quantity = numberData.getQuantity();
                totalNumber.add(quantity);
            }
        }
        return totalNumber;
    }

    public String findTradeName(Integer id){
        String selectSymbol = marketTickExtMapper.selectSymbol(id);
        if(selectSymbol!=null){
            String[] split = selectSymbol.split("_");
            if(split.length>1){
                String name = (split[0] + "/" + split[1]);
                return name;
            }
        }

        return null;
    }

}
