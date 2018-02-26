websocket.send("{'event':'addChannel','channel':'ok_sub_futureusd_X_kline_Y_Z'}");



wss://api.zb.com:9999/websocket

币安可以在web获取，python api不能获取
币安和货币不需要认证

zb和oxke需要认证
huobi
https://api.huobi.pro/v1/common/symbols
https://github.com/huobiapi/API_Docs/wiki/REST_api_reference

https://api.huobi.pro/market/history/kline?period=1day&size=200&symbol=btcusdt 



请求参数:

参数名称	是否必须	类型	描述	默认值	取值范围
symbol	true	string	交易对		btcusdt, bchbtc, rcneth ...
period	true	string	K线类型		1min, 5min, 15min, 30min, 60min, 1day, 1mon, 1week, 1year
size	false	integer	获取数量	150	[1,2000]
响应数据:

参数名称	是否必须	数据类型	描述	取值范围
status	true	string	请求处理结果	"ok" , "error"
ts	true	number	响应生成时间点，单位：毫秒	
tick	true	object	KLine 数据	
ch	true	string	数据所属的 channel，格式： market.$symbol.kline.$period	
data 说明:

  "data": [
{
    "id": K线id,
    "amount": 成交量,
    "count": 成交笔数,
    "open": 开盘价,
    "close": 收盘价,当K线为最晚的一根时，是最新成交价
    "low": 最低价,
    "high": 最高价,
    "vol": 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
  }
]



===========

GET /market/depth 获取 Market Depth 数据

请求参数:

参数名称	是否必须	类型	描述	默认值	取值范围
symbol	true	string	交易对		btcusdt, bchbtc, rcneth ...
type	true	string	Depth 类型		step0, step1, step2, step3, step4, step5（合并深度0-5）；step0时，不合并深度
用户选择“合并深度”时，一定报价精度内的市场挂单将予以合并显示。合并深度仅改变显示方式，不改变实际成交价格。
响应数据:

参数名称	是否必须	数据类型	描述	取值范围
status	true	string		"ok" 或者 "error"
ts	true	number	响应生成时间点，单位：毫秒	
tick	true	object	Depth 数据	
ch	true	string	数据所属的 channel，格式： market.$symbol.depth.$type	
tick 说明:

  "tick": {
    "id": 消息id,
    "ts": 消息生成时间，单位：毫秒,
    "bids": 买盘,[price(成交价), amount(成交量)], 按price降序,
    "asks": 卖盘,[price(成交价), amount(成交量)], 按price升序
  }
请求响应示例:

/* GET /market/depth?symbol=ethusdt&type=step1 */





#############
zb
http://api.zb.com/data/v1/ticker?market=btc_usdt
http://api.zb.com/data/v1/depth?market=btc_usdt&size=3


###############
bian
https://www.binance.com/api/v1/depth?symbol=BNBBTC&limit=5

GET /api/v1/depth
Weight: Adjusted based on the limit:

Limit	Weight
5, 10, 20, 50, 100	1
500	5
1000	10
Parameters:

Name	Type	Mandatory	Description
symbol	STRING	YES	
limit	INT	NO	Default 100; max 1000. Valid limits:[5, 10, 20, 50, 100, 500, 1000]
Caution: setting limit=0 can return a lot of data.

Response:

{
  "lastUpdateId": 1027024,
  "bids": [
    [
      "4.00000000",     // PRICE
      "431.00000000",   // QTY
      []                // Ignore.
    ]
  ],
  "asks": [
    [
      "4.00000200",
      "12.00000000",
      []
    ]
  ]
}

https://api.binance.com/api/v1/klines?symbol=BNBBTC&limit=5&interval=1m
Parameters:

Name	Type	Mandatory	Description
symbol	STRING	YES	
interval	ENUM	YES	
limit	INT	NO	Default 500; max 500.
startTime	LONG	NO	
endTime	LONG	NO	
If startTime and endTime are not sent, the most recent klines are returned.
Response:

[
  [
    1499040000000,      // Open time
    "0.01634790",       // Open
    "0.80000000",       // High
    "0.01575800",       // Low
    "0.01577100",       // Close
    "148976.11427815",  // Volume
    1499644799999,      // Close time
    "2434.19055334",    // Quote asset volume
    308,                // Number of trades
    "1756.87402397",    // Taker buy base asset volume
    "28.46694368",      // Taker buy quote asset volume
    "17928899.62484339" // Ignore
  ]
]




################
https://www.okex.com/rest_api.html
okex
https://www.okex.com/api/v1/future_kline.do
URL https://www.okex.com/api/v1/future_kline.do
示例
# Request
GET https://www.okex.com/api/v1/future_kline.do
# Response
[
    [
        1440308700000,
        233.37,
        233.48,
        233.37,
        233.48,
        52,
        22.2810015
    ],
    [
        1440308760000,
        233.38,
        233.38,
        233.27,
        233.37,
        186,
        79.70234956
    ]
]
返回值说明
[
    1440308760000,	时间戳
    233.38,		开
    233.38,		高
    233.27,		低
    233.37,		收
    186,		交易量
    79.70234956		交易量转化BTC或LTC数量
]
请求参数
参数名	参数类型	必填	描述
symbol
String
是
btc_usd   ltc_usd    eth_usd    etc_usd    bch_usd
type
String
是
1min : 1分钟
3min : 3分钟
5min : 5分钟
15min : 15分钟
30min : 30分钟
1day : 1日
3day : 3日
1week : 1周
1hour : 1小时
2hour : 2小时
4hour : 4小时
6hour : 6小时
12hour : 12小时
contract_type
String
是
合约类型。this_week：当周；next_week：下周；quarter：季度
size
Integer
否（默认0）
指定获取数据的条数
since
Long
否（默认0）
时间戳（eg：1417536000000）。 返回该时间戳以后的数据


============
挂单数据
https://www.okex.com/api/v1/future_depth.do?symbol=btc_usd&contract_type=this_week

URL https://www.okex.com/api/v1/future_depth.do
示例
# Request 
GET https://www.okex.com/api/v1/future_depth.do?symbol=btc_usd&contract_type=this_week
# Response
{
	"asks":[
		[411.8,6],
		[411.75,11],
		[411.6,22],
		[411.5,9],
		[411.3,16]
	],
	"bids":[
		[410.65,12],
		[410.64,3],
		[410.19,15],
		[410.18,40],
		[410.09,10]
	]
}
返回值说明
asks:卖方深度
bids:买方深度
请求参数
参数名	参数类型	必填	描述
symbol
String
是
btc_usd   ltc_usd    eth_usd    etc_usd    bch_usd
contract_type
String
是
合约类型: this_week:当周   next_week:下周   quarter:季度
size
Integer
是
value: 1-200
merge
Integer
否（默认0）
value: 1 （合并深度）