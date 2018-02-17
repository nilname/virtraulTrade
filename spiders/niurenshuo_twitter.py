# coding:utf-8 #
import infoDao
import json
import re
import time
import urllib.parse
import urllib.request

from bs4 import BeautifulSoup
import utils


class twitter:
    dao = infoDao.infoDao();

    def timeToStampe(dt):
        timeArray = time.strptime(dt, "%Y-%m-%d %H:%M:%S")
        # 转换成时间戳
        timestamp = time.mktime(timeArray)
        return timestamp

    def getTimeFromStampe(timestamp):
        time_local = time.localtime(timestamp)
        # 转换成新的时间格式(2016-05-05 20:28:54)
        dt = time.strftime("%Y-%m-%d %H:%M:%S", time_local)
        return dt

    def getDatas(self, index):
        url = 'http://www.jinse.com/ajax/twitters/getList?flag=down&id=' + str(index)
        try:
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None
        # 循环div获取详细信息
        try:
            all_div = tmp['data']
        except KeyError:
            return None
        for item in all_div:
            # print(item['id'])
            infoId = item['id']
            if infoId in self.ids:
                continue
            name = item['source_uri']
            infoDatetime = item['published_at']
            if infoDatetime.split(" ")[0] != utils.gettoday():
                return None
            content = self.processContent(item)
            source="twitter"
            insertStr = "{},\"{}\",\"{}\",\"{}\",\"{}\"".format(infoId, name, infoDatetime, content,source)
            print(insertStr)
            self.dao.saveInfo(tableName=utils.twitter_tbl, columesName=utils.twitter_columes, values=insertStr)
        return int(item['id'])

    def processContent(self, item):
        content = item['chinese']
        content = re.sub(r'<[^<]+>', "", content)
        content = re.sub(r'\"', "\\\"", content)
        content = re.sub("\n", "", content)
        return content

    def update(self):
        self.ids = self.dao.getIdsBySource(utils.twitter_tbl, utils.gettoday(),"twitter")
        indexId = self.getDatas(0)
        while indexId != None:
            indexId = self.getDatas(indexId)
            time.sleep(5)


if __name__ == "__main__":
    twitterinstance = twitter()
    while True:
        twitterinstance.update()
        time.sleep(60)
