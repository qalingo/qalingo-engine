/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

public class CatalogBreadCumViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5107897401068601858L;

    protected String code;
    protected String name;

    protected boolean isRoot;

    protected CatalogBreadCumViewBean defaultParentCategory;

    protected String detailsUrl;
    
    protected String productAxeUrl;
    protected String productLineUrl;
    
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


    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }


    public CatalogBreadCumViewBean getDefaultParentCategory() {
        return defaultParentCategory;
    }

    public void setDefaultParentCategory(CatalogBreadCumViewBean defaultParentCategory) {
        this.defaultParentCategory = defaultParentCategory;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
    
    public String getProductAxeUrl(){
    	return this.productAxeUrl;
    }
    
    public void setProductAxeUrl( String productAxeUrl){
    	this.productAxeUrl = productAxeUrl;
    }
    
    public String getProductLineUrl(){
    	return this.productLineUrl;
    }
    
    public void setProductLineUrl( String productLineUrl){
    	this.productLineUrl = productLineUrl;
    }

}