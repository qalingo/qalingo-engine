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

public class RuleViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3496510221185684784L;

	private int version;
    private String code;
	private String name;
	private String description;
	private String salience;

	// private Set<RuleRepositoryAttribute> ruleRepositoryAttributes = new
	// HashSet<RuleRepositoryAttribute>();

	private String detailsUrl;
	private String editUrl;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public String getCode() {
        return code;
    }
	
	public void setCode(String code) {
        this.code = code;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSalience() {
		return salience;
	}

	public void setSalience(String salience) {
		this.salience = salience;
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
}
