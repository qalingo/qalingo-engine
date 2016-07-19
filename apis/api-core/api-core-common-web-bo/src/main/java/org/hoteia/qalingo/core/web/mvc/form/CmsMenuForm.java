package org.hoteia.qalingo.core.web.mvc.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected String fullUrlPath;
	protected int ordering;
    protected boolean active;
	
	protected String rootMenuId;
	
    protected Map<AttributeContextBean, String> globalAttributes = new HashMap<AttributeContextBean, String>();
    protected Map<AttributeContextBean, String> marketAreaAttributes = new HashMap<AttributeContextBean, String>();
	
    protected List<MultipleTextBean> i18nNames = new ArrayList<MultipleTextBean>();
    
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
	
	public String getFullUrlPath() {
		return fullUrlPath;
	}
	
	public void setFullUrlPath(String fullUrlPath) {
		this.fullUrlPath = fullUrlPath;
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
    
    public Map<AttributeContextBean, String> getGlobalAttributes() {
        return globalAttributes;
    }
    
    public void setGlobalAttributes(Map<AttributeContextBean, String> globalAttributes) {
        this.globalAttributes = globalAttributes;
    }
    
    public Map<AttributeContextBean, String> getMarketAreaAttributes() {
        return marketAreaAttributes;
    }
    
    public void setMarketAreaAttributes(Map<AttributeContextBean, String> marketAreaAttributes) {
        this.marketAreaAttributes = marketAreaAttributes;
    }
    
    public List<MultipleTextBean> getI18nNames() {
        return i18nNames;
    }
    
    public String getI18nName(String localizationCode) {
        for (MultipleTextBean multipleTextBean : i18nNames) {
            if(multipleTextBean.getCode() != null 
                    && multipleTextBean.getCode().equals(localizationCode)){
                return multipleTextBean.getText();
            }
        }
        return null;
    }
    
    public void setI18nNames(List<MultipleTextBean> i18nNames) {
        this.i18nNames = i18nNames;
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