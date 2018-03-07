# coding:utf-8 #
import pymysql
import utils


##############################################
#
# 公共的数据库接口，行情数据和咨询数据的数据库操作都调用
# 这里的方法
#
#################################################

class infoDao:
    # 打开数据库连接

    def __init__(self, dbename=utils.database, username=utils.user, password=utils.passwd, host=utils.host):
        """初始化数据库连接信息"""
        self.dbname = dbename
        self.username = username
        self.password = password
        self.host = host
        self.db = pymysql.connect(self.host, self.username, self.password, self.dbname, charset="utf8", connect_timeout=3600)

    def connect(self):
        self.db = pymysql.connect(self.host, self.username, self.password, self.dbname, charset="utf8", connect_timeout=3600)
        self.db.connect_timeout = 3600

    def getVersion(self):
        """测试数据库连接是否成功"""
        cursor = self.db.cursor()
        # 使用 execute()  方法执行 SQL 查询
        cursor.execute("show tables")

        # 使用 fetchall() 方法获取全部数据.
        data = cursor.fetchall()
        for entry in data:
            print("Database version----- : %s " % entry)

    def dels(self, platform, symble, tablename):
        """行情数据获取到时删除数据，可以通过update语句替代"""
        cursor = self.db.cursor()
        cursor.execute("delete from  {} where platform='{}' and symbol='{}'".format(tablename, platform, symble))
        self.db.commit()

    def saveInfo(self, tableName, columesName, values):
        """数据入库接口，参数需要表名，列的列表，value的列表"""
        cursor = self.db.cursor()

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

    def getId(self, tableName, timedate):
        """获取行咨询数据的最到ID"""
        cursor = self.db.cursor()

        sql = """select max(id) from {} where createtime like "{}%";""".format(tableName, timedate)
        try:
            # 执行sql语句
            print(sql)
            cursor.execute(sql)
            if cursor.rowcount == 1:
                data = cursor.fetchone()
                return data[0]
            else:
                return None
        finally:
            pass

    def getIds(self, tableName, timedate):
        """获取咨询数据的id列表"""
        cursor = self.db.cursor()

        sql = """select distinct id from {} where createtime like "{}%";""".format(tableName, timedate)
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

    def getIdsBySource(self, tableName, timedate, source):
        """根据来源获取数据id"""
        cursor = self.db.cursor()

        sql = """select distinct id from {} where source="{}" and createtime like "{}%";""".format(tableName, source,
                                                                                                   timedate)
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

    def getData(self, tablename, colume, datedata):
        cursor = self.db.cursor()

        sql = """select distinct {} from {} where newstime like "{}%";""".format(colume, tablename, datedata)
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
        """连接检查"""
        self.db.close()
        self.connect()
        # self.db.ping(True)

        # def __del__(self):
        #     # 关闭数据库连接
        #     self.db.close()



if __name__ == "__main__":
    dao = infoDao();
    d = dao.getIds("tbl_kuaixun", "2018-02-10")

    for i in d:
        print(i)
        # dao.getVersion()
        # dao.saveInfo(tableName='test', columesName='id,times,content', values="""1,"10:22:33","shibushi" """)
