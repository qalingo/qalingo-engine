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
    
    private Map<String, String> globaAttributes = new HashMap<String, String>();
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
    
    public Map<String, String> getGlobaAttributes() {
        return globaAttributes;
    }

    public void setGlobaAttributes(Map<String, String> globaAttributes) {
        this.globaAttributes = globaAttributes;
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