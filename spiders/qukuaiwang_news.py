# coding:utf-8 #
import re
import time
import urllib.parse
import urllib.request
import utils
from bs4 import BeautifulSoup
import infoDao


class qukuaiwang:
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
        # print('请求数据')

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

            self.getdetails(newsuri)
            insertStr = "\"{}\",\"{}\",\"{}\",\"{}\",\"{}\",{}".format(title, author, newstime, newsuri, img, hots)
            self.dao.saveInfo(utils.qukuaiwang_tbl, utils.qukuaiwang_columes, insertStr)
        time.sleep(5)


    def process(self, texts):
        """详情中的空白用分号替代"""
        content = texts;
        content = content.strip();
        content = re.sub(r'\s+', ";", content)
        content = re.sub(r';+', ";", content)
        return content

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
    qukuaiwanginstance = qukuaiwang()
    while True:
        qukuaiwanginstance.update()
        time.sleep(33)
