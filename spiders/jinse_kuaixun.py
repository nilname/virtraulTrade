# coding:utf-8 #
import json
import re
import time
import urllib.parse
import urllib.request

import infoDao
import utils


class Kuaixun:
    dao = infoDao.infoDao()

    def getDatas(self, index):
        try:
            url = 'http://www.jinse.com/ajax/lives/getList?search=&id=' + str(index) + '&flag=down'
            response_result = urllib.request.urlopen(url).read()
        except Exception as e:
            print("error happen")
            return None
        tmp = json.loads(response_result)
        try:
            all_div = tmp['data'][utils.gettoday()]
        except KeyError:
            return None

        for item in all_div:
            if item['day_name'] == '今天':
                infoId = item['id']
                sourceurl = item['source_url']
                infoDatetime = item['publish_time']

                if infoDatetime == None or infoDatetime == "0000-00-00 00:00:00":
                    infoDatetime = utils.gettoday() + " " + item['created_at'] + ":00"
                if infoDatetime.split(" ")[0] != utils.gettoday():
                    print(infoDatetime)
                    infoDatetime = utils.gettoday() + " " + item['created_at'] + ":00"
                if infoId in self.ids:
                    continue
                content = item['content']
                content = re.sub(r'<[^<]+>', "", content)
                content = re.sub(r'\"', "\\\"", content)
                content = re.sub('\n', "", content)
                print(re.sub("\n", "", content))
                insertStr = "{},\"{}\",\"{}\",\"{}\",\"金色财经\"".format(infoId, infoDatetime, content, sourceurl)
                print(insertStr)
                self.dao.saveInfo(tableName=utils.kuaixun_tbl, columesName=utils.kuaixun_columes, values=insertStr)
            else:
                return None
        return item['id']

    def update(self):
        self.ids = self.dao.getIdsBySource(utils.kuaixun_tbl, utils.gettoday(), "金色财经")
        indexId = self.getDatas(0)
        while indexId != None:
            indexId = self.getDatas(indexId)
            time.sleep(5)


if __name__ == "__main__":
    kuaixun = Kuaixun()
    while True:
        kuaixun.update()
        time.sleep(36)
