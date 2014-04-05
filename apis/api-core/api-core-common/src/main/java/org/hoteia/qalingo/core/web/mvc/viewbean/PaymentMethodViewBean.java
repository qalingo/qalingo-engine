package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodViewBean extends AbstractViewBean implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -3660471213780739333L;

    private String name;
    private String description;
    private String code;
    
    private List<PaymentMethodOptionViewBean> paymentMethodOptions = new ArrayList<PaymentMethodOptionViewBean>();

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

    public List<PaymentMethodOptionViewBean> getPaymentMethodOptions() {
        return paymentMethodOptions;
    }
    
    public void setPaymentMethodOptions(List<PaymentMethodOptionViewBean> paymentMethodOptions) {
        this.paymentMethodOptions = paymentMethodOptions;
    }
    
}