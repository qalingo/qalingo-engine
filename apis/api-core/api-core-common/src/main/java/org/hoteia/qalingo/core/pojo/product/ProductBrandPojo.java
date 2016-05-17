/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.product;

import java.util.Date;

public class ProductBrandPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String code;
    
    private boolean enabled;
    private boolean enabledB2B;
    private boolean enabledB2C;
    
    private String editUrl;
    private String detailsUrl;

    private Date dateCreate;
    private Date dateUpdate;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabledB2B() {
		return enabledB2B;
	}

	public void setEnabledB2B(boolean enabledB2B) {
		this.enabledB2B = enabledB2B;
	}

	public boolean isEnabledB2C() {
		return enabledB2C;
	}

	public void setEnabledB2C(boolean enabledB2C) {
		this.enabledB2C = enabledB2C;
	}

	public String getEditUrl() {
		return editUrl;
	}
    
    public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
    
    public String getDetailsUrl() {
		return detailsUrl;
	}
    
    public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
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