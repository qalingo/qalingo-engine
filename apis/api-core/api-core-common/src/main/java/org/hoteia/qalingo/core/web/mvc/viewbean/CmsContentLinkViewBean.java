package org.hoteia.qalingo.core.web.mvc.viewbean;

public class CmsContentLinkViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387541191150175454L;

	// ENTITY
	private String name;
	private String alt;
	private String type;
	private String params;
	private boolean external;
	private String fullURlPath;

	// VIEW
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlt() {
		return alt;
	}
	
	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public String getFullURlPath() {
		return fullURlPath;
	}

	public void setFullURlPath(String fullURlPath) {
		this.fullURlPath = fullURlPath;
	}
    
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}