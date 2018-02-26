# coding:utf-8 #
import json
import urllib.parse
import urllib.request
import infoDao


class resthuobi:
    dao = infoDao.infoDao();

    def getSymbly(self):
        url = "https://api.huobi.pro/v1/common/symbols"
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        # 循环div获取详细信息
        try:
            all_div = tmp['data']
        except KeyError:
            return None
        res = []
        for item in all_div:
            basename = item["base-currency"]
            quotename = item["quote-currency"]
            res.append(basename +"_"+ quotename)
        return res

    def getMarket(self, symble):
        nsymbol=symble.replace("_","")
        url = "https://api.huobi.pro/market/history/kline?period=1min&size=1&symbol=" + nsymbol
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_div = tmp['data']
        except KeyError:
            return None
        res = []
        for item in all_div:
            amounthuobi = item["amount"]
            openhuobi = item["open"]
            closehuobi = item["close"]
            lowhuobi = item["low"]
            highhuobi = item["high"]
            volhuobi = item["vol"]

            insertStr = " \"huobi\",{},\"{}\",{},{},{},{},{},{}".format(item['id'], symble, openhuobi, closehuobi,
                                                                        lowhuobi, highhuobi, volhuobi, amounthuobi)
            self.dao.dels("huobi", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

            # print(item)
            # print(insertStr)

    def getdepth(self, symble):
        nsymbol = symble.replace("_", "")
        url = "https://api.huobi.pro/market/depth?symbol=" + nsymbol + "&type=step5"
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_bids = tmp['tick']['bids']
            all_asks = tmp['tick']['asks']
        except KeyError:
            return None
        if len(all_bids) != 0:
            self.dao.dels("huobi", symble, tablename="vt_market_depth")
        if len(all_bids) > 5:
            all_bids = all_bids[:5]
        if len(all_asks) > 5:
            all_asks = all_asks[:5]
        for item in all_asks:
            insertStr = " \"huobi\",\"{}\",\"asks\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)

        for item in all_bids:
            insertStr = " \"huobi\",\"{}\",\"bids\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)


def getData():
    huobiinstance = resthuobi()
    ax = huobiinstance.getSymbly()
    for i in ax:
        huobiinstance.getMarket(i)
        huobiinstance.getdepth(i)


if __name__ == "__main__":
    getData()
