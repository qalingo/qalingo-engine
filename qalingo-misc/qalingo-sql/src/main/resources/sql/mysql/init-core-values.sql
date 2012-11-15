--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version 0.7.0)
--         http://www.apache.org/licenses/LICENSE-2.0
--
--                   Copyright (c) Hoteia, 2012-2013
-- http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
--
--

INSERT INTO teco_localization
(id, locale_code)
 VALUES 
(1, 'en'), 
(2, 'fr'),
(3, 'es'),
(4, 'it'),
(5, 'de'),
(6, 'nl'),
(7, 'zh'),
(8, 'jp'),
(9, 'pt');

INSERT INTO tbo_group 
(id, name, description, code, version)
VALUES 
(10, 'GROUP_BO_ADMIN', NULL, 'GROUP_BO_ADMIN', 1),
(20, 'GROUP_BO_USER', NULL, 'GROUP_BO_USER', 1);

INSERT INTO tbo_role 
(id, name, description, code, version)
VALUES 
(10, 'ROLE_BO_BUSINESS_ADMIN', NULL, 'ROLE_BO_BUSINESS_ADMIN', 1),
(11, 'ROLE_BO_BUSINESS_USER', NULL, 'ROLE_BO_BUSINESS_USER', 1),
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
(20,31);

-- ECO GLOBAL
INSERT INTO teco_currency_referential  
(id, abbreviated, code, description, name, sign) 
VALUES 
(1, 'eur', 'EUR', 'Euro', 'Euro', '€'),
(2, 'usd', 'USD', 'Dollar USA', 'Dollar USA', '$'),
(3, 'gbp', 'GBP', 'Livre sterling', 'Livre sterling', '£'),
(4, 'cad', 'CAD', 'Dollar Canada', 'Dollar Canada', '€'),
(5, 'yuan ', 'CNY', 'yuan', 'yuan', 'Ұ'),
(6, 'yen ', 'JPY', 'yen', 'yen', '¥');

INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, planned, version)
VALUES 
(10, 'Customer attribute screen name', 'Customer attribute screen name description', 'CUSTOMER_ATTRIBUTE_SCREENNAME', 1, 4, 1, 1, 0, 1),
(20, 'Product Category attribute name', 'Product Category attribute name description', 'PRODUCT_CATEGORY_ATTRIBUTE_I18N_NAME', 1, 1, 1, 0, 0, 1),
(21, 'Product Category attribute order', 'Product Category attribute order description', 'PRODUCT_CATEGORY_ATTRIBUTE_ORDER', 4, 1, 0, 0, 0, 1),
(30, 'Product Marketing attribute name', 'Product Marketing attribute name description', 'PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME', 1, 2, 1, 0, 0, 1),
(31, 'Product Marketing attribute order', 'Product Marketing attribute order description', 'PRODUCT_MARKETING_ATTRIBUTE_ORDER', 4, 2, 0, 0, 0, 1),
(40, 'Product Sku attribute name', 'Product Sku attribute name description', 'PRODUCT_SKU_ATTRIBUTE_I18N_NAME', 1, 3, 1, 0, 0, 1),
(41, 'Product Sku attribute order', 'Product Sku attribute order description', 'PRODUCT_SKU_ATTRIBUTE_ORDER', 4, 3, 0, 0, 0, 1),
(50, 'Store attribute order', 'Store attribute order description', 'STORE_ATTRIBUTE_ORDER', 4, 5, 0, 0, 0, 1),
(51, 'Store attribute city name', 'Store attribute city name description', 'STORE_ATTRIBUTE_I18N_CITY', 1, 5, 0, 0, 0, 1);

-- Cache TTL settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1, 'Cache TTL settings', 'Cache TTL settings', 'WEB_CACHE_ELEMENT_TIME_TO_LIVE', '3600', 1);

-- Theme settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(10, 'Setting resource theme path', 'Setting resource theme path', 'THEME_RESOURCE_PREFIX_PATH', '', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(10, 'BO_BUSINESS',  '/bo-business-resources/', 10),
(11, 'BO_REPORTING', '/bo-reporting-resources/', 10),
(12, 'BO_TECHNICAL', '/bo-technical-resources/', 10),
(13, 'FO_MCOMMERCE', '/fo-mcommerce-resources/', 10),
(14, 'FO_PREHOME',   '/fo-prehome-resources/', 10);

-- Spring-batch settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(20, 'Spring-batch webapp Url', 'Spring-batch webapp Url', 'SPRING_BATCH_URL', '', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(20, 'CMS',  'http://app-cms-sync.demo.qalingo.com/', 20),
(21, 'CRM', 'http://app-crm-sync.demo.qalingo.com/', 20),
(22, 'ERP', 'http://app-erp-sync.demo.qalingo.com/', 20),
(23, 'NOTIFICATION', 'http://app-notification.demo.qalingo.com/', 20);

-- SOLR settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(30, 'SOLR webapp Url', 'SOLR webapp Url', 'SOLR', '', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(30, 'MASTER',  'http://176.31.248.152:14080/solr', 30);

-- Catalog resource settings like images
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(40, 'Catalog resource theme path', 'Catalog resource theme path', 'CATALOG_RESOURCE_PREFIX_PATH', '/fo-mcommerce-resources/', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(15, 'INT', '/fo-mcommerce-resources/', 40);

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

-- ECO CATALOGUE

INSERT INTO teco_catalog_master 
(id, description, code, is_default, business_name, version)
 VALUES 
(1, 'Default Master Catalog description', 'MASTER_CAT', 1, 'Master Catalog', 1);


-- RULE | PROMO

INSERT INTO teco_rule_referential   
(id, version, name, description, rule_type) 
 VALUES 
(10, 1, 'PROMO SKU PROMOTION', 'DESCRIPTION SKU PROMOTION', 'productSkuPromotion'), 
(20, 1, 'PROMO SHIPPING PROMOTION', 'DESCRIPTION SHIPPING PROMOTION', 'shippingPromotion');

INSERT INTO teco_rule_repository  
(id, version, name, description, code, is_active, start_date, end_date) 
 VALUES 
(10, 1, 'PROMO TEST 1', 'DESCRIPTION PROMO TEST 1', 'PROMO1', 1, '2012-09-05 15:43:09', '2013-10-05 15:43:09'), 
(20, 1, 'PROMO TEST 2', 'DESCRIPTION PROMO TEST 2', 'PROMO2', 1, '2012-09-05 15:43:09', '2013-10-05 15:43:09');

INSERT INTO teco_rule_repository_referential_rel 
(rule_repository_id, rule_referential_id)
 VALUES 
(10, 10),
(10, 20),
(20, 20);
