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
(id, code, country, language)
 VALUES 
(1, 'en', 'en', 'en'), 
(2, 'fr', 'fr', 'fr'), 
(3, 'es', 'es', 'es'), 
(4, 'it', 'it', 'it'), 
(5, 'de', 'de', 'de'), 
(6, 'nl', 'nl', 'nl'), 
(7, 'zh', 'zh', 'zh'), 
(8, 'jp', 'jp', 'jp'), 
(9, 'pt', 'pt', 'pt');

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
(5, 'yuan ', 'CNY', 'yuan', 'Yuan', 'Ұ'),
(6, 'yen ', 'JPY', 'yen', 'Yen', '¥');

INSERT INTO teco_attribute_definition 
(id, name, description, code, attribute_type, object_type, localizable, global, planned, version)
VALUES 
(1, 'Market Area Email From (Generic)', 'Market Area Email From (Generic)', 'MARKET_AREA_EMAIL_FROM', 1, 8, 0, 0, 0, 1),
(2, 'Market Area Email Contact (To)', 'Market Area Email Contact (To)', 'MARKET_AREA_EMAIL_CONTACT', 1, 8, 0, 0, 0, 1),
(5, 'Market Area Domaine name', 'Market Area Domaine name', 'MARKET_AREA_DOMAIN_NAME', 1, 8, 0, 0, 0, 1),
(6, 'Market Area Share Option', 'Market Area Share Option', 'MARKET_AREA_SHARE_OPTIONS', 1, 8, 0, 0, 0, 1),
(10, 'Customer attribute screen name', 'Customer attribute screen name description', 'CUSTOMER_ATTRIBUTE_SCREENNAME', 1, 4, 1, 1, 0, 1),
(20, 'Product Category attribute name', 'Product Category attribute name description', 'CATALOG_CATEGORY_ATTRIBUTE_I18N_NAME', 1, 1, 1, 1, 0, 1),
(21, 'Product Category attribute order', 'Product Category attribute order description', 'CATALOG_CATEGORY_ATTRIBUTE_ORDER', 4, 1, 0, 0, 0, 1),
(30, 'Product Marketing attribute name', 'Product Marketing attribute name description', 'PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME', 1, 2, 1, 1, 0, 1),
(31, 'Product Marketing attribute order', 'Product Marketing attribute order description', 'PRODUCT_MARKETING_ATTRIBUTE_ORDER', 4, 2, 0, 0, 0, 1),
(40, 'Product Sku attribute name', 'Product Sku attribute name description', 'PRODUCT_SKU_ATTRIBUTE_I18N_NAME', 1, 3, 1, 1, 0, 1),
(41, 'Product Sku attribute order', 'Product Sku attribute order description', 'PRODUCT_SKU_ATTRIBUTE_ORDER', 4, 3, 0, 0, 0, 1),
(50, 'Store attribute order', 'Store attribute order description', 'STORE_ATTRIBUTE_ORDER', 4, 5, 0, 0, 0, 1),
(51, 'Store attribute city name', 'Store attribute city name description', 'STORE_ATTRIBUTE_I18N_CITY', 1, 5, 0, 0, 0, 1);

-- CORE ENGINE SETTINGS

-- Cache TTL settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1000, 'Cache TTL settings', 'Cache TTL settings', 'WEB_CACHE_ELEMENT_TIME_TO_LIVE', '3600', 1);

-- SEO settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1005, 'Escape accent URL settings', 'Escape accent URL settings', 'ESCAPE_ACCENT_FROM_URL', 'true', 1);


-- Theme settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1010, 'Setting resource theme path', 'Setting resource theme path', 'THEME_RESOURCE_PREFIX_PATH', '', 1),
(1011, 'Setting resource assets root file path', 'Setting resource assets catalog root path', 'ASSET_FILE_ROOT_PATH', '/datas/assets', 1),
(1012, 'Setting resource assets root web path', 'Setting resource assets root web path', 'ASSET_WEB_ROOT_PATH', '/assets', 1),
(1013, 'Setting resource assets catalog file path', 'Setting resource assets catalog file path', 'ASSET_CATALOG_FILE_PATH', '/images/catalog/category', 1),
(1014, 'Setting resource assets product marketing file path', 'Setting resource assets product marketing file path', 'ASSET_PRODUCT_MARKETING_FILE_PATH', '/images/catalog/product-marketing', 1),
(1015, 'Setting resource assets product sku file path', 'Setting resource assets product sku file path', 'ASSET_PROPDUCT_SKU_FILE_PATH', '/images/catalog/product-sku', 1);


INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1010, 'BO_BUSINESS',  '/bo-business-resources/', 1010),
(1011, 'BO_REPORTING', '/bo-reporting-resources/', 1010),
(1012, 'BO_TECHNICAL', '/bo-technical-resources/', 1010),
(1013, 'FO_MCOMMERCE', '/fo-mcommerce-resources/', 1010),
(1014, 'FO_PREHOME',   '/fo-prehome-resources/', 1010);

-- Spring-batch settings
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

-- SOLR settings
INSERT INTO teco_engine_setting 
(id, name, description, code, default_value, version)
VALUES 
(1030, 'SOLR webapp Url', 'SOLR webapp Url', 'SOLR', '', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1030, 'MASTER',  'http://176.31.248.152:14080/solr', 1030);

-- Catalog resource settings like images
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
(1050, 'Setting items by list ', 'Setting items by list', 'COUNT_ITEM_BY_PAGE', '20', 1);

INSERT INTO teco_engine_setting_value  
(id, context, value, engine_setting_id)
VALUES 
(1050, 'BO_TECHNICAL_ENGINE_SETTING_LIST',  '20', 1050),
(1051, 'BO_TECHNICAL_USER_LIST',  '20', 1050),
(1052, 'BO_TECHNICAL_CACHE_LIST',  '20', 1050),
(1053, 'BO_TECHNICAL_BATCH_LIST',  '20', 1050);

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
(id, description, code, is_default, business_name)
 VALUES 
(1, 'Default Master Catalog description', 'MASTER_CAT', 1, 'Master Catalog');


-- ECO RULE | PROMO

INSERT INTO teco_rule_referential   
(id, name, description, rule_type) 
 VALUES 
(10, 'PROMO SKU PROMOTION', 'DESCRIPTION SKU PROMOTION', 'productSkuPromotion'), 
(20, 'PROMO SHIPPING PROMOTION', 'DESCRIPTION SHIPPING PROMOTION', 'shippingPromotion');

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
