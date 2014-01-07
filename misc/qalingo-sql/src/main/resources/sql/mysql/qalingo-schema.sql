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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_jgtlyfve34p2c65xnct2vt2ic` (`CODE`),
  KEY `FK_ri7mpdesyu68ijswohbwbbrni` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK_ri7mpdesyu68ijswohbwbbrni` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_iq1gmq54mbe3ur0di4wfdfguc` (`LOCALIZATION_ID`),
  KEY `FK_8c0il34v26pmnsr9bj0y2modn` (`COMPANY_ID`),
  CONSTRAINT `FK_8c0il34v26pmnsr9bj0y2modn` FOREIGN KEY (`COMPANY_ID`) REFERENCES `tbo_company` (`ID`),
  CONSTRAINT `FK_iq1gmq54mbe3ur0di4wfdfguc` FOREIGN KEY (`LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_jhtxqsbet0iv5un0bw2up7it8` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ghj7lvw3yn310la07s3tkq5ex` (`ROLE_ID`),
  KEY `FK_q2gyrcrktmbyaybo4ht4k9sup` (`GROUP_ID`),
  CONSTRAINT `FK_q2gyrcrktmbyaybo4ht4k9sup` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`),
  CONSTRAINT `FK_ghj7lvw3yn310la07s3tkq5ex` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `POSITION` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MENU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_5fr5my4ocrdti33j7nu8107qp` (`CODE`),
  KEY `FK_84imp7y9243g4saxsbjjlvhxx` (`MENU_ID`),
  CONSTRAINT `FK_84imp7y9243g4saxsbjjlvhxx` FOREIGN KEY (`MENU_ID`) REFERENCES `tbo_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_sar6b3rghv4i3aysalf1akbfc` (`GROUP_ID`),
  KEY `FK_3y8jyx7mr9pwy3jhpble51umm` (`MENU_ID`),
  CONSTRAINT `FK_3y8jyx7mr9pwy3jhpble51umm` FOREIGN KEY (`MENU_ID`) REFERENCES `tbo_menu` (`ID`),
  CONSTRAINT `FK_sar6b3rghv4i3aysalf1akbfc` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_m8sw8b3qmq1ol75hfb7cjqttm` (`ROLE_ID`),
  KEY `FK_qkisw1nl39mts80hc2kh8ym9t` (`MENU_ID`),
  CONSTRAINT `FK_qkisw1nl39mts80hc2kh8ym9t` FOREIGN KEY (`MENU_ID`) REFERENCES `tbo_menu` (`ID`),
  CONSTRAINT `FK_m8sw8b3qmq1ol75hfb7cjqttm` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_buqdwcghn33244cd5nf6h6sla` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_5fmapjq84mv54vj0e0gphwmlc` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_njffcca5ky58bn6t8arnlyl6c` (`PERMISSION_ID`),
  KEY `FK_a2bl7vgmbffqtbuf95nbip0m0` (`ROLE_ID`),
  CONSTRAINT `FK_a2bl7vgmbffqtbuf95nbip0m0` FOREIGN KEY (`ROLE_ID`) REFERENCES `tbo_role` (`ID`),
  CONSTRAINT `FK_njffcca5ky58bn6t8arnlyl6c` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `tbo_permission` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_5itsmicuc964ddp5kt5v67hr9` (`LOGIN`,`EMAIL`),
  KEY `FK_2gfthps0wpn4ea6acbvjtfass` (`COMPANY_ID`),
  KEY `FK_bt5puocdq9nufwisk5jkl6le7` (`DEFAULT_LOCALIZATION_ID`),
  CONSTRAINT `FK_bt5puocdq9nufwisk5jkl6le7` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`),
  CONSTRAINT `FK_2gfthps0wpn4ea6acbvjtfass` FOREIGN KEY (`COMPANY_ID`) REFERENCES `tbo_company` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_onhrqfdg55klxu05ro4v9s8ig` (`USER_ID`),
  CONSTRAINT `FK_onhrqfdg55klxu05ro4v9s8ig` FOREIGN KEY (`USER_ID`) REFERENCES `tbo_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_j9lsv4ikpehnovyc6pfuse52g` (`GROUP_ID`),
  KEY `FK_hn80dj0uf94163376qilv6c02` (`USER_ID`),
  CONSTRAINT `FK_hn80dj0uf94163376qilv6c02` FOREIGN KEY (`USER_ID`) REFERENCES `tbo_user` (`ID`),
  CONSTRAINT `FK_j9lsv4ikpehnovyc6pfuse52g` FOREIGN KEY (`GROUP_ID`) REFERENCES `tbo_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  `MASTER_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `VIRTUAL_CATEGORY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_42uds38cxrithydjyvlj90r5m` (`CODE`),
  KEY `FK_pslu02aw5x6wvd0hcuy0w4aya` (`PRODUCT_SKU_ID`),
  KEY `FK_eu2rwel4u9bnkklmi25mh5syx` (`RETAILER_ID`),
  KEY `FK_58mp7g53a6e67fedstygv77pt` (`PRODUCT_MARKETING_ID`),
  KEY `FK_jg95opnnnu2tkc4ssstos9ufb` (`MASTER_CATEGORY_ID`),
  KEY `FK_klp4mf0nau382ti1gym48s742` (`VIRTUAL_CATEGORY_ID`),
  CONSTRAINT `FK_klp4mf0nau382ti1gym48s742` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK_58mp7g53a6e67fedstygv77pt` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`),
  CONSTRAINT `FK_eu2rwel4u9bnkklmi25mh5syx` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK_jg95opnnnu2tkc4ssstos9ufb` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK_pslu02aw5x6wvd0hcuy0w4aya` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `WITH_PLANNER` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `SHIPPING_ADDRESS_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `ECO_ENGINE_SESSION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_27ynp6ehlcx4sv1am9tpu81em` (`CURRENCY_ID`),
  KEY `FK_jvfvq23e9dv8x7as49xj5mcqt` (`ECO_ENGINE_SESSION_ID`),
  CONSTRAINT `FK_jvfvq23e9dv8x7as49xj5mcqt` FOREIGN KEY (`ECO_ENGINE_SESSION_ID`) REFERENCES `teco_engine_session` (`ID`),
  CONSTRAINT `FK_27ynp6ehlcx4sv1am9tpu81em` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_pmiv2c5fjqsdr56i4b0b3mqg7` (`CART_ID`),
  CONSTRAINT `FK_pmiv2c5fjqsdr56i4b0b3mqg7` FOREIGN KEY (`CART_ID`) REFERENCES `teco_cart` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_gwvt4mmiy3nk4fu7lu0y8ayhf` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_7f5h5w3ni8ulpxyy32igr4jl1` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_t81hl7a2ll1bofdykq5crqikm` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK_t81hl7a2ll1bofdykq5crqikm` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_category_type` (`ID`),
  CONSTRAINT `FK_7f5h5w3ni8ulpxyy32igr4jl1` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_20jtrodij2e86u5se7t5aybtn` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_pk69qew7xv83w63gh9qv0lux9` (`CODE`),
  KEY `FK_f6rem72x7plusjgr7c6x3mhpt` (`CATALOG_CATEGORY_TYPE_ID`),
  KEY `FK_tg64vbfd9b9i9xq11bkaoym6a` (`DEFAULT_PARENT_CATEGORY_ID`),
  CONSTRAINT `FK_tg64vbfd9b9i9xq11bkaoym6a` FOREIGN KEY (`DEFAULT_PARENT_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK_f6rem72x7plusjgr7c6x3mhpt` FOREIGN KEY (`CATALOG_CATEGORY_TYPE_ID`) REFERENCES `teco_catalog_category_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_dwswy0asbubrq33tcc757bm2p` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_j25weryjwyng5igxhr6b025gi` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK_j25weryjwyng5igxhr6b025gi` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK_dwswy0asbubrq33tcc757bm2p` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_tr3wlesldr8xwx7nfeldejf22` (`CHILD_MASTER_CATALOG_CATEGORY_ID`),
  KEY `FK_e3p75ulpdwwi6r7tfrbkl30h3` (`PARENT_MASTER_CATALOG_CATEGORY_ID`),
  CONSTRAINT `FK_e3p75ulpdwwi6r7tfrbkl30h3` FOREIGN KEY (`PARENT_MASTER_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK_tr3wlesldr8xwx7nfeldejf22` FOREIGN KEY (`CHILD_MASTER_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_5kaifgv2e7qc0n6s4wdnboa23` (`MASTER_CATEGORY_ID`),
  KEY `FK_5kaifgv2e7qc0n6s4wdnboa23` (`MASTER_CATEGORY_ID`),
  KEY `FK_i8weifm92grikx3c3itn44gu1` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK_i8weifm92grikx3c3itn44gu1` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`),
  CONSTRAINT `FK_5kaifgv2e7qc0n6s4wdnboa23` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_stvj95gpice0u1v9982gul1fd` (`PRODUCT_MARKETING_ID`),
  KEY `FK_ic7sf7wcl7nyjl0aaylhdgt8j` (`MASTER_CATEGORY_ID`),
  CONSTRAINT `FK_ic7sf7wcl7nyjl0aaylhdgt8j` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`),
  CONSTRAINT `FK_stvj95gpice0u1v9982gul1fd` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_hjbbkhx8q4ejrb2wxgjkj47vg` (`CODE`),
  KEY `FK_5l9j2me2lb7utxb93hd0lsdoq` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK_5l9j2me2lb7utxb93hd0lsdoq` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_hujy6pk60gnfinveussu4mapt` (`CODE`),
  KEY `FK_msx7hkv97c3r3ryc39ouky0px` (`MASTER_CATEGORY_ID`),
  KEY `FK_jrnv1gx2xu5jruf5qp28iy2po` (`DEFAULT_PARENT_CATEGORY_ID`),
  CONSTRAINT `FK_jrnv1gx2xu5jruf5qp28iy2po` FOREIGN KEY (`DEFAULT_PARENT_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK_msx7hkv97c3r3ryc39ouky0px` FOREIGN KEY (`MASTER_CATEGORY_ID`) REFERENCES `teco_catalog_master_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ic6uw4mlk3e2oldy22osmqngi` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_mfjrkdbfvqq1nc1ucfollfjrw` (`VIRTUAL_CATEGORY_ID`),
  CONSTRAINT `FK_mfjrkdbfvqq1nc1ucfollfjrw` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK_ic6uw4mlk3e2oldy22osmqngi` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_t77t4dj911idjlr03hhf6talg` (`CHILD_VIRTUAL_CATALOG_CATEGORY_ID`),
  KEY `FK_5ltu1a9tdm6dm58m1slokb34` (`PARENT_VIRTUAL_CATALOG_CATEGORY_ID`),
  CONSTRAINT `FK_5ltu1a9tdm6dm58m1slokb34` FOREIGN KEY (`PARENT_VIRTUAL_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK_t77t4dj911idjlr03hhf6talg` FOREIGN KEY (`CHILD_VIRTUAL_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_6j4o7usi2fptxgg48vgtpr04u` (`PRODUCT_MARKETING_ID`),
  KEY `FK_eqnyuk292cjwa2oc7kfqib8qa` (`VIRTUAL_CATEGORY_ID`),
  CONSTRAINT `FK_eqnyuk292cjwa2oc7kfqib8qa` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK_6j4o7usi2fptxgg48vgtpr04u` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_9we4f3lyjvij5c1ytydmlc85a` (`VIRTUAL_CATEGORY_ID`),
  KEY `FK_p8880sdu1yu6860we6ke6ek2e` (`VIRTUAL_CATALOG_ID`),
  CONSTRAINT `FK_p8880sdu1yu6860we6ke6ek2e` FOREIGN KEY (`VIRTUAL_CATALOG_ID`) REFERENCES `teco_catalog_virtual` (`ID`),
  CONSTRAINT `FK_9we4f3lyjvij5c1ytydmlc85a` FOREIGN KEY (`VIRTUAL_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `FORMAT_LOCALE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SIGN` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_qr9s7ghwj9vv5hffhinjgf0yx` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_4ey6n9c19d6fvmav4oswlnaq8` (`GROUP_ID`),
  KEY `FK_lylguwn1nfrna3yqwgj57v714` (`CUSTOMER_ID`),
  CONSTRAINT `FK_lylguwn1nfrna3yqwgj57v714` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FK_4ey6n9c19d6fvmav4oswlnaq8` FOREIGN KEY (`GROUP_ID`) REFERENCES `teco_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `CODE` varchar(255) DEFAULT NULL,
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
  UNIQUE KEY `UK_iweqobxtdlxh2tykm1prmcp50` (`LOGIN`,`EMAIL`),
  KEY `FK_o9u7p743832k8x3w2yfm62yt9` (`CUSTOMER_ID`),
  CONSTRAINT `FK_o9u7p743832k8x3w2yfm62yt9` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer_order_audit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_6ns0c0f2jsv5vylhqgvmx9rgm` (`CUSTOMER_ID`),
  CONSTRAINT `FK_6ns0c0f2jsv5vylhqgvmx9rgm` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_8wvdbg7ax7vkudnpt1t2n2lo2` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_o346x7l9oxaqb7faomj3wi7dr` (`CUSTOMER_ID`),
  CONSTRAINT `FK_o346x7l9oxaqb7faomj3wi7dr` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`),
  CONSTRAINT `FK_8wvdbg7ax7vkudnpt1t2n2lo2` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_t1ccrk5b4miw52btcxkjski1n` (`CUSTOMER_ID`),
  CONSTRAINT `FK_t1ccrk5b4miw52btcxkjski1n` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_fmnewq71ona1je6j9sycde8i4` (`CUSTOMER_ID`),
  CONSTRAINT `FK_fmnewq71ona1je6j9sycde8i4` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_hgf8oiirj7d28mfoanjacw0af` (`CUSTOMER_ID`),
  CONSTRAINT `FK_hgf8oiirj7d28mfoanjacw0af` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_jsxvunsqsnnpi2ixtlul60d0g` (`CUSTOMER_ID`),
  CONSTRAINT `FK_jsxvunsqsnnpi2ixtlul60d0g` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_9lyhrpw2bn7eio1mcwrm6op1g` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK_9lyhrpw2bn7eio1mcwrm6op1g` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ajjoua9gp03icod37te18sc0f` (`CUSTOMER_ID`),
  CONSTRAINT `FK_ajjoua9gp03icod37te18sc0f` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_aowrrxstxdhvwhk1r58s2jaia` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK_aowrrxstxdhvwhk1r58s2jaia` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_2nkip78o71pmqxkn57yesauor` (`CUSTOMER_MARKET_AREA_ID`),
  CONSTRAINT `FK_2nkip78o71pmqxkn57yesauor` FOREIGN KEY (`CUSTOMER_MARKET_AREA_ID`) REFERENCES `teco_customer_market_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_delivery_method`
--

DROP TABLE IF EXISTS `teco_delivery_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_delivery_method` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DELIVERY_TIME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_nxoawrr3p3iq3tf3k7mb0gxnu` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_lr2445lxm8m3cobul9dhdrmei` (`DELIVERY_METHOD_ID`),
  CONSTRAINT `FK_lr2445lxm8m3cobul9dhdrmei` FOREIGN KEY (`DELIVERY_METHOD_ID`) REFERENCES `teco_delivery_method` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_96jmb8ewdl8b0j37ahlavub48` (`DELIVERY_METHOD_COUNTRY_ID`),
  CONSTRAINT `FK_96jmb8ewdl8b0j37ahlavub48` FOREIGN KEY (`DELIVERY_METHOD_COUNTRY_ID`) REFERENCES `teco_delivery_method_country` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `PRICE` decimal(19,2) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `CURRENCY_ID` bigint(20) DEFAULT NULL,
  `DELIVERY_METHOD_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_e5r8jbbbqynq4ar6urgdcm75g` (`CURRENCY_ID`),
  KEY `FK_ais8xqylv5x9mynfdopjssxom` (`DELIVERY_METHOD_ID`),
  CONSTRAINT `FK_ais8xqylv5x9mynfdopjssxom` FOREIGN KEY (`DELIVERY_METHOD_ID`) REFERENCES `teco_delivery_method` (`ID`),
  CONSTRAINT `FK_e5r8jbbbqynq4ar6urgdcm75g` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_3r25t03l91mpa3gai6ij0po35` (`JSESSION_ID`,`ENGINE_SESSION_GUID`),
  KEY `FK_ksx6o3grm0ojnr6ecir0f7cdi` (`CUSTOMER_ID`),
  CONSTRAINT `FK_ksx6o3grm0ojnr6ecir0f7cdi` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_r4p9m8owfa61eax986d2e7y3a` (`ENGINE_SETTING_ID`),
  CONSTRAINT `FK_r4p9m8owfa61eax986d2e7y3a` FOREIGN KEY (`ENGINE_SETTING_ID`) REFERENCES `teco_engine_setting` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_lfhk6kaq4vv9659fsj3vb0638` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_9jfufgbedfkbba3q64srrb0qb` (`ROLE_ID`),
  KEY `FK_2u74afwipmmhjfwnem7cnupjk` (`GROUP_ID`),
  CONSTRAINT `FK_2u74afwipmmhjfwnem7cnupjk` FOREIGN KEY (`GROUP_ID`) REFERENCES `teco_group` (`ID`),
  CONSTRAINT `FK_9jfufgbedfkbba3q64srrb0qb` FOREIGN KEY (`ROLE_ID`) REFERENCES `teco_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_6wraha67edqbemhpxly769119` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_2apd77axe2l3wwoc4l6mtex68` (`CODE`),
  KEY `FK_1i6bp2icaj1qxt5p1hem2jgok` (`MARKETPLACE_ID`),
  CONSTRAINT `FK_1i6bp2icaj1qxt5p1hem2jgok` FOREIGN KEY (`MARKETPLACE_ID`) REFERENCES `teco_marketplace` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_9y8jprquilkibgkqftb33lrra` (`CODE`),
  KEY `FK_8joho0w5e4v2k7uivl14nv7i0` (`VIRTUAL_CATALOG_ID`),
  KEY `FK_ilvktj5qg7v2qyu8q7r60s4fv` (`DEFAULT_CURRENCY_ID`),
  KEY `FK_tac29tofjt21h72gr93vpdrk2` (`DEFAULT_LOCALIZATION_ID`),
  KEY `FK_ohc19aau3i7k4w8l2cy8nl1v6` (`DEFAULT_RETAILER_ID`),
  KEY `FK_7978cjw1bjdhwitpgjbtn6fp8` (`MARKET_ID`),
  CONSTRAINT `FK_7978cjw1bjdhwitpgjbtn6fp8` FOREIGN KEY (`MARKET_ID`) REFERENCES `teco_market` (`ID`),
  CONSTRAINT `FK_8joho0w5e4v2k7uivl14nv7i0` FOREIGN KEY (`VIRTUAL_CATALOG_ID`) REFERENCES `teco_catalog_virtual` (`ID`),
  CONSTRAINT `FK_ilvktj5qg7v2qyu8q7r60s4fv` FOREIGN KEY (`DEFAULT_CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`),
  CONSTRAINT `FK_ohc19aau3i7k4w8l2cy8nl1v6` FOREIGN KEY (`DEFAULT_RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK_tac29tofjt21h72gr93vpdrk2` FOREIGN KEY (`DEFAULT_LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `MARKET_AREA_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_sdki81kyc87611aix7oqlcu9h` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_3axk17oi7rj6tsx904gnivp2u` (`MARKET_AREA_ID`),
  CONSTRAINT `FK_3axk17oi7rj6tsx904gnivp2u` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FK_sdki81kyc87611aix7oqlcu9h` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_1rita6ip286wmulxu66lmrg9h` (`CURRENCY_ID`),
  KEY `FK_1eobkjdt3n5vbom09kc55kew` (`MARKET_AREA_ID`),
  CONSTRAINT `FK_1eobkjdt3n5vbom09kc55kew` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FK_1rita6ip286wmulxu66lmrg9h` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_market_area_delivery_method_rel`
--

DROP TABLE IF EXISTS `teco_market_area_delivery_method_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_market_area_delivery_method_rel` (
  `MARKET_AREA_ID` bigint(20) NOT NULL,
  `DELIVERY_METHOD_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKET_AREA_ID`,`DELIVERY_METHOD_ID`),
  KEY `FK_tpjxvlsptfw3bef95u1sflay1` (`DELIVERY_METHOD_ID`),
  KEY `FK_t1h83cib1yig2madks5njgpey` (`MARKET_AREA_ID`),
  CONSTRAINT `FK_t1h83cib1yig2madks5njgpey` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FK_tpjxvlsptfw3bef95u1sflay1` FOREIGN KEY (`DELIVERY_METHOD_ID`) REFERENCES `teco_delivery_method` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_1kx7esbdcj3yy5w4xehn9f8u8` (`LOCALIZATION_ID`),
  KEY `FK_q5o9k9s8y1on3fcqn8klr7ddm` (`MARKET_AREA_ID`),
  CONSTRAINT `FK_q5o9k9s8y1on3fcqn8klr7ddm` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FK_1kx7esbdcj3yy5w4xehn9f8u8` FOREIGN KEY (`LOCALIZATION_ID`) REFERENCES `teco_localization` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`MARKET_AREA_ID`,`PAYMENT_GATEWAY_ID`),
  KEY `FK_extlng6gciimd73b1a7mx07ic` (`PAYMENT_GATEWAY_ID`),
  KEY `FK_6hgtw291p17ujwv5ns247l51x` (`MARKET_AREA_ID`),
  CONSTRAINT `FK_6hgtw291p17ujwv5ns247l51x` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FK_extlng6gciimd73b1a7mx07ic` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_delivery_method` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_470pa4phh726i5ri03h747ns1` (`RETAILER_ID`),
  KEY `FK_a2ycmlncom74wv0lumd5wc471` (`MARKET_AREA_ID`),
  CONSTRAINT `FK_a2ycmlncom74wv0lumd5wc471` FOREIGN KEY (`MARKET_AREA_ID`) REFERENCES `teco_market_area` (`ID`),
  CONSTRAINT `FK_470pa4phh726i5ri03h747ns1` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `MARKET_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_slgwys7sa25p0sl3yst53h7pt` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_lhgefj82thuqb35sc4khlgj4k` (`MARKET_ID`),
  CONSTRAINT `FK_lhgefj82thuqb35sc4khlgj4k` FOREIGN KEY (`MARKET_ID`) REFERENCES `teco_market` (`ID`),
  CONSTRAINT `FK_slgwys7sa25p0sl3yst53h7pt` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `THEME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `MASTER_CATALOG_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_3nnab9mjrkbk7qu12hpcsdunn` (`CODE`),
  KEY `FK_fvcph5yghwr7dxthgec4oof5q` (`MASTER_CATALOG_ID`),
  CONSTRAINT `FK_fvcph5yghwr7dxthgec4oof5q` FOREIGN KEY (`MASTER_CATALOG_ID`) REFERENCES `teco_catalog_master` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `MARKET_PLACE_ID` bigint(20) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `START_DATE` datetime DEFAULT NULL,
  `STRING_VALUE` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `ATTRIBUTE_DEFINITION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_gg3i0ptr7xodyuqg3ibbmj8ws` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_afj0ad0gnuamvuwdb9pv7kpj4` (`MARKET_PLACE_ID`),
  CONSTRAINT `FK_afj0ad0gnuamvuwdb9pv7kpj4` FOREIGN KEY (`MARKET_PLACE_ID`) REFERENCES `teco_marketplace` (`ID`),
  CONSTRAINT `FK_gg3i0ptr7xodyuqg3ibbmj8ws` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_2jip9r6fh6dqgh9y0iht3bb4j` (`ORDER_NUM`,`PREFIX_HASH_FOLDER`),
  KEY `FK_4qbyev26o160s9bh278uvk12b` (`BILLING_ORDER_ADDRESS_ID`),
  KEY `FK_litgnal4lh6ukuc4wpoi6oia4` (`CURRENCY_ID`),
  KEY `FK_q28o5aoyaf8mmldrwi7jja9u8` (`SHIPPING_ORDER_ADDRESS_ID`),
  CONSTRAINT `FK_q28o5aoyaf8mmldrwi7jja9u8` FOREIGN KEY (`SHIPPING_ORDER_ADDRESS_ID`) REFERENCES `teco_order_address` (`ID`),
  CONSTRAINT `FK_4qbyev26o160s9bh278uvk12b` FOREIGN KEY (`BILLING_ORDER_ADDRESS_ID`) REFERENCES `teco_order_address` (`ID`),
  CONSTRAINT `FK_litgnal4lh6ukuc4wpoi6oia4` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ekfox2p71kic83oo9q1lwcw4a` (`CURRENCY_ID`),
  KEY `FK_nmc05tyhok7t5r58golsviykv` (`PRODUCT_SKU_ID`),
  KEY `FK_4txi9gl9xdxpd87r1hxcb2s5r` (`ORDER_SHIPMENT_ID`),
  CONSTRAINT `FK_4txi9gl9xdxpd87r1hxcb2s5r` FOREIGN KEY (`ORDER_SHIPMENT_ID`) REFERENCES `teco_order_shipment` (`ID`),
  CONSTRAINT `FK_ekfox2p71kic83oo9q1lwcw4a` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`),
  CONSTRAINT `FK_nmc05tyhok7t5r58golsviykv` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_hcm2wd0xy8tv9xonj1gj4wa3o` (`ORDER_ID`),
  CONSTRAINT `FK_hcm2wd0xy8tv9xonj1gj4wa3o` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_dailb5i9me4hmw5jgmis82uk4` (`ORDER_ID`),
  CONSTRAINT `FK_dailb5i9me4hmw5jgmis82uk4` FOREIGN KEY (`ORDER_ID`) REFERENCES `teco_order_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_la8u2cni5ba1h9tvku6i58buk` (`ORDER_TAX_ID`),
  CONSTRAINT `FK_la8u2cni5ba1h9tvku6i58buk` FOREIGN KEY (`ORDER_TAX_ID`) REFERENCES `teco_order_item` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_5vuoaviy3b25t72uw5dxc4mi0` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_41l8qujedib73v63k3xmqicik` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_cjqbeq8c4x3mr04so9oabigqa` (`PAYMENT_GATEWAY_ID`),
  CONSTRAINT `FK_cjqbeq8c4x3mr04so9oabigqa` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_payment_gateway` (`ID`),
  CONSTRAINT `FK_41l8qujedib73v63k3xmqicik` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_payment_gateway_option`
--

DROP TABLE IF EXISTS `teco_payment_gateway_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_payment_gateway_option` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_km6edx35p2h1ia0u31obg5int` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_8uxl5gaur5jbrejrfyq2o1coy` (`PAYMENT_GATEWAY_OPTION_ID`),
  KEY `FK_l9cqx2i5typdbb1xdhaf2w3jt` (`PAYMENT_GATEWAY_ID`),
  CONSTRAINT `FK_l9cqx2i5typdbb1xdhaf2w3jt` FOREIGN KEY (`PAYMENT_GATEWAY_ID`) REFERENCES `teco_payment_gateway` (`ID`),
  CONSTRAINT `FK_8uxl5gaur5jbrejrfyq2o1coy` FOREIGN KEY (`PAYMENT_GATEWAY_OPTION_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_8fsiojqd5mex9ogn3rgtb34vl` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_r11ekvpnpxnctmncdkoux13id` (`PRODUCT_MARKETING_ID`),
  CONSTRAINT `FK_r11ekvpnpxnctmncdkoux13id` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_db9lji02w98uivl5labh2bgav` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `DEFAULT_CATALOG_CATEGORY_ID` bigint(20) DEFAULT NULL,
  `BRAND_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_MARKETING_TYPE_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_BRAND_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_j5r9gdb29rp0lai5p4sfvg7kr` (`CODE`),
  KEY `FK_6lq5mq8q83iws09l8wcn27pld` (`DEFAULT_CATALOG_CATEGORY_ID`),
  KEY `FK_lr9jcv8vyk65yoxs6mhefjvjb` (`BRAND_ID`),
  KEY `FK_9ebtxkky1bkn3aylnlosilt1s` (`PRODUCT_MARKETING_TYPE_ID`),
  KEY `FK_75aj4wwkwq884odorh4utp8np` (`PRODUCT_BRAND_ID`),
  CONSTRAINT `FK_75aj4wwkwq884odorh4utp8np` FOREIGN KEY (`PRODUCT_BRAND_ID`) REFERENCES `teco_product_brand` (`ID`),
  CONSTRAINT `FK_6lq5mq8q83iws09l8wcn27pld` FOREIGN KEY (`DEFAULT_CATALOG_CATEGORY_ID`) REFERENCES `teco_catalog_virtual_category` (`ID`),
  CONSTRAINT `FK_9ebtxkky1bkn3aylnlosilt1s` FOREIGN KEY (`PRODUCT_MARKETING_TYPE_ID`) REFERENCES `teco_product_marketing_type` (`ID`),
  CONSTRAINT `FK_lr9jcv8vyk65yoxs6mhefjvjb` FOREIGN KEY (`BRAND_ID`) REFERENCES `teco_product_brand` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `PRODUCT_MARKETING_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_rgqe14d5qrapcm839ya1n76wd` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_i1xh03upkoiamagxt6ykq7n78` (`PRODUCT_MARKETING_ID`),
  CONSTRAINT `FK_i1xh03upkoiamagxt6ykq7n78` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`),
  CONSTRAINT `FK_rgqe14d5qrapcm839ya1n76wd` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_m50p0yicb7fi9pldynqe0cijo` (`CUSTOMER_ID`),
  KEY `FK_ae2rmxqxb0q5akle12bhysdf5` (`PRODUCT_MARKETING_CUSTOMER_COMMENT_ID`),
  CONSTRAINT `FK_ae2rmxqxb0q5akle12bhysdf5` FOREIGN KEY (`PRODUCT_MARKETING_CUSTOMER_COMMENT_ID`) REFERENCES `teco_product_marketing_customer_comment` (`ID`),
  CONSTRAINT `FK_m50p0yicb7fi9pldynqe0cijo` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_marketing_familly`
--

DROP TABLE IF EXISTS `teco_product_marketing_familly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_marketing_familly` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_7s5fjuro86j0x6pj78dpf8caa` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_7bhakjmbwu8hvgqfplivn1yit` (`PRODUCT_SKU_OPTION_ID`),
  KEY `FK_ki3inv6kpiwqjaq13qrsipmfa` (`PRODUCT_MARKETING_FAMILLY_ID`),
  CONSTRAINT `FK_ki3inv6kpiwqjaq13qrsipmfa` FOREIGN KEY (`PRODUCT_MARKETING_FAMILLY_ID`) REFERENCES `teco_product_marketing_familly` (`ID`),
  CONSTRAINT `FK_7bhakjmbwu8hvgqfplivn1yit` FOREIGN KEY (`PRODUCT_SKU_OPTION_ID`) REFERENCES `teco_product_sku_option` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_r7xfoiqlpikewoqpip9rycl1w` (`TAX_ID`),
  KEY `FK_p9st11iyjyv2vofgbe1lwr0dc` (`PRODUCT_MARKETING_TYPE_FAMILLY_ID`),
  CONSTRAINT `FK_p9st11iyjyv2vofgbe1lwr0dc` FOREIGN KEY (`PRODUCT_MARKETING_TYPE_FAMILLY_ID`) REFERENCES `teco_product_marketing_familly` (`ID`),
  CONSTRAINT `FK_r7xfoiqlpikewoqpip9rycl1w` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_rouuopvjw2y7ualoy1q11t9hb` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `PRODUCT_MARKETING_FAMILLY_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_qwte4b3chh7euedo10fc8ttjw` (`CODE`),
  KEY `FK_2u1t4r0u8v5ew4l93ojjjrvnt` (`PRODUCT_MARKETING_FAMILLY_ID`),
  CONSTRAINT `FK_2u1t4r0u8v5ew4l93ojjjrvnt` FOREIGN KEY (`PRODUCT_MARKETING_FAMILLY_ID`) REFERENCES `teco_product_marketing_familly` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ejx6m1hgf8b94wamjxk818dht` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK_ejx6m1hgf8b94wamjxk818dht` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_kf7pv1gn604t7q40gd1pfms31` (`CODE`),
  KEY `FK_1avcxj38rfds2y8glubfph532` (`PRODUCT_MARKETING_ID`),
  CONSTRAINT `FK_1avcxj38rfds2y8glubfph532` FOREIGN KEY (`PRODUCT_MARKETING_ID`) REFERENCES `teco_product_marketing` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_3ukgpfcnghtjpi0u5sbk318e6` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_9487690qgoghswa2i0hb7bgcw` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK_9487690qgoghswa2i0hb7bgcw` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK_3ukgpfcnghtjpi0u5sbk318e6` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_option`
--

DROP TABLE IF EXISTS `teco_product_sku_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_option` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ORDERING` int(11) NOT NULL DEFAULT '0',
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_94sp02fxcu75p01qc8ajdc1gu` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_product_sku_option_definition`
--

DROP TABLE IF EXISTS `teco_product_sku_option_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_product_sku_option_definition` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  `PRODUCT_SKU_OPTION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_q2goxufq1dtyo2q2fcmxb6tvr` (`CODE`),
  KEY `FK_ni4vxov42h5k2r6oksxexp3mi` (`PRODUCT_SKU_OPTION_ID`),
  CONSTRAINT `FK_ni4vxov42h5k2r6oksxexp3mi` FOREIGN KEY (`PRODUCT_SKU_OPTION_ID`) REFERENCES `teco_product_sku_option` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_4nmf2n1cgkgm99gto5psoh1co` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_46adp0m68hrhbdpdjle7r2o1f` (`PRODUCT_SKU_OPTION_DEFINITION_ID`),
  CONSTRAINT `FK_46adp0m68hrhbdpdjle7r2o1f` FOREIGN KEY (`PRODUCT_SKU_OPTION_DEFINITION_ID`) REFERENCES `teco_product_sku_option_definition` (`ID`),
  CONSTRAINT `FK_4nmf2n1cgkgm99gto5psoh1co` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_bgboicm9up5jyt7fatu3smmnv` (`CURRENCY_ID`),
  KEY `FK_e52m0k44x4lq3afn5j5t88a7u` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK_e52m0k44x4lq3afn5j5t88a7u` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK_bgboicm9up5jyt7fatu3smmnv` FOREIGN KEY (`CURRENCY_ID`) REFERENCES `teco_currency_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_o7wh50bigscje6mv81a650jj` (`RETAILER_ID`),
  KEY `FK_ox0kw3m6ipwqp6aa8ivt16oqs` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK_ox0kw3m6ipwqp6aa8ivt16oqs` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`),
  CONSTRAINT `FK_o7wh50bigscje6mv81a650jj` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_SKU_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_1l2v1hqlbujreak5wvqoevg4l` (`PRODUCT_SKU_ID`),
  CONSTRAINT `FK_1l2v1hqlbujreak5wvqoevg4l` FOREIGN KEY (`PRODUCT_SKU_ID`) REFERENCES `teco_product_sku` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_2wsjy48fpeg0b5y7u45yjtq7b` (`CODE`),
  KEY `FK_1fb7tx5iss42g74bgcnl11wyl` (`WAREHOUSE_ID`),
  CONSTRAINT `FK_1fb7tx5iss42g74bgcnl11wyl` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `teco_warehouse` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_a19vy4wjrs6u7x990dq2gn3vj` (`RETAILER_ID`),
  CONSTRAINT `FK_a19vy4wjrs6u7x990dq2gn3vj` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_lgejahjv2plymevf6noo14eo1` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_kmgb67c192avdv8fcraktv9h3` (`RETAILER_ID`),
  CONSTRAINT `FK_kmgb67c192avdv8fcraktv9h3` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK_lgejahjv2plymevf6noo14eo1` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_q3i7abgmovgdhiou2o1kyu9kp` (`CUSTOMER_ID`),
  KEY `FK_38dj0todn59qxlmgrrshlppgj` (`RETAILER_CUSTOMER_COMMENT_ID`),
  KEY `FK_q4y6g21mnmc60eda8ikrfqo0t` (`RETAILER_ID`),
  CONSTRAINT `FK_q4y6g21mnmc60eda8ikrfqo0t` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK_38dj0todn59qxlmgrrshlppgj` FOREIGN KEY (`RETAILER_CUSTOMER_COMMENT_ID`) REFERENCES `teco_retailer_customer_comment` (`ID`),
  CONSTRAINT `FK_q3i7abgmovgdhiou2o1kyu9kp` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `teco_customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_8pb6ev9xuhcfvhaflfifb9n0y` (`RETAILER_ID`),
  CONSTRAINT `FK_8pb6ev9xuhcfvhaflfifb9n0y` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_4v0hrgla6ks021b8fcbnx9084` (`RETAILER_ID`),
  CONSTRAINT `FK_4v0hrgla6ks021b8fcbnx9084` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ms07jhe4eaocj9vjkukchwgtf` (`RETAILER_TAG_ID`),
  KEY `FK_5c37fxe25slu08uxsvqefm2oo` (`RETAILER_ID`),
  CONSTRAINT `FK_5c37fxe25slu08uxsvqefm2oo` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`),
  CONSTRAINT `FK_ms07jhe4eaocj9vjkukchwgtf` FOREIGN KEY (`RETAILER_TAG_ID`) REFERENCES `teco_retailer_tag` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_63w76vtphs4e78nbw0e6xw80f` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_stlc1capniu0t13usch371mte` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_tre0vn3qv27rb3endg1ku09fi` (`PERMISSION_ID`),
  KEY `FK_tiuwpenmbyx9i5qfc3bfy9m33` (`ROLE_ID`),
  CONSTRAINT `FK_tiuwpenmbyx9i5qfc3bfy9m33` FOREIGN KEY (`ROLE_ID`) REFERENCES `teco_role` (`ID`),
  CONSTRAINT `FK_tre0vn3qv27rb3endg1ku09fi` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `teco_permission` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_p9p0lu8vw1fhh6pe5ret09pv0` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_6wie29t8i194wd0rhm1alsa80` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK_6wie29t8i194wd0rhm1alsa80` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_q7r8lvglu96xvlxg1kktjpymn` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  PRIMARY KEY (`ID`),
  KEY `FK_jf8w98ga6u1k4ioj1xlalpyxf` (`ATTRIBUTE_DEFINITION_ID`),
  CONSTRAINT `FK_jf8w98ga6u1k4ioj1xlalpyxf` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_a3t539282rbih701cduyoesq9` (`RULE_REPOSITORY_ATTRIBUTE_ID`),
  KEY `FK_qkh6nbk24b3k69f22gc4jp2dw` (`RULE_REPOSITORY_ID`),
  CONSTRAINT `FK_qkh6nbk24b3k69f22gc4jp2dw` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_repository` (`ID`),
  CONSTRAINT `FK_a3t539282rbih701cduyoesq9` FOREIGN KEY (`RULE_REPOSITORY_ATTRIBUTE_ID`) REFERENCES `teco_rule_repository_attribute` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_qqxtrhi3r4frkss3s0p7w8vsq` (`RULE_REFERENTIAL_ID`),
  KEY `FK_hccyhi23dh1d1bs1nex4t74pj` (`RULE_REPOSITORY_ID`),
  CONSTRAINT `FK_hccyhi23dh1d1bs1nex4t74pj` FOREIGN KEY (`RULE_REPOSITORY_ID`) REFERENCES `teco_rule_repository` (`ID`),
  CONSTRAINT `FK_qqxtrhi3r4frkss3s0p7w8vsq` FOREIGN KEY (`RULE_REFERENTIAL_ID`) REFERENCES `teco_rule_referential` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  UNIQUE KEY `UK_gmnm3e49xll2v8uemmpdc6t8q` (`CODE`),
  KEY `FK_h3bot0d7v5xnubu5gtofvgdi4` (`RETAILER_ID`),
  CONSTRAINT `FK_h3bot0d7v5xnubu5gtofvgdi4` FOREIGN KEY (`RETAILER_ID`) REFERENCES `teco_retailer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_gy0h7rds91206sd3olhe0eo38` (`ATTRIBUTE_DEFINITION_ID`),
  KEY `FK_6mt5uahf6pnspaikcgyg2gpb2` (`STORE_ID`),
  CONSTRAINT `FK_6mt5uahf6pnspaikcgyg2gpb2` FOREIGN KEY (`STORE_ID`) REFERENCES `teco_store` (`ID`),
  CONSTRAINT `FK_gy0h7rds91206sd3olhe0eo38` FOREIGN KEY (`ATTRIBUTE_DEFINITION_ID`) REFERENCES `teco_attribute_definition` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  `TAX_TYPE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_e5r59i1lebxrjgsvljy4pndwb` (`CODE`),
  KEY `FK_t7t5vsnijc5r98bgdr71mgaqe` (`TAX_TYPE_ID`),
  CONSTRAINT `FK_t7t5vsnijc5r98bgdr71mgaqe` FOREIGN KEY (`TAX_TYPE_ID`) REFERENCES `teco_tax_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_ik94mj4mmtyu4p53ej944lpx2` (`TAX_ID`),
  CONSTRAINT `FK_ik94mj4mmtyu4p53ej944lpx2` FOREIGN KEY (`TAX_ID`) REFERENCES `teco_tax` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
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
  KEY `FK_8h3s2vjc6ydowxesngehvu1u2` (`TAX_COUNTRY_ID`),
  CONSTRAINT `FK_8h3s2vjc6ydowxesngehvu1u2` FOREIGN KEY (`TAX_COUNTRY_ID`) REFERENCES `teco_tax_country` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_tax_type`
--

DROP TABLE IF EXISTS `teco_tax_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_tax_type` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_f8ud9jwwilyhyvaxskbfcvrje` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teco_warehouse`
--

DROP TABLE IF EXISTS `teco_warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teco_warehouse` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DATE_CREATE` datetime DEFAULT NULL,
  `DATE_UPDATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RETAILER_ID` bigint(20) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_7makw8u6rofkhq443lfe5oe99` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-07 19:02:39
