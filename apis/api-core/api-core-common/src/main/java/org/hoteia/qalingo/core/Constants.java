/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core;

import java.util.Date;

public final class Constants {

	public static final String NAMESPACE_URI = "http://ws.qalingo.com/schemas/ws/eco";

    public static final String PREFIX_REST_SERVICE  = "/rest";
    public static final String PREFIX_WS_SERVICE    = "/ws";

	// GLOBAL
	
    public static final String QALINGO = "Qalingo";
	public static final String APP_NAME = "appName";
	public static final String APP_NAME_HTML = "appNameHtml";
	public static final String APP_NAME_BO_BUSINESS_CODE = "BO_BUSINESS";
	public static final String APP_NAME_BO_REPORTING_CODE = "BO_REPORTING";
	public static final String APP_NAME_BO_TECHNICAL_CODE = "BO_TECHNICAL";
	public static final String APP_NAME_FO_MCOMMERCE_CODE = "FO_MCOMMERCE";

	public static final String UTF8 = "UTF-8";
	public static final String ANSI = "ISO-8859-1";

	public static final int PAGE_SIZE = 20;

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

    public static final String REFERER          = "Referer";
    public static final String X_FORWARDED_FOR  = "X-Forwarded-For";

	public static final String HOST = "Host";
	public static final String HTTP = "Http";
	public static final String HTTP_COLON_SLASH_SLASH = "http://";
	public static final String SPRING_URL_PATH = "";

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
	public static final long ONE_HOUR = 3600000L;
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

    // SESSION
    public static final String ENGINE_ECO_SESSION_OBJECT    = "ENGINE_ECO_SESSION_OBJECT";
    public static final String ENGINE_BO_SESSION_OBJECT     = "ENGINE_BO_SESSION_OBJECT";
    public static final String ENGINE_CLICKSTREAM           = "ENGINE_CLICKSTREAM";

	// SEARCH PAGINATION
	public static final String SEARCH_TEXT = "searchText";

	// SPRING VIEW PAGINATION
	public static final String PAGINATION_PAGE_PARAMETER = "page";
	public static final String PAGINATION_PAGE_URL = "currentPageUrl";
	public static final String PAGINATION_PAGE_PAGED_LIST_HOLDER = "pagedListHolder";
	public static final String PAGINATION_PAGE_SIZE = "pageSize";
	public static final int PAGINATION_DEFAULT_PAGE_SIZE = 9;
	public static final String PAGINATION_SORT_BY = "sortBy";
	public static final String PAGINATION_ORDER = "order";
	public static final String PAGE_ORDER_ASC = "asc";
	public static final String PAGE_ORDER_DESC = "desc";
	public static final String PAGE_VIEW_MODE = "mode";
	public static final String PRICE_RANGE_PARAMETER = "price";
	public static final String CATALOG_CATEGORIES_PARAMETER = "categoriesFilter";
	
	public static final String STORE_CITY_PARAMETER = "citiesFilter";
	public static final String STORE_COUNTRY_PARAMETER = "countriesFilter";

	// SECURITY
	public static final String SECRET_VALUE = "5U%oP9$";

	// PROPERTIE FILE NAMES
	public static final String TITLES_RESOURCE_BUNDLE = "qalingo-titles";
	public static final String TITLE_MESSAGE_PREFIX = "title.";
	public static final String COUNTRIES_RESOURCE_BUNDLE = "qalingo-countries";
	public static final String COUNTRY_MESSAGE_PREFIX = "country.";

	// VELOCITY LAYOUT ATTRIBUTES
	public static final String VELOCITY_LAYOUT_ATTRIBUTE_HEAD_META = "head_meta";
	public static final String VELOCITY_LAYOUT_ATTRIBUTE_HEAD_CONTENT = "head_content";
	public static final String VELOCITY_LAYOUT_ATTRIBUTE_FOOTER_SCRIPT_CONTENT = "footer_script_content";

//	// SPRING VIEW BEAN
//	public static final String COMMON_VIEW_BEAN = "common";
//	public static final String QUICK_SEARCH_VIEW_BEAN = "quickSearch";
//	public static final String MENUS_VIEW_BEAN = "menus";
//	public static final String MORE_PAGE_MENUS_VIEW_BEAN = "morePageMenus";
//	public static final String MARKET_PLACES_VIEW_BEAN = "marketPlaces";
//	public static final String MARKETS_VIEW_BEAN = "markets";
//	public static final String MARKET_AREAS_VIEW_BEAN = "marketAreas";
//	public static final String MARKET_LANGUAGES_VIEW_BEAN = "marketLanguages";
//	public static final String RETAILERS_VIEW_BEAN = "retailers";
//	public static final String LANGUAGE_VIEW_BEAN = "languages";
//	public static final String LEGAl_TERMS_VIEW_BEAN = "legalTerms";
//	public static final String FOOTER_MENUS_VIEW_BEAN = "footerMenus";
//	public static final String SECURITY_VIEW_BEAN = "security";
//
//	public static final String CATALOG_VIEW_BEAN = "catalog";
//	public static final String CATALOG_CATEGORY_VIEW_BEAN = "catalogCategory";
//	public static final String PRODUCT_MARKETING_VIEW_BEAN = "productMarketing";
//	public static final String PRODUCT_SKU_VIEW_BEAN = "productSku";
//
//	public static final String ASSET_VIEW_BEAN = "asset";
//
//	public static final String RULE_VIEW_BEAN = "rule";
//	public static final String ORDER_VIEW_BEAN = "order";
//	public static final String SHIPPING_VIEW_BEAN = "shipping";
//	public static final String CUSTOMER_VIEW_BEAN = "customer";
//	public static final String USER_VIEW_BEAN = "user";
//	public static final String RETAILER_VIEW_BEAN = "retailer";

	public static final int SHORT_DESCRIPTION_MAX_LENGTH = 50;
	public static final int SHARE_META_DESCRIPTION_MAX_LENGTH = 150;
	
//	// SPRING FORM
//	public static final String ASSET_FORM = "assetForm";
//
//	public static final String RULE_FORM = "ruleForm";
//	public static final String ORDER_FORM = "orderForm";
//	public static final String SHIPPING_FORM = "shippingForm";
//	public static final String CUSTOMER_FORM = "customerForm";
//	public static final String USER_FORM = "userForm";
//	public static final String RETAILER_FORM = "retailerForm";

	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String INFO_MESSAGE = "infoMessage";
	public static final String SUCCESS_MESSAGE = "successMessage";
	
	public static final int COOKIES_LENGTH = 2592000; // in seconds - 30 days
    public static final String COOKIE_ECO_ENGINE_SESSION_ID = "QualingoEcoEngineSessionGuid";
	public static final String COOKIE_RECENT_PRODUCT_COOKIE_NAME = "QualingoRecentProducts";
	
	// PRODUCT RATING
	public static final String PRODUCT_QUALITY_RATING_TYPE = "QUALITY_OF_SERVICE";
	public static final String PRODUCT_PRICE_RATING_TYPE = "RATIO_QUALITY_PRICE";
	public static final String PRODUCT_VALUE_RATING_TYPE = "PRICE_SCORE";
}