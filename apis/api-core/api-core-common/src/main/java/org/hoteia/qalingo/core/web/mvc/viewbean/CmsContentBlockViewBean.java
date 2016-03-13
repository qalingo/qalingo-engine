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

    private String title;
    private String text;

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
                if(result == 0){
                    return (new Long(o1.getId())).compareTo((new Long(o2.getId())));
                }
                return result;
            }
            return 0;
        }
    }
}