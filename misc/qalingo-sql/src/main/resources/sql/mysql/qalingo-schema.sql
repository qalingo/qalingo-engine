--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version 0.8.0)
--         http://www.apache.org/licenses/LICENSE-2.0
--
--                   Copyright (c) Hoteia, 2012-2014
-- http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
--
--

-- MySQL dump 10.13  Distrib 5.1.51, for Win64 (unknown)
--
-- Host: localhost    Database: qalingo
-- ------------------------------------------------------
-- Server version	5.1.51-community-log

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
-- Table structure for table `tbo_batch_process_object`
--

DROP TABLE IF EXISTS `tbo_batch_process_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_batch_process_object` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `OBJECT` longblob,
  `PROCESSED_COUNT` int(11) NOT NULL DEFAULT '0',
  `STATUS` varchar(255) DEFAULT NULL,
  `TYPE_OBJECT` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_company`
--

DROP TABLE IF EXISTS `tbo_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_company` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `DEFAULT_LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK871A81BFBB52ECD0` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK871A81BFBB52ECD0` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_company_localization_rel`
--

DROP TABLE IF EXISTS `tbo_company_localization_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_company_localization_rel` (
  `COMPANY_ID` bigint(20) NOT NULL,
  `LOCALIZATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`,`LOCALIZATION_ID`),
  KEY `FKC542DC53203F2066` (`COMPANY_ID`),
  KEY `FKC542DC53B56862CE` (`LOCALIZATION_ID`),
  CONSTRAINT `FKC542DC53B56862CE` FOREIGN KEY (`LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FKC542DC53203F2066` FOREIGN KEY (`COMPANY_ID`) REFERENCES `tbo_company` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_email`
--

DROP TABLE IF EXISTS `tbo_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_email` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EMAIL_CONTENT` longblob,
  `EXCEPTION_CONTENT` longblob,
  `PROCESSED_COUNT` int(11) NOT NULL DEFAULT '0',
  `STATUS` varchar(255) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_engine_session`
--

DROP TABLE IF EXISTS `tbo_engine_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_engine_session` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `ENGINE_SESSION_GUID` varchar(255) DEFAULT NULL,
  `JSESSION_ID` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_group`
--

DROP TABLE IF EXISTS `tbo_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_group` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_group_role_rel`
--

DROP TABLE IF EXISTS `tbo_group_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_group_role_rel` (
  `GROUP_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`GROUP_ID`,`ROLE_ID`),
  KEY `FK9583FB6E6BDBB619` (`ROLE_ID`),
  KEY `FK9583FB6E3D0ED11B` (`GROUP_ID`),
  CONSTRAINT `FK9583FB6E3D0ED11B` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`),
  CONSTRAINT `FK9583FB6E6BDBB619` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_menu`
--

DROP TABLE IF EXISTS `tbo_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `POSITION` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MENU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK456B9F3D2880990E` (`MENU_ID`),
  CONSTRAINT `FK456B9F3D2880990E` FOREIGN KEY (`MENU_ID`) REFERENCES `tbo_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_menu_group_rel`
--

DROP TABLE IF EXISTS `tbo_menu_group_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_menu_group_rel` (
  `MENU_ID` bigint(20) NOT NULL,
  `GROUP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MENU_ID`,`GROUP_ID`),
  KEY `FK363794172880990E` (`MENU_ID`),
  KEY `FK363794173D0ED11B` (`GROUP_ID`),
  CONSTRAINT `FK363794173D0ED11B` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`),
  CONSTRAINT `FK363794172880990E` FOREIGN KEY (`MENU_ID`) REFERENCES `tbo_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_menu_role_rel`
--

DROP TABLE IF EXISTS `tbo_menu_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_menu_role_rel` (
  `MENU_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MENU_ID`,`ROLE_ID`),
  KEY `FK99F21FF22880990E` (`MENU_ID`),
  KEY `FK99F21FF26BDBB619` (`ROLE_ID`),
  CONSTRAINT `FK99F21FF26BDBB619` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`),
  CONSTRAINT `FK99F21FF22880990E` FOREIGN KEY (`MENU_ID`) REFERENCES `tbo_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_permission`
--

DROP TABLE IF EXISTS `tbo_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_permission` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_role`
--

DROP TABLE IF EXISTS `tbo_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_role_permission_rel`
--

DROP TABLE IF EXISTS `tbo_role_permission_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_role_permission_rel` (
  `ROLE_ID` bigint(20) NOT NULL,
  `PERMISSION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`),
  KEY `FKA8268F546BDBB619` (`ROLE_ID`),
  KEY `FKA8268F54734B3839` (`PERMISSION_ID`),
  CONSTRAINT `FKA8268F54734B3839` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `tbo_permission` (`ID`),
  CONSTRAINT `FKA8268F546BDBB619` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_server_status`
--

DROP TABLE IF EXISTS `tbo_server_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_server_status` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LAST_CHECK_RECEIVED` datetime DEFAULT NULL,
  `MESSAGE_CONTENT` longblob,
  `SERVER_IP` varchar(255) DEFAULT NULL,
  `SERVER_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_user`
--

DROP TABLE IF EXISTS `tbo_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `COMPANY_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `LOGIN` (`LOGIN`,`CODE`,`EMAIL`),
  KEY `FK456F75A9203F2066` (`COMPANY_ID`),
  KEY `FK456F75A9BB52ECD0` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK456F75A9BB52ECD0` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK456F75A9203F2066` FOREIGN KEY (`COMPANY_ID`) REFERENCES `tbo_company` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_user_connection_log`
--

DROP TABLE IF EXISTS `tbo_user_connection_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_user_connection_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `APP` varchar(255) DEFAULT NULL,
  `HOST` varchar(255) DEFAULT NULL,
  `LOGIN_DATE` datetime DEFAULT NULL,
  `PRIVATE_ADDRESS` varchar(255) DEFAULT NULL,
  `PUBLIC_ADDRESS` varchar(255) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK49DF399E719F18E` (`USER_ID`),
  CONSTRAINT `FK49DF399E719F18E` FOREIGN KEY (`USER_ID`) REFERENCES `tbo_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbo_user_group_rel`
--

DROP TABLE IF EXISTS `tbo_user_group_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbo_user_group_rel` (
  `USER_ID` bigint(20) NOT NULL,
  `GROUP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`GROUP_ID`),
  KEY `FK4C635383E719F18E` (`USER_ID`),
  KEY `FK4C6353833D0ED11B` (`GROUP_ID`),
  CONSTRAINT `FK4C6353833D0ED11B` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`),
  CONSTRAINT `FK4C635383E719F18E` FOREIGN KEY (`USER_ID`) REFERENCES `tbo_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_asset`
--

DROP TABLE IF EXISTS `teco_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_asset` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `FILE_SIZE` bigint(20) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `PATH` varchar(255) DEFAULT NULL,
  `SCOPE` varchar(255) DEFAULT NULL,
  `SIZE` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  `VIRTUAL_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `STORE_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK475D66AE820B6ACF` (`PRODUCT_MARKETING_ID`),
  KEY `FK475D66AE4AAA0650` (`VIRTUAL_CATEGORY_ID`),
  KEY `FK475D66AEDBEE772F` (`PRODUCT_SKU_ID`),
  KEY `FK475D66AEE2E9A1E4` (`MASTER_CATEGORY_ID`),
  KEY `FK475D66AEC0E6A96E` (`RETAILER_ID`),
  KEY `FK475D66AE723F98E6` (`STORE_ID`),
  CONSTRAINT `FK475D66AE723F98E6` FOREIGN KEY (`STORE_ID`) REFERENCES `teco_store` (`ID`),
  CONSTRAINT `FK475D66AE4AAA0650` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK475D66AE820B6ACF` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`),
  CONSTRAINT `FK475D66AEC0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK475D66AEDBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK475D66AEE2E9A1E4` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_attribute_definition`
--

DROP TABLE IF EXISTS `teco_attribute_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_attribute_definition` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ATTRIBUTE_TYPE` int(11) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `ENABLED` tinyint(1) NOT NULL DEFAULT '0',
  `GLOBAL` tinyint(1) NOT NULL DEFAULT '1',
  `LOCALIZABLE` tinyint(1) NOT NULL DEFAULT '0',
  `MANDATORY` tinyint(1) NOT NULL DEFAULT '0',
  `MULTI_VALUE` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `OBJECT_TYPE` int(11) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `WITH_PLANNER` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_cart`
--

DROP TABLE IF EXISTS `teco_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_cart` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILLING_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `SHIPPING_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `ECO_ENGINE_SESSION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKA900B8224F2670D` (`CURRENCY_ID`),
  KEY `FKA900B82FA5C5FAE` (`ECO_ENGINE_SESSION_ID`),
  CONSTRAINT `FKA900B82FA5C5FAE` FOREIGN KEY (`ECO_ENGINE_SESSION_ID`) REFERENCES `teco_engine_session` (`ID`),
  CONSTRAINT `FKA900B8224F2670D` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_cart_item`
--

DROP TABLE IF EXISTS `teco_cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_cart_item` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `VIRTUAL_CATEGORY_CODE` varchar(255) DEFAULT NULL,
  `PRODUCT_MARKETING_CODE` varchar(255) DEFAULT NULL,
  `PRODUCT_SKU_CODE` varchar(255) DEFAULT NULL,
  `QUANTITY` int(11) NOT NULL DEFAULT '0',
  `CART_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKB7F201B010E2356E` (`CART_ID`),
  CONSTRAINT `FKB7F201B010E2356E` FOREIGN KEY (`CART_ID`) REFERENCES `teco_cart` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_category_type`
--

DROP TABLE IF EXISTS `teco_catalog_category_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_category_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_category_type_attribute`
--

DROP TABLE IF EXISTS `teco_catalog_category_type_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_category_type_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKA94A7130E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKA94A713053C1237C` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FKA94A713053C1237C` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_category_type` (`ID`),
  CONSTRAINT `FKA94A7130E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master`
--

DROP TABLE IF EXISTS `teco_catalog_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master_category`
--

DROP TABLE IF EXISTS `teco_catalog_master_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master_category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `RANKING` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATALOG_ID` bigint(20) DEFAULT NULL,
  `CATEGORY_TYPE_ID` bigint(20) DEFAULT NULL,
  `PARENT_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK67287653F2303BA5` (`MASTER_CATALOG_ID`),
  KEY `FK67287653D904542C` (`PARENT_CATEGORY_ID`),
  KEY `FK672876531408D7DC` (`CATEGORY_TYPE_ID`),
  CONSTRAINT `FK672876531408D7DC` FOREIGN KEY (`CATEGORY_TYPE_ID`) REFERENCES `teco_catalog_category_type` (`ID`),
  CONSTRAINT `FK67287653D904542C` FOREIGN KEY (`PARENT_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK67287653F2303BA5` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master_category_attribute`
--

DROP TABLE IF EXISTS `teco_catalog_master_category_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master_category_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DATE_VALUE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK93D39F9066B69C41` (`CATEGORY_ID`),
  KEY `FK93D39F90E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK93D39F90E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK93D39F9066B69C41` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master_category_product_sku_rel`
--

DROP TABLE IF EXISTS `teco_catalog_master_category_product_sku_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master_category_product_sku_rel` (
  `IS_DEFAULT_CATEGORY` tinyint(1) NOT NULL DEFAULT '0',
  `IS_DEFAULT_SKU` tinyint(1) NOT NULL DEFAULT '0',
  `RANKING` int(11) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) NOT NULL DEFAULT '0',
  `PRODUCT_SKU_ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MASTER_CATEGORY_ID`,`PRODUCT_SKU_ID`),
  KEY `FK90926CDBDBEE772F` (`PRODUCT_SKU_ID`),
  KEY `FK90926CDBE2E9A1E4` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK90926CDBE2E9A1E4` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK90926CDBDBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual`
--

DROP TABLE IF EXISTS `teco_catalog_virtual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATALOG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK1945EB03F2303BA5` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK1945EB03F2303BA5` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual_category`
--

DROP TABLE IF EXISTS `teco_catalog_virtual_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual_category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `RANKING` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `VIRTUAL_CATALOG_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `PARENT_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK31CA6BA74A8201B` (`VIRTUAL_CATALOG_ID`),
  KEY `FK31CA6BAE57796F` (`PARENT_CATEGORY_ID`),
  KEY `FK31CA6BAE2E9A1E4` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK31CA6BAE2E9A1E4` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK31CA6BA74A8201B` FOREIGN KEY (`VIRTUAL_CATALOG_ID`) REFERENCES `teco_catalog_virtual` (`ID`),
  CONSTRAINT `FK31CA6BAE57796F` FOREIGN KEY (`PARENT_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual_category_attribute`
--

DROP TABLE IF EXISTS `teco_catalog_virtual_category_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual_category_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK984FBB379C09C184` (`CATEGORY_ID`),
  KEY `FK984FBB37E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK984FBB37E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK984FBB379C09C184` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual_category_product_sku_rel`
--

DROP TABLE IF EXISTS `teco_catalog_virtual_category_product_sku_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual_category_product_sku_rel` (
  `IS_DEFAULT_CATEGORY` tinyint(1) NOT NULL DEFAULT '0',
  `IS_DEFAULT_SKU` tinyint(1) NOT NULL DEFAULT '0',
  `RANKING` int(11) DEFAULT NULL,
  `VIRTUAL_CATEGORY_ID` bigint(20) NOT NULL DEFAULT '0',
  `PRODUCT_SKU_ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`VIRTUAL_CATEGORY_ID`,`PRODUCT_SKU_ID`),
  KEY `FK127EEF424AAA0650` (`VIRTUAL_CATEGORY_ID`),
  KEY `FK127EEF42DBEE772F` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK127EEF42DBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK127EEF424AAA0650` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_cms_content`
--

DROP TABLE IF EXISTS `teco_cms_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_cms_content` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_currency_referential`
--

DROP TABLE IF EXISTS `teco_currency_referential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_currency_referential` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ABBREVIATED` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `FORMAT_LOCALE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SIGN` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_cust_group_rel`
--

DROP TABLE IF EXISTS `teco_cust_group_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_cust_group_rel` (
  `CUSTOMER_ID` bigint(20) NOT NULL,
  `GROUP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`,`GROUP_ID`),
  KEY `FK2557558F273B8AE8` (`GROUP_ID`),
  KEY `FK2557558FE42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK2557558FE42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FK2557558F273B8AE8` FOREIGN KEY (`GROUP_ID`) REFERENCES `teco_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer`
--

DROP TABLE IF EXISTS `teco_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ANONYMOUS` tinyint(1) NOT NULL DEFAULT '0',
  `AVATAR_IMG` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DEFAULT_BILLING_ADDRESS` bigint(20) DEFAULT NULL,
  `DEFAULT_LOCALE` varchar(255) DEFAULT NULL,
  `DEFAULT_SHIPPING_ADDRESS` bigint(20) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `NETWORK_ORIGN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PERMALINK` varchar(255) DEFAULT NULL,
  `PLATFORM_ORIGN` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `VALIDATED` tinyint(1) NOT NULL DEFAULT '0',
  `VALIDATION_TOKEN` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `LOGIN` (`LOGIN`,`CODE`,`EMAIL`),
  KEY `FK891074048FE2D7B` (`CUSTOMER_ID`),
  CONSTRAINT `FK891074048FE2D7B` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer_order_audit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_address`
--

DROP TABLE IF EXISTS `teco_customer_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_address` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(255) DEFAULT NULL,
  `ADDRESS_NAME` varchar(255) DEFAULT NULL,
  `AREA_CODE` varchar(255) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_DEFAULT_BILLING` tinyint(1) NOT NULL DEFAULT '1',
  `IS_DEFAULT_SHIPPING` tinyint(1) NOT NULL DEFAULT '1',
  `LASTNAME` varchar(255) DEFAULT NULL,
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `POSTAL_CODE` varchar(255) DEFAULT NULL,
  `STATE_CODE` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK4AA895D5E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK4AA895D5E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_attribute`
--

DROP TABLE IF EXISTS `teco_customer_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKF81AC3DE578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKF81AC3DE42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FKF81AC3DE42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FKF81AC3DE578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_connection_log`
--

DROP TABLE IF EXISTS `teco_customer_connection_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_connection_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `APP_CODE` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `HOST` varchar(255) DEFAULT NULL,
  `LOGIN_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK27122122E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK27122122E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_credential`
--

DROP TABLE IF EXISTS `teco_customer_credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_credential` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `RESET_PROCESSED_DATE` datetime DEFAULT NULL,
  `RESET_TOKEN` varchar(255) DEFAULT NULL,
  `TOKEN_TIMESTAMP` datetime DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9FCA1AB6E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK9FCA1AB6E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_market_area`
--

DROP TABLE IF EXISTS `teco_customer_market_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_market_area` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `FAX` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `MOBILE` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK5AFE4431E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK5AFE4431E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_oauth`
--

DROP TABLE IF EXISTS `teco_customer_oauth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_oauth` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EXPIRES` varchar(255) DEFAULT NULL,
  `OAUTH_TOKEN` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKC39310B8E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FKC39310B8E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_optin`
--

DROP TABLE IF EXISTS `teco_customer_optin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_optin` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `ORIGIN` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKC399DD39354CF5A6` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FKC399DD39354CF5A6` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_order_audit`
--

DROP TABLE IF EXISTS `teco_customer_order_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_order_audit` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CALENDAR_YEAR_ORDER_AMOUNT_AUDIT` varchar(255) DEFAULT NULL,
  `CALENDAR_YEAR_PRODUCT_AUDIT` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DAY_ORDER_AMOUNT_AUDIT` varchar(255) DEFAULT NULL,
  `DAY_PRODUCT_AUDIT` varchar(255) DEFAULT NULL,
  `LAST_ORDER_DATE` datetime DEFAULT NULL,
  `MONTH_ORDER_AMOUNT_AUDIT` varchar(255) DEFAULT NULL,
  `MONTH_PRODUCT_AUDIT` varchar(255) DEFAULT NULL,
  `SPECIFIC_YEAR_ORDER_AMOUNT_AUDIT` varchar(255) DEFAULT NULL,
  `SPECIFIC_YEAR_PRODUCT_AUDIT` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `WEEK_ORDER_AMOUNT_AUDIT` varchar(255) DEFAULT NULL,
  `WEEK_PRODUCT_AUDIT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_payment_information`
--

DROP TABLE IF EXISTS `teco_customer_payment_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_payment_information` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARD_CVV` varchar(255) DEFAULT NULL,
  `CARD_EXP_MONTH` varchar(255) DEFAULT NULL,
  `CARD_EXP_YEAR` varchar(255) DEFAULT NULL,
  `HOLDERNAME` varchar(255) DEFAULT NULL,
  `CARD_NUMBER` varchar(255) DEFAULT NULL,
  `CUSTOMER_MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `PAYMENT_TYPE` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK8F95F0B4E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK8F95F0B4E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_product_comment`
--

DROP TABLE IF EXISTS `teco_customer_product_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_product_comment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMENT` varchar(255) DEFAULT NULL,
  `CUSTOMER_MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `POSITION` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_SKU_CODE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK541417D0354CF5A6` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK541417D0354CF5A6` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_customer_wishlist`
--

DROP TABLE IF EXISTS `teco_customer_wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_customer_wishlist` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `POSITION` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_SKU_CODE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK1951F7E4354CF5A6` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK1951F7E4354CF5A6` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_delivery_method`
--

DROP TABLE IF EXISTS `teco_delivery_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_delivery_method` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DELIVERY_TIME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_delivery_method_country`
--

DROP TABLE IF EXISTS `teco_delivery_method_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_delivery_method_country` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTRY` varchar(255) DEFAULT NULL,
  `DELIVERY_METHOD_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK69AD2215CAFB757` (`DELIVERY_METHOD_ID`),
  CONSTRAINT `FK69AD2215CAFB757` FOREIGN KEY (`DELIVERY_METHOD_ID`) REFERENCES `teco_delivery_method` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_delivery_method_county`
--

DROP TABLE IF EXISTS `teco_delivery_method_county`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_delivery_method_county` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTY` varchar(255) DEFAULT NULL,
  `DELIVERY_METHOD_COUNTRY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKA55FD53F6956AFA4` (`DELIVERY_METHOD_COUNTRY_ID`),
  CONSTRAINT `FKA55FD53F6956AFA4` FOREIGN KEY (`DELIVERY_METHOD_COUNTRY_ID`) REFERENCES `teco_delivery_method_country` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_delivery_method_price`
--

DROP TABLE IF EXISTS `teco_delivery_method_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_delivery_method_price` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_END` datetime DEFAULT NULL,
  `DATE_START` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DELIVERY_METHOD_ID` bigint(20) DEFAULT NULL,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKAB37569424F2670D` (`CURRENCY_ID`),
  KEY `FKAB3756945CAFB757` (`DELIVERY_METHOD_ID`),
  CONSTRAINT `FKAB3756945CAFB757` FOREIGN KEY (`DELIVERY_METHOD_ID`) REFERENCES `teco_delivery_method` (`ID`),
  CONSTRAINT `FKAB37569424F2670D` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_engine_session`
--

DROP TABLE IF EXISTS `teco_engine_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_engine_session` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `ENGINE_SESSION_GUID` varchar(255) DEFAULT NULL,
  `JSESSION_ID` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `JSESSION_ID` (`JSESSION_ID`,`ENGINE_SESSION_GUID`),
  KEY `FK3ECC475BE42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK3ECC475BE42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_engine_setting`
--

DROP TABLE IF EXISTS `teco_engine_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_engine_setting` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DEFAULT_VALUE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_engine_setting_value`
--

DROP TABLE IF EXISTS `teco_engine_setting_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_engine_setting_value` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTEXT` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `ENGINE_SETTING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKF40A6BA7E81C1001` (`ENGINE_SETTING_ID`),
  CONSTRAINT `FKF40A6BA7E81C1001` FOREIGN KEY (`ENGINE_SETTING_ID`) REFERENCES `teco_engine_setting` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_group`
--

DROP TABLE IF EXISTS `teco_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_group` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_group_role_rel`
--

DROP TABLE IF EXISTS `teco_group_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_group_role_rel` (
  `GROUP_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`GROUP_ID`,`ROLE_ID`),
  KEY `FKDD89ACF2B57A0EAC` (`ROLE_ID`),
  KEY `FKDD89ACF2273B8AE8` (`GROUP_ID`),
  CONSTRAINT `FKDD89ACF2273B8AE8` FOREIGN KEY (`GROUP_ID`) REFERENCES `teco_group` (`ID`),
  CONSTRAINT `FKDD89ACF2B57A0EAC` FOREIGN KEY (`ROLE_ID`) REFERENCES `teco_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_localization`
--

DROP TABLE IF EXISTS `teco_localization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_localization` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `COUNTRY` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `LANGUAGE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market`
--

DROP TABLE IF EXISTS `teco_market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MARKETPLACE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKB7CB901E2B6FEF86` (`MARKETPLACE_ID`),
  CONSTRAINT `FKB7CB901E2B6FEF86` FOREIGN KEY (`MARKETPLACE_ID`) REFERENCES `teco_marketplace` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area`
--

DROP TABLE IF EXISTS `teco_market_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `GEOLOC_COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ECOMMERCE` tinyint(1) NOT NULL DEFAULT '0',
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `VIRTUAL_CATALOG_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_CURRENCY_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_RETAILER_ID` bigint(20) DEFAULT NULL,
  `MARKET_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK6179540E2DE532EE` (`MARKET_ID`),
  KEY `FK6179540E74A8201B` (`VIRTUAL_CATALOG_ID`),
  KEY `FK6179540EEE26420F` (`DEFAULT_CURRENCY_ID`),
  KEY `FK6179540EBB52ECD0` (`DEFAULT_LOCALIZATION_ID`),
  KEY `FK6179540E8A1A8470` (`DEFAULT_RETAILER_ID`),
  CONSTRAINT `FK6179540E8A1A8470` FOREIGN KEY (`DEFAULT_RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK6179540E2DE532EE` FOREIGN KEY (`MARKET_ID`) REFERENCES `teco_market` (`ID`),
  CONSTRAINT `FK6179540E74A8201B` FOREIGN KEY (`VIRTUAL_CATALOG_ID`) REFERENCES `teco_catalog_virtual` (`ID`),
  CONSTRAINT `FK6179540EBB52ECD0` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK6179540EEE26420F` FOREIGN KEY (`DEFAULT_CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_attribute`
--

DROP TABLE IF EXISTS `teco_market_area_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `CONTEXT` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1B82CF8BD93FD4A7` (`MARKET_AREA_ID`),
  KEY `FK1B82CF8BE578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK1B82CF8BE578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK1B82CF8BD93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_currency_rel`
--

DROP TABLE IF EXISTS `teco_market_area_currency_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_currency_rel` (
  `MARKET_AREA_ID` bigint(20) NOT NULL,
  `CURRENCY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKET_AREA_ID`,`CURRENCY_ID`),
  KEY `FKF42D77DC24F2670D` (`CURRENCY_ID`),
  KEY `FKF42D77DCD93FD4A7` (`MARKET_AREA_ID`),
  CONSTRAINT `FKF42D77DCD93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FKF42D77DC24F2670D` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_localization_rel`
--

DROP TABLE IF EXISTS `teco_market_area_localization_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_localization_rel` (
  `MARKET_AREA_ID` bigint(20) NOT NULL,
  `LOCALIZATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKET_AREA_ID`,`LOCALIZATION_ID`),
  KEY `FK872711E4D93FD4A7` (`MARKET_AREA_ID`),
  KEY `FK872711E4B56862CE` (`LOCALIZATION_ID`),
  CONSTRAINT `FK872711E4B56862CE` FOREIGN KEY (`LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK872711E4D93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_payment_gateway_rel`
--

DROP TABLE IF EXISTS `teco_market_area_payment_gateway_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_payment_gateway_rel` (
  `MARKET_AREA_ID` bigint(20) NOT NULL,
  `PAYMENT_GATEWAY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PAYMENT_GATEWAY_ID`,`MARKET_AREA_ID`),
  KEY `FK61404D54D93FD4A7` (`MARKET_AREA_ID`),
  KEY `FK61404D54D3E07DA3` (`PAYMENT_GATEWAY_ID`),
  CONSTRAINT `FK61404D54D3E07DA3` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_payment_gateway` (`ID`),
  CONSTRAINT `FK61404D54D93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_retailer_rel`
--

DROP TABLE IF EXISTS `teco_market_area_retailer_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_retailer_rel` (
  `MARKET_AREA_ID` bigint(20) NOT NULL,
  `RETAILER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKET_AREA_ID`,`RETAILER_ID`),
  KEY `FK6211C93BD93FD4A7` (`MARKET_AREA_ID`),
  KEY `FK6211C93BC0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK6211C93BC0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK6211C93BD93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_tax_rel`
--

DROP TABLE IF EXISTS `teco_market_area_tax_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_tax_rel` (
  `TAX_ID` bigint(20) NOT NULL,
  `MARKET_AREA_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKET_AREA_ID`,`TAX_ID`),
  KEY `FKC4ADFE94D93FD4A7` (`MARKET_AREA_ID`),
  KEY `FKC4ADFE94575EEFA6` (`TAX_ID`),
  CONSTRAINT `FKC4ADFE94575EEFA6` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_tax` (`ID`),
  CONSTRAINT `FKC4ADFE94D93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_warehouse_rel`
--

DROP TABLE IF EXISTS `teco_market_area_warehouse_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_warehouse_rel` (
  `RANKING` int(11) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) NOT NULL DEFAULT '0',
  `WAREHOUSE_ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MARKET_AREA_ID`,`WAREHOUSE_ID`),
  KEY `FK35BA26CD93FD4A7` (`MARKET_AREA_ID`),
  KEY `FK35BA26CCC4E6FA6` (`WAREHOUSE_ID`),
  CONSTRAINT `FK35BA26CCC4E6FA6` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `teco_warehouse` (`ID`),
  CONSTRAINT `FK35BA26CD93FD4A7` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_attribute`
--

DROP TABLE IF EXISTS `teco_market_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `CONTEXT` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` varchar(255) DEFAULT NULL,
  `MARKET_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1B85379B2DE532EE` (`MARKET_ID`),
  KEY `FK1B85379BE578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK1B85379BE578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK1B85379B2DE532EE` FOREIGN KEY (`MARKET_ID`) REFERENCES `teco_market` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_marketplace`
--

DROP TABLE IF EXISTS `teco_marketplace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_marketplace` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATALOG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK60AAB389F2303BA5` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK60AAB389F2303BA5` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_marketplace_attribute`
--

DROP TABLE IF EXISTS `teco_marketplace_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_marketplace_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `CONTEXT` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_PLACE_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKF9AB514621A2F7ED` (`MARKET_PLACE_ID`),
  KEY `FKF9AB5146E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKF9AB5146E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FKF9AB514621A2F7ED` FOREIGN KEY (`MARKET_PLACE_ID`) REFERENCES `teco_marketplace` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_notification`
--

DROP TABLE IF EXISTS `teco_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_notification` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `CHECKED` tinyint(1) NOT NULL DEFAULT '0',
  `LABEL` varchar(255) DEFAULT NULL,
  `MESSAGE` varchar(255) DEFAULT NULL,
  `TAG_ID` bigint(20) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_address`
--

DROP TABLE IF EXISTS `teco_order_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_address` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(255) DEFAULT NULL,
  `AREA_CODE` varchar(255) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `POSTAL_CODE` varchar(255) DEFAULT NULL,
  `STATE_CODE` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_customer`
--

DROP TABLE IF EXISTS `teco_order_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_customer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDER_NUM` varchar(255) DEFAULT NULL,
  `PREFIX_HASH_FOLDER` varchar(255) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `BILLING_ORDER_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `SHIPPING_ORDER_ADDRESS_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ORDER_NUM` (`ORDER_NUM`,`PREFIX_HASH_FOLDER`),
  KEY `FK660F4BB124F2670D` (`CURRENCY_ID`),
  KEY `FK660F4BB1370125C2` (`SHIPPING_ORDER_ADDRESS_ID`),
  KEY `FK660F4BB1A0CA7495` (`BILLING_ORDER_ADDRESS_ID`),
  CONSTRAINT `FK660F4BB1A0CA7495` FOREIGN KEY (`BILLING_ORDER_ADDRESS_ID`) REFERENCES `teco_order_address` (`ID`),
  CONSTRAINT `FK660F4BB124F2670D` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`),
  CONSTRAINT `FK660F4BB1370125C2` FOREIGN KEY (`SHIPPING_ORDER_ADDRESS_ID`) REFERENCES `teco_order_address` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_item`
--

DROP TABLE IF EXISTS `teco_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_item` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `PRODUCT_SKU_CODE` varchar(255) DEFAULT NULL,
  `QUANTITY` int(11) NOT NULL DEFAULT '0',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `ORDER_SHIPMENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKB57F306624F2670D` (`CURRENCY_ID`),
  KEY `FKB57F306627D14443` (`ORDER_SHIPMENT_ID`),
  KEY `FKB57F3066DBEE772F` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FKB57F3066DBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FKB57F306624F2670D` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`),
  CONSTRAINT `FKB57F306627D14443` FOREIGN KEY (`ORDER_SHIPMENT_ID`) REFERENCES `teco_order_shipment` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_number`
--

DROP TABLE IF EXISTS `teco_order_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_number` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LAST_ORDER_NUMBER` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_payment`
--

DROP TABLE IF EXISTS `teco_order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_payment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(19,2) DEFAULT NULL,
  `AUTHORIZATION_CODE` varchar(255) DEFAULT NULL,
  `CARDHOLDER_NAME` varchar(255) DEFAULT NULL,
  `CARD_TYPE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `CVV2_CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EXPIRATION_MONTH` varchar(255) DEFAULT NULL,
  `EXPIRATION_YEAR` varchar(255) DEFAULT NULL,
  `IP_ADDRESS` varchar(255) DEFAULT NULL,
  `PAYMENT_TYPE` varchar(255) DEFAULT NULL,
  `REQUEST_TOKEN` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `TRANSACTION_TYPE` varchar(255) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4F1B9BB364678FC4` (`ORDER_ID`),
  CONSTRAINT `FK4F1B9BB364678FC4` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_shipment`
--

DROP TABLE IF EXISTS `teco_order_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_shipment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DELIVERY_METHOD_ID` bigint(20) DEFAULT NULL,
  `EXPECTED_DELIVERY_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK23273D6D64678FC4` (`ORDER_ID`),
  CONSTRAINT `FK23273D6D64678FC4` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_order_tax`
--

DROP TABLE IF EXISTS `teco_order_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order_tax` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AMOUNT` decimal(19,2) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PERCENT` decimal(19,2) DEFAULT NULL,
  `tax_ID` bigint(20) DEFAULT NULL,
  `ORDER_TAX_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK923E0FD83360088D` (`ORDER_TAX_ID`),
  CONSTRAINT `FK923E0FD83360088D` FOREIGN KEY (`ORDER_TAX_ID`) REFERENCES `teco_order_item` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_payment_gateway`
--

DROP TABLE IF EXISTS `teco_payment_gateway`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_payment_gateway` (
  `PAYMENT_GATEWAY_TYPE` varchar(31) NOT NULL,
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_payment_gateway_attribute`
--

DROP TABLE IF EXISTS `teco_payment_gateway_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_payment_gateway_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PAYMENT_GATEWAY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKCB4A3CE6E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKCB4A3CE6D3E07DA3` (`PAYMENT_GATEWAY_ID`),
  CONSTRAINT `FKCB4A3CE6D3E07DA3` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_payment_gateway` (`ID`),
  CONSTRAINT `FKCB4A3CE6E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_payment_gateway_option`
--

DROP TABLE IF EXISTS `teco_payment_gateway_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_payment_gateway_option` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `OPTION_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_payment_gateway_option_rel`
--

DROP TABLE IF EXISTS `teco_payment_gateway_option_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_payment_gateway_option_rel` (
  `PAYMENT_GATEWAY_ID` bigint(20) NOT NULL,
  `PAYMENT_GATEWAY_OPTION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PAYMENT_GATEWAY_ID`,`PAYMENT_GATEWAY_OPTION_ID`),
  KEY `FKD7C270851ED55A18` (`PAYMENT_GATEWAY_OPTION_ID`),
  KEY `FKD7C27085D3E07DA3` (`PAYMENT_GATEWAY_ID`),
  CONSTRAINT `FKD7C27085D3E07DA3` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_payment_gateway` (`ID`),
  CONSTRAINT `FKD7C270851ED55A18` FOREIGN KEY (`PAYMENT_GATEWAY_OPTION_ID`) REFERENCES `teco_payment_gateway_option` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_permission`
--

DROP TABLE IF EXISTS `teco_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_permission` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_association_link`
--

DROP TABLE IF EXISTS `teco_product_association_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_association_link` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_END` datetime DEFAULT NULL,
  `DATE_START` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `RANK_POSITION` int(11) NOT NULL DEFAULT '1',
  `TYPE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK825179EA820B6ACF` (`PRODUCT_MARKETING_ID`),
  KEY `FK825179EADBEE772F` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK825179EADBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK825179EA820B6ACF` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_brand`
--

DROP TABLE IF EXISTS `teco_product_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_brand` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing`
--

DROP TABLE IF EXISTS `teco_product_marketing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `BRAND_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_TYPE_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_BRAND_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKE7564D7444895AF` (`PRODUCT_BRAND_ID`),
  KEY `FKE7564D74671B145F` (`BRAND_ID`),
  KEY `FKE7564D74A4CC5D5C` (`PRODUCT_MARKETING_TYPE_ID`),
  CONSTRAINT `FKE7564D74A4CC5D5C` FOREIGN KEY (`PRODUCT_MARKETING_TYPE_ID`) REFERENCES `teco_product_marketing_type` (`ID`),
  CONSTRAINT `FKE7564D7444895AF` FOREIGN KEY (`PRODUCT_BRAND_ID`) REFERENCES `teco_product_brand` (`ID`),
  CONSTRAINT `FKE7564D74671B145F` FOREIGN KEY (`BRAND_ID`) REFERENCES `teco_product_brand` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_attribute`
--

DROP TABLE IF EXISTS `teco_product_marketing_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKC35BC171820B6ACF` (`PRODUCT_MARKETING_ID`),
  KEY `FKC35BC171E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKC35BC171E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FKC35BC171820B6ACF` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_customer_comment`
--

DROP TABLE IF EXISTS `teco_product_marketing_customer_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_customer_comment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMENT` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_CUSTOMER_COMMENT_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9CD2FE494CEA4489` (`PRODUCT_MARKETING_CUSTOMER_COMMENT_ID`),
  KEY `FK9CD2FE49E42F1AAE` (`CUSTOMER_ID`),
  CONSTRAINT `FK9CD2FE49E42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FK9CD2FE494CEA4489` FOREIGN KEY (`PRODUCT_MARKETING_CUSTOMER_COMMENT_ID`) REFERENCES `teco_product_marketing_customer_comment` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_customer_rate`
--

DROP TABLE IF EXISTS `teco_product_marketing_customer_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_customer_rate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `PROCESSED` tinyint(1) NOT NULL DEFAULT '0',
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  `RATE` int(11) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_familly`
--

DROP TABLE IF EXISTS `teco_product_marketing_familly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_familly` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_familly_product_sku_option_rel`
--

DROP TABLE IF EXISTS `teco_product_marketing_familly_product_sku_option_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_familly_product_sku_option_rel` (
  `PRODUCT_MARKETING_FAMILLY_ID` bigint(20) NOT NULL,
  `PRODUCT_SKU_OPTION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PRODUCT_MARKETING_FAMILLY_ID`,`PRODUCT_SKU_OPTION_ID`),
  KEY `FK51D700C9245D0958` (`PRODUCT_MARKETING_FAMILLY_ID`),
  KEY `FK51D700C9BB5D62AA` (`PRODUCT_SKU_OPTION_ID`),
  CONSTRAINT `FK51D700C9BB5D62AA` FOREIGN KEY (`PRODUCT_SKU_OPTION_ID`) REFERENCES `teco_product_sku_option` (`ID`),
  CONSTRAINT `FK51D700C9245D0958` FOREIGN KEY (`PRODUCT_MARKETING_FAMILLY_ID`) REFERENCES `teco_product_marketing_familly` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_familly_tax_rel`
--

DROP TABLE IF EXISTS `teco_product_marketing_familly_tax_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_familly_tax_rel` (
  `PRODUCT_MARKETING_TYPE_FAMILLY_ID` bigint(20) NOT NULL,
  `TAX_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PRODUCT_MARKETING_TYPE_FAMILLY_ID`,`TAX_ID`),
  KEY `FK511E6D9D17D3630B` (`PRODUCT_MARKETING_TYPE_FAMILLY_ID`),
  KEY `FK511E6D9DCD7C3933` (`TAX_ID`),
  CONSTRAINT `FK511E6D9DCD7C3933` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_currency_referential` (`ID`),
  CONSTRAINT `FK511E6D9D17D3630B` FOREIGN KEY (`PRODUCT_MARKETING_TYPE_FAMILLY_ID`) REFERENCES `teco_product_marketing_familly` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_tag`
--

DROP TABLE IF EXISTS `teco_product_marketing_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_tag` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_type`
--

DROP TABLE IF EXISTS `teco_product_marketing_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_MARKETING_FAMILLY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK6EE63B25245D0958` (`PRODUCT_MARKETING_FAMILLY_ID`),
  CONSTRAINT `FK6EE63B25245D0958` FOREIGN KEY (`PRODUCT_MARKETING_FAMILLY_ID`) REFERENCES `teco_product_marketing_familly` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_type_attribute`
--

DROP TABLE IF EXISTS `teco_product_marketing_type_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_type_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKFC705E2E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKFC705E2E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku`
--

DROP TABLE IF EXISTS `teco_product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKF5E65EEB820B6ACF` (`PRODUCT_MARKETING_ID`),
  CONSTRAINT `FKF5E65EEB820B6ACF` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_attribute`
--

DROP TABLE IF EXISTS `teco_product_sku_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4419AA28DBEE772F` (`PRODUCT_SKU_ID`),
  KEY `FK4419AA28E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK4419AA28E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK4419AA28DBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_option`
--

DROP TABLE IF EXISTS `teco_product_sku_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_option` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_option_definition`
--

DROP TABLE IF EXISTS `teco_product_sku_option_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_option_definition` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_SKU_OPTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK2FF49309BB5D62AA` (`PRODUCT_SKU_OPTION_ID`),
  CONSTRAINT `FK2FF49309BB5D62AA` FOREIGN KEY (`PRODUCT_SKU_OPTION_ID`) REFERENCES `teco_product_sku_option` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_option_definition_attribute`
--

DROP TABLE IF EXISTS `teco_product_sku_option_definition_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_option_definition_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_OPTION_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKABC3D0C6CF21EE99` (`PRODUCT_SKU_OPTION_DEFINITION_ID`),
  KEY `FKABC3D0C6E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKABC3D0C6E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FKABC3D0C6CF21EE99` FOREIGN KEY (`PRODUCT_SKU_OPTION_DEFINITION_ID`) REFERENCES `teco_product_sku_option_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_price`
--

DROP TABLE IF EXISTS `teco_product_sku_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_price` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PRICE_CATALOG` decimal(19,2) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_END` datetime DEFAULT NULL,
  `DATE_START` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK312A5ED524F2670D` (`CURRENCY_ID`),
  KEY `FK312A5ED5DBEE772F` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK312A5ED5DBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK312A5ED524F2670D` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_retailer_rel`
--

DROP TABLE IF EXISTS `teco_product_sku_retailer_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_retailer_rel` (
  `PRODUCT_SKU_ID` bigint(20) NOT NULL,
  `RETAILER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PRODUCT_SKU_ID`,`RETAILER_ID`),
  KEY `FKCD1C0D7EDBEE772F` (`PRODUCT_SKU_ID`),
  KEY `FKCD1C0D7EC0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FKCD1C0D7EC0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FKCD1C0D7EDBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_stock`
--

DROP TABLE IF EXISTS `teco_product_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_stock` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `GLOBAL_STOCK_QUANTITY` int(11) NOT NULL DEFAULT '0',
  `REORDER_NEXT_DATE` datetime DEFAULT NULL,
  `REORDER_QTY_ALERT` int(11) NOT NULL DEFAULT '0',
  `REORDER_QTY_TRIGGER` int(11) NOT NULL DEFAULT '0',
  `RESERVED_STOCK_ECO` int(11) NOT NULL DEFAULT '0',
  `RESERVED_STOCK_WHAREHOUSE` int(11) NOT NULL DEFAULT '0',
  `STOCK_USED_BY_ACTIVE_CART` int(11) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK15CE5964CC4E6FA6` (`WAREHOUSE_ID`),
  KEY `FK15CE5964DBEE772F` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK15CE5964DBEE772F` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK15CE5964CC4E6FA6` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `teco_warehouse` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer`
--

DROP TABLE IF EXISTS `teco_retailer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `IS_BRAND` tinyint(1) NOT NULL DEFAULT '0',
  `IS_CORNER` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ECOMMERCE` tinyint(1) NOT NULL DEFAULT '0',
  `IS_OFFICIAL_RETAILER` tinyint(1) NOT NULL DEFAULT '0',
  `LOGO` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE_SCORE` tinyint(1) NOT NULL DEFAULT '0',
  `QUALITY_OF_SERVICE` tinyint(1) NOT NULL DEFAULT '0',
  `RATIO_QUALITY_PRICE` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKD1055052CC4E6FA6` (`WAREHOUSE_ID`),
  CONSTRAINT `FKD1055052CC4E6FA6` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `teco_warehouse` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_address`
--

DROP TABLE IF EXISTS `teco_retailer_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_address` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(255) DEFAULT NULL,
  `AREA_CODE` varchar(255) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FAX` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `MOBILE` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `POSTAL_CODE` varchar(255) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `STATE_CODE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `WEBSITE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4F93ACE7C0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK4F93ACE7C0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_attribute`
--

DROP TABLE IF EXISTS `teco_retailer_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK860346CFE578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK860346CFC0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK860346CFC0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK860346CFE578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_customer_comment`
--

DROP TABLE IF EXISTS `teco_retailer_customer_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_customer_comment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMENT` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `RETAILER_CUSTOMER_COMMENT_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK14A532B4121958A` (`RETAILER_CUSTOMER_COMMENT_ID`),
  KEY `FK14A532BE42F1AAE` (`CUSTOMER_ID`),
  KEY `FK14A532BC0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK14A532BC0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK14A532B4121958A` FOREIGN KEY (`RETAILER_CUSTOMER_COMMENT_ID`) REFERENCES `teco_retailer_customer_comment` (`ID`),
  CONSTRAINT `FK14A532BE42F1AAE` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_customer_rate`
--

DROP TABLE IF EXISTS `teco_retailer_customer_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_customer_rate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `PROCESSED` tinyint(1) NOT NULL DEFAULT '0',
  `RATE` int(11) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK8AC9F374C0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK8AC9F374C0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_link`
--

DROP TABLE IF EXISTS `teco_retailer_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_link` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LINK` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1F1873C7C0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK1F1873C7C0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_retailer_tag_rel`
--

DROP TABLE IF EXISTS `teco_retailer_retailer_tag_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_retailer_tag_rel` (
  `RETAILER_ID` bigint(20) NOT NULL,
  `RETAILER_TAG_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`RETAILER_ID`,`RETAILER_TAG_ID`),
  KEY `FK714F523233C865` (`RETAILER_TAG_ID`),
  KEY `FK714F5232C0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK714F5232C0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK714F523233C865` FOREIGN KEY (`RETAILER_TAG_ID`) REFERENCES `teco_retailer_tag` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_retailer_tag`
--

DROP TABLE IF EXISTS `teco_retailer_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_retailer_tag` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_role`
--

DROP TABLE IF EXISTS `teco_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_role_permission_rel`
--

DROP TABLE IF EXISTS `teco_role_permission_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_role_permission_rel` (
  `ROLE_ID` bigint(20) NOT NULL,
  `PERMISSION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`PERMISSION_ID`),
  KEY `FK86473050B57A0EAC` (`ROLE_ID`),
  KEY `FK8647305012FB968C` (`PERMISSION_ID`),
  CONSTRAINT `FK8647305012FB968C` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `teco_permission` (`ID`),
  CONSTRAINT `FK86473050B57A0EAC` FOREIGN KEY (`ROLE_ID`) REFERENCES `teco_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_rule_referential`
--

DROP TABLE IF EXISTS `teco_rule_referential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_rule_referential` (
  `RULE_TYPE` varchar(31) NOT NULL,
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `SALIENCE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_rule_referential_attribute`
--

DROP TABLE IF EXISTS `teco_rule_referential_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_rule_referential_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKF0E49585E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKF0E49585E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_rule_repository`
--

DROP TABLE IF EXISTS `teco_rule_repository`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_rule_repository` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `END_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_rule_repository_attribute`
--

DROP TABLE IF EXISTS `teco_rule_repository_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_rule_repository_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKAF754208E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKAF754208E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_rule_repository_attribute_rel`
--

DROP TABLE IF EXISTS `teco_rule_repository_attribute_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_rule_repository_attribute_rel` (
  `RULE_REPOSITORY_ID` bigint(20) NOT NULL,
  `RULE_REPOSITORY_ATTRIBUTE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`RULE_REPOSITORY_ID`,`RULE_REPOSITORY_ATTRIBUTE_ID`),
  KEY `FK8FA169C29E84E6E4` (`RULE_REPOSITORY_ID`),
  KEY `FK8FA169C2501BA447` (`RULE_REPOSITORY_ID`),
  KEY `FK8FA169C289EF4DD2` (`RULE_REPOSITORY_ATTRIBUTE_ID`),
  CONSTRAINT `FK8FA169C289EF4DD2` FOREIGN KEY (`RULE_REPOSITORY_ATTRIBUTE_ID`) REFERENCES `teco_rule_repository_attribute` (`ID`),
  CONSTRAINT `FK8FA169C2501BA447` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_repository` (`ID`),
  CONSTRAINT `FK8FA169C29E84E6E4` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_rule_repository_referential_rel`
--

DROP TABLE IF EXISTS `teco_rule_repository_referential_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_rule_repository_referential_rel` (
  `RULE_REPOSITORY_ID` bigint(20) NOT NULL,
  `RULE_REFERENTIAL_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`RULE_REPOSITORY_ID`,`RULE_REFERENTIAL_ID`),
  KEY `FK2477FFAF501BA447` (`RULE_REPOSITORY_ID`),
  KEY `FK2477FFAF6BFDE76B` (`RULE_REFERENTIAL_ID`),
  CONSTRAINT `FK2477FFAF6BFDE76B` FOREIGN KEY (`RULE_REFERENTIAL_ID`) REFERENCES `teco_rule_referential` (`ID`),
  CONSTRAINT `FK2477FFAF501BA447` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_repository` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_store`
--

DROP TABLE IF EXISTS `teco_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_store` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(255) DEFAULT NULL,
  `AREA_CODE` varchar(255) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `POSTAL_CODE` varchar(255) DEFAULT NULL,
  `STATE_CODE` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK485B749FC0E6A96E` (`RETAILER_ID`),
  CONSTRAINT `FK485B749FC0E6A96E` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_store_attribute`
--

DROP TABLE IF EXISTS `teco_store_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_store_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `STORE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3BC62EDCE578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK3BC62EDC723F98E6` (`STORE_ID`),
  CONSTRAINT `FK3BC62EDC723F98E6` FOREIGN KEY (`STORE_ID`) REFERENCES `teco_store` (`ID`),
  CONSTRAINT `FK3BC62EDCE578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_store_business_hour`
--

DROP TABLE IF EXISTS `teco_store_business_hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_store_business_hour` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLOSING_DATE_END` varchar(255) DEFAULT NULL,
  `CLOSING_DATE_START` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `END_HOUR` varchar(255) DEFAULT NULL,
  `FRIDAY` tinyint(1) DEFAULT NULL,
  `MONDAY` tinyint(1) DEFAULT NULL,
  `SATURDAY` tinyint(1) DEFAULT NULL,
  `START_HOUR` varchar(255) DEFAULT NULL,
  `STORE_ID` bigint(20) DEFAULT NULL,
  `SUNDAY` tinyint(1) DEFAULT NULL,
  `THURSDAY` tinyint(1) DEFAULT NULL,
  `TUESDAY` tinyint(1) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `WEDNESDAY` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKFADF1363723F98E6` (`STORE_ID`),
  CONSTRAINT `FKFADF1363723F98E6` FOREIGN KEY (`STORE_ID`) REFERENCES `teco_store` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax`
--

DROP TABLE IF EXISTS `teco_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `PERCENT` decimal(19,2) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `TAX_TYPE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKCECB16E99FF9BCFD` (`TAX_TYPE_ID`),
  CONSTRAINT `FKCECB16E99FF9BCFD` FOREIGN KEY (`TAX_TYPE_ID`) REFERENCES `teco_tax_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax_attribute`
--

DROP TABLE IF EXISTS `teco_tax_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax_attribute` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOB_VALUE` longblob,
  `BOOLEAN_VALUE` tinyint(1) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `LONG_STRING_VALUE` longtext,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `SHORT_STRING_VALUE` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `TAX_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK86AEFCA6E578C5FF` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK86AEFCA6575EEFA6` (`TAX_ID`),
  CONSTRAINT `FK86AEFCA6575EEFA6` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_tax` (`ID`),
  CONSTRAINT `FK86AEFCA6E578C5FF` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax_country`
--

DROP TABLE IF EXISTS `teco_tax_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax_country` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTRY` varchar(255) DEFAULT NULL,
  `TAX_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9810A820575EEFA6` (`TAX_ID`),
  CONSTRAINT `FK9810A820575EEFA6` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_tax` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax_state`
--

DROP TABLE IF EXISTS `teco_tax_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax_state` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTY` varchar(255) DEFAULT NULL,
  `TAX_COUNTRY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKE003B39BF7926197` (`TAX_COUNTRY_ID`),
  CONSTRAINT `FKE003B39BF7926197` FOREIGN KEY (`TAX_COUNTRY_ID`) REFERENCES `teco_tax_country` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax_type`
--

DROP TABLE IF EXISTS `teco_tax_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_warehouse`
--

DROP TABLE IF EXISTS `teco_warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_warehouse` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS1` varchar(255) DEFAULT NULL,
  `ADDRESS2` varchar(255) DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(255) DEFAULT NULL,
  `AREA_CODE` varchar(255) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` longtext,
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `POSTAL_CODE` varchar(255) DEFAULT NULL,
  `STATE_CODE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_warehouse_delivery_method_rel`
--

DROP TABLE IF EXISTS `teco_warehouse_delivery_method_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_warehouse_delivery_method_rel` (
  `DELIVERY_METHOD_ID` bigint(20) NOT NULL,
  `WAREHOUSE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`WAREHOUSE_ID`,`DELIVERY_METHOD_ID`),
  KEY `FK20A34388CC4E6FA6` (`WAREHOUSE_ID`),
  KEY `FK20A343885CAFB757` (`DELIVERY_METHOD_ID`),
  CONSTRAINT `FK20A343885CAFB757` FOREIGN KEY (`DELIVERY_METHOD_ID`) REFERENCES `teco_delivery_method` (`ID`),
  CONSTRAINT `FK20A34388CC4E6FA6` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `teco_warehouse` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-04 11:39:44
