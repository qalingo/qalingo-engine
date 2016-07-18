package org.hoteia.qalingo.core.domain;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.comparator.CmsMenuComparator;

@Entity
@Table(name="TCMS_MENU")
public class CmsMenu extends AbstractCmsEntity<CmsMenu, CmsMenuAttribute> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2371543657810234869L;

    public static final String CACHE_NAME = "web_cache_cms_content";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE")
    private String code;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "APP")
    private String app;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.MarketArea.class)
    @JoinColumn(name = "MARKET_AREA_ID", insertable = true, updatable = true)
    private MarketArea marketArea;
    
    @Column(name = "POSITION")
    private String position;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsLink.class)
    @JoinColumn(name = "CMS_LINK_ID")
	private CmsLink link;
	
	@Column(name="ORDERING", nullable=false, columnDefinition="int(11) default 0")
	private int ordering;
	
    @Column(name = "ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CmsMenu.class)
    @JoinColumn(name="MENU_ID", insertable = true, updatable = true)
	private CmsMenu menu;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsMenu.class)
    @JoinColumn(name = "MENU_ID")
    private Set<CmsMenu> subMenus = new HashSet<CmsMenu>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsMenuAttribute.class)
    @JoinColumn(name = "MENU_ID")
    private Set<CmsMenuAttribute> attributes = new HashSet<CmsMenuAttribute>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentBlock.class)
    @JoinColumn(name = "MENU_ID")
    private Set<CmsContentBlock> blocks = new HashSet<CmsContentBlock>();
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
    public CmsMenu() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	
	public MarketArea getMarketArea() {
		return marketArea;
	}
	
	public void setMarketArea(MarketArea marketArea) {
		this.marketArea = marketArea;
	}
	
	public CmsLink getLink() {
		return link;
	}
	
	public void setLink(CmsLink link) {
		this.link = link;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CmsMenu getMenu() {
		return menu;
	}

	public void setMenu(CmsMenu menu) {
		this.menu = menu;
	}

	public Set<CmsMenu> getSubMenus() {
		return subMenus;
	}

    public List<CmsMenu> getSortedCmsMenus() {
        List<CmsMenu> sortedCmsMenus = null;
        if (subMenus != null 
                && Hibernate.isInitialized(subMenus)) {
        	sortedCmsMenus = new LinkedList<CmsMenu>(subMenus);
            Collections.sort(sortedCmsMenus, new CmsMenuComparator());
        }
        return sortedCmsMenus;
    }
    
	public void setSubMenus(Set<CmsMenu> subMenus) {
		this.subMenus = subMenus;
	}

	public Set<CmsMenuAttribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Set<CmsMenuAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public Set<CmsContentBlock> getBlocks() {
		return blocks;
	}
	
	public void setBlocks(Set<CmsContentBlock> blocks) {
		this.blocks = blocks;
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
	
    // Attributes

    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute attribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18Name = (String) getValue(CmsMenuAttribute.CMS_MENU_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Name)){
            return i18Name;
        }
        return name;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ordering;
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
		CmsMenu other = (CmsMenu) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordering != other.ordering)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CmsMenu [id=" + id + ", version=" + version + ", code=" + code
				+ ", name=" + name + ", ordering=" + ordering
				+ ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
	}
	
}