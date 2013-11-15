package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PaymentGatewayViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 1273815556772162377L;

    private String name;
    private String description;
    private String code;
    
    private Map<String, String> paymentGatewayAttributes = new HashMap<String, String>();

    private String dateCreate;
    private String dateUpdate;

    private String detailsUrl;
    private String editUrl;

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

    public Map<String, String> getPaymentGatewayAttributes() {
        return paymentGatewayAttributes;
    }
    
    public void setPaymentGatewayAttributes(Map<String, String> paymentGatewayAttributes) {
        this.paymentGatewayAttributes = paymentGatewayAttributes;
    }
    
    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
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