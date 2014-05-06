/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import org.apache.commons.lang.BooleanUtils;

public abstract class AbstractAttribute extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2397084953607874647L;
	
	// TODO : ENUM OR NOT ?
    public final static String MARKET_AREA_ATTRIBUTE_EMAIL_FROM_ADDRESS = "MARKET_AREA_EMAIL_FROM_ADDRESS";
    public final static String MARKET_AREA_ATTRIBUTE_EMAIL_FROM_NAME = "MARKET_AREA_EMAIL_FROM_NAME";
    public final static String MARKET_AREA_ATTRIBUTE_EMAIL_TO_CONTACT = "MARKET_AREA_EMAIL_CONTACT";
    public final static String MARKET_AREA_ATTRIBUTE_DOMAIN_NAME = "MARKET_AREA_DOMAIN_NAME";
    public final static String MARKET_AREA_ATTRIBUTE_SHARE_OPTIONS = "MARKET_AREA_SHARE_OPTIONS";
    public final static String MARKET_AREA_ATTRIBUTE_SAVE_PAYMENT_INFORMATION = "MARKET_AREA_SAVE_PAYMENT_INFORMATION";
    public final static String MARKET_AREA_ATTRIBUTE_ORDER_CONFIRMATION_TEMPLATE = "MARKET_AREA_ORDER_CONFIRMATION_TEMPLATE";
    public final static String MARKET_AREA_ATTRIBUTE_SHIPPING_CONFIRMATION_TEMPLATE = "MARKET_AREA_SHIPPING_CONFIRMATION_TEMPLATE";
    public final static String MARKET_AREA_ATTRIBUTE_INVOICE_TEMPLATE = "MARKET_AREA_INVOICE_TEMPLATE";

	public final static String CUSTOMER_ATTRIBUTE_SCREENAME = "CUSTOMER_SCREENNAME";

	public final static String CATALOG_CATEGORY_ATTRIBUTE_I18N_NAME = "CATALOG_CATEGORY_I18N_NAME";
	
    public final static String PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME        = "PRODUCT_MARKETING_I18N_NAME";
    public final static String PRODUCT_MARKETING_ATTRIBUTE_I18N_DESCRIPTION = "PRODUCT_MARKETING_I18N_DESCRIPTION";
    public final static String PRODUCT_MARKETING_ATTRIBUTE_FEATURED         = "PRODUCT_MARKETING_FEATURED";

    public final static String PRODUCT_SKU_ATTRIBUTE_I18N_NAME          = "PRODUCT_SKU_I18N_NAME";
    public final static String PRODUCT_SKU_ATTRIBUTE_I18N_DESCRIPTION   = "PRODUCT_SKU_I18N_DESCRIPTION";
    public final static String PRODUCT_SKU_ATTRIBUTE_WIDTH              = "PRODUCT_SKU_WIDTH";
    public final static String PRODUCT_SKU_ATTRIBUTE_HEIGHT             = "PRODUCT_SKU_HEIGHT";
    public final static String PRODUCT_SKU_ATTRIBUTE_LENGTH             = "PRODUCT_SKU_LENGTH";
    public final static String PRODUCT_SKU_ATTRIBUTE_WEIGHT             = "PRODUCT_SKU_WEIGHT";
    public final static String PRODUCT_SKU_ATTRIBUTE_IS_SALABLE         = "PRODUCT_SKU_IS_SALABLE";

	public final static String RETAILER_ATTRIBUTE_I18N_NAME        = "RETAILER_I18N_NAME";
    public final static String RETAILER_ATTRIBUTE_I18N_DESCRIPTION = "RETAILER_I18N_DESCRIPTION";

    public final static String STORE_ATTRIBUTE_I18N_NAME        = "STORE_I18N_NAME";
    public final static String STORE_ATTRIBUTE_I18N_DESCRIPTION = "STORE_I18N_DESCRIPTION";
    public final static String STORE_ATTRIBUTE_I18N_CITY_NAME   = "STORE_I18N_CITY_NAME";

    public final static String PAYMENT_GATEWAY_ATTRIBUTE_CLIENT_TOKEN = "PAYMENT_GATEWAY_CLIENT_TOKEN";

    public abstract AttributeDefinition getAttributeDefinition();

    public abstract String getShortStringValue();

    public abstract void setShortStringValue(String value);

    public abstract String getLongStringValue();
    
    public abstract void setLongStringValue(String longStringValue);
    
    public abstract Integer getIntegerValue();

    public abstract void setIntegerValue(Integer value);

    public abstract Double getDoubleValue();

    public abstract void setDoubleValue(Double value);

    public abstract Float getFloatValue();

    public abstract void setFloatValue(Float value);

    public abstract byte[] getBlobValue();

    public abstract void setBlobValue(byte[] value);

    public abstract Boolean getBooleanValue();

    public abstract void setBooleanValue(Boolean value);

    public abstract String getLocalizationCode();

    public abstract void setLocalizationCode(String localizationCode);
    
	public Object getValue() {
		AttributeDefinition attributeDefinition = getAttributeDefinition();
		if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_SHORT_STRING) {
			return (Object) getShortStringValue();
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_LONG_STRING) {
            return (Object) getLongStringValue();
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE) {
			return (Object) getDoubleValue();
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_FLOAT) {
			return (Object) getFloatValue();
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_INTEGER) {
			return (Object) getIntegerValue();
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BLOB) {
			return (Object) getBlobValue();
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN) {
			return (Object) getBooleanValue();
		}
		return null;
	}
	
	public String getValueAsString() {
		if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_SHORT_STRING){
			if(getShortStringValue() != null){
				return getShortStringValue();
			}
		} else if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_LONG_STRING){
            if(getLongStringValue() != null){
                return getLongStringValue();
            }
        } else if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE){
			if(getDoubleValue() != null){
				return getDoubleValue().toString();
			}
		} else if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_FLOAT){
			if(getFloatValue() != null){
				return getFloatValue().toString();
			}
		} else if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_INTEGER){
			if(getIntegerValue() != null){
				return getIntegerValue().toString();
			}
		} else if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BLOB){
			if(getBlobValue() != null){
				return getBlobValue().toString();
			}
		} else if(getAttributeDefinition().getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN){
			if(getBooleanValue() != null){
				return getBooleanValue().toString();
			}
		}
		return null;
	}
	
    public void setValue(String value) {
        AttributeDefinition attributeDefinition = getAttributeDefinition();
        if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_SHORT_STRING) {
            setShortStringValue(value);
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_LONG_STRING) {
            setLongStringValue(value);
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE) {
            setDoubleValue(new Double(value));
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_FLOAT) {
            setFloatValue(new Float(value));
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_INTEGER) {
            setIntegerValue(new Integer(value));
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BLOB) {
            setBlobValue(value.getBytes());
        } else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN) {
            setBooleanValue(BooleanUtils.toBoolean(value));
        }
    }
	
}