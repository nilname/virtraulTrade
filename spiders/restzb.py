# coding:utf-8 #
import json
import urllib.parse
import urllib.request
import infoDao


class restzb:
    dao = infoDao.infoDao();

    def getSymbly(self):
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

            insertStr = " \"zb\",{},\"{}\",{},{},{},{},{},{}".format(item[0] / 1000000, symble, openzb, closezb,
                                                                     lowzb, highzb, volzb, amountzb)
            self.dao.dels("zb", symble, tablename="vt_market_tick")
            self.dao.saveInfo(tableName="vt_market_tick",
                              columesName="platform,id,symbol,open, close, low, high, vol, amount",
                              values=insertStr)

            # print(item)
            # print(insertStr)

    def getdepth(self, symble):
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



def getData():
    zbinstance = restzb()
    ax = zbinstance.getSymbly()
    for i in ax:
        zbinstance.getMarket(i)
        zbinstance.getdepth(i)
if __name__ == "__main__":
        getData()
