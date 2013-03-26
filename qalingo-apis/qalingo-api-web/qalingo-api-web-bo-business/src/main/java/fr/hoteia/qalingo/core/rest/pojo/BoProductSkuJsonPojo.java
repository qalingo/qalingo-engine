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

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * <p>
 * <a href="ProductSkuJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BoProductSkuJsonPojo extends ProductSkuJsonPojo {
	
	private String detailsUrl;
	private String editUrl;

	public BoProductSkuJsonPojo() {
	}
	
	public String getDetailsUrl() {
		return detailsUrl;
	}
	
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	
	public String getEditUrl() {
		return editUrl;
	}
	
	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
}