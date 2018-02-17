# coding:utf-8 #
import json
import re
import time
import urllib.parse
import urllib.request
import utils
import infoDao


class weibo:
    dao = infoDao.infoDao();

    def getId(self, timedate):
        return self.dao.getId(utils.weibo_tbl, timedate)

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
        url = 'http://www.jinse.com/ajax/weibo/getList?flag=down&id=' + str(index)
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
            infoId = item['id']
            if infoId in self.ids:
                continue
            infoDatetime = item['created_at']
            name = item['source_uri']
            if infoDatetime.split(" ")[0] != utils.gettoday():
                return None
            content = self.processContent(item)
            source="weibo"
            insertStr = "{},\"{}\",\"{}\",\"{}\",\"{}\"".format(infoId, name, infoDatetime, content.strip('\n'),source)
            self.dao.saveInfo(tableName=utils.weibo_tbl, columesName=utils.weibo_columes, values=insertStr)
        return int(item['id'])

    def processContent(self, item):
        content = item['content']
        content = re.sub(r'<[^<]+>', "", content)
        content = re.sub(r'\"', "\\\"", content)
        content = re.sub("\n", "", content)
        return content

    def update(self):
        self.ids = self.dao.getIdsBySource(utils.weibo_tbl, utils.gettoday(),"weibo")
        indexId = self.getDatas(0)
        while indexId != None:
            indexId = self.getDatas(indexId)
            time.sleep(5)


if __name__ == "__main__":
    weiboInstance = weibo()
    while True:
        weiboInstance.update()
        time.sleep(30)
