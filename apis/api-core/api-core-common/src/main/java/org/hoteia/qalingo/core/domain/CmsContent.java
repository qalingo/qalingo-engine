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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.comparator.CmsContentAssetComparator;
import org.hoteia.qalingo.core.comparator.CmsContentBlockComparator;

@Entity
@Table(name="TCMS_CONTENT")
public class CmsContent extends AbstractCmsEntity<CmsContent, CmsContentAttribute> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2371548827810234869L;

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
    
    @Column(name = "APP")
    private String app;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "TITLE")
    private String title;

    @Column(name = "LINK_TITLE")
    private String linkTitle;
    
    @Column(name = "SEO_SEGMENT")
    private String seoSegment;

    @Column(name = "SEO_KEY")
    private String seoKey;
    
    @Column(name = "SUMMARY")
    @Lob
    private String summary;
    
    @Column(name = "MASTER", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean master;
    
    @Column(name = "ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentAttribute.class)
    @JoinColumn(name = "CMS_CONTENT_ID")
    private Set<CmsContentAttribute> attributes = new HashSet<CmsContentAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentAsset.class)
    @JoinColumn(name = "CMS_CONTENT_ID")
    private Set<CmsContentAsset> assets = new HashSet<CmsContentAsset>();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.User.class)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.MarketArea.class)
    @JoinColumn(name = "MARKET_AREA_ID", insertable = true, updatable = true)
    private MarketArea marketArea;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Localization.class)
    @JoinColumn(name = "LOCALIZATION_ID", insertable = true, updatable = true)
    private Localization localization;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CmsContent.class)
    @JoinColumn(name = "CMS_CONTENT_ID", insertable = true, updatable = true)
    private CmsContent masterCmsContent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentBlock.class)
    @JoinColumn(name = "CMS_CONTENT_ID")
    private Set<CmsContentBlock> blocks = new HashSet<CmsContentBlock>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductSku.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TCMS_CONTENT_PRODUCT_SKU_REL", joinColumns = @JoinColumn(name = "CMS_CONTENT_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_SKU_ID"))
    private Set<ProductSku> productSkus = new HashSet<ProductSku>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductBrand.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TCMS_CONTENT_PRODUCT_BRAND_REL", joinColumns = @JoinColumn(name = "CMS_CONTENT_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_BRAND_ID"))
    private Set<ProductBrand> productBrands = new HashSet<ProductBrand>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_PUBLISH")
    private Date datePublish;
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
    public CmsContent() {
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

	public String getApp() {
		return app;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLinkTitle() {
		return linkTitle;
	}
	
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getSeoSegment() {
		return seoSegment;
	}

	public void setSeoSegment(String seoSegment) {
		this.seoSegment = seoSegment;
	}

	public String getSeoKey() {
		return seoKey;
	}

	public void setSeoKey(String seoKey) {
		this.seoKey = seoKey;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<CmsContentAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<CmsContentAttribute> attributes) {
		this.attributes = attributes;
	}

	public Set<CmsContentAsset> getAssets() {
		return assets;
	}
	
    public List<CmsContentAsset> getSortedAssets() {
        List<CmsContentAsset> sortedCmsContentAssets = null;
        if (assets != null 
                && Hibernate.isInitialized(assets)) {
        	sortedCmsContentAssets = new LinkedList<CmsContentAsset>(assets);
            Collections.sort(sortedCmsContentAssets, new CmsContentAssetComparator());
        }
        return sortedCmsContentAssets;
    }
    
	public void setAssets(Set<CmsContentAsset> assets) {
		this.assets = assets;
	}
	
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    
    public MarketArea getMarketArea() {
        return marketArea;
    }
    
    public void setMarketArea(MarketArea marketArea) {
        this.marketArea = marketArea;
    }
    
    public Localization getLocalization() {
        return localization;
    }
    
    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
    
	public CmsContent getMasterCmsContent() {
        return masterCmsContent;
    }
	
	public void setMasterCmsContent(CmsContent masterCmsContent) {
        this.masterCmsContent = masterCmsContent;
    }
	
    public Set<CmsContentBlock> getBlocks() {
        return blocks;
    }

    public List<CmsContentBlock> getSortedCmsContentBlocks() {
        List<CmsContentBlock> sortedCmsContentBlocks = null;
        if (blocks != null 
                && Hibernate.isInitialized(blocks)) {
            sortedCmsContentBlocks = new LinkedList<CmsContentBlock>(blocks);
            Collections.sort(sortedCmsContentBlocks, new CmsContentBlockComparator());
        }
        return sortedCmsContentBlocks;
    }
    
    public CmsContentBlock getBlockByType(String type) {
         if (blocks != null 
                    && Hibernate.isInitialized(blocks)
                    && StringUtils.isNotEmpty(type)) {
            for (CmsContentBlock cmsContentBlock : blocks) {
                if(cmsContentBlock.getType().equals(type)){
                    return cmsContentBlock;
                }
            }
        }
        return null;
    }
    
    public void setBlocks(Set<CmsContentBlock> blocks) {
        this.blocks = blocks;
    }
    
	public Set<ProductSku> getProductSkus() {
		return productSkus;
	}
	
    public List<ProductSku> getSortedProductSkus() {
        List<ProductSku> sortedProductSkus = null;
        if (Hibernate.isInitialized(productSkus)
                && productSkus != null) {
        	sortedProductSkus = new LinkedList<ProductSku>(productSkus);
        	// TODO : sort
        }
        return sortedProductSkus;
    }
    
	public void setProductSkus(Set<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}
	
	public Set<ProductBrand> getProductBrands() {
        return productBrands;
    }
	
    public ProductBrand getDefaultProductBrand() {
        if (Hibernate.isInitialized(productBrands)
                && productBrands != null) {
            for (ProductBrand productBrand : productBrands) {
                return productBrand;
            }
        }
        return null;
    }
    
	public void setProductBrands(Set<ProductBrand> productBrands) {
        this.productBrands = productBrands;
    }
	
	public Date getDatePublish() {
        return datePublish;
    }
	
	public void setDatePublish(Date datePublish) {
        this.datePublish = datePublish;
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

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((app == null) ? 0 : app.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((datePublish == null) ? 0 : datePublish.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((seoKey == null) ? 0 : seoKey.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + version;
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
        CmsContent other = (CmsContent) obj;
        if (app == null) {
            if (other.app != null)
                return false;
        } else if (!app.equals(other.app))
            return false;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (datePublish == null) {
            if (other.datePublish != null)
                return false;
        } else if (!datePublish.equals(other.datePublish))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (seoKey == null) {
            if (other.seoKey != null)
                return false;
        } else if (!seoKey.equals(other.seoKey))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    @Override
	public String toString() {
		return "CmsContent [id=" + id + ", version=" + version + ", code="
				+ code + ", app=" + app + ", type=" + type + ", title=" + title
				+ ", linkTitle=" + linkTitle + ", seoSegment=" + seoSegment
				+ ", seoKey=" + seoKey + ", summary=" + summary + ", active="
				+ active + ", dateCreate=" + dateCreate + ", dateUpdate="
				+ dateUpdate + "]";
	}
	
}