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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.annotation.CacheEntityInformation;
import org.hoteia.qalingo.core.comparator.CmsContentAssetComparator;
import org.hoteia.qalingo.core.comparator.CmsContentBlockComparator;

@Entity
@Table(name="TCMS_CONTENT_BLOCK")
@CacheEntityInformation(cacheName="web_cache_cms_content")
public class CmsContentBlock extends AbstractCmsEntity<CmsContentBlock, CmsContentBlockAttribute> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2371541327810234869L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false) // , columnDefinition = "int(11) default 1"
    private int version;

    @Column(name = "CODE")
    private String code;
    
    @Column(name = "ACTIVE", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean active = false;
    
    @Column(name = "TITLE")
    private String title;

    @Column(name = "TEXT")
    @Lob
    private String text;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsLink.class)
    @JoinColumn(name = "CMS_LINK_ID")
	private CmsLink link;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PARAMS")
    private String params;

    @Column(name="ORDERING", nullable=false) // , columnDefinition="int(11) default 0"
    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.MarketArea.class)
    @JoinColumn(name = "MARKET_AREA_ID", insertable = true, updatable = true)
    private MarketArea marketArea;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CmsContent.class)
    @JoinColumn(name = "CMS_CONTENT_ID", insertable = true, updatable = true)
    private CmsContent cmsContent;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CmsContentBlock.class)
    @JoinColumn(name = "CMS_CONTENT_BLOCK_ID", insertable = true, updatable = true)
    private CmsContentBlock cmsContentBlock;
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentBlockAttribute.class)
    @JoinColumn(name = "CMS_CONTENT_BLOCK_ID")
    private Set<CmsContentBlockAttribute> attributes = new HashSet<CmsContentBlockAttribute>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentAsset.class)
    @JoinColumn(name = "CMS_CONTENT_BLOCK_ID")
    private Set<CmsContentAsset> assets = new HashSet<CmsContentAsset>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CmsContentBlock.class)
    @JoinColumn(name = "CMS_CONTENT_BLOCK_ID")
    private Set<CmsContentBlock> blocks = new HashSet<CmsContentBlock>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
    
    public CmsContentBlock() {
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

	public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public CmsLink getLink() {
		return link;
	}
	
	public void setLink(CmsLink link) {
		this.link = link;
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

    public MarketArea getMarketArea() {
        return marketArea;
    }
	
	public void setMarketArea(MarketArea marketArea) {
		this.marketArea = marketArea;
	}
	
	public CmsContent getCmsContent() {
	    if(cmsContent != null){
	        return cmsContent;
	    } else {
	        if(cmsContentBlock != null){
	            return cmsContentBlock.getCmsContent();
	        }
	    }
		return null;
	}
	
	public void setCmsContent(CmsContent cmsContent) {
		this.cmsContent = cmsContent;
	}
	
	public CmsContentBlock getCmsContentBlock() {
        return cmsContentBlock;
    }
	
	public void setCmsContentBlock(CmsContentBlock cmsContentBlock) {
        this.cmsContentBlock = cmsContentBlock;
    }
	
	public Set<CmsContentBlockAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<CmsContentBlockAttribute> attributes) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ordering;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        CmsContentBlock other = (CmsContentBlock) obj;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ordering != other.ordering)
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
	public String toString() {
		return "CmsContentBlock [id=" + id + ", version=" + version + ", code="
				+ code + ", title=" + title + ", text=" + text + ", link="
				+ link + ", type=" + type + ", ordering=" + ordering
				+ ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
				+ ", getId()=" + getId() + ", getVersion()=" + getVersion()
				+ ", getCode()=" + getCode() + ", getTitle()=" + getTitle()
				+ ", getText()=" + getText() + ", getLink()=" + getLink()
				+ ", getType()=" + getType() + ", getMarketArea()="
				+ getMarketArea() + ", getOrdering()=" + getOrdering()
				+ ", getAttributes()=" + getAttributes() + ", getAssets()="
				+ getAssets() + ", getSortedAssets()=" + getSortedAssets()
				+ ", getDateCreate()=" + getDateCreate() + ", getDateUpdate()="
				+ getDateUpdate() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}
    
}