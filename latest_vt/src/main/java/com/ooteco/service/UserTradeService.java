package com.ooteco.service;

import com.ooteco.constant.DelegateTradeStatus;
import com.ooteco.constant.MarketDepthType;
import com.ooteco.exceptions.CustomException;
import com.ooteco.exceptions.SymbolParseException;
import com.ooteco.model.*;
import com.ooteco.model.param.UserCoinParam;
import com.ooteco.utils.PlatformUtil;
import com.ooteco.utils.TradeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserTradeService {

    private Logger logger = Logger.getLogger(UserDelegateTrade.class);
    private String tradeLockPrefix = "trade";
    @Autowired
    private UserDelegateTradeService userDelegateTradeService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private MarketDepthService marketDepthService;
    @Autowired
    private UserKnockdownTradeService userKnockdownTradeService;
    @Autowired
    private MarketTickService marketTickService;

    @Transactional(rollbackFor = Exception.class)
    public void cancel(Integer id) throws SymbolParseException, CustomException{
        synchronized (getTradeLock(id).intern()) {
            UserDelegateTrade delegateTrade = userDelegateTradeService.get(id);
            if(delegateTrade.getIsCanceled()){
                throw new CustomException("订单已取消");
            }
            if(delegateTrade.getStatus() == DelegateTradeStatus.complete.ordinal()){
                throw new CustomException("订单已完成");
            }
            userDelegateTradeService.cancelDelegate(id);
            delegateTrade.setAmount(delegateTrade.getPrice().multiply(delegateTrade.getRemainQuantity()));
            delegateTrade.setQuantity(delegateTrade.getRemainQuantity());
            MarketTick platformTradeType = marketTickService.get(delegateTrade.getTradeType());
            userCoinService.unfreezeUserCoin(getFreezeUserCoinParam(delegateTrade, platformTradeType.getSymbol()));
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void matchDelegateTradeScheduled() throws SymbolParseException, CustomException{
        logger.info("start scheduled match");
        List<UserDelegateTrade> records = userDelegateTradeService.getUncomplicatedRecords();
        for(UserDelegateTrade record : records){
            synchronized (getTradeLock(record.getId()).intern()) {
                record = userDelegateTradeService.get(record.getId());
                if (!record.getIsCanceled()) {
                    MarketTick platformTradeType = marketTickService.get(record.getTradeType());
                    matchDelegateTrade(record, platformTradeType.getSymbol());
                }
            }
        }
        logger.info("scheduled match over");
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleDelegateTrade(UserDelegateTrade delegateTrade) throws SymbolParseException, CustomException{
        userDelegateTradeService.insert(delegateTrade);
        MarketTick platformTradeType = marketTickService.get(delegateTrade.getTradeType());
        userCoinService.freezeUserCoin(getFreezeUserCoinParam(delegateTrade, platformTradeType.getSymbol()));

        matchDelegateTrade(delegateTrade, platformTradeType.getSymbol());
    }

    @Transactional(rollbackFor = Exception.class)
    public void matchDelegateTrade(UserDelegateTrade delegateTrade, String symbol) throws SymbolParseException, CustomException{
        DelegateTradeMatchResult matchResult = executeMatch(delegateTrade);
        delegateTrade.setAmount(matchResult.getMatchedAmount());
        delegateTrade.setQuantity(matchResult.getMatchedQuantity());
        updateUserCoinAfterTradeSuccess(delegateTrade, symbol);
        addUserCoin(delegateTrade, symbol);
        userDelegateTradeService.updateStatusAndRemainQuantity(delegateTrade.getId(), matchResult.getStatus(), matchResult.getMatchedQuantity());
    }

    public int addUserCoin(UserDelegateTrade delegateTrade, String symbol) throws SymbolParseException{
        UserCoinParam addUserCoinParam = getAddUserCoinParam(delegateTrade, symbol);
        return userCoinService.addUserCoin(addUserCoinParam);

    }

    public DelegateTradeMatchResult executeMatch(Integer delegateTradeId) throws SymbolParseException,CustomException{
        UserDelegateTrade delegateTrade = userDelegateTradeService.get(delegateTradeId);
        return executeMatch(delegateTrade);
    }

    public DelegateTradeMatchResult executeMatch(UserDelegateTrade delegateTrade) throws SymbolParseException,CustomException{
        MarketTick platformTradeType = marketTickService.get(delegateTrade.getTradeType());
        MarketDepth marketDepth = new MarketDepth();
        marketDepth.setPlatform(platformTradeType.getPlatform());
        marketDepth.setSymbol(platformTradeType.getSymbol());
        marketDepth.setPrice(delegateTrade.getPrice().doubleValue());
        marketDepth.setType(TradeUtils.getMarketDepthType(delegateTrade.getOperateType()).name());
        return executeMatch(marketDepth, delegateTrade);
    }

    public int updateUserCoinAfterTradeSuccess(UserDelegateTrade delegateTrade,String symbol) throws SymbolParseException{
        UserCoinParam param = getFreezeUserCoinParam(delegateTrade, symbol);
        return userCoinService.tradeSuccess(param);
    }

    @Transactional(rollbackFor = Exception.class)
    public DelegateTradeMatchResult executeMatch(MarketDepth marketDepth, UserDelegateTrade delegateTrade) throws SymbolParseException, CustomException{
        DelegateTradeMatchResult delegateTradeMatchResult = new DelegateTradeMatchResult();
        List<MarketDepth> marketDepthRecords =
                marketDepthService.getMatchPriceRecords(marketDepth);
        TradeCoin tradeCoin = TradeUtils.getTradeCoin(marketDepth.getSymbol());
        BigDecimal remainQuantity = delegateTrade.getRemainQuantity();
        BigDecimal matchedQuantity = new BigDecimal(0);
        int status = delegateTrade.getStatus();
        int i = 0;
        for(MarketDepth record : marketDepthRecords){
            UserKnockdownTrade knockdownTrade = new UserKnockdownTrade();
            knockdownTrade.setPrice(new BigDecimal(record.getPrice()));
            knockdownTrade.setUserId(delegateTrade.getUserId());
            if(delegateTrade.getOperateType() == MarketDepthType.asks.ordinal()){
                knockdownTrade.setAskTradeId(delegateTrade.getId());
                knockdownTrade.setBidTradeId(delegateTrade.getId() + i);
            }else if(delegateTrade.getOperateType() == MarketDepthType.bids.ordinal()){
                knockdownTrade.setBidTradeId(delegateTrade.getId());
                knockdownTrade.setAskTradeId(delegateTrade.getId() + i);
            }
            ++i;
            BigDecimal marketPrice = PlatformUtil.getMarketPrice(tradeCoin);
            knockdownTrade.setMarketPrice(marketPrice);
            BigDecimal differQuantity = remainQuantity.subtract(new BigDecimal(record.getAmount()));
            if(differQuantity.compareTo(new BigDecimal(0)) <= 0){
                knockdownTrade.setQuantity(remainQuantity);
                matchedQuantity = matchedQuantity.add(remainQuantity);
                userKnockdownTradeService.insert(knockdownTrade);
                status = DelegateTradeStatus.complete.ordinal();
                break;
            }else {
                knockdownTrade.setQuantity(new BigDecimal(record.getAmount()));
                userKnockdownTradeService.insert(knockdownTrade);
                matchedQuantity = matchedQuantity.add(new BigDecimal(record.getAmount()));
                remainQuantity = differQuantity;
                status = DelegateTradeStatus.processing.ordinal();
            }
        }
        delegateTradeMatchResult.setStatus(status);
        delegateTradeMatchResult.setMatchedQuantity(matchedQuantity);
        delegateTradeMatchResult.setPrice(delegateTrade.getPrice());
        return delegateTradeMatchResult;
    }

    private UserCoinParam getFreezeUserCoinParam(UserDelegateTrade userDelegateTrade, String symbol) throws SymbolParseException{
        UserCoinParam freezeUserCoinParam = new UserCoinParam();
        TradeCoin tradeCoin = TradeUtils.getTradeCoin(symbol);
        if(MarketDepthType.isAsksType(userDelegateTrade.getOperateType())){
            freezeUserCoinParam.setCoin(tradeCoin.getBaseCoin());
            freezeUserCoinParam.setQuantity(userDelegateTrade.getAmount());
        }else if(MarketDepthType.isBidsType(userDelegateTrade.getOperateType())){
            freezeUserCoinParam.setCoin(tradeCoin.getTradeCoin());
            freezeUserCoinParam.setQuantity(userDelegateTrade.getQuantity());
        }
        freezeUserCoinParam.setUserId(userDelegateTrade.getUserId());
        return freezeUserCoinParam;
    }

    private UserCoinParam getAddUserCoinParam(UserDelegateTrade userDelegateTrade, String symbol) throws SymbolParseException{
        UserCoinParam freezeUserCoinParam = new UserCoinParam();
        TradeCoin tradeCoin = TradeUtils.getTradeCoin(symbol);
        if(MarketDepthType.isBidsType(userDelegateTrade.getOperateType())){
            freezeUserCoinParam.setCoin(tradeCoin.getBaseCoin());
            freezeUserCoinParam.setQuantity(userDelegateTrade.getAmount());
        }else if(MarketDepthType.isAsksType(userDelegateTrade.getOperateType())){
            freezeUserCoinParam.setCoin(tradeCoin.getTradeCoin());
            freezeUserCoinParam.setQuantity(userDelegateTrade.getQuantity());
        }
        freezeUserCoinParam.setUserId(userDelegateTrade.getUserId());
        return freezeUserCoinParam;
    }

    private String getTradeLock(Integer id){
        return tradeLockPrefix + id;
    }
}
