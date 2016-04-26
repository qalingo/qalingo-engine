package org.hoteia.qalingo.core.web.mvc.form;

import java.util.Date;

public class CmsMenuForm {
	
	protected String id;
    protected int version;
    
    protected String code;
    protected String name;
    protected Long marketAreaId;
    protected boolean external;
    protected String position;
    protected String type;
    protected String params;
    protected String fullURlPath;
	protected int ordering;
    protected boolean active;
	
	protected String rootMenuId;
	
    protected Date dateCreate;
	protected Date dateUpdate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Long getMarketAreaId() {
		return marketAreaId;
	}

	public void setMarketAreaId(Long marketAreaId) {
		this.marketAreaId = marketAreaId;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
	
	public String getFullURlPath() {
		return fullURlPath;
	}
	
	public void setFullURlPath(String fullURlPath) {
		this.fullURlPath = fullURlPath;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRootMenuId() {
        return rootMenuId;
    }
    
    public void setRootMenuId(String rootMenuId) {
        this.rootMenuId = rootMenuId;
    }

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

}