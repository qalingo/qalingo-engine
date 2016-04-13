/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.dao.CmsContentDao;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.hoteia.qalingo.core.domain.CmsContentAsset;
import org.hoteia.qalingo.core.domain.CmsContentBlock;
import org.hoteia.qalingo.core.domain.CmsMenu;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cmsContentService")
@Transactional
public class CmsContentService {

    @Autowired
    private CmsContentDao cmsContentDao;

    public String buildArticleCode(final MarketArea marketArea, final Localization localization, final CmsContent cmsContent, Object... params) {
        return buildCmsContentCode(marketArea, localization, cmsContent.getSeoKey(), params);
    }
    
    public String buildArticleCode(final MarketArea marketArea, final Localization localization, final String cmsContentSeoKey, Object... params) {
        return buildCmsContentCode(marketArea, localization, cmsContentSeoKey, params);
    }
    
    public String buildCmsContentCode(final MarketArea marketArea, final Localization localization, final String cmsContentSeoKey, Object... params) {
        String code = marketArea.getCode() + "_" +  localization.getCode() + "_" + cmsContentSeoKey;
        return CoreUtil.cleanEntityCode(code);
    }
    
    public CmsContent getCmsContentById(final Long cmsContentId, Object... params) {
        return cmsContentDao.getCmsContentById(cmsContentId, params);
    }

    public CmsContent getCmsContentById(final String rawCmsContentId, Object... params) {
        long cmsContentId = -1;
        try {
            cmsContentId = Long.parseLong(rawCmsContentId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCmsContentById(cmsContentId, params);
    }

    public CmsContent getCmsContentByCode(final String cmsContentCode, Object... params) {
        return cmsContentDao.getCmsContentByCode(cmsContentCode, params);
    }
    
    public CmsContent getCmsContentByAppAndType(final String app, final String type, final Long marketAreaId, final String cmsContentSeoKey, Object... params) {
        return cmsContentDao.getCmsContentByCode(app, type, marketAreaId, cmsContentSeoKey, params);
    }
    
    public CmsContent findCmsContentByCode(final String cmsContentCode, final Long marketAreaId, Object... params) {
        return cmsContentDao.getCmsContentByCode(cmsContentCode, marketAreaId, params);
    }
    
    public CmsContent findCmsContentByType(final String app, final String type, final Long marketAreaId, Object... params) {
        return cmsContentDao.findCmsContentByType(app, type, marketAreaId, params);
    }

    public CmsContent findCmsContentBySeoKey(final String app, final String type, final Long marketAreaId, final String cmsContentSeoKey, Object... params) {
//      return cmsContentDao.findCmsContentBySeoKey(app, type, marketAreaId, cmsContentSeoKey, params);
    	return cmsContentDao.getCmsContentBySeoKey(app, type, marketAreaId, cmsContentSeoKey, params);
    }

    public List<Long> findAllCmsContentIds(final String app, final String type, Object... params) {
        return cmsContentDao.findAllCmsContentIds(type, params);
    }

    public List<Long> findAllCmsContentIdsByMasterContentId(final Long cmsContentId, Object... params) {
        return cmsContentDao.findAllCmsContentIdsByMasterContentId(cmsContentId, params);
    }
    
    public List<Long> findCmsContentIdByMasterContentIdAndMarketAreaId(final Long cmsContentId, final Long marketAreaId, Object... params) {
        return cmsContentDao.findCmsContentIdByMasterContentIdAndMarketAreaId(cmsContentId, marketAreaId, params);
    }

    public List<Long> findCmsContentIdByMasterContentIdAndMarketAreaIdAndLocalizationId(final Long cmsContentId, final Long marketAreaId, final Long localizationId, Object... params) {
        return cmsContentDao.findCmsContentIdByMasterContentIdAndMarketAreaIdAndLocalizationId(cmsContentId, marketAreaId, localizationId, params);
    }
    
    public List<CmsContent> findCmsContentsBySeoKey(final String app, final String type, final Long marketAreaId, final String cmsContentSeoKey, Object... params) {
        return cmsContentDao.findCmsContentsBySeoKey(app, type, marketAreaId, cmsContentSeoKey, params);
    }
    
    public List<CmsContent> findAllCmsContents(final String app, final String type, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsContents(type, marketAreaId, params);
    }

    public List<Long> findAllCmsContentIds(final String app, final String type, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsContentIds(type, marketAreaId, params);
    }

    public List<CmsContent> findCmsContents(final String app, final String type, final Long marketAreaId, int maxResults, Object... params) {
        return cmsContentDao.findCmsContents(app, type, marketAreaId, maxResults, params);
    }
    
    public List<Long> findLastCmsContentIds(final String app, final String type, final Long marketAreaId, int maxResults, Object... params) {
        return cmsContentDao.findLastCmsContentIds(app, type, marketAreaId, maxResults, params);
    }

    public List<Long> findLastActiveCmsContentIds(final String app, final String type, final Long marketAreaId, int maxResults, Object... params) {
        return cmsContentDao.findLastActiveCmsContentIds(app, type, marketAreaId, maxResults, params);
    }

    public List<Long> findLastActiveCmsContentIds(final String app, final String type, final Long marketAreaId, final Long localizationId, int maxResults, Object... params) {
        return cmsContentDao.findLastActiveCmsContentIds(app, type, marketAreaId, localizationId, maxResults, params);
    }
    
    public List<CmsContent> findCmsContentsByProductSkuId(final String app, final String type, final Long marketAreaId, long productSkuId, int maxResults, Object... params) {
        return cmsContentDao.findCmsContentsByProductSkuId(app, type, marketAreaId, productSkuId, maxResults, params);
    }

    public CmsContent saveOrUpdateCmsContent(CmsContent cmsContent) {
    	return cmsContentDao.saveOrUpdateCmsContent(cmsContent);
    }

    public void deleteCmsContent(CmsContent cmsContent) {
        cmsContentDao.deleteCmsContent(cmsContent);
    }
    
    // CMS CONTENT BLOCK
    
    public CmsContentBlock getCmsContentBlockById(final Long cmsContentBlockId, Object... params) {
        return cmsContentDao.getCmsContentBlockById(cmsContentBlockId, params);
    }

    public CmsContentBlock getCmsContentBlockById(final String rawCmsContentBlockId, Object... params) {
        long cmsContentBlockId = -1;
        try {
            cmsContentBlockId = Long.parseLong(rawCmsContentBlockId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCmsContentBlockById(cmsContentBlockId, params);
    }

    public CmsContentBlock getCmsContentBlockByCode(final String cmsContentBlockCode, Object... params) {
        return cmsContentDao.getCmsContentBlockByCode(cmsContentBlockCode, params);
    }
    
    public CmsContentBlock getCmsContentBlockByType(final String app, final String type, final Long marketAreaId, Object... params) {
        return cmsContentDao.getCmsContentBlockByType(app, type, marketAreaId, params);
    }
    
    public CmsContentBlock saveOrUpdateCmsContentBlock(CmsContentBlock cmsContentBlock) {
    	return cmsContentDao.saveOrUpdateCmsContentBlock(cmsContentBlock);
    }

    public void deleteCmsContentBlock(CmsContentBlock cmsContentBlock) {
        cmsContentDao.deleteCmsContentBlock(cmsContentBlock);
    }
    
    // CMS CONTENT ASSET
    
    public CmsContentAsset getCmsContentAssetById(final Long cmsContentAssetId, Object... params) {
        return cmsContentDao.getCmsContentAssetById(cmsContentAssetId, params);
    }

    public CmsContentAsset getCmsContentAssetById(final String rawCmsContentAssetId, Object... params) {
        long cmsContentAssetId = -1;
        try {
            cmsContentAssetId = Long.parseLong(rawCmsContentAssetId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCmsContentAssetById(cmsContentAssetId, params);
    }

    public CmsContentAsset getCmsContentAssetByCode(final String cmsContentAssetCode, Object... params) {
        return cmsContentDao.getCmsContentAssetByCode(cmsContentAssetCode, params);
    }
    
    public CmsContentAsset saveOrUpdateCmsContentAsset(CmsContentAsset cmsContentAsset) {
    	return cmsContentDao.saveOrUpdateCmsContentAsset(cmsContentAsset);
    }

    public void deleteCmsContentAsset(CmsContentAsset cmsContentAsset) {
        cmsContentDao.deleteCmsContentAsset(cmsContentAsset);
    }
    
    // FO MENU
    
    public CmsMenu getCmsMenuById(final Long menuId, Object... params) {
        return cmsContentDao.getCmsMenuById(menuId, params);
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
        return cmsContentDao.getCmsMenuByCode(menuCode, params);
    }
    
    public List<CmsMenu> findAllCmsMenus(final String app, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsMenus(app, marketAreaId, params);
    }

    public List<CmsMenu> findAllActiveRootCmsMenusByPosition(final String app, final Long marketAreaId, final String position, Object... params) {
        return cmsContentDao.findAllActiveRootCmsMenusByPosition(app, marketAreaId, position, params);
    }
    
    public List<CmsMenu> findAllCmsMenusByMarketAreaId(final String app, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsMenusByMarketAreaId(app, marketAreaId, params);
    }
    
    public CmsMenu saveOrUpdateCmsMenu(CmsMenu menu) {
    	return cmsContentDao.saveOrUpdateCmsMenu(menu);
    }

    public void deleteCmsMenu(CmsMenu menu) {
        cmsContentDao.deleteCmsMenu(menu);
    }

}