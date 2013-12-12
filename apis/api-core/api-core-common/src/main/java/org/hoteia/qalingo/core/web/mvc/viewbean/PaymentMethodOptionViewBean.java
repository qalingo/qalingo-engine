package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class PaymentMethodOptionViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8244556884926086914L;

    private String name;
    private String description;
    private String code;
    
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

}