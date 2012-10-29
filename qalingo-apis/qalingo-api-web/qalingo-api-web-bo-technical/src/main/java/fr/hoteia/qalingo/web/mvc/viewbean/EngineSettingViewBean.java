/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EngineSettingViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1826858352678981858L;
	
	protected String name;
	protected String description;
	protected String code;
	
	protected String engineSettingDetailsUrl;
	
	protected List<EngineSettingValueViewBean> engineSettingValues = new ArrayList<EngineSettingValueViewBean>();

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

	public String getEngineSettingDetailsUrl() {
		return engineSettingDetailsUrl;
	}

	public void setEngineSettingDetailsUrl(String engineSettingDetailsUrl) {
		this.engineSettingDetailsUrl = engineSettingDetailsUrl;
	}

	public List<EngineSettingValueViewBean> getEngineSettingValues() {
		return engineSettingValues;
	}

	public void setEngineSettingValues(List<EngineSettingValueViewBean> engineSettingValues) {
		this.engineSettingValues = engineSettingValues;
	}

	
}
