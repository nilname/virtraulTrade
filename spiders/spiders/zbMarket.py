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
        return ['zb_usdt', 'btc_usdt', 'bcc_usdt', 'ubtc_usdt', 'ltc_usdt', 'eth_usdt', 'etc_usdt', 'bts_usdt', 'eos_usdt', 'qtum_usdt',
        'hsr_usdt', 'xrp_usdt', 'bcd_usdt', 'dash_usdt', 'sbtc_usdt', 'ink_usdt', 'tv_usdt', 'bcx_usdt', 'bth_usdt', 'lbtc_usdt',
        'chat_usdt', 'hlc_usdt', 'bcw_usdt', 'btp_usdt', 'topc_usdt', 'ent_usdt', 'bat_usdt', '1st_usdt', 'safe_usdt', 'qun_usdt',
        'btn_usdt', 'true_usdt', 'cdc_usdt', 'ddm_usdt', 'hotc_usdt', 'gram_usdt', 'zb_btc', 'bcc_btc', 'ubtc_btc', 'ltc_btc',
        'eth_btc', 'etc_btc', 'bts_btc', 'eos_btc', 'qtum_btc', 'hsr_btc', 'xrp_btc', 'bcd_btc', 'dash_btc', 'sbtc_btc', 'ink_btc',
        'tv_btc', 'bcx_btc', 'bth_btc', 'lbtc_btc', 'chat_btc', 'hlc_btc', 'bcw_btc', 'btp_btc', 'topc_btc', 'ent_btc', 'bat_btc',
        '1st_btc', 'safe_btc', 'qun_btc', 'btn_btc', 'true_btc', 'cdc_btc', 'ddm_btc', 'bite_btc', 'hotc_btc', 'xuc_btc', 'epc_btc',
        'bds_btc', 'gram_btc', 'zb_qc', 'btc_qc', 'bcc_qc', 'ubtc_qc', 'ltc_qc', 'eth_qc', 'etc_qc', 'bts_qc', 'eos_qc', 'qtum_qc',
        'hsr_qc', 'xrp_qc', 'bcd_qc', 'dash_qc', 'sbtc_qc', 'ink_qc', 'tv_qc', 'bcx_qc', 'bth_qc', 'lbtc_qc', 'chat_qc', 'hlc_qc',
        'bcw_qc', 'btp_qc', 'bitcny_qc', 'topc_qc', 'ent_qc', 'bat_qc', '1st_qc', 'safe_qc', 'qun_qc', 'btn_qc', 'true_qc', 'cdc_qc',
        'ddm_qc', 'hotc_qc', 'usdt_qc', 'xuc_qc', 'epc_qc', 'bds_qc', 'gram_qc']

#    def getMarket(self, symble):
#        """获取ZB的行情数据，通过获取kline数据得到"""
#        url = "http://api.zb.com/data/v1/kline?market=" + symble + "&type=1min&size=1"
#        try:
#            response_result = urllib.request.urlopen(url).read()
#            tmp = json.loads(response_result)
#        except Exception as e:
#            return None
#        try:
#            all_div = tmp['data']
#        except KeyError:
#            return None
#        for item in all_div:
#            amountzb = 0
#            openzb = item[1]
#            closezb = item[4]
#            lowzb = item[3]
#            highzb = item[2]
#            volzb = item[5]
#            # 拼接sql子段信息
#            insertStr = " \"zb\",\"{}\",{},{},{},{},{},{}".format(symble, openzb, closezb,
 #                                                                 lowzb, highzb, volzb, amountzb)
            # 先删除已有的信息
#            self.dao.dels("zb", symble, tablename="vt_market_tick")
            # 插入新获取的内容
#            self.dao.saveInfo(tableName="vt_market_tick",
#                              columesName="platform,symbol,open, close, low, high, vol, amount",
#                              values=insertStr)
            # 可以用update语句替代
#            # print(item)
#            # print(insertStr)

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
#            self.getMarket(i)
            self.getdepth(i)
            self.getHistory(i)
        time.sleep(1)


if __name__ == "__main__":
    """获取市场行情主入口函数"""
    zb = restzb()
    inslist = [zb]
    for ins in inslist:
        try:
            ins.dao.reconnect()
            ins.getData();
        except Exception as e:
            continue;