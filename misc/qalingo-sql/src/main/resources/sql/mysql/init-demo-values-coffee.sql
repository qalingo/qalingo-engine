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
(id, version, code, email, title, firstname, lastname, password, login, is_active, company_id)
VALUES (1, 1, 'admin', 'qalingo@qalingo.com', 'MR', 'Admin', 'Qalingo', 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58', 'admin', 1, 1);

INSERT INTO tbo_user_group_rel VALUES (1, 10),(1,20);

-- ECO GLOBAL

-- password equal "password"
INSERT INTO teco_customer 
(id, version, email, title, firstname, lastname, password, login, code, is_active)
VALUES (1, 1, 'qalingo@qalingo.com', 'MR', 'Customer', 'Qalingo', 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58', 'customer', 'CUST1', 1);

INSERT INTO teco_customer_address 
(id, title, firstname, lastname, address1, address2, additional_information, address_name, city, postal_code, country_code, is_default_billing, is_default_shipping, customer_id)
VALUES (1, 'MR', 'Customer', 'Qalingo', 'rue de versaille', '', '', 'address1', 'Paris', '75000', 'FR', 1, 1, 1);

INSERT INTO teco_customer_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, customer_id, attribute_definition_id, localization_code, market_area_id)
VALUES (1, null, null, null, null, null, 'SCREEN NAME ATTRIBUTE', 1, 4000, null, null);

INSERT INTO teco_customer_credential 
(id, customer_id, password)
VALUES (1, 1, 'c25f6e969040c60ca4598072d13d26a0539013a6f43fedb44362fe757683ebc43931ab8cd1f78f58');

INSERT INTO teco_cust_group_rel 
(customer_id, group_id) 
VALUES (1, 10);


-- ECO CATALOGUE

INSERT INTO teco_catalog_virtual 
(id, description, code, is_default, name, version, master_catalog_id)
 VALUES 
( 1000, 'Virtual Catalog description', 'V_CAT_INT', 0, 'Virtuel Catalog International', 1, 1),
( 2000, 'Virtual Catalog description', 'V_CAT_FRA', 0, 'Virtuel Catalog France', 1, 1),
( 3000, 'Virtual Catalog description', 'V_CAT_ESP', 0, 'Virtuel Catalog Espagne', 1, 1),
( 4000, 'Virtual Catalog description', 'V_CAT_USA', 0, 'Virtuel Catalog United-States', 1, 1),
( 5000, 'Virtual Catalog description', 'V_CAT_CAN', 0, 'Virtuel Catalog Canada', 1, 1),
( 6000, 'Virtual Catalog description', 'V_CAT_BRA', 0, 'Virtuel Catalog Brazil', 1, 1),
( 7000, 'Virtual Catalog description', 'V_CAT_ARG', 0, 'Virtuel Catalog Argentina', 1, 1),
( 8000, 'Virtual Catalog description', 'V_CAT_CHN', 0, 'Virtuel Catalog China', 1, 1),
( 9000, 'Virtual Catalog description', 'V_CAT_JPN', 0, 'Virtuel Catalog Japan', 1, 1),
(10000, 'Virtual Catalog description', 'V_CAT_VNM', 0, 'Virtuel Catalog Vietnam', 1, 1);

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
(id, context, short_string_value, market_place_id, attribute_definition_id)
VALUES 
(161, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 1, 8000),
(162, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 1, 8000),
(1161, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 10, 8000),
(1162, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 10, 8000),
(1261, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 20, 8000),
(1262, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 20, 8000),
(1361, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 30, 8000),
(1362, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 30, 8000);

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
(  1, 'Market INT description', 'market area 1 : INT', 'INT', null, null, 1, 1,  1,  1000, 1, 1, 150, '-30.000', '45.000'),
(101, 'Market FRA description', 'market area 2 : FRA', 'FRA', null, 'FR', 1, 1, 10,  2000, 2, 1, 45, '48.480', '2.200'),	
(102, 'Market ESP description', 'market area 3 : ESP', 'ESP', null, 'ES', 1, 1, 10,  3000, 3, 1, 45, '40.260', '3.420'),	
(201, 'Market USA description', 'market area 4 : USA', 'USA', null, 'US', 1, 1, 20,  4000, 1, 1, 150, '40.000', '-90.000'),
(202, 'Market CAN description', 'market area 4 : CAN', 'CAN', null, 'CA', 0, 1, 20,  5000, 1, 1, 27, '55.000', '-90.000'),
(210, 'Market BRA description', 'market area 2 : BRA', 'BRA', null, 'BR', 1, 1, 21,  6000, 8, 1, 21, '-22.570', '-43.120'),
(211, 'Market ARG description', 'market area 2 : ARG', 'ARG', null, 'AR', 1, 1, 21,  7000, 3, 1, 8, '-34.350', '-58.220'),
(301, 'Market CHN description', 'market area 5 : CHN', 'CHN', null, 'CN', 0, 1, 30,  8000, 9, 1, 31, '121.280', '31.100'),
(302, 'Market JPN description', 'market area 6 : JPN', 'JPN', null, 'JP', 1, 1, 30,  9000, 7, 1, 72, '35.400', '139.450'),
(303, 'Market VNM description', 'market area 7 : VNM', 'VNM', null, 'VN', 0, 1, 30, 10000, 13, 1, 154, '10.762622', '106.660172');

/*
(510, 'BO_BUSINESS',  'bo-business.dev.qalingo.com', 1, 5),
(511, 'BO_REPORTING', 'bo-reporting.dev.qalingo.com', 1, 5),
(512, 'BO_TECHNICAL', 'bo-technical.dev.qalingo.com', 1, 5),
INSERT INTO teco_market_area_attribute   
(id, context, short_string_value, market_area_id, attribute_definition_id)
VALUES 
(513, 'FO_MCOMMERCE', 'fo-mcommerce.dev.qalingo.com', 1, 5),
(514, 'FO_PREHOME',   'fo-prehome.dev.qalingo.com', 1, 5);
*/

INSERT INTO teco_market_area_attribute   
(id, context, short_string_value, market_area_id, attribute_definition_id)
VALUES 
(10, 'DEFAULT_CONTEXT', 'no-reply@YOURDOMAIN.com', 1, 1),
(11, 'DEFAULT_CONTEXT', 'Demo Qalingo', 1, 2),
(12, 'DEFAULT_CONTEXT', 'contact@YOURDOMAIN.com', 1, 3);

INSERT INTO teco_market_area_attribute   
(id, context, short_string_value, market_area_id, attribute_definition_id)
VALUES 
(20, 'DEFAULT_CONTEXT', true, 1, 7);


INSERT INTO teco_market_area_attribute   
(id, context, short_string_value, market_area_id, attribute_definition_id)
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
(id, name, code, address1, address2, additional_information, postal_code, city, state_code, country_code, latitude, longitude, type, version, retailer_id)
 VALUES 
--(10, 'Store New-York', 'STRNYC', '57th Street & Lexington', '','', '', 'New York', '', 'US', '40.667', '-73.633', 'SHOP', 1),
--(20, 'Store Paris', 'STRPARIS', '85 avenue Lafayette', '', '','', 'Paris', '', 'FR', '48.833', '2.333', 'SHOP,CORNER', 1),
--add
(10, 'The Coffee Bean & Tea Leaf', 'STRTO1', '1772 A East Avenida De Los Arboles', '', '','91362', 'Thousand Oaks', 'CA', 'US', '34.224294', '-118.844712', 'SHOP', 1, 1),
(20, 'The Coffee Bean & Tea Leaf', 'STRTO2', '487 North Moorpark Road #3', '', '','91360', 'Thousand Oaks', 'CA', 'US', '34.185997', '-118.875596', 'SHOP,CORNER', 1, 1),
(30, 'The Coffee Bean & Tea Leaf', 'STRTO3', '278 West Hillcrest Drive', '', '','91360', 'Thousand Oaks', 'CA', 'US', '34.185926', '-118.888599', 'SHOP,CORNER', 1, 1),

(40, 'The Coffee Bean & Tea Leaf', 'STRTO4', '968 S Westlake Blvd #6', '', '','91361', 'Westlake Village', 'CA', 'US', '34.154077', '-118.826673', 'SHOP,CORNER', 1, 1),

(50, 'The Coffee Bean & Tea Leaf', 'STRLA1', '209 South Mednik Avenue', '', '','90022', 'Los Angeles', 'CA', 'US', '34.049815', '-118.162873', 'SHOP,CORNER', 1, 1),

(60, 'The Coffee Bean & Tea Leaf', 'STRLA2', '2081 Hillhurst Avenue', '', '','90027', 'Los Angeles', 'CA', 'US', '34.126016', '-118.286469', 'SHOP,CORNER', 1, 1),

(70, 'The Coffee Bean & Tea Leaf', 'STRLA3', '11698 San Vicente Boulevard', '', '','90049', 'Los Angeles', 'CA', 'US', '34.069725', '-118.46225', 'SHOP,CORNER', 1, 1),

(80, 'The Coffee Bean & Tea Leaf', 'STRLA4', '6333 West 3rd Street, E-11, Farmers Market', '', '','90036', 'Los Angeles', 'CA', 'US', '34.088493', '-118.362687', 'SHOP,CORNER', 1, 1),

(90, 'The Coffee Bean & Tea Leaf', 'STRLA5', 'Exposition Blvd', '', '','90007', 'Los Angeles', 'CA', 'US', '34.034453', '-118.284409', 'SHOP,CORNER', 1, 1),

(100, 'Le Tropic Café', 'STRPARIS1', '66 Rue des Lombards', '', '','75001', 'Paris', '75', 'FR', '48.8599562', '2.3473762', 'SHOP,CORNER', 1, 1),
(110, "Le Fouquet's", 'STRPARIS2', '99 Avenue des Champs-Élysées', '', '','75008', 'Paris', '75', 'FR', '48.8719523', '2.3008841', 'SHOP,CORNER', 1, 1),
(120, 'ESPRESSAMENTE FRANCE', 'STRPARIS3', '13 Rue Auber', '', '','75009', 'Paris', '75', 'FR', '48.875667', '2.327873', 'SHOP,CORNER', 1, 1),

(130, 'The Coffee Bean & Tea Leaf', 'STRTX1', '1000 E. 41st Street, Suite G-100', '', '','', 'Austin', ' TX', 'US', '30.299852', '-97.721888', 'SHOP,CORNER', 1, 1),
(140, 'The Coffee Bean & Tea Leaf', 'STRNV1', '4321 West Flamingo Road', '', '','', 'Las Vegas', 'NV', 'US', '36.113471', '-115.194826', 'SHOP,CORNER', 1, 1),
(150, 'The Coffee Bean & Tea Leaf', 'STRAZ1', '4032 North Miller Road #102', '', '','', 'Scottsdale', 'AZ', 'US', '33.494435', '-111.917612', 'SHOP,CORNER', 1, 1),
(160, 'The Coffee Bean & Tea Leaf', 'STRAZ2', '2000 East Rio Salado Parkway', '', '','', 'Tempe', 'AZ', 'US', '33.432981', '-111.904298', 'SHOP,CORNER', 1, 1),
(170, 'The Coffee Bean & Tea Leaf', 'STRNY1', '1412 Broadway', '', '','', 'New York', 'NY', 'US', '40.753629', '-73.986939', 'SHOP,CORNER', 1, 1),
(180, 'The Coffee Bean & Tea Leaf', 'STRVN1', '235 Đồng Khởi, Bến Nghé, 1', '', '','', 'Ho Chi Minh', '', 'VN', '10.778864', '106.699859', 'SHOP,CORNER', 1, 1),
(190, 'The Coffee Bean & Tea Leaf', 'STRVN2', '12-14, Phường Bến Nghé, District 1 (Quan 1), Thành phố', '', '','', 'Ho Chi Minh', '', 'VN', '10.779886', '106.70397', 'SHOP,CORNER', 1, 1),
(200, 'The Coffee Bean & Tea Leaf', 'STRSG1', '51 Bras Basah Rd', '', '','', 'Singapore', '', 'SG', '1.297665', '103.850053', 'SHOP,CORNER', 1, 1),
(210, 'The Coffee Bean & Tea Leaf', 'STRSG2', 'Food Opera ION Orchard', '', '','', 'Singapore', '', 'SG', '1.303994', '103.832033', 'SHOP,CORNER', 1, 1);
--(180, 'ESPRESSAMENTE FRANCE', 'STRPARIS3', '13 Rue Auber 75009 Paris, France +33 1 42 66 13 85', '', '','', 'Paris', '', 'FR', '48.875667', '2.327873', 'SHOP,CORNER', 1),
--(190, 'ESPRESSAMENTE FRANCE', 'STRPARIS3', '13 Rue Auber 75009 Paris, France +33 1 42 66 13 85', '', '','', 'Paris', '', 'FR', '48.875667', '2.327873', 'SHOP,CORNER', 1),
--(200, 'ESPRESSAMENTE FRANCE', 'STRPARIS3', '13 Rue Auber 75009 Paris, France +33 1 42 66 13 85', '', '','', 'Paris', '', 'FR', '48.875667', '2.327873', 'SHOP,CORNER', 1),
--(210, 'ESPRESSAMENTE FRANCE', 'STRPARIS3', '13 Rue Auber 75009 Paris, France +33 1 42 66 13 85', '', '','', 'Paris', '', 'FR', '48.875667', '2.327873', 'SHOP,CORNER', 1);


INSERT INTO teco_store_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, store_id, attribute_definition_id, localization_code, market_area_id)
VALUES 
(10, null, null, null, null, null, 'Thousand Oaks', 10, 5020, 'en', null),
(11, null, null, null, null, null, 'Thousand Oaks', 10, 5020, 'fr', null),

(20, null, null, null, null, null, 'Thousand Oaks', 20, 5020, 'en', null),
(21, null, null, null, null, null, 'Thousand Oaks', 20, 5020, 'fr', null),

(30, null, null, null, null, null, 'Thousand Oaks', 30, 5020, 'en', null),
(31, null, null, null, null, null, 'Thousand Oaks', 30, 5020, 'fr', null),

(40, null, null, null, null, null, 'Thousand Oaks', 40, 5020, 'en', null),
(41, null, null, null, null, null, 'Thousand Oaks', 40, 5020, 'fr', null),

(50, null, null, null, null, null, 'Los Angeles', 50, 5020, 'en', null),
(51, null, null, null, null, null, 'Los Angeles', 50, 5020, 'fr', null),

(60, null, null, null, null, null, 'Los Angeles', 60, 5020, 'en', null),
(61, null, null, null, null, null, 'Los Angeles', 60, 5020, 'fr', null),

(70, null, null, null, null, null, 'Los Angeles', 70, 5020, 'en', null),
(71, null, null, null, null, null, 'Los Angeles', 70, 5020, 'fr', null),

(80, null, null, null, null, null, 'Los Angeles', 80, 5020, 'en', null),
(81, null, null, null, null, null, 'Los Angeles', 80, 5020, 'fr', null),

(90, null, null, null, null, null, 'Los Angeles', 90, 5020, 'en', null),
(91, null, null, null, null, null, 'Los Angeles', 90, 5020, 'fr', null),

(100, null, null, null, null, null, 'Paris', 100, 5020, 'en', null),
(101, null, null, null, null, null, 'Paris', 100, 5020, 'fr', null),

(110, null, null, null, null, null, 'Paris', 110, 5020, 'en', null),
(111, null, null, null, null, null, 'Paris', 110, 5020, 'fr', null),

(120, null, null, null, null, null, 'Paris', 120, 5020, 'en', null),
(121, null, null, null, null, null, 'Paris', 120, 5020, 'fr', null),

(130, null, null, null, null, null, 'Texas', 130, 5020, 'en', null),
(131, null, null, null, null, null, 'Texas', 130, 5020, 'fr', null),

(140, null, null, null, null, null, 'Nevada', 140, 5020, 'en', null),
(141, null, null, null, null, null, 'Nevada', 140, 5020, 'fr', null),

(150, null, null, null, null, null, 'Arizona', 150, 5020, 'en', null),
(151, null, null, null, null, null, 'Arizona', 150, 5020, 'fr', null),

(160, null, null, null, null, null, 'Arizona', 160, 5020, 'en', null),
(161, null, null, null, null, null, 'Arizona', 160, 5020, 'fr', null),

(170, null, null, null, null, null, 'New York', 170, 5020, 'en', null),
(171, null, null, null, null, null, 'New York', 170, 5020, 'fr', null),

(180, null, null, null, null, null, 'Ho Chi Minh', 180, 5020, 'en', null),
(181, null, null, null, null, null, 'Ho Chi Minh', 180, 5020, 'fr', null),

(190, null, null, null, null, null, 'Ho Chi Minh', 190, 5020, 'en', null),
(191, null, null, null, null, null, 'Ho Chi Minh', 190, 5020, 'fr', null),

(200, null, null, null, null, null, 'Singapore', 200, 5020, 'en', null),
(201, null, null, null, null, null, 'Singapore', 200, 5020, 'fr', null),

(210, null, null, null, null, null, 'Singapore', 210, 5020, 'en', null),
(211, null, null, null, null, null, 'Singapore', 210, 5020, 'fr', null);

-- insert Operation Hour

INSERT INTO teco_store_business_hour
(closing_date_end, closing_date_start, end_hour, start_hour, monday, tuesday, wednesday, thursday, friday, saturday, sunday, store_id)
VALUES
--- store id 10
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 10),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 10),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 10),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 10),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 10),
--- store id 20
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 20),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 20),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 20),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 20),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 20),
--- store id 30
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 30),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 30),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 30),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 30),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 30),
--- store id 40
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 40),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 40),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 40),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 40),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 40),
--- store id 50
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 50),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 50),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 50),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 50),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 50),
--- store id 60
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 60),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 60),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 60),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 60),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 60),
--- store id 70
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 70),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 70),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 70),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 70),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 70),
--- store id 80
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 80),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 80),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 80),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 80),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 80),
--- store id 90
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 90),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 90),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 90),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 90),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 90),
--- store id 100
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 100),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 100),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 100),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 100),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 100),
--- store id 110
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 110),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 110),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 110),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 110),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 110),
--- store id 120
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 120),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 120),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 120),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 120),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 120),
--- store id 130
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 130),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 130),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 130),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 130),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 130),
--- store id 140
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 140),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 140),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 140),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 140),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 140),
--- store id 150
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 150),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 150),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 150),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 150),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 150),
--- store id 160
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 160),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 160),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 160),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 160),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 160),
--- store id 170
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 170),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 170),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 170),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 170),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 170),
--- store id 180
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 180),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 180),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 180),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 180),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 180),
--- store id 190
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 190),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 190),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 190),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 190),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 190),
--- store id 200
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 200),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 200),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 200),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 200),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 200),
--- store id 210
('6', '2', '10.00:PM', '8.00:AM', 1,0,0,0,0,0,0, 210),
('6', '2', '11.00:PM', '9.00:AM', 0,1,0,0,0,0,0, 210),
('6', '2', '12.00:PM', '10.00:AM', 0,0,1,0,0,0,0, 210),
('6', '2', '8.00:PM', '8.00:AM', 0,0,0,1,0,0,0, 210),
('6', '2', '7.00:PM', '9.00:AM', 0,0,0,0,1,0,0, 210);

-- STORE ASSET
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, store_id, scope)
 VALUES 
(1011, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'STORE'), 
(1012, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 10', 1, 'ICON', null, 1, 10, 'STORE'),
(1013, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 10', 1, 'SLIDESHOW', null, 1, 10, 'STORE'),
(1014, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 10', 1, 'SLIDESHOW', null, 1, 10, 'STORE'),
(1015, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 10', 1, 'SLIDESHOW', null, 1, 10, 'STORE'),
(1016, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 10', 1, 'SLIDESHOW', null, 1, 10, 'STORE'),
(1017, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 10', 1, 'SLIDESHOW', null, 1, 10, 'STORE'),

(1021, 'image ...', 'store-1-img.png', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 20, 'STORE'), 
(1022, 'image ...', 'store-1-img-icon.png', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 20, 'STORE'),
(1023, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),
(1024, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),
(1025, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),
(1026, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),
(1027, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),
(1028, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),
(1029, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 20, 'STORE'),


(1031, 'image ...', '278-west-hillcrest.jpg', 0, 'image 1 store 30', 1, 'PACKSHOT', 'SMALL', 1, 30, 'STORE'), 
(1032, 'image ...', '278-west-hillcrest-icon.jpg', 0, 'icon 1 store 30', 1, 'ICON', null, 1, 30, 'STORE'),
(1033, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 30, 'STORE'),
(1034, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 30, 'STORE'),
(1035, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 30, 'STORE'),
(1036, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 30, 'STORE'),
(1037, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 30, 'STORE'),
(1038, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 30, 'STORE'),

(1041, 'image ...', 'westlake-blvd.jpg', 0, 'image 1 store 40', 1, 'PACKSHOT', 'SMALL', 1, 40, 'STORE'), 
(1042, 'image ...', 'westlake-blvd-icon.jpg', 0, 'icon 1 store 40', 1, 'ICON', null, 1, 40, 'STORE'),
(1043, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 40, 'STORE'),
(1044, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 40, 'STORE'),
(1045, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 40, 'STORE'),
(1046, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 40, 'STORE'),
(1047, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 40, 'STORE'),
(1048, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 40, 'STORE'),

(1051, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 50, 'STORE'), 
(1052, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 50, 'STORE'),
(1053, 'image slide show', 'store-full2', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 50, 'STORE'),
(1054, 'image ...', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 50, 'STORE'),
(1055, 'image ...', 'store-full1.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 50, 'STORE'),
(1056, 'image ...', 'store-full4.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 50, 'STORE'),
(1057, 'image ...', 'store-full5.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 50, 'STORE'),
(1058, 'image ...', 'store-full6.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 50, 'STORE'),

(1061, 'image ...', 'store-1-img.png', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 60, 'STORE'), 
(1062, 'image ...', 'store-1-img-icon.png', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 60, 'STORE'),
(1063, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 60, 'STORE'),
(1064, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 60, 'STORE'),
(1065, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 60, 'STORE'),
(1066, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 60, 'STORE'),
(1067, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 60, 'STORE'),


(1071, 'image ...', 'san-vicente-boulevard.jpg', 0, 'image 1 store 30', 1, 'PACKSHOT', 'SMALL', 1, 70, 'STORE'), 
(1072, 'image ...', 'san-vicente-boulevard-icon.jpg', 0, 'icon 1 store 30', 1, 'ICON', null, 1, 70, 'STORE'),
(1073, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 70, 'STORE'),
(1074, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 70, 'STORE'),
(1075, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 70, 'STORE'),
(1076, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 70, 'STORE'),
(1077, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 70, 'STORE'),
(1078, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 30', 1, 'SLIDESHOW', null, 1, 70, 'STORE'),

(1081, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 40', 1, 'PACKSHOT', 'SMALL', 1, 80, 'STORE'), 
(1082, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 40', 1, 'ICON', null, 1, 80, 'STORE'),
(1083, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 80, 'STORE'),
(1084, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 80, 'STORE'),
(1085, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 80, 'STORE'),
(1086, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 80, 'STORE'),
(1087, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 80, 'STORE'),
(1088, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 40', 1, 'SLIDESHOW', null, 1, 80, 'STORE'),

(1091, 'image ...', 'exposition-blvd.jpg', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 90, 'STORE'), 
(1092, 'image ...', 'exposition-blvd-icon.jpg', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 90, 'STORE'),
(1093, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 90, 'STORE'),
(1094, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 90, 'STORE'),
(1095, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 90, 'STORE'),
(1096, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 90, 'STORE'),
(1097, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 90, 'STORE'),
(1098, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 90, 'STORE'),

(10101, 'image ...', 'le-tropic-cafe.jpg', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 100, 'STORE'), 
(10102, 'image ...', 'le-tropic-cafe-icon.jpg', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 100, 'STORE'),
(10103, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 100, 'STORE'),
(10104, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 100, 'STORE'),
(10105, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 100, 'STORE'),
(10106, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 100, 'STORE'),
(10107, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 100, 'STORE'),
(10108, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 100, 'STORE'),

(10111, 'image ...', 'le-fouquets.jpg', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 110, 'STORE'), 
(10112, 'image ...', 'le-fouquets-icon.jpg', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 110, 'STORE'),
(10113, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 110, 'STORE'),
(10114, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 110, 'STORE'),
(10115, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 110, 'STORE'),
(10116, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 110, 'STORE'),
(10117, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 110, 'STORE'),
(10118, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 110, 'STORE'),

(10121, 'image ...', 'espressamente-france.jpg', 0, 'image 1 store 20', 1, 'PACKSHOT', 'SMALL', 1, 120, 'STORE'), 
(10122, 'image ...', 'espressamente-france-icon.jpg', 0, 'icon 1 store 20', 1, 'ICON', null, 1, 120, 'STORE'),
(10123, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 120, 'STORE'),
(10124, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 120, 'STORE'),
(10125, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 120, 'STORE'),
(10126, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 120, 'STORE'),
(10127, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 120, 'STORE'),
(10128, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 20', 1, 'SLIDESHOW', null, 1, 120, 'STORE'),

(10131, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 130', 1, 'PACKSHOT', 'SMALL', 1, 130, 'STORE'), 
(10132, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 130', 1, 'ICON', null, 1, 130, 'STORE'),
(10133, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),
(10134, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),
(10135, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),
(10136, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),
(10137, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),
(10138, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),
(10139, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 130', 1, 'SLIDESHOW', null, 1, 130, 'STORE'),

(10141, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 140', 1, 'PACKSHOT', 'SMALL', 1, 140, 'STORE'), 
(10142, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 140', 1, 'ICON', null, 1, 140, 'STORE'),
(10143, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 140', 1, 'SLIDESHOW', null, 1, 140, 'STORE'),
(10144, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 140', 1, 'SLIDESHOW', null, 1, 140, 'STORE'),
(10145, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 140', 1, 'SLIDESHOW', null, 1, 140, 'STORE'),
(10146, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 140', 1, 'SLIDESHOW', null, 1, 140, 'STORE'),
(10147, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 140', 1, 'SLIDESHOW', null, 1, 140, 'STORE'),
(10148, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 140', 1, 'SLIDESHOW', null, 1, 140, 'STORE'),

(10151, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 150', 1, 'PACKSHOT', 'SMALL', 1, 150, 'STORE'), 
(10152, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 150', 1, 'ICON', null, 1, 150, 'STORE'),
(10153, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 150', 1, 'SLIDESHOW', null, 1, 150, 'STORE'),
(10154, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 150', 1, 'SLIDESHOW', null, 1, 150, 'STORE'),
(10155, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 150', 1, 'SLIDESHOW', null, 1, 150, 'STORE'),
(10156, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 150', 1, 'SLIDESHOW', null, 1, 150, 'STORE'),
(10157, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 150', 1, 'SLIDESHOW', null, 1, 150, 'STORE'),
(10158, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 150', 1, 'SLIDESHOW', null, 1, 150, 'STORE'),

(10161, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 160', 1, 'PACKSHOT', 'SMALL', 1, 160, 'STORE'), 
(10162, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 160', 1, 'ICON', null, 1, 160, 'STORE'),
(10163, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),
(10164, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),
(10165, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),
(10166, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),
(10167, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),
(10168, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),
(10169, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 160', 1, 'SLIDESHOW', null, 1, 160, 'STORE'),

(10171, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 170', 1, 'PACKSHOT', 'SMALL', 1, 170, 'STORE'), 
(10172, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 170', 1, 'ICON', null, 1, 170, 'STORE'),
(10173, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 170', 1, 'SLIDESHOW', null, 1, 170, 'STORE'),
(10174, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 170', 1, 'SLIDESHOW', null, 1, 170, 'STORE'),
(10175, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 170', 1, 'SLIDESHOW', null, 1, 170, 'STORE'),
(10176, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 170', 1, 'SLIDESHOW', null, 1, 170, 'STORE'),
(10177, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 170', 1, 'SLIDESHOW', null, 1, 170, 'STORE'),
(10178, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 170', 1, 'SLIDESHOW', null, 1, 170, 'STORE'),

(10181, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 180', 1, 'PACKSHOT', 'SMALL', 1, 180, 'STORE'), 
(10182, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 180', 1, 'ICON', null, 1, 180, 'STORE'),
(10183, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 180', 1, 'SLIDESHOW', null, 1, 180, 'STORE'),
(10184, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 180', 1, 'SLIDESHOW', null, 1, 180, 'STORE'),
(10185, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 180', 1, 'SLIDESHOW', null, 1, 180, 'STORE'),
(10186, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 180', 1, 'SLIDESHOW', null, 1, 180, 'STORE'),
(10187, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 180', 1, 'SLIDESHOW', null, 1, 180, 'STORE'),
(10188, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 180', 1, 'SLIDESHOW', null, 1, 180, 'STORE'),

(10191, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 190', 1, 'PACKSHOT', 'SMALL', 1, 190, 'STORE'), 
(10192, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 190', 1, 'ICON', null, 1, 190, 'STORE'),
(10193, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 190', 1, 'SLIDESHOW', null, 1, 190, 'STORE'),
(10194, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 190', 1, 'SLIDESHOW', null, 1, 190, 'STORE'),
(10195, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 190', 1, 'SLIDESHOW', null, 1, 190, 'STORE'),
(10196, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 190', 1, 'SLIDESHOW', null, 1, 190, 'STORE'),
(10197, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 190', 1, 'SLIDESHOW', null, 1, 190, 'STORE'),
(10198, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 190', 1, 'SLIDESHOW', null, 1, 190, 'STORE'),

(10201, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 200', 1, 'PACKSHOT', 'SMALL', 1, 200, 'STORE'), 
(10202, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 200', 1, 'ICON', null, 1, 200, 'STORE'),
(10203, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),
(10204, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),
(10205, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),
(10206, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),
(10207, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),
(10208, 'image slide show', 'store-full7.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),
(10209, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 200', 1, 'SLIDESHOW', null, 1, 200, 'STORE'),

(10211, 'image ...', 'store-2-img.jpg', 0, 'image 1 store 210', 1, 'PACKSHOT', 'SMALL', 1, 210, 'STORE'), 
(10212, 'image ...', 'store-2-img-icon.jpg', 0, 'icon 1 store 210', 1, 'ICON', null, 1, 210, 'STORE'),
(10213, 'image slide show', 'store-full1.jpg', 0, 'icon 1 store 210', 1, 'SLIDESHOW', null, 1, 210, 'STORE'),
(10214, 'image slide show', 'store-full2.jpg', 0, 'icon 1 store 210', 1, 'SLIDESHOW', null, 1, 210, 'STORE'),
(10215, 'image slide show', 'store-full3.jpg', 0, 'icon 1 store 210', 1, 'SLIDESHOW', null, 1, 210, 'STORE'),
(10216, 'image slide show', 'store-full4.jpg', 0, 'icon 1 store 210', 1, 'SLIDESHOW', null, 1, 210, 'STORE'),
(10217, 'image slide show', 'store-full5.jpg', 0, 'icon 1 store 210', 1, 'SLIDESHOW', null, 1, 210, 'STORE'),
(10218, 'image slide show', 'store-full6.jpg', 0, 'icon 1 store 210', 1, 'SLIDESHOW', null, 1, 210, 'STORE');

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

-- MASTER CATALOG
INSERT INTO teco_catalog_master_category 
(id, description, code, is_default, name, master_catalog_id, version)
 VALUES 
(10, 'Category 1 description', 'CATE10', 1, 'Coffee',			1, 1),
(20, 'Category 2 description', 'CATE20', 0, 'Tea',				1, 1),
(30, 'Category 3 description', 'CATE30', 0, 'Essentials',		1, 1),
(40, 'Category 4 description', 'CATE40', 0, 'Nouveautés',		1, 1),
(50, 'Category 5 description', 'CATE50', 0, 'Idées de cadeaux', 1, 1);

INSERT INTO teco_catalog_master_category 
(id, description, code, is_default, name, version, parent_category_id, master_catalog_id)
 VALUES 
(101, 'Category 1 sub category description', 'CATE101', 0, 'Just Brewed',			1, 10, 1),
(102, 'Category 1 sub category description', 'CATE102', 0, 'Light & Subtle',		1, 10, 1),
(103, 'Category 1 sub category description', 'CATE103', 0, 'Light & Distinctive',	1, 10, 1),
(104, 'Category 1 sub category description', 'CATE104', 0, 'Medium & Smooth',		1, 10, 1),
(105, 'Category 1 sub category description', 'CATE105', 0, 'Dark & Distinctive',	1, 10, 1), 
(201, 'Category 2 sub category description', 'CATE201', 0, 'Just Steeped',			1, 20, 1),
(202, 'Category 2 sub category description', 'CATE202', 0, 'Green',					1, 20, 1), 
(203, 'Category 2 sub category description', 'CATE203', 0, 'Herbal Infusion',		1, 20, 1),
(301, 'Category 3 sub category description', 'CATE301', 0, 'Powders',				1, 30, 1), 
(302, 'Category 3 sub category description', 'CATE302', 0, 'Drinkware',				1, 30, 1),
(401, 'Category 4 sub category description', 'CATE401', 0, 'Tea',					1, 40, 1), 
(402, 'Category 4 sub category description', 'CATE402', 0, 'Coffee',				1, 40, 1),
(501, 'Category 5 sub category description', 'CATE501', 0, 'Fêtes des pères',		1, 50, 1), 
(502, 'Category 5 sub category description', 'CATE502', 0, "Fêtes des mères",		1, 50, 1);

-- VIRTUAL CATALOG
-- Root Virtual Categories

-- V_CAT_INT
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(1010, 'Category 1 description', null, 1, 'Coffee',				10, 1000, 1),
(1020, 'Category 2 description', null, 0, 'Tea',				20, 1000, 1),
(1030, 'Category 3 description', null, 0, 'Essentials',			30, 1000, 1),
(1040, 'Category 4 description', null, 0, 'Nouveautés',			40, 1000, 1),
(1050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 1000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(10110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 1010, 1000),
(10120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 1010, 1000),
(10130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 1010, 1000),
(10140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 1010, 1000),
(10150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 1010, 1000), 
(10210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 1020, 1000),
(10220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 1020, 1000), 
(10230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 1020, 1000),
(10310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 1030, 1000), 
(10320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 1030, 1000),
(10410, 'Category 4 sub category description', null, 0, 'Tea',					1, 401, 1040, 1000), 
(10420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 402, 1040, 1000),
(10510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 501, 1050, 1000), 
(10520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 502, 1050, 1000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(10110, null, null, null, null, null, 'Just Brewed',  			10110, 1000, null),
(10120, null, null, null, null, null, 'Light & Subtle', 		10120, 1000, null),
(10130, null, null, null, null, null, 'Light & Distinctive', 	10130, 1000, null),
(10140, null, null, null, null, null, 'Medium & Smooth', 		10140, 1000, null),
(10150, null, null, null, null, null, 'Dark & Distinctive',	10150, 1000, null),
(10210, null, null, null, null, null, 'Just Steeped',			10210, 1000, null),
(10220, null, null, null, null, null, 'Green',					10220, 1000, null),
(10230, null, null, null, null, null, 'Herbal Infusion',		10230, 1000, null),
(10310, null, null, null, null, null, 'Powders',				10310, 1000, null),
(10320, null, null, null, null, null, 'Drinkware',				10320, 1000, null),
(10410, null, null, null, null, null, 'Tea',					10410, 1000, null),
(10420, null, null, null, null, null, 'Coffee',					10420, 1000, null),
(10510, null, null, null, null, null, 'Feasts Fathers',			10510, 1000, null),
(10520, null, null, null, null, null, "Mother's Day",			10520, 1000, null);

-- V_CAT_FRA
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(2010, 'Category 1 description', null, 1, 'Coffee',				10, 2000, 1),
(2020, 'Category 2 description', null, 0, 'Tea',				20, 2000, 1),
(2030, 'Category 3 description', null, 0, 'Essentials',			30, 2000, 1),
(2040, 'Category 4 description', null, 0, 'Nouveautés',			40, 2000, 1),
(2050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 2000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(20110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 2010, 2000),
(20120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 2010, 2000),
(20130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 2010, 2000),
(20140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 2010, 2000),
(20150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 2010, 2000), 
(20210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 2020, 2000),
(20220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 2020, 2000), 
(20230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 2020, 2000),
(20310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 2030, 2000), 
(20320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 2030, 2000),
(20410, 'Category 4 sub category description', null, 0, 'Tea',					1, 401, 2040, 2000), 
(20420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 402, 2040, 2000),
(20510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 501, 2050, 2000), 
(20520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 502, 2050, 2000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(20110, null, null, null, null, null, 'Just Brewed',  			20110, 1000, 'fr'),
(20120, null, null, null, null, null, 'Light & Subtle', 		20120, 1000, 'fr'),
(20130, null, null, null, null, null, 'Light & Distinctive', 	20130, 1000, 'fr'),
(20140, null, null, null, null, null, 'Medium & Smooth', 		20140, 1000, 'fr'),
(20150, null, null, null, null, null, 'Dark & Distinctive',	20150, 1000, 'fr'),
(20210, null, null, null, null, null, 'Just Steeped',			20210, 1000, 'fr'),
(20220, null, null, null, null, null, 'Green',					20220, 1000, 'fr'),
(20230, null, null, null, null, null, 'Herbal Infusion',		20230, 1000, 'fr'),
(20310, null, null, null, null, null, 'Powders',				20310, 1000, 'fr'),
(20320, null, null, null, null, null, 'Drinkware',				20320, 1000, 'fr'),
(20410, null, null, null, null, null, 'Tea',					20410, 1000, 'fr'),
(20420, null, null, null, null, null, 'Coffee',					20420, 1000, 'fr'),
(20510, null, null, null, null, null, 'Feasts Fathers',			20510, 1000, 'fr'),
(20520, null, null, null, null, null, "Mother's Day",			20520, 1000, 'fr');

-- V_CAT_ESP
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(3010, 'Category 1 description', null, 1, 'Coffee',				10, 3000, 1),
(3020, 'Category 2 description', null, 0, 'Tea',				20, 3000, 1),
(3030, 'Category 3 description', null, 0, 'Essentials',			30, 3000, 1),
(3040, 'Category 4 description', null, 0, 'Nouveautés',			40, 3000, 1),
(3050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 3000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(30110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 3010, 3000),
(30120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 3010, 3000),
(30130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 3010, 3000),
(30140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 3010, 3000),
(30150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 3010, 3000), 
(30210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 3020, 3000),
(30220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 3020, 3000), 
(30230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 3020, 3000),
(30310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 3030, 3000), 
(30320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 3030, 3000),
(30410, 'Category 4 sub category description', null, 0, 'Tea',					1, 401, 3040, 3000), 
(30420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 402, 3040, 3000),
(30510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 501, 3050, 3000), 
(30520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 502, 3050, 3000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(30110, null, null, null, null, null, 'Just Brewed',  			30110, 1000, 'es'),
(30120, null, null, null, null, null, 'Light & Subtle', 		30120, 1000, 'es'),
(30130, null, null, null, null, null, 'Light & Distinctive', 	30130, 1000, 'es'),
(30140, null, null, null, null, null, 'Medium & Smooth', 		30140, 1000, 'es'),
(30150, null, null, null, null, null, 'Dark & Distinctive',	30150, 1000, 'es'),
(30210, null, null, null, null, null, 'Just Steeped',			30210, 1000, 'es'),
(30220, null, null, null, null, null, 'Green',					30220, 1000, 'es'),
(30230, null, null, null, null, null, 'Herbal Infusion',		30230, 1000, 'es'),
(30310, null, null, null, null, null, 'Powders',				30310, 1000, 'es'),
(30320, null, null, null, null, null, 'Drinkware',				30320, 1000, 'es'),
(30410, null, null, null, null, null, 'Tea',					30410, 1000, 'es'),
(30420, null, null, null, null, null, 'Coffee',					30420, 1000, 'es'),
(30510, null, null, null, null, null, 'Feasts Fathers',			30510, 1000, 'es'),
(30520, null, null, null, null, null, "Mother's Day",			30520, 1000, 'es');

-- V_CAT_USA
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(4010, 'Category 1 description', null, 1, 'Coffee',				10, 4000, 1),
(4020, 'Category 2 description', null, 0, 'Tea',				20, 4000, 1),
(4030, 'Category 3 description', null, 0, 'Essentials',			30, 4000, 1),
(4040, 'Category 4 description', null, 0, 'Nouveautés',			40, 4000, 1),
(4050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 4000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(40110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 4010, 4000),
(40120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 4010, 4000),
(40130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 4010, 4000),
(40140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 4010, 4000),
(40150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 4010, 4000), 
(40210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 4020, 4000),
(40220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 4020, 4000), 
(40230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 4020, 4000),
(40310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 4030, 4000), 
(40320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 4030, 4000),
(40410, 'Category 4 sub category description', null, 0, 'Tea',					1, 401, 4040, 4000), 
(40420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 402, 4040, 4000),
(40510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 501, 4050, 4000), 
(40520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 502, 4050, 4000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(40110, null, null, null, null, null, 'Just Brewed',  			40110, 1000, 'en'),
(40120, null, null, null, null, null, 'Light & Subtle', 		40120, 1000, 'en'),
(40130, null, null, null, null, null, 'Light & Distinctive', 	40130, 1000, 'en'),
(40140, null, null, null, null, null, 'Medium & Smooth', 		40140, 1000, 'en'),
(40150, null, null, null, null, null, 'Dark & Distinctive',	40150, 1000, 'en'),
(40210, null, null, null, null, null, 'Just Steeped',			40210, 1000, 'en'),
(40220, null, null, null, null, null, 'Green',					40220, 1000, 'en'),
(40230, null, null, null, null, null, 'Herbal Infusion',		40230, 1000, 'en'),
(40310, null, null, null, null, null, 'Powders',				40310, 1000, 'en'),
(40320, null, null, null, null, null, 'Drinkware',				40320, 1000, 'en'),
(40410, null, null, null, null, null, 'Tea',					40410, 1000, 'en'),
(40420, null, null, null, null, null, 'Coffee',					40420, 1000, 'en'),
(40510, null, null, null, null, null, 'Feasts Fathers',			40510, 1000, 'en'),
(40520, null, null, null, null, null, "Mother's Day",			40520, 1000, 'en');

-- V_CAT_CAN
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(5010, 'Category 1 description', null, 1, 'Coffee',				10, 5000, 1),
(5020, 'Category 2 description', null, 0, 'Tea',				20, 5000, 1),
(5030, 'Category 3 description', null, 0, 'Essentials',			30, 5000, 1),
(5040, 'Category 4 description', null, 0, 'Nouveautés',			40, 5000, 1),
(5050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 5000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(50110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 5010, 5000),
(50120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 5010, 5000),
(50130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 5010, 5000),
(50140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 5010, 5000),
(50150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 5010, 5000), 
(50210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 5020, 5000),
(50220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 5020, 5000), 
(50230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 5020, 5000),
(50310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 5030, 5000), 
(50320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 5030, 5000),
(50410, 'Category 4 sub category description', null, 0, 'Tea',					1, 501, 5040, 5000), 
(50420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 502, 5040, 5000),
(50510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 501, 5050, 5000), 
(50520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 502, 5050, 5000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(50110, null, null, null, null, null, 'Just Brewed',  			50110, 1000, 'fr-ca'),
(50120, null, null, null, null, null, 'Light & Subtle', 		50120, 1000, 'fr-ca'),
(50130, null, null, null, null, null, 'Light & Distinctive', 	50130, 1000, 'fr-ca'),
(50140, null, null, null, null, null, 'Medium & Smooth', 		50140, 1000, 'fr-ca'),
(50150, null, null, null, null, null, 'Dark & Distinctive',	50150, 1000, 'fr-ca'),
(50210, null, null, null, null, null, 'Just Steeped',			50210, 1000, 'fr-ca'),
(50220, null, null, null, null, null, 'Green',					50220, 1000, 'fr-ca'),
(50230, null, null, null, null, null, 'Herbal Infusion',		50230, 1000, 'fr-ca'),
(50310, null, null, null, null, null, 'Powders',				50310, 1000, 'fr-ca'),
(50320, null, null, null, null, null, 'Drinkware',				50320, 1000, 'fr-ca'),
(50410, null, null, null, null, null, 'Tea',					50410, 1000, 'fr-ca'),
(50420, null, null, null, null, null, 'Coffee',					50420, 1000, 'fr-ca'),
(50510, null, null, null, null, null, 'Feasts Fathers',			50510, 1000, 'fr-ca'),
(50520, null, null, null, null, null, "Mother's Day",			50520, 1000, 'fr-ca');

-- V_CAT_BRA
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(6010, 'Category 1 description', null, 1, 'Coffee',				10, 6000, 1),
(6020, 'Category 2 description', null, 0, 'Tea',				20, 6000, 1),
(6030, 'Category 3 description', null, 0, 'Essentials',			30, 6000, 1),
(6040, 'Category 4 description', null, 0, 'Nouveautés',			40, 6000, 1),
(6050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 6000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(60110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 6010, 6000),
(60120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 6010, 6000),
(60130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 6010, 6000),
(60140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 6010, 6000),
(60150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 6010, 6000), 
(60210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 6020, 6000),
(60220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 6020, 6000), 
(60230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 6020, 6000),
(60310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 6030, 6000), 
(60320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 6030, 6000),
(60410, 'Category 4 sub category description', null, 0, 'Tea',					1, 601, 6040, 6000), 
(60420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 602, 6040, 6000),
(60510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 601, 6050, 6000), 
(60520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 602, 6050, 6000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(60110, null, null, null, null, null, 'Just Brewed',  			60110, 1000, 'pt'),
(60120, null, null, null, null, null, 'Light & Subtle', 		60120, 1000, 'pt'),
(60130, null, null, null, null, null, 'Light & Distinctive', 	60130, 1000, 'pt'),
(60140, null, null, null, null, null, 'Medium & Smooth', 		60140, 1000, 'pt'),
(60150, null, null, null, null, null, 'Dark & Distinctive',	60150, 1000, 'pt'),
(60210, null, null, null, null, null, 'Just Steeped',			60210, 1000, 'pt'),
(60220, null, null, null, null, null, 'Green',					60220, 1000, 'pt'),
(60230, null, null, null, null, null, 'Herbal Infusion',		60230, 1000, 'pt'),
(60310, null, null, null, null, null, 'Powders',				60310, 1000, 'pt'),
(60320, null, null, null, null, null, 'Drinkware',				60320, 1000, 'pt'),
(60410, null, null, null, null, null, 'Tea',					60410, 1000, 'pt'),
(60420, null, null, null, null, null, 'Coffee',					60420, 1000, 'pt'),
(60510, null, null, null, null, null, 'Feasts Fathers',			60510, 1000, 'pt'),
(60520, null, null, null, null, null, "Mother's Day",			60520, 1000, 'pt');

-- V_CAT_ARG
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(7010, 'Category 1 description', null, 1, 'Coffee',				10, 7000, 1),
(7020, 'Category 2 description', null, 0, 'Tea',				20, 7000, 1),
(7030, 'Category 3 description', null, 0, 'Essentials',			30, 7000, 1),
(7040, 'Category 4 description', null, 0, 'Nouveautés',			40, 7000, 1),
(7050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 7000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(70110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 7010, 7000),
(70120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 7010, 7000),
(70130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 7010, 7000),
(70140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 7010, 7000),
(70150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 7010, 7000), 
(70210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 7020, 7000),
(70220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 7020, 7000), 
(70230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 7020, 7000),
(70310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 7030, 7000), 
(70320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 7030, 7000),
(70410, 'Category 4 sub category description', null, 0, 'Tea',					1, 701, 7040, 7000), 
(70420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 702, 7040, 7000),
(70510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 701, 7050, 7000), 
(70520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 702, 7050, 7000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(70110, null, null, null, null, null, 'Just Brewed',  			70110, 1000, 'es-ar'),
(70120, null, null, null, null, null, 'Light & Subtle', 		70120, 1000, 'es-ar'),
(70130, null, null, null, null, null, 'Light & Distinctive', 	70130, 1000, 'es-ar'),
(70140, null, null, null, null, null, 'Medium & Smooth', 		70140, 1000, 'es-ar'),
(70150, null, null, null, null, null, 'Dark & Distinctive',	70150, 1000, 'es-ar'),
(70210, null, null, null, null, null, 'Just Steeped',			70210, 1000, 'es-ar'),
(70220, null, null, null, null, null, 'Green',					70220, 1000, 'es-ar'),
(70230, null, null, null, null, null, 'Herbal Infusion',		70230, 1000, 'es-ar'),
(70310, null, null, null, null, null, 'Powders',				70310, 1000, 'es-ar'),
(70320, null, null, null, null, null, 'Drinkware',				70320, 1000, 'es-ar'),
(70410, null, null, null, null, null, 'Tea',					70410, 1000, 'es-ar'),
(70420, null, null, null, null, null, 'Coffee',					70420, 1000, 'es-ar'),
(70510, null, null, null, null, null, 'Feasts Fathers',			70510, 1000, 'es-ar'),
(70520, null, null, null, null, null, "Mother's Day",			70520, 1000, 'es-ar');

-- V_CAT_CHN
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(8010, 'Category 1 description', null, 1, 'Coffee',				10, 8000, 1),
(8020, 'Category 2 description', null, 0, 'Tea',				20, 8000, 1),
(8030, 'Category 3 description', null, 0, 'Essentials',			30, 8000, 1),
(8040, 'Category 4 description', null, 0, 'Nouveautés',			40, 8000, 1),
(8050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 8000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(80110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 8010, 8000),
(80120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 8010, 8000),
(80130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 8010, 8000),
(80140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 8010, 8000),
(80150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 8010, 8000), 
(80210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 8020, 8000),
(80220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 8020, 8000), 
(80230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 8020, 8000),
(80310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 8030, 8000), 
(80320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 8030, 8000),
(80410, 'Category 4 sub category description', null, 0, 'Tea',					1, 701, 8040, 8000), 
(80420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 702, 8040, 8000),
(80510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 701, 8050, 8000), 
(80520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 702, 8050, 8000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(80110, null, null, null, null, null, 'Just Brewed',  			80110, 1000, 'zh-cn'),
(80120, null, null, null, null, null, 'Light & Subtle', 		80120, 1000, 'zh-cn'),
(80130, null, null, null, null, null, 'Light & Distinctive', 	80130, 1000, 'zh-cn'),
(80140, null, null, null, null, null, 'Medium & Smooth', 		80140, 1000, 'zh-cn'),
(80150, null, null, null, null, null, 'Dark & Distinctive',	80150, 1000, 'zh-cn'),
(80210, null, null, null, null, null, 'Just Steeped',			80210, 1000, 'zh-cn'),
(80220, null, null, null, null, null, 'Green',					80220, 1000, 'zh-cn'),
(80230, null, null, null, null, null, 'Herbal Infusion',		80230, 1000, 'zh-cn'),
(80310, null, null, null, null, null, 'Powders',				80310, 1000, 'zh-cn'),
(80320, null, null, null, null, null, 'Drinkware',				80320, 1000, 'zh-cn'),
(80410, null, null, null, null, null, 'Tea',					80410, 1000, 'zh-cn'),
(80420, null, null, null, null, null, 'Coffee',					80420, 1000, 'zh-cn'),
(80510, null, null, null, null, null, 'Feasts Fathers',			80510, 1000, 'zh-cn'),
(80520, null, null, null, null, null, "Mother's Day",			80520, 1000, 'zh-cn');

-- V_CAT_JPN
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(9010, 'Category 1 description', null, 1, 'Coffee',				10, 9000, 1),
(9020, 'Category 2 description', null, 0, 'Tea',				20, 9000, 1),
(9030, 'Category 3 description', null, 0, 'Essentials',			30, 9000, 1),
(9040, 'Category 4 description', null, 0, 'Nouveautés',			40, 9000, 1),
(9050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 9000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(90110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 9010, 9000),
(90120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 9010, 9000),
(90130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 9010, 9000),
(90140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 9010, 9000),
(90150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 9010, 9000), 
(90210, 'Category 2 sub category description', null, 0, 'Just Steeped',			1, 201, 9020, 9000),
(90220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 9020, 9000), 
(90230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 9020, 9000),
(90310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 9030, 9000), 
(90320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 9030, 9000),
(90410, 'Category 4 sub category description', null, 0, 'Tea',					1, 701, 9040, 9000), 
(90420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 702, 9040, 9000),
(90510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 701, 9050, 9000), 
(90520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 702, 9050, 9000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(90110, null, null, null, null, null, 'Just Brewed',  			90110, 1000, 'jp'),
(90120, null, null, null, null, null, 'Light & Subtle', 		90120, 1000, 'jp'),
(90130, null, null, null, null, null, 'Light & Distinctive', 	90130, 1000, 'jp'),
(90140, null, null, null, null, null, 'Medium & Smooth', 		90140, 1000, 'jp'),
(90150, null, null, null, null, null, 'Dark & Distinctive',	90150, 1000, 'jp'),
(90210, null, null, null, null, null, 'Just Steeped',			90210, 1000, 'jp'),
(90220, null, null, null, null, null, 'Green',					90220, 1000, 'jp'),
(90230, null, null, null, null, null, 'Herbal Infusion',		90230, 1000, 'jp'),
(90310, null, null, null, null, null, 'Powders',				90310, 1000, 'jp'),
(90320, null, null, null, null, null, 'Drinkware',				90320, 1000, 'jp'),
(90410, null, null, null, null, null, 'Tea',					90410, 1000, 'jp'),
(90420, null, null, null, null, null, 'Coffee',					90420, 1000, 'jp'),
(90510, null, null, null, null, null, 'Feasts Fathers',			90510, 1000, 'jp'),
(90520, null, null, null, null, null, "Mother's Day",			90520, 1000, 'jp');

-- V_CAT_VNM
INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, master_category_id, virtual_catalog_id, version)
 VALUES 
(100010, 'Category 1 description', null, 1, 'Coffee',			10, 10000, 1),
(100020, 'Category 2 description', null, 0, 'Tea',				20, 10000, 1),
(100030, 'Category 3 description', null, 0, 'Essentials',		30, 10000, 1),
(100040, 'Category 4 description', null, 0, 'Nouveautés',		40, 10000, 1),
(100050, 'Category 5 description', null, 0, 'Idées de cadeaux',	50, 10000, 1);

INSERT INTO teco_catalog_virtual_category 
(id, description, code, is_default, name, version, master_category_id, parent_category_id, virtual_catalog_id)
 VALUES 
(100110, 'Category 1 sub category description', null, 0, 'Just Brewed',			1, 101, 100010, 10000),
(100120, 'Category 1 sub category description', null, 0, 'Light & Subtle',		1, 102, 100010, 10000),
(100130, 'Category 1 sub category description', null, 0, 'Light & Distinctive',	1, 103, 100010, 10000),
(100140, 'Category 1 sub category description', null, 0, 'Medium & Smooth',		1, 104, 100010, 10000),
(100150, 'Category 1 sub category description', null, 0, 'Dark & Distinctive',	1, 105, 100010, 10000), 
(100210, 'Category 2 sub category description', null, 0, 'Just Steeped',		1, 201, 100020, 10000),
(100220, 'Category 2 sub category description', null, 0, 'Green',				1, 202, 100020, 10000), 
(100230, 'Category 2 sub category description', null, 0, 'Herbal Infusion',		1, 203, 100020, 10000),
(100310, 'Category 3 sub category description', null, 0, 'Powders',				1, 301, 100030, 10000), 
(100320, 'Category 3 sub category description', null, 0, 'Drinkware',			1, 302, 100030, 10000),
(100410, 'Category 4 sub category description', null, 0, 'Tea',					1, 701, 100040, 10000), 
(100420, 'Category 4 sub category description', null, 0, 'Coffee',				1, 702, 100040, 10000),
(100510, 'Category 5 sub category description', null, 0, 'Fêtes des pères',		1, 701, 100050, 10000), 
(100520, 'Category 5 sub category description', null, 0, "Fêtes des mères",		1, 702, 100050, 10000);

INSERT INTO teco_catalog_virtual_category_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, category_id, attribute_definition_id, localization_code) 
VALUES 
(100110, null, null, null, null, null, 'Just Brewed',  			100110, 1000, 'vi-vn'),
(100120, null, null, null, null, null, 'Light & Subtle', 		100120, 1000, 'vi-vn'),
(100130, null, null, null, null, null, 'Light & Distinctive', 	100130, 1000, 'vi-vn'),
(100140, null, null, null, null, null, 'Medium & Smooth', 		100140, 1000, 'vi-vn'),
(100150, null, null, null, null, null, 'Dark & Distinctive',	100150, 1000, 'vi-vn'),
(100210, null, null, null, null, null, 'Just Steeped',			100210, 1000, 'vi-vn'),
(100220, null, null, null, null, null, 'Green',					100220, 1000, 'vi-vn'),
(100230, null, null, null, null, null, 'Herbal Infusion',		100230, 1000, 'vi-vn'),
(100310, null, null, null, null, null, 'Powders',				100310, 1000, 'vi-vn'),
(100320, null, null, null, null, null, 'Drinkware',				100320, 1000, 'vi-vn'),
(100410, null, null, null, null, null, 'Tea',					100410, 1000, 'vi-vn'),
(100420, null, null, null, null, null, 'Coffee',				100420, 1000, 'vi-vn'),
(100510, null, null, null, null, null, 'Feasts Fathers',		100510, 1000, 'vi-vn'),
(100520, null, null, null, null, null, "Mother's Day",			100520, 1000, 'vi-vn');

-- PRODUCT MARKETING

INSERT INTO teco_product_marketing 
(id, description, code, is_default, name, version, brand_id)
 VALUES 
(1, 'The flavor of resh ripe blueberries complemented by traditional brown sugar and pastry notes for a tasty flavored coffee treat.', 'PROD1', 1, 'Blueberry Streusel Coffee', 1, 10), 
(2, 'A fragrant toffee aroma with flavors of walnuts and semi-sweet chocolate, with a crisp black cherry finish; this coffee comes from the Kintamani Highlands in North Bali, Indonesia. ', 'PROD2', 0, 'Bali Blue Moon Coffee', 1, 10), 
(3, "Take this tumbler on your trips near and far. As you travel you'll be reminded of many of the cities and countries that The Coffee Bean touches in all parts of the world. Capacity: 14oz  Double wall insulated. Clear Acrylic. Hand Wash recommended. Made with BPA free materials. ", 'PROD3', 0, 'World Art Tumbler', 1, 10), 
(4, 'A giant of worldwide coffee production, and the country that brought you the thong, Brazil produces not only the most coffee, it also grows some of the best. Our Brazil Cerrado is grown in the hot, flat savannahs of Minas Gerais. Labeled “Specialty Grade,” Brazil Cerrado is dry processed for a cup of coffee that displays terrific body, and a nutty, sweet taste that can only be found in the best Brazilian coffees. ', 'PROD4', 0, 'Brazil Cerrado Coffee', 1, 20),
(5, 'To create our House Blend we combine natural and washed Central and South American coffees. We choose our blends for brightness, flavor, and aromas that make for a smooth, satisfying cup of coffee that can be enjoyed all day long.', 'PROD5', 0, 'House Blend Coffee', 1, 20),
(6, 'To create our House Blend we combine natural and washed Central and South American coffees. We choose our blends for brightness, flavor, and aromas that make for a smooth, satisfying cup of coffee that can be enjoyed all day long.', 'PROD6', 0, 'Decaf House Blend Coffee', 1, 20),
(7, 'To create our House Blend, we combine natural and washed Central and South American coffees. We choose our blends for brightness, flavor, and aromas that make for a smooth, satisfying cup of coffee that can be enjoyed all day long.', 'PROD7', 0, 'House Blend Coffee 24 2oz Portion Packs', 1, 20),
(8, 'We offer Ethiopian Yirgacheffe, picked by hand on a farm in the mountains high above the town of Sidamo. There, the coffee beans are washed, and then soaked up to 72 hours in fermentation tanks. This wet process method produces intensely flavorful beans, with an intensely floral aroma, and mellow, smooth taste. ', 'PROD8', 0, 'Ethiopia Yirgacheffe Coffee', 1, 20),
(9, 'Kenya has become the giant of African coffee production, ever since coffee made its way over the mountains from Ethiopia. Kenya AA is the largest bean grown in Kenya, and brews up a complex, fruity, light, and very bright cup. This is an exquisite coffee with an assertive, lively personality. Trust us, in coffee that’s a good thing. ', 'PROD9', 0, 'Kenya AA Coffee', 1, 20),
(10, 'From tree to bag. This premier coffee is hand picked by workers on small family owned farms in the state of Nariño, on the Pacific Coast in Colombia. The farmers take great pride in growing, picking, and preparing their products by hand. We take great pride in offering you the results of their hard work. Their dedication pays off in a balanced, bright cup of coffee, richly aromatic with a creamy body. ', 'PROD10', 0, 'Colombia Narino Coffee', 1, 20),
(11, 'Offered exclusively at The Coffee Bean & Tea Leaf Costa Rica La Cascada Tarrazu is balanced, clean, and mild. Prized for bright, crisp taste, Costa Rica La Cascada comes from the high altitude farms in the Tarrazu region where rich soils produce some of the best coffees in the land. ', 'PROD11', 0, 'Costa Rica Cascada Tarrazu Coffee', 1, 20),
(12, 'Costa Rica strikes gold with La Minita Tarrazu. Black gold. Not oil, but coffee grown at La Minita, the legendary “little gold mine” of the Indians who sought gold in the plantation’s soil. Today, the gold mine plantation produces wealth in the form of an elegantly balanced coffee grown between 4,000 and 6,000 feet above sea level. The unique micro climates in the mountains of Costa Rica creates a coffee that exhibits a sweet aroma, full body, delicate acidity, and a clean after taste. ', 'PROD12', 0, 'Costa Rica La Minita Tarrazu Coffee', 1, 20),
(13, 'Enjoy the flavor, and skip the caffeine. This premier coffee is hand picked by workers on small family-owned farms in the state of Nariño, on the Pacific Coast. The farmers take great pride in growing, picking, and preparing their products by hand. We take great pride in offering you the results of their hard work. Their dedication pays off in a balanced, bright cup of coffee, richly aromatic with a creamy body. ', 'PROD13', 0, 'Decaf Colombia Narino Coffee', 1, 20),
(14, 'From the shady central highlands of Antigua, Guatemala, comes a full-bodied coffee classic noted for its balance, nuance, and aroma. We purchase the beans in small lots from established farms in Antigua to guarantee the high quality of our beans.', 'PROD14', 0, 'Guatemala Antigua Coffee', 1, 20),
(15, 'Mocha Java is the world’s original coffee blend. Our interpretation combines sweet, fruity Ethiopian Yirgacheffe with the deep body and rich flavor of Java Estate coffees. The result has long been praised by coffee aficionados around the world.', 'PROD15', 0, 'Mocha Java Coffee', 1, 20),
(16, 'The finest coffee in New Guinea comes from the Sigri estate, in the Waghi Valley of Papua New Guinea, where climate, soil and elevation combine to create ideal growing conditions. A coffee prized by connoisseurs, our Papua New Guinea Sigri A is naturally sweet, with a fruity aroma, a spicy body, and clean flavor.1 lb.', 'PROD16', 0, 'Papua New Guinea Sigri Coffee', 1, 20),
(17, 'Discriminating coffee drinkers have long placed Sumatra Mandheling at the top of the list of the best coffees from Sumatra and perhaps the world. The syrupy, full body of Sumatra Mandheling, combined with its muted-acidity, makes an elegant, exotic cup of coffee. The taste of roasted caramel, and hints of chocolate make it truly exotic.', 'PROD17', 0, 'Sumatra Mandheling Coffee', 1, 20),
(18, 'Brisk and invigorating, our English Breakfast Tea is a blend of black teas from Sri Lankan Ceylon, Taiwanese Black Tea, and Chinese Keemun Black Tea. Our crisp, smooth, and medium-bodied blend is our take on a beloved classic and a tribute to England’s tradition of tea drinking. ', 'PROD18', 0, 'Breakfast Blend Coffee', 1, 20),
(19, 'From tree to bag. This premier coffee is hand picked by workers on small family owned farms in the state of Nariño, on the Pacific Coast in Colombia. The farmers take great pride in growing, picking, and preparing their products by hand. We take great pride in offering you the results of their hard work. Their dedication pays off in a balanced, bright cup of coffee, richly aromatic with a creamy body.', 'PROD19', 0, 'Colombia Narino Coffee, 24 2oz Portion Packs', 1, 20),
(20, 'Offered exclusively at The Coffee Bean & Tea Leaf®, Costa Rica La Cascada Tarrazu is balanced, clean, and mild. Prized for bright, crisp taste, Costa Rica La Cascada comes from the high altitude farms in the Tarrazu region where rich soils produce some of the best coffees in the land.', 'PROD20', 0, 'Costa Rica Coffee, 24 2oz Portion Packs', 1, 20),
(21, "Our Master Roaster created this seasonal treat for the holiday season in 1996. We've proudly featured it in our stores every year since!", 'PROD21', 0, 'Holiday Blend Coffee', 1, 20),
(22, 'A fragrant toffee aroma with flavors of walnuts and semi-sweet chocolate, with a crisp black cherry finish; this coffee comes from the Kintamani Highlands in North Bali, Indonesia. ', 'PROD22', 0, 'Bali Blue Moon Coffee', 1, 20),
(23, 'Fair Trade & Organic. Plum aroma with a smooth chocolatey body and spicy finish.', 'PROD23', 0, 'University Blend Coffee', 1, 20),
(24, 'Full bodied with semi-sweet cocoa aroma, soft fruity flavor and a smooth finish.', 'PROD24', 0, 'Harvest Blend Coffee', 1, 20),
(25, 'For coffee aficionados who love an espresso but can live without the caffeine, we offer our Decaffeinated Espresso Roast Blend. This strong and subtle Espresso combines the best of four select origin coffees, each individually roasted to perfection and combined after roasting. This coffee is subtle enough to be enjoyed as a straight shot, yet assertive enough for a latte. This signature blend is a delicious cornerstone of our business.', 'PROD25', 0, 'Decaf Espesso Roast Coffee', 1, 20),
(26, 'Intensely French. Without the caffeine. We start with a delicious, delightful Costa Rican coffee, then dark roast it in the tradition of the famous French style. You’ll taste Caramelized sugars, a hint of chocolate and a deeply smoky flavor that attests to the heat of the roast characterize this intensely flavorful coffee.', 'PROD26', 0, 'Decaf French Roast Coffee', 1, 20),
(27, 'We blend for flavor, Viennese style to achieve a deep brown, medium dark roast that toasts the beans to perfection. Our Viennese Blend brings select coffees together at the medium-dark roast level for a surprising balance and depth.', 'PROD27', 0, 'Decaf Viennese Coffee', 1, 20),
(28, 'The darkest roast on the planet. We begin with a quintessential Costa Rican coffee that can stand up to the heat and intensity of a French Roast. Caramelized sugars, a hint of chocolate and a deeply smoky flavor that attests to the heat of the roast characterize this intensely flavorful coffee.', 'PROD28', 0, 'French Roast Coffee', 1, 20),
(29, 'Deep roasted flavor with a smoky aroma and a chocolaty finish', 'PROD29', 0, 'Italian Roast Coffee', 1, 20),
(30, 'Coming from Northern Tanzania, Africa, this coffee offers floral jasmine aroma, bright medium bodied flavor with hints of black currant and a chocolaty finish', 'PROD30', 0, 'Tanzania Peaberry Coffee', 1, 20),
(31, 'People love our Colombia Nariño Dark for its full, rich, creamy body and rich aroma. We roast it a bit darker to mellow the brightness and add a touch of sweet, smoky caramel to the aroma and flavor.', 'PROD31', 0, 'Colombia Narino Dark Coffee', 1, 20),
(32, 'We built our business on this signature blend. To create our Espresso Roast Blend, we roast four select origin coffees to perfection,then combine them to create the perfect base for our espresso drinks.', 'PROD32', 0, 'Espresso Roast Coffee', 1, 20),
(33, 'Our Director of Tea, David DeCandia invites readers to explore the culture of tea and the adventures to save ancient Blue Tiger tea inside Master Davey and the Magic Tea House. The enchanting book is paired with a limited edition Blue Tiger Tea blend which consists of lemon myrtle, malva flower & tea blossoms with raspberry flavoring.', 'PROD33', 0, 'Master Davey Set: Blue Tiger Tea & Book', 1, 20),
(34, 'Characteristically sweet rooibos and delicate black tea are highlighted by the festive flavor of mulling spices!.20 Whole Leaf Tea Bags', 'PROD34', 0, 'Winter Dream Tea', 1, 20),
(35, 'Ceylon black tea blended with peppermint leaf, strawberries and all natural flavors.', 'PROD35', 0, 'Peppermint Stick Tea', 1, 20),
(36, 'Sweetly scented orange blossoms, a tart hint of orange zest and our exclusive oolong tea capture the spirit of Los Angeles.  This item is also available at our retail locations at Los Angeles International Airport.', 'PROD36', 0, 'Los Angeles Sunshine Blend Tea', 1, 20),
(37, 'Experience the divine essence of this delicate white tea, expertly flavored with an enticing peach and ginger blend. This item is also available at our retail locations at Hartfield-Jackson Atlanta International Airport.', 'PROD37', 0, 'Georgia Peach Ginger White Tea', 1, 20),
(38, 'Can making tea be a labor of love? Consider our Jasmine Dragon Phoenix Pearl. To create this tea we take two beautiful young Chinese green tea leaves and one plump bud, then roll them together into a large tight pearl packed with flavor. As night falls we scent the pearls with the fragrance of Jasmine flowers seven times during a single evening.', 'PROD38', 0, 'Jasmine Dragon Phoenix Pearl Tea', 1, 20),
(39, 'There’s nothing quite like the distinctive taste of Genmaicha Green tea, with its blend of Japanese Sencha tea and partially toasted rice. Refreshing and light, the toasted rice adds body and sweetness to the finish.', 'PROD39', 0, 'Genmaicha Green Tea', 1, 20),
(40, 'Savor the tea of emperors. This delicate green tea is revered by the Chinese for its jade green color and unique shape. The only place in the world where Lung Ching Dragonwell tea is grown is in the West Lake district in Hangzhou, China. We make the tea the same way the Chinese have made it for centuries. The tea is meticulously prepared from tender leaves using a traditional handmade technique.', 'PROD40', 0, 'Lung Ching Dragonwell Tea', 1, 20),
(41, 'This wonderful green tea is grown in China, the birthplace of tea, where the field workers take enormous pride in delivering only the highest quality green tea, legendary for its overall health benefits and antioxidant content. And our special CO2 decaffeination process lets you enjoy all the benefits of this fine, Chinese green tea with optimal taste and quality, without the caffeine.', 'PROD41', 0, 'Decaf Green Tea', 1, 20),
(42, 'In the high country of Sweden the wildberry pickers look forward to summer and ripening berries. This drink blends hibiscus, raisins and an assortment of berries to create a warm, soul-soothing infusion full of natural fruit flavors and sweet, fragrant aromas.', 'PROD42', 0, 'Swedish Berries Tea', 1, 20),
(43, 'Crossing continents and traditions, we took this caffeine-free South African (No Suggestions) and infused it with the exotic flavors of chai to create this sweet, creamy, delicately spiced infusion. ', 'PROD43', 0, 'Chai Rooibos Tea', 1, 20),
(44, 'Our hand-harvested honeybush has a mild, sweet taste and aroma, somewhat like honey, and is infused with aromatic vanilla flavoring to create a unique infusion. This herbal infusion yields a vibrant reddish orange liquor not unlike the famous red glowing sunrise known throughout Africa.', 'PROD44', 0, 'African Sunrise Tea', 1, 20),
(45, "We searched the world over trying to create the most delicious, richest vanilla beverage ever. If you've tasted our French Deluxe™ vanilla powder, you'll agree that we've succeeded. Bring home our classic French Deluxe™ Vanilla and have fun making your favorite beverages in your own kitchen.", 'PROD45', 0, 'French Deluxe Vanilla Powder', 1, 20),
(46, "Our Special Dutch™ chocolate powder is now available with No Sugar Added! We added the ultra-premium ingredients to make the finest grade of cocoa. Try it alone, hot or cold, you'll love it any way! Drop a bit in your coffee or tea for an added bit of pleasure. We liked it so much, we made it our SECRET ingredient in our renowned Mocha Ice Blended® drinks, Cafe Mocha and Mocha Latte.", 'PROD46', 0, 'Special Dutch Chocolate Powder, no Sugar', 1, 20),
(47, 'A blend of versatility and style with an über-sleek stainless steel purple finish. This tumbler is double wall insulated and made with BPA Free materials.', 'PROD47', 0, 'Americana Tumbler', 1, 20),
(48, 'A blend of versatility and durability with an über-sleek chrome finish. Double-wall insulated. For hot or cold beverages Capacity: 17 oz. Hand-wash suggested ', 'PROD48', 0, 'Bergamo Bottle', 1, 20),
(49, 'This sleek marvel is double-wall insulated with premium stainless steel, so it works with hot and cold beverages. BPA-free Capacity: 16 oz', 'PROD49', 0, 'The Jaidun Tumbler', 1, 20),
(50, 'It bends but never breaks, and is henceforth a Japanese symbol of strength and prosperity. May every sip of our fine teas - and every step of your unique journey - be bamboo.', 'PROD50', 0, 'Bamboo Ceramic Tea Set', 1, 20);


--update teco_product_marketing 
--set description = 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

INSERT INTO teco_product_marketing_attribute 
(id, blob_value, boolean_value, double_value, float_value, integer_value, short_string_value, product_marketing_id, attribute_definition_id, localization_code, market_area_id) 
VALUES 
(1, null, null, null, null, null, 'Blueberry Streusel Coffee', 1, 2000, 'en', null),
(2, null, null, null, null, null, 'Bali Blue Moon Coffee', 2, 2000, 'en', null),
(3, null, null, null, null, null, 'World Art Tumbler', 3, 2000, 'en', null),
(4, null, null, null, null, null, 'Brazil Cerrado Coffee', 4, 2000, 'en', null),
(5, null, null, null, null, null, 'House Blend Coffee', 5, 2000, 'en', null),
(6, null, null, null, null, null, 'Decaf House Blend Coffee', 6, 2000, 'en', null),
(7, null, null, null, null, null, 'House Blend Coffee 24 2oz Portion Packs', 7, 2000, 'en', null),
(8, null, null, null, null, null, 'Ethiopia Yirgacheffe Coffee', 8, 2000, 'en', null),
(9, null, null, null, null, null, 'Kenya AA Coffee', 9, 2000, 'en', null),
(10, null, null, null, null, null, 'Colombia Narino Coffee', 10, 2000, 'en', null),
(11, null, null, null, null, null, 'Costa Rica Cascada Tarrazu Coffee', 11, 2000, 'en', null),
(12, null, null, null, null, null, 'Costa Rica La Minita Tarrazu Coffee', 12, 2000, 'en', null),
(13, null, null, null, null, null, 'Decaf Colombia Narino Coffee', 13, 2000, 'en', null),
(14, null, null, null, null, null, 'Guatemala Antigua Coffee', 14, 2000, 'en', null),
(15, null, null, null, null, null, 'Mocha Java Coffee', 15, 2000, 'en', null),
(16, null, null, null, null, null, 'Papua New Guinea Sigri Coffee', 16, 2000, 'en', null),
(17, null, null, null, null, null, 'Sumatra Mandheling Coffee', 17, 2000, 'en', null),
(18, null, null, null, null, null, 'Breakfast Blend Coffee', 18, 2000, 'en', null),
(19, null, null, null, null, null, 'Colombia Narino Coffee, 24 2oz Portion Packs', 19, 2000, 'en', null),
(20, null, null, null, null, null, 'Costa Rica Coffee, 24 2oz Portion Packs', 20, 2000, 'en', null),
(21, null, null, null, null, null, 'Holiday Blend Coffee', 21, 2000, 'en', null),
(22, null, null, null, null, null, 'Bali Blue Moon Coffee', 22, 2000, 'en', null),
(23, null, null, null, null, null, 'University Blend Coffee', 23, 2000, 'en', null),
(24, null, null, null, null, null, 'Harvest Blend Coffee', 24, 2000, 'en', null),
(25, null, null, null, null, null, 'Decaf Espesso Roast Coffee', 25, 2000, 'en', null),
(26, null, null, null, null, null, 'Decaf French Roast Coffee', 26, 2000, 'en', null),
(27, null, null, null, null, null, 'Decaf Viennese Coffee', 27, 2000, 'en', null),
(28, null, null, null, null, null, 'French Roast Coffee', 28, 2000, 'en', null),
(29, null, null, null, null, null, 'Italian Roast Coffee', 29, 2000, 'en', null),
(30, null, null, null, null, null, 'Tanzania Peaberry Coffee', 30, 2000, 'en', null),
(31, null, null, null, null, null, 'Colombia Narino Dark Coffee', 31, 2000, 'en', null),
(32, null, null, null, null, null, 'Espresso Roast Coffee', 32, 2000, 'en', null),
(33, null, null, null, null, null, 'Master Davey Set: Blue Tiger Tea & Book', 33, 2000, 'en', null),
(34, null, null, null, null, null, 'Winter Dream Tea', 34, 2000, 'en', null),
(35, null, null, null, null, null, 'Peppermint Stick Tea', 35, 2000, 'en', null),
(36, null, null, null, null, null, 'Los Angeles Sunshine Blend Tea', 36, 2000, 'en', null),
(37, null, null, null, null, null, 'Georgia Peach Ginger White Tea', 37, 2000, 'en', null),
(38, null, null, null, null, null, 'Jasmine Dragon Phoenix Pearl Tea', 38, 2000, 'en', null),
(39, null, null, null, null, null, 'Genmaicha Green Tea', 39, 2000, 'en', null),
(40, null, null, null, null, null, 'Lung Ching Dragonwell Tea', 40, 2000, 'en', null),
(41, null, null, null, null, null, 'Decaf Green Tea', 41, 2000, 'en', null),
(42, null, null, null, null, null, 'Swedish Berries Tea', 42, 2000, 'en', null),
(43, null, null, null, null, null, 'Chai Rooibos Tea', 43, 2000, 'en', null),
(44, null, null, null, null, null, 'African Sunrise Tea', 44, 2000, 'en', null),
(45, null, null, null, null, null, 'French Deluxe Vanilla Powder', 45, 2000, 'en', null),
(46, null, null, null, null, null, 'Special Dutch Chocolate Powder, no Sugar', 46, 2000, 'en', null),
(47, null, null, null, null, null, 'Americana Tumbler', 47, 2000, 'en', null),
(48, null, null, null, null, null, 'Bergamo Bottle', 48, 2000, 'en', null),
(49, null, null, null, null, null, 'The Jaidun Tumbler', 49, 2000, 'en', null),
(50, null, null, null, null, null, 'Bamboo Ceramic Tea Set', 50, 2000, 'en', null);


INSERT INTO teco_product_sku 
(id, description, code, is_default, name, version, product_marketing_id)
 VALUES 
(1, 'prod 1 product sku 11',   'SKU11',  1, 'Sku 11',  1, 1), 
(2, 'prod 2 product sku 21',   'SKU21',  0, 'Sku 21',  1, 2),
(3, 'prod 3 product sku 31',   'SKU31',  0, 'Sku 31',  1, 3),
(4, 'prod 4 product sku 41',   'SKU41',  0, 'Sku 41',  1, 4),
(5, 'prod 5 product sku 51',   'SKU51',  0, 'Sku 51',  1, 5),
(6, 'prod 5 product sku 52',   'SKU52',  0, 'Sku 52',  1, 5),
(7, 'prod 6 product sku 61',   'SKU61',  0, 'Sku 61',  1, 6),
(8, 'prod 6 product sku 62',   'SKU62',  0, 'Sku 62',  1, 6),
(9, 'prod 7 product sku 71',   'SKU71',  0, 'Sku 71',  1, 7),
(10, 'prod 8 product sku 81',  'SKU81',  0, 'Sku 81',  1, 8),
(11, 'prod 9 product sku 91',  'SKU91',  0, 'Sku 91',  1, 9),
(12, 'prod 10 product sku 101', 'SKU101', 0, 'Sku 101', 1, 10),
(13, 'prod 10 product sku 102', 'SKU102', 0, 'Sku 102', 1, 10),
(14, 'prod 11 product sku 111', 'SKU111', 0, 'Sku 111', 1, 11),
(15, 'prod 12 product sku 121', 'SKU121', 0, 'Sku 121', 1, 12),
(16, 'prod 13 product sku 131', 'SKU131', 0, 'Sku 131', 1, 13),
(17, 'prod 14 product sku 141', 'SKU141', 1, 'Sku 141', 1, 14), 
(18, 'prod 15 product sku 151', 'SKU151', 0, 'Sku 151', 1, 15),
(19, 'prod 16 product sku 163', 'SKU163', 0, 'Sku 161', 1, 16),
(20, 'prod 17 product sku 171', 'SKU171', 0, 'Sku 171', 1, 17),
(21, 'prod 18 product sku 181', 'SKU181', 0, 'Sku 181', 1, 18),
(22, 'prod 19 product sku 191', 'SKU191', 0, 'Sku 191', 1, 19),
(23, 'prod 20 product sku 201', 'SKU201', 0, 'Sku 201', 1, 20),
(24, 'prod 21 product sku 211', 'SKU211', 0, 'Sku 211', 1, 21),
(25, 'prod 22 product sku 221', 'SKU221', 0, 'Sku 221', 1, 22),
(26, 'prod 23 product sku 231', 'SKU231', 0, 'Sku 231', 1, 23),
(27, 'prod 24 product sku 241', 'SKU241', 0, 'Sku 241', 1, 24),
(28, 'prod 25 product sku 251', 'SKU251', 0, 'Sku 251', 1, 25),
(29, 'prod 26 product sku 261', 'SKU261', 0, 'Sku 261', 1, 26),
(30, 'prod 27 product sku 271', 'SKU271', 0, 'Sku 271', 1, 27),
(31, 'prod 28 product sku 281', 'SKU281', 0, 'Sku 281', 1, 28),
(32, 'prod 28 product sku 282', 'SKU282', 0, 'Sku 282', 1, 28),
(33, 'prod 29 product sku 291', 'SKU291', 1, 'Sku 291', 1, 29), 
(34, 'prod 30 product sku 301', 'SKU301', 0, 'Sku 301', 1, 30),
(35, 'prod 31 product sku 311', 'SKU311', 0, 'Sku 311', 1, 31),
(36, 'prod 32 product sku 321', 'SKU321', 0, 'Sku 321', 1, 32),
(37, 'prod 32 product sku 322', 'SKU322', 0, 'Sku 322', 1, 32),
(38, 'prod 33 product sku 331', 'SKU331', 0, 'Sku 331', 1, 33),
(39, 'prod 34 product sku 341', 'SKU341', 0, 'Sku 341', 1, 34),
(40, 'prod 35 product sku 351', 'SKU351', 0, 'Sku 351', 1, 35),
(41, 'prod 36 product sku 361', 'SKU361', 0, 'Sku 361', 1, 36),
(42, 'prod 37 product sku 371', 'SKU371', 0, 'Sku 371', 1, 37),
(43, 'prod 38 product sku 381', 'SKU381', 0, 'Sku 381', 1, 38),
(44, 'prod 39 product sku 391', 'SKU391', 0, 'Sku 391', 1, 39),
(45, 'prod 40 product sku 401', 'SKU401', 0, 'Sku 401', 1, 40),
(46, 'prod 41 product sku 411', 'SKU411', 0, 'Sku 411', 1, 41),
(47, 'prod 42 product sku 421', 'SKU421', 0, 'Sku 421', 1, 42),
(48, 'prod 43 product sku 431', 'SKU431', 0, 'Sku 431', 1, 43),
(49, 'prod 44 product sku 441', 'SKU441', 0, 'Sku 441', 1, 44),
(50, 'prod 45 product sku 451', 'SKU451', 0, 'Sku 451', 1, 45),
(51, 'prod 46 product sku 461', 'SKU461', 0, 'Sku 461', 1, 46),
(52, 'prod 77 product sku 471', 'SKU471', 0, 'Sku 471', 1, 47),
(53, 'prod 88 product sku 481', 'SKU481', 0, 'Sku 481', 1, 48),
(54, 'prod 99 product sku 491', 'SKU491', 0, 'Sku 491', 1, 49),
(55, 'prod200 product sku 501', 'SKU501', 0, 'Sku 501', 1, 50);
update teco_product_sku
set description = 'Lor23 ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.';

INSERT INTO teco_catalog_master_category_product_sku_rel  
(master_category_id, product_sku_id)
 VALUES 
(10110, 1), 
(10110, 2), 
(10110, 3),
(10120, 4),
(10120, 5),
(10120, 6),
(10120, 7),
(10130, 8),
(10140, 9),
(10140, 10),
(10140, 11),
(10140, 12),
(10140, 13),
(10140, 14),
(10140, 15),
(10140, 16),
(10140, 17),
(10140, 18),
(10140, 19),
(10140, 20), 
(10150, 21), 
(10150, 22),
(10150, 23),
(10150, 24),
(10150, 25),
(10150, 26),
(10150, 27),
(10150, 28),
(10150, 29),
(10150, 30),
(10150, 31),
(10150, 32),
(10210, 33),
(10210, 34),
(10210, 35),
(10210, 36),
(10210, 37),
(10220, 38),
(10220, 39),
(10220, 40),
(10220, 41),
(10230, 42),
(10230, 43),
(10230, 44),
(10310, 45),
(10310, 46),
(10320, 47),
(10320, 48),
(10320, 49),
(10320, 50);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(10110, 1), 
(10110, 2), 
(10110, 3),
(10120, 4),
(10120, 5),
(10120, 6),
(10120, 7),
(10120, 9),
(10130, 10),
(10140, 11),
(10140, 12),
(10140, 14),
(10140, 15),
(10140, 16),
(10140, 17),
(10140, 18),
(10140, 19),
(10140, 20),
(10140, 21),
(10140, 22),
(10140, 23), 
(10150, 24),
(10150, 25),
(10150, 26),
(10150, 27),
(10150, 28),
(10150, 29),
(10150, 30),
(10150, 32),
(10150, 33),
(10150, 34),
(10150, 35),
(10150, 36),
(10210, 38),
(10210, 39),
(10210, 40),
(10210, 41),
(10210, 42),
(10220, 43),
(10220, 44),
(10220, 45),
(10220, 46),
(10230, 47),
(10230, 48),
(10230, 49),
(10310, 50),
(10310, 51),
(10320, 52),
(10320, 53),
(10320, 54),
(10320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(20110, 1), 
(20110, 2), 
(20110, 3),
(20120, 4),
(20120, 5),
(20120, 7),
(20120, 9),
(20130, 10),
(20140, 11),
(20140, 12),
(20140, 14),
(20140, 15),
(20140, 16),
(20140, 17),
(20140, 18),
(20140, 19),
(20140, 20),
(20140, 21),
(20140, 22),
(20140, 23), 
(20150, 24),
(20150, 25),
(20150, 26),
(20150, 27),
(20150, 28),
(20150, 29),
(20150, 30),
(20150, 32),
(20150, 33),
(20150, 34),
(20150, 35),
(20150, 36),
(20210, 38),
(20210, 39),
(20210, 40),
(20210, 41),
(20210, 42),
(20220, 43),
(20220, 44),
(20220, 45),
(20220, 46),
(20230, 47),
(20230, 48),
(20230, 49),
(20310, 50),
(20310, 51),
(20320, 52),
(20320, 53),
(20320, 54),
(20320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(30110, 1), 
(30110, 2), 
(30110, 3),
(30120, 4),
(30120, 5),
(30120, 7),
(30120, 9),
(30130, 10),
(30140, 11),
(30140, 12),
(30140, 14),
(30140, 15),
(30140, 16),
(30140, 17),
(30140, 18),
(30140, 19),
(30140, 20),
(30140, 21),
(30140, 22),
(30140, 23), 
(30150, 24),
(30150, 25),
(30150, 26),
(30150, 27),
(30150, 28),
(30150, 29),
(30150, 30),
(30150, 32),
(30150, 33),
(30150, 34),
(30150, 35),
(30150, 36),
(30210, 38),
(30210, 39),
(30210, 40),
(30210, 41),
(30210, 42),
(30220, 43),
(30220, 44),
(30220, 45),
(30220, 46),
(30230, 47),
(30230, 48),
(30230, 49),
(30310, 50),
(30310, 51),
(30320, 52),
(30320, 53),
(30320, 54),
(30320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(40110, 1), 
(40110, 2), 
(40110, 3),
(40120, 4),
(40120, 5),
(40120, 7),
(40120, 9),
(40130, 10),
(40140, 11),
(40140, 12),
(40140, 14),
(40140, 15),
(40140, 16),
(40140, 17),
(40140, 18),
(40140, 19),
(40140, 20),
(40140, 21),
(40140, 22),
(40140, 23), 
(40150, 24),
(40150, 25),
(40150, 26),
(40150, 27),
(40150, 28),
(40150, 29),
(40150, 30),
(40150, 32),
(40150, 33),
(40150, 34),
(40150, 35),
(40150, 36),
(40210, 38),
(40210, 39),
(40210, 40),
(40210, 41),
(40210, 42),
(40220, 43),
(40220, 44),
(40220, 45),
(40220, 46),
(40230, 47),
(40230, 48),
(40230, 49),
(40310, 50),
(40310, 51),
(40320, 52),
(40320, 53),
(40320, 54),
(40320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(50110, 1), 
(50110, 2), 
(50110, 3),
(50120, 4),
(50120, 5),
(50120, 7),
(50120, 9),
(50130, 10),
(50140, 11),
(50140, 12),
(50140, 14),
(50140, 15),
(50140, 16),
(50140, 17),
(50140, 18),
(50140, 19),
(50140, 20),
(50140, 21),
(50140, 22),
(50140, 23), 
(50150, 24),
(50150, 25),
(50150, 26),
(50150, 27),
(50150, 28),
(50150, 29),
(50150, 30),
(50150, 32),
(50150, 33),
(50150, 34),
(50150, 35),
(50150, 36),
(50210, 38),
(50210, 39),
(50210, 40),
(50210, 41),
(50210, 42),
(50220, 43),
(50220, 44),
(50220, 45),
(50220, 46),
(50230, 47),
(50230, 48),
(50230, 49),
(50310, 50),
(50310, 51),
(50320, 52),
(50320, 53),
(50320, 54),
(50320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(60110, 1), 
(60110, 2), 
(60110, 3),
(60120, 4),
(60120, 5),
(60120, 7),
(60120, 9),
(60130, 10),
(60140, 11),
(60140, 12),
(60140, 14),
(60140, 15),
(60140, 16),
(60140, 17),
(60140, 18),
(60140, 19),
(60140, 20),
(60140, 21),
(60140, 22),
(60140, 23), 
(60150, 24),
(60150, 25),
(60150, 26),
(60150, 27),
(60150, 28),
(60150, 29),
(60150, 30),
(60150, 32),
(60150, 33),
(60150, 34),
(60150, 35),
(60150, 36),
(60210, 38),
(60210, 39),
(60210, 40),
(60210, 41),
(60210, 42),
(60220, 43),
(60220, 44),
(60220, 45),
(60220, 46),
(60230, 47),
(60230, 48),
(60230, 49),
(60310, 50),
(60310, 51),
(60320, 52),
(60320, 53),
(60320, 54),
(60320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(70110, 1), 
(70110, 2), 
(70110, 3),
(70120, 4),
(70120, 5),
(70120, 7),
(70120, 9),
(70130, 10),
(70140, 11),
(70140, 12),
(70140, 14),
(70140, 15),
(70140, 16),
(70140, 17),
(70140, 18),
(70140, 19),
(70140, 20),
(70140, 21),
(70140, 22),
(70140, 23), 
(70150, 24),
(70150, 25),
(70150, 26),
(70150, 27),
(70150, 28),
(70150, 29),
(70150, 30),
(70150, 32),
(70150, 33),
(70150, 34),
(70150, 35),
(70150, 36),
(70210, 38),
(70210, 39),
(70210, 40),
(70210, 41),
(70210, 42),
(70220, 43),
(70220, 44),
(70220, 45),
(70220, 46),
(70230, 47),
(70230, 48),
(70230, 49),
(70310, 50),
(70310, 51),
(70320, 52),
(70320, 53),
(70320, 54),
(70320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(80110, 1), 
(80110, 2), 
(80110, 3),
(80120, 4),
(80120, 5),
(80120, 7),
(80120, 9),
(80130, 10),
(80140, 11),
(80140, 12),
(80140, 14),
(80140, 15),
(80140, 16),
(80140, 17),
(80140, 18),
(80140, 19),
(80140, 20),
(80140, 21),
(80140, 22),
(80140, 23), 
(80150, 24),
(80150, 25),
(80150, 26),
(80150, 27),
(80150, 28),
(80150, 29),
(80150, 30),
(80150, 32),
(80150, 33),
(80150, 34),
(80150, 35),
(80150, 36),
(80210, 38),
(80210, 39),
(80210, 40),
(80210, 41),
(80210, 42),
(80220, 43),
(80220, 44),
(80220, 45),
(80220, 46),
(80230, 47),
(80230, 48),
(80230, 49),
(80310, 50),
(80310, 51),
(80320, 52),
(80320, 53),
(80320, 54),
(80320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(90110, 1), 
(90110, 2), 
(90110, 3),
(90120, 4),
(90120, 5),
(90120, 7),
(90120, 9),
(90130, 10),
(90140, 11),
(90140, 12),
(90140, 14),
(90140, 15),
(90140, 16),
(90140, 17),
(90140, 18),
(90140, 19),
(90140, 20),
(90140, 21),
(90140, 22),
(90140, 23), 
(90150, 24),
(90150, 25),
(90150, 26),
(90150, 27),
(90150, 28),
(90150, 29),
(90150, 30),
(90150, 32),
(90150, 33),
(90150, 34),
(90150, 35),
(90150, 36),
(90210, 38),
(90210, 39),
(90210, 40),
(90210, 41),
(90210, 42),
(90220, 43),
(90220, 44),
(90220, 45),
(90220, 46),
(90230, 47),
(90230, 48),
(90230, 49),
(90310, 50),
(90310, 51),
(90320, 52),
(90320, 53),
(90320, 54),
(90320, 55);

INSERT INTO teco_catalog_virtual_category_product_sku_rel  
(virtual_category_id, product_sku_id)
 VALUES 
(100110, 1), 
(100110, 2), 
(100110, 3),
(100120, 4),
(100120, 5),
(100120, 7),
(100120, 9),
(100130, 10),
(100140, 11),
(100140, 12),
(100140, 14),
(100140, 15),
(100140, 16),
(100140, 17),
(100140, 18),
(100140, 19),
(100140, 20),
(100140, 21),
(100140, 22),
(100140, 23), 
(100150, 24),
(100150, 25),
(100150, 26),
(100150, 27),
(100150, 28),
(100150, 29),
(100150, 30),
(100150, 32),
(100150, 33),
(100150, 34),
(100150, 35),
(100150, 36),
(100210, 38),
(100210, 39),
(100210, 40),
(100210, 41),
(100210, 42),
(100220, 43),
(100220, 44),
(100220, 45),
(100220, 46),
(100230, 47),
(100230, 48),
(100230, 49),
(100310, 50),
(100310, 51),
(100320, 52),
(100320, 53),
(100320, 54),
(100320, 55);

INSERT INTO teco_product_marketing_attribute
(boolean_value, market_area_id, version, attribute_definition_id, product_marketing_id)
VALUES 
(1,1,1,2020,1),
(1,1,1,2020,2),
(1,1,1,2020,3),
(1,1,1,2020,4);

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
(56, 101, 11.95, 1, 45, 1),
(57, 101, 13.95, 1, 45, 2),
(58, 101, 11.95, 1, 45, 3),
(59, 101, 10.95, 1, 45, 4),
(60, 101, 8.95, 1, 45, 5),
(61, 101, 11.95, 1, 45, 6),
(62, 101, 8.95, 1, 45, 7),
(63, 101, 11.95, 1, 45, 8),
(64, 101, 38.95, 1, 45, 9),
(65, 101, 13.95, 1, 45, 10),
(66, 101, 13.95, 1, 45, 11),
(67, 101, 8.95, 1, 45, 12),
(68, 101, 13.95, 1, 45, 13),
(69, 101, 13.95, 1, 45, 14),
(70, 101, 13.95, 1, 45, 15),
(71, 101, 13.95, 1, 45, 16),
(72, 101, 13.95, 1, 45, 17),
(73, 101, 13.95, 1, 45, 18),
(74, 101, 13.95, 1, 45, 19),
(75, 101, 13.95, 1, 45, 20),
(76, 101, 8.95, 1, 45, 21),
(77, 101, 38.95, 1, 45, 22),
(78, 101, 38.95, 1, 45, 23),
(79, 101, 13.95, 1, 45, 24),
(80, 101, 13.95, 1, 45, 25),
(81, 101, 9.95, 1, 45, 26),
(82, 101, 11.95, 1, 45, 27),
(83, 101, 8.95, 1, 45, 28),
(84, 101, 13.95, 1, 45, 29),
(85, 101, 13.95, 1, 45, 30),
(86, 101, 8.95, 1, 45, 31),
(87, 101, 13.95, 1, 45, 32),
(88, 101, 8.95, 1, 45, 33),
(89, 101, 13.95, 1, 45, 34),
(90, 101, 13.95, 1, 45, 35),
(91, 101, 8.95, 1, 45, 36),
(92, 101, 13.95, 1, 45, 37),
(93, 101, 24.95, 1, 45, 38),
(94, 101, 8.95, 1, 45, 39),
(95, 101, 8.95, 1, 45, 40),
(96, 101, 11.95, 1, 45, 41),
(97, 101, 11.95, 1, 45, 42),
(98, 101, 20.75, 1, 45, 43),
(99, 101, 10.75, 1, 45, 44),
(100, 101, 8.75, 1, 45, 45),
(101, 101, 10.75, 1, 45, 46),
(102, 101, 8.75, 1, 45, 47),
(103, 101, 8.75, 1, 45, 48),
(104, 101, 8.75, 1, 45, 49),
(105, 101, 11.95, 1, 45, 50),
(106, 101, 11.95, 1, 45, 51),
(107, 101, 14.95, 1, 45, 52),
(108, 101, 14.95, 1, 45, 53),
(109, 101, 14.95, 1, 45, 54),
(110, 101, 18.95, 1, 45, 55),

(156, 102, 11.95, 1, 45, 1),
(157, 102, 13.95, 1, 45, 2),
(158, 102, 11.95, 1, 45, 3),
(159, 102, 10.95, 1, 45, 4),
(160, 102, 8.95, 1, 45, 5),
(161, 102, 11.95, 1, 45, 6),
(162, 102, 8.95, 1, 45, 7),
(163, 102, 11.95, 1, 45, 8),
(164, 102, 38.95, 1, 45, 9),
(165, 102, 13.95, 1, 45, 10),
(166, 102, 13.95, 1, 45, 11),
(167, 102, 8.95, 1, 45, 12),
(168, 102, 13.95, 1, 45, 13),
(169, 102, 13.95, 1, 45, 14),
(170, 102, 13.95, 1, 45, 15),
(171, 102, 13.95, 1, 45, 16),
(172, 102, 13.95, 1, 45, 17),
(173, 102, 13.95, 1, 45, 18),
(174, 102, 13.95, 1, 45, 19),
(175, 102, 13.95, 1, 45, 20),
(176, 102, 8.95, 1, 45, 21),
(177, 102, 38.95, 1, 45, 22),
(178, 102, 38.95, 1, 45, 23),
(179, 102, 13.95, 1, 45, 24),
(180, 102, 13.95, 1, 45, 25),
(181, 102, 9.95, 1, 45, 26),
(182, 102, 11.95, 1, 45, 27),
(183, 102, 8.95, 1, 45, 28),
(184, 102, 13.95, 1, 45, 29),
(185, 102, 13.95, 1, 45, 30),
(186, 102, 8.95, 1, 45, 31),
(187, 102, 13.95, 1, 45, 32),
(188, 102, 8.95, 1, 45, 33),
(189, 102, 13.95, 1, 45, 34),
(190, 102, 13.95, 1, 45, 35),
(191, 102, 8.95, 1, 45, 36),
(192, 102, 13.95, 1, 45, 37),
(193, 102, 24.95, 1, 45, 38),
(194, 102, 8.95, 1, 45, 39),
(195, 102, 8.95, 1, 45, 40),
(196, 1021, 11.95, 1, 45, 41),
(197, 102, 11.95, 1, 45, 42),
(198, 102, 20.75, 1, 45, 43),
(199, 102, 10.75, 1, 45, 44),
(1100, 102, 8.75, 1, 45, 45),
(1101, 102, 10.75, 1, 45, 46),
(1102, 102, 8.75, 1, 45, 47),
(1103, 102, 8.75, 1, 45, 48),
(1104, 102, 8.75, 1, 45, 49),
(1105, 102, 11.95, 1, 45, 50),
(1106, 102, 11.95, 1, 45, 51),
(1107, 102, 14.95, 1, 45, 52),
(1108, 102, 14.95, 1, 45, 53),
(1109, 102, 14.95, 1, 45, 54),
(1110, 102, 18.95, 1, 45, 55);

-- MASTER CATEGORY ASSET
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, master_category_id, scope)
 VALUES 
(10010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 10, 'MASTER_CATEGORY'), 
(10020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 20, 'MASTER_CATEGORY'), 
(10030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 30, 'MASTER_CATEGORY'), 
(10040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 40, 'MASTER_CATEGORY'), 
(10050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 50, 'MASTER_CATEGORY'),
(10110, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'MASTER_CATEGORY'), 
(10120, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 20, 'MASTER_CATEGORY'), 
(10130, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 30, 'MASTER_CATEGORY'), 
(10140, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 40, 'MASTER_CATEGORY'),
(10150, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 50, 'MASTER_CATEGORY');

-- VIRTUAL CATEGORY ASSET
-- V_CAT_INT

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(1001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 1010, 'VIRTUAL_CATEGORY'), 
(1001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 1020, 'VIRTUAL_CATEGORY'), 
(1001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 1030, 'VIRTUAL_CATEGORY'), 
(1001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 1040, 'VIRTUAL_CATEGORY'), 
(1001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 1050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(1011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 1010, 'VIRTUAL_CATEGORY'), 
(1011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 1020, 'VIRTUAL_CATEGORY'), 
(1011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 1030, 'VIRTUAL_CATEGORY'), 
(1011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 1040, 'VIRTUAL_CATEGORY'),
(1011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 1050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(10010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 10110, 'VIRTUAL_CATEGORY'), 
(10010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 10120, 'VIRTUAL_CATEGORY'),
(10010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 10130, 'VIRTUAL_CATEGORY'), 
(10010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 10140, 'VIRTUAL_CATEGORY'),
(10010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 10150, 'VIRTUAL_CATEGORY'), 
(10010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 10210, 'VIRTUAL_CATEGORY'),
(10010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 10220, 'VIRTUAL_CATEGORY'), 
(10010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 10230, 'VIRTUAL_CATEGORY'),
(10010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 10310, 'VIRTUAL_CATEGORY'), 
(10010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 10320, 'VIRTUAL_CATEGORY'),
(10010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 10410, 'VIRTUAL_CATEGORY'), 
(10010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 10420, 'VIRTUAL_CATEGORY'),
(10010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 10510, 'VIRTUAL_CATEGORY'), 
(10010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 10520, 'VIRTUAL_CATEGORY');

-- V_CAT_FRA
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(2001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 2010, 'VIRTUAL_CATEGORY'), 
(2001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 2020, 'VIRTUAL_CATEGORY'), 
(2001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 2030, 'VIRTUAL_CATEGORY'), 
(2001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 2040, 'VIRTUAL_CATEGORY'), 
(2001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 2050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(2011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 2010, 'VIRTUAL_CATEGORY'), 
(2011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 2020, 'VIRTUAL_CATEGORY'), 
(2011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 2030, 'VIRTUAL_CATEGORY'), 
(2011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 2040, 'VIRTUAL_CATEGORY'),
(2011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 2050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(20010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 20110, 'VIRTUAL_CATEGORY'), 
(20010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 20120, 'VIRTUAL_CATEGORY'),
(20010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 20130, 'VIRTUAL_CATEGORY'), 
(20010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 20140, 'VIRTUAL_CATEGORY'),
(20010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 20150, 'VIRTUAL_CATEGORY'), 
(20010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 20210, 'VIRTUAL_CATEGORY'),
(20010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 20220, 'VIRTUAL_CATEGORY'), 
(20010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 20230, 'VIRTUAL_CATEGORY'),
(20010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 20310, 'VIRTUAL_CATEGORY'), 
(20010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 20320, 'VIRTUAL_CATEGORY'),
(20010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 20410, 'VIRTUAL_CATEGORY'), 
(20010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 20420, 'VIRTUAL_CATEGORY'),
(20010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 20510, 'VIRTUAL_CATEGORY'), 
(20010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 20520, 'VIRTUAL_CATEGORY');

-- V_CAT_ESP
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(3001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 3010, 'VIRTUAL_CATEGORY'), 
(3001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 3020, 'VIRTUAL_CATEGORY'), 
(3001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 3030, 'VIRTUAL_CATEGORY'), 
(3001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 3040, 'VIRTUAL_CATEGORY'), 
(3001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 3050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(3011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 3010, 'VIRTUAL_CATEGORY'), 
(3011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 3020, 'VIRTUAL_CATEGORY'), 
(3011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 3030, 'VIRTUAL_CATEGORY'), 
(3011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 3040, 'VIRTUAL_CATEGORY'),
(3011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 3050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(30010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 30110, 'VIRTUAL_CATEGORY'), 
(30010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 30120, 'VIRTUAL_CATEGORY'),
(30010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 30130, 'VIRTUAL_CATEGORY'), 
(30010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 30140, 'VIRTUAL_CATEGORY'),
(30010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 30150, 'VIRTUAL_CATEGORY'), 
(30010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 30210, 'VIRTUAL_CATEGORY'),
(30010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 30220, 'VIRTUAL_CATEGORY'), 
(30010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 30230, 'VIRTUAL_CATEGORY'),
(30010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 30310, 'VIRTUAL_CATEGORY'), 
(30010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 30320, 'VIRTUAL_CATEGORY'),
(30010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 30410, 'VIRTUAL_CATEGORY'), 
(30010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 30420, 'VIRTUAL_CATEGORY'),
(30010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 30510, 'VIRTUAL_CATEGORY'), 
(30010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 30520, 'VIRTUAL_CATEGORY');

-- V_CAT_USA
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(4001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 4010, 'VIRTUAL_CATEGORY'), 
(4001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 4020, 'VIRTUAL_CATEGORY'), 
(4001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 4030, 'VIRTUAL_CATEGORY'), 
(4001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 4040, 'VIRTUAL_CATEGORY'), 
(4001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 4050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(4011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 4010, 'VIRTUAL_CATEGORY'), 
(4011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 4020, 'VIRTUAL_CATEGORY'), 
(4011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 4030, 'VIRTUAL_CATEGORY'), 
(4011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 4040, 'VIRTUAL_CATEGORY'),
(4011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 4050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(40010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 40110, 'VIRTUAL_CATEGORY'), 
(40010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 40120, 'VIRTUAL_CATEGORY'),
(40010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 40130, 'VIRTUAL_CATEGORY'), 
(40010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 40140, 'VIRTUAL_CATEGORY'),
(40010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 40150, 'VIRTUAL_CATEGORY'), 
(40010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 40210, 'VIRTUAL_CATEGORY'),
(40010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 40220, 'VIRTUAL_CATEGORY'), 
(40010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 40230, 'VIRTUAL_CATEGORY'),
(40010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 40310, 'VIRTUAL_CATEGORY'), 
(40010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 40320, 'VIRTUAL_CATEGORY'),
(40010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 40410, 'VIRTUAL_CATEGORY'), 
(40010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 40420, 'VIRTUAL_CATEGORY'),
(40010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 40510, 'VIRTUAL_CATEGORY'), 
(40010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 40520, 'VIRTUAL_CATEGORY');

-- V_CAT_CAN
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(5001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 5010, 'VIRTUAL_CATEGORY'), 
(5001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 5020, 'VIRTUAL_CATEGORY'), 
(5001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 5030, 'VIRTUAL_CATEGORY'), 
(5001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 5040, 'VIRTUAL_CATEGORY'), 
(5001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 5050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(5011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 5010, 'VIRTUAL_CATEGORY'), 
(5011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 5020, 'VIRTUAL_CATEGORY'), 
(5011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 5030, 'VIRTUAL_CATEGORY'), 
(5011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 5040, 'VIRTUAL_CATEGORY'),
(5011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 5050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(50010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 50110, 'VIRTUAL_CATEGORY'), 
(50010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 50120, 'VIRTUAL_CATEGORY'),
(50010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 50130, 'VIRTUAL_CATEGORY'), 
(50010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 50140, 'VIRTUAL_CATEGORY'),
(50010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 50150, 'VIRTUAL_CATEGORY'), 
(50010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 50210, 'VIRTUAL_CATEGORY'),
(50010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 50220, 'VIRTUAL_CATEGORY'), 
(50010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 50230, 'VIRTUAL_CATEGORY'),
(50010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 50310, 'VIRTUAL_CATEGORY'), 
(50010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 50320, 'VIRTUAL_CATEGORY'),
(50010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 50410, 'VIRTUAL_CATEGORY'), 
(50010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 50420, 'VIRTUAL_CATEGORY'),
(50010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 50510, 'VIRTUAL_CATEGORY'), 
(50010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 50520, 'VIRTUAL_CATEGORY');

-- V_CAT_BRA
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(6001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 6010, 'VIRTUAL_CATEGORY'), 
(6001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 6020, 'VIRTUAL_CATEGORY'), 
(6001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 6030, 'VIRTUAL_CATEGORY'), 
(6001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 6040, 'VIRTUAL_CATEGORY'), 
(6001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 6050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(6011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 6010, 'VIRTUAL_CATEGORY'), 
(6011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 6020, 'VIRTUAL_CATEGORY'), 
(6011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 6030, 'VIRTUAL_CATEGORY'), 
(6011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 6040, 'VIRTUAL_CATEGORY'),
(6011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 6050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(60010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 60110, 'VIRTUAL_CATEGORY'), 
(60010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 60120, 'VIRTUAL_CATEGORY'),
(60010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 60130, 'VIRTUAL_CATEGORY'), 
(60010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 60140, 'VIRTUAL_CATEGORY'),
(60010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 60150, 'VIRTUAL_CATEGORY'), 
(60010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 60210, 'VIRTUAL_CATEGORY'),
(60010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 60220, 'VIRTUAL_CATEGORY'), 
(60010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 60230, 'VIRTUAL_CATEGORY'),
(60010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 60310, 'VIRTUAL_CATEGORY'), 
(60010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 60320, 'VIRTUAL_CATEGORY'),
(60010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 60410, 'VIRTUAL_CATEGORY'), 
(60010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 60420, 'VIRTUAL_CATEGORY'),
(60010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 60510, 'VIRTUAL_CATEGORY'), 
(60010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 60520, 'VIRTUAL_CATEGORY');

-- V_CAT_ARG
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(7001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 7010, 'VIRTUAL_CATEGORY'), 
(7001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 7020, 'VIRTUAL_CATEGORY'), 
(7001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 7030, 'VIRTUAL_CATEGORY'), 
(7001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 7040, 'VIRTUAL_CATEGORY'), 
(7001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 7050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(7011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 7010, 'VIRTUAL_CATEGORY'), 
(7011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 7020, 'VIRTUAL_CATEGORY'), 
(7011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 7030, 'VIRTUAL_CATEGORY'), 
(7011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 7040, 'VIRTUAL_CATEGORY'),
(7011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 7050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(70010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 70110, 'VIRTUAL_CATEGORY'), 
(70010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 70120, 'VIRTUAL_CATEGORY'),
(70010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 70130, 'VIRTUAL_CATEGORY'), 
(70010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 70140, 'VIRTUAL_CATEGORY'),
(70010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 70150, 'VIRTUAL_CATEGORY'), 
(70010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 70210, 'VIRTUAL_CATEGORY'),
(70010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 70220, 'VIRTUAL_CATEGORY'), 
(70010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 70230, 'VIRTUAL_CATEGORY'),
(70010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 70310, 'VIRTUAL_CATEGORY'), 
(70010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 70320, 'VIRTUAL_CATEGORY'),
(70010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 70410, 'VIRTUAL_CATEGORY'), 
(70010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 70420, 'VIRTUAL_CATEGORY'),
(70010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 70510, 'VIRTUAL_CATEGORY'), 
(70010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 70520, 'VIRTUAL_CATEGORY');

-- V_CAT_CHN
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(8001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 8010, 'VIRTUAL_CATEGORY'), 
(8001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 8020, 'VIRTUAL_CATEGORY'), 
(8001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 8030, 'VIRTUAL_CATEGORY'), 
(8001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 8040, 'VIRTUAL_CATEGORY'), 
(8001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 8050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(8011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 8010, 'VIRTUAL_CATEGORY'), 
(8011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 8020, 'VIRTUAL_CATEGORY'), 
(8011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 8030, 'VIRTUAL_CATEGORY'), 
(8011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 8040, 'VIRTUAL_CATEGORY'),
(8011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 8050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(80010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 80110, 'VIRTUAL_CATEGORY'), 
(80010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 80120, 'VIRTUAL_CATEGORY'),
(80010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 80130, 'VIRTUAL_CATEGORY'), 
(80010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 80140, 'VIRTUAL_CATEGORY'),
(80010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 80150, 'VIRTUAL_CATEGORY'), 
(80010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 80210, 'VIRTUAL_CATEGORY'),
(80010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 80220, 'VIRTUAL_CATEGORY'), 
(80010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 80230, 'VIRTUAL_CATEGORY'),
(80010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 80310, 'VIRTUAL_CATEGORY'), 
(80010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 80320, 'VIRTUAL_CATEGORY'),
(80010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 80410, 'VIRTUAL_CATEGORY'), 
(80010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 80420, 'VIRTUAL_CATEGORY'),
(80010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 80510, 'VIRTUAL_CATEGORY'), 
(80010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 80520, 'VIRTUAL_CATEGORY');

-- V_CAT_JPN
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(9001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 9010, 'VIRTUAL_CATEGORY'), 
(9001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 9020, 'VIRTUAL_CATEGORY'), 
(9001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 9030, 'VIRTUAL_CATEGORY'), 
(9001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 9040, 'VIRTUAL_CATEGORY'), 
(9001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 9050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(9011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 9010, 'VIRTUAL_CATEGORY'), 
(9011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 9020, 'VIRTUAL_CATEGORY'), 
(9011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 9030, 'VIRTUAL_CATEGORY'), 
(9011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 9040, 'VIRTUAL_CATEGORY'),
(9011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 9050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(90010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 90110, 'VIRTUAL_CATEGORY'), 
(90010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 90120, 'VIRTUAL_CATEGORY'),
(90010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 90130, 'VIRTUAL_CATEGORY'), 
(90010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 90140, 'VIRTUAL_CATEGORY'),
(90010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 90150, 'VIRTUAL_CATEGORY'), 
(90010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 90210, 'VIRTUAL_CATEGORY'),
(90010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 90220, 'VIRTUAL_CATEGORY'), 
(90010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 90230, 'VIRTUAL_CATEGORY'),
(90010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 90310, 'VIRTUAL_CATEGORY'), 
(90010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 90320, 'VIRTUAL_CATEGORY'),
(90010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 90410, 'VIRTUAL_CATEGORY'), 
(90010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 90420, 'VIRTUAL_CATEGORY'),
(90010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 90510, 'VIRTUAL_CATEGORY'), 
(90010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 90520, 'VIRTUAL_CATEGORY');

-- V_CAT_VNM
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(10001010, 'image ...', 'cat-1-img-1.jpg', 0, 'image 5', 1, 'BACKGROUND', null, 1, 10010, 'VIRTUAL_CATEGORY'), 
(10001020, 'image ...', 'cat-1-img-1.jpg', 0, 'image 6', 1, 'BACKGROUND', null, 1, 10020, 'VIRTUAL_CATEGORY'), 
(10001030, 'image ...', 'cat-1-img-1.jpg', 0, 'image 7', 1, 'BACKGROUND', null, 1, 10030, 'VIRTUAL_CATEGORY'), 
(10001040, 'image ...', 'cat-1-img-1.jpg', 0, 'image 8', 1, 'BACKGROUND', null, 1, 10040, 'VIRTUAL_CATEGORY'), 
(10001050, 'image ...', 'cat-1-img-1.jpg', 0, 'image 9', 1, 'BACKGROUND', null, 1, 10050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(10011010, 'image ...', 'cat-1-img-1-small.png', 1, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10010, 'VIRTUAL_CATEGORY'), 
(10011020, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 10020, 'VIRTUAL_CATEGORY'), 
(10011030, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 10030, 'VIRTUAL_CATEGORY'), 
(10011040, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 10040, 'VIRTUAL_CATEGORY'),
(10011050, 'image ...', 'cat-1-img-1-small.jpg', 0, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 10050, 'VIRTUAL_CATEGORY');

INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, virtual_category_id, scope)
 VALUES 
(100010110, 'image ...', 'cat-1-img-1.jpg', 0, 'image 101', 1, 'SLIDESHOW', null, 1, 100110, 'VIRTUAL_CATEGORY'), 
(100010120, 'image ...', 'cat-1-img-1.jpg', 0, 'image 102', 1, 'SLIDESHOW', null, 1, 100120, 'VIRTUAL_CATEGORY'),
(100010130, 'image ...', 'cat-1-img-1.jpg', 0, 'image 103', 1, 'SLIDESHOW', null, 1, 100130, 'VIRTUAL_CATEGORY'), 
(100010140, 'image ...', 'cat-1-img-1.jpg', 0, 'image 104', 1, 'SLIDESHOW', null, 1, 100140, 'VIRTUAL_CATEGORY'),
(100010150, 'image ...', 'cat-1-img-1.jpg', 0, 'image 105', 1, 'SLIDESHOW', null, 1, 100150, 'VIRTUAL_CATEGORY'), 
(100010210, 'image ...', 'cat-2-img-1.jpg', 0, 'image 201', 1, 'SLIDESHOW', null, 1, 100210, 'VIRTUAL_CATEGORY'),
(100010220, 'image ...', 'cat-2-img-1.jpg', 0, 'image 202', 1, 'SLIDESHOW', null, 1, 100220, 'VIRTUAL_CATEGORY'), 
(100010230, 'image ...', 'cat-2-img-1.jpg', 0, 'image 203', 1, 'SLIDESHOW', null, 1, 100230, 'VIRTUAL_CATEGORY'),
(100010310, 'image ...', 'cat-3-img-1.jpg', 0, 'image 301', 1, 'SLIDESHOW', null, 1, 100310, 'VIRTUAL_CATEGORY'), 
(100010320, 'image ...', 'cat-3-img-1.jpg', 0, 'image 302', 1, 'SLIDESHOW', null, 1, 100320, 'VIRTUAL_CATEGORY'),
(100010410, 'image ...', 'cat-4-img-1.jpg', 0, 'image 401', 1, 'SLIDESHOW', null, 1, 100410, 'VIRTUAL_CATEGORY'), 
(100010420, 'image ...', 'cat-4-img-1.jpg', 0, 'image 402', 1, 'SLIDESHOW', null, 1, 100420, 'VIRTUAL_CATEGORY'),
(100010510, 'image ...', 'cat-5-img-1.jpg', 0, 'image 501', 1, 'SLIDESHOW', null, 1, 100510, 'VIRTUAL_CATEGORY'), 
(100010520, 'image ...', 'cat-5-img-1.jpg', 0, 'image 502', 1, 'SLIDESHOW', null, 1, 100520, 'VIRTUAL_CATEGORY');

-- PRODUCT MARKETING ASSET
INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, product_marketing_id, scope)
 VALUES 
(1, 'image ...', 'blueberry-streusel-coffee-icon.jpg', 1, 'image l', 1, 'PACKSHOT', 'SMALL', 1, 1, 'PRODUCT_MARKETING'), 
(2, 'image ...', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 2', 1, 'PACKSHOT', 'SMALL', 1, 2, 'PRODUCT_MARKETING'), 
(3, 'image ...', 'world-art-tumbler-icon.jpg', 0, 'image 3', 1, 'PACKSHOT', 'SMALL', 1, 3, 'PRODUCT_MARKETING'), 
(4, 'image ...', 'brazil-cerrado-coffee-icon.jpg', 0, 'image 4', 1, 'PACKSHOT', 'SMALL', 1, 4, 'PRODUCT_MARKETING'), 
(5, 'image ...', 'house-blend-coffee-icon.jpg', 1, 'image 5', 1, 'PACKSHOT', 'SMALL', 1, 5, 'PRODUCT_MARKETING'), 
(6, 'image ...', 'decaf-house-blend-coffee-icon.jpg', 0, 'image 6', 1, 'PACKSHOT', 'SMALL', 1, 6, 'PRODUCT_MARKETING'), 
(7, 'image ...', 'house-blend-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 7', 1, 'PACKSHOT', 'SMALL', 1, 7, 'PRODUCT_MARKETING'), 
(8, 'image ...', 'ethiopia-yirgacheffe-coffee-icon.jpg', 0, 'image 8', 1, 'PACKSHOT', 'SMALL', 1, 8, 'PRODUCT_MARKETING'),
(9, 'image ...', 'kenya-aa-coffee-icon.jpg', 1, 'image 9', 1, 'PACKSHOT', 'SMALL', 1, 9, 'PRODUCT_MARKETING'), 
(10, 'image ...', 'colombia-narino-coffee-icon.jpg', 0, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'PRODUCT_MARKETING'), 
(11, 'image ...', 'costa-rica-cascada-tarrazu-coffee-icon.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 11, 'PRODUCT_MARKETING'), 
(12, 'image ...', 'costa-rica-la-minita-tarrazu-coffee-icon.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 12, 'PRODUCT_MARKETING'),
(13, 'image ...', 'decaf-colombia-narino-coffee-icon.jpg', 1, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 13, 'PRODUCT_MARKETING'), 
(14, 'image ...', 'guatemala-antigua-coffee-icon.jpg', 0, 'image 14', 1, 'PACKSHOT', 'SMALL', 1, 14, 'PRODUCT_MARKETING'),
(15, 'image ...', 'mocha-java-coffee-icon.jpg', 0, 'image 15', 1, 'PACKSHOT', 'SMALL', 1, 15, 'PRODUCT_MARKETING'), 
(16, 'image ...', 'papua-new-guinea-sigri-coffee-icon.jpg', 0, 'image 16', 1, 'PACKSHOT', 'SMALL', 1, 16, 'PRODUCT_MARKETING'), 
(17, 'image ...', 'sumatra-mandheling-coffee-icon.jpg', 0, 'image 17', 1, 'PACKSHOT', 'SMALL', 1, 17, 'PRODUCT_MARKETING'),
(18, 'image ...', 'breakfast-blend-coffee-icon.jpg', 1, 'image 18', 1, 'PACKSHOT', 'SMALL', 1, 18, 'PRODUCT_MARKETING'), 
(19, 'image ...', 'colombia-narino-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 19', 1, 'PACKSHOT', 'SMALL', 1, 19, 'PRODUCT_MARKETING'),
(20, 'image ...', 'costa-rica-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 20', 1, 'PACKSHOT', 'SMALL', 1, 20, 'PRODUCT_MARKETING'), 
(21, 'image ...', 'holiday-blend-coffee-icon.jpg', 0, 'image 21', 1, 'PACKSHOT', 'SMALL', 1, 21, 'PRODUCT_MARKETING'), 
(22, 'image ...', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 22', 1, 'PACKSHOT', 'SMALL', 1, 22, 'PRODUCT_MARKETING'),
(23, 'image ...', 'university-blend-coffee-icon.jpg', 1, 'image 23', 1, 'PACKSHOT', 'SMALL', 1, 23, 'PRODUCT_MARKETING'), 
(24, 'image ...', 'harvest-blend-coffee-icon.jpg', 0, 'image 24', 1, 'PACKSHOT', 'SMALL', 1, 24, 'PRODUCT_MARKETING'),
(25, 'image ...', 'decaf-espesso-roast-coffee-icon.jpg', 0, 'image 25', 1, 'PACKSHOT', 'SMALL', 1, 25, 'PRODUCT_MARKETING'), 
(26, 'image ...', 'decaf-french-roast-coffee-icon.jpg', 0, 'image 26', 1, 'PACKSHOT', 'SMALL', 1, 26, 'PRODUCT_MARKETING'), 
(27, 'image ...', 'decaf-viennese-coffee-icon.jpg', 0, 'image 27', 1, 'PACKSHOT', 'SMALL', 1, 27, 'PRODUCT_MARKETING'),
(28, 'image ...', 'french-roast-coffee-icon.jpg', 1, 'image 28', 1, 'PACKSHOT', 'SMALL', 1, 28, 'PRODUCT_MARKETING'), 
(29, 'image ...', 'italian-roast-coffee-icon.jpg', 0, 'image 29', 1, 'PACKSHOT', 'SMALL', 1, 29, 'PRODUCT_MARKETING'),
(30, 'image ...', 'tanzania-peaberry-coffee-icon.jpg', 0, 'image 30', 1, 'PACKSHOT', 'SMALL', 1, 30, 'PRODUCT_MARKETING'), 
(31, 'image ...', 'colombia-narino-dark-coffee-icon.jpg', 0, 'image 31', 1, 'PACKSHOT', 'SMALL', 1, 31, 'PRODUCT_MARKETING'), 
(32, 'image ...', 'espresso-roast-coffee-icon.jpg', 0, 'image 32', 1, 'PACKSHOT', 'SMALL', 1, 32, 'PRODUCT_MARKETING'),
(33, 'image ...', 'master-davey-set-blue-tiger-tea-book-icon.jpg', 1, 'image 33', 1, 'PACKSHOT', 'SMALL', 1, 33, 'PRODUCT_MARKETING'), 
(34, 'image ...', 'winter-dream-tea-icon.jpg', 0, 'image 34', 1, 'PACKSHOT', 'SMALL', 1, 34, 'PRODUCT_MARKETING'),
(35, 'image ...', 'peppermint-stick-tea-icon.jpg', 0, 'image 35', 1, 'PACKSHOT', 'SMALL', 1, 35, 'PRODUCT_MARKETING'), 
(36, 'image ...', 'los-angeles-sunshine-blend-tea-icon.jpg', 0, 'image 36', 1, 'PACKSHOT', 'SMALL', 1, 36, 'PRODUCT_MARKETING'), 
(37, 'image ...', 'georgia-peach-ginger-white-tea-icon.jpg', 0, 'image 37', 1, 'PACKSHOT', 'SMALL', 1, 37, 'PRODUCT_MARKETING'),
(38, 'image ...', 'jasmine-dragon-phoenix-pearl-tea-icon.jpg', 1, 'image 38', 1, 'PACKSHOT', 'SMALL', 1, 38, 'PRODUCT_MARKETING'), 
(39, 'image ...', 'genmaicha-green-tea-icon.jpg', 0, 'image 39', 1, 'PACKSHOT', 'SMALL', 1, 39, 'PRODUCT_MARKETING'),
(40, 'image ...', 'lung-ching-dragonwell-tea-icon.jpg', 0, 'image 40', 1, 'PACKSHOT', 'SMALL', 1, 40, 'PRODUCT_MARKETING'), 
(41, 'image ...', 'decaf-green-tea-icon.jpg', 0, 'image 41', 1, 'PACKSHOT', 'SMALL', 1, 41, 'PRODUCT_MARKETING'), 
(42, 'image ...', 'swedish-berries-tea-icon.jpg', 0, 'image 42', 1, 'PACKSHOT', 'SMALL', 1, 42, 'PRODUCT_MARKETING'),
(43, 'image ...', 'chai-rooibos-tea-icon.jpg', 1, 'image 43', 1, 'PACKSHOT', 'SMALL', 1, 43, 'PRODUCT_MARKETING'), 
(44, 'image ...', 'african-sunrise-tea-icon.jpg', 0, 'image 44', 1, 'PACKSHOT', 'SMALL', 1, 44, 'PRODUCT_MARKETING'),
(45, 'image ...', 'french-deluxe-vanilla-powder-icon.jpg', 0, 'image 45', 1, 'PACKSHOT', 'SMALL', 1, 45, 'PRODUCT_MARKETING'), 
(46, 'image ...', 'special-dutch-chocolate-powder-no-sugar-icon.jpg', 0, 'image 46', 1, 'PACKSHOT', 'SMALL', 1, 46, 'PRODUCT_MARKETING'), 
(47, 'image ...', 'americana-tumbler-icon.jpg', 0, 'image 47', 1, 'PACKSHOT', 'SMALL', 1, 47, 'PRODUCT_MARKETING'),
(48, 'image ...', 'bergamo-bottle-icon.jpg', 1, 'image 48', 1, 'PACKSHOT', 'SMALL', 1, 48, 'PRODUCT_MARKETING'), 
(49, 'image ...', 'the-jaidun-tumbler-icon.jpg', 0, 'image 49', 1, 'PACKSHOT', 'SMALL', 1, 49, 'PRODUCT_MARKETING'),
(50, 'image ...', 'bamboo-ceramic-tea-set-icon.jpg', 0, 'image 50', 1, 'PACKSHOT', 'SMALL', 1, 50, 'PRODUCT_MARKETING'),

(51, 'image ...', 'blueberry-streusel-coffee.jpg', 1, 'image 5l', 1, 'BACKGROUND', null, 1, 1, 'PRODUCT_MARKETING'), 
(52, 'image ...', 'bali-blue-moon-coffee.jpg', 0, 'image 52', 1, 'BACKGROUND', null, 1, 2, 'PRODUCT_MARKETING'), 
(53, 'image ...', 'world-art-tumbler.jpg', 0, 'image 53', 1, 'BACKGROUND', null, 1, 3, 'PRODUCT_MARKETING'), 
(54, 'image ...', 'brazil-cerrado-coffee.jpg', 0, 'image 54', 1, 'BACKGROUND', null, 1, 4, 'PRODUCT_MARKETING'), 
(55, 'image ...', 'house-blend-coffee.jpg', 1, 'image 55', 1, 'BACKGROUND', null, 1, 5, 'PRODUCT_MARKETING'), 
(56, 'image ...', 'decaf-house-blend-coffee.jpg', 0, 'image 56', 1, 'BACKGROUND', null, 1, 6, 'PRODUCT_MARKETING'), 
(57, 'image ...', 'house-blend-coffee-24-2oz-portion-packs.jpg', 0, 'image 57', 1, 'BACKGROUND', null, 1, 7, 'PRODUCT_MARKETING'), 
(58, 'image ...', 'ethiopia-yirgacheffe-coffee.jpg', 0, 'image 58', 1, 'BACKGROUND', null, 1, 8, 'PRODUCT_MARKETING'),
(59, 'image ...', 'kenya-aa-coffee.jpg', 1, 'image 59', 1, 'BACKGROUND', null, 1, 9, 'PRODUCT_MARKETING'), 
(60, 'image ...', 'colombia-narino-coffee.jpg', 0, 'image 60', 1, 'BACKGROUND', null, 1, 10, 'PRODUCT_MARKETING'), 
(61, 'image ...', 'costa-rica-cascada-tarrazu-coffee.jpg', 0, 'image 61', 1, 'BACKGROUND', null, 1, 11, 'PRODUCT_MARKETING'), 
(62, 'image ...', 'costa-rica-la-minita-tarrazu-coffee.jpg', 0, 'image 62', 1, 'BACKGROUND', null, 1, 12, 'PRODUCT_MARKETING'),
(63, 'image ...', 'decaf-colombia-narino-coffee.jpg', 1, 'image 63', 1, 'BACKGROUND', null, 1, 13, 'PRODUCT_MARKETING'), 
(64, 'image ...', 'guatemala-antigua-coffee.jpg', 0, 'image 64', 1, 'BACKGROUND', null, 1, 14, 'PRODUCT_MARKETING'),
(65, 'image ...', 'mocha-java-coffee.jpg', 0, 'image 65', 1, 'BACKGROUND', null, 1, 15, 'PRODUCT_MARKETING'), 
(66, 'image ...', 'papua-new-guinea-sigri-coffee.jpg', 0, 'image 66', 1, 'BACKGROUND', null, 1, 16, 'PRODUCT_MARKETING'), 
(67, 'image ...', 'sumatra-mandheling-coffee.jpg', 0, 'image 67', 1, 'BACKGROUND', null, 1, 17, 'PRODUCT_MARKETING'),
(68, 'image ...', 'breakfast-blend-coffee.jpg', 1, 'image 68', 1, 'BACKGROUND', null, 1, 18, 'PRODUCT_MARKETING'), 
(69, 'image ...', 'colombia-narino-coffee-24-2oz-portion-packs.jpg', 0, 'image 69', 1, 'BACKGROUND', null, 1, 19, 'PRODUCT_MARKETING'),
(70, 'image ...', 'costa-rica-coffee-24-2oz-portion-packs.jpg', 0, 'image 70', 1, 'BACKGROUND', null, 1, 20, 'PRODUCT_MARKETING'), 
(71, 'image ...', 'holiday-blend-coffee.jpg', 0, 'image 71', 1, 'BACKGROUND', null, 1, 21, 'PRODUCT_MARKETING'), 
(72, 'image ...', 'bali-blue-moon-coffee.jpg', 0, 'image 72', 1, 'BACKGROUND', null, 1, 22, 'PRODUCT_MARKETING'),
(73, 'image ...', 'university-blend-coffee.jpg', 1, 'image 73', 1, 'BACKGROUND', null, 1, 23, 'PRODUCT_MARKETING'), 
(74, 'image ...', 'harvest-blend-coffee.jpg', 0, 'image 74', 1, 'BACKGROUND', null, 1, 24, 'PRODUCT_MARKETING'),
(75, 'image ...', 'decaf-espesso-roast-coffee.jpg', 0, 'image 75', 1, 'BACKGROUND', null, 1, 25, 'PRODUCT_MARKETING'), 
(76, 'image ...', 'decaf-french-roast-coffee.jpg', 0, 'image 76', 1, 'BACKGROUND', null, 1, 26, 'PRODUCT_MARKETING'), 
(77, 'image ...', 'decaf-viennese-coffee.jpg', 0, 'image 77', 1, 'BACKGROUND', null, 1, 27, 'PRODUCT_MARKETING'),
(78, 'image ...', 'french-roast-coffee.jpg', 1, 'image 78', 1, 'BACKGROUND', null, 1, 28, 'PRODUCT_MARKETING'), 
(79, 'image ...', 'italian-roast-coffee.jpg', 0, 'image 79', 1, 'BACKGROUND', null, 1, 29, 'PRODUCT_MARKETING'),
(80, 'image ...', 'tanzania-peaberry-coffee.jpg', 0, 'image 80', 1, 'BACKGROUND', null, 1, 30, 'PRODUCT_MARKETING'), 
(81, 'image ...', 'colombia-narino-dark-coffee.jpg', 0, 'image 81', 1, 'BACKGROUND', null, 1, 31, 'PRODUCT_MARKETING'), 
(82, 'image ...', 'espresso-roast-coffee.jpg', 0, 'image 82', 1, 'BACKGROUND', null, 1, 32, 'PRODUCT_MARKETING'),
(83, 'image ...', 'master-davey-set-blue-tiger-tea-book.jpg', 1, 'image 83', 1, 'BACKGROUND', null, 1, 33, 'PRODUCT_MARKETING'), 
(84, 'image ...', 'winter-dream-tea.jpg', 0, 'image 84', 1, 'BACKGROUND', null, 1, 34, 'PRODUCT_MARKETING'),
(85, 'image ...', 'peppermint-stick-tea.jpg', 0, 'image 85', 1, 'BACKGROUND', null, 1, 35, 'PRODUCT_MARKETING'), 
(86, 'image ...', 'los-angeles-sunshine-blend-tea.jpg', 0, 'image 86', 1, 'BACKGROUND', null, 1, 36, 'PRODUCT_MARKETING'), 
(87, 'image ...', 'georgia-peach-ginger-white-tea.jpg', 0, 'image 87', 1, 'BACKGROUND', null, 1, 37, 'PRODUCT_MARKETING'),
(88, 'image ...', 'jasmine-dragon-phoenix-pearl-tea.jpg', 1, 'image 88', 1, 'BACKGROUND', null, 1, 38, 'PRODUCT_MARKETING'), 
(89, 'image ...', 'genmaicha-green-tea.jpg', 0, 'image 89', 1, 'BACKGROUND', null, 1, 39, 'PRODUCT_MARKETING'),
(90, 'image ...', 'lung-ching-dragonwell-tea.jpg', 0, 'image 90', 1, 'BACKGROUND', null, 1, 40, 'PRODUCT_MARKETING'), 
(91, 'image ...', 'decaf-green-tea.jpg', 0, 'image 91', 1, 'BACKGROUND', null, 1, 41, 'PRODUCT_MARKETING'), 
(92, 'image ...', 'swedish-berries-tea.jpg', 0, 'image 92', 1, 'BACKGROUND', null, 1, 42, 'PRODUCT_MARKETING'),
(93, 'image ...', 'chai-rooibos-tea.jpg', 1, 'image 93', 1, 'BACKGROUND', null, 1, 43, 'PRODUCT_MARKETING'), 
(94, 'image ...', 'african-sunrise-tea.jpg', 0, 'image 94', 1, 'BACKGROUND', null, 1, 44, 'PRODUCT_MARKETING'),
(95, 'image ...', 'french-deluxe-vanilla-powder.jpg', 0, 'image 95', 1, 'BACKGROUND', null, 1, 45, 'PRODUCT_MARKETING'), 
(96, 'image ...', 'special-dutch-chocolate-powder-no-sugar.jpg', 0, 'image 96', 1, 'BACKGROUND', null, 1, 46, 'PRODUCT_MARKETING'), 
(97, 'image ...', 'americana-tumbler.jpg', 0, 'image 97', 1, 'BACKGROUND', null, 1, 47, 'PRODUCT_MARKETING'),
(98, 'image ...', 'bergamo-bottle.jpg', 1, 'image 98', 1, 'BACKGROUND', null, 1, 48, 'PRODUCT_MARKETING'), 
(99, 'image ...', 'the-jaidun-tumbler.jpg', 0, 'image 99', 1, 'BACKGROUND', null, 1, 49, 'PRODUCT_MARKETING'),
(100,'image ...', 'bamboo-ceramic-tea-set.jpg', 0, 'image 100', 1, 'BACKGROUND', null, 1, 50, 'PRODUCT_MARKETING');


INSERT INTO teco_asset  
(id, description, path, is_default, name, version, type, size, is_global, product_sku_id, scope)
 VALUES
(101, 'image ...', 'blueberry-struesel-coffee-icon.jpg', 1, 'image l', 1, 'PACKSHOT', 'SMALL', 1, 1, 'PRODUCT_SKU'), 
(102, 'image ...', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 2', 1, 'PACKSHOT', 'SMALL', 1, 2, 'PRODUCT_SKU'), 
(103, 'image ...', 'world-art-tumbler-icon.jpg', 0, 'image 3', 1, 'PACKSHOT', 'SMALL', 1, 3, 'PRODUCT_SKU'), 
(104, 'image ...', 'brazil-cerrado-coffee-icon.jpg', 0, 'image 4', 1, 'PACKSHOT', 'SMALL', 1, 4, 'PRODUCT_SKU'), 
(105, 'image ...', 'house-blend-coffee-icon.jpg', 1, 'image 5', 1, 'PACKSHOT', 'SMALL', 1, 5, 'PRODUCT_SKU'), 
(106, 'image ...', 'decaf-house-blend-coffee-icon.jpg', 0, 'image 6', 1, 'PACKSHOT', 'SMALL', 1, 6, 'PRODUCT_SKU'), 
(107, 'image ...', 'house-blend-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 7', 1, 'PACKSHOT', 'SMALL', 1, 7, 'PRODUCT_SKU'), 
(108, 'image ...', 'ethiopia-yirgacheffe-coffee-icon.jpg', 0, 'image 8', 1, 'PACKSHOT', 'SMALL', 1, 8, 'PRODUCT_SKU'),
(109, 'image ...', 'kenya-aa-coffee-icon.jpg', 1, 'image 9', 1, 'PACKSHOT', 'SMALL', 1, 9, 'PRODUCT_SKU'), 
(110, 'image ...', 'colombia-narino-coffee-icon.jpg', 0, 'image 10', 1, 'PACKSHOT', 'SMALL', 1, 10, 'PRODUCT_SKU'), 
(111, 'image ...', 'costa-rica-cascada-tarrazu-coffee-icon.jpg', 0, 'image 11', 1, 'PACKSHOT', 'SMALL', 1, 11, 'PRODUCT_SKU'), 
(112, 'image ...', 'costa-rica-la-minita-tarrazu-coffee-icon.jpg', 0, 'image 12', 1, 'PACKSHOT', 'SMALL', 1, 12, 'PRODUCT_SKU'),
(113, 'image ...', 'decaf-colombia-narino-coffee-icon.jpg', 1, 'image 13', 1, 'PACKSHOT', 'SMALL', 1, 13, 'PRODUCT_SKU'), 
(114, 'image ...', 'guatemala-antigua-coffee-icon.jpg', 0, 'image 14', 1, 'PACKSHOT', 'SMALL', 1, 14, 'PRODUCT_SKU'),
(115, 'image ...', 'mocha-java-coffee-icon.jpg', 0, 'image 15', 1, 'PACKSHOT', 'SMALL', 1, 15, 'PRODUCT_SKU'), 
(116, 'image ...', 'papua-new-guinea-sigri-coffee-icon.jpg', 0, 'image 16', 1, 'PACKSHOT', 'SMALL', 1, 16, 'PRODUCT_SKU'), 
(117, 'image ...', 'sumatra-mandheling-coffee-icon.jpg', 0, 'image 17', 1, 'PACKSHOT', 'SMALL', 1, 17, 'PRODUCT_SKU'),
(118, 'image ...', 'breakfast-blend-coffee-icon.jpg', 1, 'image 18', 1, 'PACKSHOT', 'SMALL', 1, 18, 'PRODUCT_SKU'), 
(119, 'image ...', 'colombia-narino-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 19', 1, 'PACKSHOT', 'SMALL', 1, 19, 'PRODUCT_SKU'),
(120, 'image ...', 'costa-rica-coffee-24-2oz-portion-packs-icon.jpg', 0, 'image 20', 1, 'PACKSHOT', 'SMALL', 1, 20, 'PRODUCT_SKU'), 
(121, 'image ...', 'holiday-blend-coffee-icon.jpg', 0, 'image 21', 1, 'PACKSHOT', 'SMALL', 1, 21, 'PRODUCT_SKU'), 
(122, 'image ...', 'bali-blue-moon-coffee-icon.jpg', 0, 'image 22', 1, 'PACKSHOT', 'SMALL', 1, 22, 'PRODUCT_SKU'),
(123, 'image ...', 'university-blend-coffee-icon.jpg', 1, 'image 23', 1, 'PACKSHOT', 'SMALL', 1, 23, 'PRODUCT_SKU'), 
(124, 'image ...', 'harvest-blend-coffee-icon.jpg', 0, 'image 24', 1, 'PACKSHOT', 'SMALL', 1, 24, 'PRODUCT_SKU'),
(125, 'image ...', 'decaf-espesso-roast-coffee-icon.jpg', 0, 'image 25', 1, 'PACKSHOT', 'SMALL', 1, 25, 'PRODUCT_SKU'), 
(126, 'image ...', 'decaf-french-roast-coffee-icon.jpg', 0, 'image 26', 1, 'PACKSHOT', 'SMALL', 1, 26, 'PRODUCT_SKU'), 
(127, 'image ...', 'decaf-viennese-coffee-icon.jpg', 0, 'image 27', 1, 'PACKSHOT', 'SMALL', 1, 27, 'PRODUCT_SKU'),
(128, 'image ...', 'french-roast-coffee-icon.jpg', 1, 'image 28', 1, 'PACKSHOT', 'SMALL', 1, 28, 'PRODUCT_SKU'), 
(129, 'image ...', 'italian-roast-coffee-icon.jpg', 0, 'image 29', 1, 'PACKSHOT', 'SMALL', 1, 29, 'PRODUCT_SKU'),
(130, 'image ...', 'tanzania-peaberry-coffee-icon.jpg', 0, 'image 30', 1, 'PACKSHOT', 'SMALL', 1, 30, 'PRODUCT_SKU'), 
(131, 'image ...', 'colombia-narino-dark-coffee-icon.jpg', 0, 'image 31', 1, 'PACKSHOT', 'SMALL', 1, 31, 'PRODUCT_SKU'), 
(132, 'image ...', 'espresso-roast-coffee-icon.jpg', 0, 'image 32', 1, 'PACKSHOT', 'SMALL', 1, 32, 'PRODUCT_SKU'),
(133, 'image ...', 'master-davey-set-blue-tiger-tea-book-icon.jpg', 1, 'image 33', 1, 'PACKSHOT', 'SMALL', 1, 33, 'PRODUCT_SKU'), 
(134, 'image ...', 'winter-dream-tea-icon.jpg', 0, 'image 34', 1, 'PACKSHOT', 'SMALL', 1, 34, 'PRODUCT_SKU'),
(135, 'image ...', 'peppermint-stick-tea-icon.jpg', 0, 'image 35', 1, 'PACKSHOT', 'SMALL', 1, 35, 'PRODUCT_SKU'), 
(136, 'image ...', 'los-angeles-sunshine-blend-tea-icon.jpg', 0, 'image 36', 1, 'PACKSHOT', 'SMALL', 1, 36, 'PRODUCT_SKU'), 
(137, 'image ...', 'georgia-peach-ginger-white-tea-icon.jpg', 0, 'image 37', 1, 'PACKSHOT', 'SMALL', 1, 37, 'PRODUCT_SKU'),
(138, 'image ...', 'jasmine-dragon-phoenix-pearl-tea-icon.jpg', 1, 'image 38', 1, 'PACKSHOT', 'SMALL', 1, 38, 'PRODUCT_SKU'), 
(139, 'image ...', 'genmaicha-green-tea-icon.jpg', 0, 'image 39', 1, 'PACKSHOT', 'SMALL', 1, 39, 'PRODUCT_SKU'),
(140, 'image ...', 'lung-ching-dragonwell-tea-icon.jpg', 0, 'image 40', 1, 'PACKSHOT', 'SMALL', 1, 40, 'PRODUCT_SKU'), 
(141, 'image ...', 'decaf-green-tea-icon.jpg', 0, 'image 41', 1, 'PACKSHOT', 'SMALL', 1, 41, 'PRODUCT_SKU'), 
(142, 'image ...', 'swedish-berries-tea-icon.jpg', 0, 'image 42', 1, 'PACKSHOT', 'SMALL', 1, 42, 'PRODUCT_SKU'),
(143, 'image ...', 'chai-rooibos-tea-icon.jpg', 1, 'image 43', 1, 'PACKSHOT', 'SMALL', 1, 43, 'PRODUCT_SKU'), 
(144, 'image ...', 'african-sunrise-tea-icon.jpg', 0, 'image 44', 1, 'PACKSHOT', 'SMALL', 1, 44, 'PRODUCT_SKU'),
(145, 'image ...', 'french-deluxe-vanilla-powder-icon.jpg', 0, 'image 45', 1, 'PACKSHOT', 'SMALL', 1, 45, 'PRODUCT_SKU'), 
(146, 'image ...', 'special-dutch-chocolate-powder-no-sugar-icon.jpg', 0, 'image 46', 1, 'PACKSHOT', 'SMALL', 1, 46, 'PRODUCT_SKU'), 
(147, 'image ...', 'americana-tumbler-icon.jpg', 0, 'image 47', 1, 'PACKSHOT', 'SMALL', 1, 47, 'PRODUCT_SKU'),
(148, 'image ...', 'bergamo-bottle-icon.jpg', 1, 'image 48', 1, 'PACKSHOT', 'SMALL', 1, 48, 'PRODUCT_SKU'), 
(149, 'image ...', 'the-jaidun-tumbler-icon.jpg', 0, 'image 49', 1, 'PACKSHOT', 'SMALL', 1, 49, 'PRODUCT_SKU'),
(150, 'image ...', 'bamboo-ceramic-tea-set-icon.jpg', 0, 'image 50', 1, 'PACKSHOT', 'SMALL', 1, 50, 'PRODUCT_SKU'),

(151, 'image ...', 'blueberry-struesel-coffee.jpg', 1, 'image 5l', 1, 'BACKGROUND', null, 1, 1, 'PRODUCT_SKU'), 
(152, 'image ...', 'bali-blue-moon-coffee.jpg', 0, 'image 52', 1, 'BACKGROUND', null, 1, 2, 'PRODUCT_SKU'), 
(153, 'image ...', 'world-art-tumbler.jpg', 0, 'image 53', 1, 'BACKGROUND', null, 1, 3, 'PRODUCT_SKU'), 
(154, 'image ...', 'brazil-cerrado-coffee.jpg', 0, 'image 54', 1, 'BACKGROUND', null, 1, 4, 'PRODUCT_SKU'), 
(155, 'image ...', 'house-blend-coffee.jpg', 1, 'image 55', 1, 'BACKGROUND', null, 1, 5, 'PRODUCT_SKU'), 
(156, 'image ...', 'decaf-house-blend-coffee.jpg', 0, 'image 56', 1, 'BACKGROUND', null, 1, 6, 'PRODUCT_SKU'), 
(157, 'image ...', 'house-blend-coffee-24-2oz-portion-packs.jpg', 0, 'image 57', 1, 'BACKGROUND', null, 1, 7, 'PRODUCT_SKU'), 
(158, 'image ...', 'ethiopia-yirgacheffe-coffee.jpg', 0, 'image 58', 1, 'BACKGROUND', null, 1, 8, 'PRODUCT_SKU'),
(159, 'image ...', 'kenya-aa-coffee.jpg', 1, 'image 59', 1, 'BACKGROUND', null, 1, 9, 'PRODUCT_SKU'), 
(160, 'image ...', 'colombia-narino-coffee.jpg', 0, 'image 60', 1, 'BACKGROUND', null, 1, 10, 'PRODUCT_SKU'), 
(161, 'image ...', 'costa-rica-cascada-tarrazu-coffee.jpg', 0, 'image 61', 1, 'BACKGROUND', null, 1, 11, 'PRODUCT_SKU'), 
(162, 'image ...', 'costa-rica-la-minita-tarrazu-coffee.jpg', 0, 'image 62', 1, 'BACKGROUND', null, 1, 12, 'PRODUCT_SKU'),
(163, 'image ...', 'decaf-colombia-narino-coffee.jpg', 1, 'image 63', 1, 'BACKGROUND', null, 1, 13, 'PRODUCT_SKU'), 
(164, 'image ...', 'guatemala-antigua-coffee.jpg', 0, 'image 64', 1, 'BACKGROUND', null, 1, 14, 'PRODUCT_SKU'),
(165, 'image ...', 'mocha-java-coffee.jpg', 0, 'image 65', 1, 'BACKGROUND', null, 1, 15, 'PRODUCT_SKU'), 
(166, 'image ...', 'papua-new-guinea-sigri-coffee.jpg', 0, 'image 66', 1, 'BACKGROUND', null, 1, 16, 'PRODUCT_SKU'), 
(167, 'image ...', 'sumatra-mandheling-coffee.jpg', 0, 'image 67', 1, 'BACKGROUND', null, 1, 17, 'PRODUCT_SKU'),
(168, 'image ...', 'breakfast-blend-coffee.jpg', 1, 'image 68', 1, 'BACKGROUND', null, 1, 18, 'PRODUCT_SKU'), 
(169, 'image ...', 'colombia-narino-coffee-24-2oz-portion-packs.jpg', 0, 'image 69', 1, 'BACKGROUND', null, 1, 19, 'PRODUCT_SKU'),
(170, 'image ...', 'costa-rica-coffee-24-2oz-portion-packs.jpg', 0, 'image 70', 1, 'BACKGROUND', null, 1, 20, 'PRODUCT_SKU'), 
(171, 'image ...', 'holiday-blend-coffee.jpg', 0, 'image 71', 1, 'BACKGROUND', null, 1, 21, 'PRODUCT_SKU'), 
(172, 'image ...', 'bali-blue-moon-coffee.jpg', 0, 'image 72', 1, 'BACKGROUND', null, 1, 22, 'PRODUCT_SKU'),
(173, 'image ...', 'university-blend-coffee.jpg', 1, 'image 73', 1, 'BACKGROUND', null, 1, 23, 'PRODUCT_SKU'), 
(174, 'image ...', 'harvest-blend-coffee.jpg', 0, 'image 74', 1, 'BACKGROUND', null, 1, 24, 'PRODUCT_SKU'),
(175, 'image ...', 'decaf-espesso-roast-coffee.jpg', 0, 'image 75', 1, 'BACKGROUND', null, 1, 25, 'PRODUCT_SKU'), 
(176, 'image ...', 'decaf-french-roast-coffee.jpg', 0, 'image 76', 1, 'BACKGROUND', null, 1, 26, 'PRODUCT_SKU'), 
(177, 'image ...', 'decaf-viennese-coffee.jpg', 0, 'image 77', 1, 'BACKGROUND', null, 1, 27, 'PRODUCT_SKU'),
(178, 'image ...', 'french-roast-coffee.jpg', 1, 'image 78', 1, 'BACKGROUND', null, 1, 28, 'PRODUCT_SKU'), 
(179, 'image ...', 'italian-roast-coffee.jpg', 0, 'image 79', 1, 'BACKGROUND', null, 1, 29, 'PRODUCT_SKU'),
(180, 'image ...', 'tanzania-peaberry-coffee.jpg', 0, 'image 80', 1, 'BACKGROUND', null, 1, 30, 'PRODUCT_SKU'), 
(181, 'image ...', 'colombia-narino-dark-coffee.jpg', 0, 'image 81', 1, 'BACKGROUND', null, 1, 31, 'PRODUCT_SKU'), 
(182, 'image ...', 'espresso-roast-coffee.jpg', 0, 'image 82', 1, 'BACKGROUND', null, 1, 32, 'PRODUCT_SKU'),
(183, 'image ...', 'master-davey-set-blue-tiger-tea-book.jpg', 1, 'image 83', 1, 'BACKGROUND', null, 1, 33, 'PRODUCT_SKU'), 
(184, 'image ...', 'winter-dream-tea.jpg', 0, 'image 84', 1, 'BACKGROUND', null, 1, 34, 'PRODUCT_SKU'),
(185, 'image ...', 'peppermint-stick-tea.jpg', 0, 'image 85', 1, 'BACKGROUND', null, 1, 35, 'PRODUCT_SKU'), 
(186, 'image ...', 'los-angeles-sunshine-blend-tea.jpg', 0, 'image 86', 1, 'BACKGROUND', null, 1, 36, 'PRODUCT_SKU'), 
(187, 'image ...', 'georgia-peach-ginger-white-tea.jpg', 0, 'image 87', 1, 'BACKGROUND', null, 1, 37, 'PRODUCT_SKU'),
(188, 'image ...', 'jasmine-dragon-phoenix-pearl-tea.jpg', 1, 'image 88', 1, 'BACKGROUND', null, 1, 38, 'PRODUCT_SKU'), 
(189, 'image ...', 'genmaicha-green-tea.jpg', 0, 'image 89', 1, 'BACKGROUND', null, 1, 39, 'PRODUCT_SKU'),
(190, 'image ...', 'lung-ching-dragonwell-tea.jpg', 0, 'image 90', 1, 'BACKGROUND', null, 1, 40, 'PRODUCT_SKU'), 
(191, 'image ...', 'decaf-green-tea.jpg', 0, 'image 91', 1, 'BACKGROUND', null, 1, 41, 'PRODUCT_SKU'), 
(192, 'image ...', 'swedish-berries-tea.jpg', 0, 'image 92', 1, 'BACKGROUND', null, 1, 42, 'PRODUCT_SKU'),
(193, 'image ...', 'chai-rooibos-tea.jpg', 1, 'image 93', 1, 'BACKGROUND', null, 1, 43, 'PRODUCT_SKU'), 
(194, 'image ...', 'african-sunrise-tea.jpg', 0, 'image 94', 1, 'BACKGROUND', null, 1, 44, 'PRODUCT_SKU'),
(195, 'image ...', 'french-deluxe-vanilla-powder.jpg', 0, 'image 95', 1, 'BACKGROUND', null, 1, 45, 'PRODUCT_SKU'), 
(196, 'image ...', 'special-dutch-chocolate-powder-no-sugar.jpg', 0, 'image 96', 1, 'BACKGROUND', null, 1, 46, 'PRODUCT_SKU'), 
(197, 'image ...', 'americana-tumbler.jpg', 0, 'image 97', 1, 'BACKGROUND', null, 1, 47, 'PRODUCT_SKU'),
(198, 'image ...', 'bergamo-bottle.jpg', 1, 'image 98', 1, 'BACKGROUND', null, 1, 48, 'PRODUCT_SKU'), 
(199, 'image ...', 'the-jaidun-tumbler.jpg', 0, 'image 99', 1, 'BACKGROUND', null, 1, 49, 'PRODUCT_SKU'),
(200, 'image ...', 'bamboo-ceramic-tea-set.jpg', 0, 'image 100', 1, 'BACKGROUND', null, 1, 50, 'PRODUCT_SKU');

-- WAREHOUSE

INSERT INTO teco_warehouse  
(id, code, name, description, version)
 VALUES 
(10, 'WAREHOUSE_GLOBAL', 'WAREHOUSE Global', 'WAREHOUSE Global', 1), 
(20, 'WAREHOUSE_US', 'WAREHOUSE USA', 'WAREHOUSE USA', 1),
(30, 'WAREHOUSE_FR', 'WAREHOUSE France', 'WAREHOUSE France', 1);

INSERT INTO teco_market_area_warehouse_rel  
(market_area_id, warehouse_id)
 VALUES 
(1, 10),
(101, 10),
(102, 10),
(201, 10),
(202, 10),
(210, 10),
(211, 10),
(301, 10),
(302, 10),
(303, 10);

INSERT INTO teco_market_area_warehouse_rel  
(market_area_id, warehouse_id)
 VALUES 
(201, 20),
(101, 30);

-- DELIVERY METHODS

INSERT INTO teco_delivery_method  
(id, code, name, description, version)
 VALUES 
(10, 'UPS_FR', 'UPS', 'UPS', 1), 
(20, 'CHONOPOST', 'Chronopost', 'Chronopost', 1), 
(30, 'COLISSIMO', 'Collisimo', 'Collisimo', 1),
(40, 'UPS_US', 'UPS', 'UPS', 1), 
(50, 'FEDEX_US', 'Fedex', 'Fedex', 1), 
(60, 'US_POSTAL', 'US Postal', 'US Postal', 1),
(70, 'GLOBAL_DELIVERY', 'Worlwide Delivery', 'Worlwide Delivery', 1);

INSERT INTO teco_warehouse_delivery_method_rel  
(warehouse_id, delivery_method_id)
 VALUES 
(10, 70),
(20, 40),
(20, 50),
(20, 60),
(30, 10),
(30, 20),
(30, 30);

INSERT INTO teco_delivery_method_price  
(id, price, currency_id, delivery_method_id)
VALUES 
(10, 14.35, 45, 10),
(20, 15.35, 45, 20),
(30, 16.35, 45, 30),
(40, 14.35, 150, 40),
(50, 15.35, 150, 50),
(60, 16.35, 150, 60);

-- TAX
INSERT INTO teco_market_area_tax_rel  
(market_area_id, tax_id)
 VALUES 
(101, 10),
(101, 20),
(101, 30),
(101, 40);

-- RULE | PROMO

-- ORDER
INSERT INTO teco_order_number  
(id, last_order_number, version)
VALUES 
(1, 1000, 1);
