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
import java.util.ArrayList;
import java.util.List;

public class EngineSettingViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1826858352678981858L;
	
    protected String id;
	protected String name;
	protected String description;
	protected String code;
	protected String defaultValue;
	
	protected List<EngineSettingValueViewBean> engineSettingValues = new ArrayList<EngineSettingValueViewBean>();

    protected String detailsUrl;
    protected String editUrl;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<EngineSettingValueViewBean> getEngineSettingValues() {
        return engineSettingValues;
    }

    public void setEngineSettingValues(List<EngineSettingValueViewBean> engineSettingValues) {
        this.engineSettingValues = engineSettingValues;
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