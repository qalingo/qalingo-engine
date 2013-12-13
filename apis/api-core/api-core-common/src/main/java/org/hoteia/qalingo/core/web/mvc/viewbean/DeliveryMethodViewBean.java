package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class DeliveryMethodViewBean extends AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 267113307585572454L;
	private Long id;
	private int version;
	private String name;
	private String description;
	private String code;
	private String price;

	// private Set<ShippingCountry> shippingCountries = new
	// HashSet<ShippingCountry>();

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

	public String getPrice() {
        return price;
    }
	
	public void setPrice(String price) {
        this.price = price;
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
