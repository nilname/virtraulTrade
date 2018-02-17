# coding:utf-8 #
import pymysql
import utils


class infoDao:
    # 打开数据库连接

    def __init__(self, dbename=utils.database, username=utils.user, password=utils.passwd, host=utils.host):
        self.dbname = dbename
        self.db = pymysql.connect(host, username, password, self.dbname, charset="utf8",connect_timeout=3600)
        self.db.connect_timeout=3600

    def getVersion(self):
        # 使用 cursor() 方法创建一个游标对象 cursor
        cursor = self.db.cursor()
        # 使用 execute()  方法执行 SQL 查询
        cursor.execute("show tables")

        # 使用 fetchall() 方法获取全部数据.
        data = cursor.fetchall()
        for entry in data:
            print("Database version----- : %s " % entry)

    def saveInfo(self, tableName, columesName, values):
        # 使用cursor()方法获取操作游标
        cursor = self.db.cursor()

        # SQL 插入语句
        sql = """insert into  {} ( {} )values({});""".format(tableName, columesName, values)
        try:
            # 执行sql语句
            print(sql)
            cursor.execute(sql)
            # 提交到数据库执行
            self.db.commit()
        except Exception as e:
            print(e)
            # 如果发生错误则回滚

            self.db.rollback()

    def getId(self,tableName,timedate):
        cursor = self.db.cursor()

        sql = """select max(id) from {} where createtime like "{}%";""".format(tableName, timedate)
        try:
            # 执行sql语句
            print(sql)
            cursor.execute(sql)
            if cursor.rowcount==1:
                data = cursor.fetchone()
                return data[0]
            else:
                return None
        finally:
            pass

    def getIds(self,tableName,timedate):
        cursor = self.db.cursor()

        sql = """select distinct id from {} where createtime like "{}%";""".format(tableName, timedate)
        try:
            # 执行sql语句
            print(sql)
            cursor.execute(sql)
            data = cursor.fetchall()
            relist=[]
            for it in data:
                relist.append(it[0])
            return relist

        finally:
            pass
    def getIdsBySource(self,tableName,timedate,source):
        cursor = self.db.cursor()

        sql = """select distinct id from {} where source="{}" and createtime like "{}%";""".format(tableName, source,timedate)
        try:
            # 执行sql语句
            print(sql)
            cursor.execute(sql)
            data = cursor.fetchall()
            relist=[]
            for it in data:
                relist.append(it[0])
            return relist

        finally:
            pass
    def getData(self,tablename, colume, datedata):
        cursor = self.db.cursor()

        sql = """select distinct {} from {} where newstime like "{}%";""".format(colume,tablename, datedata)
        try:
            # 执行sql语句
            print(sql)
            cursor.execute(sql)
            data = cursor.fetchall()
            relist = []
            for it in data:
                relist.append(it[0])
            return relist

        finally:
            pass

    def reconnect(self):
        self.db.ping(True)

    # def __del__(self):
    #     # 关闭数据库连接
    #     self.db.close()


if __name__ == "__main__":
    dao = infoDao();
    d=dao.getIds("tbl_kuaixun","2018-02-10")

    for i in d:
        print(i)
    # dao.getVersion()
    # dao.saveInfo(tableName='test', columesName='id,times,content', values="""1,"10:22:33","shibushi" """)
