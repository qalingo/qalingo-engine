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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `DEFAULT_LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK871A81BF61742B8` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK871A81BF61742B8` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`)
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
  KEY `FKC542DC5346B97E` (`COMPANY_ID`),
  KEY `FKC542DC532CB8B6` (`LOCALIZATION_ID`),
  CONSTRAINT `FKC542DC532CB8B6` FOREIGN KEY (`LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FKC542DC5346B97E` FOREIGN KEY (`COMPANY_ID`) REFERENCES `tbo_company` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FK9583FB6E8CC74001` (`ROLE_ID`),
  KEY `FK9583FB6E39948433` (`GROUP_ID`),
  CONSTRAINT `FK9583FB6E39948433` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`),
  CONSTRAINT `FK9583FB6E8CC74001` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FKA8268F548CC74001` (`ROLE_ID`),
  KEY `FKA8268F541E51B421` (`PERMISSION_ID`),
  CONSTRAINT `FKA8268F541E51B421` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `tbo_permission` (`ID`),
  CONSTRAINT `FKA8268F548CC74001` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`)
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
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `COMPANY_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK456F75A946B97E` (`COMPANY_ID`),
  KEY `FK456F75A961742B8` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK456F75A961742B8` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK456F75A946B97E` FOREIGN KEY (`COMPANY_ID`) REFERENCES `tbo_company` (`ID`)
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
  `ADDRESS` varchar(255) DEFAULT NULL,
  `APP` varchar(255) DEFAULT NULL,
  `HOST` varchar(255) DEFAULT NULL,
  `LOGIN_DATE` datetime DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK49DF399D9E6AF76` (`USER_ID`),
  CONSTRAINT `FK49DF399D9E6AF76` FOREIGN KEY (`USER_ID`) REFERENCES `tbo_user` (`ID`)
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
  KEY `FK4C635383D9E6AF76` (`USER_ID`),
  KEY `FK4C63538339948433` (`GROUP_ID`),
  CONSTRAINT `FK4C63538339948433` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`),
  CONSTRAINT `FK4C635383D9E6AF76` FOREIGN KEY (`USER_ID`) REFERENCES `tbo_user` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
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
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  `VIRTUAL_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK475D66AE85628CB7` (`PRODUCT_MARKETING_ID`),
  KEY `FK475D66AECF0A1A38` (`VIRTUAL_CATEGORY_ID`),
  KEY `FK475D66AE701F2717` (`PRODUCT_SKU_ID`),
  KEY `FK475D66AE39C370FC` (`MASTER_CATEGORY_ID`),
  KEY `FK475D66AEE1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK475D66AEE1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK475D66AE39C370FC` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK475D66AE701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK475D66AE85628CB7` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`),
  CONSTRAINT `FK475D66AECF0A1A38` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `GLOBAL` tinyint(1) NOT NULL DEFAULT '1',
  `LOCALIZABLE` tinyint(1) NOT NULL DEFAULT '0',
  `MULTI_VALUE` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `OBJECT_TYPE` int(11) DEFAULT NULL,
  `PLANNED` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
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
  `SHIPPING_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `SESSION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKA900B8218EB4A7` (`SESSION_ID`),
  CONSTRAINT `FKA900B8218EB4A7` FOREIGN KEY (`SESSION_ID`) REFERENCES `teco_engine_session` (`ID`)
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
  `PRODUCT_SKU_CODE` varchar(255) DEFAULT NULL,
  `QUANTITY` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `CART_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKB7F201B03AEF356` (`CART_ID`),
  KEY `FKB7F201B0701F2717` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FKB7F201B0701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FKB7F201B03AEF356` FOREIGN KEY (`CART_ID`) REFERENCES `teco_cart` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKA94A71309E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKA94A7130C73D894` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FKA94A7130C73D894` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_category_type` (`ID`),
  CONSTRAINT `FKA94A71309E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CATALOG_CATEGORY_TYPE_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_PARENT_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK67287653A8704202` (`DEFAULT_PARENT_CATEGORY_ID`),
  KEY `FK67287653390A45A` (`CATALOG_CATEGORY_TYPE_ID`),
  CONSTRAINT `FK67287653390A45A` FOREIGN KEY (`CATALOG_CATEGORY_TYPE_ID`) REFERENCES `teco_catalog_category_type` (`ID`),
  CONSTRAINT `FK67287653A8704202` FOREIGN KEY (`DEFAULT_PARENT_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
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
  `DOUBLE_VALUE` double DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FLOAT_VALUE` float DEFAULT NULL,
  `INTEGER_VALUE` int(11) DEFAULT NULL,
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK93D39F909E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK93D39F9039C370FC` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK93D39F9039C370FC` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK93D39F909E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master_category_child_category_rel`
--

DROP TABLE IF EXISTS `teco_catalog_master_category_child_category_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master_category_child_category_rel` (
  `PARENT_MASTER_CATALOG_CATEGORY_ID` bigint(20) NOT NULL,
  `CHILD_MASTER_CATALOG_CATEGORY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PARENT_MASTER_CATALOG_CATEGORY_ID`,`CHILD_MASTER_CATALOG_CATEGORY_ID`),
  KEY `FK32E618727DC938B` (`PARENT_MASTER_CATALOG_CATEGORY_ID`),
  KEY `FK32E6187FBC56FD9` (`CHILD_MASTER_CATALOG_CATEGORY_ID`),
  CONSTRAINT `FK32E6187FBC56FD9` FOREIGN KEY (`CHILD_MASTER_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK32E618727DC938B` FOREIGN KEY (`PARENT_MASTER_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master_category_master_rel`
--

DROP TABLE IF EXISTS `teco_catalog_master_category_master_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master_category_master_rel` (
  `MASTER_CATALOG_ID` bigint(20) NOT NULL,
  `MASTER_CATEGORY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MASTER_CATALOG_ID`,`MASTER_CATEGORY_ID`),
  UNIQUE KEY `MASTER_CATEGORY_ID` (`MASTER_CATEGORY_ID`),
  KEY `FK54FB4688FFF6A2BD` (`MASTER_CATALOG_ID`),
  KEY `FK54FB468839C370FC` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK54FB468839C370FC` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK54FB4688FFF6A2BD` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_master_category_product_marketing_rel`
--

DROP TABLE IF EXISTS `teco_catalog_master_category_product_marketing_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_master_category_product_marketing_rel` (
  `MASTER_CATEGORY_ID` bigint(20) NOT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MASTER_CATEGORY_ID`,`PRODUCT_MARKETING_ID`),
  KEY `FK7DCCCE6485628CB7` (`PRODUCT_MARKETING_ID`),
  KEY `FK7DCCCE6439C370FC` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK7DCCCE6439C370FC` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK7DCCCE6485628CB7` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATALOG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK1945EB03FFF6A2BD` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK1945EB03FFF6A2BD` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_PARENT_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FK31CA6BAB49AC15` (`DEFAULT_PARENT_CATEGORY_ID`),
  KEY `FK31CA6BA39C370FC` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK31CA6BA39C370FC` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK31CA6BAB49AC15` FOREIGN KEY (`DEFAULT_PARENT_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
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
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `VIRTUAL_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK984FBB37CF0A1A38` (`VIRTUAL_CATEGORY_ID`),
  KEY `FK984FBB379E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK984FBB379E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK984FBB37CF0A1A38` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual_category_child_category_rel`
--

DROP TABLE IF EXISTS `teco_catalog_virtual_category_child_category_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual_category_child_category_rel` (
  `PARENT_VIRTUAL_CATALOG_CATEGORY_ID` bigint(20) NOT NULL,
  `CHILD_VIRTUAL_CATALOG_CATEGORY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`PARENT_VIRTUAL_CATALOG_CATEGORY_ID`,`CHILD_VIRTUAL_CATALOG_CATEGORY_ID`),
  KEY `FK650971C0E7C470FD` (`PARENT_VIRTUAL_CATALOG_CATEGORY_ID`),
  KEY `FK650971C090F71E6F` (`CHILD_VIRTUAL_CATALOG_CATEGORY_ID`),
  CONSTRAINT `FK650971C090F71E6F` FOREIGN KEY (`CHILD_VIRTUAL_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK650971C0E7C470FD` FOREIGN KEY (`PARENT_VIRTUAL_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual_category_product_marketing_rel`
--

DROP TABLE IF EXISTS `teco_catalog_virtual_category_product_marketing_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual_category_product_marketing_rel` (
  `VIRTUAL_CATEGORY_ID` bigint(20) NOT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`VIRTUAL_CATEGORY_ID`,`PRODUCT_MARKETING_ID`),
  KEY `FK1171A78B85628CB7` (`PRODUCT_MARKETING_ID`),
  KEY `FK1171A78BCF0A1A38` (`VIRTUAL_CATEGORY_ID`),
  CONSTRAINT `FK1171A78BCF0A1A38` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK1171A78B85628CB7` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_catalog_virtual_category_virtual_rel`
--

DROP TABLE IF EXISTS `teco_catalog_virtual_category_virtual_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_catalog_virtual_category_virtual_rel` (
  `VIRTUAL_CATALOG_ID` bigint(20) NOT NULL,
  `VIRTUAL_CATEGORY_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`VIRTUAL_CATALOG_ID`,`VIRTUAL_CATEGORY_ID`),
  KEY `FK9ABE67201FAE9C03` (`VIRTUAL_CATALOG_ID`),
  KEY `FK9ABE6720CF0A1A38` (`VIRTUAL_CATEGORY_ID`),
  CONSTRAINT `FK9ABE6720CF0A1A38` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK9ABE67201FAE9C03` FOREIGN KEY (`VIRTUAL_CATALOG_ID`) REFERENCES `teco_catalog_virtual` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SIGN` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FK2557558F3501F200` (`GROUP_ID`),
  KEY `FK2557558F51AA496` (`CUSTOMER_ID`),
  CONSTRAINT `FK2557558F51AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FK2557558F3501F200` FOREIGN KEY (`GROUP_ID`) REFERENCES `teco_group` (`ID`)
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
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DEFAULT_LOCALE` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRSTNAME` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FK4AA895D551AA496` (`CUSTOMER_ID`),
  CONSTRAINT `FK4AA895D551AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKF81AC3D9E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKF81AC3D51AA496` (`CUSTOMER_ID`),
  CONSTRAINT `FKF81AC3D51AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FKF81AC3D9E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  `APP` varchar(255) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `HOST` varchar(255) DEFAULT NULL,
  `LOGIN_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK2712212251AA496` (`CUSTOMER_ID`),
  CONSTRAINT `FK2712212251AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
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
  `MARKET_AREA_CODE` varchar(255) DEFAULT NULL,
  `MOBILE` varchar(255) DEFAULT NULL,
  `OPTIN` int(11) NOT NULL DEFAULT '0',
  `PHONE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK5AFE443151AA496` (`CUSTOMER_ID`),
  CONSTRAINT `FK5AFE443151AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
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
  KEY `FKC39310B851AA496` (`CUSTOMER_ID`),
  CONSTRAINT `FKC39310B851AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
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
  KEY `FK541417D0BF633D8E` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK541417D0BF633D8E` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
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
  KEY `FK1951F7E4BF633D8E` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK1951F7E4BF633D8E` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_email`
--

DROP TABLE IF EXISTS `teco_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_email` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `EMAIL_CONTENT` longblob,
  `STATUS` varchar(255) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `JSESSION_ID` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CART_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3ECC475B3AEF356` (`CART_ID`),
  CONSTRAINT `FK3ECC475B3AEF356` FOREIGN KEY (`CART_ID`) REFERENCES `teco_cart` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DEFAULT_VALUE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
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
  KEY `FKF40A6BA7F5E27719` (`ENGINE_SETTING_ID`),
  CONSTRAINT `FKF40A6BA7F5E27719` FOREIGN KEY (`ENGINE_SETTING_ID`) REFERENCES `teco_engine_setting` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FKDD89ACF23E6494` (`ROLE_ID`),
  KEY `FKDD89ACF23501F200` (`GROUP_ID`),
  CONSTRAINT `FKDD89ACF23501F200` FOREIGN KEY (`GROUP_ID`) REFERENCES `teco_group` (`ID`),
  CONSTRAINT `FKDD89ACF23E6494` FOREIGN KEY (`ROLE_ID`) REFERENCES `teco_role` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `COUNTRY` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `LANGUAGE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MARKETPLACE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKB7CB901E1D553C9E` (`MARKETPLACE_ID`),
  CONSTRAINT `FKB7CB901E1D553C9E` FOREIGN KEY (`MARKETPLACE_ID`) REFERENCES `teco_marketplace` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `DOMAIN_NAME` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ECOMMERCE` tinyint(1) NOT NULL DEFAULT '0',
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `DEFAULT_LOCALIZATION_ID` bigint(20) DEFAULT NULL,
  `MARKET_ID` bigint(20) DEFAULT NULL,
  `VIRTUAL_CATALOG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK6179540EDDA51C25` (`CURRENCY_ID`),
  KEY `FK6179540E1FAE9C03` (`VIRTUAL_CATALOG_ID`),
  KEY `FK6179540EA07A16D6` (`MARKET_ID`),
  KEY `FK6179540E61742B8` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK6179540E61742B8` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK6179540E1FAE9C03` FOREIGN KEY (`VIRTUAL_CATALOG_ID`) REFERENCES `teco_catalog_virtual` (`ID`),
  CONSTRAINT `FK6179540EA07A16D6` FOREIGN KEY (`MARKET_ID`) REFERENCES `teco_market` (`ID`),
  CONSTRAINT `FK6179540EDDA51C25` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
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
  KEY `FK872711E46D70848F` (`MARKET_AREA_ID`),
  KEY `FK872711E42CB8B6` (`LOCALIZATION_ID`),
  CONSTRAINT `FK872711E42CB8B6` FOREIGN KEY (`LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK872711E46D70848F` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
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
  KEY `FK6211C93B6D70848F` (`MARKET_AREA_ID`),
  KEY `FK6211C93BE1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK6211C93BE1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK6211C93B6D70848F` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `PATH` varchar(255) DEFAULT NULL,
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATALOG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK60AAB389FFF6A2BD` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK60AAB389FFF6A2BD` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
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
  `CREATED_DATE` datetime DEFAULT NULL,
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
-- Table structure for table `teco_order`
--

DROP TABLE IF EXISTS `teco_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_order` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILLING_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `ORDER_NUM` varchar(255) DEFAULT NULL,
  `SHIPPING_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKB57F3066701F2717` (`PRODUCT_SKU_ID`),
  KEY `FKB57F3066BC0964DE` (`ORDER_ID`),
  CONSTRAINT `FKB57F3066BC0964DE` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order` (`ID`),
  CONSTRAINT `FKB57F3066701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
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
  KEY `FK4F1B9BB3BC0964DE` (`ORDER_ID`),
  CONSTRAINT `FK4F1B9BB3BC0964DE` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order` (`ID`)
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
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `SHIPPING_ID` bigint(20) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK23273D6DBC0964DE` (`ORDER_ID`),
  CONSTRAINT `FK23273D6DBC0964DE` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order` (`ID`)
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
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PERCENT` decimal(19,2) DEFAULT NULL,
  `tax_ID` bigint(20) DEFAULT NULL,
  `ORDER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK923E0FD8BC0964DE` (`ORDER_ID`),
  CONSTRAINT `FK923E0FD8BC0964DE` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PAYMENT_GATEWAY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKCB4A3CE69E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKCB4A3CE65840918B` (`PAYMENT_GATEWAY_ID`),
  CONSTRAINT `FKCB4A3CE65840918B` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_payment_gateway` (`ID`),
  CONSTRAINT `FKCB4A3CE69E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK825179EA85628CB7` (`PRODUCT_MARKETING_ID`),
  CONSTRAINT `FK825179EA85628CB7` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `BRAND_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_TYPE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKE7564D74B1DF6A47` (`BRAND_ID`),
  KEY `FKE7564D742704B44` (`PRODUCT_MARKETING_TYPE_ID`),
  CONSTRAINT `FKE7564D742704B44` FOREIGN KEY (`PRODUCT_MARKETING_TYPE_ID`) REFERENCES `teco_product_marketing_type` (`ID`),
  CONSTRAINT `FKE7564D74B1DF6A47` FOREIGN KEY (`BRAND_ID`) REFERENCES `teco_product_brand` (`ID`)
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
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETTING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKC35BC1716C4FD693` (`PRODUCT_MARKETTING_ID`),
  KEY `FKC35BC1719E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKC35BC1719E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FKC35BC1716C4FD693` FOREIGN KEY (`PRODUCT_MARKETTING_ID`) REFERENCES `teco_product_marketing` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKFC705E29E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKFC705E29E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) NOT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  KEY `FKF5E65EEB85628CB7` (`PRODUCT_MARKETING_ID`),
  CONSTRAINT `FKF5E65EEB85628CB7` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
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
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4419AA28701F2717` (`PRODUCT_SKU_ID`),
  KEY `FK4419AA289E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK4419AA289E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`),
  CONSTRAINT `FK4419AA28701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK312A5ED5DDA51C25` (`CURRENCY_ID`),
  KEY `FK312A5ED5701F2717` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK312A5ED5701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK312A5ED5DDA51C25` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
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
  KEY `FKCD1C0D7E701F2717` (`PRODUCT_SKU_ID`),
  KEY `FKCD1C0D7EE1D23356` (`RETAILER_ID`),
  CONSTRAINT `FKCD1C0D7EE1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FKCD1C0D7E701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `STOCK_GLOBAL` int(11) DEFAULT NULL,
  `STOCK_PREORDERED` int(11) DEFAULT NULL,
  `STOCK_RESERVED_ECO` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK15CE5964701F2717` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK15CE5964701F2717` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `IS_BRAND` tinyint(1) NOT NULL DEFAULT '0',
  `IS_DEFAULT` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ECOMMERCE` tinyint(1) NOT NULL DEFAULT '0',
  `IS_OFFICIAL_RETAILER` tinyint(1) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE_SCORE` tinyint(1) NOT NULL DEFAULT '0',
  `QUALITY_OF_SERVICE` tinyint(1) NOT NULL DEFAULT '0',
  `RATIO_QUALITY_PRICE` tinyint(1) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FK4F93ACE7E1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK4F93ACE7E1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
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
  `IS_GLOBAL` tinyint(1) NOT NULL DEFAULT '0',
  `LOCALIZATION_CODE` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK860346CF9E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK860346CFE1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK860346CFE1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK860346CF9E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  KEY `FK14A532B51AA496` (`CUSTOMER_ID`),
  KEY `FK14A532BE1D23356` (`RETAILER_ID`),
  KEY `FK14A532BEE44713F` (`RETAILER_ID`),
  CONSTRAINT `FK14A532BEE44713F` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer_customer_comment` (`ID`),
  CONSTRAINT `FK14A532B51AA496` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FK14A532BE1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
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
  `rate` int(11) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK8AC9F374E1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK8AC9F374E1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
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
  KEY `FK714F5232F219157D` (`RETAILER_TAG_ID`),
  KEY `FK714F5232E1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK714F5232E1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK714F5232F219157D` FOREIGN KEY (`RETAILER_TAG_ID`) REFERENCES `teco_retailer_tag` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FK864730503E6494` (`ROLE_ID`),
  KEY `FK864730509D11DE74` (`PERMISSION_ID`),
  CONSTRAINT `FK864730509D11DE74` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `teco_permission` (`ID`),
  CONSTRAINT `FK864730503E6494` FOREIGN KEY (`ROLE_ID`) REFERENCES `teco_role` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SALIENCE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKF0E495859E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FKF0E495859E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `RULE_REFERENTIAL_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKAF7542089E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FKAF75420873A05083` (`RULE_REFERENTIAL_ID`),
  CONSTRAINT `FKAF75420873A05083` FOREIGN KEY (`RULE_REFERENTIAL_ID`) REFERENCES `teco_rule_referential` (`ID`),
  CONSTRAINT `FKAF7542089E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  KEY `FK8FA169C2A6274FFC` (`RULE_REPOSITORY_ID`),
  KEY `FK8FA169C2FB22202F` (`RULE_REPOSITORY_ID`),
  KEY `FK8FA169C29191B6EA` (`RULE_REPOSITORY_ATTRIBUTE_ID`),
  CONSTRAINT `FK8FA169C29191B6EA` FOREIGN KEY (`RULE_REPOSITORY_ATTRIBUTE_ID`) REFERENCES `teco_rule_repository_attribute` (`ID`),
  CONSTRAINT `FK8FA169C2A6274FFC` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_referential` (`ID`),
  CONSTRAINT `FK8FA169C2FB22202F` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_repository` (`ID`)
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
  KEY `FK2477FFAFFB22202F` (`RULE_REPOSITORY_ID`),
  KEY `FK2477FFAF73A05083` (`RULE_REFERENTIAL_ID`),
  CONSTRAINT `FK2477FFAF73A05083` FOREIGN KEY (`RULE_REFERENTIAL_ID`) REFERENCES `teco_rule_referential` (`ID`),
  CONSTRAINT `FK2477FFAFFB22202F` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_repository` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_shipping`
--

DROP TABLE IF EXISTS `teco_shipping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_shipping` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_shipping_country`
--

DROP TABLE IF EXISTS `teco_shipping_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_shipping_country` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTRY` varchar(255) DEFAULT NULL,
  `SHIPPING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKB02EDF476D201296` (`SHIPPING_ID`),
  CONSTRAINT `FKB02EDF476D201296` FOREIGN KEY (`SHIPPING_ID`) REFERENCES `teco_shipping` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_shipping_county`
--

DROP TABLE IF EXISTS `teco_shipping_county`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_shipping_county` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTY` varchar(255) DEFAULT NULL,
  `SHIPPING_COUNTRY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKD4228B59A1C46C81` (`SHIPPING_COUNTRY_ID`),
  CONSTRAINT `FKD4228B59A1C46C81` FOREIGN KEY (`SHIPPING_COUNTRY_ID`) REFERENCES `teco_shipping_country` (`ID`)
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
  `BUSINESS_NAME` varchar(255) DEFAULT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `POSTAL_CODE` varchar(255) DEFAULT NULL,
  `STATE_CODE` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK485B749FE1D23356` (`RETAILER_ID`),
  CONSTRAINT `FK485B749FE1D23356` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  `STORE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3BC62EDC9E2B7B17` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK3BC62EDCD90A97FE` (`STORE_ID`),
  CONSTRAINT `FK3BC62EDCD90A97FE` FOREIGN KEY (`STORE_ID`) REFERENCES `teco_store` (`ID`),
  CONSTRAINT `FK3BC62EDC9E2B7B17` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PERCENT` decimal(19,2) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
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
  KEY `FK9810A820CA8ED4BE` (`TAX_ID`),
  CONSTRAINT `FK9810A820CA8ED4BE` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_tax` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax_county`
--

DROP TABLE IF EXISTS `teco_tax_county`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax_county` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE_COUNTY` varchar(255) DEFAULT NULL,
  `TAX_COUNTRY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4E7C3608BC3117F` (`TAX_COUNTRY_ID`),
  CONSTRAINT `FK4E7C3608BC3117F` FOREIGN KEY (`TAX_COUNTRY_ID`) REFERENCES `teco_tax_country` (`ID`)
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

-- Dump completed on 2013-07-29 21:14:28
