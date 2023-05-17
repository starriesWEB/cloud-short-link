-- MySQL dump 10.13  Distrib 5.7.34, for Win64 (x86_64)
--
-- Host: 47.104.106.160    Database: cloud_account
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Current Database: `cloud_account`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cloud_account` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloud_account`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `account_no` bigint DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '盐，用于个人敏感信息处理',
  `mail` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `auth` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '认证级别，DEFAULT，REALNAME，ENTERPRISE，访问次数不一样',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE,
  UNIQUE KEY `uk_account` (`account_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,862797238783471616,'https://short-link-cloud.oss-cn-qingdao.aliyuncs.com/user/2023/04/25/05b5b7c4e0c745589e16e14a09f425c3.jpg','13227579809','$1$99gZFQq0$M15m4RgUMJK/AipA/Y58G/','$1$99gZFQq0',NULL,'test','DEFAULT','2023-05-09 20:48:12','2023-05-09 20:48:12');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traffic_0`
--

DROP TABLE IF EXISTS `traffic_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traffic_0` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `day_limit` int DEFAULT NULL COMMENT '每天限制多少条，短链',
  `day_used` int DEFAULT NULL COMMENT '当天用了多少条，短链',
  `total_limit` int DEFAULT NULL COMMENT '总次数，活码才用',
  `account_no` bigint DEFAULT NULL COMMENT '账号',
  `out_trade_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单号',
  `level` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品层级：FIRST青铜、SECOND黄金、THIRD钻石',
  `expired_date` date DEFAULT NULL COMMENT '过期日期',
  `plugin_type` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件类型',
  `product_id` bigint DEFAULT NULL COMMENT '商品主键',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trade_no` (`out_trade_no`,`account_no`),
  KEY `idx_account_no` (`account_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1655917950920122370 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traffic_0`
--

LOCK TABLES `traffic_0` WRITE;
/*!40000 ALTER TABLE `traffic_0` DISABLE KEYS */;
INSERT INTO `traffic_0` VALUES (1655917589605998593,2,2,NULL,862797238783471616,'free_init','FIRST','2023-06-08','SHORT_LINK',1,'2023-05-09 20:48:13','2023-05-11 17:56:28'),(1655917950920122369,5,0,NULL,862797238783471616,'2CLLiK9OVJMiwXAyDIYTS1zwOiMnEcwr','SECOND','2023-06-08','SHORT_LINK',2,'2023-05-09 20:49:39','2023-05-11 17:20:31');
/*!40000 ALTER TABLE `traffic_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traffic_1`
--

DROP TABLE IF EXISTS `traffic_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traffic_1` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `day_limit` int DEFAULT NULL COMMENT '每天限制多少条，短链',
  `day_used` int DEFAULT NULL COMMENT '当天用了多少条，短链',
  `total_limit` int DEFAULT NULL COMMENT '总次数，活码才用',
  `account_no` bigint DEFAULT NULL COMMENT '账号',
  `out_trade_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单号',
  `level` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品层级：FIRST青铜、SECOND黄金、THIRD钻石',
  `expired_date` date DEFAULT NULL COMMENT '过期日期',
  `plugin_type` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件类型',
  `product_id` bigint DEFAULT NULL COMMENT '商品主键',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trade_no` (`out_trade_no`,`account_no`),
  KEY `idx_account_no` (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traffic_1`
--

LOCK TABLES `traffic_1` WRITE;
/*!40000 ALTER TABLE `traffic_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `traffic_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traffic_task`
--

DROP TABLE IF EXISTS `traffic_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traffic_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `account_no` bigint DEFAULT NULL,
  `traffic_id` bigint DEFAULT NULL,
  `use_times` int DEFAULT NULL,
  `lock_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '锁定状态锁定LOCK 完成FINISH-取消CANCEL',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '唯一标识',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_biz_id` (`biz_id`) USING BTREE,
  KEY `idx_release` (`account_no`,`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traffic_task`
--

LOCK TABLES `traffic_task` WRITE;
/*!40000 ALTER TABLE `traffic_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `traffic_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `cloud_link_0`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cloud_link_0` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloud_link_0`;

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain` (
  `id` bigint unsigned NOT NULL,
  `account_no` bigint DEFAULT NULL COMMENT '用户自己绑定的域名',
  `domain_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '域名类型，自建custom, 官方offical',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `del` int unsigned DEFAULT '0' COMMENT '0是默认，1是禁用',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domain`
--

LOCK TABLES `domain` WRITE;
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
INSERT INTO `domain` VALUES (1,NULL,'OFFICIAL','baidu.com',0,'2023-04-28 13:28:56','2023-04-28 13:28:56'),(2,858118728684957696,'CUSTOM','aliyun.com',0,'2023-04-28 13:31:57','2023-04-28 13:40:41');
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_code_mapping_0`
--

DROP TABLE IF EXISTS `group_code_mapping_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_code_mapping_0` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_code_mapping_0`
--

LOCK TABLES `group_code_mapping_0` WRITE;
/*!40000 ALTER TABLE `group_code_mapping_0` DISABLE KEYS */;
INSERT INTO `group_code_mapping_0` VALUES (1656590095786520578,1656227476160425986,'测试添加v1','863469748407529472&https://www.baidu.com/','baidu.com','a3AoZOD0','072106CCC9073939EFB373CE9D8CECC1','1970-01-01 07:59:59',862797238783471616,'2023-05-11 17:20:30','2023-05-11 17:20:30',0,'ACTIVE',NULL),(1656598813655744513,1656227476160425986,'测试添加v1','863478468889833473&https://www.baidu.com/','baidu.com','a4eQcq00','B9D0A5CC4B8AB769726CCBAF2AF1EA0F','1970-01-01 07:59:59',862797238783471616,'2023-05-11 17:55:09','2023-05-11 17:55:09',0,'ACTIVE',NULL),(1656599146582859778,1656227476160425986,'测试添加v1','863478801946931200&https://www.baidu.com/','baidu.com','1333vK00','F0B7FC8CEEE2146C2AAABC18C890640E','1970-01-01 07:59:59',862797238783471616,'2023-05-11 17:56:28','2023-05-11 17:56:28',0,'ACTIVE',NULL);
/*!40000 ALTER TABLE `group_code_mapping_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_code_mapping_1`
--

DROP TABLE IF EXISTS `group_code_mapping_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_code_mapping_1` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_code_mapping_1`
--

LOCK TABLES `group_code_mapping_1` WRITE;
/*!40000 ALTER TABLE `group_code_mapping_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_code_mapping_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_group`
--

DROP TABLE IF EXISTS `link_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link_group` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组名',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_group`
--

LOCK TABLES `link_group` WRITE;
/*!40000 ALTER TABLE `link_group` DISABLE KEYS */;
INSERT INTO `link_group` VALUES (1656227476160425986,'group1',862797238783471616,'2023-05-10 17:19:35','2023-05-10 17:19:35');
/*!40000 ALTER TABLE `link_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link_0`
--

DROP TABLE IF EXISTS `short_link_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link_0` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link_0`
--

LOCK TABLES `short_link_0` WRITE;
/*!40000 ALTER TABLE `short_link_0` DISABLE KEYS */;
/*!40000 ALTER TABLE `short_link_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link_a`
--

DROP TABLE IF EXISTS `short_link_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link_a` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link_a`
--

LOCK TABLES `short_link_a` WRITE;
/*!40000 ALTER TABLE `short_link_a` DISABLE KEYS */;
/*!40000 ALTER TABLE `short_link_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `cloud_link_1`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cloud_link_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloud_link_1`;

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain` (
  `id` bigint unsigned NOT NULL,
  `account_no` bigint DEFAULT NULL COMMENT '用户自己绑定的域名',
  `domain_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '域名类型，自建custom, 官方offical',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `del` int unsigned DEFAULT '0' COMMENT '0是默认，1是禁用',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domain`
--

LOCK TABLES `domain` WRITE;
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_code_mapping_0`
--

DROP TABLE IF EXISTS `group_code_mapping_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_code_mapping_0` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_code_mapping_0`
--

LOCK TABLES `group_code_mapping_0` WRITE;
/*!40000 ALTER TABLE `group_code_mapping_0` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_code_mapping_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_code_mapping_1`
--

DROP TABLE IF EXISTS `group_code_mapping_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_code_mapping_1` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_code_mapping_1`
--

LOCK TABLES `group_code_mapping_1` WRITE;
/*!40000 ALTER TABLE `group_code_mapping_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_code_mapping_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_group`
--

DROP TABLE IF EXISTS `link_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link_group` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组名',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_group`
--

LOCK TABLES `link_group` WRITE;
/*!40000 ALTER TABLE `link_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `link_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link_0`
--

DROP TABLE IF EXISTS `short_link_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link_0` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link_0`
--

LOCK TABLES `short_link_0` WRITE;
/*!40000 ALTER TABLE `short_link_0` DISABLE KEYS */;
INSERT INTO `short_link_0` VALUES (1656599149510483970,1656227476160425986,'测试添加v1','863478801946931200&https://www.baidu.com/','baidu.com','1333vK00','F0B7FC8CEEE2146C2AAABC18C890640E','1970-01-01 07:59:59',862797238783471616,'2023-05-11 17:56:29','2023-05-11 17:56:29',0,'ACTIVE',NULL);
/*!40000 ALTER TABLE `short_link_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link_a`
--

DROP TABLE IF EXISTS `short_link_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link_a` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link_a`
--

LOCK TABLES `short_link_a` WRITE;
/*!40000 ALTER TABLE `short_link_a` DISABLE KEYS */;
/*!40000 ALTER TABLE `short_link_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `cloud_link_a`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cloud_link_a` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloud_link_a`;

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain` (
  `id` bigint unsigned NOT NULL,
  `account_no` bigint DEFAULT NULL COMMENT '用户自己绑定的域名',
  `domain_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '域名类型，自建custom, 官方offical',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `del` int unsigned DEFAULT '0' COMMENT '0是默认，1是禁用',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domain`
--

LOCK TABLES `domain` WRITE;
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_code_mapping_0`
--

DROP TABLE IF EXISTS `group_code_mapping_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_code_mapping_0` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_code_mapping_0`
--

LOCK TABLES `group_code_mapping_0` WRITE;
/*!40000 ALTER TABLE `group_code_mapping_0` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_code_mapping_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_code_mapping_1`
--

DROP TABLE IF EXISTS `group_code_mapping_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_code_mapping_1` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_code_mapping_1`
--

LOCK TABLES `group_code_mapping_1` WRITE;
/*!40000 ALTER TABLE `group_code_mapping_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_code_mapping_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_group`
--

DROP TABLE IF EXISTS `link_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link_group` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组名',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_group`
--

LOCK TABLES `link_group` WRITE;
/*!40000 ALTER TABLE `link_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `link_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link_0`
--

DROP TABLE IF EXISTS `short_link_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link_0` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link_0`
--

LOCK TABLES `short_link_0` WRITE;
/*!40000 ALTER TABLE `short_link_0` DISABLE KEYS */;
INSERT INTO `short_link_0` VALUES (1656599071160885250,1656227476160425986,'测试添加v1','863478468889833473&https://www.baidu.com/','baidu.com','a4eQcq00','B9D0A5CC4B8AB769726CCBAF2AF1EA0F','1970-01-01 07:59:59',862797238783471616,'2023-05-11 17:56:10','2023-05-11 17:56:10',0,'ACTIVE',NULL);
/*!40000 ALTER TABLE `short_link_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `short_link_a`
--

DROP TABLE IF EXISTS `short_link_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `short_link_a` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `short_link_a`
--

LOCK TABLES `short_link_a` WRITE;
/*!40000 ALTER TABLE `short_link_a` DISABLE KEYS */;
/*!40000 ALTER TABLE `short_link_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `cloud_shop`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cloud_shop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloud_shop`;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品标题',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详情',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品层级：FIRST青铜、SECOND黄金、THIRD钻石',
  `old_amount` decimal(16,0) DEFAULT NULL COMMENT '原价',
  `amount` decimal(16,0) DEFAULT NULL COMMENT '现价',
  `plugin_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '工具类型 short_link、qrcode',
  `day_times` int DEFAULT NULL COMMENT '日次数：短链类型',
  `total_times` int DEFAULT NULL COMMENT '总次数：活码才有',
  `valid_day` int DEFAULT NULL COMMENT '有效天数',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'⻘铜会员-默认','数据查看⽀持||⽇⽣成短链{{dayTimes}}\n次||限制跳转50次||默认域名',NULL,'FIRST',19,0,'SHORT_LINK',2,NULL,1,'2021-10-14 17:33:44','2021-10-11 10:49:35'),(2,'⻩⾦会员-⽉度','数据查看⽀持||⽇⽣成短链{{dayTimes}}\n次||限制不限制||默认域名',NULL,'SECOND',99,1,'SHORT_LINK',5,NULL,30,'2021-10-19 14:36:28','2021-10-11 10:57:47'),(3,'⿊⾦会员-⽉度','数据查看⽀持||⽇⽣成短链{{dayTimes}}\n次||限制不限制||⾃定义域名',NULL,'THIRD',199,2,'SHORT_LINK',8,NULL,30,'2021-10-19 14:36:30','2021-10-11 11:01:13');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_order_0`
--

DROP TABLE IF EXISTS `product_order_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_order_0` (
  `id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL COMMENT '订单类型',
  `product_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `product_amount` decimal(16,2) DEFAULT NULL COMMENT '商品单价',
  `product_snapshot` varchar(2048) DEFAULT NULL COMMENT '商品快照',
  `buy_num` int DEFAULT NULL COMMENT '购买数量',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` varchar(11) DEFAULT NULL COMMENT 'NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `total_amount` decimal(16,2) DEFAULT NULL COMMENT '订单总金额',
  `pay_amount` decimal(16,2) DEFAULT NULL COMMENT '订单实际支付价格',
  `pay_type` varchar(64) DEFAULT NULL COMMENT '支付类型，微信-银行-支付宝',
  `nickname` varchar(64) DEFAULT NULL COMMENT '账号昵称',
  `account_no` bigint DEFAULT NULL COMMENT '用户id',
  `del` int DEFAULT '0' COMMENT '0表示未删除，1表示已经删除',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `bill_type` varchar(32) DEFAULT NULL COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
  `bill_header` varchar(200) DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(200) DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '发票收票人电话',
  `bill_receiver_email` varchar(200) DEFAULT NULL COMMENT '发票收票人邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_query` (`out_trade_no`,`account_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_order_0`
--

LOCK TABLES `product_order_0` WRITE;
/*!40000 ALTER TABLE `product_order_0` DISABLE KEYS */;
INSERT INTO `product_order_0` VALUES (1655917813078466561,2,'⻩⾦会员-⽉度',1.00,'{\"id\":2,\"title\":\"⻩⾦会员-⽉度\",\"detail\":\"数据查看⽀持||⽇⽣成短链{{dayTimes}}\\n次||限制不限制||默认域名\",\"img\":null,\"level\":\"SECOND\",\"oldAmount\":99,\"amount\":1,\"pluginType\":\"SHORT_LINK\",\"dayTimes\":5,\"totalTimes\":null,\"validDay\":30,\"gmtModified\":\"2021-10-19T14:36:28\",\"gmtCreate\":\"2021-10-11T10:57:47\"}',1,'2CLLiK9OVJMiwXAyDIYTS1zwOiMnEcwr','PAY','2023-05-09 20:49:07',1.00,1.00,'WECHAT_PAY','test',858118728684957696,0,'2023-05-09 20:49:39','2023-05-09 20:49:06','NO_BILL','','','','');
/*!40000 ALTER TABLE `product_order_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_order_1`
--

DROP TABLE IF EXISTS `product_order_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_order_1` (
  `id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL COMMENT '订单类型',
  `product_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `product_amount` decimal(16,2) DEFAULT NULL COMMENT '商品单价',
  `product_snapshot` varchar(2048) DEFAULT NULL COMMENT '商品快照',
  `buy_num` int DEFAULT NULL COMMENT '购买数量',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` varchar(11) DEFAULT NULL COMMENT 'NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `total_amount` decimal(16,2) DEFAULT NULL COMMENT '订单总金额',
  `pay_amount` decimal(16,2) DEFAULT NULL COMMENT '订单实际支付价格',
  `pay_type` varchar(64) DEFAULT NULL COMMENT '支付类型，微信-银行-支付宝',
  `nickname` varchar(64) DEFAULT NULL COMMENT '账号昵称',
  `account_no` bigint DEFAULT NULL COMMENT '用户id',
  `del` int DEFAULT '0' COMMENT '0表示未删除，1表示已经删除',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `bill_type` varchar(32) DEFAULT NULL COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
  `bill_header` varchar(200) DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(200) DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '发票收票人电话',
  `bill_receiver_email` varchar(200) DEFAULT NULL COMMENT '发票收票人邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_query` (`out_trade_no`,`account_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_order_1`
--

LOCK TABLES `product_order_1` WRITE;
/*!40000 ALTER TABLE `product_order_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_order_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `nacos_config`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `nacos_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nacos_config`;

--
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8mb3_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8mb3_bin,
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` VALUES (0,1,'test','DEFAULT_GROUP','','test','098f6bcd4621d373cade4e832627b4f6','2023-04-22 11:01:17','2023-04-21 22:01:17','nacos','111.60.2.139','I',''),(1,2,'test','DEFAULT_GROUP','','test','098f6bcd4621d373cade4e832627b4f6','2023-04-22 11:01:22','2023-04-21 22:01:22','nacos','111.60.2.139','D','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('nacos','ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `xxl_job`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `xxl_job` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `xxl_job`;

--
-- Table structure for table `xxl_job_group`
--

DROP TABLE IF EXISTS `xxl_job_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `address_type` tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_group`
--

LOCK TABLES `xxl_job_group` WRITE;
/*!40000 ALTER TABLE `xxl_job_group` DISABLE KEYS */;
INSERT INTO `xxl_job_group` VALUES (1,'xxl-job-executor-sample','示例执行器',0,NULL),(2,'traffic-app-executor','流量包执行器',0,'http://192.168.137.1:9999/');
/*!40000 ALTER TABLE `xxl_job_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_info`
--

DROP TABLE IF EXISTS `xxl_job_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_info`
--

LOCK TABLES `xxl_job_info` WRITE;
/*!40000 ALTER TABLE `xxl_job_info` DISABLE KEYS */;
INSERT INTO `xxl_job_info` VALUES (1,1,'0 0 0 * * ? *','测试任务1','2018-11-03 22:21:31','2018-11-03 22:21:31','XXL','','FIRST','demoJobHandler','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2018-11-03 22:21:31','',0,0,0),(2,2,'0/5 * * * * ?','删除过期流量包','2023-05-09 12:33:15','2023-05-09 12:34:52','starry','','FIRST','trafficExpiredHandler','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2023-05-09 12:33:15','',0,0,0);
/*!40000 ALTER TABLE `xxl_job_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_lock`
--

DROP TABLE IF EXISTS `xxl_job_lock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_lock`
--

LOCK TABLES `xxl_job_lock` WRITE;
/*!40000 ALTER TABLE `xxl_job_lock` DISABLE KEYS */;
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');
/*!40000 ALTER TABLE `xxl_job_lock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_log`
--

DROP TABLE IF EXISTS `xxl_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_log`
--

LOCK TABLES `xxl_job_log` WRITE;
/*!40000 ALTER TABLE `xxl_job_log` DISABLE KEYS */;
INSERT INTO `xxl_job_log` VALUES (1,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:24',200,'任务触发类型：手动触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:33:55',500,'java.lang.reflect.InvocationTargetException\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at com.xxl.job.core.handler.impl.MethodJobHandler.execute(MethodJobHandler.java:29)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:152)\r\nCaused by: org.springframework.dao.TransientDataAccessResourceException: \r\n### Error updating database.  Cause: java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30066ms.\r\n### The error may exist in com/starry/mapper/TrafficMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: DELETE FROM traffic     WHERE (expired_date <= ?)\r\n### Cause: java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30066ms.\n; HikariPool-1 - Connection is not available, request timed out after 30066ms.; nested exception is java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30066ms.\r\n	at org.springframework.jdbc.support.SQLExceptionSubclassTranslator.doTranslate(SQLExceptionSubclassTranslator.java:70)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:70)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:79)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:88)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:440)\r\n	at com.sun.proxy.$Proxy111.delete(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.delete(SqlSessionTemplate.java:303)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(MybatisMapperMethod.java:70)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethodInvoker.invoke(MybatisMapperProxy.java:148)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at com.sun.proxy.$Proxy115.delete(Unknown Source)\r\n	at com.starry.manager.impl.TrafficManagerImpl.deleteExpireTraffic(TrafficManagerImpl.java:75)\r\n	at com.starry.service.impl.TrafficServiceImpl.deleteExpireTraffic(TrafficServiceImpl.java:132)\r\n	at com.starry.service.impl.TrafficServiceImpl$$FastClassBySpringCGLIB$$b9bbb5e5.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)\r\n	at com.starry.service.impl.TrafficServiceImpl$$EnhancerBySpringCGLIB$$197412ee.deleteExpireTraffic(<generated>)\r\n	at com.starry.job.TrafficJobHandler.execute(TrafficJobHandler.java:27)\r\n	... 6 more\r\nCaused by: java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30066ms.\r\n	at com.zaxxer.hikari.pool.HikariPool.createTimeoutException(HikariPool.java:696)\r\n	at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:197)\r\n	at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:162)\r\n	at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:128)\r\n	at org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShardingConnection.createConnection(ShardingConnection.java:69)\r\n	at org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractConnectionAdapter.createConnections(AbstractConnectionAdapter.java:121)\r\n	at org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractConnectionAdapter.getConnections(AbstractConnectionAdapter.java:110)\r\n	at org.apache.shardingsphere.shardingjdbc.executor.PreparedStatementExecutor$1.getConnections(PreparedStatementExecutor.java:73)\r\n	at org.apache.shardingsphere.sharding.execute.sql.prepare.SQLExecutePrepareTemplate.getSQLExecuteGroups(SQLExecutePrepareTemplate.java:84)\r\n	at org.apache.shardingsphere.sharding.execute.sql.prepare.SQLExecutePrepareTemplate.getSynchronizedExecuteUnitGroups(SQLExecutePrepareTemplate.java:62)\r\n	at org.apache.shardingsphere.sharding.execute.sql.prepare.SQLExecutePrepareTemplate.getExecuteUnitGroups(SQLExecutePrepareTemplate.java:54)\r\n	at org.apache.shardingsphere.shardingjdbc.executor.PreparedStatementExecutor.obtainExecuteGroups(PreparedStatementExecutor.java:69)\r\n	at org.apache.shardingsphere.shardingjdbc.executor.PreparedStatementExecutor.init(PreparedStatementExecutor.java:64)\r\n	at org.apache.shardingsphere.shardingjdbc.jdbc.core.statement.ShardingPreparedStatement.initPreparedStatementExecutor(ShardingPreparedStatement.java:211)\r\n	at org.apache.shardingsphere.shardingjdbc.jdbc.core.statement.ShardingPreparedStatement.execute(ShardingPreparedStatement.java:144)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.logging.jdbc.PreparedStatementLogger.invoke(PreparedStatementLogger.java:59)\r\n	at com.sun.proxy.$Proxy172.execute(Unknown Source)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:47)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:74)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.$Proxy171.update(Unknown Source)\r\n	at com.baomidou.mybatisplus.core.executor.MybatisSimpleExecutor.doUpdate(MybatisSimpleExecutor.java:56)\r\n	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:117)\r\n	at com.baomidou.mybatisplus.core.executor.MybatisCachingExecutor.update(MybatisCachingExecutor.java:85)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Invocation.proceed(Invocation.java:49)\r\n	at com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor.intercept(MybatisPlusInterceptor.java:82)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:61)\r\n	at com.sun.proxy.$Proxy170.update(Unknown Source)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:197)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.delete(DefaultSqlSession.java:212)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:426)\r\n	... 19 more\r\nCaused by: java.sql.SQLNonTransientConnectionException: No operations allowed after connection closed.\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:110)\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:89)\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:63)\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:73)\r\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:73)\r\n	at com.mysql.cj.jdbc.ConnectionImpl.setNetworkTimeout(ConnectionImpl.java:2480)\r\n	at com.zaxxer.hikari.pool.PoolBase.setNetworkTimeout(PoolBase.java:566)\r\n	at com.zaxxer.hikari.pool.PoolBase.isConnectionAlive(PoolBase.java:173)\r\n	at com.zaxxer.hikari.pool.HikariPool.getConnection(HikariPool.java:186)\r\n	... 64 more\r\nCaused by: com.mysql.cj.exceptions.ConnectionIsClosedException: No operations allowed after connection closed.\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\r\n	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)\r\n	at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:61)\r\n	at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:105)\r\n	at com.mysql.cj.exceptions.ExceptionFactory.createException(ExceptionFactory.java:151)\r\n	at com.mysql.cj.NativeSession.checkClosed(NativeSession.java:758)\r\n	at com.mysql.cj.jdbc.ConnectionImpl.checkClosed(ConnectionImpl.java:568)\r\n	at com.mysql.cj.jdbc.ConnectionImpl.setNetworkTimeout(ConnectionImpl.java:2476)\r\n	... 67 more\r\n',1),(2,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:30',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:41',500,'人为操作，主动终止:',1),(3,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:35',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:15',200,'',0),(4,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:40',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:16',200,'',0),(5,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:45',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:16',200,'',0),(6,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:50',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:16',200,'',0),(7,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:33:55',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:16',200,'',0),(8,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:16',200,'',0),(9,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:05',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:17',200,'',0),(10,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:10',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:17',200,'',0),(11,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:15',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:17',200,'',0),(12,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:20',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:21',200,'',0),(13,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:25',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:26',200,'',0),(14,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:30',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:31',200,'',0),(15,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:35',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:36',200,'',0),(16,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:40',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:41',200,'',0),(17,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:45',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:46',200,'',0),(18,2,2,'http://192.168.137.1:9999/','trafficExpiredHandler','',NULL,0,'2023-05-09 12:34:50',200,'任务触发类型：Cron触发<br>调度机器：192.168.137.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.137.1:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.137.1:9999/<br>code：200<br>msg：null','2023-05-09 12:34:51',200,'',0);
/*!40000 ALTER TABLE `xxl_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_log_report`
--

DROP TABLE IF EXISTS `xxl_job_log_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_log_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_log_report`
--

LOCK TABLES `xxl_job_log_report` WRITE;
/*!40000 ALTER TABLE `xxl_job_log_report` DISABLE KEYS */;
INSERT INTO `xxl_job_log_report` VALUES (1,'2023-05-09 00:00:00',0,16,2),(2,'2023-05-08 00:00:00',0,0,0),(3,'2023-05-07 00:00:00',0,0,0);
/*!40000 ALTER TABLE `xxl_job_log_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_logglue`
--

DROP TABLE IF EXISTS `xxl_job_logglue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_logglue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_logglue`
--

LOCK TABLES `xxl_job_logglue` WRITE;
/*!40000 ALTER TABLE `xxl_job_logglue` DISABLE KEYS */;
/*!40000 ALTER TABLE `xxl_job_logglue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_registry`
--

DROP TABLE IF EXISTS `xxl_job_registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_registry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_registry`
--

LOCK TABLES `xxl_job_registry` WRITE;
/*!40000 ALTER TABLE `xxl_job_registry` DISABLE KEYS */;
/*!40000 ALTER TABLE `xxl_job_registry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_user`
--

DROP TABLE IF EXISTS `xxl_job_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xxl_job_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_user`
--

LOCK TABLES `xxl_job_user` WRITE;
/*!40000 ALTER TABLE `xxl_job_user` DISABLE KEYS */;
INSERT INTO `xxl_job_user` VALUES (1,'admin','e10adc3949ba59abbe56e057f20f883e',1,NULL);
/*!40000 ALTER TABLE `xxl_job_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-17 12:07:43
