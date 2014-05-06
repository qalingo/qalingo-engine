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
import java.util.HashMap;
import java.util.Map;

public class PaymentGatewayViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 1273815556772162377L;

    private String code;
    private String name;
    private String description;
    private boolean active;
    
    private Map<String, String> globalAttributes = new HashMap<String, String>();
    private Map<String, String> marketAreaAttributes = new HashMap<String, String>();
    private Map<String, String> options = new HashMap<String, String>();

    private String detailsUrl;
    private String editUrl;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Map<String, String> getGlobalAttributes() {
        return globalAttributes;
    }

    public void setGlobalAttributes(Map<String, String> globalAttributes) {
        this.globalAttributes = globalAttributes;
    }

    public Map<String, String> getMarketAreaAttributes() {
        return marketAreaAttributes;
    }

    public void setMarketAreaAttributes(Map<String, String> marketAreaAttributes) {
        this.marketAreaAttributes = marketAreaAttributes;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
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