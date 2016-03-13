package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.HashSet;
import java.util.Set;

import org.hoteia.qalingo.core.domain.CmsMenu;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;

public class CmsMenuViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549193150175454L;

    private String code;
    protected String key;
    protected String name;
    private String description;
    private String app;
    private MarketAreaViewBean marketArea;
    private LocalizationViewBean localization;
    private boolean active;
    private boolean catalog = false;
    
    protected CmsContentLinkViewBean link;
    
    private String position;
	private int ordering;

    protected String img;

    protected String cssClass;
    protected String cssIcon;
    
	private CmsMenu menu;
    
	private Set<CmsMenuViewBean> subMenus = new HashSet<CmsMenuViewBean>();
    private Set<CmsContentBlockViewBean> blocks = new HashSet<CmsContentBlockViewBean>();
    
	private String editUrl;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public MarketAreaViewBean getMarketArea() {
		return marketArea;
	}

	public void setMarketArea(MarketAreaViewBean marketArea) {
		this.marketArea = marketArea;
	}

	public LocalizationViewBean getLocalization() {
		return localization;
	}

	public void setLocalization(LocalizationViewBean localization) {
		this.localization = localization;
	}

    public boolean isHome() {
        if(key != null
                && FoUrls.HOME.getKey().equalsIgnoreCase(key)){
            return true;
        }
        return false;
    }
    
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCatalog() {
		return catalog;
	}

	public void setCatalog(boolean catalog) {
		this.catalog = catalog;
	}

	public CmsContentLinkViewBean getLink() {
		return link;
	}
	
	public void setLink(CmsContentLinkViewBean link) {
		this.link = link;
	}
	
	public String getUrl() {
		if(link != null){
			link.getUrl();
		}
		return null;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

    public String getCssClass() {
        if(isActive()){
            return cssClass + " active";
        }
        return cssClass;
     }

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssIcon() {
		return cssIcon;
	}

	public void setCssIcon(String cssIcon) {
		this.cssIcon = cssIcon;
	}

	public CmsMenu getMenu() {
		return menu;
	}

	public void setMenu(CmsMenu menu) {
		this.menu = menu;
	}

	public Set<CmsMenuViewBean> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<CmsMenuViewBean> subMenus) {
		this.subMenus = subMenus;
	}

    public boolean getHasSubMenu() {
        if (subMenus != null && subMenus.size() > 0) {
            return true;
        }
        return false;
    }
    
	public Set<CmsContentBlockViewBean> getBlocks() {
		return blocks;
	}

	public CmsContentBlockViewBean getCmsBlock() {
		if (blocks != null && blocks.size() > 0) {
			return blocks.iterator().next();
		}
		return null;
	}
	
	public void setBlocks(Set<CmsContentBlockViewBean> blocks) {
		this.blocks = blocks;
	}

    public boolean getHasCmsBlock() {
        if (blocks != null && blocks.size() > 0) {
            return true;
        }
        return false;
    }
    
	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

}