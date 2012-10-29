--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version ${license.version})
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

INSERT INTO tbo_company 
(id, version, name, description, code, default_localization_id)
VALUES (1, 1, 'Company demo', 'company demo description', 'CPD', 1);

INSERT INTO tbo_company_localization_rel 
(company_id, localization_id)
VALUES 
(1,1),
(1,2);

-- password equal "password"
INSERT INTO tbo_user 
(id, version, email, firstname, lastname, password, login, is_active, company_id)
VALUES (1, 1, 'qalingo@qalingo.com', 'Admin', 'Qalingo', 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58', 'admin', 1, 1);

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

INSERT INTO tbo_user_group_rel VALUES (1, 10),(1,20);

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

-- password equal "password"
INSERT INTO teco_customer 
(id, version, email, firstname, lastname, password, login, is_active)
VALUES (1, 1, 'qalingo@qalingo.com', 'Customer', 'Qalingo', 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58', 'customer', 1);

INSERT INTO teco_customer_address 
(id, title, firstname, lastname, address1, address2, additional_information, address_name, city, postal_code, country_code, is_default_billing, is_default_shipping, customer_id)
VALUES (1, 'MR', 'Customer', 'Qalingo', 'rue de versaille', '', '', 'address1', 'Paris', '75000', 'FR', 1, 1, 1);

INSERT INTO teco_customer_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, customer_id, attribute_definition_id, localization_code, market_area_id)
VALUES (1, null, null, null, null, null, 'TEST ATTRIBUTE', 1, 10, null, null);

INSERT INTO teco_cust_group_rel 
(customer_id, group_id) 
VALUES (1, 10);


-- ECO CATALOGUE

INSERT INTO teco_catalog_master 
(id, description, code, is_default, business_name, version)
 VALUES 
(1, 'Default Master Catalog description', 'MASTER_CAT', 1, 'Master Catalog', 1);

INSERT INTO teco_catalog_virtual 
(id, description, code, is_default, business_name, version)
 VALUES 
(100, 'Virtual Catalog description', 'V_CAT_INT', 0, 'Virtuel Catalog International', 1),
(101, 'Virtual Catalog description', 'V_CAT_FRA', 0, 'Virtuel Catalog France', 1),
(102, 'Virtual Catalog description', 'V_CAT_ESP', 0, 'Virtuel Catalog Espagne', 1),
(201, 'Virtual Catalog description', 'V_CAT_USA', 0, 'Virtuel Catalog United-States', 1),
(202, 'Virtual Catalog description', 'V_CAT_CAN', 0, 'Virtuel Catalog', 1),
(210, 'Virtual Catalog description', 'V_CAT_BRA', 0, 'Virtuel Catalog Brazil', 1),
(211, 'Virtual Catalog description', 'V_CAT_ARG', 0, 'Virtuel Catalog Argentina', 1),
(301, 'Virtual Catalog description', 'V_CAT_CHN', 0, 'Virtuel Catalog China', 1),
(302, 'Virtual Catalog description', 'V_CAT_JPN', 0, 'Virtuel Catalog Japan', 1);

INSERT INTO teco_retailer 
(id, name, description, code, is_brand, is_default, is_official_retailer)
 VALUES 
(1, 'Default Retailer', 'Default Retailer', 'DRE', 0, 1, 0), 
(2, 'Default Retailer', 'Brand Retailer', 'BRE', 1, 0, 0), 
(3, 'Default Retailer', 'Official Retailer', 'ORE', 0, 0, 1);

INSERT INTO teco_marketplace 
(id, description, code, theme, is_default, name, version, master_catalog_id)
 VALUES 
(1, 'Market place 1 = Brand, Area ??', 'MARKPL1', null, 1, 'market place 1 (International)', 1, 1),
(10, 'Market place 2 = Brand, Area ??', 'MARKPL2', null, 0, 'market place 2 (Europa)', 1, 1),
(20, 'Market place 3 = Brand, Area ??', 'MARKPL3', null, 0, 'market place 3 (North America,South America)', 1, 1),
(30, 'Market place 4 = Brand, Area ??', 'MARKPL4', null, 0, 'market place 4 (ASIA)', 1, 1);

INSERT INTO teco_market 
(id, description, code, theme, is_default, name, version, marketplace_id)
 VALUES 
(1, 'Market 1 = example International', 'INT', null, 1, 'International', 1, 1),
(10, 'Market 2 = example Europa', 'EUR',  null, 1, 'Europe', 1, 10), 
(20, 'Market 3 = example North America', 'NAM', null, 1, 'North America', 1, 20),
(21, 'Market 4 = example South America', 'SAM', null, 0, 'South America', 1, 20),
(30, 'Market 5 = example Asia', 'ASIA', null, 1, 'Asia', 1, 30);

INSERT INTO teco_market_area 
(id, description, name, code, theme, is_default, version, domain_name, default_localization_id, market_id, virtual_catalog_id, currency_id)
 VALUES 
(1, 'Market INT description', 'market area 1 : INT', 'INT', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 1, 1, 100, 2), 
(101, 'Market FRA description', 'market area 2 : FRA', 'FRA', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 2, 10, 101, 1),
(102, 'Market ESP description', 'market area 3 : ESP', 'ESP', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 3, 10, 102, 1),
(201, 'Market USA description', 'market area 4 : USA', 'USA', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 1, 20, 201, 2),
(202, 'Market CAN description', 'market area 4 : CAN', 'CAN', null, 0, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 1, 20, 202, 4),
(210, 'Market BRA description', 'market area 2 : BRA', 'BRA', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 9, 21, 210, 2),
(211, 'Market ARG description', 'market area 2 : ARG', 'ARG', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 3, 21, 211, 2),
(301, 'Market CHN description', 'market area 5 : CHN', 'CHN', null, 0, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 7, 30, 301, 5),
(302, 'Market JPN description', 'market area 6 : JPN', 'JPN', null, 1, 1, 'fo-mcommerce.demo.qalingo-hoteia.com', 8, 30, 302, 6);

INSERT INTO teco_market_area_localization_rel 
(market_area_id, localization_id)
 VALUES 
(1, 1),
(1, 2),
(1, 3),
(101, 2),
(102, 3),
(201, 1),
(202, 1),
(202, 2),
(210, 9),
(211, 3),
(301, 7),
(302, 8);

INSERT INTO teco_market_area_retailer_rel 
(market_area_id, retailer_id)
 VALUES 
(1, 1),
(101, 1),
(102, 1),
(201, 1),
(202, 1),
(210, 1),
(211, 1),
(301, 1),
(302, 1);

-- STORE

INSERT INTO teco_store 
(id, business_name, code, address1, address2, additional_information, postal_code, city, county_code, country_code, longitude, latitude, version)
 VALUES 
(10, 'Store New-York', 'STRNYC', '57th Street & Lexington', '','', '', 'New York', '', 'US', '-73.633', '40.667', 1),
(20, 'Store Paris', 'STRPARIS', '85 avenue Lafayette', '', '','', 'Paris', '', 'FR', '2.333', '48.833', 1);

INSERT INTO teco_store_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, store_id, attribute_definition_id, localization_code, market_area_id)
VALUES 
(10, null, null, null, null, null, 'New-York en i18n', 10, 51, 'en', null),
(11, null, null, null, null, null, 'New-York fr i18n', 10, 51, 'fr', null),
(20, null, null, null, null, null, 'Paris en i18n', 20, 51, 'en', null),
(21, null, null, null, null, null, 'Paris fr i18n', 20, 51, 'fr', null);

-- PRODUCT

INSERT INTO teco_product_brand 
(id, description, code, name, version)
 VALUES 
(10, 'brand  1 description', 'BR10', 'Brand 1', 1),
(20, 'brand  2 description', 'BR20', 'Brand 2', 1),
(30, 'brand  3 description', 'BR30', 'Brand 3', 1),
(40, 'brand  4 description', 'BR40', 'Brand 4', 1),
(50, 'brand  5 description', 'BR50', 'Brand 5', 1);


INSERT INTO teco_product_category_master 
(id, description, code, is_default, business_name, version)
 VALUES 
(10, 'Category 1 description', 'CATE10', 0, 'Nouveautés', 1),
(20, 'Category 2 description', 'CATE20', 0, 'Idées de cadeaux', 1),
(30, 'Category 3 description', 'CATE30', 1, 'Café', 1),
(40, 'Category 4 description', 'CATE40', 0, 'Thé', 1),
(50, 'Category 5 description', 'CATE50', 0, 'Commerce équitable', 1);

INSERT INTO teco_product_category_virtual 
(id, description, code, is_default, business_name, version)
 VALUES 
(10, 'Category 1 description', 'CATE10', 0, 'Nouveautés', 1),
(20, 'Category 2 description', 'CATE20', 0, 'Idées de cadeaux', 1),
(30, 'Category 3 description', 'CATE30', 1, 'Café', 1),
(40, 'Category 4 description', 'CATE40', 0, 'Thé', 1),
(50, 'Category 5 description', 'CATE50', 0, 'Commerce équitable', 1);

INSERT INTO teco_catalog_master_category_master_rel 
(master_catalog_id, master_category_id)
 VALUES 
(1, 10),
(1, 20),
(1, 30),
(1, 40),
(1, 50);

INSERT INTO teco_catalog_virtual_category_virtual_rel 
(virtual_catalog_id, virtual_category_id)
 VALUES 
(100, 10),
(100, 20),
(100, 30),
(100, 40),
(100, 50),
(101, 10),
(101, 20),
(101, 30),
(101, 40),
(101, 50),
(102, 10),
(102, 20),
(102, 30),
(102, 40),
(102, 50),
(201, 10),
(201, 20),
(201, 30),
(201, 40),
(201, 50),
(202, 10),
(202, 20),
(202, 30),
(202, 40),
(202, 50),
(210, 10),
(210, 20),
(210, 30),
(210, 40),
(210, 50),
(211, 10),
(211, 20),
(211, 30),
(211, 40),
(211, 50),
(301, 10),
(301, 20),
(301, 30),
(301, 40),
(301, 50),
(302, 10),
(302, 20),
(302, 30),
(302, 40),
(302, 50);

INSERT INTO teco_product_category_master 
(id, description, code, is_default, business_name, version)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Nouveautés Thé', 1),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Nouveautés Café', 1),
(201, 'Category 2 sub category description', 'CATE201', 0, 'Fêtes des pères', 1),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Fêtes des mères', 1),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Brésil', 1), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Colombie', 1),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Inde', 1), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Anglais', 1),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Max Havelaar', 1), 
(502, 'Category 5 sub category description', 'CATE502', 0, 'Artisans du Monde', 1);

INSERT INTO teco_product_category_virtual 
(id, description, code, is_default, business_name, version, master_category_id)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Nouveautés Thé', 1, 101),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Nouveautés Café', 1, 102),
(201, 'Category 2 sub category description', 'CATE201', 0, 'Fêtes des pères', 1, 201),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Fêtes des mères', 1, 202),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Brésil', 1, 301), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Colombie', 1, 302),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Inde', 1, 401), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Anglais', 1, 402),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Max Havelaar', 1, 501), 
(502, 'Category 5 sub category description', 'CATE502', 0, 'Artisans du Monde', 1, 502);

INSERT INTO teco_product_category_virtual_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, product_category_id, attribute_definition_id, localization_code, market_area_id) 
VALUES 
(1,  null, null, null, null, null, 'Nouveautés Thé i18n',  101, 20, null, 1),
(2,  null, null, null, null, null, 'Nouveautés Café i18n',  102, 20, null, 1),
(3,  null, null, null, null, null, 'Fêtes des pères i18n',  201, 20, null, 1),
(4,  null, null, null, null, null, 'Fêtes des mères i18n',  202, 20, null, 1),
(5,  null, null, null, null, null, 'Brésil i18n',  301, 20, null, 1),
(6,  null, null, null, null, null, 'Colombie i18n',  302, 20, null, 1),
(7,  null, null, null, null, null, 'Inde i18n',  401, 20, null, 1),
(8,  null, null, null, null, null, 'Anglais i18n',  402, 20, null, 1),
(9,  null, null, null, null, null, 'Max Havelaar i18n',  501, 20, null, 1),
(10, null, null, null, null, null, 'Artisans du Monde i18n', 502, 20, null, 1);

INSERT INTO teco_product_category_master_child_category_rel  
(parent_master_product_category_id, child_master_product_category_id)
 VALUES 
(10, 101),
(10, 102),
(20, 201),
(20, 202),
(30, 301),
(30, 302),
(40, 401),
(40, 402),
(50, 501),
(50, 502);

INSERT INTO teco_product_category_virtual_child_category_rel  
(parent_virtual_product_category_id, child_virtual_product_category_id)
 VALUES 
(10, 101),
(10, 102),
(20, 201),
(20, 202),
(30, 301),
(30, 302),
(40, 401),
(40, 402),
(50, 501),
(50, 502);

INSERT INTO teco_product_marketing 
(id, description, code, is_default, business_name, version, brand_id)
 VALUES 
(1, 'product marketing 1', 'PROD1', 1, 'Produit l', 1, 10), 
(2, 'product marketing 2', 'PROD2', 0, 'Produit 2', 1, 10), 
(3, 'product marketing 3', 'PROD3', 0, 'Produit 3', 1, 10), 
(4, 'product marketing 4', 'PROD4', 0, 'Produit 4', 1, 20);

INSERT INTO teco_product_marketing_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, product_marketting_id, attribute_definition_id, localization_code, market_area_id) 
VALUES 
(1, null, null, null, null, null, 'PROD1 i18n', 1, 30, null, 1),
(2, null, null, null, null, null, 'PROD2 i18n', 2, 30, null, 1),
(3, null, null, null, null, null, 'PROD3 i18n', 3, 30, null, 1),
(4, null, null, null, null, null, 'PROD4 i18n', 4, 30, null, 1);

INSERT INTO teco_product_sku 
(id, description, code, is_default, business_name, version, product_marketing_id)
 VALUES 
(1, 'prod 1 product sku 1', 'SKU11', 1, 'Sku 11', 1, 1), 
(2, 'prod 1 product sku 2', 'SKU12', 0, 'Sku 12', 1, 1),
(3, 'prod 1 product sku 3', 'SKU13', 0, 'Sku 13', 1, 1),
(4, 'prod 2 product sku 1', 'SKU21', 0, 'Sku 21', 1, 2);

INSERT INTO teco_product_category_master_product_marketing_rel  
(master_category_id, product_marketing_id)
 VALUES 
(302, 1), 
(302, 2), 
(302, 3),
(401, 4);

INSERT INTO teco_product_category_virtual_product_marketing_rel  
(virtual_category_id, product_marketing_id)
 VALUES 
(302, 1), 
(302, 2), 
(302, 3),
(401, 4);

-- PRICE

-- MARKET PLACE INT
INSERT INTO teco_product_sku_price  
(id, market_area_id, price, retailer_id, currency_id, product_sku_id)
VALUES 
(30, 1, 14.35, 1, 2, 1),
(40, 1, 15.35, 1, 2, 2),
(50, 1, 16.35, 1, 2, 3),
(60, 1, 17.35, 1, 2, 4);

-- MARKET PLACE EUR
INSERT INTO teco_product_sku_price  
(id, market_area_id, price, retailer_id, currency_id, product_sku_id)
VALUES 
(130, 101, 14.45, 1, 1, 1),
(140, 101, 15.45, 1, 1, 2),
(150, 101, 16.45, 1, 1, 3),
(160, 101, 17.45, 1, 1, 4),
(230, 102, 14.45, 1, 1, 1),
(240, 102, 15.45, 1, 1, 2),
(250, 102, 16.45, 1, 1, 3),
(260, 102, 17.45, 1, 1, 4);

INSERT INTO teco_product_image  
(id, description, code, path, is_default, name, version, type, size)
 VALUES 
(1, 'image ...', 'IMG1', '/img/catalog/product-marketing/prod-1-img-1.png', 1, 'image l', 1, 'PACKSHOT', 'SMALL'), 
(2, 'image ...', 'IMG2', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 2', 1, 'PACKSHOT', 'SMALL'), 
(3, 'image ...', 'IMG3', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 3', 1, 'PACKSHOT', 'SMALL'), 
(4, 'image ...', 'IMG4', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 4', 1, 'PACKSHOT', 'SMALL'), 
(5, 'image ...', 'IMG5', '/img/catalog/category/cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null), 
(6, 'image ...', 'IMG6', '/img/catalog/category/cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null), 
(7, 'image ...', 'IMG7', '/img/catalog/category/cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null), 
(8, 'image ...', 'IMG8', '/img/catalog/category/cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null), 
(9, 'image ...', 'IMG9', '/img/catalog/category/cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null),
(10, 'image ...', 'IMG10', '/img/catalog/category/cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL'), 
(11, 'image ...', 'IMG11', '/img/catalog/category/cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL'), 
(12, 'image ...', 'IMG12', '/img/catalog/category/cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL'), 
(13, 'image ...', 'IMG13', '/img/catalog/category/cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL'),
(14, 'image ...', 'IMG5', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 5', 1, 'BACKGROUND', null), 
(15, 'image ...', 'IMG6', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 6', 1, 'BACKGROUND', null), 
(16, 'image ...', 'IMG7', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 7', 1, 'BACKGROUND', null), 
(17, 'image ...', 'IMG8', '/img/catalog/product-marketing/prod-1-img-1.png', 0, 'image 8', 1, 'BACKGROUND', null);

-- BACKGROUND FOR CATEGORY
INSERT INTO teco_product_category_virtual_image_rel   
(virtual_category_id, product_image_id) 
VALUES 
(10, 5),
(20, 6),
(30, 7),
(40, 8),
(50, 9),
(101, 5),
(102, 6),
(201, 7),
(202, 8),
(301, 9),
(302, 5),
(401, 6),
(402, 7),
(501, 8),
(502, 9);

INSERT INTO teco_product_marketing_image_rel  
(product_marketing_id, product_image_id) 
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO teco_product_sku_image_rel  
(product_sku_id, product_image_id) 
VALUES 
(1, 10),
(2, 11),
(3, 12),
(4, 13),
(1, 14),
(2, 15),
(3, 16),
(4, 17);

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
