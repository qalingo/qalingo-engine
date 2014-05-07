--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version 0.8.0)
--         http://www.apache.org/licenses/LICENSE-2.0
--
--                   Copyright (c) Hoteia, 2012-2014
-- http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
--
--

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


INSERT INTO teco_localization
(id, code, country, language, name, description)
 VALUES 
(1,  'en',    'en', 'en', 'English', 'Default english localization.'), 
(2,  'fr',    'fr', 'fr', 'French', 'Default french localization.'), 
(3,  'es',    'es', 'es', 'Spanish', 'Default spanish localization.'), 
(4,  'it',    'it', 'it', 'Italian', 'Default italian localization.'), 
(5,  'de',    'de', 'de', 'German', 'Default german localization.'), 
(6,  'nl',    'nl', 'nl', 'Dutch', 'Default dutch localization.'), 
(7,  'jp',    'jp', 'jp', 'Japan', 'Default japan localization.'), 
(8,  'pt',    'pt', 'pt', 'Portuguese', 'Default portuguese localization.'), 
(9,  'zh-cn', 'CN', 'zh', 'Chinese - China', 'Default chinese localization.'), 
(10, 'zh-hk', 'HK', 'zh', 'Chinese - Hong Kong', 'Chinese localization for Hong Kong.'), 
(11, 'zh-sg', 'SG', 'zh', 'Chinese - Singapore', 'Chinese localization for Singapore.'), 
(12, 'zh-tw', 'TW', 'zh', 'Chinese - Taiwan', 'Chinese localization for Taiwan.'),
(13, 'vi-VN', 'VN', 'vi', 'Vietnamese', 'Vietnamese localization for Vietnam.');

INSERT INTO tbo_group 
(id, name, description, code, version)
VALUES 
(10, 'GROUP_BO_ADMIN', NULL, 'GROUP_BO_ADMIN', 1),
(20, 'GROUP_BO_USER', NULL, 'GROUP_BO_USER', 1),
(30, 'GROUP_BO_RETAILER', NULL, 'GROUP_BO_RETAILER', 1);

INSERT INTO tbo_role 
(id, name, description, code, version)
VALUES 
(10, 'ROLE_BO_BUSINESS_ADMIN', NULL, 'ROLE_BO_BUSINESS_ADMIN', 1),
(12, 'ROLE_BO_BUSINESS_USER', NULL, 'ROLE_BO_BUSINESS_USER', 1),
(11, 'ROLE_BO_BUSINESS_RETAILER', NULL, 'ROLE_BO_BUSINESS_RETAILER', 1),
(20, 'ROLE_BO_REPORTING_ADMIN', NULL, 'ROLE_BO_REPORTING_ADMIN', 1),
(21, 'ROLE_BO_REPORTING_USER', NULL, 'ROLE_BO_REPORTING_USER', 1),
(30, 'ROLE_BO_TECHNICAL_ADMIN', NULL, 'ROLE_BO_TECHNICAL_ADMIN', 1),
(31, 'ROLE_BO_TECHNICAL_USER', NULL, 'ROLE_BO_TECHNICAL_USER', 1);

INSERT INTO tbo_group_role_rel VALUES 
(10, 10),
(10,20),
(10,30),
(20, 11),
(20,21),
(20,31),
(30,12);

-- ECO GLOBAL

-- ATTRIBUTES : CATALOG CATEGORY
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(1000, 'Catalog Category attribute name', 'Catalog Category attribute name', 'CATALOG_CATEGORY_I18N_NAME',                      1, 1, 1, 1, 0, 1, 1),
(1010, 'Catalog Category attribute description', 'Catalog Category attribute description', 'CATALOG_CATEGORY_I18N_DESCRIPTION', 2, 1, 1, 1, 0, 1, 2);

-- ATTRIBUTES : PRODUCT MARKETING
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(2000, 'Product Marketing: attribute name', 'Product Marketing: attribute name', 'PRODUCT_MARKETING_I18N_NAME',                      1, 2, 1, 1, 0, 1, 1),
(2010, 'Product Marketing: attribute description', 'Product Marketing: attribute description', 'PRODUCT_MARKETING_I18N_DESCRIPTION', 2, 2, 1, 1, 0, 1, 2),
(2020, 'Product Marketing: featured product', 'Product Marketing: is featured product', 'PRODUCT_MARKETING_FEATURED',                7, 2, 0, 1, 0, 1, 3);

-- ATTRIBUTES : PRODUCT SKU
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(3000, 'Product Sku: attribute name', 'Product Sku: attribute name', 'PRODUCT_SKU_I18N_NAME',                      1, 3, 1, 1, 0, 1, 1),
(3010, 'Product Sku: attribute description', 'Product Sku: attribute description', 'PRODUCT_SKU_I18N_DESCRIPTION', 2, 3, 1, 1, 0, 1, 2),
(3020, 'Product Sku: attribute width', 'Product Sku: attribute width', 'PRODUCT_SKU_WIDTH',                        5, 3, 0, 1, 0, 1, 3),
(3030, 'Product Sku: attribute height', 'Product Sku: attribute height', 'PRODUCT_SKU_HEIGHT',                     5, 3, 0, 1, 0, 1, 4),
(3040, 'Product Sku: attribute length', 'Product Sku: attribute length', 'PRODUCT_SKU_LENGTH',                     5, 3, 0, 1, 0, 1, 5),
(3050, 'Product Sku: attribute weight', 'Product Sku: attribute weight', 'PRODUCT_SKU_WEIGHT',                     5, 3, 0, 1, 0, 1, 6),
(3060, 'Product Sku: attribute salable', 'Product Sku: attribute salable', 'PRODUCT_SKU_IS_SALABLE',               7, 3, 0, 0, 0, 1, 7);

-- ATTRIBUTES : CUSTOMER
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(4000, 'Customer attribute screen name', 'Customer attribute screen name', 'CUSTOMER_SCREENNAME', 1, 4, 0, 1, 0, 1, 1);

-- ATTRIBUTES : STORE
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(5000, 'Store attribute name', 'Store attribute name', 'STORE_I18N_NAME',                      1, 5, 1, 1, 0, 1, 1),
(5010, 'Store attribute description', 'Store attribute description', 'STORE_I18N_DESCRIPTION', 2, 5, 1, 1, 0, 1, 2),
(5020, 'Store attribute city name', 'Store attribute city name', 'STORE_I18N_CITY_NAME',       1, 5, 1, 1, 0, 1, 3);

-- ATTRIBUTES : RETAILER
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(10000, 'Retailer attribute name', 'Retailer attribute name', 'RETAILER_I18N_NAME',                      1, 10, 1, 1, 0, 1, 1),
(10010, 'Retailer attribute description', 'Retailer attribute description', 'RETAILER_I18N_DESCRIPTION', 2, 10, 1, 1, 0, 1, 1);

-- ATTRIBUTES : PAYMENT GATEWAY
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version, ordering)
VALUES 
(6000, 'Payment Gateway client id', 'Payment Gateway client id', 'PAYMENT_GATEWAY_CLIENT_TOKEN', 1, 6, 1, 0, 0, 1, 1);

-- ATTRIBUTES : MARKET AREA
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version)
VALUES 
(8000, 'Market Place Domaine name', 'Market Place Domaine name', 'MARKET_PLACE_DOMAIN_NAME', 1, 8, 0, 0, 0, 1),
(8001, 'Market Domaine name', 'Market Domaine name', 'MARKET_DOMAIN_NAME', 1, 8, 0, 0, 0, 1),
(1, 'Market Area Email From Address (Generic)', 'Market Area Email From Address (Generic)', 'MARKET_AREA_EMAIL_FROM_ADDRESS', 1, 8, 0, 0, 0, 1),
(2, 'Market Area Email From Name (Generic)', 'Market Area Email From Name(Generic)', 'MARKET_AREA_EMAIL_FROM_NAME', 1, 8, 0, 0, 0, 1),
(3, 'Market Area Email Contact (To)', 'Market Area Email Contact (To)', 'MARKET_AREA_EMAIL_CONTACT', 1, 8, 0, 0, 0, 1),
(5, 'Market Area Domaine name', 'Market Area Domaine name', 'MARKET_AREA_DOMAIN_NAME', 1, 8, 0, 0, 0, 1),
(6, 'Market Area Share Option', 'Market Area Share Option', 'MARKET_AREA_SHARE_OPTIONS', 1, 8, 0, 0, 0, 1),
(7, 'Market Area Save Payment informations', 'Market Area Save Payment informations', 'MARKET_AREA_SAVE_PAYMENT_INFORMATION', 7, 8, 0, 0, 0, 1),
(8, 'Market Area Order confirmation template file path', 'Market Area Order confirmation template file path', 'MARKET_AREA_ORDER_CONFIRMATION_TEMPLATE', 1, 8, 0, 0, 0, 1),
(9, 'Market Area Shipping confirmation template file path', 'Market Area Shipping confirmation template file path', 'MARKET_AREA_SHIPPING_CONFIRMATION_TEMPLATE', 1, 8, 0, 0, 0, 1),
(10, 'Market Area Invoice template file path', 'Market Area Invoice template file path', 'MARKET_AREA_INVOICE_TEMPLATE', 1, 8, 0, 0, 0, 1);

-- ATTRIBUTES : TAX
INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, with_planner, version)
VALUES 
(9000, 'Tax alias name', 'Tax alias name', 'TAX_ALIAS_NAME', 1, 9, 1, 1, 0, 1);

-- CORE ENGINE SETTINGS
-- Environment Staging/Reel setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(10, 'Environment Staging Mode enabled setting', 'Environment Staging Mode enabled setting', 'ENVIRONMENT_STAGING_MODE_ENABLED', 'false', 1),
(11, 'Environment Type setting', 'Environment Type setting', 'ENVIRONMENT_TYPE', 'REEL', 1);

-- Geoloc database folder path setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(201, 'Geoloc city database folder path', 'Geoloc city database folder path', 'CITY_DATABASE_PATH', '/home/tomcat/servers/datas/maxmind/GeoLite2-City.mmdb', 1),
(202, 'Geoloc country database folder path', 'Geoloc country database folder path', 'COUNTRY_DATABASE_PATH', '/home/tomcat/servers/datas/maxmind/GeoLite2-Country.mmdb', 1);

-- Document folder path setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(301, 'Document folder path', 'Document folder path', 'DOCUMENT_FILE_FOLDER_PATH', '/home/tomcat/qalingo/datas/documents', 1);

-- Document web path setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(302, 'Document web path', 'Document web path', 'DOCUMENT_FILE_WEB_PATH', '/documents', 1);

-- Default Order Confirmation template setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(401, 'Default Order Confirmation template setting', 'Default Order Confirmation template setting', 'DEFAULT_ORDER_CONFIRMATION_TEMPLATE', '/home/tomcat/qalingo/datas/template/default/order-confirmation/order-confirmation.jrxml', 1);

-- Default shipping Confirmation template setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(402, 'Default shipping Confirmation template setting', 'Default shipping Confirmation template setting', 'DEFAULT_SHIPPING_CONFIRMATION_TEMPLATE', '/home/tomcat/qalingo/datas/template/default/shipping-confirmation/shipping-confirmation.jrxml', 1);

-- Default Invoice template setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(403, 'Default Invoice template setting', 'Default Invoice template setting', 'DEFAULT_INVOICE_TEMPLATE', '/home/tomcat/qalingo/datas/template/default/invoice/invoice.jrxml', 1);

-- Save Email file mirroring setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(500, 'Email file mirroring activated', 'Email file mirroring', 'EMAIL_FILE_MIRRORING_ACTIVATED', 'false', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(500, 'NEWSLETTER_SUBSCRIPTION', 'true', 500);

-- Save Email file mirroring setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(501, 'Email file mirroring folder path', 'Email file mirroring folder path', 'EMAIL_FILE_MIRRORING_FOLDER_PATH', '/home/tomcat/qalingo/datas/emails/', 1);

-- Save Email file mirroring setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(502, 'Email file mirroring web path', 'Email file mirroring web path', 'EMAIL_FILE_MIRRORING_WEB_PATH', '/emails/', 1);

-- Save Email file mirroring setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(503, 'Email file mirroring extension', 'Email file mirroring extension', 'EMAIL_FILE_MIRRORING_EXTENSION', '.html', 1);

-- Cache TTL setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1000, 'Cache TTL setting', 'Cache TTL settings', 'WEB_CACHE_ELEMENT_TIME_TO_LIVE', '3600', 1);

-- SEO setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1005, 'Escape accent URL setting', 'Escape accent URL settings', 'ESCAPE_ACCENT_FROM_URL', 'true', 1);


-- Theme settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1010, 'Setting resource theme path', 'Setting resource theme path', 'THEME_RESOURCE_PREFIX_PATH', '', 1),
(1011, 'Setting resource assets root file path', 'Setting resource assets catalog root path', 'ASSET_FILE_ROOT_PATH', '/datas/assets', 1),
(1012, 'Setting resource assets root web path', 'Setting resource assets root web path', 'ASSET_WEB_ROOT_PATH', '/assets', 1),
(1013, 'Setting resource assets catalog file path', 'Setting resource assets catalog file path', 'ASSET_CATALOG_FILE_PATH', '/images/catalog/category', 1),
(1014, 'Setting resource assets product marketing file path', 'Setting resource assets product marketing file path', 'ASSET_PRODUCT_MARKETING_FILE_PATH', '/images/catalog/product-marketing', 1),
(1015, 'Setting resource assets product sku file path', 'Setting resource assets product sku file path', 'ASSET_PROPDUCT_SKU_FILE_PATH', '/images/catalog/product-sku', 1),
(1016, 'Setting resource assets retailer & store file path', 'Setting resource assets retailer & store file path', 'ASSET_RETAILER_STORE_FILE_PATH', '/images/retailer-store', 1);


-- Tracking/Monitoring settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(2010, 'Setting tracking number', 'Setting tracking number', 'WEB_TRACKING_NUMBER', '', 1),
(2011, 'Setting tracking name', 'Setting tracking name', 'WEB_TRACKING_NAME', '', 1),
(2020, 'Setting monitoring number', 'Setting monitoring number', 'WEB_MONITORING_NUMBER', '', 1),
(2021, 'Setting monitoring name', 'Setting monitoring name', 'WEB_MONITORING_NAME', '', 1);


INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1010, 'BO_BUSINESS',  '/bo-business-resources/', 1010),
(1011, 'BO_REPORTING', '/bo-reporting-resources/', 1010),
(1012, 'BO_TECHNICAL', '/bo-technical-resources/', 1010),
(1013, 'FO_MCOMMERCE', '/fo-mcommerce-resources/', 1010),
(1014, 'FO_PREHOME',   '/fo-prehome-resources/', 1010);

-- Spring-batch setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1020, 'Spring-batch webapp Url', 'Spring-batch webapp Url', 'SPRING_BATCH_URL', '', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1020, 'CMS',  'http://app-cms-sync.demo.qalingo.com/', 1020),
(1021, 'CRM', 'http://app-crm-sync.demo.qalingo.com/', 1020),
(1022, 'ERP', 'http://app-erp-sync.demo.qalingo.com/', 1020),
(1023, 'NOTIFICATION', 'http://app-notification.demo.qalingo.com/', 1020);

-- SOLR setting
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1030, 'SOLR webapp Url', 'SOLR webapp Url', 'SOLR', '', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1030, 'MASTER',  'http://176.31.248.152:14080/solr', 1030);

-- Catalog resource setting like images
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1040, 'Catalog resource theme path', 'Catalog resource theme path', 'CATALOG_RESOURCE_PREFIX_PATH', '/fo-mcommerce-resources/', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1040, 'INT', '/catalog-resources/', 1040);

-- Page size settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1050, 'Setting items by list ', 'Setting items by list', 'COUNT_ITEM_BY_PAGE', '15', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1050, 'BO_TECHNICAL_ENGINE_SETTING_LIST',  '15', 1050),
(1051, 'BO_TECHNICAL_USER_LIST',  '15', 1050),
(1052, 'BO_TECHNICAL_CACHE_LIST',  '15', 1050),
(1053, 'BO_TECHNICAL_BATCH_LIST',  '15', 1050);

-- MarketArea support checkout as guest settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1060, 'Setting MarketArea support checkout as guest', 'Setting MarketArea support checkout as guest', 'MARKETAREA_SUPPORT_CHECKOUT_AS_GUEST', 'true', 1);

-- USER ENGINE SETTINGS

-- User max connection log settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(2000, 'Setting max user connection log', 'Setting max user connection log', 'MAX_USER_CONNECTION_LOG', '20', 1);

-- CUSTOMER ENGINE SETTINGS

-- Customer max connection log settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(3000, 'Setting max customer connection log', 'Setting max customer connection log', 'MAX_CUSTOMER_CONNECTION_LOG', '10', 1);

-- Customer max addresses settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(3010, 'Setting max customer addresses', 'Setting max customer addresses', 'MAX_CUSTOMER_ADDRESSES', '5', 1);

INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(10000, 'Setting Oauth App Key or Id', 'Setting Oauth App Key or Id', 'OAUTH_APP_KEY_OR_ID', '', 1),
(10010, 'Setting Oauth App Secret', 'Setting Oauth App Secret', 'OAUTH_APP_SECRET', '', 1),
(10020, 'Setting Oauth App Permissions', 'Setting Oauth App Permissions', 'OAUTH_APP_PERMISSIONS', '', 1);

-- CURRENCIES
INSERT INTO teco_currency_referential  
(id, code, description, abbreviated, name, sign, format_locale) 
VALUES 
(2, 'AED', 'United Arab Emirates Dirham', 'AED', 'United Arab Emirates Dirham', 'AED','en_US'),
(3, 'AFN', 'Afghanistan Afghani', 'AFN', 'Afghanistan Afghani', 'AFN','en_US'),
(4, 'ALL', 'Albania Lek', 'ALL', 'Albania Lek', 'ALL','en_US'),
(5, 'AMD', 'Armenia Dram', 'AMD', 'Armenia Dram', 'AMD','en_US'),
(6, 'ANG', 'Netherlands Antilles Guilder', 'ANG', 'Netherlands Antilles Guilder', 'ANG','en_US'),
(7, 'AOA', 'Angola Kwanza', 'AOA', 'Angola Kwanza', 'AOA','en_US'),
(8, 'ARS', 'Argentina Peso', 'ARS', 'Argentina Peso', 'ARS','en_US'),
(9, 'AUD', 'Australia Dollar', 'AUD', 'Australia Dollar', 'AUD','en_US'),
(10, 'AWG', 'Aruba Guilder', 'AWG', 'Aruba Guilder', 'AWG','en_US'),
(11, 'AZN', 'Azerbaijan New Manat', 'AZN', 'Azerbaijan New Manat', 'AZN','en_US'),
(12, 'BAM', 'Bosnia and Herzegovina Convertible Marka', 'BAM', 'Bosnia and Herzegovina Convertible Marka', 'BAM','en_US'),
(13, 'BBD', 'Barbados Dollar', 'BBD', 'Barbados Dollar', 'BBD','en_US'),
(14, 'BDT', 'Bangladesh Taka', 'BDT', 'Bangladesh Taka', 'BDT','en_US'),
(15, 'BGN', 'Bulgaria Lev', 'BGN', 'Bulgaria Lev', 'BGN','en_US'),
(16, 'BHD', 'Bahrain Dinar', 'BHD', 'Bahrain Dinar', 'BHD','en_US'),
(17, 'BIF', 'Burundi Franc', 'BIF', 'Burundi Franc', 'BIF','en_US'),
(18, 'BMD', 'Bermuda Dollar', 'BMD', 'Bermuda Dollar', 'BMD','en_US'),
(19, 'BND', 'Brunei Darussalam Dollar', 'BND', 'Brunei Darussalam Dollar', 'BND','en_US'),
(20, 'BOB', 'Bolivia Boliviano', 'BOB', 'Bolivia Boliviano', 'BOB','en_US'),
(21, 'BRL', 'Brazil Real', 'BRL', 'Brazil Real', 'BRL','en_US'),
(22, 'BSD', 'Bahamas Dollar', 'BSD', 'Bahamas Dollar', 'BSD','en_US'),
(23, 'BTN', 'Bhutan Ngultrum', 'BTN', 'Bhutan Ngultrum', 'BTN','en_US'),
(24, 'BWP', 'Botswana Pula', 'BWP', 'Botswana Pula', 'BWP','en_US'),
(25, 'BYR', 'Belarus Ruble', 'BYR', 'Belarus Ruble', 'BYR','en_US'),
(26, 'BZD', 'Belize Dollar', 'BZD', 'Belize Dollar', 'BZD','en_US'),
(27, 'CAD', 'Canada Dollar', 'CAD', 'Canada Dollar', 'CAD','ca_CA'),
(28, 'CDF', 'Congo/Kinshasa Franc', 'CDF', 'Congo/Kinshasa Franc', 'CDF','en_US'),
(29, 'CHF', 'Switzerland Franc', 'CHF', 'Switzerland Franc', 'CHF','en_US'),
(30, 'CLP', 'Chile Peso', 'CLP', 'Chile Peso', 'CLP','en_US'),
(31, 'CNY', 'China Yuan Renminbi', 'CNY', 'China Yuan Renminbi', 'Y','zh_CN'),
(32, 'COP', 'Colombia Peso', 'COP', 'Colombia Peso', 'COP','en_US'),
(33, 'CRC', 'Costa Rica Colon', 'CRC', 'Costa Rica Colon', 'CRC','en_US'),
(34, 'CUC', 'Cuba Convertible Peso', 'CUC', 'Cuba Convertible Peso', 'CUC','en_US'),
(35, 'CUP', 'Cuba Peso', 'CUP', 'Cuba Peso', 'CUP','en_US'),
(36, 'CVE', 'Cape Verde Escudo', 'CVE', 'Cape Verde Escudo', 'CVE','en_US'),
(37, 'CZK', 'Czech Republic Koruna', 'CZK', 'Czech Republic Koruna', 'CZK','en_US'),
(38, 'DJF', 'Djibouti Franc', 'DJF', 'Djibouti Franc', 'DJF','en_US'),
(39, 'DKK', 'Denmark Krone', 'DKK', 'Denmark Krone', 'DKK','en_US'),
(40, 'DOP', 'Dominican Republic Peso', 'DOP', 'Dominican Republic Peso', 'DOP','en_US'),
(41, 'DZD', 'Algeria Dinar', 'DZD', 'Algeria Dinar', 'DZD','en_US'),
(42, 'EGP', 'Egypt Pound', 'EGP', 'Egypt Pound', 'EGP','en_US'),
(43, 'ERN', 'Eritrea Nakfa', 'ERN', 'Eritrea Nakfa', 'ERN','en_US'),
(44, 'ETB', 'Ethiopia Birr', 'ETB', 'Ethiopia Birr', 'ETB','en_US'),
(45, 'EUR', 'Euro Member Countries', 'EUR', 'Euro', 'E','fr_FR'),
(46, 'FJD', 'Fiji Dollar', 'FJD', 'Fiji Dollar', 'FJD','en_US'),
(47, 'FKP', 'Falkland Islands (Malvinas) Pound', 'FKP', 'Falkland Islands (Malvinas) Pound', 'FKP','en_US'),
(48, 'GBP', 'United Kingdom Pound', 'GBP', 'United Kingdom Pound', '£','en_GB'),
(49, 'GEL', 'Georgia Lari', 'GEL', 'Georgia Lari', 'GEL','en_US'),
(50, 'GGP', 'Guernsey Pound', 'GGP', 'Guernsey Pound', 'GGP','en_US'),
(51, 'GHS', 'Ghana Cedi', 'GHS', 'Ghana Cedi', 'GHS','en_US'),
(52, 'GIP', 'Gibraltar Pound', 'GIP', 'Gibraltar Pound', 'GIP','en_US'),
(53, 'GMD', 'Gambia Dalasi', 'GMD', 'Gambia Dalasi', 'GMD','en_US'),
(54, 'GNF', 'Guinea Franc', 'GNF', 'Guinea Franc', 'GNF','en_US'),
(55, 'GTQ', 'Guatemala Quetzal', 'GTQ', 'Guatemala Quetzal', 'GTQ','en_US'),
(56, 'GYD', 'Guyana Dollar', 'GYD', 'Guyana Dollar', 'GYD','en_US'),
(57, 'HKD', 'Hong Kong Dollar', 'HKD', 'Hong Kong Dollar', 'HKD','en_US'),
(58, 'HNL', 'Honduras Lempira', 'HNL', 'Honduras Lempira', 'HNL','en_US'),
(59, 'HRK', 'Croatia Kuna', 'HRK', 'Croatia Kuna', 'HRK','en_US'),
(60, 'HTG', 'Haiti Gourde', 'HTG', 'Haiti Gourde', 'HTG','en_US'),
(61, 'HUF', 'Hungary Forint', 'HUF', 'Hungary Forint', 'HUF','en_US'),
(62, 'IDR', 'Indonesia Rupiah', 'IDR', 'Indonesia Rupiah', 'IDR','en_US'),
(63, 'ILS', 'Israel Shekel', 'ILS', 'Israel Shekel', 'ILS','en_US'),
(64, 'IMP', 'Isle of Man Pound', 'IMP', 'Isle of Man Pound', 'IMP','en_US'),
(65, 'INR', 'India Rupee', 'INR', 'India Rupee', 'INR','en_US'),
(66, 'IQD', 'Iraq Dinar', 'IQD', 'Iraq Dinar', 'IQD','en_US'),
(67, 'IRR', 'Iran Rial', 'IRR', 'Iran Rial', 'IRR','en_US'),
(68, 'ISK', 'Iceland Krona', 'ISK', 'Iceland Krona', 'ISK','en_US'),
(69, 'JEP', 'Jersey Pound', 'JEP', 'Jersey Pound', 'JEP','en_US'),
(70, 'JMD', 'Jamaica Dollar', 'JMD', 'Jamaica Dollar', 'JMD','en_US'),
(71, 'JOD', 'Jordan Dinar', 'JOD', 'Jordan Dinar', 'JOD','en_US'),
(72, 'JPY', 'Japan Yen', 'JPY', 'Japan Yen', 'Yi','jp_JP'),
(73, 'KES', 'Kenya Shilling', 'KES', 'Kenya Shilling', 'KES','en_US'),
(74, 'KGS', 'Kyrgyzstan Som', 'KGS', 'Kyrgyzstan Som', 'KGS','en_US'),
(75, 'KHR', 'Cambodia Riel', 'KHR', 'Cambodia Riel', 'KHR','en_US'),
(76, 'KMF', 'Comoros Franc', 'KMF', 'Comoros Franc', 'KMF','en_US'),
(77, 'KPW', 'Korea (North) Won', 'KPW', 'Korea (North) Won', 'KPW','en_US'),
(78, 'KRW', 'Korea (South) Won', 'KRW', 'Korea (South) Won', 'KRW','en_US'),
(79, 'KWD', 'Kuwait Dinar', 'KWD', 'Kuwait Dinar', 'KWD','en_US'),
(80, 'KYD', 'Cayman Islands Dollar', 'KYD', 'Cayman Islands Dollar', 'KYD','en_US'),
(81, 'KZT', 'Kazakhstan Tenge', 'KZT', 'Kazakhstan Tenge', 'KZT','en_US'),
(82, 'LAK', 'Laos Kip', 'LAK', 'Laos Kip', 'LAK','en_US'),
(83, 'LBP', 'Lebanon Pound', 'LBP', 'Lebanon Pound', 'LBP','en_US'),
(84, 'LKR', 'Sri Lanka Rupee', 'LKR', 'Sri Lanka Rupee', 'LKR','en_US'),
(85, 'LRD', 'Liberia Dollar', 'LRD', 'Liberia Dollar', 'LRD','en_US'),
(86, 'LSL', 'Lesotho Loti', 'LSL', 'Lesotho Loti', 'LSL','en_US'),
(87, 'LTL', 'Lithuania Litas', 'LTL', 'Lithuania Litas', 'LTL','en_US'),
(88, 'LVL', 'Latvia Lat', 'LVL', 'Latvia Lat', 'LVL','en_US'),
(89, 'LYD', 'Libya Dinar', 'LYD', 'Libya Dinar', 'LYD','en_US'),
(90, 'MAD', 'Morocco Dirham', 'MAD', 'Morocco Dirham', 'MAD','en_US'),
(91, 'MDL', 'Moldova Leu', 'MDL', 'Moldova Leu', 'MDL','en_US'),
(92, 'MGA', 'Madagascar Ariary', 'MGA', 'Madagascar Ariary', 'MGA','en_US'),
(93, 'MKD', 'Macedonia Denar', 'MKD', 'Macedonia Denar', 'MKD','en_US'),
(94, 'MMK', 'Myanmar (Burma) Kyat', 'MMK', 'Myanmar (Burma) Kyat', 'MMK','en_US'),
(95, 'MNT', 'Mongolia Tughrik', 'MNT', 'Mongolia Tughrik', 'MNT','en_US'),
(96, 'MOP', 'Macau Pataca', 'MOP', 'Macau Pataca', 'MOP','en_US'),
(97, 'MRO', 'Mauritania Ouguiya', 'MRO', 'Mauritania Ouguiya', 'MRO','en_US'),
(98, 'MUR', 'Mauritius Rupee', 'MUR', 'Mauritius Rupee', 'MUR','en_US'),
(99, 'MVR', 'Maldives (Maldive Islands) Rufiyaa', 'MVR', 'Maldives (Maldive Islands) Rufiyaa', 'MVR','en_US'),
(100, 'MWK', 'Malawi Kwacha', 'MWK', 'Malawi Kwacha', 'MWK','en_US'),
(101, 'MXN', 'Mexico Peso', 'MXN', 'Mexico Peso', 'MXN','en_US'),
(102, 'MYR', 'Malaysia Ringgit', 'MYR', 'Malaysia Ringgit', 'MYR','en_US'),
(103, 'MZN', 'Mozambique Metical', 'MZN', 'Mozambique Metical', 'MZN','en_US'),
(104, 'NAD', 'Namibia Dollar', 'NAD', 'Namibia Dollar', 'NAD','en_US'),
(105, 'NGN', 'Nigeria Naira', 'NGN', 'Nigeria Naira', 'NGN','en_US'),
(106, 'NIO', 'Nicaragua Cordoba', 'NIO', 'Nicaragua Cordoba', 'NIO','en_US'),
(107, 'NOK', 'Norway Krone', 'NOK', 'Norway Krone', 'NOK','en_US'),
(108, 'NPR', 'Nepal Rupee', 'NPR', 'Nepal Rupee', 'NPR','en_US'),
(109, 'NZD', 'New Zealand Dollar', 'NZD', 'New Zealand Dollar', 'NZD','en_US'),
(110, 'OMR', 'Oman Rial', 'OMR', 'Oman Rial', 'OMR','en_US'),
(111, 'PAB', 'Panama Balboa', 'PAB', 'Panama Balboa', 'PAB','en_US'),
(112, 'PEN', 'Peru Nuevo Sol', 'PEN', 'Peru Nuevo Sol', 'PEN','en_US'),
(113, 'PGK', 'Papua New Guinea Kina', 'PGK', 'Papua New Guinea Kina', 'PGK','en_US'),
(114, 'PHP', 'Philippines Peso', 'PHP', 'Philippines Peso', 'PHP','en_US'),
(115, 'PKR', 'Pakistan Rupee', 'PKR', 'Pakistan Rupee', 'PKR','en_US'),
(116, 'PLN', 'Poland Zloty', 'PLN', 'Poland Zloty', 'PLN','en_US'),
(117, 'PYG', 'Paraguay Guarani', 'PYG', 'Paraguay Guarani', 'PYG','en_US'),
(118, 'QAR', 'Qatar Riyal', 'QAR', 'Qatar Riyal', 'QAR','en_US'),
(119, 'RON', 'Romania New Leu', 'RON', 'Romania New Leu', 'RON','en_US'),
(120, 'RSD', 'Serbia Dinar', 'RSD', 'Serbia Dinar', 'RSD','en_US'),
(121, 'RUB', 'Russia Ruble', 'RUB', 'Russia Ruble', 'RUB','en_US'),
(122, 'RWF', 'Rwanda Franc', 'RWF', 'Rwanda Franc', 'RWF','en_US'),
(123, 'SAR', 'Saudi Arabia Riyal', 'SAR', 'Saudi Arabia Riyal', 'SAR','en_US'),
(124, 'SBD', 'Solomon Islands Dollar', 'SBD', 'Solomon Islands Dollar', 'SBD','en_US'),
(125, 'SCR', 'Seychelles Rupee', 'SCR', 'Seychelles Rupee', 'SCR','en_US'),
(126, 'SDG', 'Sudan Pound', 'SDG', 'Sudan Pound', 'SDG','en_US'),
(127, 'SEK', 'Sweden Krona', 'SEK', 'Sweden Krona', 'SEK','en_US'),
(128, 'SGD', 'Singapore Dollar', 'SGD', 'Singapore Dollar', 'SGD','en_US'),
(129, 'SHP', 'Saint Helena Pound', 'SHP', 'Saint Helena Pound', 'SHP','en_US'),
(130, 'SLL', 'Sierra Leone Leone', 'SLL', 'Sierra Leone Leone', 'SLL','en_US'),
(131, 'SOS', 'Somalia Shilling', 'SOS', 'Somalia Shilling', 'SOS','en_US'),
(132, 'SPL*', 'Seborga Luigino', 'SPL*', 'Seborga Luigino', 'SPL*','en_US'),
(133, 'SRD', 'Suriname Dollar', 'SRD', 'Suriname Dollar', 'SRD','en_US'),
(134, 'STD', 'São Tomé and Príncipe Dobra', 'STD', 'São Tomé and Príncipe Dobra', 'STD','en_US'),
(135, 'SVC', 'El Salvador Colon', 'SVC', 'El Salvador Colon', 'SVC','en_US'),
(136, 'SYP', 'Syria Pound', 'SYP', 'Syria Pound', 'SYP','en_US'),
(137, 'SZL', 'Swaziland Lilangeni', 'SZL', 'Swaziland Lilangeni', 'SZL','en_US'),
(138, 'THB', 'Thailand Baht', 'THB', 'Thailand Baht', 'THB','en_US'),
(139, 'TJS', 'Tajikistan Somoni', 'TJS', 'Tajikistan Somoni', 'TJS','en_US'),
(140, 'TMT', 'Turkmenistan Manat', 'TMT', 'Turkmenistan Manat', 'TMT','en_US'),
(141, 'TND', 'Tunisia Dinar', 'TND', 'Tunisia Dinar', 'TND','en_US'),
(142, 'TOP', 'Tonga Pa''anga', 'TOP', 'Tonga Pa''anga', 'TOP','en_US'),
(143, 'TRY', 'Turkey Lira', 'TRY', 'Turkey Lira', 'TRY','en_US'),
(144, 'TTD', 'Trinidad and Tobago Dollar', 'TTD', 'Trinidad and Tobago Dollar', 'TTD','en_US'),
(145, 'TVD', 'Tuvalu Dollar', 'TVD', 'Tuvalu Dollar', 'TVD','en_US'),
(146, 'TWD', 'Taiwan New Dollar', 'TWD', 'Taiwan New Dollar', 'TWD','en_US'),
(147, 'TZS', 'Tanzania Shilling', 'TZS', 'Tanzania Shilling', 'TZS','en_US'),
(148, 'UAH', 'Ukraine Hryvna', 'UAH', 'Ukraine Hryvna', 'UAH','en_US'),
(149, 'UGX', 'Uganda Shilling', 'UGX', 'Uganda Shilling', 'UGX','en_US'),
(150, 'USD', 'United States Dollar', 'USD', 'Dollar', '$','en_US'),
(151, 'UYU', 'Uruguay Peso', 'UYU', 'Uruguay Peso', 'UYU','en_US'),
(152, 'UZS', 'Uzbekistan Som', 'UZS', 'Uzbekistan Som', 'UZS','en_US'),
(153, 'VEF', 'Venezuela Bolivar', 'VEF', 'Venezuela Bolivar', 'VEF','en_US'),
(154, 'VND', 'Viet Nam Dong', 'VND', 'Viet Nam Dong', 'VND','en_US'),
(155, 'VUV', 'Vanuatu Vatu', 'VUV', 'Vanuatu Vatu', 'VUV','en_US'),
(156, 'WST', 'Samoa Tala', 'WST', 'Samoa Tala', 'WST','en_US'),
(157, 'XAF', 'Communauté Financière Africaine (BEAC) CFA Franc BEAC', 'XAF', 'Communauté Financière Africaine (BEAC) CFA Franc BEAC', 'XAF','en_US'),
(158, 'XCD', 'East Caribbean Dollar', 'XCD', 'East Caribbean Dollar', 'XCD','en_US'),
(159, 'XDR', 'International Monetary Fund (IMF) Special Drawing Rights', 'XDR', 'International Monetary Fund (IMF) Special Drawing Rights', 'XDR','en_US'),
(160, 'XOF', 'Communauté Financière Africaine (BCEAO) Franc', 'XOF', 'Communauté Financière Africaine (BCEAO) Franc', 'XOF','en_US'),
(161, 'XPF', 'Comptoirs Français du Pacifique (CFP) Franc', 'XPF', 'Comptoirs Français du Pacifique (CFP) Franc', 'XPF','en_US'),
(162, 'YER', 'Yemen Rial', 'YER', 'Yemen Rial', 'YER','en_US'),
(163, 'ZAR', 'South Africa Rand', 'ZAR', 'South Africa Rand', 'ZAR','en_US'),
(164, 'ZMW', 'Zambia Kwacha', 'ZMW', 'Zambia Kwacha', 'ZMW','en_US'),
(165, 'ZWD', 'Zimbabwe Dollar', 'ZWD', 'Zimbabwe Dollar', 'ZWD','en_US');


-- TAXES
-- http://www.vatlive.com/vat-rates/european-vat-rates/eu-vat-rates/
-- http://ec.europa.eu/taxation_customs/resources/documents/taxation/vat/how_vat_works/rates/vat_rates_fr.pdf
INSERT INTO teco_tax 
(id, code, name, description, percent) 
VALUES 
(10, 'FRENCH_VAT_STANDARD ', 'TVA Française, taux normal', 'La taxe sur la valeur ajoutée (TVA) a un taux normal fixé à 20 % (art. 278 du code général des impôts), pour la majorité des ventes de biens et des prestations de services : il s''applique à tous les produits ou services pour lesquels aucun autre taux n''est expressément prévu.', 20.00),
(20, 'FRENCH_VAT_INTERMEDIATE', 'TVA Française, taux intermédiaire', 'La taxe sur la valeur ajoutée (TVA) a un taux intermédiaire fixé à 10 % (art. 278 bis et suivants du CGI) est notamment applicable aux produits agricoles non transformés, au bois de chauffage, aux transports de voyageurs, à la restauration, aux travaux d''amélioration du logement, aux droits d''entrée dans les musées, zoo, etc. ; il concerne les biens et prestations de services qui relevaient du taux de 5,5 % avant le 1er janvier 2012 à l''exception de certains biens et services limitativement énumérés par l’article 278-0 bis du CGI.', 10.00),
(30, 'FRENCH_VAT_REDUCED', 'TVA Française, taux réduit', 'La taxe sur la valeur ajoutée (TVA) a un taux réduit fixé à 5,5 % (art. 278-0 bis du CGI) concerne les produits alimentaires, équipements et services pour handicapés, abonnements gaz et électricité, fourniture de repas dans les cantines scolaires, fourniture de chaleur produite à partir d’énergies renouvelables, livres sur tout support, billeterie de spectacle vivant ; depuis le 1er janvier 2014, il s''applique également aux logements sociaux et aux travaux d’amélioration de la qualité énergétique des logements.', 5.50),
(40, 'FRENCH_VAT_SPECIFIC', 'TVA Française, taux particulier', 'La taxe sur la valeur ajoutée (TVA) a un taux particulier fixé à 2,1 % (art. 281 quater et suivants du CGI) est réservé aux médicaments remboursables par la sécurité sociale, aux ventes d’animaux vivants de boucherie et de charcuterie à des non assujettis, à la redevance télévision, à certains spectacles et aux publications de presse inscrites à la Commission paritaire des publications et agences de presse.', 2.10);

INSERT INTO teco_tax_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, tax_id, attribute_definition_id, localization_code, market_area_id)
VALUES 
(10, null, null, null, null, null, 'TVA', 10, 9000, null, null),
(20, null, null, null, null, null, 'TVA', 20, 9000, null, null),
(30, null, null, null, null, null, 'TVA', 30, 9000, null, null),
(40, null, null, null, null, null, 'TVA', 40, 9000, null, null);

-- ECO CUSTOMER
INSERT INTO teco_group 
(id, name, description, code, version)
VALUES 
(10, 'GROUP_FO_CUSTOMER', NULL, 'GROUP_FO_CUSTOMER', 1);

INSERT INTO teco_role 
(id, name, description, code, version)
VALUES 
(10, 'ROLE_FO_CUSTOMER', NULL, 'ROLE_FO_CUSTOMER', 1);

INSERT INTO teco_group_role_rel VALUES 
(10, 10);

-- ECO CATALOG

INSERT INTO teco_catalog_master 
(id, description, code, is_default, name)
 VALUES 
(1, 'Default Master Catalog description', 'MASTER_CAT', 1, 'Master Catalog');


-- ECO RULE | PROMO

INSERT INTO teco_rule_referential   
(id, code, name, description, rule_type) 
 VALUES 
(10, 'PROMO_SKU_PROMOTION', 'PROMO SKU PROMOTION', 'DESCRIPTION SKU PROMOTION', 'productSkuPromotion'), 
(20, 'PROMO_SHIPPING_PROMOTION', 'PROMO SHIPPING PROMOTION', 'DESCRIPTION SHIPPING PROMOTION', 'shippingPromotion');

INSERT INTO teco_rule_repository  
(id, name, description, code, is_active, start_date, end_date) 
 VALUES 
(10, 'PROMO TEST 1', 'DESCRIPTION PROMO TEST 1', 'PROMO1', 1, '2012-09-05 15:43:09', '2013-10-05 15:43:09'), 
(20, 'PROMO TEST 2', 'DESCRIPTION PROMO TEST 2', 'PROMO2', 1, '2012-09-05 15:43:09', '2013-10-05 15:43:09');

INSERT INTO teco_rule_repository_referential_rel 
(rule_repository_id, rule_referential_id)
 VALUES 
(10, 10),
(10, 20),
(20, 20);

-- ECO PAYMENT GATEWAY

INSERT INTO teco_payment_gateway   
(id, payment_gateway_type, code, name, description) 
 VALUES 
(10, 'paymentGatewayFake', 'PGF', 'Fake Payment Gateway', 'Payment Gateway with no provider, just for tests');

INSERT INTO teco_payment_gateway_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, payment_gateway_id, attribute_definition_id, market_area_id)
VALUES (1, null, null, null, null, null, 'YOUR_GATEWAY_TOKEN', 10, 6000, null);

INSERT INTO teco_payment_gateway_option  
(id, code, name, description, option_value) 
 VALUES 
(10, 'PROXY_PORT', 'Proxy port', 'Proxy port', ''),
(20, 'PROXY_ADDRESS', 'Proxy address', 'Proxy address', '');
