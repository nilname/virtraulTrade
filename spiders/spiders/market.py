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
                'hotc_qc', 'hotc_btc', 'usdt_qc', 'xuc_qc', 'xuc_btc', 'epc_qc', 'epc_btc', 'bds_qc',
                'bds_btc', 'gram_qc', 'gram_usdt', 'gram_btc']

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
            insertStr = " \"zb\",\"{}\",{},{},{},{},{},{}".format(symble, openzb, closezb,
                                                                  lowzb, highzb, volzb, amountzb)
            # 先删除已有的信息
            self.dao.dels("zb", symble, tablename="vt_market_tick")
            # 插入新获取的内容
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,symbol,open, close, low, high, vol, amount",
                              values=insertStr)
            # 可以用update语句替代
            # print(item)
            # print(insertStr)

    def getHistory(self, symble):
        """获取历史成交记录"""
        url = "http://api.zb.com/data/v1/trades?market=" + symble
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
            self.dao.dels("zb", symble, tablename="vt_market_trade_history")
        if len(all_history) > 10:
            all_history = all_history[:10]
        for item in all_history:
            insertStr = " \"zb\",\"{}\",\"{}\",{},{},\"{}\"".format(symble, item['type'], item['price'],
                                                                    item['amount'],
                                                                    get_times_from_timestamp(item['date']))
            self.dao.saveInfo(tableName="vt_market_trade_history",
                              columesName="platform,symbol,direction,price, amount,ts",
                              values=insertStr)

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
            self.getHistory(i)
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


###########################################
#
#  获取火币的行情数据
#
#####################################
class resthuobi:
    dao = infoDao.infoDao();

    def getHistory(self, symble):
        """获取历史成交记录"""
        """https://api.huobipro.com/market/history/trade?size=10&symbol=ethusdt"""
        nsymbol = symble.replace("_", "")
        url = "https://api.huobipro.com/market/history/trade?size=10&symbol=" + nsymbol
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        try:
            all_history = tmp['data']
        except KeyError:
            return None
        if len(all_history) != 0:
            self.dao.dels("huobi", symble, tablename="vt_market_trade_history")

        for items in all_history:
            sub_history = items['data']
            for item in sub_history:
                insertStr = " \"huobi\",\"{}\",\"{}\",{},{},\'{}\'".format(symble, item['direction'], item['price'],
                                                                           item['amount'], get_times_from_timestamp(
                        item['ts'] / 1000.0))
                self.dao.saveInfo(tableName="vt_market_trade_history",
                                  columesName="platform,symbol,direction,price, amount,ts",
                                  values=insertStr)
                # print(insertStr)

    def getSymbly(self):
        """通过火币的api接口过去交易对信息"""
        # url = "https://api.huobipro.com/v1/common/symbols"
        # try:
        #     response_result = urllib.request.urlopen(url).read()
        #     tmp = json.loads(response_result)
        # except Exception as e:
        #     return None
        # try:
        #     all_div = tmp['data']
        # except KeyError:
        #     return None
        # res = []
        # for item in all_div:
        #     basename = item["base-currency"]
        #     quotename = item["quote-currency"]
        #     res.append(basename + "_" + quotename)
        return ['nas_eth', 'eos_eth', 'swftc_btc', 'zec_usdt', 'evx_btc', 'mds_eth', 'smt_eth', 'trx_eth', 'theta_usdt',
                'lun_eth', 'bch_btc', 'tnb_btc', 'gnx_eth', 'dat_btc', 'chat_eth', 'qtum_usdt', 'let_btc', 'snt_btc',
                'wpr_btc', 'elf_eth', 'utk_btc', 'sbtc_btc', 'neo_usdt', 'mco_btc', 'ost_eth', 'bt2_btc', 'qun_btc',
                'hsr_eth', 'topc_eth', 'salt_eth', 'aidoc_eth', 'wax_btc', 'dta_eth', 'btc_usdt', 'gas_eth', 'neo_btc',
                'salt_btc', 'btm_btc', 'blz_eth', 'bat_eth', 'eko_btc', 'appc_btc', 'cmt_btc', 'qtum_eth', 'req_btc',
                'btm_eth', 'icx_btc', 'ocn_btc', 'zec_btc', 'dgd_eth', 'dat_eth', 'nas_usdt', 'stk_eth', 'ht_eth',
                'qun_eth', 'soc_btc', 'elf_btc', 'cmt_eth', 'dbc_btc', 'storj_usdt', 'wax_eth', 'powr_btc', 'zil_usdt',
                'dta_btc', 'snc_btc', 'mee_btc', 'nas_btc', 'tnb_eth', 'swftc_eth', 'eos_btc', 'link_eth', 'iost_btc',
                'ht_usdt', 'rdn_btc', 'lun_btc', 'gnx_btc', 'ela_btc', 'let_eth', 'evx_eth', 'act_eth', 'bch_usdt',
                'icx_eth', 'bcx_btc', 'mtn_eth', 'propy_eth', 'xrp_usdt', 'theta_eth', 'snc_eth', 'dbc_eth', 'itc_usdt',
                'smt_btc', 'srn_eth', 'eth_usdt', 'itc_btc', 'omg_btc', 'stk_btc', 'mds_btc', 'adx_btc', 'etc_btc',
                'knc_btc', 'cvc_btc', 'qsp_eth', 'btg_btc', 'edu_btc', 'zla_eth', 'mtx_eth', 'rcn_eth', 'utk_eth',
                'edu_eth', 'mtl_btc', 'gnt_usdt', 'appc_eth', 'wicc_btc', 'yee_eth', 'omg_usdt', 'link_btc', 'xem_usdt',
                'dash_btc', 'tnt_btc', 'qash_eth', 'ruff_eth', 'ela_eth', 'iost_eth', 'theta_btc', 'snt_usdt',
                'soc_eth', 'eos_usdt', 'ela_usdt', 'mana_btc', 'smt_usdt', 'xrp_btc', 'ltc_usdt', 'bcd_btc', 'cvc_usdt',
                'gnt_eth', 'ht_btc', 'rcn_btc', 'eng_eth', 'pay_eth', 'cvc_eth', 'mee_eth', 'powr_eth', 'blz_btc',
                'pay_btc', 'adx_eth', 'let_usdt', 'ruff_usdt', 'eko_eth', 'srn_btc', 'ocn_eth', 'ven_eth', 'abt_eth',
                'bifi_btc', 'act_btc', 'etc_usdt', 'ost_btc', 'iost_usdt', 'mco_eth', 'abt_btc', 'storj_btc', 'hsr_btc',
                'ven_btc', 'gnt_btc', 'trx_btc', 'dta_usdt', 'zil_btc', 'lsk_btc', 'ltc_btc', 'yee_btc', 'ruff_btc',
                'wpr_eth', 'ast_btc', 'dash_usdt', 'dgd_btc', 'zil_eth', 'zrx_btc', 'eth_btc', 'req_eth', 'wicc_eth',
                'eng_btc', 'lsk_eth', 'rpx_btc', 'tnt_eth', 'mtx_btc', 'ven_usdt', 'aidoc_btc', 'hsr_usdt', 'qtum_btc',
                'qsp_btc', 'bat_btc', 'qash_btc', 'itc_eth', 'xem_btc', 'mana_eth', 'gas_btc', 'mtn_btc', 'chat_btc',
                'bt1_btc', 'zla_btc', 'omg_eth', 'topc_btc', 'propy_btc', 'rdn_eth', 'elf_usdt']

    def getMarket(self, symble):
        """获取市场行情数据"""
        nsymbol = symble.replace("_", "")
        url = "https://api.huobipro.com/market/history/kline?period=1min&size=1&symbol=" + nsymbol
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

            insertStr = " \"huobi\",\"{}\",{},{},{},{},{},{}".format(symble, openhuobi, closehuobi,
                                                                     lowhuobi, highhuobi, volhuobi, amounthuobi)
            self.dao.dels("huobi", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

            # print(item)
            # print(insertStr)

    def getdepth(self, symble):
        """获取火币的挂单信息"""
        nsymbol = symble.replace("_", "")
        url = "https://api.huobipro.com/market/depth?symbol=" + nsymbol + "&type=step5"
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
            self.getHistory(i)
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
        # return ['btc_usd', 'ltc_usd', 'eth_usd', 'etc_usd', 'bch_usd']
        return ['ltc_btc', 'eth_btc', 'etc_btc', 'bch_btc', 'btc_usdt', 'eth_usdt', 'ltc_usdt', 'etc_usdt', 'bch_usdt',
                'etc_eth', 'bt1_btc', 'bt2_btc', 'btg_btc', 'qtum_btc', 'hsr_btc', 'neo_btc', 'gas_btc', 'qtum_usdt',
                'hsr_usdt', 'neo_usdt', 'gas_usdt']

    def getMarket(self, symble):
        """获取行情数据"""
        # https://www.okex.com/api/v1/kline.do?symbol=ltc_btc&type=1min
        url = "https://www.okex.com/api/v1/kline.do?symbol=" + symble + "&type=1min&size=1"
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

            insertStr = " \"okex\",\"{}\",{},{},{},{},{},{}".format(symble, openokex, closeokex,
                                                                    lowokex, highokex, volokex, amountokex)
            # 入库
            self.dao.dels("okex", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

    def getdepth(self, symble):
        """获取挂单信息"""
        #  https://www.okex.com/api/v1/depth.do?symbol=ltc_btc
        url = "https://www.okex.com/api/v1/depth.do?symbol=" + symble + "&size=5"
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

    def getHistory(self, symble):
        """获取历史成交记录"""
        #  https://www.okex.com/api/v1/trades.do?symbol=ltc_btc&since=7622718804
        url = "https://www.okex.com/api/v1/trades.do?symbol=" + symble
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
            self.dao.dels("okex", symble, tablename="vt_market_trade_history")
        if len(all_history) > 10:
            all_history = all_history[:10]
        for item in all_history:
            insertStr = " \"okex\",\"{}\",\"{}\",{},{},\"{}\"".format(symble, item['type'], item['price'],
                                                                      item['amount'],
                                                                      get_times_from_timestamp(item['date']))
            self.dao.saveInfo(tableName="vt_market_trade_history",
                              columesName="platform,symbol,direction,price, amount,ts",
                              values=insertStr)

    def getData(self):
        """外部公共接口"""
        ax = self.getSymbly()
        for i in ax:
            self.getMarket(i)
            self.getdepth(i)
            self.getHistory(i)
        time.sleep(1)


if __name__ == "__main__":
    """获取市场行情主入口函数"""
    huobi = resthuobi()
    zb = restzb()
    okex = restokex()
    bian = restbian()
    # 火币接口不稳定会导致数据爬取变慢
    inslist = [huobi,okex, zb, bian]

    while True:
        for ins in inslist:
            try:
                ins.dao.reconnect()
                ins.getData();
            except Exception as e:
                continue;
