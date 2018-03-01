import kuaixun_bishijie
import kuaixun_jinse
import niurenshuo_weibo
import niurenshuo_twitter
import qukuaiwang_news
import time

if __name__ == "__main__":
    qukuaiwanginstance = qukuaiwang_news.qukuaiwang()
    kuaixun = kuaixun_jinse.Kuaixun()
    twitterinstance = niurenshuo_twitter.twitter()
    weiboInstance = niurenshuo_weibo.weibo()
    bishijieinstance = kuaixun_bishijie.bishijie()
    # spiders=[qukuaiwanginstance,kuaixun,twitterinstance,weiboInstance,bishijieinstance]
    spiders=[qukuaiwanginstance,kuaixun,twitterinstance,weiboInstance,bishijieinstance]

    while True:
        for its in spiders:
            its.dao.reconnect()
            its.update()
        # qukuaiwanginstance.update()
        # kuaixun.update()
        # twitterinstance.update()
        # weiboInstance.update()
        # bishijieinstance.update()
        time.sleep(20)
