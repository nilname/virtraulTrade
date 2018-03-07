import time
import datetime

###########################
# 工具类
# 数据库常量信息
#
#
##############################

weibo_tbl="vt_info_niuren"
weibo_columes="id,name,createtime,content,source"

twitter_tbl="vt_info_niuren"
twitter_columes="id,name,createtime,content,source"

kuaixun_tbl="vt_info_news"
kuaixun_columes="id,createtime,content,sourceurl,source"



toutiao_tbl="vt_info_block_news"
toutiao_columes="title,author,newstime,newsuri"

qukuaiwang_tbl="vt_info_block_news"
qukuaiwang_columes="title,author,newstime,newsuri,img,viewcount"



qukuaiwang_detail_tbl="vt_info_block_news_detail"
qukuaiwang_detail_columes="title,content,images"
# host='localhost'
# user='root'
# passwd=''
# database='test'

host='120.79.34.242'
user='vt_dev'
passwd='vt_dev__2018~*!#$'
database='virtualTrade'


def gettoday():
    return time.strftime('%Y-%m-%d', time.localtime())




def gettimes():
    try:
        d = datetime.datetime.fromtimestamp(time.time())
        str1 = d.strftime("%Y-%m-%d %H:%M:%S.%f")
        # 2015-08-28 16:43:37.283000'
        return str1
    except Exception as e:
        print
        e
        return ''

if __name__=="__main__":
    print(gettimes())
    print(gettimes())




