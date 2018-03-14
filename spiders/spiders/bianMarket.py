# coding:utf-8 #
import time
import json
import urllib.parse
import urllib.request
import infoDao
import datetime

def get_times_from_timestamp(ts):
    try:
        d = datetime.datetime.fromtimestamp(ts)
        str1 = d.strftime("%Y-%m-%d %H:%M:%S.%f")
        # 2015-08-28 16:43:37.283000'
        return str1
    except Exception as e:
        # print e
        return ''


##########################################
#
# 获取币安的行情数据
#
#########################
class restbian:
    dao = infoDao.infoDao();

    def getSymbly(self):
        return ['WTCBTC', 'ETHBTC', 'VENBTC', 'XRPBTC', 'TRXBTC', 'DGDBTC', 'NANOBTC', 'NEOBTC', 'ICXBTC', 'BNBBTC', 'ADABTC',
                'BCDBTC', 'ETCBTC', 'ICNBTC', 'LTCBTC', 'EOSBTC', 'CMTBTC', 'MTLBTC', 'XMRBTC', 'IOTABTC', 'NCASHBTC', 'NULSBTC',
                'GVTBTC', 'BCPTBTC', 'XLMBTC', 'BCCBTC', 'OMGBTC', 'NEBLBTC', 'BRDBTC', 'INSBTC', 'HSRBTC', 'XVGBTC', 'SNTBTC',
                'ZILBTC', 'LSKBTC', 'IOSTBTC', 'STRATBTC', 'POEBTC', 'GXSBTC', 'BQXBTC', 'RCNBTC', 'ELFBTC', 'GASBTC', 'KNCBTC',
                'ZRXBTC', 'BTSBTC', 'BTGBTC', 'POABTC', 'ENJBTC', 'APPCBTC', 'QTUMBTC', 'VIBEBTC', 'AIONBTC', 'GTOBTC', 'ARNBTC',
                'ZECBTC', 'DASHBTC', 'CNDBTC', 'OSTBTC', 'PPTBTC', 'ARKBTC', 'FUNBTC', 'BLZBTC', 'TNBBTC', 'VIBBTC', 'ENGBTC',
                'STORJBTC', 'WINGSBTC', 'RPXBTC', 'LINKBTC', 'LRCBTC', 'SUBBTC', 'LENDBTC','QSPBTC', 'LUNBTC', 'SALTBTC', 'WAVESBTC',
                'WABIBTC', 'AMBBTC', 'PIVXBTC', 'REQBTC', 'CDTBTC', 'CTRBTC', 'AEBTC', 'ADXBTC', 'STEEMBTC', 'MCOBTC', 'TRIGBTC', 'MODBTC',
                'XZCBTC', 'ASTBTC', 'POWRBTC', 'BATBTC', 'FUELBTC', 'SNGLSBTC', 'MANABTC', 'DLTBTC', 'KMDBTC', 'DNTBTC', 'EDOBTC', 'CHATBTC',
                'MTHBTC', 'YOYOBTC', 'NAVBTC', 'RDNBTC', 'RLCBTC', 'TNTBTC', 'EVXBTC', 'SNMBTC', 'MDABTC', 'BNTBTC', 'OAXBTC', 'VIABTC',
                'TRXETH', 'VENETH', 'XRPETH', 'WTCETH', 'EOSETH', 'NEOETH', 'DGDETH', 'NANOETH', 'ICXETH', 'ADAETH', 'IOTAETH', 'CMTETH',
                'LTCETH', 'BNBETH', 'ICNETH', 'ETCETH', 'NULSETH', 'BRDETH', 'BCDETH', 'OMGETH', 'NCASHETH', 'XLMETH', 'BCPTETH', 'XMRETH',
                'POAETH', 'GXSETH', 'GVTETH', 'ZILETH', 'NEBLETH', 'ELFETH', 'BQXETH', 'BLZETH', 'BCCETH', 'XVGETH', 'SNTETH', 'ZRXETH',
                'QTUMETH', 'GTOETH', 'LSKETH', 'REQETH', 'QSPETH', 'KNCETH', 'IOSTETH', 'AIONETH', 'ENGETH', 'POEETH', 'BTSETH', 'DASHETH',
                'LRCETH', 'LINKETH', 'LENDETH', 'CNDETH', 'PPTETH', 'STRATETH', 'FUNETH', 'ENJETH', 'AMBETH', 'TNBETH', 'AEETH', 'RPXETH',
                'OSTETH', 'VIBEETH', 'SUBETH', 'ADXETH', 'APPCETH', 'ZECETH', 'MTLETH', 'CDTETH', 'CTRETH', 'BTGETH', 'ARKETH', 'HSRETH',
                'MODETH', 'RCNETH', 'LUNETH', 'WABIETH', 'BATETH', 'STORJETH', 'INSETH', 'ARNETH', 'POWRETH', 'FUELETH', 'RDNETH', 'SALTETH',
                'VIBETH', 'ASTETH', 'WAVESETH', 'WINGSETH', 'MANAETH', 'SNGLSETH', 'CHATETH', 'MCOETH', 'RLCETH', 'MDAETH', 'TNTETH', 'BNTETH',
                'STEEMETH', 'TRIGETH', 'DLTETH', 'MTHETH', 'PIVXETH', 'YOYOETH', 'OAXETH', 'XZCETH', 'DNTETH', 'EVXETH', 'KMDETH', 'NAVETH',
                'EDOETH', 'SNMETH', 'VIAETH']


    def getMarket(self, symble):
        """获取币安行情数据"""
        url = "https://api.binance.com/api/v1/klines?symbol=" + symble + "&limit=1&interval=1m"
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_div = tmp
        except KeyError:
            return None
        for item in all_div:
            amountbian = 0
            openbian = item[1]
            closebian = item[4]
            lowbian = item[3]
            highbian = item[2]
            volbian = item[5]

            insertStr = " \"bian\",\"{}\",{},{},{},{},{},{}".format(symble, openbian, closebian,
                                                                    lowbian, highbian, volbian, amountbian)
            self.dao.dels("bian", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

            # print(item)
            # print(insertStr)

    def getdepth(self, symble):
        """获取挂单数据"""
        url = "https://www.binance.com/api/v1/depth?symbol=" + symble + "&limit=5"
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_bids = tmp['bids']
            all_asks = tmp['asks']
        except KeyError:
            return None
        if len(all_bids) != 0:
            self.dao.dels("bian", symble, tablename="vt_market_depth")
        if len(all_bids) > 5:
            all_bids = all_bids[:5]
        if len(all_asks) > 5:
            all_asks = all_asks[:5]
        for item in all_asks:
            insertStr = " \"bian\",\"{}\",\"asks\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)

        for item in all_bids:
            insertStr = " \"bian\",\"{}\",\"bids\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)

    # / api / v1 / historicalTrades

    def getHistory(self, symble):
        """获取历史成交记录"""
        url = "https://www.binance.com/api/v1/historicalTrades?symbol=" + symble + "&limit=10"
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_history = tmp
        except KeyError:
            return None
        if len(all_history) != 0:
            self.dao.dels("bian", symble, tablename="vt_market_trade_history")
        if len(all_history) > 10:
            all_history = all_history[:10]
        for item in all_history:
            direction = 'sell'
            if item['isBuyerMaker']:
                direction = 'buy'
            insertStr = " \"bian\",\"{}\",\"{}\",{},{},\"{}\"".format(symble, direction, item['price'], item['qty'],
                                                                      get_times_from_timestamp(
                                                                          int(item['time']) / 1000.0))
            self.dao.saveInfo(tableName="vt_market_trade_history",
                              columesName="platform,symbol,direction,price, amount,ts",
                              values=insertStr)

    def getData(self):
        """获取行情和挂单数据总接口"""
        ax = self.getSymbly()
        for i in ax:
            self.getMarket(i)
            self.getdepth(i)
            self.getHistory(i)

if __name__ == "__main__":
    """获取市场行情主入口函数"""
    bian = restbian()
    inslist = [bian]
    for ins in inslist:
       try:
          ins.dao.reconnect()
          ins.getData();
       except Exception as e:
           continue;