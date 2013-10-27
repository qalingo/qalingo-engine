/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_ORDER")
public class Order implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -3979521859173438793L;
	
	public static final String ORDER_STATUS_PENDING = "PENDING";
	
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
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;

	@Column(name="BILLING_ADDRESS_ID")
	private Long billingAddressId;
	
	@Column(name="SHIPPING_ADDRESS_ID")
	private Long shippingAddressId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_ID")
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_ID")
	private Set<OrderPayment> orderPayments = new HashSet<OrderPayment>(); 
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_ID")
	private Set<OrderShipment> orderShipments = new HashSet<OrderShipment>(); 
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ORDER_ID")
	private Set<OrderTax> orderTaxes = new HashSet<OrderTax>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public Order(){
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
	
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<OrderPayment> getOrderPayments() {
		return orderPayments;
	}
	
	public void setOrderPayments(Set<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}
	
	public Set<OrderShipment> getOrderShipments() {
		return orderShipments;
	}
	
	public void setOrderShipments(Set<OrderShipment> orderShipments) {
		this.orderShipments = orderShipments;
	}
	
	public Set<OrderTax> getOrderTaxes() {
		return orderTaxes;
	}

	public void setOrderTaxes(Set<OrderTax> orderTaxes) {
		this.orderTaxes = orderTaxes;
	}
	
	public BigDecimal getTotalAmount() {
		if(orderItems != null){
			BigDecimal totalAmount = new BigDecimal("0");
			for (Iterator<OrderItem> iterator = orderItems.iterator(); iterator.hasNext();) {
				OrderItem orderItem = (OrderItem) iterator.next();
				BigDecimal totalAmountOrderItem = orderItem.getTotalAmountOrderItem();
				if(totalAmountOrderItem != null){
					totalAmount = totalAmount.add(orderItem.getTotalAmountOrderItem());
				}
			}
			return totalAmount;
		}
		return null;
	}
	
}
