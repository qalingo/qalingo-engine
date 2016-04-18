/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.hoteia.qalingo.core.domain.CmsContentAsset;
import org.hoteia.qalingo.core.domain.CmsContentBlock;
import org.hoteia.qalingo.core.domain.CmsMenu;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.FetchPlanGraphCmsContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("cmsContentDao")
public class CmsContentDao extends AbstractGenericDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CmsContent getCmsContentById(final Long cmsContentId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        FetchPlan fetchPlan = handleSpecificCmsContentFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", cmsContentId));
        
        CmsContent cmsContent = (CmsContent) criteria.uniqueResult();
        if(cmsContent != null){
        	cmsContent.setFetchPlan(fetchPlan);
        }
        return cmsContent;
	}

    public CmsContent getCmsContentByCode(final String cmsContentCode, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContent.class);

    	FetchPlan fetchPlan = handleSpecificCmsContentFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", cmsContentCode));
        
        CmsContent cmsContent = (CmsContent) criteria.uniqueResult();
        if(cmsContent != null){
        	cmsContent.setFetchPlan(fetchPlan);
        }
        return cmsContent;
    }
    
    public CmsContent getCmsContentByCode(final String cmsContentCode, final Long marketAreaId, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContent.class);

    	FetchPlan fetchPlan = handleSpecificCmsContentFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", cmsContentCode));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));

        CmsContent cmsContent = (CmsContent) criteria.uniqueResult();
        if(cmsContent != null){
        	cmsContent.setFetchPlan(fetchPlan);
        }
        return cmsContent;
    }
    
    public CmsContent findCmsContentByType(final String app, final String type, final Long marketAreaId, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContent.class);

    	FetchPlan fetchPlan = handleSpecificCmsContentFetchMode(criteria, params);

        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));

        CmsContent cmsContent = null;
        try {
            cmsContent = (CmsContent) criteria.uniqueResult();
            if(cmsContent != null){
            	cmsContent.setFetchPlan(fetchPlan);
            }
		} catch (NonUniqueResultException e) {
			logger.error("NonUniqueResultException: app='" + app + "', type: '" + type + "'" + "', marketAreaId: '" + marketAreaId + "'");
		}
        return cmsContent;
    }
    
    public List<Long> findAllCmsContentIds(final String type, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("type", type));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
   
    public List<Long> findAllCmsContentIds(final String type, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<Long> findAllCmsContentIds(final String type, final Long marketAreaId, final Long localizationId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.createAlias("localization", "localization", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("localization.id", localizationId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<Long> findAllCmsContentIdsByMasterContentId(final Long cmsContentId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.createAlias("masterCmsContent", "masterCmsContent", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("masterCmsContent.id", cmsContentId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<Long> findCmsContentIdByMasterContentIdAndMarketAreaId(final Long cmsContentId, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.createAlias("masterCmsContent", "masterCmsContent", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("masterCmsContent.id", cmsContentId));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<Long> findCmsContentIdByMasterContentIdAndMarketAreaIdAndLocalizationId(final Long cmsContentId, final Long marketAreaId, final Long localizationId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.createAlias("masterCmsContent", "masterCmsContent", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("masterCmsContent.id", cmsContentId));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.createAlias("localization", "localization", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("localization.id", localizationId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<Long> findAllCmsContentIdsBySeoKey(final String app, final String type, final String cmsContentSeoKey, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("seoKey", cmsContentSeoKey));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<CmsContent> findAllCmsContentsBySeoKey(final String app, final String type, final String cmsContentSeoKey, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContent.class);
        
    	handleSpecificCmsContentFetchMode(criteria, params);

        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("seoKey", cmsContentSeoKey));
        
        @SuppressWarnings("unchecked")
        List<CmsContent> cmsContents = criteria.list();
		return cmsContents;
    }
    
    public List<CmsContent> findAllCmsContents(final String type, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);
        
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        
        criteria.addOrder(Order.desc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<CmsContent> cmsContents = criteria.list();
		return cmsContents;
    }
    
    public List<CmsContent> findCmsContents(final String app, final String type, final Long marketAreaId, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);

        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        
        criteria.addOrder(Order.asc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<CmsContent> cmsContents = criteria.list();
		return cmsContents;
    }
    
    public List<Long> findLastCmsContentIds(final String app, final String type, final Long marketAreaId, final int maxResults, Object... params) {
        
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
		return cmsContentIds;
    }

    public List<Long> findLastActiveCmsContentIds(final String app, final String type, final Long marketAreaId, final int maxResults, Object... params) {
        
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.add(Restrictions.eq("active", true));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
		return cmsContentIds;
    }
    
    public List<Long> findLastActiveCmsContentIds(final String app, final String type, final Long marketAreaId, final Long localizationId, final int maxResults, Object... params) {
        
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.createAlias("localization", "localization", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("localization.id", localizationId));
        criteria.add(Restrictions.eq("active", true));
        criteria.setProjection(Projections.property("id"));
        criteria.addOrder(Order.desc("dateCreate"));
        
        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        @SuppressWarnings("unchecked")
        List<Long> cmsContentIds = criteria.list();
        return cmsContentIds;
    }
    
    public List<CmsContent> findCmsContentsByProductSkuId(final String app, final String type, final Long marketAreaId, long productSkuId, int maxResults, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        
        handleSpecificCmsContentFetchMode(criteria, params);

        if(maxResults != 0){
            criteria.setMaxResults(maxResults);
        }
        
        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        
        criteria.createAlias("productSkus", "productSku", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("productSku.id", productSkuId));

        criteria.addOrder(Order.asc("dateCreate"));

        @SuppressWarnings("unchecked")
        List<CmsContent> cmsContents = criteria.list();
		return cmsContents;
    }
    
	public CmsContent saveOrUpdateCmsContent(final CmsContent cmsContent) {
		if(cmsContent.getDateCreate() == null){
			cmsContent.setDateCreate(new Date());
		}
		cmsContent.setDateUpdate(new Date());
        if (cmsContent.getId() != null) {
            if(em.contains(cmsContent)){
                em.refresh(cmsContent);
            }
            CmsContent mergedCmsContent = em.merge(cmsContent);
            em.flush();
            return mergedCmsContent;
        } else {
            em.persist(cmsContent);
            return cmsContent;
        }
	}
	
	public void deleteCmsContent(final CmsContent cmsContent) {
		em.remove(cmsContent);
	}
	
    protected FetchPlan handleSpecificCmsContentFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCmsContent.defaultCmsContentFetchPlan());
        }
    }
    
    // CMS CONTENT BLOCK
    
	public CmsContentBlock getCmsContentBlockById(final Long cmsContentBlockId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContentBlock.class);
        
        FetchPlan fetchPlan = handleSpecificCmsContentBlockFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", cmsContentBlockId));
        
        CmsContentBlock cmsContentBlock = (CmsContentBlock) criteria.uniqueResult();
        if(cmsContentBlock != null){
        	cmsContentBlock.setFetchPlan(fetchPlan);
        }
        return cmsContentBlock;
	}

    public CmsContentBlock getCmsContentBlockByCode(final String cmsContentBlockCode, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContentBlock.class);

    	FetchPlan fetchPlan = handleSpecificCmsContentBlockFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", cmsContentBlockCode));
        
        CmsContentBlock cmsContentBlock = (CmsContentBlock) criteria.uniqueResult();
        if(cmsContentBlock != null){
        	cmsContentBlock.setFetchPlan(fetchPlan);
        }
        return cmsContentBlock;
    }
    
    public CmsContentBlock getCmsContentBlockByType(final String app, final String type, final Long marketAreaId, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContentBlock.class);

    	FetchPlan fetchPlan = handleSpecificCmsContentBlockFetchMode(criteria, params);

        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("type", type));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));

        CmsContentBlock cmsContentBlock = null;
        try {
            cmsContentBlock = (CmsContentBlock) criteria.uniqueResult();
            if(cmsContentBlock != null){
            	cmsContentBlock.setFetchPlan(fetchPlan);
            }
		} catch (NonUniqueResultException e) {
			logger.error("NonUniqueResultException: app='" + app + "', type: '" + type + "'" + "', marketAreaId: '" + marketAreaId + "'");
		}
        return cmsContentBlock;
    }
    
	public CmsContentBlock saveOrUpdateCmsContentBlock(final CmsContentBlock cmsContentBlock) {
		if(cmsContentBlock.getDateCreate() == null){
			cmsContentBlock.setDateCreate(new Date());
		}
		cmsContentBlock.setDateUpdate(new Date());
        if (cmsContentBlock.getId() != null) {
            if(em.contains(cmsContentBlock)){
                em.refresh(cmsContentBlock);
            }
            CmsContentBlock mergedCmsContentBlock = em.merge(cmsContentBlock);
            em.flush();
            return mergedCmsContentBlock;
        } else {
            em.persist(cmsContentBlock);
            return cmsContentBlock;
        }
	}
	
	public void deleteCmsContentBlock(final CmsContentBlock cmsContentBlock) {
		em.remove(cmsContentBlock);
	}
	
    protected FetchPlan handleSpecificCmsContentBlockFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCmsContent.defaultCmsContentBlockFetchPlan());
        }
    }
    
    // CMS CONTENT ASSET
    
	public CmsContentAsset getCmsContentAssetById(final Long cmsContentAssetId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContentAsset.class);
        
        FetchPlan fetchPlan = handleSpecificCmsContentAssetFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", cmsContentAssetId));
        
        CmsContentAsset cmsContentAsset = (CmsContentAsset) criteria.uniqueResult();
        if(cmsContentAsset != null){
        	cmsContentAsset.setFetchPlan(fetchPlan);
        }
        return cmsContentAsset;
	}

    public CmsContentAsset getCmsContentAssetByCode(final String cmsContentAssetCode, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsContentAsset.class);

    	FetchPlan fetchPlan = handleSpecificCmsContentAssetFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", cmsContentAssetCode));
        
        CmsContentAsset cmsContentAsset = (CmsContentAsset) criteria.uniqueResult();
        if(cmsContentAsset != null){
        	cmsContentAsset.setFetchPlan(fetchPlan);
        }
        return cmsContentAsset;
    }
    
	public CmsContentAsset saveOrUpdateCmsContentAsset(final CmsContentAsset cmsContentAsset) {
		if(cmsContentAsset.getDateCreate() == null){
			cmsContentAsset.setDateCreate(new Date());
		}
		cmsContentAsset.setDateUpdate(new Date());
        if (cmsContentAsset.getId() != null) {
            if(em.contains(cmsContentAsset)){
                em.refresh(cmsContentAsset);
            }
            CmsContentAsset mergedCmsContentAsset = em.merge(cmsContentAsset);
            em.flush();
            return mergedCmsContentAsset;
        } else {
            em.persist(cmsContentAsset);
            return cmsContentAsset;
        }
	}
	
	public void deleteCmsContentAsset(final CmsContentAsset cmsContentAsset) {
		em.remove(cmsContentAsset);
	}
	
    protected FetchPlan handleSpecificCmsContentAssetFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCmsContent.defaultCmsContentAssetFetchPlan());
        }
    }
    
    // CMS MENU
    
    public CmsMenu getCmsMenuById(final Long menuId, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsMenu.class);
    	
        FetchPlan fetchPlan = handleSpecificCmsMenuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", menuId));
        CmsMenu menu = (CmsMenu) criteria.uniqueResult();
        if(menu != null){
        	menu.setFetchPlan(fetchPlan);
        }
        return menu;
    }

    public CmsMenu getCmsMenuById(final String rawMenuId, Object... params) {
        long menuId = -1;
        try {
        	menuId = Long.parseLong(rawMenuId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCmsMenuById(menuId, params);
    }
    
    public CmsMenu getCmsMenuByCode(final String menuCode, Object... params) {
    	Criteria criteria = createDefaultCriteria(CmsMenu.class);
    	
        FetchPlan fetchPlan = handleSpecificCmsMenuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", menuCode));
        CmsMenu menu = (CmsMenu) criteria.uniqueResult();
        if(menu != null){
        	menu.setFetchPlan(fetchPlan);
        }
        return menu;
    }
    
    public List<CmsMenu> findAllCmsMenus(final String app, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsMenu.class);
        
        handleSpecificCmsMenuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("app", app));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        
        criteria.addOrder(Order.asc("position"));
        criteria.addOrder(Order.asc("ordering"));

        @SuppressWarnings("unchecked")
        List<CmsMenu> menus = criteria.list();
		return menus;
    }
    
    public List<CmsMenu> findAllActiveRootCmsMenusByPosition(final String app, final Long marketAreaId, final String position, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsMenu.class);
        
        handleSpecificCmsMenuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("app", app));
        criteria.add(Restrictions.eq("position", position));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        criteria.add(Restrictions.eq("active", true));
        criteria.add(Restrictions.isNull("menu"));
        
        criteria.addOrder(Order.asc("position"));
        criteria.addOrder(Order.asc("ordering"));

        @SuppressWarnings("unchecked")
        List<CmsMenu> menus = criteria.list();
		return menus;
    }
    
    public List<CmsMenu> findAllCmsMenusByMarketAreaId(final String app, final Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsMenu.class);
        
        handleSpecificCmsMenuFetchMode(criteria, params);

        criteria.add(Restrictions.eq("app", app));
        criteria.createAlias("marketArea", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));
        
        criteria.addOrder(Order.asc("position"));
        criteria.addOrder(Order.asc("ordering"));

        @SuppressWarnings("unchecked")
        List<CmsMenu> menus = criteria.list();
		return menus;
    }
    
    public CmsMenu saveOrUpdateCmsMenu(CmsMenu menu) {
    	if(menu.getDateCreate() == null){
    		menu.setDateCreate(new Date());
		}
    	menu.setDateUpdate(new Date());
        if (menu.getId() != null) {
            if(em.contains(menu)){
                em.refresh(menu);
            }
            CmsMenu mergedCmsMenu = em.merge(menu);
            em.flush();
            return mergedCmsMenu;
        } else {
            em.persist(menu);
            return menu;
        }
    }

    public void deleteCmsMenu(CmsMenu menu) {
    	em.remove(menu);
    }
    
    protected FetchPlan handleSpecificCmsMenuFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCmsContent.defaultCmsMenuFetchPlan());
        }
    }

}