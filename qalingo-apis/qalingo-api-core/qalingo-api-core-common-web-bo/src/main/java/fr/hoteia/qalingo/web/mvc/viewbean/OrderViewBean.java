package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

public class OrderViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6638416873942425282L;
	private Long id;
	private int version;
	private String status;
	private String orderNum;
	private Long customerId;
	private Long billingAddressId;
	private Long shippingAddressId;

	// private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	// private Set<OrderPayment> orderPayments = new HashSet<OrderPayment>();
	// private Set<OrderShipment> orderShipments = new HashSet<OrderShipment>();
	// private Set<OrderTax> orderTaxes = new HashSet<OrderTax>();

	private String dateCreate;
	private String dateUpdate;

	private String detailsUrl;
	private String editUrl;

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
