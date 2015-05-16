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

public class SearchFacetValueBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 3100619595421675045L;
    
    private String code;
    private String type;
    private String label;
    private boolean selected = false;
    private long count;
	
	public SearchFacetValueBean(){
	}
	
    public SearchFacetValueBean(String code, String label, long count){
        this.code = code;
        this.label = label;
        this.count = count;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
	public String getLabel() {
        return label;
    }
	
	public void setLabel(String label) {
        this.label = label;
    }
	
	public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getCount() {
        return count;
    }
	
	public void setCount(long count) {
        this.count = count;
    }
	
	public String getCountLabel(){
	    return "(" + count + ")";
	}
	
}