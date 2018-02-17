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

    def getHtml(self,url, values):
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
    def requestCnblogs(self,index):
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

    def flush(self,index):
        cnblogs = self.requestCnblogs(index)
        if cnblogs ==None:
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
            newsuri = 'http://www.qukuaiwang.com.cn' + item.a['href']
            insertStr = "\"{}\",\"{}\",\"{}\",\"{}\"".format(title, author, newstime, newsuri)
            self.dao.saveInfo(utils.qukuaiwang_tbl,utils.qukuaiwang_columes,insertStr)
        time.sleep(5)

    def processContent(self, item):
        content = item.text;
        content = content.strip();
        content = re.sub(r'<[^<]+>', "", content)
        content = re.sub(r'\"', "\\\"", content)
        content = re.sub("\n+", ";", content)
        return content

    def update(self):
        self.titles=self.getTitles()
        for i in range(0,20):
            res=self.flush(i)
            if res ==None:
                break

    def getTitles(self):
        return self.dao.getData(utils.qukuaiwang_tbl,"title",utils.gettoday())

# create table tbl_qukuaiwang (id int auto_increment primary key,title text, author varchar(255),newstime datetime,  newsuri varchar(255) ) engine=InnoDB default charset=utf8;


if __name__ == "__main__":
    qukuaiwanginstance=qukuaiwang()
    while True:
        qukuaiwanginstance.update()
        time.sleep(33)
