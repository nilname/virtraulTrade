package com.ooteco.model.ext;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MarketInfoMain implements Serializable {
    private static final long serialVersionUID = -3562550857760039655L;
    private BigDecimal vol;//成交额

    private String symbol;//名称

    private BigDecimal newsTrade;//最新的成交价格

    private BigDecimal change;//涨跌幅

    private BigDecimal exchangePrice;//人民币换算

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getNewsTrade() {
        return newsTrade;
    }

    public void setNewsTrade(BigDecimal newsTrade) {
        this.newsTrade = newsTrade;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(BigDecimal exchangePrice) {
        this.exchangePrice = exchangePrice;
    }



}
