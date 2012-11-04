/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TECO_ORDER_ITEM")
public class OrderItem implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6982641911557993534L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="PRICE")
	private BigDecimal price;
	
	@Column(name="QUANTITY", nullable=false, columnDefinition="int(11) default 0")
	private int quantity;
	
	@Column(name="PRODUCT_SKU_CODE")
	private String productSkuCode;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name="PRODUCT_SKU_ID", insertable=false, updatable=false)
	private ProductSku productSku;

	public OrderItem(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProductSkuCode() {
		return productSkuCode;
	}
	
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}
	
	public ProductSku getProductSku() {
		return productSku;
	}
	
	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}
	
	public BigDecimal getTotalAmountOrderItem() {
		BigDecimal totalAmount = new BigDecimal("0");
		if(price != null){
			totalAmount = totalAmount.add(price);
		}
		totalAmount = totalAmount.multiply(new BigDecimal(quantity));
		return totalAmount;
	}
	
}
