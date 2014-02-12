--
-- Most of the code in the Qalingo project is copyrighted Hoteia and licensed
-- under the Apache License Version 2.0 (release version 0.7.0)
--         http://www.apache.org/licenses/LICENSE-2.0
--
--                   Copyright (c) Hoteia, 2012-2013
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

INSERT INTO tbo_company 
(id, version, name, description, code, default_localization_id)
VALUES (1, 1, 'Company demo', 'company demo description', 'CPD', 1);

update tbo_company 
set description = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

INSERT INTO tbo_company_localization_rel 
(company_id, localization_id)
VALUES 
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(1,11),
(1,12),
(1,13);

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
VALUES (1, null, null, null, null, null, 'TEST ATTRIBUTE', 1, 100, null, null);

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
(302, 'Virtual Catalog description', 'V_CAT_JPN', 0, 'Virtuel Catalog Japan', 1, 1),
(303, 'Virtual Catalog description', 'V_CAT_VNM', 0, 'Virtuel Catalog Vietnam', 1, 1);

update teco_catalog_virtual 
set description = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

INSERT INTO teco_retailer 
(id, name, description, code, is_brand, is_official_retailer)
 VALUES 
(1, 'Default Retailer', 'Default Retailer', 'DRE', 0, 0), 
(2, 'Default Retailer', 'Brand Retailer', 'BRE', 1, 0), 
(3, 'Default Retailer', 'Official Retailer', 'ORE', 0, 1);

update teco_retailer 
set description = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

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

INSERT INTO teco_marketplace_attribute   
(id, context, string_value, market_place_id, attribute_definition_id)
VALUES 
(161, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 1, 600),
(162, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 1, 600),
(1161, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 10, 600),
(1162, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 10, 600),
(1261, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 20, 600),
(1262, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 20, 600),
(1361, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 30, 600),
(1362, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 30, 600);

INSERT INTO teco_market 
(id, description, code, theme, is_default, name, version, marketplace_id)
 VALUES 
(1, 'Market 1 = example International', 'INT', null, 1, 'International', 1, 1),
(10, 'Market 2 = example Europa', 'EUR',  null, 1, 'Europe', 1, 10), 
(20, 'Market 3 = example North America', 'NAM', null, 1, 'North America', 1, 20),
(21, 'Market 4 = example South America', 'SAM', null, 0, 'South America', 1, 20),
(30, 'Market 5 = example Asia', 'ASIA', null, 1, 'Asia', 1, 30);

INSERT INTO teco_market_area 
(id, description, name, code, theme, geoloc_country_code, is_default, version, market_id, virtual_catalog_id, default_localization_id, default_retailer_id, default_currency_id, latitude, longitude)
 VALUES 
(  1, 'Market INT description', 'market area 1 : INT', 'INT', null, null, 1, 1,  1, 100, 1, 1, 150, '-30.000', '45.000'),
(101, 'Market FRA description', 'market area 2 : FRA', 'FRA', null, 'FR', 1, 1, 10, 101, 2, 1, 45, '48.480', '2.200'),	
(102, 'Market ESP description', 'market area 3 : ESP', 'ESP', null, 'ES', 1, 1, 10, 102, 3, 1, 45, '40.260', '3.420'),	
(201, 'Market USA description', 'market area 4 : USA', 'USA', null, 'US', 1, 1, 20, 201, 1, 1, 150, '40.000', '-90.000'),
(202, 'Market CAN description', 'market area 4 : CAN', 'CAN', null, 'CA', 0, 1, 20, 202, 1, 1, 27, '55.000', '-90.000'),
(210, 'Market BRA description', 'market area 2 : BRA', 'BRA', null, 'BR', 1, 1, 21, 210, 8, 1, 21, '-22.570', '-43.120'),
(211, 'Market ARG description', 'market area 2 : ARG', 'ARG', null, 'AR', 1, 1, 21, 211, 3, 1, 8, '-34.350', '-58.220'),
(301, 'Market CHN description', 'market area 5 : CHN', 'CHN', null, 'CN', 0, 1, 30, 301, 9, 1, 31, '121.280', '31.100'),
(302, 'Market JPN description', 'market area 6 : JPN', 'JPN', null, 'JP', 1, 1, 30, 302, 7, 1, 72, '35.400', '139.450'),
(303, 'Market VNM description', 'market area 7 : VNM', 'VNM', null, 'VN', 0, 1, 30, 303, 13, 1, 154, '10.762622', '106.660172');

/*
(510, 'BO_BUSINESS',  'bo-business.dev.qalingo.com', 1, 5),
(511, 'BO_REPORTING', 'bo-reporting.dev.qalingo.com', 1, 5),
(512, 'BO_TECHNICAL', 'bo-technical.dev.qalingo.com', 1, 5),
INSERT INTO teco_market_area_attribute   
(id, context, string_value, market_area_id, attribute_definition_id)
VALUES 
(513, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 1, 5),
(514, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 1, 5);
*/

INSERT INTO teco_market_area_attribute   
(id, context, string_value, market_area_id, attribute_definition_id)
VALUES 
(10, 'DEFAULT_CONTEXT', 'no-reply@YOURDOMAIN.com', 1, 1),
(11, 'DEFAULT_CONTEXT', 'Demo Qalingo', 1, 2),
(12, 'DEFAULT_CONTEXT', 'contact@YOURDOMAIN.com', 1, 3);

INSERT INTO teco_market_area_attribute   
(id, context, string_value, market_area_id, attribute_definition_id)
VALUES 
(20, 'DEFAULT_CONTEXT', true, 1, 7);


INSERT INTO teco_market_area_attribute   
(id, context, string_value, market_area_id, attribute_definition_id)
VALUES 
(100, 'FO_MCOMMERCE', 'no-reply@YOURDOMAIN.com', 1, 1),
(101, 'FO_MCOMMERCE',   'Demo Qalingo', 1, 2),
(102, 'FO_MCOMMERCE',   'contact@YOURDOMAIN.com', 1, 3),
(103, 'FO_MCOMMERCE',   'facebook,twitter', 1, 6);

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
(210, 8),
(211, 3),
(301, 9),
(302, 7),
(303, 13);

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
(302, 1),
(303, 1);

INSERT INTO teco_market_area_currency_rel 
(market_area_id, currency_id)
 VALUES 
(1, 150),
(1, 45),
(101, 45),
(102, 45),
(201, 150),
(202, 27),
(210, 21),
(211, 8),
(301, 31),
(302, 72),
(303, 154);

-- STORE

INSERT INTO teco_store 
(id, business_name, code, address1, address2, additional_information, postal_code, city, state_code, country_code, latitude, longitude, type, version)
 VALUES 
(10, 'Store New-York', 'STRNYC', '57th Street & Lexington', '','', '', 'New York', '', 'US', '40.667', '-73.633', 'SHOP', 1),
(20, 'Store Paris', 'STRPARIS', '85 avenue Lafayette', '', '','', 'Paris', '', 'FR', '48.833', '2.333', 'SHOP,CORNER', 1);

INSERT INTO teco_store_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, store_id, attribute_definition_id, localization_code, market_area_id)
VALUES 
(10, null, null, null, null, null, 'New-York en i18n', 10, 510, 'en', null),
(11, null, null, null, null, null, 'New-York fr i18n', 10, 510, 'fr', null),
(20, null, null, null, null, null, 'Paris en i18n', 20, 510, 'en', null),
(21, null, null, null, null, null, 'Paris fr i18n', 20, 510, 'fr', null);

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, store_id, scope)
 VALUES 
(1001, 'image ...', 'IMG_STORE10_', 'store-1-img.png', 0, 'image 1 store 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'STORE'), 
(1002, 'image ...', 'ICON_STORE10', 'store-1-icon.png', 0, 'icon 1 store 10', 1, 'ICON', null, 1, 10, 'STORE'),
(1011, 'image ...', 'IMG_STORE20_', 'store-1-img.png', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 20, 'STORE'), 
(1012, 'image ...', 'ICON_STORE20', 'store-1-icon.png', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 20, 'STORE');

-- PRODUCT

INSERT INTO teco_product_brand 
(id, description, code, name, version)
 VALUES 
(10, 'brand  1 description', 'BR10', 'Brand 1', 1),
(20, 'brand  2 description', 'BR20', 'Brand 2', 1),
(30, 'brand  3 description', 'BR30', 'Brand 3', 1),
(40, 'brand  4 description', 'BR40', 'Brand 4', 1),
(50, 'brand  5 description', 'BR50', 'Brand 5', 1);

update teco_product_brand 
set description = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';
--
INSERT INTO teco_catalog_master_category 
(id, description, code, is_default, business_name, version)
 VALUES 
(10, 'Category 1 description', 'CATE10', 1, 'Coffee', 1),
(20, 'Category 2 description', 'CATE20', 0, 'Tea', 1),
(30, 'Category 3 description', 'CATE30', 0, 'Essentials', 1),
(40, 'Category 4 description', 'CATE40', 0, 'Nouveautés', 1),
(50, 'Category 5 description', 'CATE50', 0, 'Idées de cadeaux', 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, business_name, master_category_id, version)
 VALUES 
(10, 'Category 1 description', 'CATE10', 1, 'Coffee', 10, 1),
(20, 'Category 2 description', 'CATE20', 0, 'Tea', 20, 1),
(30, 'Category 3 description', 'CATE30', 0, 'Essentials', 30, 1),
(40, 'Category 4 description', 'CATE40', 0, 'Nouveautés', 20, 1),
(50, 'Category 5 description', 'CATE50', 0, 'Idées de cadeaux', 30, 1);

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
(302, 50),
(303, 10),
(303, 20),
(303, 30),
(303, 40),
(303, 50);

INSERT INTO teco_catalog_master_category 
(id, description, code, is_default, business_name, version, default_parent_category_id)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Just Brewed', 1, 10),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Light & Subtle', 1, 10),
(103, 'Category 1 sub category description', 'CATE103', 0, 'Light & Distinctive', 1, 10),
(104, 'Category 1 sub category description', 'CATE104', 0, 'Medium & Smooth', 1, 10),
(105, 'Category 1 sub category description', 'CATE105', 0, 'Dark & Distincetive', 1, 10), 
(201, 'Category 2 sub category description', 'CATE201', 0, 'Just Steeped', 1, 20),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Green', 1, 20), 
(203, 'Category 2 sub category description', 'CATE203', 0, 'Herbal Infusion', 1, 20),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Powders', 1, 30), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Drinkware', 1, 30),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Tea', 1, 40), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Coffee', 1, 40),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Fêtes des pères', 1, 50), 
(502, 'Category 5 sub category description', 'CATE502', 0, "Fêtes des mères", 1, 50);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, business_name, version, master_category_id, default_parent_category_id)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Just Brewed', 1, 101, 10),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Light & Subtle', 1, 102, 10),
(103, 'Category 1 sub category description', 'CATE103', 0, 'Light & Distinctive', 1, 103, 10),
(104, 'Category 1 sub category description', 'CATE104', 0, 'Medium & Smooth', 1, 104, 10),
(105, 'Category 1 sub category description', 'CATE105', 0, 'Dark & Distincetive', 1, 105, 10), 
(201, 'Category 2 sub category description', 'CATE201', 0, 'Just Steeped', 1, 201, 20),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Green', 1, 202, 20), 
(203, 'Category 2 sub category description', 'CATE203', 0, 'Herbal Infusion', 1, 203, 20),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Powders', 1, 301, 30), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Drinkware', 1, 302, 30),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Tea', 1, 401, 40), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Coffee', 1, 402, 40),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Fêtes des pères', 1, 501, 50), 
(502, 'Category 5 sub category description', 'CATE502', 0, "Fêtes des mères", 1, 502, 50);
INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, virtual_category_id, attribute_definition_id, localization_code, market_area_id) 
VALUES 
(1,  null, null, null, null, null, 'Just Brewed',  101, 200, null, 1),
(2,  null, null, null, null, null, 'Light & Subtle',  102, 200, null, 1),
(3,  null, null, null, null, null, 'Light & Distinctive',  103, 200, null, 1),
(4,  null, null, null, null, null, 'Medium & Smooth',  104, 200, null, 1),
(5,  null, null, null, null, null, 'Dark & Distincetive',  105, 200, null, 1),
(6,  null, null, null, null, null, 'Just Steeped',  201, 200, null, 1),
(7,  null, null, null, null, null, 'Green',  202, 200, null, 1),
(8,  null, null, null, null, null, 'Herbal Infusion',  203, 200, null, 1),
(9,  null, null, null, null, null, 'Powders',  301, 200, null, 1),
(10, null, null, null, null, null, 'Drinkware', 302, 200, null, 1),
(11,  null, null, null, null, null, 'Tea',  401, 200, null, 1),
(12,  null, null, null, null, null, 'Coffee',  402, 200, null, 1),
(13,  null, null, null, null, null, 'Feasts Fathers',  501, 200, null, 1),
(14, null, null, null, null, null, "Mother's Day", 502, 200, null, 1);


INSERT INTO teco_catalog_master_category_child_category_rel  
(parent_master_catalog_category_id, child_master_catalog_category_id)
 VALUES 
(10, 101),
(10, 102),
(10, 103),
(10, 104),
(10, 105),
(20, 201),
(20, 202),
(20, 203),
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
(10, 103),
(10, 104),
(10, 105),
(20, 201),
(20, 202),
(20, 203),
(30, 301),
(30, 302),
(40, 401),
(40, 402),
(50, 501),
(50, 502);

INSERT INTO teco_product_marketing 
(id, description, code, is_default, business_name, version, brand_id, default_catalog_category_id)
 VALUES 
(1, 'The flavor of resh ripe blueberries complemented by traditional brown sugar and pastry notes for a tasty flavored coffee treat.', 'PROD1', 1, 'Blueberry Streusel Coffee', 1, 10, 101), 
(2, 'A fragrant toffee aroma with flavors of walnuts and semi-sweet chocolate, with a crisp black cherry finish; this coffee comes from the Kintamani Highlands in North Bali, Indonesia. ', 'PROD2', 0, 'Bali Blue Moon Coffee', 1, 10, 101), 
(3, "Take this tumbler on your trips near and far. As you travel you'll be reminded of many of the cities and countries that The Coffee Bean touches in all parts of the world. Capacity: 14oz  Double wall insulated. Clear Acrylic. Hand Wash recommended. Made with BPA free materials. ", 'PROD3', 0, 'World Art Tumbler', 1, 10, 101), 
(4, 'A giant of worldwide coffee production, and the country that brought you the thong, Brazil produces not only the most coffee, it also grows some of the best. Our Brazil Cerrado is grown in the hot, flat savannahs of Minas Gerais. Labeled “Specialty Grade,” Brazil Cerrado is dry processed for a cup of coffee that displays terrific body, and a nutty, sweet taste that can only be found in the best Brazilian coffees. ', 'PROD4', 0, 'Brazil Cerrado Coffee', 1, 20, 102),
(5, 'To create our House Blend we combine natural and washed Central and South American coffees. We choose our blends for brightness, flavor, and aromas that make for a smooth, satisfying cup of coffee that can be enjoyed all day long.', 'PROD5', 0, 'House Blend Coffee', 1, 20, 102),
(6, 'To create our House Blend we combine natural and washed Central and South American coffees. We choose our blends for brightness, flavor, and aromas that make for a smooth, satisfying cup of coffee that can be enjoyed all day long.', 'PROD6', 0, 'Decaf House Blend Coffee', 1, 20, 102),
(7, 'To create our House Blend, we combine natural and washed Central and South American coffees. We choose our blends for brightness, flavor, and aromas that make for a smooth, satisfying cup of coffee that can be enjoyed all day long.', 'PROD7', 0, 'House Blend Coffee 24 2oz Portion Packs', 1, 20, 102),
(8, 'We offer Ethiopian Yirgacheffe, picked by hand on a farm in the mountains high above the town of Sidamo. There, the coffee beans are washed, and then soaked up to 72 hours in fermentation tanks. This wet process method produces intensely flavorful beans, with an intensely floral aroma, and mellow, smooth taste. ', 'PROD8', 0, 'Ethiopia Yirgacheffe Coffee', 1, 20, 103),
(9, 'Kenya has become the giant of African coffee production, ever since coffee made its way over the mountains from Ethiopia. Kenya AA is the largest bean grown in Kenya, and brews up a complex, fruity, light, and very bright cup. This is an exquisite coffee with an assertive, lively personality. Trust us, in coffee that’s a good thing. ', 'PROD9', 0, 'Kenya AA Coffee', 1, 20, 104),
(10, 'From tree to bag. This premier coffee is hand picked by workers on small family owned farms in the state of Nariño, on the Pacific Coast in Colombia. The farmers take great pride in growing, picking, and preparing their products by hand. We take great pride in offering you the results of their hard work. Their dedication pays off in a balanced, bright cup of coffee, richly aromatic with a creamy body. ', 'PROD10', 0, 'Colombia Narino Coffee', 1, 20, 104),
(11, 'Offered exclusively at The Coffee Bean & Tea Leaf Costa Rica La Cascada Tarrazu is balanced, clean, and mild. Prized for bright, crisp taste, Costa Rica La Cascada comes from the high altitude farms in the Tarrazu region where rich soils produce some of the best coffees in the land. ', 'PROD11', 0, 'Costa Rica Cascada Tarrazu Coffee', 1, 20, 104),
(12, 'Costa Rica strikes gold with La Minita Tarrazu. Black gold. Not oil, but coffee grown at La Minita, the legendary “little gold mine” of the Indians who sought gold in the plantation’s soil. Today, the gold mine plantation produces wealth in the form of an elegantly balanced coffee grown between 4,000 and 6,000 feet above sea level. The unique micro climates in the mountains of Costa Rica creates a coffee that exhibits a sweet aroma, full body, delicate acidity, and a clean after taste. ', 'PROD12', 0, 'Costa Rica La Minita Tarrazu Coffee', 1, 20, 104),
(13, 'Enjoy the flavor, and skip the caffeine. This premier coffee is hand picked by workers on small family-owned farms in the state of Nariño, on the Pacific Coast. The farmers take great pride in growing, picking, and preparing their products by hand. We take great pride in offering you the results of their hard work. Their dedication pays off in a balanced, bright cup of coffee, richly aromatic with a creamy body. ', 'PROD13', 0, 'Decaf Colombia Narino Coffee', 1, 20, 104),
(14, 'From the shady central highlands of Antigua, Guatemala, comes a full-bodied coffee classic noted for its balance, nuance, and aroma. We purchase the beans in small lots from established farms in Antigua to guarantee the high quality of our beans.', 'PROD14', 0, 'Guatemala Antigua Coffee', 1, 20, 104),
(15, 'Mocha Java is the world’s original coffee blend. Our interpretation combines sweet, fruity Ethiopian Yirgacheffe with the deep body and rich flavor of Java Estate coffees. The result has long been praised by coffee aficionados around the world.', 'PROD15', 0, 'Mocha Java Coffee', 1, 20, 104),
(16, 'The finest coffee in New Guinea comes from the Sigri estate, in the Waghi Valley of Papua New Guinea, where climate, soil and elevation combine to create ideal growing conditions. A coffee prized by connoisseurs, our Papua New Guinea Sigri A is naturally sweet, with a fruity aroma, a spicy body, and clean flavor.1 lb.', 'PROD16', 0, 'Papua New Guinea Sigri Coffee', 1, 20, 104),
(17, 'Discriminating coffee drinkers have long placed Sumatra Mandheling at the top of the list of the best coffees from Sumatra and perhaps the world. The syrupy, full body of Sumatra Mandheling, combined with its muted-acidity, makes an elegant, exotic cup of coffee. The taste of roasted caramel, and hints of chocolate make it truly exotic.', 'PROD17', 0, 'Sumatra Mandheling Coffee', 1, 20, 104),
(18, 'Brisk and invigorating, our English Breakfast Tea is a blend of black teas from Sri Lankan Ceylon, Taiwanese Black Tea, and Chinese Keemun Black Tea. Our crisp, smooth, and medium-bodied blend is our take on a beloved classic and a tribute to England’s tradition of tea drinking. ', 'PROD18', 0, 'Breakfast Blend Coffee', 1, 20, 104),
(19, 'From tree to bag. This premier coffee is hand picked by workers on small family owned farms in the state of Nariño, on the Pacific Coast in Colombia. The farmers take great pride in growing, picking, and preparing their products by hand. We take great pride in offering you the results of their hard work. Their dedication pays off in a balanced, bright cup of coffee, richly aromatic with a creamy body.', 'PROD19', 0, 'Colombia Narino Coffee, 24 2oz Portion Packs', 1, 20, 104),
(20, 'Offered exclusively at The Coffee Bean & Tea Leaf®, Costa Rica La Cascada Tarrazu is balanced, clean, and mild. Prized for bright, crisp taste, Costa Rica La Cascada comes from the high altitude farms in the Tarrazu region where rich soils produce some of the best coffees in the land.', 'PROD20', 0, 'Costa Rica Coffee, 24 2oz Portion Packs', 1, 20, 104),
(21, "Our Master Roaster created this seasonal treat for the holiday season in 1996. We've proudly featured it in our stores every year since!", 'PROD21', 0, 'Holiday Blend Coffee', 1, 20, 105),
(22, 'A fragrant toffee aroma with flavors of walnuts and semi-sweet chocolate, with a crisp black cherry finish; this coffee comes from the Kintamani Highlands in North Bali, Indonesia. ', 'PROD22', 0, 'Bali Blue Moon Coffee', 1, 20, 105),
(23, 'Fair Trade & Organic. Plum aroma with a smooth chocolatey body and spicy finish.', 'PROD23', 0, 'University Blend Coffee', 1, 20, 105),
(24, 'Full bodied with semi-sweet cocoa aroma, soft fruity flavor and a smooth finish.', 'PROD24', 0, 'Harvest Blend Coffee', 1, 20, 105),
(25, 'For coffee aficionados who love an espresso but can live without the caffeine, we offer our Decaffeinated Espresso Roast Blend. This strong and subtle Espresso combines the best of four select origin coffees, each individually roasted to perfection and combined after roasting. This coffee is subtle enough to be enjoyed as a straight shot, yet assertive enough for a latte. This signature blend is a delicious cornerstone of our business.', 'PROD25', 0, 'Decaf Espesso Roast Coffee', 1, 20, 105),
(26, 'Intensely French. Without the caffeine. We start with a delicious, delightful Costa Rican coffee, then dark roast it in the tradition of the famous French style. You’ll taste Caramelized sugars, a hint of chocolate and a deeply smoky flavor that attests to the heat of the roast characterize this intensely flavorful coffee.', 'PROD26', 0, 'Decaf French Roast Coffee', 1, 20, 105),
(27, 'We blend for flavor, Viennese style to achieve a deep brown, medium dark roast that toasts the beans to perfection. Our Viennese Blend brings select coffees together at the medium-dark roast level for a surprising balance and depth.', 'PROD27', 0, 'Decaf Viennese Coffee', 1, 20, 105),
(28, 'The darkest roast on the planet. We begin with a quintessential Costa Rican coffee that can stand up to the heat and intensity of a French Roast. Caramelized sugars, a hint of chocolate and a deeply smoky flavor that attests to the heat of the roast characterize this intensely flavorful coffee.', 'PROD28', 0, 'French Roast Coffee', 1, 20, 105),
(29, 'Deep roasted flavor with a smoky aroma and a chocolaty finish', 'PROD29', 0, 'Italian Roast Coffee', 1, 20, 105),
(30, 'Coming from Northern Tanzania, Africa, this coffee offers floral jasmine aroma, bright medium bodied flavor with hints of black currant and a chocolaty finish', 'PROD30', 0, 'Tanzania Peaberry Coffee', 1, 20, 105),
(31, 'People love our Colombia Nariño Dark for its full, rich, creamy body and rich aroma. We roast it a bit darker to mellow the brightness and add a touch of sweet, smoky caramel to the aroma and flavor.', 'PROD31', 0, 'Colombia Narino Dark Coffee', 1, 20, 105),
(32, 'We built our business on this signature blend. To create our Espresso Roast Blend, we roast four select origin coffees to perfection,then combine them to create the perfect base for our espresso drinks.', 'PROD32', 0, 'Espresso Roast Coffee', 1, 20, 105),
(33, 'Our Director of Tea, David DeCandia invites readers to explore the culture of tea and the adventures to save ancient Blue Tiger tea inside Master Davey and the Magic Tea House. The enchanting book is paired with a limited edition Blue Tiger Tea blend which consists of lemon myrtle, malva flower & tea blossoms with raspberry flavoring.', 'PROD33', 0, 'Master Davey Set: Blue Tiger Tea & Book', 1, 20, 201),
(34, 'Characteristically sweet rooibos and delicate black tea are highlighted by the festive flavor of mulling spices!.20 Whole Leaf Tea Bags', 'PROD34', 0, 'Winter Dream Tea', 1, 20, 201),
(35, 'Ceylon black tea blended with peppermint leaf, strawberries and all natural flavors.', 'PROD35', 0, 'Peppermint Stick Tea', 1, 20, 201),
(36, 'Sweetly scented orange blossoms, a tart hint of orange zest and our exclusive oolong tea capture the spirit of Los Angeles.  This item is also available at our retail locations at Los Angeles International Airport.', 'PROD36', 0, 'Los Angeles Sunshine Blend Tea', 1, 20, 201),
(37, 'Experience the divine essence of this delicate white tea, expertly flavored with an enticing peach and ginger blend. This item is also available at our retail locations at Hartfield-Jackson Atlanta International Airport.', 'PROD37', 0, 'Georgia Peach Ginger White Tea', 1, 20, 201),
(38, 'Can making tea be a labor of love? Consider our Jasmine Dragon Phoenix Pearl. To create this tea we take two beautiful young Chinese green tea leaves and one plump bud, then roll them together into a large tight pearl packed with flavor. As night falls we scent the pearls with the fragrance of Jasmine flowers seven times during a single evening.', 'PROD38', 0, 'Jasmine Dragon Phoenix Pearl Tea', 1, 20, 202),
(39, 'There’s nothing quite like the distinctive taste of Genmaicha Green tea, with its blend of Japanese Sencha tea and partially toasted rice. Refreshing and light, the toasted rice adds body and sweetness to the finish.', 'PROD39', 0, 'Genmaicha Green Tea', 1, 20, 202),
(40, 'Savor the tea of emperors. This delicate green tea is revered by the Chinese for its jade green color and unique shape. The only place in the world where Lung Ching Dragonwell tea is grown is in the West Lake district in Hangzhou, China. We make the tea the same way the Chinese have made it for centuries. The tea is meticulously prepared from tender leaves using a traditional handmade technique.', 'PROD40', 0, 'Lung Ching Dragonwell Tea', 1, 20, 202),
(41, 'This wonderful green tea is grown in China, the birthplace of tea, where the field workers take enormous pride in delivering only the highest quality green tea, legendary for its overall health benefits and antioxidant content. And our special CO2 decaffeination process lets you enjoy all the benefits of this fine, Chinese green tea with optimal taste and quality, without the caffeine.', 'PROD41', 0, 'Decaf Green Tea', 1, 20, 202),
(42, 'In the high country of Sweden the wildberry pickers look forward to summer and ripening berries. This drink blends hibiscus, raisins and an assortment of berries to create a warm, soul-soothing infusion full of natural fruit flavors and sweet, fragrant aromas.', 'PROD42', 0, 'Swedish Berries Tea', 1, 20, 203),
(43, 'Crossing continents and traditions, we took this caffeine-free South African (No Suggestions) and infused it with the exotic flavors of chai to create this sweet, creamy, delicately spiced infusion. ', 'PROD43', 0, 'Chai Rooibos Tea', 1, 20, 203),
(44, 'Our hand-harvested honeybush has a mild, sweet taste and aroma, somewhat like honey, and is infused with aromatic vanilla flavoring to create a unique infusion. This herbal infusion yields a vibrant reddish orange liquor not unlike the famous red glowing sunrise known throughout Africa.', 'PROD44', 0, 'African Sunrise Tea', 1, 20, 203),
(45, "We searched the world over trying to create the most delicious, richest vanilla beverage ever. If you've tasted our French Deluxe™ vanilla powder, you'll agree that we've succeeded. Bring home our classic French Deluxe™ Vanilla and have fun making your favorite beverages in your own kitchen.", 'PROD45', 0, 'French Deluxe Vanilla Powder', 1, 20, 301),
(46, "Our Special Dutch™ chocolate powder is now available with No Sugar Added! We added the ultra-premium ingredients to make the finest grade of cocoa. Try it alone, hot or cold, you'll love it any way! Drop a bit in your coffee or tea for an added bit of pleasure. We liked it so much, we made it our SECRET ingredient in our renowned Mocha Ice Blended® drinks, Cafe Mocha and Mocha Latte.", 'PROD46', 0, 'Special Dutch Chocolate Powder, no Sugar', 1, 20, 301),
(47, 'A blend of versatility and style with an über-sleek stainless steel purple finish. This tumbler is double wall insulated and made with BPA Free materials.', 'PROD47', 0, 'Americana Tumbler', 1, 20, 302),
(48, 'A blend of versatility and durability with an über-sleek chrome finish.
Double-wall insulated 
For hot or cold beverages
Capacity: 17 oz.
Hand-wash suggested ', 'PROD48', 0, 'Bergamo Bottle', 1, 20, 302),
(49, 'This sleek marvel is double-wall insulated with premium stainless steel, so it works with hot and cold beverages. 
BPA-free 
Capacity: 16 oz', 'PROD49', 0, 'The Jaidun Tumbler', 1, 20, 302),
(50, 'It bends but never breaks, and is henceforth a Japanese symbol of strength and prosperity. May every sip of our fine teas - and every step of your unique journey - be bamboo.', 'PROD50', 0, 'Bamboo Ceramic Tea Set', 1, 20, 302);




--update teco_product_marketing 
--set description = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

INSERT INTO teco_product_marketing_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, string_value, product_marketing_id, attribute_definition_id, localization_code, market_area_id) 
VALUES 
(1, null, null, null, null, null, 'Blueberry Streusel Coffee', 1, 300, null, 1),
(2, null, null, null, null, null, 'Bali Blue Moon Coffee', 2, 300, null, 1),
(3, null, null, null, null, null, 'World Art Tumbler', 3, 300, null, 1),
(4, null, null, null, null, null, 'Brazil Cerrado Coffee', 4, 300, null, 1),
(5, null, null, null, null, null, 'House Blend Coffee', 5, 300, null, 1),
(6, null, null, null, null, null, 'Decaf House Blend Coffee', 6, 300, null, 1),
(7, null, null, null, null, null, 'House Blend Coffee 24 2oz Portion Packs', 7, 300, null, 1),
(8, null, null, null, null, null, 'Ethiopia Yirgacheffe Coffee', 8, 300, null, 1),
(9, null, null, null, null, null, 'Kenya AA Coffee', 9, 300, null, 1),
(10, null, null, null, null, null, 'Colombia Narino Coffee', 10, 300, null, 1),
(11, null, null, null, null, null, 'Costa Rica Cascada Tarrazu Coffee', 11, 300, null, 1),
(12, null, null, null, null, null, 'Costa Rica La Minita Tarrazu Coffee', 12, 300, null, 1),
(13, null, null, null, null, null, 'Decaf Colombia Narino Coffee', 13, 300, null, 1),
(14, null, null, null, null, null, 'Guatemala Antigua Coffee', 14, 300, null, 1),
(15, null, null, null, null, null, 'Mocha Java Coffee', 15, 300, null, 1),
(16, null, null, null, null, null, 'Papua New Guinea Sigri Coffee', 16, 300, null, 1),
(17, null, null, null, null, null, 'Sumatra Mandheling Coffee', 17, 300, null, 1),
(18, null, null, null, null, null, 'Breakfast Blend Coffee', 18, 300, null, 1),
(19, null, null, null, null, null, 'Colombia Narino Coffee, 24 2oz Portion Packs', 19, 300, null, 1),
(20, null, null, null, null, null, 'Costa Rica Coffee, 24 2oz Portion Packs', 20, 300, null, 1),
(21, null, null, null, null, null, 'Holiday Blend Coffee', 21, 300, null, 1),
(22, null, null, null, null, null, 'Bali Blue Moon Coffee', 22, 300, null, 1),
(23, null, null, null, null, null, 'University Blend Coffee', 23, 300, null, 1),
(24, null, null, null, null, null, 'Harvest Blend Coffee', 24, 300, null, 1),
(25, null, null, null, null, null, 'Decaf Espesso Roast Coffee', 25, 300, null, 1),
(26, null, null, null, null, null, 'Decaf French Roast Coffee', 26, 300, null, 1),
(27, null, null, null, null, null, 'Decaf Viennese Coffee', 27, 300, null, 1),
(28, null, null, null, null, null, 'French Roast Coffee', 28, 300, null, 1),
(29, null, null, null, null, null, 'Italian Roast Coffee', 29, 300, null, 1),
(30, null, null, null, null, null, 'Tanzania Peaberry Coffee', 30, 300, null, 1),
(31, null, null, null, null, null, 'Colombia Narino Dark Coffee', 31, 300, null, 1),
(32, null, null, null, null, null, 'Espresso Roast Coffee', 32, 300, null, 1),
(33, null, null, null, null, null, 'Master Davey Set: Blue Tiger Tea & Book', 33, 300, null, 1),
(34, null, null, null, null, null, 'Winter Dream Tea', 34, 300, null, 1),
(35, null, null, null, null, null, 'Peppermint Stick Tea', 35, 300, null, 1),
(36, null, null, null, null, null, 'Los Angeles Sunshine Blend Tea', 36, 300, null, 1),
(37, null, null, null, null, null, 'Georgia Peach Ginger White Tea', 37, 300, null, 1),
(38, null, null, null, null, null, 'Jasmine Dragon Phoenix Pearl Tea', 38, 300, null, 1),
(39, null, null, null, null, null, 'Genmaicha Green Tea', 39, 300, null, 1),
(40, null, null, null, null, null, 'Lung Ching Dragonwell Tea', 40, 300, null, 1),
(41, null, null, null, null, null, 'Decaf Green Tea', 41, 300, null, 1),
(42, null, null, null, null, null, 'Swedish Berries Tea', 42, 300, null, 1),
(43, null, null, null, null, null, 'Chai Rooibos Tea', 43, 300, null, 1),
(44, null, null, null, null, null, 'African Sunrise Tea', 44, 300, null, 1),
(45, null, null, null, null, null, 'French Deluxe Vanilla Powder', 45, 300, null, 1),
(46, null, null, null, null, null, 'Special Dutch Chocolate Powder, no Sugar', 46, 300, null, 1),
(47, null, null, null, null, null, 'Americana Tumbler', 47, 300, null, 1),
(48, null, null, null, null, null, 'Bergamo Bottle', 48, 300, null, 1),
(49, null, null, null, null, null, 'The Jaidun Tumbler', 49, 300, null, 1),
(50, null, null, null, null, null, 'Bamboo Ceramic Tea Set', 50, 300, null, 1);


INSERT INTO teco_product_sku 
(id, description, code, is_default, business_name, version, product_marketing_id)
 VALUES 
(1, 'prod 1 product sku 1', 'SKU11', 1, 'Sku 11', 1, 1), 
(2, 'prod 2 product sku 1', 'SKU21', 0, 'Sku 21', 1, 2),
(3, 'prod 3 product sku 1', 'SKU31', 0, 'Sku 31', 1, 3),
(4, 'prod 4 product sku 1', 'SKU41', 0, 'Sku 41', 1, 4),
(5, 'prod 5 product sku 1', 'SKU51', 0, 'Sku 51', 1, 5),
(6, 'prod 5 product sku 2', 'SKU52', 0, 'Sku 52', 1, 5),
(7, 'prod 6 product sku 1', 'SKU61', 0, 'Sku 61', 1, 6),
(8, 'prod 6 product sku 2', 'SKU62', 0, 'Sku 62', 1, 6),
(9, 'prod 7 product sku 1', 'SKU71', 0, 'Sku 71', 1, 7),
(10, 'prod 8 product sku 1', 'SKU81', 0, 'Sku 81', 1, 8),
(11, 'prod 9 product sku 1', 'SKU91', 0, 'Sku 91', 1, 9),
(12, 'prod 10 product sku 1', 'SKU101', 0, 'Sku 101', 1, 10),
(13, 'prod 10 product sku 2', 'SKU102', 0, 'Sku 102', 1, 10),
(14, 'prod 11 product sku 1', 'SKU111', 0, 'Sku 111', 1, 11),
(15, 'prod 12 product sku 1', 'SKU121', 0, 'Sku 121', 1, 12),
(16, 'prod 13 product sku 1', 'SKU131', 0, 'Sku 131', 1, 13),
(17, 'prod 14 product sku 1', 'SKU141', 1, 'Sku 141', 1, 14), 
(18, 'prod 15 product sku 1', 'SKU151', 0, 'Sku 151', 1, 15),
(19, 'prod 16 product sku 1', 'SKU163', 0, 'Sku 161', 1, 16),
(20, 'prod 17 product sku 1', 'SKU171', 0, 'Sku 171', 1, 17),
(21, 'prod 18 product sku 1', 'SKU181', 0, 'Sku 181', 1, 18),
(22, 'prod 19 product sku 1', 'SKU191', 0, 'Sku 191', 1, 19),
(23, 'prod 20 product sku 1', 'SKU201', 0, 'Sku 201', 1, 20),
(24, 'prod 21 product sku 1', 'SKU211', 0, 'Sku 211', 1, 21),
(25, 'prod 22 product sku 1', 'SKU221', 0, 'Sku 221', 1, 22),
(26, 'prod 23 product sku 1', 'SKU231', 0, 'Sku 231', 1, 23),
(27, 'prod 24 product sku 1', 'SKU241', 0, 'Sku 241', 1, 24),
(28, 'prod 25 product sku 1', 'SKU251', 0, 'Sku 251', 1, 25),
(29, 'prod 26 product sku 1', 'SKU261', 0, 'Sku 261', 1, 26),
(30, 'prod 27 product sku 1', 'SKU271', 0, 'Sku 271', 1, 27),
(31, 'prod 28 product sku 1', 'SKU281', 0, 'Sku 281', 1, 28),
(32, 'prod 28 product sku 2', 'SKU282', 0, 'Sku 282', 1, 28),
(33, 'prod 29 product sku 1', 'SKU291', 1, 'Sku 291', 1, 29), 
(34, 'prod 30 product sku 1', 'SKU301', 0, 'Sku 301', 1, 30),
(35, 'prod 31 product sku 1', 'SKU311', 0, 'Sku 311', 1, 31),
(36, 'prod 32 product sku 1', 'SKU321', 0, 'Sku 321', 1, 32),
(37, 'prod 32 product sku 2', 'SKU322', 0, 'Sku 322', 1, 32),
(38, 'prod 33 product sku 1', 'SKU331', 0, 'Sku 331', 1, 33),
(39, 'prod 34 product sku 1', 'SKU341', 0, 'Sku 341', 1, 34),
(40, 'prod 35 product sku 1', 'SKU351', 0, 'Sku 351', 1, 35),
(41, 'prod 36 product sku 1', 'SKU361', 0, 'Sku 361', 1, 36),
(42, 'prod 37 product sku 1', 'SKU371', 0, 'Sku 371', 1, 37),
(43, 'prod 38 product sku 1', 'SKU381', 0, 'Sku 381', 1, 38),
(44, 'prod 39 product sku 1', 'SKU391', 0, 'Sku 391', 1, 39),
(45, 'prod 40 product sku 1', 'SKU401', 0, 'Sku 401', 1, 40),
(46, 'prod 41 product sku 1', 'SKU411', 0, 'Sku 411', 1, 41),
(47, 'prod 42 product sku 1', 'SKU421', 0, 'Sku 421', 1, 42),
(48, 'prod 43 product sku 1', 'SKU431', 0, 'Sku 431', 1, 43),
(49, 'prod 44 product sku 1', 'SKU441', 0, 'Sku 441', 1, 44),
(50, 'prod 45 product sku 1', 'SKU451', 0, 'Sku 451', 1, 45),
(51, 'prod 46 product sku 1', 'SKU461', 0, 'Sku 461', 1, 46),
(52, 'prod 77 product sku 1', 'SKU471', 0, 'Sku 471', 1, 47),
(53, 'prod 88 product sku 1', 'SKU481', 0, 'Sku 481', 1, 48),
(54, 'prod 99 product sku 1', 'SKU491', 0, 'Sku 491', 1, 49),
(55, 'prod200 product sku 1', 'SKU501', 0, 'Sku 501', 1, 50);
update teco_product_sku
set description = 'Lor23 ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

INSERT INTO teco_catalog_master_category_product_marketing_rel  
(master_category_id, product_marketing_id)
 VALUES 
(101, 1), 
(101, 2), 
(101, 3),
(102, 4),
(102, 5),
(102, 6),
(102, 7),
(103, 8),
(104, 9),
(104, 10),
(104, 11),
(104, 12),
(104, 13),
(104, 14),
(104, 15),
(104, 16),
(104, 17),
(104, 18),
(104, 19),
(104, 20), 
(105, 21),
(105, 22),
(105, 23),
(105, 24),
(105, 25),
(105, 26),
(105, 27),
(105, 28),
(105, 29),
(105, 30),
(105, 31),
(105, 32),
(201, 33),
(201, 34),
(201, 35),
(201, 36),
(201, 37),
(202, 38),
(202, 39),
(202, 40),
(202, 41),
(203, 42),
(203, 43),
(203, 44),
(301, 45),
(301, 46),
(302, 47),
(302, 48),
(302, 49),
(302, 50);

INSERT INTO teco_catalog_virtual_category_product_marketing_rel  
(virtual_category_id, product_marketing_id)
 VALUES 
(101, 1), 
(101, 2), 
(101, 3),
(102, 4),
(102, 5),
(102, 6),
(102, 7),
(103, 8),
(104, 9),
(104, 10),
(104, 11),
(104, 12),
(104, 13),
(104, 14),
(104, 15),
(104, 16),
(104, 17),
(104, 18),
(104, 19),
(104, 20), 
(105, 21), 
(105, 22),
(105, 23),
(105, 24),
(105, 25),
(105, 26),
(105, 27),
(105, 28),
(105, 29),
(105, 30),
(105, 31),
(105, 32),
(201, 33),
(201, 34),
(201, 35),
(201, 36),
(201, 37),
(202, 38),
(202, 39),
(202, 40),
(202, 41),
(203, 42),
(203, 43),
(203, 44),
(301, 45),
(301, 46),
(302, 47),
(302, 48),
(302, 49),
(302, 50);


INSERT INTO teco_product_marketing_attribute
(boolean_value,is_global,market_area_id,ordering,version,attribute_definition_id,product_marketing_id)
VALUES (1,0,1,0,1,520,1), (1,0,1,0,1,520,2), (1,0,1,0,1,520,3), (1,0,1,0,1,520,4);

-- PRICE

-- MARKET PLACE INT
INSERT INTO teco_product_sku_price  
(id, market_area_id, price_catalog, retailer_id, currency_id, product_sku_id)
VALUES 
(1, 1, 11.95, 1, 150, 1),
(2, 1, 13.95, 1, 150, 2),
(3, 1, 11.95, 1, 150, 3),
(4, 1, 10.95, 1, 150, 4),
(5, 1, 8.95, 1, 150, 5),
(6, 1, 11.95, 1, 150, 6),
(7, 1, 8.95, 1, 150, 7),
(8, 1, 11.95, 1, 150, 8),
(9, 1, 38.95, 1, 150, 9),
(10, 1, 13.95, 1, 150, 10),
(11, 1, 13.95, 1, 150, 11),
(12, 1, 8.95, 1, 150, 12),
(13, 1, 13.95, 1, 150, 13),
(14, 1, 13.95, 1, 150, 14),
(15, 1, 13.95, 1, 150, 15),
(16, 1, 13.95, 1, 150, 16),
(17, 1, 13.95, 1, 150, 17),
(18, 1, 13.95, 1, 150, 18),
(19, 1, 13.95, 1, 150, 19),
(20, 1, 13.95, 1, 150, 20),
(21, 1, 8.95, 1, 150, 21),
(22, 1, 38.95, 1, 150, 22),
(23, 1, 38.95, 1, 150, 23),
(24, 1, 13.95, 1, 150, 24),
(25, 1, 13.95, 1, 150, 25),
(26, 1, 9.95, 1, 150, 26),
(27, 1, 11.95, 1, 150, 27),
(28, 1, 8.95, 1, 150, 28),
(29, 1, 13.95, 1, 150, 29),
(30, 1, 13.95, 1, 150, 30),
(31, 1, 8.95, 1, 150, 31),
(32, 1, 13.95, 1, 150, 32),
(33, 1, 8.95, 1, 150, 33),
(34, 1, 13.95, 1, 150, 34),
(35, 1, 13.95, 1, 150, 35),
(36, 1, 8.95, 1, 150, 36),
(37, 1, 13.95, 1, 150, 37),
(38, 1, 24.95, 1, 150, 38),
(39, 1, 8.95, 1, 150, 39),
(40, 1, 8.95, 1, 150, 40),
(41, 1, 11.95, 1, 150, 41),
(42, 1, 11.95, 1, 150, 42),
(43, 1, 20.75, 1, 150, 43),
(44, 1, 10.75, 1, 150, 44),
(45, 1, 8.75, 1, 150, 45),
(46, 1, 10.75, 1, 150, 46),
(47, 1, 8.75, 1, 150, 47),
(48, 1, 8.75, 1, 150, 48),
(49, 1, 8.75, 1, 150, 49),
(50, 1, 11.95, 1, 150, 50),
(51, 1, 11.95, 1, 150, 51),
(52, 1, 14.95, 1, 150, 52),
(53, 1, 14.95, 1, 150, 53),
(54, 1, 14.95, 1, 150, 54),
(55, 1, 18.95, 1, 150, 55);


-- MARKET PLACE EUR
INSERT INTO teco_product_sku_price  
(id, market_area_id, price_catalog, retailer_id, currency_id, product_sku_id)
VALUES 
(56, 101, 11.95, 1, 150, 1),
(57, 101, 13.95, 1, 150, 2),
(58, 101, 11.95, 1, 150, 3),
(59, 101, 10.95, 1, 150, 4),
(60, 101, 8.95, 1, 150, 5),
(61, 101, 11.95, 1, 150, 6),
(62, 101, 8.95, 1, 150, 7),
(63, 101, 11.95, 1, 150, 8),
(64, 101, 38.95, 1, 150, 9),
(65, 101, 13.95, 1, 150, 10),
(66, 101, 13.95, 1, 150, 11),
(67, 101, 8.95, 1, 150, 12),
(68, 101, 13.95, 1, 150, 13),
(69, 101, 13.95, 1, 150, 14),
(70, 101, 13.95, 1, 150, 15),
(71, 101, 13.95, 1, 150, 16),
(72, 101, 13.95, 1, 150, 17),
(73, 101, 13.95, 1, 150, 18),
(74, 101, 13.95, 1, 150, 19),
(75, 101, 13.95, 1, 150, 20),
(76, 101, 8.95, 1, 150, 21),
(77, 101, 38.95, 1, 150, 22),
(78, 101, 38.95, 1, 150, 23),
(79, 101, 13.95, 1, 150, 24),
(80, 101, 13.95, 1, 150, 25),
(81, 101, 9.95, 1, 150, 26),
(82, 101, 11.95, 1, 150, 27),
(83, 101, 8.95, 1, 150, 28),
(84, 101, 13.95, 1, 150, 29),
(85, 101, 13.95, 1, 150, 30),
(86, 101, 8.95, 1, 150, 31),
(87, 101, 13.95, 1, 150, 32),
(88, 101, 8.95, 1, 150, 33),
(89, 101, 13.95, 1, 150, 34),
(90, 101, 13.95, 1, 150, 35),
(91, 101, 8.95, 1, 150, 36),
(92, 101, 13.95, 1, 150, 37),
(93, 101, 24.95, 1, 150, 38),
(94, 101, 8.95, 1, 150, 39),
(95, 101, 8.95, 1, 150, 40),
(96, 101, 11.95, 1, 150, 41),
(97, 101, 11.95, 1, 150, 42),
(98, 101, 20.75, 1, 150, 43),
(99, 101, 10.75, 1, 150, 44),
(100, 101, 8.75, 1, 150, 45),
(101, 101, 10.75, 1, 150, 46),
(102, 101, 8.75, 1, 150, 47),
(103, 101, 8.75, 1, 150, 48),
(104, 101, 8.75, 1, 150, 49),
(105, 101, 11.95, 1, 150, 50),
(106, 101, 11.95, 1, 150, 51),
(107, 101, 14.95, 1, 150, 52),
(108, 101, 14.95, 1, 150, 53),
(109, 101, 14.95, 1, 150, 54),
(110, 101, 18.95, 1, 150, 55),

(156, 102, 11.95, 1, 150, 1),
(157, 102, 13.95, 1, 150, 2),
(158, 102, 11.95, 1, 150, 3),
(159, 102, 10.95, 1, 150, 4),
(160, 102, 8.95, 1, 150, 5),
(161, 102, 11.95, 1, 150, 6),
(162, 102, 8.95, 1, 150, 7),
(163, 102, 11.95, 1, 150, 8),
(164, 102, 38.95, 1, 150, 9),
(165, 102, 13.95, 1, 150, 10),
(166, 102, 13.95, 1, 150, 11),
(167, 102, 8.95, 1, 150, 12),
(168, 102, 13.95, 1, 150, 13),
(169, 102, 13.95, 1, 150, 14),
(170, 102, 13.95, 1, 150, 15),
(171, 102, 13.95, 1, 150, 16),
(172, 102, 13.95, 1, 150, 17),
(173, 102, 13.95, 1, 150, 18),
(174, 102, 13.95, 1, 150, 19),
(175, 102, 13.95, 1, 150, 20),
(176, 102, 8.95, 1, 150, 21),
(177, 102, 38.95, 1, 150, 22),
(178, 102, 38.95, 1, 150, 23),
(179, 102, 13.95, 1, 150, 24),
(180, 102, 13.95, 1, 150, 25),
(181, 102, 9.95, 1, 150, 26),
(182, 102, 11.95, 1, 150, 27),
(183, 102, 8.95, 1, 150, 28),
(184, 102, 13.95, 1, 150, 29),
(185, 102, 13.95, 1, 150, 30),
(186, 102, 8.95, 1, 150, 31),
(187, 102, 13.95, 1, 150, 32),
(188, 102, 8.95, 1, 150, 33),
(189, 102, 13.95, 1, 150, 34),
(190, 102, 13.95, 1, 150, 35),
(191, 102, 8.95, 1, 150, 36),
(192, 102, 13.95, 1, 150, 37),
(193, 102, 24.95, 1, 150, 38),
(194, 102, 8.95, 1, 150, 39),
(195, 102, 8.95, 1, 150, 40),
(196, 1021, 11.95, 1, 150, 41),
(197, 102, 11.95, 1, 150, 42),
(198, 102, 20.75, 1, 150, 43),
(199, 102, 10.75, 1, 150, 44),
(1100, 102, 8.75, 1, 150, 45),
(1101, 102, 10.75, 1, 150, 46),
(1102, 102, 8.75, 1, 150, 47),
(1103, 102, 8.75, 1, 150, 48),
(1104, 102, 8.75, 1, 150, 49),
(1105, 102, 11.95, 1, 150, 50),
(1106, 102, 11.95, 1, 150, 51),
(1107, 102, 14.95, 1, 150, 52),
(1108, 102, 14.95, 1, 150, 53),
(1109, 102, 14.95, 1, 150, 54),
(1110, 102, 18.95, 1, 150, 55);


INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, master_category_id, scope)
 VALUES 
(1110,  'image ...', 'MCAT_BACKGROUND_10', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 10, 'MASTER_CATEGORY'), 
(2110,  'image ...', 'MCAT_BACKGROUND_20', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 20, 'MASTER_CATEGORY'), 
(3110,  'image ...', 'MCAT_BACKGROUND_30', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 30, 'MASTER_CATEGORY'), 
(4110,  'image ...', 'MCAT_BACKGROUND_40', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 40, 'MASTER_CATEGORY'), 
(5110,  'image ...', 'MCAT_BACKGROUND_50', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 50, 'MASTER_CATEGORY'),
(6110,  'image ...', 'MCAT_PACKSHOT_10', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'MASTER_CATEGORY'), 
(7110,  'image ...', 'MCAT_PACKSHOT_20', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 20, 'MASTER_CATEGORY'), 
(8110,  'image ...', 'MCAT_PACKSHOT_30', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 30, 'MASTER_CATEGORY'), 
(9110,  'image ...', 'MCAT_PACKSHOT_40', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 40, 'MASTER_CATEGORY'),
(1010, 'image ...', 'MCAT_PACKSHOT_50', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 50, 'MASTER_CATEGORY');

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(1111, 'image ...', 'VCAT_BACKGROUND_10', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 10, 'VIRTUAL_CATEGORY'), 
(1211, 'image ...', 'VCAT_BACKGROUND_20', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 20, 'VIRTUAL_CATEGORY'), 
(1311, 'image ...', 'VCAT_BACKGROUND_30', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 30, 'VIRTUAL_CATEGORY'), 
(1411, 'image ...', 'VCAT_BACKGROUND_40', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 40, 'VIRTUAL_CATEGORY'), 
(1511, 'image ...', 'VCAT_BACKGROUND_50', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 50, 'VIRTUAL_CATEGORY'),
(1611, 'image ...', 'VCAT_PACKSHOT_10', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'VIRTUAL_CATEGORY'), 
(1711, 'image ...', 'VCAT_PACKSHOT_20', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 20, 'VIRTUAL_CATEGORY'), 
(1811, 'image ...', 'VCAT_PACKSHOT_30', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 30, 'VIRTUAL_CATEGORY'), 
(1911, 'image ...', 'VCAT_PACKSHOT_40', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 40, 'VIRTUAL_CATEGORY'),
(2011, 'image ...', 'VCAT_PACKSHOT_50', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 50, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(11101, 'image ...', 'VCAT_SLIDESHOW_101', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 101, 'VIRTUAL_CATEGORY'), 
(11102, 'image ...', 'VCAT_SLIDESHOW_102', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 102, 'VIRTUAL_CATEGORY'),
(11103, 'image ...', 'VCAT_SLIDESHOW_103', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 103, 'VIRTUAL_CATEGORY'), 
(11104, 'image ...', 'VCAT_SLIDESHOW_104', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 104, 'VIRTUAL_CATEGORY'),
(11105, 'image ...', 'VCAT_SLIDESHOW_105', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 105, 'VIRTUAL_CATEGORY'), 
(11201, 'image ...', 'VCAT_SLIDESHOW_201', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 201, 'VIRTUAL_CATEGORY'),
(11202, 'image ...', 'VCAT_SLIDESHOW_202', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 202, 'VIRTUAL_CATEGORY'), 
(11203, 'image ...', 'VCAT_SLIDESHOW_203', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 203, 'VIRTUAL_CATEGORY'),
(11301, 'image ...', 'VCAT_SLIDESHOW_301', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 301, 'VIRTUAL_CATEGORY'), 
(11302, 'image ...', 'VCAT_SLIDESHOW_302', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 302, 'VIRTUAL_CATEGORY'),
(11501, 'image ...', 'VCAT_SLIDESHOW_501', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 501, 'VIRTUAL_CATEGORY'), 
(11502, 'image ...', 'VCAT_SLIDESHOW_502', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 502, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, product_marketing_id, scope)
 VALUES 
(1, 'image ...', 'PM_IMG1', 'blueberry-struesel-coffee-icon.jpg', 1, 'image l', 1, 'PACKSHOT', 'SMALL', 1, 1, 'PRODUCT_MARKETING'), 
(2, 'image ...', 'PM_IMG2', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 2', 1, 'PACKSHOT', 'SMALL', 1, 2, 'PRODUCT_MARKETING'), 
(3, 'image ...', 'PM_IMG3', 'world-art-tumbler-icon.jpg', 0, 'image 3', 1, 'PACKSHOT', 'SMALL', 1, 3, 'PRODUCT_MARKETING'), 
(4, 'image ...', 'PM_IMG4', 'brazil-cerrado-coffee-icon.jpg', 0, 'image 4', 1, 'PACKSHOT', 'SMALL', 1, 4, 'PRODUCT_MARKETING'), 
(5, 'image ...', 'PM_IMG5', 'house-blend-coffee-icon.jpg', 1, 'image 5', 1, 'PACKSHOT', 'SMALL', 1, 5, 'PRODUCT_MARKETING'), 
(6, 'image ...', 'PM_IMG6', 'decaf-house-blend-coffee-icon.jpg', 0, 'image 6', 1, 'PACKSHOT', 'SMALL', 1, 6, 'PRODUCT_MARKETING'), 
(7, 'image ...', 'PM_IMG7', 'house-blend-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 7', 1, 'PACKSHOT', 'SMALL', 1, 7, 'PRODUCT_MARKETING'), 
(8, 'image ...', 'PM_IMG8', 'ethiopia-yirgacheffe-coffee-icon.jpg', 0, 'image 8', 1, 'PACKSHOT', 'SMALL', 1, 8, 'PRODUCT_MARKETING'),
(9, 'image ...', 'PM_IMG9', 'kenya-aa-coffee-icon.jpg', 1, 'image 9', 1, 'PACKSHOT', 'SMALL', 1, 9, 'PRODUCT_MARKETING'), 
(10, 'image ...', 'PM_IMG10', 'colombia-narino-coffee-icon.jpg', 0, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'PRODUCT_MARKETING'), 
(11, 'image ...', 'PM_IMG11', 'costa-rica-cascada-tarrazu-coffee-icon.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 11, 'PRODUCT_MARKETING'), 
(12, 'image ...', 'PM_IMG12', 'costa-rica-la-minita-tarrazu-coffee-icon.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 12, 'PRODUCT_MARKETING'),
(13, 'image ...', 'PM_IMG13', 'decaf-colombia-narino-coffee-icon.jpg', 1, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 13, 'PRODUCT_MARKETING'), 
(14, 'image ...', 'PM_IMG14', 'guatemala-antigua-coffee-icon.jpg', 0, 'image 14', 1, 'PACKSHOT', 'SMALL', 1, 14, 'PRODUCT_MARKETING'),
(15, 'image ...', 'PM_IMG15', 'mocha-java-coffee-icon.jpg', 0, 'image 15', 1, 'PACKSHOT', 'SMALL', 1, 15, 'PRODUCT_MARKETING'), 
(16, 'image ...', 'PM_IMG16', 'papua-new-guinea-sigri-coffee-icon.jpg', 0, 'image 16', 1, 'PACKSHOT', 'SMALL', 1, 16, 'PRODUCT_MARKETING'), 
(17, 'image ...', 'PM_IMG17', 'sumatra-mandheling-coffee-icon.jpg', 0, 'image 17', 1, 'PACKSHOT', 'SMALL', 1, 17, 'PRODUCT_MARKETING'),
(18, 'image ...', 'PM_IMG18', 'breakfast-blend-coffee-icon.jpg', 1, 'image 18', 1, 'PACKSHOT', 'SMALL', 1, 18, 'PRODUCT_MARKETING'), 
(19, 'image ...', 'PM_IMG19', 'colombia-narino-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 19', 1, 'PACKSHOT', 'SMALL', 1, 19, 'PRODUCT_MARKETING'),
(20, 'image ...', 'PM_IMG20', 'costa-rica-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 20', 1, 'PACKSHOT', 'SMALL', 1, 20, 'PRODUCT_MARKETING'), 
(21, 'image ...', 'PM_IMG21', 'holiday-blend-coffee-icon.jpg', 0, 'image 21', 1, 'PACKSHOT', 'SMALL', 1, 21, 'PRODUCT_MARKETING'), 
(22, 'image ...', 'PM_IMG22', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 22', 1, 'PACKSHOT', 'SMALL', 1, 22, 'PRODUCT_MARKETING'),
(23, 'image ...', 'PM_IMG23', 'university-blend-coffee-icon.jpg', 1, 'image 23', 1, 'PACKSHOT', 'SMALL', 1, 23, 'PRODUCT_MARKETING'), 
(24, 'image ...', 'PM_IMG24', 'harvest-blend-coffee-icon.jpg', 0, 'image 24', 1, 'PACKSHOT', 'SMALL', 1, 24, 'PRODUCT_MARKETING'),
(25, 'image ...', 'PM_IMG25', 'decaf-espesso-roast-coffee-icon.jpg', 0, 'image 25', 1, 'PACKSHOT', 'SMALL', 1, 25, 'PRODUCT_MARKETING'), 
(26, 'image ...', 'PM_IMG26', 'decaf-french-roast-coffee-icon.jpg', 0, 'image 26', 1, 'PACKSHOT', 'SMALL', 1, 26, 'PRODUCT_MARKETING'), 
(27, 'image ...', 'PM_IMG27', 'decaf-viennese-coffee-icon.jpg', 0, 'image 27', 1, 'PACKSHOT', 'SMALL', 1, 27, 'PRODUCT_MARKETING'),
(28, 'image ...', 'PM_IMG28', 'french-roast-coffee-icon.jpg', 1, 'image 28', 1, 'PACKSHOT', 'SMALL', 1, 28, 'PRODUCT_MARKETING'), 
(29, 'image ...', 'PM_IMG29', 'italian-roast-coffee-icon.jpg', 0, 'image 29', 1, 'PACKSHOT', 'SMALL', 1, 29, 'PRODUCT_MARKETING'),
(30, 'image ...', 'PM_IMG30', 'tanzania-peaberry-coffee-icon.jpg', 0, 'image 30', 1, 'PACKSHOT', 'SMALL', 1, 30, 'PRODUCT_MARKETING'), 
(31, 'image ...', 'PM_IMG31', 'colombia-narino-dark-coffee-icon.jpg', 0, 'image 31', 1, 'PACKSHOT', 'SMALL', 1, 31, 'PRODUCT_MARKETING'), 
(32, 'image ...', 'PM_IMG32', 'espresso-roast-coffee-icon.jpg', 0, 'image 32', 1, 'PACKSHOT', 'SMALL', 1, 32, 'PRODUCT_MARKETING'),
(33, 'image ...', 'PM_IMG33', 'master-davey-set-blue-tiger-tea-book-icon.jpg', 1, 'image 33', 1, 'PACKSHOT', 'SMALL', 1, 33, 'PRODUCT_MARKETING'), 
(34, 'image ...', 'PM_IMG34', 'winter-dream-tea-icon.jpg', 0, 'image 34', 1, 'PACKSHOT', 'SMALL', 1, 34, 'PRODUCT_MARKETING'),
(35, 'image ...', 'PM_IMG35', 'peppermint-stick-tea-icon.jpg', 0, 'image 35', 1, 'PACKSHOT', 'SMALL', 1, 35, 'PRODUCT_MARKETING'), 
(36, 'image ...', 'PM_IMG36', 'los-angeles-sunshine-blend-tea-icon.jpg', 0, 'image 36', 1, 'PACKSHOT', 'SMALL', 1, 36, 'PRODUCT_MARKETING'), 
(37, 'image ...', 'PM_IMG37', 'georgia-peach-ginger-white-tea-icon.jpg', 0, 'image 37', 1, 'PACKSHOT', 'SMALL', 1, 37, 'PRODUCT_MARKETING'),
(38, 'image ...', 'PM_IMG38', 'jasmine-dragon-phoenix-pearl-tea-icon.jpg', 1, 'image 38', 1, 'PACKSHOT', 'SMALL', 1, 38, 'PRODUCT_MARKETING'), 
(39, 'image ...', 'PM_IMG39', 'genmaicha-green-tea-icon.jpg', 0, 'image 39', 1, 'PACKSHOT', 'SMALL', 1, 39, 'PRODUCT_MARKETING'),
(40, 'image ...', 'PM_IMG40', 'lung-ching-dragonwell-tea-icon.jpg', 0, 'image 40', 1, 'PACKSHOT', 'SMALL', 1, 40, 'PRODUCT_MARKETING'), 
(41, 'image ...', 'PM_IMG41', 'decaf-green-tea-icon.jpg', 0, 'image 41', 1, 'PACKSHOT', 'SMALL', 1, 41, 'PRODUCT_MARKETING'), 
(42, 'image ...', 'PM_IMG42', 'swedish-berries-tea-icon.jpg', 0, 'image 42', 1, 'PACKSHOT', 'SMALL', 1, 42, 'PRODUCT_MARKETING'),
(43, 'image ...', 'PM_IMG43', 'chai-rooibos-tea-icon.jpg', 1, 'image 43', 1, 'PACKSHOT', 'SMALL', 1, 43, 'PRODUCT_MARKETING'), 
(44, 'image ...', 'PM_IMG44', 'african-sunrise-tea-icon.jpg', 0, 'image 44', 1, 'PACKSHOT', 'SMALL', 1, 44, 'PRODUCT_MARKETING'),
(45, 'image ...', 'PM_IMG45', 'french-deluxe-vanilla-powder-icon.jpg', 0, 'image 45', 1, 'PACKSHOT', 'SMALL', 1, 45, 'PRODUCT_MARKETING'), 
(46, 'image ...', 'PM_IMG46', 'special-dutch-chocolate-powder-no-sugar-icon.jpg', 0, 'image 46', 1, 'PACKSHOT', 'SMALL', 1, 46, 'PRODUCT_MARKETING'), 
(47, 'image ...', 'PM_IMG47', 'americana-tumbler-icon.jpg', 0, 'image 47', 1, 'PACKSHOT', 'SMALL', 1, 47, 'PRODUCT_MARKETING'),
(48, 'image ...', 'PM_IMG48', 'bergamo-bottle-icon.jpg', 1, 'image 48', 1, 'PACKSHOT', 'SMALL', 1, 48, 'PRODUCT_MARKETING'), 
(49, 'image ...', 'PM_IMG49', 'the-jaidun-tumbler-icon.jpg', 0, 'image 49', 1, 'PACKSHOT', 'SMALL', 1, 49, 'PRODUCT_MARKETING'),
(50, 'image ...', 'PM_IMG50', 'bamboo-ceramic-tea-set-icon.jpg', 0, 'image 50', 1, 'PACKSHOT', 'SMALL', 1, 50, 'PRODUCT_MARKETING'),

(51, 'image ...', 'PM_IMG51', 'blueberry-struesel-coffee.jpg', 1, 'image 5l', 1, 'BACKGROUND', null, 1, 1, 'PRODUCT_MARKETING'), 
(52, 'image ...', 'PM_IMG52', 'bali-blue-moon-coffee.jpg', 0, 'image 52', 1, 'BACKGROUND', null, 1, 2, 'PRODUCT_MARKETING'), 
(53, 'image ...', 'PM_IMG53', 'world-art-tumbler.jpg', 0, 'image 53', 1, 'BACKGROUND', null, 1, 3, 'PRODUCT_MARKETING'), 
(54, 'image ...', 'PM_IMG54', 'brazil-cerrado-coffee.jpg', 0, 'image 54', 1, 'BACKGROUND', null, 1, 4, 'PRODUCT_MARKETING'), 
(55, 'image ...', 'PM_IMG55', 'house-blend-coffee.jpg', 1, 'image 55', 1, 'BACKGROUND', null, 1, 5, 'PRODUCT_MARKETING'), 
(56, 'image ...', 'PM_IMG56', 'decaf-house-blend-coffee.jpg', 0, 'image 56', 1, 'BACKGROUND', null, 1, 6, 'PRODUCT_MARKETING'), 
(57, 'image ...', 'PM_IMG57', 'house-blend-coffee-24-2oz-portion-packs.jpg', 0, 'image 57', 1, 'BACKGROUND', null, 1, 7, 'PRODUCT_MARKETING'), 
(58, 'image ...', 'PM_IMG58', 'ethiopia-yirgacheffe-coffee.jpg', 0, 'image 58', 1, 'BACKGROUND', null, 1, 8, 'PRODUCT_MARKETING'),
(59, 'image ...', 'PM_IMG59', 'kenya-aa-coffee.jpg', 1, 'image 59', 1, 'BACKGROUND', null, 1, 9, 'PRODUCT_MARKETING'), 
(60, 'image ...', 'PM_IMG60', 'colombia-narino-coffee.jpg', 0, 'image 60', 1, 'BACKGROUND', null, 1, 10, 'PRODUCT_MARKETING'), 
(61, 'image ...', 'PM_IMG61', 'costa-rica-cascada-tarrazu-coffee.jpg', 0, 'image 61', 1, 'BACKGROUND', null, 1, 11, 'PRODUCT_MARKETING'), 
(62, 'image ...', 'PM_IMG62', 'costa-rica-la-minita-tarrazu-coffee.jpg', 0, 'image 62', 1, 'BACKGROUND', null, 1, 12, 'PRODUCT_MARKETING'),
(63, 'image ...', 'PM_IMG63', 'decaf-colombia-narino-coffee.jpg', 1, 'image 63', 1, 'BACKGROUND', null, 1, 13, 'PRODUCT_MARKETING'), 
(64, 'image ...', 'PM_IMG64', 'guatemala-antigua-coffee.jpg', 0, 'image 64', 1, 'BACKGROUND', null, 1, 14, 'PRODUCT_MARKETING'),
(65, 'image ...', 'PM_IMG65', 'mocha-java-coffee.jpg', 0, 'image 65', 1, 'BACKGROUND', null, 1, 15, 'PRODUCT_MARKETING'), 
(66, 'image ...', 'PM_IMG66', 'papua-new-guinea-sigri-coffee.jpg', 0, 'image 66', 1, 'BACKGROUND', null, 1, 16, 'PRODUCT_MARKETING'), 
(67, 'image ...', 'PM_IMG67', 'sumatra-mandheling-coffee.jpg', 0, 'image 67', 1, 'BACKGROUND', null, 1, 17, 'PRODUCT_MARKETING'),
(68, 'image ...', 'PM_IMG68', 'breakfast-blend-coffee.jpg', 1, 'image 68', 1, 'BACKGROUND', null, 1, 18, 'PRODUCT_MARKETING'), 
(69, 'image ...', 'PM_IMG69', 'colombia-narino-coffee-24-2oz-portion-packs.jpg', 0, 'image 69', 1, 'BACKGROUND', null, 1, 19, 'PRODUCT_MARKETING'),
(70, 'image ...', 'PM_IMG70', 'costa-rica-coffee-24-2oz-portion-packs.jpg', 0, 'image 70', 1, 'BACKGROUND', null, 1, 20, 'PRODUCT_MARKETING'), 
(71, 'image ...', 'PM_IMG71', 'holiday-blend-coffee.jpg', 0, 'image 71', 1, 'BACKGROUND', null, 1, 21, 'PRODUCT_MARKETING'), 
(72, 'image ...', 'PM_IMG72', 'bali-blue-moon-coffee.jpg', 0, 'image 72', 1, 'BACKGROUND', null, 1, 22, 'PRODUCT_MARKETING'),
(73, 'image ...', 'PM_IMG73', 'university-blend-coffee.jpg', 1, 'image 73', 1, 'BACKGROUND', null, 1, 23, 'PRODUCT_MARKETING'), 
(74, 'image ...', 'PM_IMG74', 'harvest-blend-coffee.jpg', 0, 'image 74', 1, 'BACKGROUND', null, 1, 24, 'PRODUCT_MARKETING'),
(75, 'image ...', 'PM_IMG75', 'decaf-espesso-roast-coffee.jpg', 0, 'image 75', 1, 'BACKGROUND', null, 1, 25, 'PRODUCT_MARKETING'), 
(76, 'image ...', 'PM_IMG76', 'decaf-french-roast-coffee.jpg', 0, 'image 76', 1, 'BACKGROUND', null, 1, 26, 'PRODUCT_MARKETING'), 
(77, 'image ...', 'PM_IMG77', 'decaf-viennese-coffee.jpg', 0, 'image 77', 1, 'BACKGROUND', null, 1, 27, 'PRODUCT_MARKETING'),
(78, 'image ...', 'PM_IMG78', 'french-roast-coffee.jpg', 1, 'image 78', 1, 'BACKGROUND', null, 1, 28, 'PRODUCT_MARKETING'), 
(79, 'image ...', 'PM_IMG79', 'italian-roast-coffee.jpg', 0, 'image 79', 1, 'BACKGROUND', null, 1, 29, 'PRODUCT_MARKETING'),
(80, 'image ...', 'PM_IMG80', 'tanzania-peaberry-coffee.jpg', 0, 'image 80', 1, 'BACKGROUND', null, 1, 30, 'PRODUCT_MARKETING'), 
(81, 'image ...', 'PM_IMG81', 'colombia-narino-dark-coffee.jpg', 0, 'image 81', 1, 'BACKGROUND', null, 1, 31, 'PRODUCT_MARKETING'), 
(82, 'image ...', 'PM_IMG82', 'espresso-roast-coffee.jpg', 0, 'image 82', 1, 'BACKGROUND', null, 1, 32, 'PRODUCT_MARKETING'),
(83, 'image ...', 'PM_IMG83', 'master-davey-set-blue-tiger-tea-book.jpg', 1, 'image 83', 1, 'BACKGROUND', null, 1, 33, 'PRODUCT_MARKETING'), 
(84, 'image ...', 'PM_IMG84', 'winter-dream-tea.jpg', 0, 'image 84', 1, 'BACKGROUND', null, 1, 34, 'PRODUCT_MARKETING'),
(85, 'image ...', 'PM_IMG85', 'peppermint-stick-tea.jpg', 0, 'image 85', 1, 'BACKGROUND', null, 1, 35, 'PRODUCT_MARKETING'), 
(86, 'image ...', 'PM_IMG86', 'los-angeles-sunshine-blend-tea.jpg', 0, 'image 86', 1, 'BACKGROUND', null, 1, 36, 'PRODUCT_MARKETING'), 
(87, 'image ...', 'PM_IMG87', 'georgia-peach-ginger-white-tea.jpg', 0, 'image 87', 1, 'BACKGROUND', null, 1, 37, 'PRODUCT_MARKETING'),
(88, 'image ...', 'PM_IMG88', 'jasmine-dragon-phoenix-pearl-tea.jpg', 1, 'image 88', 1, 'BACKGROUND', null, 1, 38, 'PRODUCT_MARKETING'), 
(89, 'image ...', 'PM_IMG89', 'genmaicha-green-tea.jpg', 0, 'image 89', 1, 'BACKGROUND', null, 1, 39, 'PRODUCT_MARKETING'),
(90, 'image ...', 'PM_IMG90', 'lung-ching-dragonwell-tea.jpg', 0, 'image 90', 1, 'BACKGROUND', null, 1, 40, 'PRODUCT_MARKETING'), 
(91, 'image ...', 'PM_IMG91', 'decaf-green-tea.jpg', 0, 'image 91', 1, 'BACKGROUND', null, 1, 41, 'PRODUCT_MARKETING'), 
(92, 'image ...', 'PM_IMG92', 'swedish-berries-tea.jpg', 0, 'image 92', 1, 'BACKGROUND', null, 1, 42, 'PRODUCT_MARKETING'),
(93, 'image ...', 'PM_IMG93', 'chai-rooibos-tea.jpg', 1, 'image 93', 1, 'BACKGROUND', null, 1, 43, 'PRODUCT_MARKETING'), 
(94, 'image ...', 'PM_IMG94', 'african-sunrise-tea.jpg', 0, 'image 94', 1, 'BACKGROUND', null, 1, 44, 'PRODUCT_MARKETING'),
(95, 'image ...', 'PM_IMG95', 'french-deluxe-vanilla-powder.jpg', 0, 'image 95', 1, 'BACKGROUND', null, 1, 45, 'PRODUCT_MARKETING'), 
(96, 'image ...', 'PM_IMG96', 'special-dutch-chocolate-powder-no-sugar.jpg', 0, 'image 96', 1, 'BACKGROUND', null, 1, 46, 'PRODUCT_MARKETING'), 
(97, 'image ...', 'PM_IMG97', 'americana-tumbler.jpg', 0, 'image 97', 1, 'BACKGROUND', null, 1, 47, 'PRODUCT_MARKETING'),
(98, 'image ...', 'PM_IMG98', 'bergamo-bottle.jpg', 1, 'image 98', 1, 'BACKGROUND', null, 1, 48, 'PRODUCT_MARKETING'), 
(99, 'image ...', 'PM_IMG99', 'the-jaidun-tumbler.jpg', 0, 'image 99', 1, 'BACKGROUND', null, 1, 49, 'PRODUCT_MARKETING'),
(100, 'image ...', 'PM_IMG100', 'bamboo-ceramic-tea-set.jpg', 0, 'image 100', 1, 'BACKGROUND', null, 1, 50, 'PRODUCT_MARKETING');


INSERT INTO teco_asset  
(id, description, code, path, is_default, name, version, type, size, is_global, product_sku_id, scope)
 VALUES
 
 (101, 'image ...', 'PS_IMG1', 'blueberry-struesel-coffee-icon.jpg', 1, 'image l', 1, 'PACKSHOT', 'SMALL', 1, 1, 'PRODUCT_SKU'), 
(102, 'image ...', 'PS_IMG2', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 2', 1, 'PACKSHOT', 'SMALL', 1, 2, 'PRODUCT_SKU'), 
(103, 'image ...', 'PS_IMG3', 'world-art-tumbler-icon.jpg', 0, 'image 3', 1, 'PACKSHOT', 'SMALL', 1, 3, 'PRODUCT_SKU'), 
(104, 'image ...', 'PS_IMG4', 'brazil-cerrado-coffee-icon.jpg', 0, 'image 4', 1, 'PACKSHOT', 'SMALL', 1, 4, 'PRODUCT_SKU'), 
(105, 'image ...', 'PS_IMG5', 'house-blend-coffee-icon.jpg', 1, 'image 5', 1, 'PACKSHOT', 'SMALL', 1, 5, 'PRODUCT_SKU'), 
(106, 'image ...', 'PS_IMG6', 'decaf-house-blend-coffee-icon.jpg', 0, 'image 6', 1, 'PACKSHOT', 'SMALL', 1, 6, 'PRODUCT_SKU'), 
(107, 'image ...', 'PS_IMG7', 'house-blend-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 7', 1, 'PACKSHOT', 'SMALL', 1, 7, 'PRODUCT_SKU'), 
(108, 'image ...', 'PS_IMG8', 'ethiopia-yirgacheffe-coffee-icon.jpg', 0, 'image 8', 1, 'PACKSHOT', 'SMALL', 1, 8, 'PRODUCT_SKU'),
(109, 'image ...', 'PS_IMG9', 'kenya-aa-coffee-icon.jpg', 1, 'image 9', 1, 'PACKSHOT', 'SMALL', 1, 9, 'PRODUCT_SKU'), 
(110, 'image ...', 'PS_IMG10', 'colombia-narino-coffee-icon.jpg', 0, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'PRODUCT_SKU'), 
(111, 'image ...', 'PS_IMG11', 'costa-rica-cascada-tarrazu-coffee-icon.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 11, 'PRODUCT_SKU'), 
(112, 'image ...', 'PS_IMG12', 'costa-rica-la-minita-tarrazu-coffee-icon.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 12, 'PRODUCT_SKU'),
(113, 'image ...', 'PS_IMG13', 'decaf-colombia-narino-coffee-icon.jpg', 1, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 13, 'PRODUCT_SKU'), 
(114, 'image ...', 'PS_IMG14', 'guatemala-antigua-coffee-icon.jpg', 0, 'image 14', 1, 'PACKSHOT', 'SMALL', 1, 14, 'PRODUCT_SKU'),
(115, 'image ...', 'PS_IMG15', 'mocha-java-coffee-icon.jpg', 0, 'image 15', 1, 'PACKSHOT', 'SMALL', 1, 15, 'PRODUCT_SKU'), 
(116, 'image ...', 'PS_IMG16', 'papua-new-guinea-sigri-coffee-icon.jpg', 0, 'image 16', 1, 'PACKSHOT', 'SMALL', 1, 16, 'PRODUCT_SKU'), 
(117, 'image ...', 'PS_IMG17', 'sumatra-mandheling-coffee-icon.jpg', 0, 'image 17', 1, 'PACKSHOT', 'SMALL', 1, 17, 'PRODUCT_SKU'),
(118, 'image ...', 'PS_IMG18', 'breakfast-blend-coffee-icon.jpg', 1, 'image 18', 1, 'PACKSHOT', 'SMALL', 1, 18, 'PRODUCT_SKU'), 
(119, 'image ...', 'PS_IMG19', 'colombia-narino-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 19', 1, 'PACKSHOT', 'SMALL', 1, 19, 'PRODUCT_SKU'),
(120, 'image ...', 'PS_IMG20', 'costa-rica-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 20', 1, 'PACKSHOT', 'SMALL', 1, 20, 'PRODUCT_SKU'), 
(121, 'image ...', 'PS_IMG21', 'holiday-blend-coffee-icon.jpg', 0, 'image 21', 1, 'PACKSHOT', 'SMALL', 1, 21, 'PRODUCT_SKU'), 
(122, 'image ...', 'PS_IMG22', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 22', 1, 'PACKSHOT', 'SMALL', 1, 22, 'PRODUCT_SKU'),
(123, 'image ...', 'PS_IMG23', 'university-blend-coffee-icon.jpg', 1, 'image 23', 1, 'PACKSHOT', 'SMALL', 1, 23, 'PRODUCT_SKU'), 
(124, 'image ...', 'PS_IMG24', 'harvest-blend-coffee-icon.jpg', 0, 'image 24', 1, 'PACKSHOT', 'SMALL', 1, 24, 'PRODUCT_SKU'),
(125, 'image ...', 'PS_IMG25', 'decaf-espesso-roast-coffee-icon.jpg', 0, 'image 25', 1, 'PACKSHOT', 'SMALL', 1, 25, 'PRODUCT_SKU'), 
(126, 'image ...', 'PS_IMG26', 'decaf-french-roast-coffee-icon.jpg', 0, 'image 26', 1, 'PACKSHOT', 'SMALL', 1, 26, 'PRODUCT_SKU'), 
(127, 'image ...', 'PS_IMG27', 'decaf-viennese-coffee-icon.jpg', 0, 'image 27', 1, 'PACKSHOT', 'SMALL', 1, 27, 'PRODUCT_SKU'),
(128, 'image ...', 'PS_IMG28', 'french-roast-coffee-icon.jpg', 1, 'image 28', 1, 'PACKSHOT', 'SMALL', 1, 28, 'PRODUCT_SKU'), 
(129, 'image ...', 'PS_IMG29', 'italian-roast-coffee-icon.jpg', 0, 'image 29', 1, 'PACKSHOT', 'SMALL', 1, 29, 'PRODUCT_SKU'),
(130, 'image ...', 'PS_IMG30', 'tanzania-peaberry-coffee-icon.jpg', 0, 'image 30', 1, 'PACKSHOT', 'SMALL', 1, 30, 'PRODUCT_SKU'), 
(131, 'image ...', 'PS_IMG31', 'colombia-narino-dark-coffee-icon.jpg', 0, 'image 31', 1, 'PACKSHOT', 'SMALL', 1, 31, 'PRODUCT_SKU'), 
(132, 'image ...', 'PS_IMG32', 'espresso-roast-coffee-icon.jpg', 0, 'image 32', 1, 'PACKSHOT', 'SMALL', 1, 32, 'PRODUCT_SKU'),
(133, 'image ...', 'PS_IMG33', 'master-davey-set-blue-tiger-tea-book-icon.jpg', 1, 'image 33', 1, 'PACKSHOT', 'SMALL', 1, 33, 'PRODUCT_SKU'), 
(134, 'image ...', 'PS_IMG34', 'winter-dream-tea-icon.jpg', 0, 'image 34', 1, 'PACKSHOT', 'SMALL', 1, 34, 'PRODUCT_SKU'),
(135, 'image ...', 'PS_IMG35', 'peppermint-stick-tea-icon.jpg', 0, 'image 35', 1, 'PACKSHOT', 'SMALL', 1, 35, 'PRODUCT_SKU'), 
(136, 'image ...', 'PS_IMG36', 'los-angeles-sunshine-blend-tea-icon.jpg', 0, 'image 36', 1, 'PACKSHOT', 'SMALL', 1, 36, 'PRODUCT_SKU'), 
(137, 'image ...', 'PS_IMG37', 'georgia-peach-ginger-white-tea-icon.jpg', 0, 'image 37', 1, 'PACKSHOT', 'SMALL', 1, 37, 'PRODUCT_SKU'),
(138, 'image ...', 'PS_IMG38', 'jasmine-dragon-phoenix-pearl-tea-icon.jpg', 1, 'image 38', 1, 'PACKSHOT', 'SMALL', 1, 38, 'PRODUCT_SKU'), 
(139, 'image ...', 'PS_IMG39', 'genmaicha-green-tea-icon.jpg', 0, 'image 39', 1, 'PACKSHOT', 'SMALL', 1, 39, 'PRODUCT_SKU'),
(140, 'image ...', 'PS_IMG40', 'lung-ching-dragonwell-tea-icon.jpg', 0, 'image 40', 1, 'PACKSHOT', 'SMALL', 1, 40, 'PRODUCT_SKU'), 
(141, 'image ...', 'PS_IMG41', 'decaf-green-tea-icon.jpg', 0, 'image 41', 1, 'PACKSHOT', 'SMALL', 1, 41, 'PRODUCT_SKU'), 
(142, 'image ...', 'PS_IMG42', 'swedish-berries-tea-icon.jpg', 0, 'image 42', 1, 'PACKSHOT', 'SMALL', 1, 42, 'PRODUCT_SKU'),
(143, 'image ...', 'PS_IMG43', 'chai-rooibos-tea-icon.jpg', 1, 'image 43', 1, 'PACKSHOT', 'SMALL', 1, 43, 'PRODUCT_SKU'), 
(144, 'image ...', 'PS_IMG44', 'african-sunrise-tea-icon.jpg', 0, 'image 44', 1, 'PACKSHOT', 'SMALL', 1, 44, 'PRODUCT_SKU'),
(145, 'image ...', 'PS_IMG45', 'french-deluxe-vanilla-powder-icon.jpg', 0, 'image 45', 1, 'PACKSHOT', 'SMALL', 1, 45, 'PRODUCT_SKU'), 
(146, 'image ...', 'PS_IMG46', 'special-dutch-chocolate-powder-no-sugar-icon.jpg', 0, 'image 46', 1, 'PACKSHOT', 'SMALL', 1, 46, 'PRODUCT_SKU'), 
(147, 'image ...', 'PS_IMG47', 'americana-tumbler-icon.jpg', 0, 'image 47', 1, 'PACKSHOT', 'SMALL', 1, 47, 'PRODUCT_SKU'),
(148, 'image ...', 'PS_IMG48', 'bergamo-bottle-icon.jpg', 1, 'image 48', 1, 'PACKSHOT', 'SMALL', 1, 48, 'PRODUCT_SKU'), 
(149, 'image ...', 'PS_IMG49', 'the-jaidun-tumbler-icon.jpg', 0, 'image 49', 1, 'PACKSHOT', 'SMALL', 1, 49, 'PRODUCT_SKU'),
(150, 'image ...', 'PS_IMG50', 'bamboo-ceramic-tea-set-icon.jpg', 0, 'image 50', 1, 'PACKSHOT', 'SMALL', 1, 50, 'PRODUCT_SKU'),

(151, 'image ...', 'PS_IMG51', 'blueberry-struesel-coffee.jpg', 1, 'image 5l', 1, 'BACKGROUND', null, 1, 1, 'PRODUCT_SKU'), 
(152, 'image ...', 'PS_IMG52', 'bali-blue-moon-coffee.jpg', 0, 'image 52', 1, 'BACKGROUND', null, 1, 2, 'PRODUCT_SKU'), 
(153, 'image ...', 'PS_IMG53', 'world-art-tumbler.jpg', 0, 'image 53', 1, 'BACKGROUND', null, 1, 3, 'PRODUCT_SKU'), 
(154, 'image ...', 'PS_IMG54', 'brazil-cerrado-coffee.jpg', 0, 'image 54', 1, 'BACKGROUND', null, 1, 4, 'PRODUCT_SKU'), 
(155, 'image ...', 'PS_IMG55', 'house-blend-coffee.jpg', 1, 'image 55', 1, 'BACKGROUND', null, 1, 5, 'PRODUCT_SKU'), 
(156, 'image ...', 'PS_IMG56', 'decaf-house-blend-coffee.jpg', 0, 'image 56', 1, 'BACKGROUND', null, 1, 6, 'PRODUCT_SKU'), 
(157, 'image ...', 'PS_IMG57', 'house-blend-coffee-24-2oz-portion-packs.jpg', 0, 'image 57', 1, 'BACKGROUND', null, 1, 7, 'PRODUCT_SKU'), 
(158, 'image ...', 'PS_IMG58', 'ethiopia-yirgacheffe-coffee.jpg', 0, 'image 58', 1, 'BACKGROUND', null, 1, 8, 'PRODUCT_SKU'),
(159, 'image ...', 'PS_IMG59', 'kenya-aa-coffee.jpg', 1, 'image 59', 1, 'BACKGROUND', null, 1, 9, 'PRODUCT_SKU'), 
(160, 'image ...', 'PS_IMG60', 'colombia-narino-coffee.jpg', 0, 'image 60', 1, 'BACKGROUND', null, 1, 10, 'PRODUCT_SKU'), 
(161, 'image ...', 'PS_IMG61', 'costa-rica-cascada-tarrazu-coffee.jpg', 0, 'image 61', 1, 'BACKGROUND', null, 1, 11, 'PRODUCT_SKU'), 
(162, 'image ...', 'PS_IMG62', 'costa-rica-la-minita-tarrazu-coffee.jpg', 0, 'image 62', 1, 'BACKGROUND', null, 1, 12, 'PRODUCT_SKU'),
(163, 'image ...', 'PS_IMG63', 'decaf-colombia-narino-coffee.jpg', 1, 'image 63', 1, 'BACKGROUND', null, 1, 13, 'PRODUCT_SKU'), 
(164, 'image ...', 'PS_IMG64', 'guatemala-antigua-coffee.jpg', 0, 'image 64', 1, 'BACKGROUND', null, 1, 14, 'PRODUCT_SKU'),
(165, 'image ...', 'PS_IMG65', 'mocha-java-coffee.jpg', 0, 'image 65', 1, 'BACKGROUND', null, 1, 15, 'PRODUCT_SKU'), 
(166, 'image ...', 'PS_IMG66', 'papua-new-guinea-sigri-coffee.jpg', 0, 'image 66', 1, 'BACKGROUND', null, 1, 16, 'PRODUCT_SKU'), 
(167, 'image ...', 'PS_IMG67', 'sumatra-mandheling-coffee.jpg', 0, 'image 67', 1, 'BACKGROUND', null, 1, 17, 'PRODUCT_SKU'),
(168, 'image ...', 'PS_IMG68', 'breakfast-blend-coffee.jpg', 1, 'image 68', 1, 'BACKGROUND', null, 1, 18, 'PRODUCT_SKU'), 
(169, 'image ...', 'PS_IMG69', 'colombia-narino-coffee-24-2oz-portion-packs.jpg', 0, 'image 69', 1, 'BACKGROUND', null, 1, 19, 'PRODUCT_SKU'),
(170, 'image ...', 'PS_IMG70', 'costa-rica-coffee-24-2oz-portion-packs.jpg', 0, 'image 70', 1, 'BACKGROUND', null, 1, 20, 'PRODUCT_SKU'), 
(171, 'image ...', 'PS_IMG71', 'holiday-blend-coffee.jpg', 0, 'image 71', 1, 'BACKGROUND', null, 1, 21, 'PRODUCT_SKU'), 
(172, 'image ...', 'PS_IMG72', 'bali-blue-moon-coffee.jpg', 0, 'image 72', 1, 'BACKGROUND', null, 1, 22, 'PRODUCT_SKU'),
(173, 'image ...', 'PS_IMG73', 'university-blend-coffee.jpg', 1, 'image 73', 1, 'BACKGROUND', null, 1, 23, 'PRODUCT_SKU'), 
(174, 'image ...', 'PS_IMG74', 'harvest-blend-coffee.jpg', 0, 'image 74', 1, 'BACKGROUND', null, 1, 24, 'PRODUCT_SKU'),
(175, 'image ...', 'PS_IMG75', 'decaf-espesso-roast-coffee.jpg', 0, 'image 75', 1, 'BACKGROUND', null, 1, 25, 'PRODUCT_SKU'), 
(176, 'image ...', 'PS_IMG76', 'decaf-french-roast-coffee.jpg', 0, 'image 76', 1, 'BACKGROUND', null, 1, 26, 'PRODUCT_SKU'), 
(177, 'image ...', 'PS_IMG77', 'decaf-viennese-coffee.jpg', 0, 'image 77', 1, 'BACKGROUND', null, 1, 27, 'PRODUCT_SKU'),
(178, 'image ...', 'PS_IMG78', 'french-roast-coffee.jpg', 1, 'image 78', 1, 'BACKGROUND', null, 1, 28, 'PRODUCT_SKU'), 
(179, 'image ...', 'PS_IMG79', 'italian-roast-coffee.jpg', 0, 'image 79', 1, 'BACKGROUND', null, 1, 29, 'PRODUCT_SKU'),
(180, 'image ...', 'PS_IMG80', 'tanzania-peaberry-coffee.jpg', 0, 'image 80', 1, 'BACKGROUND', null, 1, 30, 'PRODUCT_SKU'), 
(181, 'image ...', 'PS_IMG81', 'colombia-narino-dark-coffee.jpg', 0, 'image 81', 1, 'BACKGROUND', null, 1, 31, 'PRODUCT_SKU'), 
(182, 'image ...', 'PS_IMG82', 'espresso-roast-coffee.jpg', 0, 'image 82', 1, 'BACKGROUND', null, 1, 32, 'PRODUCT_SKU'),
(183, 'image ...', 'PS_IMG83', 'master-davey-set-blue-tiger-tea-book.jpg', 1, 'image 83', 1, 'BACKGROUND', null, 1, 33, 'PRODUCT_SKU'), 
(184, 'image ...', 'PS_IMG84', 'winter-dream-tea.jpg', 0, 'image 84', 1, 'BACKGROUND', null, 1, 34, 'PRODUCT_SKU'),
(185, 'image ...', 'PS_IMG85', 'peppermint-stick-tea.jpg', 0, 'image 85', 1, 'BACKGROUND', null, 1, 35, 'PRODUCT_SKU'), 
(186, 'image ...', 'PS_IMG86', 'los-angeles-sunshine-blend-tea.jpg', 0, 'image 86', 1, 'BACKGROUND', null, 1, 36, 'PRODUCT_SKU'), 
(187, 'image ...', 'PS_IMG87', 'georgia-peach-ginger-white-tea.jpg', 0, 'image 87', 1, 'BACKGROUND', null, 1, 37, 'PRODUCT_SKU'),
(188, 'image ...', 'PS_IMG88', 'jasmine-dragon-phoenix-pearl-tea.jpg', 1, 'image 88', 1, 'BACKGROUND', null, 1, 38, 'PRODUCT_SKU'), 
(189, 'image ...', 'PS_IMG89', 'genmaicha-green-tea.jpg', 0, 'image 89', 1, 'BACKGROUND', null, 1, 39, 'PRODUCT_SKU'),
(190, 'image ...', 'PS_IMG90', 'lung-ching-dragonwell-tea.jpg', 0, 'image 90', 1, 'BACKGROUND', null, 1, 40, 'PRODUCT_SKU'), 
(191, 'image ...', 'PS_IMG91', 'decaf-green-tea.jpg', 0, 'image 91', 1, 'BACKGROUND', null, 1, 41, 'PRODUCT_SKU'), 
(192, 'image ...', 'PS_IMG92', 'swedish-berries-tea.jpg', 0, 'image 92', 1, 'BACKGROUND', null, 1, 42, 'PRODUCT_SKU'),
(193, 'image ...', 'PS_IMG93', 'chai-rooibos-tea.jpg', 1, 'image 93', 1, 'BACKGROUND', null, 1, 43, 'PRODUCT_SKU'), 
(194, 'image ...', 'PS_IMG94', 'african-sunrise-tea.jpg', 0, 'image 94', 1, 'BACKGROUND', null, 1, 44, 'PRODUCT_SKU'),
(195, 'image ...', 'PS_IMG95', 'french-deluxe-vanilla-powder.jpg', 0, 'image 95', 1, 'BACKGROUND', null, 1, 45, 'PRODUCT_SKU'), 
(196, 'image ...', 'PS_IMG96', 'special-dutch-chocolate-powder-no-sugar.jpg', 0, 'image 96', 1, 'BACKGROUND', null, 1, 46, 'PRODUCT_SKU'), 
(197, 'image ...', 'PS_IMG97', 'americana-tumbler.jpg', 0, 'image 97', 1, 'BACKGROUND', null, 1, 47, 'PRODUCT_SKU'),
(198, 'image ...', 'PS_IMG98', 'bergamo-bottle.jpg', 1, 'image 98', 1, 'BACKGROUND', null, 1, 48, 'PRODUCT_SKU'), 
(199, 'image ...', 'PS_IMG99', 'the-jaidun-tumbler.jpg', 0, 'image 99', 1, 'BACKGROUND', null, 1, 49, 'PRODUCT_SKU'),
(200, 'image ...', 'PS_IMG100', 'bamboo-ceramic-tea-set.jpg', 0, 'image 100', 1, 'BACKGROUND', null, 1, 50, 'PRODUCT_SKU');


-- DELIVERY METHODS

INSERT INTO teco_delivery_method  
(id, code, name, description, version)
 VALUES 
(10, 'UPS', 'UPS', 'UPS', 1), 
(20, 'CHONOPOST', 'Chronopost', 'Chronopost', 1), 
(30, 'COLISSIMO', 'Collisimo', 'Collisimo', 1);

INSERT INTO teco_market_area_delivery_method_rel  
(market_area_id, delivery_method_id)
 VALUES 
(1, 10),
(1, 20),
(1, 30);

INSERT INTO teco_delivery_method_price  
(id, market_area_id, price, retailer_id, currency_id, delivery_method_id)
VALUES 
(10, 1, 14.35, 1, 150, 10),
(20, 1, 15.35, 1, 150, 20),
(30, 1, 16.35, 1, 150, 30);

-- RULE | PROMO

-- ORDER
INSERT INTO teco_order_number  
(id, last_order_number, version)
VALUES 
(1, 1000, 1);
