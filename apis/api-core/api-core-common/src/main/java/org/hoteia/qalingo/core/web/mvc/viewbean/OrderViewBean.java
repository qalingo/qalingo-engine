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
import java.util.ArrayList;
import java.util.List;

public class OrderViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7481342045685333815L;
	
    private Long id;
    private int version;
    private String status;
    private String confirmationMessage;
    private String orderNum;

    private String expectedDeliveryDate;
    
    private String orderItemsTotalWithCurrencySign;
    private String orderShippingTotalWithCurrencySign;
    private String orderTaxesTotalWithCurrencySign;
    private String orderTotalWithCurrencySign;

    private String paymentGateway;
    private String paymentMethod;

    private String cardHolder;
    private String cardNumber;
    private String cardCrypto;
    private String cardExpirationDate;
    private String cardExpirationMonth;
    private String cardExpirationYear;

    private Long customerId;
    private Long billingAddressId;
    private Long shippingAddressId;

    private List<OrderItemViewBean> orderItems = new ArrayList<OrderItemViewBean>();
    private List<OrderShippingViewBean> orderShippings = new ArrayList<OrderShippingViewBean>();
    private List<OrderTaxViewBean> orderTaxes = new ArrayList<OrderTaxViewBean>();

    private String detailsUrl;

	public OrderViewBean() {
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getConfirmationMessage() {
        return confirmationMessage;
    }
    
    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }
    
	public String getOrderNum() {
		return orderNum;
	}
	
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }
	
	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

	public String getOrderItemsTotalWithCurrencySign() {
        return orderItemsTotalWithCurrencySign;
    }

    public void setOrderItemsTotalWithCurrencySign(String orderItemsTotalWithCurrencySign) {
        this.orderItemsTotalWithCurrencySign = orderItemsTotalWithCurrencySign;
    }

    public String getOrderShippingTotalWithCurrencySign() {
        return orderShippingTotalWithCurrencySign;
    }

    public void setOrderShippingTotalWithCurrencySign(String orderShippingTotalWithCurrencySign) {
        this.orderShippingTotalWithCurrencySign = orderShippingTotalWithCurrencySign;
    }

    public String getOrderTaxesTotalWithCurrencySign() {
        return orderTaxesTotalWithCurrencySign;
    }

    public void setOrderTaxesTotalWithCurrencySign(String orderFeesTotalWithCurrencySign) {
        this.orderTaxesTotalWithCurrencySign = orderFeesTotalWithCurrencySign;
    }

    public String getOrderTotalWithCurrencySign() {
        return orderTotalWithCurrencySign;
    }

    public void setOrderTotalWithCurrencySign(String orderTotalWithCurrencySign) {
        this.orderTotalWithCurrencySign = orderTotalWithCurrencySign;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCrypto() {
		return cardCrypto;
	}

	public void setCardCrypto(String cardCrypto) {
		this.cardCrypto = cardCrypto;
	}

	public String getCardExpirationDate() {
		return cardExpirationDate;
	}

	public void setCardExpirationDate(String cardExpirationDate) {
		this.cardExpirationDate = cardExpirationDate;
	}

	public String getCardExpirationMonth() {
		return cardExpirationMonth;
	}

	public void setCardExpirationMonth(String cardExpirationMonth) {
		this.cardExpirationMonth = cardExpirationMonth;
	}

	public String getCardExpirationYear() {
		return cardExpirationYear;
	}

	public void setCardExpirationYear(String cardExpirationYear) {
		this.cardExpirationYear = cardExpirationYear;
	}

	public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(Long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public List<OrderItemViewBean> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemViewBean> orderItems) {
		this.orderItems = orderItems;
	}
	
    public int getTotalCartItems() {
        if (orderItems != null) {
            return orderItems.size();
        }
        return 0;
    }
    
    public List<OrderShippingViewBean> getOrderShippings() {
        return orderShippings;
    }

    public void setOrderShippings(List<OrderShippingViewBean> orderShippings) {
        this.orderShippings = orderShippings;
    }

    public int getTotalOrderShippings() {
        if (orderShippings != null) {
            return orderShippings.size();
        }
        return 0;
    }
    
    public List<OrderTaxViewBean> getOrderTaxes() {
        return orderTaxes;
    }
    
    public void setOrderTaxes(List<OrderTaxViewBean> orderTaxes) {
        this.orderTaxes = orderTaxes;
    }
    
    public int getTotalOrderTaxes() {
        if (orderTaxes != null) {
            return orderTaxes.size();
        }
        return 0;
    }
    
    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
    
}