# coding:utf-8 #
import json
import re
import time
import urllib.parse
import urllib.request

from bs4 import BeautifulSoup

import infoDao
import utils


class jinseKuaixun:
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
        self.ids = self.dao.getIdsBySource(utils.kuaixun_tbl, utils.gettoday(), "币世界")
        timest = self.getDatas("")
        while timest != None:
            timest = self.getDatas(timest)
            time.sleep(5)


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
            source = "twitter"
            insertStr = "{},\"{}\",\"{}\",\"{}\",\"{}\"".format(infoId, name, infoDatetime, content, source)
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
        self.ids = self.dao.getIdsBySource(utils.twitter_tbl, utils.gettoday(), "twitter")
        indexId = self.getDatas(0)
        while indexId != None:
            indexId = self.getDatas(indexId)
            time.sleep(5)


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
            source = "weibo"
            insertStr = "{},\"{}\",\"{}\",\"{}\",\"{}\"".format(infoId, name, infoDatetime, content.strip('\n'), source)
            self.dao.saveInfo(tableName=utils.weibo_tbl, columesName=utils.weibo_columes, values=insertStr)
        return int(item['id'])

    def processContent(self, item):
        content = item['content']
        content = re.sub(r'<[^<]+>', "", content)
        content = re.sub(r'\"', "\\\"", content)
        content = re.sub("\n", "", content)
        return content

    def update(self):
        self.ids = self.dao.getIdsBySource(utils.weibo_tbl, utils.gettoday(), "weibo")
        indexId = self.getDatas(0)
        while indexId != None:
            indexId = self.getDatas(indexId)
            time.sleep(5)


class headfirst:
    dao = infoDao.infoDao()
    baseUrl = "http://www.qukuaiwang.com.cn"

    def getHtml(self, url, values):
        try:
            user_agent = 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36'
            headers = {'User-Agent': user_agent}
            data = urllib.parse.urlencode(values)
            response_result = urllib.request.urlopen(url + '?' + data).read()
        except Exception as e:
            return None
        html = response_result.decode('utf-8')
        return html

    # 获取数据
    def requestCnblogs(self, index):
        # print('请求数据')
        url = 'http://www.qukuaiwang.com.cn/News/index/p/' + str(index) + '.html'

        value = {
            'CategoryId': 808,
            'CategoryType': 'SiteHome',
            'ItemListActionName': 'PostList',
            'PageIndex': index,
            'ParentCategoryId': 0,
            'TotalPostCount': 4000
        }
        result = self.getHtml(url, value)
        # print(result)
        return result




    def getdetails(self, src):
        """获取头条详情信息"""

        value = {
            'CategoryId': 808,
            'CategoryType': 'SiteHome',
            'ItemListActionName': 'PostList',
            'ParentCategoryId': 0,
            'TotalPostCount': 4000
        }
        result = self.getHtml(src, value)
        soup = BeautifulSoup(result, 'html.parser')
        contens = soup.find('div', attrs={'class': 'contents'})
        title = contens.h1.text
        contendetail = contens.find('div', attrs={'class': 'content-art'})
        imgs=contens.find_all('img');
        img=""
        for image in imgs:
            img+=image['src']+";"
        contentdetals=self.process(contendetail.text)
        insertStr = "\"{}\",\"{}\",\"{}\"".format(title, contentdetals, img)
        self.dao.saveInfo(utils.qukuaiwang_detail_tbl, utils.qukuaiwang_detail_columes, insertStr)
        ###########
        #入库


    def flush(self, index):
        cnblogs = self.requestCnblogs(index)
        if cnblogs == None:
            return None
        soup = BeautifulSoup(cnblogs, 'html.parser')
        all_div = soup.find_all('div', attrs={'class': 'list-art clear'})

        for item in all_div:
            content = self.processContent(item)
            it = content.split(";")
            if it[0] in self.titles:
                continue
            newstime = re.sub("/", "-", it[2])
            if newstime.split(" ")[0] != utils.gettoday():
                return None
            print(content)
            print('http://www.qukuaiwang.com.cn' + item.a['href'])
            title = it[0]
            author = it[1]
            hots = it[3]
            img = self.baseUrl + item.img['src']
            newsuri = 'http://www.qukuaiwang.com.cn' + item.a['href']
            insertStr = "\"{}\",\"{}\",\"{}\",\"{}\",\"{}\",{}".format(title, author, newstime, newsuri, img, hots)
            self.dao.saveInfo(utils.qukuaiwang_tbl, utils.qukuaiwang_columes, insertStr)
        time.sleep(5)

    def processContent(self, item):
        content = item.text;
        content = content.strip();
        content = re.sub(r'<[^<]+>', "", content)
        content = re.sub(r'\"', "\\\"", content)
        content = re.sub("\n+", ";", content)
        return content

    def update(self):
        self.titles = self.getTitles()
        for i in range(0, 20):
            res = self.flush(i)
            if res == None:
                break

    def getTitles(self):
        return self.dao.getData(utils.qukuaiwang_tbl, "title", utils.gettoday())


if __name__ == "__main__":
    """获取资讯数据主入口函数"""
    bishijieinstance = bishijie()
    kuaixuninstance = jinseKuaixun()
    weiboinstance = weibo()
    twitterinstance = twitter()
    headfirstinstance = headfirst()

    spiders = [bishijieinstance, kuaixuninstance, weiboinstance, twitterinstance,headfirstinstance]
    while True:
        for its in spiders:
            its.dao.reconnect()
            its.update()
