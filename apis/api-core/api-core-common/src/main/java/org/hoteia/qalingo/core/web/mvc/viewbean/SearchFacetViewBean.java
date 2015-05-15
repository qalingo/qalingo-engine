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

import java.util.ArrayList;
import java.util.List;

public class SearchFacetViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8803558268157558971L;
	
    private String code;
	private String name;
    private String type = "CHECKBOX";
    
	private List<SearchFacetValueBean> values = new ArrayList<SearchFacetValueBean>();
	
	public SearchFacetViewBean() {
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
	
	public String getType() {
        return type;
    }
	
	public void setType(String type) {
        this.type = type;
    }

	public List<SearchFacetValueBean> getValues() {
		return values;
	}
	
	public void addValue(SearchFacetValueBean searchFacetValueBean){
	    if(!values.contains(searchFacetValueBean)){
	        values.add(searchFacetValueBean);
	    }
	}
	
	public void setValues(List<SearchFacetValueBean> values) {
		this.values = values;
	}
}
