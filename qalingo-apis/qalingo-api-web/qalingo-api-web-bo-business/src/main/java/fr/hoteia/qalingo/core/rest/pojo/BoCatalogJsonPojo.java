/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.pojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * <p>
 * <a href="BoCatalogJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BoCatalogJsonPojo extends CatalogJsonPojo {
	
	private String detailsUrl;

	public BoCatalogJsonPojo() {
	}
	
	public String getDetailsUrl() {
		return detailsUrl;
	}
	
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
}