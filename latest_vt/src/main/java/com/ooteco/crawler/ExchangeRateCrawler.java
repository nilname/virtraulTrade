package com.ooteco.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ExchangeRateCrawler {

    private String url = "https://www.baidu.com/s?wd=USD%20CNY&rsv_spt=1";
    private Document getDocument(){
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public BigDecimal extract(){

        Document document = getDocument();
        String text = document.select(".op_exrate_result").text();
        text = text.substring(text.indexOf("=")+1,text.indexOf("人"));
        BigDecimal exchangeRate = new BigDecimal(text);
        return exchangeRate;
    }

    public static void main(String[] args){
        new ExchangeRateCrawler().extract();
    }
}
