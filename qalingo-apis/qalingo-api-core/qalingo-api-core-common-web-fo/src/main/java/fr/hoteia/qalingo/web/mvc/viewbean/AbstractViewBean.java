/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5246629091678484667L;

	protected String handleString(String string){
		if(StringUtils.isEmpty(string)){
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
