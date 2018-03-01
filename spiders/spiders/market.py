# coding:utf-8 #
import time
import json
import urllib.parse
import urllib.request
import infoDao


##################################
#
# 获取ZB 行情数据
#
##################################
class restzb:
    dao = infoDao.infoDao();

    def getSymbly(self):
        """ZB的符号表，即交易对映射表"""
        return ['zb_qc', 'zb_usdt', 'zb_btc', 'btc_qc', 'btc_usdt', 'bcc_usdt', 'ubtc_usdt', 'ltc_usdt', 'eth_usdt',
                'etc_usdt', 'bts_usdt', 'eos_usdt', 'qtum_usdt', 'hsr_usdt', 'xrp_usdt', 'bcd_usdt', 'dash_usdt',
                'bcc_qc', 'ubtc_qc', 'ltc_qc', 'eth_qc', 'etc_qc', 'bts_qc', 'eos_qc', 'qtum_qc', 'hsr_qc', 'xrp_qc',
                'bcd_qc', 'dash_qc', 'bcc_btc', 'ubtc_btc',
                'ltc_btc', 'eth_btc', 'etc_btc', 'bts_btc', 'eos_btc', 'qtum_btc', 'hsr_btc', 'xrp_btc', 'bcd_btc',
                'dash_btc', 'sbtc_usdt', 'sbtc_qc', 'sbtc_btc',
                'ink_usdt', 'ink_qc', 'ink_btc', 'tv_usdt', 'tv_qc', 'tv_btc', 'bcx_usdt', 'bcx_qc', 'bcx_btc',
                'bth_usdt', 'bth_qc',
                'bth_btc', 'lbtc_usdt', 'lbtc_qc', 'lbtc_btc', 'chat_usdt', 'chat_qc', 'chat_btc', 'hlc_usdt', 'hlc_qc',
                'hlc_btc', 'bcw_usdt', 'bcw_qc', 'bcw_btc', 'btp_usdt', 'btp_qc', 'btp_btc', 'bitcny_qc', 'topc_usdt',
                'topc_qc', 'topc_btc', 'ent_usdt', 'ent_qc', 'ent_btc', 'bat_usdt', 'bat_qc', 'bat_btc', '1st_usdt',
                '1st_qc', '1st_btc', 'safe_usdt', 'safe_qc', 'safe_btc', 'qun_usdt', 'qun_qc', 'qun_btc', 'btn_usdt',
                'btn_qc', 'btn_btc', 'true_usdt', 'true_qc',
                'true_btc', 'cdc_usdt', 'cdc_qc', 'cdc_btc', 'ddm_usdt', 'ddm_qc', 'ddm_btc', 'bite_btc', 'hotc_usdt',
                'hotc_qc', 'hotc_btc', 'qc_usdt', 'usdt_qc', 'xuc_qc', 'xuc_btc', 'epc_qc', 'epc_btc', 'bds_qc',
                'bds_btc']

    def getMarket(self, symble):
        """获取ZB的行情数据，通过获取kline数据得到"""
        url = "http://api.zb.com/data/v1/kline?market=" + symble + "&type=1min&size=1"
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_div = tmp['data']
        except KeyError:
            return None
        for item in all_div:
            amountzb = 0
            openzb = item[1]
            closezb = item[4]
            lowzb = item[3]
            highzb = item[2]
            volzb = item[5]
            # 拼接sql子段信息
            insertStr = " \"zb\",{},\"{}\",{},{},{},{},{},{}".format(item[0] / 1000000, symble, openzb, closezb,
                                                                     lowzb, highzb, volzb, amountzb)
            # 先删除已有的信息
            self.dao.dels("zb", symble, tablename="vt_market_tick")
            # 插入新获取的内容
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
                              values=insertStr)
            # 可以用update语句替代
            # print(item)
            # print(insertStr)

    def getdepth(self, symble):
        """获取挂单列表数据 抓取前5个"""
        url = "http://api.zb.com/data/v1/depth?market=" + symble + "&size=5"
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
            self.dao.dels("zb", symble, tablename="vt_market_depth")
        if len(all_bids) > 5:
            all_bids = all_bids[:5]
        if len(all_asks) > 5:
            all_asks = all_asks[:5]
        for item in all_asks:
            insertStr = " \"zb\",\"{}\",\"asks\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)

        for item in all_bids:
            insertStr = " \"zb\",\"{}\",\"bids\",{},{}".format(symble, item[0], item[1])
            self.dao.saveInfo(tableName="vt_market_depth",
                              columesName="platform,symbol,type,price, amount",
                              values=insertStr)
            # print(insertStr)

    def getData(self):
        """获取行情和挂单数据总接口"""
        ax = self.getSymbly()
        for i in ax:
            self.getMarket(i)
            self.getdepth(i)
        time.sleep(1)


##########################################
#
# 获取币安的行情数据
#
#########################
class restbian:
    dao = infoDao.infoDao();

    def getSymbly(self):
        return ['BNBBTC']  # 币安目前只看到一种交易对组合

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

            insertStr = " \"bian\",{},\"{}\",{},{},{},{},{},{}".format(item[0] / 1000000, symble, openbian, closebian,
                                                                       lowbian, highbian, volbian, amountbian)
            self.dao.dels("bian", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
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

    def getData(self):
        """获取行情和挂单数据总接口"""
        ax = self.getSymbly()
        for i in ax:
            self.getMarket(i)
            self.getdepth(i)


###########################################
#
#  获取火币的行情数据
#
#####################################
class resthuobi:
    dao = infoDao.infoDao();

    def getSymbly(self):
        """通过火币的api接口过去交易对信息"""
        url = "https://api.huobi.pro/v1/common/symbols"
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
            basename = item["base-currency"]
            quotename = item["quote-currency"]
            res.append(basename + "_" + quotename)
        return res

    def getMarket(self, symble):
        """获取市场行情数据"""
        nsymbol = symble.replace("_", "")
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
        """获取火币的挂单信息"""
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

    def getData(self):
        """外部调用公共接口"""
        ax = self.getSymbly()
        for i in ax:
            self.getMarket(i)
            self.getdepth(i)
        time.sleep(1)


#################################################
#
# 获取okex的行情数据
#
#######################################
class restokex:
    dao = infoDao.infoDao();

    def getSymbly(self):
        """交易对组合列表"""
        return ['btc_usd', 'ltc_usd', 'eth_usd', 'etc_usd', 'bch_usd']

    def getMarket(self, symble):
        """获取行情数据"""
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
            # 入库
            self.dao.dels("okex", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

    def getdepth(self, symble):
        """获取挂单信息"""
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

    def getData(self):
        """外部公共接口"""
        ax = self.getSymbly()
        for i in ax:
            self.getMarket(i)
            self.getdepth(i)
        time.sleep(1)


if __name__ == "__main__":
    """获取市场行情主入口函数"""
    huobi = resthuobi()
    zb = restzb()
    okex = restokex()
    bian = restbian()

    inslist = [huobi, zb, okex, bian]

    while True:
        for ins in inslist:
            try:
                ins.dao.reconnect()
                ins.getData();
            except Exception as e:
                continue;