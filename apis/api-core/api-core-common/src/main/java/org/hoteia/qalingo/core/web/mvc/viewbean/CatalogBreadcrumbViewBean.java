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

public class CatalogBreadcrumbViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5107897401068601858L;

    protected String code;
    protected String name;

    protected boolean isRoot;

    protected CatalogBreadcrumbViewBean defaultParentCategory;

    protected String detailsUrl;
    
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


    public CatalogBreadcrumbViewBean getDefaultParentCategory() {
        return defaultParentCategory;
    }

    public void setDefaultParentCategory(CatalogBreadcrumbViewBean defaultParentCategory) {
        this.defaultParentCategory = defaultParentCategory;
    }

    public String getDetailsUrl(){
    	return this.detailsUrl;
    }
    
    public void setDetailsUrl( String detailsUrl){
    	this.detailsUrl = detailsUrl;
    }
    
   

}