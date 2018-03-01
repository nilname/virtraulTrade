# coding:utf-8 #
import json
import re
import time
import urllib.parse
import urllib.request

import infoDao
import utils


class bishijie:
    dao = infoDao.infoDao()

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

    def getDatas(self, timestampes):
        try:
            url = 'http://www.bishijie.com/api/news/?size=100&timestamp=' + timestampes
            response_result = urllib.request.urlopen(url).read()
            tmp = json.loads(response_result)
        except Exception as e:
            return None

        try:
            all_div = tmp['data'][utils.gettoday()]['buttom']
        except KeyError:
            return None

        for item in all_div:
            newsId = (item['newsflash_id'])
            newsTime = bishijie.getTimeFromStampe(item['issue_time'])
            if newsId in self.ids:
                continue

            if newsTime.split(" ")[0] != utils.gettoday():
                return None
            content = self.processContent(item)
            newsurl = item['link']
            source = "币世界"
            insertStr = "{},\"{}\",\"{}\",\"{}\",\"{}\"".format(newsId, newsTime, content, newsurl, source)
            self.dao.saveInfo(tableName=utils.kuaixun_tbl, columesName=utils.kuaixun_columes,
                              values=insertStr)

    def processContent(self, item):
        content = item['content']
        content = re.sub(r'<[^<]+>', "", content)
        content = re.sub(r'\"', "\\\"", content)
        content = re.sub("\n", "", content)
        return content

    def update(self):
        self.ids = self.dao.getIdsBySource(utils.kuaixun_tbl, utils.gettoday(),"币世界")
        timest = self.getDatas("")
        while timest != None:
            timest = self.getDatas(timest)
            time.sleep(5)


if __name__ == "__main__":
     bishijieinstance = bishijie()
     while True:
         bishijieinstance.update()
         time.sleep(300)
