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
        return ['btc_usdt', 'bch_usdt', 'eth_usdt', 'etc_usdt', 'ltc_usdt', 'eos_usdt', 'xrp_usdt', 'omg_usdt', 'dash_usdt',
                 'zec_usdt', 'ela_usdt', 'itc_usdt', 'nas_usdt', 'ruff_usdt', 'zil_usdt', 'dta_usdt', 'let_usdt', 'ht_usdt',
                 'theta_usdt', 'hsr_usdt', 'qtum_usdt', 'snt_usdt', 'iost_usdt', 'neo_usdt', 'stor_usdt', 'gnt_usdt', 'cvc_usdt',
                 'smt_usdt', 'ven_usdt', 'elf_usdt', 'xem_usdt','bch_btc', 'eth_btc', 'ltc_btc', 'etc_btc', 'eos_btc', 'omg_btc',
                 'dash_btc', 'xrp_btc', 'zec_btc', 'abt_btc', 'blz_btc', 'edu_btc', 'eng_btc', 'wpr_btc', 'mtx_btc', 'mtn_btc',
                 'snc_btc', 'lsk_btc', 'stk_btc', 'ht_btc', 'ela_btc', 'srn_btc', 'zla_btc', 'trx_btc', 'ocn_btc', 'lun_btc', 'iost_btc',
                 'hsr_btc', 'smt_btc', 'let_btc', 'swftc_btc', 'wax_btc', 'elf_btc', 'mds_btc', 'tnb_btc', 'nas_btc', 'btm_btc', 'itc_btc',
                 'theta_btc', 'wicc_btc', 'gnx_btc', 'ven_btc', 'dta_btc', 'mana_btc', 'qash_btc', 'propy_btc', 'snt_btc', 'qun_btc',
                 'qtum_btc', 'dat_btc', 'tnt_btc','cmt_btc', 'yee_btc', 'gas_btc', 'aidoc_btc', 'storj_btc', 'xem_btc', 'pay_btc', 'neo_btc',
                 'cvc_btc', 'qsp_btc', 'topc_btc', 'rcn_btc', 'ast_btc', 'bat_btc', 'dbc_btc', 'rpx_btc', 'act_btc', 'icx_btc', 'knc_btc',
                 'mco_btc', 'zrx_btc', 'mtl_btc', 'gnt_btc', 'req_btc', 'rdn_btc', 'salt_btc', 'mee_btc', 'zil_btc', 'chat_btc', 'powr_btc',
                 'dgd_btc', 'appc_btc', 'ost_btc', 'soc_btc', 'eko_btc', 'link_btc', 'utk_btc', 'evx_btc', 'adx_btc', 'ruff_btc',
                 'bcd_btc', 'bcx_btc', 'bifi_btc', 'sbtc_btc', 'btg_btc', 'eos_eth', 'omg_eth', 'abt_eth', 'blz_eth', 'edu_eth',
                 'eng_eth', 'wpr_eth', 'mtx_eth', 'mtn_eth', 'snc_eth', 'lsk_eth', 'stk_eth', 'ht_eth', 'ela_eth', 'srn_eth',
                 'zla_eth', 'trx_eth', 'ocn_eth', 'lun_eth', 'smt_eth', 'iost_eth', 'nas_eth', 'hsr_eth', 'mds_eth', 'wax_eth',
                 'elf_eth', 'itc_eth', 'tnb_eth', 'swftc_eth', 'btm_eth', 'dta_eth', 'wicc_eth', 'let_eth', 'qash_eth', 'gnx_eth',
                 'ven_eth', 'theta_eth', 'propy_eth', 'mana_eth', 'aidoc_eth', 'yee_eth', 'qtum_eth', 'gas_eth', 'zil_eth', 'cmt_eth',
                 'pay_eth', 'tnt_eth', 'qun_eth', 'mee_eth', 'rcn_eth', 'cvc_eth', 'icx_eth', 'topc_eth', 'qsp_eth', 'act_eth', 'bat_eth',
                 'rdn_eth', 'dat_eth', 'gnt_eth', 'dbc_eth', 'chat_eth', 'appc_eth', 'mco_eth', 'soc_eth', 'req_eth', 'salt_eth', 'powr_eth',
                 'eko_eth', 'dgd_eth', 'ost_eth', 'link_eth', 'utk_eth', 'evx_eth', 'adx_eth', 'ruff_eth']

 #    def getMarket(self, symble):
 #        """获取市场行情数据"""
 #        nsymbol = symble.replace("_", "")
 #        url = "https://api.huobipro.com/market/history/kline?period=1min&size=1&symbol=" + nsymbol
 #        try:
 #            response_result = urllib.request.urlopen(url).read()
 #            tmp = json.loads(response_result)
 #        except Exception as e:
 #            return None
 #         try:
 #             all_div = tmp['data']
 #        except KeyError:
 #            return None
 #        res = []
 #        for item in all_div:
 #            amounthuobi = item["amount"]
 #            openhuobi = item["open"]
 #            closehuobi = item["close"]
  #           lowhuobi = item["low"]
 #            highhuobi = item["high"]
 #            volhuobi = item["vol"]
 #
 #            insertStr = " \"huobi\",\"{}\",{},{},{},{},{},{}".format(symble, openhuobi, closehuobi,
 #                                                                     lowhuobi, highhuobi, volhuobi, amounthuobi)
  #           self.dao.dels("huobi", symble, tablename="vt_market_tick")
  #           self.dao.saveInfo(tableName="vt_market_tick",
  #                             columesName="platform,symbol,open, close, low, high, vol, amount",
 #                              values=insertStr)

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
#            self.getMarket(i)
            self.getdepth(i)
            self.getHistory(i)
        time.sleep(1)


if __name__ == "__main__":
    """获取市场行情主入口函数"""
    huobi = resthuobi()
    # 火币接口不稳定会导致数据爬取变慢
    inslist = [huobi]
    for ins in inslist:
        try:
            ins.dao.reconnect()
            ins.getData();
        except Exception as e:
            print(e)
            continue;