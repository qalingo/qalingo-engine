--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version 0.7.0)
--         http://www.apache.org/licenses/LICENSE-2.0
--
--                   Copyright (c) Hoteia, 2012-2013
-- http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
--
--

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

INSERT INTO tbo_user_group_rel VALUES (1, 10),(1,20);

-- ECO GLOBAL

-- password equal "password"
INSERT INTO teco_customer 
(id, version, email, firstname, lastname, password, login, code, is_active)
VALUES (1, 1, 'qalingo@qalingo.com', 'Customer', 'Qalingo', 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58', 'customer', 'CUST1', 1);

INSERT INTO teco_customer_address 
(id, title, firstname, lastname, address1, address2, additional_information, address_name, city, postal_code, country_code, is_default_billing, is_default_shipping, customer_id)
VALUES (1, 'MR', 'Customer', 'Qalingo', 'rue de versaille', '', '', 'address1', 'Paris', '75000', 'FR', 1, 1, 1);

INSERT INTO teco_customer_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, customer_id, attribute_definition_id, localization_code, market_area_id)
VALUES (1, null, null, null, null, null, 'TEST ATTRIBUTE', 1, 10, null, null);

INSERT INTO teco_customer_credential 
(id, customer_id, password)
VALUES (1, 1, 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58');

INSERT INTO teco_cust_group_rel 
(customer_id, group_id) 
VALUES (1, 10);


-- ECO CATALOGUE

INSERT INTO teco_catalog_virtual 
(id, description, code, is_default, business_name, version, master_catalog_id)
 VALUES 
(100, 'Virtual Catalog description', 'V_CAT_INT', 0, 'Virtuel Catalog International', 1, 1),
(101, 'Virtual Catalog description', 'V_CAT_FRA', 0, 'Virtuel Catalog France', 1, 1),
(102, 'Virtual Catalog description', 'V_CAT_ESP', 0, 'Virtuel Catalog Espagne', 1, 1),
(201, 'Virtual Catalog description', 'V_CAT_USA', 0, 'Virtuel Catalog United-States', 1, 1),
(202, 'Virtual Catalog description', 'V_CAT_CAN', 0, 'Virtuel Catalog', 1, 1),
(210, 'Virtual Catalog description', 'V_CAT_BRA', 0, 'Virtuel Catalog Brazil', 1, 1),
(211, 'Virtual Catalog description', 'V_CAT_ARG', 0, 'Virtuel Catalog Argentina', 1, 1),
(301, 'Virtual Catalog description', 'V_CAT_CHN', 0, 'Virtuel Catalog China', 1, 1),
(302, 'Virtual Catalog description', 'V_CAT_JPN', 0, 'Virtuel Catalog Japan', 1, 1);

INSERT INTO teco_retailer 
(id, name, description, code, is_brand, is_default, is_official_retailer)
 VALUES 
(1, 'Default Retailer', 'Default Retailer', 'DRE', 0, 1, 0), 
(2, 'Default Retailer', 'Brand Retailer', 'BRE', 1, 0, 0), 
(3, 'Default Retailer', 'Official Retailer', 'ORE', 0, 0, 1);

INSERT INTO teco_retailer_address 
(id, version, address1, address2, additional_information, postal_code, city, country_code, email, fax, mobile, phone, retailer_id, latitude, longitude, is_default)
 VALUES 
(1, 1, 'address r1', '', '', '92300', 'Levallois-Perret', 'FR', 'email@qalingo.com', '0000000000', '0000000000', '0000000000', 1, '48.833', '2.333', 1), 
(2, 1, 'address r2', '', '', '75000', 'Paris', 'FR', 'email@qalingo.com', '0000000000', '0000000000', '0000000000', 2, '48.833', '2.333', 1), 
(3, 1, 'address r3', '', '', '75000', 'Paris', 'FR', 'email@qalingo.com', '0000000000', '0000000000', '0000000000', 3, '48.833', '2.333', 1);

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
(id, description, name, code, theme, is_default, version, default_localization_id, market_id, virtual_catalog_id, currency_id, latitude, longitude)
 VALUES 
(1, 'Market INT description', 'market area 1 : INT',   'INT', null, 1, 1, 1,  1, 100, 2, '-30.000', '45.000'),
(101, 'Market FRA description', 'market area 2 : FRA', 'FRA', null, 1, 1, 2, 10, 101, 1, '48.480', '2.200'),	
(102, 'Market ESP description', 'market area 3 : ESP', 'ESP', null, 1, 1, 3, 10, 102, 1, '40.260', '3.420'),	
(201, 'Market USA description', 'market area 4 : USA', 'USA', null, 1, 1, 1, 20, 201, 2, '40.000', '-90.000'),
(202, 'Market CAN description', 'market area 4 : CAN', 'CAN', null, 0, 1, 1, 20, 202, 4, '55.000', '-90.000'),
(210, 'Market BRA description', 'market area 2 : BRA', 'BRA', null, 1, 1, 9, 21, 210, 2, '-22.570', '-43.120'),
(211, 'Market ARG description', 'market area 2 : ARG', 'ARG', null, 1, 1, 3, 21, 211, 2, '-34.350', '-58.220'),
(301, 'Market CHN description', 'market area 5 : CHN', 'CHN', null, 0, 1, 7, 30, 301, 5, '121.280', '31.100'),
(302, 'Market JPN description', 'market area 6 : JPN', 'JPN', null, 1, 1, 8, 30, 302, 6, '35.400', '139.450');

/*
(510, 'BO_BUSINESS',  'bo-business.dev.qalingo.com', 1, 5),
(511, 'BO_REPORTING', 'bo-reporting.dev.qalingo.com', 1, 5),
(512, 'BO_TECHNICAL', 'bo-technical.dev.qalingo.com', 1, 5),
*/
INSERT INTO teco_market_area_attribute   
(id, context, string_value, market_area_id, attribute_definition_id)
VALUES 
(513, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 1, 5),
(514, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 1, 5);

INSERT INTO teco_market_area_attribute   
(id, context, string_value, market_area_id, attribute_definition_id)
VALUES 
(100, 'FO_MCOMMERCE', 'no-reply@YOURDOMAIN.com', 1, 1),
(101, 'FO_MCOMMERCE',   'contact@YOURDOMAIN.com', 1, 2),
(102, 'FO_MCOMMERCE',   'facebook,twitter', 1, 6);

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
(id, business_name, code, address1, address2, additional_information, postal_code, city, state_code, country_code, latitude, longitude, type, version)
 VALUES 
(10, 'Store New-York', 'STRNYC', '57th Street & Lexington', '','', '', 'New York', '', 'US', '40.667', '-73.633', 'SHOP', 1),
(20, 'Store Paris', 'STRPARIS', '85 avenue Lafayette', '', '','', 'Paris', '', 'FR', '48.833', '2.333', 'SHOP,CORNER', 1);

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


INSERT INTO teco_catalog_master_category 
(id, description, code, is_default, business_name, version)
 VALUES 
(10, 'Category 1 description', 'CATE10', 0, 'Nouveautés', 1),
(20, 'Category 2 description', 'CATE20', 0, 'Idées de cadeaux', 1),
(30, 'Category 3 description', 'CATE30', 1, 'Café', 1),
(40, 'Category 4 description', 'CATE40', 0, 'Thé', 1),
(50, 'Category 5 description', 'CATE50', 0, 'Commerce équitable', 1);

INSERT INTO teco_catalog_virtual_category 
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

INSERT INTO teco_catalog_master_category 
(id, description, code, is_default, business_name, version, default_parent_category_id)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Nouveautés Thé', 1, 10),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Nouveautés Café', 1, 10),
(201, 'Category 2 sub category description', 'CATE201', 0, 'Fêtes des pères', 1, 20),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Fêtes des mères', 1, 20),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Brésil', 1, 30), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Colombie', 1, 30),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Inde', 1, 40), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Anglais', 1, 40),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Max Havelaar', 1, 50), 
(502, 'Category 5 sub category description', 'CATE502', 0, 'Artisans du Monde', 1, 50);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, business_name, version, master_category_id, default_parent_category_id)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Nouveautés Thé', 1, 101, 10),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Nouveautés Café', 1, 102, 10),
(201, 'Category 2 sub category description', 'CATE201', 0, 'Fêtes des pères', 1, 201, 20),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Fêtes des mères', 1, 202, 20),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Brésil', 1, 301, 30), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Colombie', 1, 302, 30),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Inde', 1, 401, 40), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Anglais', 1, 402, 40),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Max Havelaar', 1, 501, 50), 
(502, 'Category 5 sub category description', 'CATE502', 0, 'Artisans du Monde', 1, 502, 50);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, virtual_category_id, attribute_definition_id, localization_code, market_area_id) 
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

INSERT INTO teco_catalog_master_category_child_category_rel  
(parent_master_catalog_category_id, child_master_catalog_category_id)
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

INSERT INTO teco_catalog_virtual_category_child_category_rel  
(parent_virtual_catalog_category_id, child_virtual_catalog_category_id)
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
(4, 'product marketing 4', 'PROD4', 0, 'Produit 4', 1, 20),
(5, 'product marketing 5', 'PROD1', 1, 'Produit 5', 1, 10), 
(6, 'product marketing 6', 'PROD2', 0, 'Produit 6', 1, 10), 
(7, 'product marketing 7', 'PROD3', 0, 'Produit 7', 1, 10), 
(8, 'product marketing 8', 'PROD4', 0, 'Produit 8', 1, 20),
(9, 'product marketing 9', 'PROD1', 1, 'Produit 9', 1, 10), 
(10, 'product marketing 10', 'PROD2', 0, 'Produit 10', 1, 10), 
(11, 'product marketing 11', 'PROD3', 0, 'Produit 11', 1, 10), 
(12, 'product marketing 12', 'PROD4', 0, 'Produit 12', 1, 20);

INSERT INTO teco_product_marketing_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, product_marketting_id, attribute_definition_id, localization_code, market_area_id) 
VALUES 
(1, null, null, null, null, null, 'PROD1 i18n', 1, 30, null, 1),
(2, null, null, null, null, null, 'PROD2 i18n', 2, 30, null, 1),
(3, null, null, null, null, null, 'PROD3 i18n', 3, 30, null, 1),
(4, null, null, null, null, null, 'PROD4 i18n', 4, 30, null, 1),
(5, null, null, null, null, null, 'PROD5 i18n', 1, 30, null, 1),
(6, null, null, null, null, null, 'PROD6 i18n', 2, 30, null, 1),
(7, null, null, null, null, null, 'PROD7 i18n', 3, 30, null, 1),
(8, null, null, null, null, null, 'PROD8 i18n', 4, 30, null, 1),
(9, null, null, null, null, null, 'PROD9 i18n', 1, 30, null, 1),
(10, null, null, null, null, null, 'PROD10 i18n', 2, 30, null, 1),
(11, null, null, null, null, null, 'PROD11 i18n', 3, 30, null, 1),
(12, null, null, null, null, null, 'PROD12 i18n', 4, 30, null, 1);

INSERT INTO teco_product_sku 
(id, description, code, is_default, business_name, version, product_marketing_id)
 VALUES 
(1, 'prod 1 product sku 1', 'SKU11', 1, 'Sku 11', 1, 1), 
(2, 'prod 1 product sku 2', 'SKU12', 0, 'Sku 12', 1, 1),
(3, 'prod 1 product sku 3', 'SKU13', 0, 'Sku 13', 1, 1),
(4, 'prod 2 product sku 1', 'SKU21', 0, 'Sku 21', 1, 2);

INSERT INTO teco_catalog_master_category_product_marketing_rel  
(master_category_id, product_marketing_id)
 VALUES 
(302, 1), 
(302, 2), 
(302, 3),
(401, 4);

INSERT INTO teco_catalog_virtual_category_product_marketing_rel  
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

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, master_category_id, scope)
 VALUES 
(1, 'image ...', 'CAT_IMG1', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 10, 'MASTER_CATEGORY'), 
(2, 'image ...', 'CAT_IMG2', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 20, 'MASTER_CATEGORY'), 
(3, 'image ...', 'CAT_IMG3', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 30, 'MASTER_CATEGORY'), 
(4, 'image ...', 'CAT_IMG4', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 40, 'MASTER_CATEGORY'), 
(5, 'image ...', 'CAT_IMG5', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 50, 'MASTER_CATEGORY'),
(6, 'image ...', 'CAT_IMG6', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'MASTER_CATEGORY'), 
(7, 'image ...', 'CAT_IMG7', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 20, 'MASTER_CATEGORY'), 
(8, 'image ...', 'CAT_IMG8', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 30, 'MASTER_CATEGORY'), 
(9, 'image ...', 'CAT_IMG9', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 40, 'MASTER_CATEGORY'),
(10, 'image ...', 'CAT_IMG9', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 50, 'MASTER_CATEGORY');

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(11, 'image ...', 'CAT_IMG1', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 10, 'VIRTUAL_CATEGORY'), 
(12, 'image ...', 'CAT_IMG2', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 20, 'VIRTUAL_CATEGORY'), 
(13, 'image ...', 'CAT_IMG3', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 30, 'VIRTUAL_CATEGORY'), 
(14, 'image ...', 'CAT_IMG4', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 40, 'VIRTUAL_CATEGORY'), 
(15, 'image ...', 'CAT_IMG5', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 50, 'VIRTUAL_CATEGORY'),
(16, 'image ...', 'CAT_IMG6', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'VIRTUAL_CATEGORY'), 
(17, 'image ...', 'CAT_IMG7', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 20, 'VIRTUAL_CATEGORY'), 
(18, 'image ...', 'CAT_IMG8', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 30, 'VIRTUAL_CATEGORY'), 
(19, 'image ...', 'CAT_IMG9', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 40, 'VIRTUAL_CATEGORY'),
(20, 'image ...', 'CAT_IMG9', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 50, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, product_marketing_id, scope)
 VALUES 
(21, 'image ...', 'PM_IMG1', 'prod-1-img-1.png', 1, 'image l', 1, 'PACKSHOT', 'SMALL', 1, 1, 'PRODUCT_MARKETING'), 
(22, 'image ...', 'PM_IMG2', 'prod-1-img-1.png', 0, 'image 2', 1, 'PACKSHOT', 'SMALL', 1, 2, 'PRODUCT_MARKETING'), 
(23, 'image ...', 'PM_IMG3', 'prod-1-img-1.png', 0, 'image 3', 1, 'PACKSHOT', 'SMALL', 1, 3, 'PRODUCT_MARKETING'), 
(24, 'image ...', 'PM_IMG4', 'prod-1-img-1.png', 0, 'image 4', 1, 'PACKSHOT', 'SMALL', 1, 4, 'PRODUCT_MARKETING'), 
(25, 'image ...', 'PM_IMG5', 'prod-1-img-1.png', 0, 'image 5', 1, 'BACKGROUND', null, 1, 1, 'PRODUCT_MARKETING'), 
(26, 'image ...', 'PM_IMG6', 'prod-1-img-1.png', 0, 'image 6', 1, 'BACKGROUND', null, 1, 2, 'PRODUCT_MARKETING'), 
(27, 'image ...', 'PM_IMG7', 'prod-1-img-1.png', 0, 'image 7', 1, 'BACKGROUND', null, 1, 3, 'PRODUCT_MARKETING'), 
(28, 'image ...', 'PM_IMG8', 'prod-1-img-1.png', 0, 'image 8', 1, 'BACKGROUND', null, 1, 4, 'PRODUCT_MARKETING');

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, product_sku_id, scope)
 VALUES 
(31, 'image ...', 'PS_IMG1', 'prod-1-img-1.png', 1, 'image l', 1, 'PACKSHOT', 'SMALL', 1, 1, 'PRODUCT_SKU'), 
(32, 'image ...', 'PS_IMG2', 'prod-1-img-1.png', 0, 'image 2', 1, 'PACKSHOT', 'SMALL', 1, 2, 'PRODUCT_SKU'), 
(33, 'image ...', 'PS_IMG3', 'prod-1-img-1.png', 0, 'image 3', 1, 'PACKSHOT', 'SMALL', 1, 3, 'PRODUCT_SKU'), 
(34, 'image ...', 'PS_IMG4', 'prod-1-img-1.png', 0, 'image 4', 1, 'PACKSHOT', 'SMALL', 1, 4, 'PRODUCT_SKU'), 
(35, 'image ...', 'PS_IMG5', 'prod-1-img-1.png', 0, 'image 5', 1, 'BACKGROUND', null, 1, 1, 'PRODUCT_SKU'), 
(36, 'image ...', 'PS_IMG6', 'prod-1-img-1.png', 0, 'image 6', 1, 'BACKGROUND', null, 1, 2, 'PRODUCT_SKU'), 
(37, 'image ...', 'PS_IMG7', 'prod-1-img-1.png', 0, 'image 7', 1, 'BACKGROUND', null, 1, 3, 'PRODUCT_SKU'), 
(38, 'image ...', 'PS_IMG8', 'prod-1-img-1.png', 0, 'image 8', 1, 'BACKGROUND', null, 1, 4, 'PRODUCT_SKU');


-- RULE | PROMO


