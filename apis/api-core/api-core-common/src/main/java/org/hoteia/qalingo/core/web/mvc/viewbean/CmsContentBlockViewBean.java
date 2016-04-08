package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.web.mvc.viewbean.CmsContentViewBean.AssetViewBeanComparator;

public class CmsContentBlockViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549191150175454L;

    private String code;

    private String type;
    private int ordering;
    private boolean active;
    
    private String title;
    private String text;
    private String params;
    
    private CmsContentLinkViewBean link;
    private Set<AssetViewBean> assets = new HashSet<AssetViewBean>();
    private List<CmsContentBlockViewBean> blocks = new ArrayList<CmsContentBlockViewBean>();
    
    private List<ProductMarketingViewBean> productMarketings = new ArrayList<ProductMarketingViewBean>();
    private List<StoreViewBean> stores = new ArrayList<StoreViewBean>();
    
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
	
    public String getTextTroncatedDefault() {
        return getTextTroncated(300);
    }
    
    public String getTextTroncated(int length) {
        if(text != null && text.length() > length){
            return text.substring(0, (length - 3 )) + "...";
        }
        return text;
    }
	
	public String getParams() {
        return params;
    }
	
	public void setParams(String params) {
        this.params = params;
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
	
	public boolean hasLink() {
		if(link != null){
			return true;
		}
		return false;
	}

	public Set<AssetViewBean> getAssets() {
		return assets;
	}
	
    public List<AssetViewBean> getSortedAssets() {
        List<AssetViewBean> sortedAssets = null;
        if (assets != null 
                && Hibernate.isInitialized(assets)) {
            sortedAssets = new LinkedList<AssetViewBean>(assets);
            Collections.sort(sortedAssets, new AssetViewBeanComparator());
        }
        return sortedAssets;
    }
    
    public AssetViewBean getDefaultAsset() {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if("default".equalsIgnoreCase(assetViewBean.getType())){
                return assetViewBean;
            }
        }
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            return assetViewBean;
        }
        return null;
    }
    
    public String getDefaultImage() {
        for (Iterator<AssetViewBean> iterator = getSortedAssets().iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if("default".equalsIgnoreCase(assetViewBean.getType())){
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        for (Iterator<AssetViewBean> iterator = getSortedAssets().iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            return assetViewBean.getAbsoluteWebPath();
        }
        return null;
    }

	public void setAssets(Set<AssetViewBean> assets) {
		this.assets = assets;
	}

	public List<CmsContentBlockViewBean> getBlocks() {
		return blocks;
	}

	public CmsContentBlockViewBean getBlockByType(String type) {
		if(StringUtils.isNotEmpty(type)){
			for (CmsContentBlockViewBean cmsContentBlockViewBean : blocks) {
				if(cmsContentBlockViewBean.getType().equals(type)){
					return cmsContentBlockViewBean;
				}
			}
		}
		return null;
	}
	
	public List<CmsContentBlockViewBean> getSortedBlocks() {
		return blocks;
	}
	
    public CmsContentBlockViewBean getDefaultBlock() {
        if (blocks != null && !blocks.isEmpty()) {
            return blocks.get(0);
        }
        return null;
    }

    public void setBlocks(List<CmsContentBlockViewBean> blocks) {
        this.blocks = blocks;
    }
	    
	public List<ProductMarketingViewBean> getProductMarketings() {
		return productMarketings;
	}
	
	public void setProductMarketings(List<ProductMarketingViewBean> productMarketings) {
		this.productMarketings = productMarketings;
	}

	public List<StoreViewBean> getStores() {
		return stores;
	}
	
	public void setStores(List<StoreViewBean> stores) {
		this.stores = stores;
	}
	
    public class AssetViewBeanComparator implements Comparator<AssetViewBean> {
        public int compare(AssetViewBean o1, AssetViewBean o2) {
            if (o1 != null && o2 != null) {
                int result = ((Integer)o1.getOrdering()).compareTo(((Integer)o2.getOrdering()));
                if(result == 0 && o1.getId() != null && o2.getId() != null){
                    return (new Long(o1.getId())).compareTo((new Long(o2.getId())));
                }
                return result;
            }
            return 0;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
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
        CmsContentBlockViewBean other = (CmsContentBlockViewBean) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
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
        return "CmsContentBlockViewBean [code=" + code + ", type=" + type + ", ordering=" + ordering + ", title=" + title + ", text=" + text + ", params=" + params + ", link=" + link + ", assets="
                + assets + ", blocks=" + blocks + ", productMarketings=" + productMarketings + ", stores=" + stores + "]";
    }
    
}