/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core;

import java.util.Date;

public final class Constants {

		public static final String NAMESPACE_URI = "http://ws.qalingo.com/schemas/ws/eco";

		// GLOBAL
		public static final String APP_NAME = "appName";
		public static final String APP_NAME_HTML = "appNameHtml";
		public static final String APP_NAME_BO_BUSINESS_CODE = "BO_BUSINESS";
		public static final String APP_NAME_BO_REPORTING_CODE = "BO_REPORTING";
		public static final String APP_NAME_BO_TECHNICAL_CODE = "BO_TECHNICAL";
		public static final String APP_NAME_FO_MCOMMERCE_CODE = "FO_MCOMMERCE";
		
		public static final String UTF8 = "UTF-8";
		public static final String ANSI = "ISO-8859-1";

		public static final String NOT_AVAILABLE = "NA";
		
	    public static final String CSV_EXTENSION = ".csv";
	    public static final String TXT_EXTENSION = ".txt";
	    public static final String XLS_EXTENSION = ".xls";
	    public static final String XML_EXTENSION = ".xml";
	    public static final String ZIP_EXTENSION = ".zip";
	    
	    public static final String NULL = "null";
	    public static final String THREE_DOT = "...";
	    public static final String EMPTY = "";
		public static final String UNDERSCORE = "_";
		public static final String DASH = "-";
		public static final String DOT = ".";
		public static final String COLON = ":";
		public static final String PIPE = "|";
		public static final String SLASH = "/";
		public static final String EQUALS = "=";
		public static final String SPACE = " ";
		public static final String POUND = "#";
		public static final String STAR = "*";
		public static final String EXLCLAMATION_MARK = "!";
		public static final String QUESTION_MARK = "?";
		public static final String CR = "\r\n";
		public static final String COMMA = ",";
		public static final String SEMI_COLON = ";";
		public static final String LEFT_PARENTHESIS = "(";
		public static final String RIGHT_PARENTHESIS = ")";
		public static final String LEFT_SQUARE_BRACKET = "[";
		public static final String RIGHT_SQUARE_BRACKET = "]";
		
		public static final String SPACE_COLON_SPACE = " : ";
		public static final String SPACE_PIPE_SPACE = " | ";
		public static final String SPACE_DASH_SPACE = " - ";
		public static final String COMMA_SPACE = ", ";
		public static final String SPACE_STAR_SPACE = " * ";    

	    public static final String REFERER = "Referer";
	    public static final String HOST = "Host";
		public static final String HTTP = "Http";
		public static final String HTTP_COLON_SLASH_SLASH = "http://";

	    public static final String LOCALHOST_ROOT_URL = "http://localhost:8080";
	    
	    public static final String KEY = "key";
	    public static final String LOCALE = "locale";
	    public static final String COUNTRY_CODE = "countryCode";
	    public static final String LANGUAGE_CODE = "languageCode";
	    public static final String LOCALE_FR = "fr";
	    public static final String LOCALE_EN = "en";
	    public static final String DEFAULT_LOCALE_CODE = LOCALE_EN;
		
		public static final String PROPERTY_DEFAULT_STAGE_KEYNAME = "10";
	    public static final Integer MILLISECONDS_IN_A_DAY = new Integer("86400000");
	    public static final Integer MILLISECONDS_IN_A_HOUR = new Integer("3600000");
	    public static final java.util.Date TODAY = new Date();
	    
		public static final String DYNAMIC_LOCALE = "dynamicLocale";
		
		public static final String I18N_KEY = "i18nAppKey";
		public static final String CATEGORY_LIST_KEY = "categoryListAppKey";
		public static final String CATEGORY_DETAILS_KEY = "categoryDetailsAppKey";
		public static final String PRODUCT_LIST_KEY = "productListAppKey";
		public static final String PRODUCT_DETAILS_KEY = "productDetailsAppKey";
		public static final String SHOPINGCART_DETAILS_KEY = "shoppingCartDetailsAppKey";
		
	    public static final String HOTEIA_ROOT_URL = "http://www.hoteia.com";
	    
		public static final String USER_AGENT_IE_7 = "MSIE 7.0";
		public static final String USER_AGENT_IE_8 = "MSIE 8.0";
		public static final String USER_AGENT_FIREFOX_3 = "Firefox/3";
		
		public static final String GOOGLE_OAUTH_SIGNATURE_METHOD = "RSA-SHA1";
		
		// ECO SESSION
		public static final String ECO_SESSION_OBJECT = "ECO_SESSION_OBJECT";

		// REQUEST PARAMETER
		public static final String REQUEST_PARAMETER_MARKET_PLACE_CODE = "market-place-code";
		public static final String REQUEST_PARAMETER_MARKET_CODE = "market-code";
		public static final String REQUEST_PARAMETER_MARKET_AREA_CODE = "market-area-code";
		public static final String REQUEST_PARAMETER_LOCALE_CODE = "locale-code";
		public static final String REQUEST_PARAMETER_RETAILER_CODE = "retailer-code";
		public static final String REQUEST_PARAM_PRODUCT_SKU_CODE = "product-sku-code";
		public static final String REQUEST_PARAM_PRODUCT_MARKETING_CODE = "product-code";
		public static final String REQUEST_PARAM_CATEGORY_CODE = "category-code";
		public static final String REQUEST_PARAM_BRAND_CODE = "brand-code";

		public static final String REQUEST_PARAM_CUSTOMER_ADDRESS_ID = "customer-address-id";
		public static final String REQUEST_PARAM_CUSTOMER_ORDER_ID = "customer-order-id";
		public static final String REQUEST_PARAM_ENGINE_SETTING_ID = "engine-setting-id";
		public static final String REQUEST_PARAM_ENGINE_SETTING_VALUE_ID = "engine-setting-value-id";
		public static final String REQUEST_PARAM_USER_ID = "user-id";

		// SEARCH PAGINATION
		public static final String SEARCH_FACET_FIELD_LIST = "facetFieldList";

		// SPRING VIEW PAGINATION
		public static final String PAGE_PARAMETER = "page";
		public static final String PAGE_URL = "currentPageUrl";
		public static final String PAGE_PAGED_LIST_HOLDER = "pagedListHolder";
		public static final int DEFAULT_PAGE_SIZE= 10;
		
		// SECURITY
		public static final String SECRET_VALUE = "5U%oP9$";

		// PROPERTIE FILE NAMES
		public static final String TITLES_RESOURCE_BUNDLE = "qalingo-Titles";
		public static final String COUNTRIES_RESOURCE_BUNDLE = "qalingo-Countries";
		
		// VELOCITY LAYOUT ATTRIBUTES
		public static final String VELOCITY_LAYOUT_ATTRIBUTE_HEAD_CONTENT = "head_content";
		public static final String VELOCITY_LAYOUT_ATTRIBUTE_FOOTER_SCRIPT_CONTENT = "footer_script_content";

		// OTHERS
		public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";


}
