package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class RuleViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3496510221185684784L;
	private Long id;
	private int version;
    private String code;
	private String name;
	private String description;
	private String salience;

	// private Set<RuleRepositoryAttribute> ruleRepositoryAttributes = new
	// HashSet<RuleRepositoryAttribute>();

	private String detailsUrl;
	private String editUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
