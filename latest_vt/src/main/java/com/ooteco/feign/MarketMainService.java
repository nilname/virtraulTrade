package com.ooteco.feign;

import com.ooteco.constant.MarketType;
import com.ooteco.controller.AppCustomController;
import com.ooteco.crawler.ExchangeRateCrawler;
import com.ooteco.mapper.MarketDepthMapper;
import com.ooteco.mapper.MarketTickMapper;
import com.ooteco.mapper.UserAccountMapper;
import com.ooteco.model.*;
import com.ooteco.model.ext.*;
import com.ooteco.service.TokenService;
import com.ooteco.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MarketMainService {
    private static Logger log = Logger.getLogger(AppCustomController.class);
    @Autowired
    private ZbMarketService zbMarketService;

    @Autowired
    private HuobiMarketService huobiMarketService;
    @Autowired
    private BinanceMarketService binanceMarketService;
    @Autowired
    private OkexMarketService okexMarketService;
    @Autowired
    private UserAccountMapper userAccountMapper;


    @Autowired
    private RedisTemplate redisTemplate;//操作其它类型数据

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ExchangeRateCrawler exchangeRateCrawler;

    @Autowired
    private MarketTickMapper marketTickMapper;
    @Autowired
    private MarketDepthMapper marketDepthMapper;
    @Scheduled(cron = "0/2 * * * * ?")
    public void marketInfoScheduled()  {
        log.info("start scheduled match");
        MarketTickExample example = new MarketTickExample();
        MarketTickExample.Criteria criteria = example.createCriteria();
        criteria.andPlatformNotEqualTo("zb");
        List<MarketTick> marketTicks = marketTickMapper.selectByExample(null);
        //List<MarketInfoMain> zbMarketInfoMainsTweentyFour = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> huobiMarketInfoMainsTweentyFour = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> okexMarketInfoMainsTweentyFour = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> bianMarketInfoMainsTweentyFour = new ArrayList<MarketInfoMain>();
       //List<MarketInfoMain> zbMarketInfoMainsToday = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> huobiMarketInfoMainsToday = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> okexMarketInfoMainsToday = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> bianMarketInfoMainsToday = new ArrayList<MarketInfoMain>();
        for(MarketTick marketTick :marketTicks){
            MarketInfoMain marketInfoMainTweentyFour = new MarketInfoMain();
            MarketInfoMain marketInfoMainToday = new MarketInfoMain();
                if/* (MarketType.zb.key.equals(marketTick.getPlatform())) {

                    marketInfoMainTweentyFour = dealZbDataTweentyFour( marketTick);
                    marketInfoMainToday = dealZbDataToday(marketTick);
                    zbMarketInfoMainsTweentyFour.add(marketInfoMainTweentyFour);
                    zbMarketInfoMainsToday.add(marketInfoMainToday);
                } else if */(MarketType.huobi.key.equals(marketTick.getPlatform())) {

                    marketInfoMainTweentyFour = dealHuobiDataTweentyFour( marketTick);
                    marketInfoMainToday = dealHuobiDataToday(marketTick);
                    if(marketInfoMainTweentyFour!=null) {
                        huobiMarketInfoMainsTweentyFour.add(marketInfoMainTweentyFour);
                    }
                    if(marketInfoMainToday!=null) {
                        huobiMarketInfoMainsToday.add(marketInfoMainToday);
                    }

                } else if (MarketType.okex.key.equals(marketTick.getPlatform())) {

                    marketInfoMainTweentyFour = dealOkexDataTweentyFour(marketTick);
                    marketInfoMainToday = dealOkexDataToday(marketTick);

                    okexMarketInfoMainsTweentyFour.add(marketInfoMainTweentyFour);
                    okexMarketInfoMainsToday.add(marketInfoMainToday);

                } else if (MarketType.bian.key.equals(marketTick.getPlatform())) {
                  //  binanceMarketService.get(marketTick.getSymbol(), "1d", 1, new Date().getTime() + "", new Date().getTime() + "");
                    marketInfoMainTweentyFour = dealBianDataTweentyFour(marketTick);
                    marketInfoMainToday = dealBianDataToday(marketTick);
                    bianMarketInfoMainsTweentyFour.add(marketInfoMainTweentyFour);
                    bianMarketInfoMainsToday.add(marketInfoMainToday);

                }
        }
        System.out.println("============================"+redisTemplate.opsForList().getOperations().hasKey("ooteco:market:bian_market_24"));
        redisTemplate.delete("ooteco:market:bian_market_24".getBytes());
        redisTemplate.delete("ooteco:market:okex_market_24".getBytes());
        redisTemplate.delete("ooteco:market:huobi_market_24".getBytes());
        redisTemplate.delete("ooteco:market:zb_market_24".getBytes());
        redisTemplate.delete("ooteco:market:bian_market_00".getBytes());
        redisTemplate.delete("ooteco:market:okex_market_00".getBytes());
        redisTemplate.delete("ooteco:market:huobi_market_00".getBytes());
        redisTemplate.delete("ooteco:market:zb_market_00".getBytes());
        System.out.println("============================"+redisTemplate.opsForList().getOperations().hasKey("ooteco:market:bian_market_24"));
        redisTemplate.opsForList().rightPushAll("ooteco:market:bian_market_24",bianMarketInfoMainsTweentyFour);
        System.out.println("============================"+redisTemplate.opsForList().getOperations().hasKey("ooteco:market:bian_market_24"));
        //redisTemplate.opsForList().rightPushAll("ooteco:market:okex_market_24",okexMarketInfoMainsTweentyFour);
        redisTemplate.opsForList().rightPushAll("huobi_market_24",huobiMarketInfoMainsTweentyFour);
        //redisTemplate.opsForList().rightPushAll("ooteco:market:zb_market_24",zbMarketInfoMainsTweentyFour);
        redisTemplate.opsForList().rightPushAll("ooteco:market:bian_market_00",bianMarketInfoMainsToday);
        //redisTemplate.opsForList().rightPushAll("ooteco:market:okex_market_00",okexMarketInfoMainsToday);
        redisTemplate.opsForList().rightPushAll("ooteco:market:huobi_market_00",huobiMarketInfoMainsToday);
        //redisTemplate.opsForList().rightPushAll("ooteco:market:zb_market_00",zbMarketInfoMainsToday);
        log.info("scheduled match over");
    }

   @Scheduled(cron = "0/2 * * * * ?")
    public void zbmarketInfoScheduled()  {
        MarketTickExample example = new MarketTickExample();
        MarketTickExample.Criteria criteria = example.createCriteria();
        criteria.andPlatformEqualTo("zb");
        List<MarketTick> marketTicks = marketTickMapper.selectByExample(null);
        List<MarketInfoMain> zbMarketInfoMainsTweentyFour = new ArrayList<MarketInfoMain>();
        List<MarketInfoMain> zbMarketInfoMainsToday = new ArrayList<MarketInfoMain>();
        for(MarketTick marketTick :marketTicks){
            MarketInfoMain marketInfoMainTweentyFour = new MarketInfoMain();
            MarketInfoMain marketInfoMainToday = new MarketInfoMain();
            marketInfoMainTweentyFour = dealZbDataTweentyFour( marketTick);
            marketInfoMainToday = dealZbDataToday(marketTick);
            zbMarketInfoMainsTweentyFour.add(marketInfoMainTweentyFour);
            zbMarketInfoMainsToday.add(marketInfoMainToday);

        }
        System.out.println("============================"+redisTemplate.opsForList().getOperations().hasKey("ooteco:market:bian_market_24"));
        redisTemplate.delete("ooteco:market:bian_market_24".getBytes());
        redisTemplate.delete("ooteco:market:okex_market_24".getBytes());
        redisTemplate.delete("ooteco:market:huobi_market_24".getBytes());
        redisTemplate.delete("ooteco:market:zb_market_24".getBytes());
        redisTemplate.delete("ooteco:market:bian_market_00".getBytes());
        redisTemplate.delete("ooteco:market:okex_market_00".getBytes());
        redisTemplate.delete("ooteco:market:huobi_market_00".getBytes());
        redisTemplate.delete("ooteco:market:zb_market_00".getBytes());
        System.out.println("============================"+redisTemplate.opsForList().getOperations().hasKey("ooteco:market:bian_market_24"));
        System.out.println("============================"+redisTemplate.opsForList().getOperations().hasKey("ooteco:market:bian_market_24"));
        redisTemplate.opsForList().rightPushAll("ooteco:market:zb_market_24",zbMarketInfoMainsTweentyFour);
        redisTemplate.opsForList().rightPushAll("ooteco:market:zb_market_00",zbMarketInfoMainsToday);
        log.info("scheduled match over");
    }

    /**
     * 处理huobi信息24小时
     * @param
     * @return
     */
    public MarketInfoMain dealHuobiDataTweentyFour(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        //
        MarketDepthExample marketDepthExample = new MarketDepthExample();
        MarketDepthExample.Criteria depthcriteria = marketDepthExample.createCriteria();
        depthcriteria.andPlatformEqualTo(marketTick.getPlatform());
        depthcriteria.andSymbolEqualTo(marketTick.getSymbol());
        List<MarketDepth> depthlist = marketDepthMapper.selectByExample(marketDepthExample);
        List<HuobiMarketInfoResp> huobiMarketInfoResps = huobiMarketService.get(marketTick.getSymbol().replace("_",""),"1min", 1440);
        if(huobiMarketInfoResps == null || huobiMarketInfoResps.size()==0){
            log.info(marketTick.getSymbol()+"获取的k线图信息不存在");
            return null;
        }
        BigDecimal vol = new BigDecimal(0);
        for(HuobiMarketInfoResp huobiMarketInfoResp:huobiMarketInfoResps){
            vol = vol.add(huobiMarketInfoResp.getVol());
        }

        BigDecimal change = huobiMarketInfoResps.get(0).getClose().divide(huobiMarketInfoResps.get(huobiMarketInfoResps.size()-1).getClose(),2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(vol);
        marketInfoMain.setSymbol(marketTick.getSymbol());

        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        MarketTickExample marketTickExample = new MarketTickExample();
        MarketTickExample.Criteria criteria = marketTickExample.createCriteria();
        criteria.andPlatformEqualTo(marketTick.getPlatform());
        criteria.andSymbolEqualTo(marketTick.getSymbol());
        List<MarketTick> list = marketTickMapper.selectByExample(marketTickExample);
        if(list!=null &&list.size()>0)
        {
            marketInfoMain.setNewsTrade(new BigDecimal(list.get(0).getClose()));
            marketInfoMain.setExchangePrice(new BigDecimal(list.get(0).getClose()).multiply(exchangeRate));
        }
        //

        return marketInfoMain;
    }

    /**
     * 处理huobi信息00时间段
     * @param
     * @return
     */
    public MarketInfoMain dealHuobiDataToday(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        List<HuobiMarketInfoResp> huobiMarketInfoResps = huobiMarketService.get(marketTick.getSymbol().replace("_",""),"1min", 1);
        if(huobiMarketInfoResps == null || huobiMarketInfoResps.size()==0){
            log.info(marketTick.getSymbol()+"获取的k线图信息不存在");
            return null;
        }
        BigDecimal vol = huobiMarketInfoResps.get(0).getVol();
        BigDecimal change = huobiMarketInfoResps.get(0).getClose().divide(huobiMarketInfoResps.get(0).getOpen(),2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(vol);
        marketInfoMain.setSymbol(marketTick.getSymbol());
        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        marketInfoMain.setNewsTrade(new BigDecimal(marketTick.getClose()));
        marketInfoMain.setExchangePrice(new BigDecimal(marketTick.getClose()).multiply(exchangeRate));
        return marketInfoMain;
    }

    /**
     * 处理Zb信息24小时
     * @param
     * @return
     */
    public MarketInfoMain dealZbDataTweentyFour(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-1);
        ZbMarketInfoResp zbMarketInfoRespList = zbMarketService.get(marketTick.getSymbol(),"1min", rightNow.getTimeInMillis()+"",1);

        List<String[]> data = zbMarketInfoRespList.getData();
        /*if(Long.parseLong(data.get(0)[0])-rightNow.getTimeInMillis()>36000000){
            zbMarketInfoRespList = zbMarketService.get(marketTick.getSymbol(),"3min", rightNow.getTimeInMillis()+"",1);
            data = zbMarketInfoRespList.getData();
        }*/
        BigDecimal vol = new BigDecimal(0);
        for(String[] v:data){
            vol =vol.add(new BigDecimal(v[5]));
        }
        BigDecimal change = new BigDecimal(data.get(data.size()-1)[4]).divide(new BigDecimal(data.get(0)[1]),2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(vol);
        marketInfoMain.setSymbol(marketTick.getSymbol());
        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        marketInfoMain.setNewsTrade(new BigDecimal(marketTick.getClose()));
        marketInfoMain.setExchangePrice(new BigDecimal(marketTick.getClose()).multiply(exchangeRate));

        return marketInfoMain;
    }


    public static void main(String[] args){

         RedisTemplate redisTemplate = new RedisTemplate(); //操作其它类型数据
        List<String> list = new ArrayList<>();
        list.add("ASD");
        list.add("aaa");

        redisTemplate.opsForList().rightPushAll("test_24",list);
         System.out.println("==============="+redisTemplate.opsForValue().get("test_24"));
      /*  Calendar rightNow1 = Calendar.getInstance();
        rightNow1.setTime(new Date());
        rightNow1.add(Calendar.HOUR,-8);
        Calendar rightNow2 = Calendar.getInstance();
        rightNow2.setTime(new Date());
        rightNow2.add(Calendar.HOUR,-16);
        Calendar rightNow3 = Calendar.getInstance();
        rightNow2.setTime(new Date());
        rightNow2.add(Calendar.HOUR,-24);
        String symbol = "BNBBTC";
        String result =new BinanceMarketService().get(symbol,"1m", 1440,rightNow3.getTimeInMillis()+"",rightNow2.getTimeInMillis()+"");
        result += new BinanceMarketService().get(symbol,"1m", 1440,rightNow2.getTimeInMillis()+"",rightNow1.getTimeInMillis()+"");
        result += new BinanceMarketService().get(symbol,"1m", 1440,rightNow1.getTimeInMillis()+"",Calendar.getInstance().getTimeInMillis()+"");
        result = result.replaceAll("]]\\[\\[","]\\[");
        String[] resultSplit = result.split("],\\[");
        System.out.println(rightNow3.getTimeInMillis()+"===============");
        System.out.println(rightNow2.getTimeInMillis()+"===============");
        System.out.println(rightNow1.getTimeInMillis()+"===============");
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(resultSplit.length+"==============="+result);
        System.out.println("==============="+Calendar.getInstance().getTimeInMillis());*/
    }

    /**
     * 处理Zb信息00时间段
     * @param
     * @return
     */
    public MarketInfoMain dealZbDataToday(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-1);
        ZbMarketInfoResp zbMarketInfoResp = zbMarketService.get(marketTick.getSymbol(),"1day", rightNow.getTimeInMillis()+"",1);
        List<String[]> data = zbMarketInfoResp.getData();
        BigDecimal vol = new BigDecimal(data.get(0)[5]);
        BigDecimal change = new BigDecimal(data.get(data.size()-1)[4]).divide(new BigDecimal(data.get(0)[1]),2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(vol);
        marketInfoMain.setSymbol(marketTick.getSymbol());
        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        marketInfoMain.setNewsTrade(new BigDecimal(marketTick.getClose()));
        marketInfoMain.setExchangePrice(new BigDecimal(marketTick.getClose()).multiply(exchangeRate));
        return marketInfoMain;
    }

    /**
     * 处理Bian信息24小时
     * @param
     * @return
     */
    public MarketInfoMain dealBianDataTweentyFour(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        Calendar rightNow1 = Calendar.getInstance();
        rightNow1.setTime(new Date());
        rightNow1.add(Calendar.HOUR,-8);
        Calendar rightNow2 = Calendar.getInstance();
        rightNow2.setTime(new Date());
        rightNow2.add(Calendar.HOUR,-16);
        Calendar rightNow3 = Calendar.getInstance();
        rightNow2.setTime(new Date());
        rightNow2.add(Calendar.HOUR,-24);
        String symbol = marketTick.getSymbol().replaceAll("_","").toUpperCase();
        String result =binanceMarketService.get(symbol,"1m", 1440,rightNow3.getTimeInMillis()+"",rightNow2.getTimeInMillis()+"");
        result += binanceMarketService.get(symbol,"1m", 1440,rightNow2.getTimeInMillis()+"",rightNow1.getTimeInMillis()+"");
        result += binanceMarketService.get(symbol,"1m", 1440,rightNow1.getTimeInMillis()+"",Calendar.getInstance().getTimeInMillis()+"");
        result = result.replaceAll("]]\\[\\[","],\\[");

        result = result.replaceAll("\"","");
        result = result.replaceAll("\\[","");
        result = result.replaceAll("]","");
        String[] resultSplit = result.split(",");
        BigDecimal vol = new BigDecimal(0);
        BigDecimal openValue = new BigDecimal(0);
        BigDecimal endValue = new BigDecimal(0);
        for(int i =0;i>resultSplit.length;i++){
            String temp = resultSplit[i].replaceAll("\\[","");
            String[] tempSplit = resultSplit[i].split(",");
            vol = vol.add(new BigDecimal(tempSplit[5]));
            if(i==0){
                openValue = new BigDecimal(tempSplit[1]);
            }
            if(i==resultSplit.length-1){
                endValue = new BigDecimal(tempSplit[4]);
            }
        }

        return marketInfoMain;
    }

    /**
     * 处理Bian信息00时间段
     * @param
     * @return
     */
    public MarketInfoMain dealBianDataToday(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-1);
        String result = binanceMarketService.get(marketTick.getSymbol(),"1d", 1,rightNow.getTimeInMillis()+"",System.currentTimeMillis()+"");
        result = result.replaceAll("\"","");
        result = result.replaceAll("\\[","");
        result = result.replaceAll("]","");
        String[] tempSplit = result.split(",");
        BigDecimal change =new BigDecimal(tempSplit[4]).divide(new BigDecimal(tempSplit[1]),2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(new BigDecimal(tempSplit[5]));
        marketInfoMain.setSymbol(marketTick.getSymbol());
        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        marketInfoMain.setNewsTrade(new BigDecimal(marketTick.getClose()));
        marketInfoMain.setExchangePrice(new BigDecimal(marketTick.getClose()).multiply(exchangeRate));
        return marketInfoMain;
    }

    /**
     * 处理Okex信息24小时
     * @param
     * @return
     */
    public MarketInfoMain dealOkexDataTweentyFour(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-1);
        String result =okexMarketService.get(marketTick.getSymbol(),"1day", rightNow.getTimeInMillis()+"",1440);
        result = result.substring(1,result.length()-1);
        result = result.replaceAll("\"","");
        String[] resultSplit = result.split("],\\[");

        BigDecimal vol = new BigDecimal(0);
        BigDecimal openValue = new BigDecimal(0);
        BigDecimal endValue = new BigDecimal(0);
        for(int i =0;i>resultSplit.length;i++){
            String temp = resultSplit[i].replaceAll("\\[","");
            String[] tempSplit = resultSplit[i].split(",");
            vol = vol.add(new BigDecimal(tempSplit[5]));
            if(i==0){
                openValue = new BigDecimal(tempSplit[1]);
            }
            if(i==resultSplit.length-1){
                endValue = new BigDecimal(tempSplit[4]);
            }
        }

        BigDecimal change =endValue.divide(openValue,2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(vol);
        marketInfoMain.setSymbol(marketTick.getSymbol());
        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        marketInfoMain.setNewsTrade(new BigDecimal(marketTick.getClose()));
        marketInfoMain.setExchangePrice(new BigDecimal(marketTick.getClose()).multiply(exchangeRate));
        return marketInfoMain;
    }

    /**
     * 处理Okex信息00时间段
     * @param
     * @return
     */
    public MarketInfoMain dealOkexDataToday(MarketTick marketTick){
        MarketInfoMain marketInfoMain = new MarketInfoMain();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-1);
        String result =okexMarketService.get(marketTick.getSymbol(),"1min", rightNow.getTimeInMillis()+"",1);

        result = result.replaceAll("\"","");
        result = result.replaceAll("\\[","");
        result = result.replaceAll("]","");
        String[] tempSplit = result.split(",");
        BigDecimal change =new BigDecimal(tempSplit[4]).divide(new BigDecimal(tempSplit[1]),2, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1));
        marketInfoMain.setChange(change);
        marketInfoMain.setVol(new BigDecimal(tempSplit[5]));
        marketInfoMain.setSymbol(marketTick.getSymbol());
        BigDecimal exchangeRate = new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate"));
        marketInfoMain.setNewsTrade(new BigDecimal(marketTick.getClose()));
        marketInfoMain.setExchangePrice(new BigDecimal(marketTick.getClose()).multiply(exchangeRate));

        return marketInfoMain;
    }

    public List<MarketInfoMain> getMarketData(String token,String platform){
        int userId;
        byte timestate;
        String data = new TokenService().decryptToken(token);
        if(!StringUtil.isEmpty(data)) {
            userId = Integer.parseInt(data);
            UserAccount userAccount =userAccountMapper.selectByPrimaryKey(userId);
            timestate = userAccount.getTimestate();
        }else{
            timestate = 0;
        }
        List<MarketInfoMain> marketInfoMainList = new ArrayList<MarketInfoMain>();
        if(timestate ==1) {
            marketInfoMainList = redisTemplate.opsForList().range(platform+"_market_24", 0, -1);
           // redisTemplate.opsForValue().get();
        }
        else{
            marketInfoMainList= redisTemplate.opsForList().range(platform+"_market_00", 0, -1);
        }
        return marketInfoMainList;
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void exchangeRateCrawlerScheduled() {
        log.info("start exchangeRate scheduled match");
        BigDecimal exchangeRate = exchangeRateCrawler.extract();
        log.info("exchangeRate ed:" +exchangeRate);
        stringRedisTemplate.opsForValue().set("ooteco:market:exchangeRate",exchangeRate.toString());
        log.info("exchangeRate scheduled match over");
    }


    public BigDecimal exchangeRateValue() {
        return new BigDecimal(stringRedisTemplate.opsForValue().get("exchangeRate" ));
    }

}
