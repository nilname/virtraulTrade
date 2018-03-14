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
#################################################
#
# 获取okex的行情数据
#
#######################################
class restokex:
    dao = infoDao.infoDao();

    def getSymbly(self):
        """交易对组合列表"""
        return ['ren_btc', 'icn_btc', 'wtc_btc', 'mof_btc', 'lev_btc', '1st_btc', 'storj_btc', 'viu_btc', 'tct_btc', 'nuls_btc',
                'cmt_btc', 'vib_btc', 'zec_btc', 'omg_btc', 'dgd_btc', 'brd_btc', 'topc_btc', 'link_btc', 'xuc_btc', 'smt_btc', 'ngc_btc',
                'mtl_btc', 'can_btc', 'xmr_btc', 'btg_btc', 'gas_btc', 'pst_btc', 'neo_btc', 'mda_btc', 'hmc_btc', 'bch_btc', 'xrp_btc',
                'rct_btc', 'mkr_btc', 'req_btc', 'qun_btc', 'mana_btc', 'ace_btc', 'read_btc', 'hot_btc', 'of_btc', 'bnt_btc', 'ukg_btc',
                'rcn_btc', 'ppt_btc', 'ubtc_btc', 'tio_btc', 'eth_btc', 'cag_btc', 'elf_btc', 'snt_btc', 'cvc_btc', 'key_btc', 'dash_btc',
                'icx_btc', 'rdn_btc', 'sngls_btc', 'ltc_btc', 'dpy_btc', 'pay_btc', 'yee_btc', 'dat_btc', 'utk_btc', 'amm_btc', 'nano_btc',
                'gtc_btc', 'xlm_btc', 'swftc_btc', 'ark_btc', 'uct_btc', 'dgb_btc', 'trio_btc', 'rfr_btc', 'spf_btc', 'trx_btc', 'mth_btc',
                'cic_btc', 'ins_btc', 'qtum_btc', 'xem_btc', 'salt_btc', 'mco_btc', 'zrx_btc', 'insur_btc', 'sbtc_btc', 'r_btc', 'poe_btc',
                'evx_btc', 'sub_btc', 'dnt_btc', 'edo_btc', 'true_btc', 'bec_btc', 'gnx_btc', 'oax_btc', 'wrc_btc', 'aac_btc', 'ost_btc',
                'btm_btc', 'soc_btc', 'gto_btc', 'lend_btc', 'dna_btc', 'eos_btc', 'etc_btc', 'chat_btc', 'theta_btc', 'snc_btc', 'yoyo_btc',
                'ast_btc', 'knc_btc', 'nas_btc', 'int_btc', 'mdt_btc', 'ctr_btc', 'rnt_btc', 'gnt_btc', 'ssc_btc', 'san_btc', 'zen_btc',
                'stc_btc', 'ipc_btc', 'la_btc', 'iota_btc', 'act_btc', 'kcash_btc', 'pra_btc', 'show_btc', 'hsr_btc', 'qvt_btc', 'iost_btc',
                'lrc_btc', 'snm_btc', 'abt_btc', 'tnb_btc', 'dent_btc', 'bcd_btc', 'eng_btc', 'tra_btc', 'atl_btc', 'fair_btc', 'vee_btc',
                'cbt_btc', 'light_btc', 'avt_btc', 'fun_btc', 'bt2_btc', 'bkx_btc', 'ugc_btc', 'zip_btc', 'aidoc_btc', 'ref_btc', 'auto_btc',
                'itc_btc', 'gsc_btc', 'mot_btc', 'mag_btc', 'wbtc_btc', 'bcx_btc', 'wfee_btc', 'mith_btc', 'ren_eth', 'icn_eth', 'wtc_eth', 'ipc_eth',
                'lev_eth', 'mof_eth', '1st_eth', 'viu_eth', 'theta_eth', 'cmt_eth', 'storj_eth', 'nuls_eth', 'ubtc_eth', 'ngc_eth', 'tct_eth',
                'mkr_eth', 'xmr_eth', 'gas_eth', 'vib_eth', 'xuc_eth', 'zec_eth', 'omg_eth', 'mtl_eth', 'hot_eth', 'smt_eth', 'neo_eth', 'dat_eth',
                'hmc_eth', 'bch_eth', 'xem_eth', 'xrp_eth', 'cic_eth', 'ref_eth', 'brd_eth', 'mana_eth', 'tio_eth', 'key_eth', 'dgd_eth', 'evx_eth',
                'oax_eth', 'utk_eth', 'read_eth', 'mth_eth', 'sngls_eth', 'elf_eth', 'ltc_eth', 'swftc_eth', 'poe_eth', 'amm_eth', 'dash_eth',
                'snc_eth', 'icx_eth', 'dpy_eth', 'link_eth', 'rcn_eth', 'pay_eth', 'bnt_eth', 'topc_eth', 'rct_eth', 'ost_eth', 'aac_eth', 'salt_eth',
                'zen_eth', 'xlm_eth', 'btm_eth', 'uct_eth', 'mco_eth', 'ark_eth', 'gto_eth', 'yee_eth', 'of_eth', 'trx_eth', 'edo_eth', 'dgb_eth',
                'ins_eth', 'qtum_eth', 'ace_eth', 'trio_eth', 'qun_eth', 'knc_eth', 'snt_eth', 'cvc_eth', 'rnt_eth', 'lend_eth', 'san_eth', 'gtc_eth',
                'gnx_eth', 'insur_eth', 'ast_eth', 'bec_eth', 'rdn_eth', 'la_eth', 'true_eth', 'int_eth', 'cag_eth', 'dent_eth', 'pra_eth', 'rfr_eth',
                'eos_eth', 'nano_eth', 'nas_eth', 'etc_eth', 'dnt_eth', 'can_eth', 'ctr_eth', 'tra_eth', 'snm_eth', 'kcash_eth', 'ssc_eth', 'ppt_eth',
                'atl_eth', 'r_eth', 'iota_eth', 'iost_eth', 'mda_eth', 'req_eth', 'qvt_eth', 'zrx_eth', 'tnb_eth', 'mdt_eth', 'gnt_eth', 'eng_eth',
                'show_eth', 'fun_eth', 'chat_eth', 'yoyo_eth', 'lrc_eth', 'dna_eth', 'soc_eth', 'hsr_eth', 'stc_eth', 'wrc_eth', 'abt_eth', 'pst_eth',
                'sub_eth', 'act_eth', 'zip_eth', 'ugc_eth', 'vee_eth', 'ukg_eth', 'fair_eth', 'avt_eth', 'mot_eth', 'bkx_eth', 'aidoc_eth', 'cbt_eth',
                'spf_eth', 'gsc_eth', 'auto_eth', 'light_eth', 'itc_eth', 'mag_eth', 'wfee_eth', 'mith_eth', 'ren_usdt', 'icn_usdt', 'wtc_usdt',
                'lev_usdt', '1st_usdt', 'viu_usdt', 'ref_usdt', 'mof_usdt', 'cmt_usdt', 'storj_usdt', 'mkr_usdt', 'ngc_usdt', 'nuls_usdt', 'key_usdt',
                'xuc_usdt', 'rcn_usdt', 'ark_usdt', 'smt_usdt', 'ppt_usdt', 'bnt_usdt', 'zec_usdt', 'oax_usdt', 'req_usdt', 'dgd_usdt', 'qun_usdt',
                'gas_usdt', 'read_usdt', 'topc_usdt', 'omg_usdt', 'tio_usdt', 'bch_usdt', 'hot_usdt', 'xrp_usdt', 'xmr_usdt', 'btg_usdt', 'neo_usdt',
                'brd_usdt', 'hmc_usdt', 'link_usdt', 'btc_usdt', 'la_usdt', 'mana_usdt', 'vib_usdt', 'dash_usdt', 'eth_usdt', 'uct_usdt', 'atl_usdt',
                'trx_usdt', 'icx_usdt', 'elf_usdt', 'insur_usdt', 'ace_usdt', 'dgb_usdt', 'gnt_usdt', 'trio_usdt', 'nano_usdt', 'dpy_usdt', 'ltc_usdt',
                'yee_usdt', 'dat_usdt', 'zen_usdt', 'zrx_usdt', 'mda_usdt', 'sngls_usdt', 'ubtc_usdt', 'pst_usdt', 'edo_usdt', 'mtl_usdt', 'mco_usdt',
                'rfr_usdt', 'tct_usdt', 'snt_usdt', 'poe_usdt', 'ukg_usdt', 'pay_usdt', 'salt_usdt', 'gtc_usdt', 'xem_usdt', 'mth_usdt', 'utk_usdt',
                'theta_usdt', 'ins_usdt', 'xlm_usdt', 'cic_usdt', 'ipc_usdt', 'amm_usdt', 'qtum_usdt', 'evx_usdt', 'can_usdt', 'dnt_usdt', 'ost_usdt',
                'ast_usdt', 'knc_usdt', 'rdn_usdt', 'swftc_usdt', 'san_usdt', 'bec_usdt', 'aac_usdt', 'chat_usdt', 'sub_usdt', 'show_usdt', 'ssc_usdt',
                'btm_usdt', 'rct_usdt', 'wrc_usdt', 'snm_usdt', 'dent_usdt', 'mdt_usdt', 'cag_usdt', 'cvc_usdt', 'gto_usdt', 'etc_usdt', 'nas_usdt',
                'snc_usdt', 'lend_usdt', 'eos_usdt', 'true_usdt', 'fun_usdt', 'pra_usdt', 'iota_usdt', 'int_usdt', 'of_usdt', 'yoyo_usdt', 'rnt_usdt',
                'lrc_usdt', 'hsr_usdt', 'tnb_usdt', 'tra_usdt', 'iost_usdt', 'gnx_usdt', 'dna_usdt', 'qvt_usdt', 'kcash_usdt', 'abt_usdt', 'act_usdt',
                'ctr_usdt', 'vee_usdt', 'cbt_usdt', 'bcd_usdt', 'avt_usdt', 'soc_usdt', 'stc_usdt', 'eng_usdt', 'bkx_usdt', 'aidoc_usdt', 'fair_usdt',
                'ugc_usdt', 'zip_usdt', 'light_usdt', 'itc_usdt', 'gsc_usdt', 'spf_usdt', 'auto_usdt', 'mag_usdt', 'mot_usdt', 'r_usdt', 'wfee_usdt',
                'mith_usdt', 'cmt_bch', 'ltc_bch', 'edo_bch', 'sbtc_bch', 'btg_bch', 'dgd_bch', 'dash_bch', 'act_bch', 'etc_bch', 'eos_bch', 'bcd_bch',
                'avt_bch', 'bcx_bch']

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
    okex = restokex()
    inslist = [okex]
    for ins in inslist:
       try:
            ins.dao.reconnect()
            ins.getData();
       except Exception as e:
            continue;