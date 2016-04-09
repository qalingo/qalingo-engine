package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;

public class CmsContentViewBean extends AbstractViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5387549193050175454L;

    private int version;
	private String code;
	private String app;
	private String type;
	private String title;
	private String linkTitle;
	private String seoSegment;
	private String seoKey;
	private String summary;
	private int ordering;
	private boolean master;
	private boolean active;

	private UserViewBean user;
    private MarketAreaViewBean marketArea;

    protected List<CmsContentBlockViewBean> blocks = new ArrayList<CmsContentBlockViewBean>();
    protected List<AssetViewBean> assets = new ArrayList<AssetViewBean>();
    protected List<ProductBrandViewBean> productBrands = new ArrayList<ProductBrandViewBean>();
    
	private String editUrl;
	private String detailsUrl;
	
	private String dateCreateDay;
	private String dateCreateMonthYear;
	
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

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

    public String getSummaryTroncatedDefault() {
        return getSummaryTroncated(300);
    }
    
    public String getSummaryTroncated(int length) {
        if(summary != null && summary.length() > length){
            return summary.substring(0, (length - 3 )) + "...";
        }
        return summary;
    }
	   
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public int getOrdering() {
		return ordering;
	}
	
	public void setOrdering(int ordering) {
		this.ordering = ordering;
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
	
	public UserViewBean getUser() {
		return user;
	}
	
	public void setUser(UserViewBean user) {
		this.user = user;
	}

    public MarketAreaViewBean getMarketArea() {
        return marketArea;
    }
    
    public void setMarketArea(MarketAreaViewBean marketArea) {
        this.marketArea = marketArea;
    }
	
	public List<CmsContentBlockViewBean> getBlocks() {
		return blocks;
	}

	public List<CmsContentBlockViewBean> getSortedBlocks() {
	    List<CmsContentBlockViewBean> sortedCmsContentBlocks = null;
        if (blocks != null 
                && Hibernate.isInitialized(blocks)) {
            sortedCmsContentBlocks = new LinkedList<CmsContentBlockViewBean>(blocks);
            Collections.sort(sortedCmsContentBlocks, new CmsContentBlockViewBeanComparator());
        }
        return sortedCmsContentBlocks;
	}
	
	public CmsContentBlockViewBean getDefaultBlock() {
		if(blocks != null && !blocks.isEmpty()){
			return blocks.get(0);
		}
		return null;
	}
	
	public CmsContentBlockViewBean getBlock(String type) {
		if(blocks != null && !blocks.isEmpty()){
			for (CmsContentBlockViewBean blockViewBean : blocks) {
				if(blockViewBean.getType() != null 
						&& blockViewBean.getType().equals(type)){
					return blockViewBean;
				}
			}
		}
		return null;
	}
	
	public boolean hasBlock(String type) {
		if(blocks != null && !blocks.isEmpty()){
			for (CmsContentBlockViewBean blockViewBean : blocks) {
				if(blockViewBean.getType() != null 
						&& blockViewBean.getType().equals(type)){
					return true;
				}
			}
		}
		return false;
	}

	public void setBlocks(List<CmsContentBlockViewBean> blocks) {
		this.blocks = blocks;
	}

    public List<AssetViewBean> getAssets() {
        return assets;
    }
    
    public List<AssetViewBean> getAssets(String type) {
        List<AssetViewBean> assetsByType = new ArrayList<AssetViewBean>();
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                assetsByType.add(assetViewBean);
            }
        }
        if(assetsByType.size() == 0){
            assetsByType.add(getDefaultAsset());
        }
        return assetsByType;
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
    
    public String getAssetPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getPath();
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getPath();
        }
        return null;
    }
    
    public String getAssetAbsoluteWebPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getAbsoluteWebPath();
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getAbsoluteWebPath();
        }
        return null;
    }
    
    public String getAssetRelativeWebPath(String type) {
        for (Iterator<AssetViewBean> iterator = assets.iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if(assetViewBean.getType().equals(type)){
                return assetViewBean.getRelativeWebPath();
            }
        }
        AssetViewBean assetViewBean = getDefaultAsset();
        if(assetViewBean != null){
            return assetViewBean.getRelativeWebPath();
        }
        return null;
    }

    public AssetViewBean getDefaultAsset() {
        for (Iterator<AssetViewBean> iterator = getSortedAssets().iterator(); iterator.hasNext();) {
            AssetViewBean assetViewBean = (AssetViewBean) iterator.next();
            if("default".equalsIgnoreCase(assetViewBean.getType())){
                return assetViewBean;
            }
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
        return null;
    }
    
    public void setAssets(List<AssetViewBean> assets) {
        this.assets = assets;
    }
    
    public List<ProductBrandViewBean> getProductBrands() {
        return productBrands;
    }
    
    public ProductBrandViewBean getDefaultProductBrand() {
        if (productBrands != null) {
            for (ProductBrandViewBean productBrand : productBrands) {
                return productBrand;
            }
        }
        return null;
    }
    
    public void setProductBrands(List<ProductBrandViewBean> productBrands) {
        this.productBrands = productBrands;
    }
    
    public String getEditUrl() {
		return editUrl;
	}
    
    public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}
	
	public String getDetailsUrl() {
		return detailsUrl;
	}
	
	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}
	
	public String getDateCreateDay() {
		return dateCreateDay;
	}
	
	public void setDateCreateDay(String dateCreateDay) {
		this.dateCreateDay = dateCreateDay;
	}
	
	public String getDateCreateMonthYear() {
		return dateCreateMonthYear;
	}
	
	public void setDateCreateMonthYear(String dateCreateMonthYear) {
		this.dateCreateMonthYear = dateCreateMonthYear;
	}

	public class CmsContentBlockViewBeanComparator implements Comparator<CmsContentBlockViewBean> {
	    public int compare(CmsContentBlockViewBean o1, CmsContentBlockViewBean o2) {
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