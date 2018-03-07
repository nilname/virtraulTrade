package com.ooteco.service;

import com.ooteco.exceptions.CustomException;
import com.ooteco.exceptions.SymbolParseException;
import com.ooteco.model.MarketTick;
import com.ooteco.model.TradeCoin;
import com.ooteco.model.UserCoin;
import com.ooteco.model.UserKnockdownTrade;
import com.ooteco.model.ext.UserDelegateTradeExt;
import com.ooteco.model.response.UserAssetResponse;
import com.ooteco.utils.PlatformUtil;
import com.ooteco.utils.TradeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private UserDelegateTradeService userDelegateTradeService;
    @Autowired
    private MarketTickService marketTickService;

    public UserAssetResponse getUserAsset(Integer id) throws SymbolParseException, CustomException{
        UserAssetResponse response = new UserAssetResponse();
        List<UserCoin> userCoins =  userCoinService.getUserCoins(id);
        BigDecimal asset = new BigDecimal(0);
        for(UserCoin userCoin : userCoins){
            BigDecimal marketPrice = PlatformUtil.getMarketPrice(new TradeCoin(userCoin.getCoin()));
            asset = asset.add(marketPrice.multiply(userCoin.getTotalQuantity()));
        }
        response.setList(userCoins);
        response.setAsset(asset);
        response.setDifferAmount(calculateUserDifferAmount(id));
        return response;
    }

    public BigDecimal calculateUserDifferAmount(Integer userId) throws SymbolParseException, CustomException{
        BigDecimal asksTradeDifferAmount = calculateUserAsksDifferAmount(userId);
        BigDecimal bidsTradeDifferAmount = calculateUserBidsDifferAmount(userId);
        return asksTradeDifferAmount.add(bidsTradeDifferAmount);
    }

    public BigDecimal calculateUserAsksDifferAmount(Integer userId) throws SymbolParseException, CustomException{
        List<UserDelegateTradeExt> asksTrades = userDelegateTradeService.getUserAsksDelegateTrades(userId);
        return calculateUserDelegateTradeDifferAmount(asksTrades);
    }

    public BigDecimal calculateUserBidsDifferAmount(Integer userId) throws SymbolParseException, CustomException{
        List<UserDelegateTradeExt> bidsTrades = userDelegateTradeService.getUserBidsDelegateTrades(userId);
        return calculateUserDelegateTradeDifferAmount(bidsTrades);
    }

    public BigDecimal calculateUserProfit(Integer userId) throws SymbolParseException, CustomException{
        BigDecimal asksTradeProfit = calculateUserAsksProfit(userId);
        BigDecimal bidsTradeProfit = calculateUserBidsProfit(userId);
        return asksTradeProfit.add(bidsTradeProfit);
    }

    private BigDecimal calculateUserAsksProfit(Integer userId) throws SymbolParseException, CustomException{
        List<UserDelegateTradeExt> asksTrades = userDelegateTradeService.getUserAsksDelegateTrades(userId);
        return calculateUserDelegateTradeProfit(asksTrades);
    }

    private BigDecimal calculateUserBidsProfit(Integer userId) throws SymbolParseException, CustomException{
        List<UserDelegateTradeExt> bidsTrades = userDelegateTradeService.getUserBidsDelegateTrades(userId);
        return calculateUserDelegateTradeProfit(bidsTrades);
    }

    private BigDecimal calculateUserDelegateTradeProfit(List<UserDelegateTradeExt> trades) throws SymbolParseException, CustomException{
        BigDecimal profit = new BigDecimal(0);
        for(UserDelegateTradeExt delegateTrade : trades){
            BigDecimal delegatePrice = delegateTrade.getPrice();
            BigDecimal knockdownAmount = new BigDecimal(0);
            for(UserKnockdownTrade knockdownTrade : delegateTrade.getKnockdownTrades()){
                BigDecimal knockdownQuantity = knockdownTrade.getQuantity();
                BigDecimal knockdownPrice = knockdownTrade.getPrice();
                knockdownAmount = knockdownAmount.add(knockdownPrice.subtract(delegatePrice).abs().multiply(knockdownQuantity));
            }
            profit = profit.add(knockdownAmount.multiply(getMarketPrice(delegateTrade.getTradeType())));
        }
        return profit;
    }

    private BigDecimal calculateUserDelegateTradeDifferAmount(List<UserDelegateTradeExt> trades) throws SymbolParseException, CustomException{
        BigDecimal differAmount = new BigDecimal(0);
        for(UserDelegateTradeExt delegateTrade : trades){
            BigDecimal knockdownAmount = new BigDecimal(0);
            for(UserKnockdownTrade knockdownTrade : delegateTrade.getKnockdownTrades()){
                BigDecimal knockdownQuantity = knockdownTrade.getQuantity();
                BigDecimal knockdownPrice = knockdownTrade.getPrice();
                BigDecimal marketPrice = knockdownTrade.getMarketPrice();
                knockdownAmount = knockdownAmount.add(knockdownPrice.subtract(marketPrice).multiply(knockdownQuantity));
            }
            differAmount = differAmount.add(knockdownAmount.multiply(getMarketPrice(delegateTrade.getTradeType())));
        }
        return differAmount;
    }

    private BigDecimal getMarketPrice(Integer tradeType) throws SymbolParseException, CustomException{
        MarketTick platformTradeType = marketTickService.get(tradeType);
        TradeCoin tradeCoin = TradeUtils.getTradeCoin(platformTradeType.getSymbol());
        return PlatformUtil.getMarketPrice(tradeCoin);
    }
}
