# coding:utf-8 #
import json
import urllib.parse
import urllib.request
import infoDao


class restokex:
    dao = infoDao.infoDao();

    def getSymbly(self):
        return ['btc_usd', 'ltc_usd', 'eth_usd', 'etc_usd', 'bch_usd']

    def getMarket(self, symble):
        url = "https://www.okex.com/api/v1/future_kline.do?symbol=" + symble + "&type=1min&contract_type=this_week&size=1"
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
            amountokex = 0
            openokex = item[1]
            closeokex = item[4]
            lowokex = item[3]
            highokex = item[2]
            volokex = item[5]

            insertStr = " \"okex\",{},\"{}\",{},{},{},{},{},{}".format(item[0] / 1000000, symble, openokex, closeokex,
                                                                       lowokex, highokex, volokex, amountokex)
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

            print(item)
            print(insertStr)

    def getdepth(self, symble):
        url = "https://www.okex.com/api/v1/future_depth.do?symbol=" + symble + "&contract_type=this_week&size=5"
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
            self.dao.dels("okex", symble, tablename="vt_market_depth")
        if len(all_bids) > 5:
            all_bids = all_bids[:5]
        if len(all_asks) > 5:
            all_asks = all_asks[:5]
        for item in all_asks:
            insertStr = " \"okex\",\"{}\",\"asks\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)

        for item in all_bids:
            insertStr = " \"okex\",\"{}\",\"bids\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)


def getData():
    okexinstance = restokex()
    ax = okexinstance.getSymbly()
    for i in ax:
        okexinstance.getMarket(i)
        okexinstance.getdepth(i)
if __name__ == "__main__":
    getData()
