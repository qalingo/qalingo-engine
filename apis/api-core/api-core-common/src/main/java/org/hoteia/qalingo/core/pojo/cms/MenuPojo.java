package fr.hoteia.opticmarketplace.core.pojo.cms;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.pojo.market.MarketAreaPojo;

public class MenuPojo {

    private Long id;
    private int version;
    private String code;
    private String name;
    private MarketAreaPojo marketArea;
    private boolean external;
    private String fullURlPath;
    private String position;
    private String type;
    private String params;
	private int ordering;
	
    private Set<MenuPojo> subMenus = new HashSet<MenuPojo>();
	
    private Date dateCreate;
	private Date dateUpdate;
	
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

	public MarketAreaPojo getMarketArea() {
		return marketArea;
	}
	
	public void setMarketArea(MarketAreaPojo marketArea) {
		this.marketArea = marketArea;
	}
	
	public String getMarketAreaName() {
		if(marketArea != null){
			return marketArea.getName();
		}
		return "";
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}
	
	public String getFullURlPath() {
		return fullURlPath;
	}
	
	public void setFullURlPath(String fullURlPath) {
		this.fullURlPath = fullURlPath;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public Set<MenuPojo> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<MenuPojo> subMenus) {
		this.subMenus = subMenus;
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

}