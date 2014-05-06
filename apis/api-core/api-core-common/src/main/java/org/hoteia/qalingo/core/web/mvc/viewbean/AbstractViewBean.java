/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.Constants;

public abstract class AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5246629091678484667L;

    private String dateCreate;
    private String dateUpdate;

    public String getDateCreate() {
        if(StringUtils.isNotEmpty(dateCreate)){
            return dateCreate;
        }
        return Constants.NOT_AVAILABLE;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDateUpdate() {
        if(StringUtils.isNotEmpty(dateUpdate)){
            return dateUpdate;
        }
        return Constants.NOT_AVAILABLE;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    protected String handleString(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        return string;
    }
	
	/**
	 * 
	 */
	protected String removeHtml(String value){
		if(StringUtils.isNotEmpty(value)){
			value = value.replaceAll("<[^>]*>", "");
			value = value.replaceAll("  ", " ");
			value = value.trim();
		}
		return value;
	}
	
	/**
	 * 
	 */
	protected String encodeQuote(String value){
		if(StringUtils.isNotEmpty(value)){
			value = value.replace("\"", "&quot;");
		}
		return value;
	}
	
	
}
