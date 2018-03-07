-- MySQL dump 10.14  Distrib 5.5.43-MariaDB, for Linux (x86_64)
--
-- Host: 120.79.34.242    Database: virtualTrade
-- ------------------------------------------------------
-- Server version	5.6.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vt_app_custom`
--

DROP TABLE IF EXISTS `vt_app_custom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_app_custom` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(120) NOT NULL DEFAULT '' COMMENT '问题',
  `answer` text COMMENT '答案',
  `inputtime` datetime NOT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_app_feedback`
--

DROP TABLE IF EXISTS `vt_app_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_app_feedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '内容',
  `callway` varchar(120) NOT NULL DEFAULT '' COMMENT '联系方式',
  `ip` varchar(15) NOT NULL DEFAULT '' COMMENT '客户端ip',
  `inputtime` datetime NOT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_app_news`
--

DROP TABLE IF EXISTS `vt_app_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_app_news` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text COMMENT '内容',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型(1活动2公告)',
  `inputtime` datetime NOT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_info_block_news`
--

DROP TABLE IF EXISTS `vt_info_block_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_info_block_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `author` varchar(255) DEFAULT NULL,
  `newstime` datetime DEFAULT NULL,
  `newsuri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_info_coin`
--

DROP TABLE IF EXISTS `vt_info_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_info_coin` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name_cn` varchar(60) NOT NULL DEFAULT '' COMMENT '中文名称',
  `name_en` varchar(60) NOT NULL DEFAULT '' COMMENT '英文名称',
  `desc` varchar(1000) NOT NULL DEFAULT '' COMMENT '描述',
  `param` varchar(500) NOT NULL DEFAULT '' COMMENT '参数',
  `link` varchar(280) NOT NULL DEFAULT '' COMMENT '链接',
  `inputtime` datetime NOT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_info_ico`
--

DROP TABLE IF EXISTS `vt_info_ico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_info_ico` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL DEFAULT '' COMMENT '标题',
  `cover` varchar(255) NOT NULL DEFAULT '' COMMENT '封面',
  `content` text NOT NULL COMMENT '内容',
  `coin_type` varchar(60) NOT NULL DEFAULT '' COMMENT '币种',
  `coin_target` double NOT NULL DEFAULT '0' COMMENT '目标个数',
  `coin_get` double NOT NULL DEFAULT '0' COMMENT '筹集个数',
  `endtime` datetime NOT NULL COMMENT '到期时间',
  `inputtime` datetime NOT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_info_news`
--

DROP TABLE IF EXISTS `vt_info_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_info_news` (
  `id` int(11) NOT NULL  AUTO_INCREMENT,
  `createtime` datetime DEFAULT NULL,
  `content` text,
  `sourceurl` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_info_niuren`
--

DROP TABLE IF EXISTS `vt_info_niuren`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_info_niuren` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `content` text,
  `source` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_market_depth`
--

DROP TABLE IF EXISTS `vt_market_depth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_market_depth` (
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  `platform` varchar(16) DEFAULT NULL COMMENT '平台',
  `symbol` varchar(16) DEFAULT NULL COMMENT 'symbol',
  `type` varchar(16) DEFAULT NULL COMMENT '挂单类型',
  `price` double(32,16) DEFAULT NULL COMMENT '挂单价格',
  `amount` double(32,16) DEFAULT NULL COMMENT '挂单数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_market_tick`
--

DROP TABLE IF EXISTS `vt_market_tick`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_market_tick` (
  `Id` int(11) DEFAULT NULL COMMENT '自增长',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  `platform` varchar(16) DEFAULT NULL COMMENT '平台',
  `symbol` varchar(16) DEFAULT NULL COMMENT 'symbol',
  `open` double(32,16) DEFAULT NULL COMMENT '开盘',
  `close` double(32,16) DEFAULT NULL COMMENT '收盘最后一笔交易',
  `low` double(32,16) DEFAULT NULL COMMENT '最低',
  `high` double(32,16) DEFAULT NULL COMMENT '最高',
  `amount` double(32,16) DEFAULT NULL COMMENT '成交量',
  `vol` double(32,16) DEFAULT NULL COMMENT '成交额度'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_u_account`
--

DROP TABLE IF EXISTS `vt_u_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_u_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(60) NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `inviter` int(11) NOT NULL DEFAULT '0' COMMENT '邀请人id',
  `invitecode` int(8) NOT NULL DEFAULT '0' COMMENT '邀请码',
  `registtime` datetime DEFAULT NULL COMMENT '注册时间',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(1正常)',
  `edittime` datetime DEFAULT NULL COMMENT '状态更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_u_coin`
--

DROP TABLE IF EXISTS `vt_u_coin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_u_coin` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `coin` varchar(10) NOT NULL COMMENT '货币名称',
  `total_quantity` decimal(16,8) NOT NULL COMMENT '持仓数量',
  `freeze_quantity` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '冻结数量',
  PRIMARY KEY (`user_id`,`coin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户持有货币表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_u_delegate_trade`
--

DROP TABLE IF EXISTS `vt_u_delegate_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_u_delegate_trade` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `price` decimal(16,8) NOT NULL COMMENT '委托价格',
  `quantity` decimal(16,8) NOT NULL COMMENT '交易数量',
  `amount` decimal(16,8) NOT NULL COMMENT '交易额',
  `remain_quantity` decimal(16,8) NOT NULL COMMENT '剩余交易数量',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态, 0-未处理,1-部分成交,2-已完成',
  `trade_type` int(3) NOT NULL COMMENT '交易类型',
  `is_canceled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否取消',
  `operate_type` int(1) NOT NULL COMMENT '操作类型,1-买入 2-卖出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户委托交易表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_u_identity`
--

DROP TABLE IF EXISTS `vt_u_identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_u_identity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL DEFAULT '0' COMMENT '用户唯一id',
  `realname` varchar(6) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `idcard` varchar(18) NOT NULL DEFAULT '' COMMENT '身份证号',
  `img_id1` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证-正面',
  `img_id2` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证-反面',
  `img_id3` varchar(255) NOT NULL DEFAULT '' COMMENT '身份证-手持',
  `img_sign` varchar(255) NOT NULL DEFAULT '' COMMENT '亲笔签名',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态(0正在审核1审核通过2审核未通过)',
  `inputtime` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_u_knockdown_trade`
--

DROP TABLE IF EXISTS `vt_u_knockdown_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_u_knockdown_trade` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `ask_trade_id` int(10) NOT NULL COMMENT '买入交易id',
  `bid_trade_id` int(10) NOT NULL COMMENT '卖出交易id',
  `price` decimal(16,8) NOT NULL COMMENT '成交价格',
  `quantity` decimal(16,8) NOT NULL COMMENT '成交数量',
  PRIMARY KEY (`ask_trade_id`,`bid_trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户交易成交表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_u_login`
--

DROP TABLE IF EXISTS `vt_u_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_u_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL DEFAULT '0',
  `loginip` varchar(15) NOT NULL DEFAULT '' COMMENT '客户端ip',
  `logintime` datetime DEFAULT NULL COMMENT '登录时间',
  `city` varchar(120) NOT NULL DEFAULT '' COMMENT '城市',
  `province` varchar(120) NOT NULL DEFAULT '' COMMENT '省份',
  `country` varchar(120) NOT NULL DEFAULT '' COMMENT '国家',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_user`
--

DROP TABLE IF EXISTS `vt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-01 19:48:18
