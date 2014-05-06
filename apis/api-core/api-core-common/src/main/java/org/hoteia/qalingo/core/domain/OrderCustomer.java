/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_ORDER_CUSTOMER", uniqueConstraints = {@UniqueConstraint(columnNames= {"ORDER_NUM", "PREFIX_HASH_FOLDER"})})
public class OrderCustomer extends AbstractEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -3979521859173438793L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="STATUS")
	private String status;

	@Column(name="ORDER_NUM")
	private String orderNum;
	
    @Column(name = "PREFIX_HASH_FOLDER")
    private String prefixHashFolder;
	   
    @Column(name = "MARKET_AREA_ID")
    private Long marketAreaId;

    @Column(name = "RETAILER_ID")
    private Long retailerId;
    
    @Column(name = "LOCALIZATION_ID")
    private Long localizationId;
    
	@Column(name="CUSTOMER_ID")
	private Long customerId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="BILLING_ORDER_ADDRESS_ID")
	private OrderAddress billingAddress;
	
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="SHIPPING_ORDER_ADDRESS_ID")
	private OrderAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID", insertable = true, updatable = true)
    private CurrencyReferential currency;
    
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_ID")
	private Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_ID")
	private Set<OrderPayment> orderPayments = new HashSet<OrderPayment>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public OrderCustomer(){
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
	
	public String getOrderNum() {
		return orderNum;
	}
	
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getPrefixHashFolder() {
        return prefixHashFolder;
    }
	
	public void setPrefixHashFolder(String prefixHashFolder) {
        this.prefixHashFolder = prefixHashFolder;
    }
	
	public Long getMarketAreaId() {
        return marketAreaId;
    }

    public void setMarketAreaId(Long marketAreaId) {
        this.marketAreaId = marketAreaId;
    }
    
    public Long getLocalizationId() {
        return localizationId;
    }
    
    public void setLocalizationId(Long localizationId) {
        this.localizationId = localizationId;
    }
    
    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public OrderAddress getBillingAddress() {
        return billingAddress;
    }
	
	public void setBillingAddress(OrderAddress billingAddress) {
        this.billingAddress = billingAddress;
    }
	
	public OrderAddress getShippingAddress() {
        return shippingAddress;
    }
	
	public void setShippingAddress(OrderAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CurrencyReferential getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyReferential currency) {
        this.currency = currency;
    }
    
	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	public Set<OrderShipment> getOrderShipments() {
        return orderShipments;
    }
	
	public void setOrderShipments(Set<OrderShipment> orderShipments) {
        this.orderShipments = orderShipments;
    }
	
    public Date getExpectedDeliveryDate() {
        Date expectedDeliveryDate = null;
        if(orderShipments != null
                && Hibernate.isInitialized(orderShipments)){
            for (Iterator<OrderShipment> iteratorOrderShipment = orderShipments.iterator(); iteratorOrderShipment.hasNext();) {
                final OrderShipment orderShipment = (OrderShipment) iteratorOrderShipment.next();
                if(expectedDeliveryDate == null){
                    expectedDeliveryDate = orderShipment.getExpectedDeliveryDate();
                } else {
                    if(expectedDeliveryDate.getTime() > orderShipment.getExpectedDeliveryDate().getTime()){
                        expectedDeliveryDate = orderShipment.getExpectedDeliveryDate();
                    }
                }
            }
        }
        return expectedDeliveryDate;
    }
    
    public Set<OrderTax> getOrderTaxes() {
        Set<OrderTax> orderTaxes = null;
        if(orderShipments != null
                && Hibernate.isInitialized(orderShipments)){
            orderTaxes = new HashSet<OrderTax>();
            for (Iterator<OrderShipment> iteratorOrderShipment = orderShipments.iterator(); iteratorOrderShipment.hasNext();) {
                final OrderShipment orderShipment = (OrderShipment) iteratorOrderShipment.next();
                for (Iterator<OrderItem> iteratorOrderItem = orderShipment.getOrderItems().iterator(); iteratorOrderItem.hasNext();) {
                    final OrderItem orderItem = (OrderItem) iteratorOrderItem.next();
                    for (Iterator<OrderTax> iteratorOrderTax = orderItem.getOrderTaxes().iterator(); iteratorOrderTax.hasNext();) {
                        OrderTax orderTax = (OrderTax) iteratorOrderTax.next();
                        orderTaxes.add(orderTax);
                    }
                }
            }
        }
        return orderTaxes;
    }
    
    public Set<OrderItem> getOrderItems() {
        Set<OrderItem> orderItems = null;
        if (orderShipments != null
                && Hibernate.isInitialized(orderShipments)) {
            orderItems = new HashSet<OrderItem>();
            for (Iterator<OrderShipment> iteratorOrderShipment = orderShipments.iterator(); iteratorOrderShipment.hasNext();) {
                final OrderShipment orderShipment = (OrderShipment) iteratorOrderShipment.next();
                for (Iterator<OrderItem> iteratorOrderItem = orderShipment.getOrderItems().iterator(); iteratorOrderItem.hasNext();) {
                    final OrderItem orderItem = (OrderItem) iteratorOrderItem.next();
                    orderItems.add(orderItem);
                }
            }
        }
        return orderItems;
    }
    
	public Set<OrderPayment> getOrderPayments() {
		return orderPayments;
	}
	
	public void setOrderPayments(Set<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}
	
    public BigDecimal getShippingMethodTotal() {
        BigDecimal shippingTotal = new BigDecimal("0");
        if (orderShipments != null
                && Hibernate.isInitialized(orderShipments)) {
            for (Iterator<OrderShipment> iteratorOrderShipment = orderShipments.iterator(); iteratorOrderShipment.hasNext();) {
                final OrderShipment orderShipment = (OrderShipment) iteratorOrderShipment.next();
                BigDecimal price = orderShipment.getPrice();
                if(price != null){
                    shippingTotal = shippingTotal.add(price);
                }
            }
        }
        return shippingTotal;
    }

    public String getShippingTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getShippingMethodTotal());
    }

    public BigDecimal getOrderItemTotal() {
        BigDecimal orderItemsTotal = new BigDecimal("0");
        if (orderShipments != null
                && Hibernate.isInitialized(orderShipments)) {
            for (Iterator<OrderShipment> iteratorOrderShipment = orderShipments.iterator(); iteratorOrderShipment.hasNext();) {
                final OrderShipment orderShipment = (OrderShipment) iteratorOrderShipment.next();
                for (Iterator<OrderItem> iteratorOrderItem = orderShipment.getOrderItems().iterator(); iteratorOrderItem.hasNext();) {
                    final OrderItem orderItem = (OrderItem) iteratorOrderItem.next();
                    orderItemsTotal = orderItemsTotal.add(orderItem.getTotalAmountOrderItem());
                }
            }
        }
        return orderItemsTotal;
    }

    public String getOrderItemTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getOrderItemTotal());
    }

    public BigDecimal getTaxTotal() {
        BigDecimal orderTaxesTotal = new BigDecimal("0");
        if (orderShipments != null
                && Hibernate.isInitialized(orderShipments)) {
            for (Iterator<OrderShipment> iteratorOrderShipment = orderShipments.iterator(); iteratorOrderShipment.hasNext();) {
                final OrderShipment orderShipment = (OrderShipment) iteratorOrderShipment.next();
                for (Iterator<OrderItem> iteratorOrderItem = orderShipment.getOrderItems().iterator(); iteratorOrderItem.hasNext();) {
                    final OrderItem orderItem = (OrderItem) iteratorOrderItem.next();
                    for (Iterator<OrderTax> iteratorOrderTax = orderItem.getOrderTaxes().iterator(); iteratorOrderTax.hasNext();) {
                        final OrderTax orderTax = (OrderTax) iteratorOrderTax.next();
                        orderTaxesTotal = orderTaxesTotal.add(orderTax.getAmount());
                    }
                }
            }
        }
        return orderTaxesTotal;
    }

    public String getTaxTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getTaxTotal());
    }

    public BigDecimal getOrderTotal() {
        BigDecimal carTotal = new BigDecimal("0");
        carTotal = carTotal.add(getOrderItemTotal());
        carTotal = carTotal.add(getShippingMethodTotal());
        carTotal = carTotal.add(getTaxTotal());
        return carTotal;
    }

    public String getOrderTotalWithStandardCurrencySign() {
        return getCurrency().formatPriceWithStandardCurrencySign(getOrderTotal());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((orderNum == null) ? 0 : orderNum.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderCustomer other = (OrderCustomer) obj;
        if (customerId == null) {
            if (other.customerId != null)
                return false;
        } else if (!customerId.equals(other.customerId))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (orderNum == null) {
            if (other.orderNum != null)
                return false;
        } else if (!orderNum.equals(other.orderNum))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "OrderCustomer [id=" + id + ", version=" + version + ", status=" + status + ", orderNum=" + orderNum + ", prefixHashFolder=" + prefixHashFolder + ", marketAreaId=" + marketAreaId
                + ", retailerId=" + retailerId + ", customerId=" + customerId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}