# coding:utf-8 #
import json
import urllib.parse
import urllib.request
import infoDao


class restbian:
    dao = infoDao.infoDao();

    def getSymbly(self):
        return ['BNBBTC']

    def getMarket(self, symble):
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

            insertStr = " \"bian\",{},\"{}\",{},{},{},{},{},{}".format(item[0] / 1000000, symble, openbian, closebian,
                                                                       lowbian, highbian, volbian, amountbian)
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

            print(item)
            print(insertStr)

    def getdepth(self, symble):
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


def getData():
    bianinstance = restbian()
    ax = bianinstance.getSymbly()
    for i in ax:
        bianinstance.getMarket(i)
        bianinstance.getdepth(i)

if __name__ == "__main__":
        getData()
