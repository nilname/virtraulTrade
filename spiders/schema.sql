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
  `inputtime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
  `ip` varchar(15) NOT NULL DEFAULT '',
  `inputtime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_app_news`
--

DROP TABLE IF EXISTS `vt_app_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_app_news` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL DEFAULT '',
  `content` text,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型(1活动2公告)',
  `inputtime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
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
  `inputtime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
  `endtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '到期时间',
  `inputtime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_info_news`
--

DROP TABLE IF EXISTS `vt_info_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_info_news` (
  `id` int(11) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `content` text,
  `sourceurl` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL
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
  `registtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '注册时间',
  `state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(1正常)',
  `edittime` bigint(20) NOT NULL DEFAULT '0' COMMENT '状态更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
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
  `inputtime` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
  `loginip` varchar(15) NOT NULL DEFAULT '',
  `logintime` bigint(20) NOT NULL DEFAULT '0',
  `city` varchar(120) NOT NULL DEFAULT '',
  `province` varchar(120) NOT NULL DEFAULT '',
  `country` varchar(120) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-17  9:15:00
