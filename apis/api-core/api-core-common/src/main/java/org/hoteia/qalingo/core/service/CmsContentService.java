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

import java.io.File;
import java.net.URI;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hoteia.qalingo.core.dao.CmsContentDao;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.hoteia.qalingo.core.domain.CmsContentAsset;
import org.hoteia.qalingo.core.domain.CmsContentAttribute;
import org.hoteia.qalingo.core.domain.CmsContentBlock;
import org.hoteia.qalingo.core.domain.CmsContentBlockAttribute;
import org.hoteia.qalingo.core.domain.CmsLink;
import org.hoteia.qalingo.core.domain.CmsMenu;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cmsContentService")
@Transactional
public class CmsContentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired
    private CmsContentDao cmsContentDao;

    public String buildPageCode(final MarketArea marketArea, final Localization localization, final CmsContent cmsContent, Object... params) {
        return buildCmsContentCode(marketArea, localization, cmsContent.getSeoKey(), params);
    }
    
    public String buildPageCode(final MarketArea marketArea, final Localization localization, final String cmsContentSeoKey, Object... params) {
        return buildCmsContentCode(marketArea, localization, cmsContentSeoKey, params);
    }

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

    public List<Long> findAllCmsContentIds(final String app, final String type, Object... params) {
        return cmsContentDao.findAllCmsContentIds(type, params);
    }

    public List<Long> findAllCmsContentIds(final String app, final String type, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsContentIds(type, marketAreaId, params);
    }
    
    public List<Long> findAllCmsContentIds(final String app, final String type, final Long marketAreaId, final Long localizationId, Object... params) {
        return cmsContentDao.findAllCmsContentIds(type, marketAreaId, localizationId, params);
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
    
    public List<Long> findAllCmsContentIdsBySeoKey(final String app, final String type, final String cmsContentSeoKey, Object... params) {
        return cmsContentDao.findAllCmsContentIdsBySeoKey(app, type, cmsContentSeoKey, params);
    }
    
    public List<CmsContent> findAllCmsContentsBySeoKey(final String app, final String type, final String cmsContentSeoKey, Object... params) {
        return cmsContentDao.findAllCmsContentsBySeoKey(app, type, cmsContentSeoKey, params);
    }
    
    public List<CmsContent> findAllCmsContents(final String app, final String type, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsContents(type, marketAreaId, params);
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
    
    public List<CmsContent> findCmsContentsByProductSkuId(final String app, final String type, final Long marketAreaId, final Long localizationId, Long productSkuId, int maxResults, Object... params) {
        return cmsContentDao.findCmsContentsByProductSkuId(app, type, marketAreaId, localizationId, productSkuId, maxResults, params);
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
    
    // CMS MENU
    
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
    
    public List<CmsMenu> findAllCmsMenus(final String app, Object... params) {
        return cmsContentDao.findAllCmsMenus(app, params);
    }
    
    public List<Long> findAllCmsMenuIds(final String app, Object... params) {
        return cmsContentDao.findAllCmsMenuIds(app, params);
    }
    
    public List<CmsMenu> findAllCmsMenus(final String app, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsMenus(app, marketAreaId, params);
    }

    public List<Long> findAllCmsMenuIds(final String app, final Long marketAreaId, Object... params) {
        return cmsContentDao.findAllCmsMenuIds(app, marketAreaId, params);
    }
    
    public List<CmsMenu> findAllActiveRootCmsMenusByPosition(final String app, final Long marketAreaId, final String position, Object... params) {
        return cmsContentDao.findAllActiveRootCmsMenusByPosition(app, marketAreaId, position, params);
    }
    
    public CmsMenu saveOrUpdateCmsMenu(CmsMenu menu) {
    	return cmsContentDao.saveOrUpdateCmsMenu(menu);
    }

    public void deleteCmsMenu(CmsMenu menu) {
        cmsContentDao.deleteCmsMenu(menu);
    }
    
    public void duplicateCmsContent(CmsContent sourceCmsContent, CmsContent targetCmsContent, MarketArea targetMarketArea, Localization targetLocalization){
        targetCmsContent.setCode(buildArticleCode(targetMarketArea, targetLocalization, sourceCmsContent));
        targetCmsContent.setApp(sourceCmsContent.getApp());
        targetCmsContent.setType(sourceCmsContent.getType());
        targetCmsContent.setTitle(sourceCmsContent.getTitle());
        targetCmsContent.setLinkTitle(sourceCmsContent.getLinkTitle());
        targetCmsContent.setSeoSegment(sourceCmsContent.getSeoSegment());
        targetCmsContent.setSeoKey(sourceCmsContent.getSeoKey());
        targetCmsContent.setSummary(sourceCmsContent.getSummary());
        targetCmsContent.setActive(sourceCmsContent.isActive());

        targetCmsContent.setUser(sourceCmsContent.getUser());
        targetCmsContent.setMarketArea(targetMarketArea);
        targetCmsContent.setLocalization(targetLocalization);

        targetCmsContent.setDatePublish(sourceCmsContent.getDatePublish());
        
        for (CmsContentAttribute sourceCmsContentAttribute : sourceCmsContent.getAttributes()) {
            CmsContentAttribute targetCmsContentAttribute = new CmsContentAttribute();
            targetCmsContentAttribute.setAttributeDefinition(sourceCmsContentAttribute.getAttributeDefinition());
            targetCmsContentAttribute.setShortStringValue(sourceCmsContentAttribute.getShortStringValue());
            targetCmsContentAttribute.setLongStringValue(sourceCmsContentAttribute.getLongStringValue());
            targetCmsContentAttribute.setIntegerValue(sourceCmsContentAttribute.getIntegerValue());
            targetCmsContentAttribute.setDoubleValue(sourceCmsContentAttribute.getDoubleValue());
            targetCmsContentAttribute.setFloatValue(sourceCmsContentAttribute.getFloatValue());
            targetCmsContentAttribute.setBlobValue(sourceCmsContentAttribute.getBlobValue());
            targetCmsContentAttribute.setBooleanValue(sourceCmsContentAttribute.getBooleanValue());
            targetCmsContentAttribute.setDateValue(sourceCmsContentAttribute.getDateValue());
            targetCmsContentAttribute.setLocalizationCode(sourceCmsContentAttribute.getLocalizationCode());
            targetCmsContentAttribute.setMarketAreaId(sourceCmsContentAttribute.getMarketAreaId());
            targetCmsContentAttribute.setStartDate(sourceCmsContentAttribute.getStartDate());
            targetCmsContentAttribute.setEndDate(sourceCmsContentAttribute.getEndDate());
            targetCmsContent.getAttributes().add(targetCmsContentAttribute);
        }
        for (CmsContentAsset sourceCmsContentAsset : sourceCmsContent.getAssets()) {
            CmsContentAsset targetCmsContentAsset = new CmsContentAsset();
            targetCmsContentAsset.setName(sourceCmsContentAsset.getName());
            targetCmsContentAsset.setDescription(sourceCmsContentAsset.getDescription());
            targetCmsContentAsset.setPath(sourceCmsContentAsset.getPath());
            targetCmsContentAsset.setScope(sourceCmsContentAsset.getScope());
            targetCmsContentAsset.setType(sourceCmsContentAsset.getType());
            targetCmsContentAsset.setSize(sourceCmsContentAsset.getSize());
            targetCmsContentAsset.setFileSize(sourceCmsContentAsset.getFileSize());
            targetCmsContentAsset.setDefault(sourceCmsContentAsset.isDefault());
            targetCmsContentAsset.setGlobal(sourceCmsContentAsset.isGlobal());
            targetCmsContentAsset.setOrdering(sourceCmsContentAsset.getOrdering());
            targetCmsContent.getAssets().add(targetCmsContentAsset);
            
            duplicateAssetCms(sourceCmsContent, null, targetCmsContent, null, sourceCmsContentAsset);
        }
        for (CmsContentBlock sourceCmsContentBlock : sourceCmsContent.getBlocks()) {
            CmsContentBlock targetCmsContentBlock = new CmsContentBlock();
            targetCmsContentBlock.setCode(buildArticleCode(targetMarketArea, targetLocalization, targetCmsContent) + "_" + sourceCmsContentBlock.getOrdering());
            targetCmsContentBlock.setActive(sourceCmsContentBlock.isActive());
            targetCmsContentBlock.setTitle(sourceCmsContentBlock.getTitle());
            targetCmsContentBlock.setText(sourceCmsContentBlock.getText());
            if (sourceCmsContentBlock.getLink() != null) {
                CmsLink sourceCmsLink = sourceCmsContentBlock.getLink();
                CmsLink targetCmsLink = new CmsLink();
                targetCmsLink.setName(sourceCmsLink.getName());
                targetCmsLink.setAlt(sourceCmsLink.getAlt());
                targetCmsLink.setType(sourceCmsLink.getType());
                targetCmsLink.setParams(sourceCmsLink.getParams());
                targetCmsLink.setExternal(sourceCmsLink.isExternal());
                targetCmsLink.setFullURlPath(sourceCmsLink.getFullURlPath());
                targetCmsContentBlock.setLink(targetCmsLink);
            }
            targetCmsContentBlock.setType(sourceCmsContentBlock.getType());
            targetCmsContentBlock.setParams(sourceCmsContentBlock.getParams());
            targetCmsContentBlock.setMarketArea(targetMarketArea);
            targetCmsContentBlock.setCmsContent(targetCmsContent);
            targetCmsContentBlock.setOrdering(sourceCmsContentBlock.getOrdering());
                
            for (CmsContentBlockAttribute sourceCmsContentBlockAttribute : sourceCmsContentBlock.getAttributes()) {
                CmsContentBlockAttribute targetCmsContentBlockAttribute = new CmsContentBlockAttribute();
                targetCmsContentBlockAttribute.setAttributeDefinition(sourceCmsContentBlockAttribute.getAttributeDefinition());
                targetCmsContentBlockAttribute.setShortStringValue(sourceCmsContentBlockAttribute.getShortStringValue());
                targetCmsContentBlockAttribute.setLongStringValue(sourceCmsContentBlockAttribute.getLongStringValue());
                targetCmsContentBlockAttribute.setIntegerValue(sourceCmsContentBlockAttribute.getIntegerValue());
                targetCmsContentBlockAttribute.setDoubleValue(sourceCmsContentBlockAttribute.getDoubleValue());
                targetCmsContentBlockAttribute.setFloatValue(sourceCmsContentBlockAttribute.getFloatValue());
                targetCmsContentBlockAttribute.setBlobValue(sourceCmsContentBlockAttribute.getBlobValue());
                targetCmsContentBlockAttribute.setBooleanValue(sourceCmsContentBlockAttribute.getBooleanValue());
                targetCmsContentBlockAttribute.setDateValue(sourceCmsContentBlockAttribute.getDateValue());
                targetCmsContentBlockAttribute.setLocalizationCode(sourceCmsContentBlockAttribute.getLocalizationCode());
                targetCmsContentBlockAttribute.setMarketAreaId(sourceCmsContentBlockAttribute.getMarketAreaId());
                targetCmsContentBlockAttribute.setStartDate(sourceCmsContentBlockAttribute.getStartDate());
                targetCmsContentBlockAttribute.setEndDate(sourceCmsContentBlockAttribute.getEndDate());
                targetCmsContentBlock.getAttributes().add(targetCmsContentBlockAttribute);
            }
            for (CmsContentAsset sourceCmsContentBlockAsset : sourceCmsContentBlock.getAssets()) {
                CmsContentAsset targetCmsContentAsset = new CmsContentAsset();
                targetCmsContentAsset.setName(sourceCmsContentBlockAsset.getName());
                targetCmsContentAsset.setDescription(sourceCmsContentBlockAsset.getDescription());
                targetCmsContentAsset.setPath(sourceCmsContentBlockAsset.getPath());
                targetCmsContentAsset.setScope(sourceCmsContentBlockAsset.getScope());
                targetCmsContentAsset.setType(sourceCmsContentBlockAsset.getType());
                targetCmsContentAsset.setSize(sourceCmsContentBlockAsset.getSize());
                targetCmsContentAsset.setFileSize(sourceCmsContentBlockAsset.getFileSize());
                targetCmsContentAsset.setDefault(sourceCmsContentBlockAsset.isDefault());
                targetCmsContentAsset.setGlobal(sourceCmsContentBlockAsset.isGlobal());
                targetCmsContentAsset.setOrdering(sourceCmsContentBlockAsset.getOrdering());
                targetCmsContentBlock.getAssets().add(targetCmsContentAsset);
                
                duplicateAssetCms(sourceCmsContent, sourceCmsContentBlock, targetCmsContent, targetCmsContentBlock, sourceCmsContentBlockAsset);
            }
            
            for (CmsContentBlock sourceSubCmsContentBlock : sourceCmsContentBlock.getBlocks()) {
                CmsContentBlock targetSubCmsContentBlock = new CmsContentBlock();
                targetSubCmsContentBlock.setCode(buildArticleCode(targetMarketArea, targetLocalization, targetCmsContent) + "_" + sourceSubCmsContentBlock.getOrdering());
                targetSubCmsContentBlock.setActive(sourceSubCmsContentBlock.isActive());
                targetSubCmsContentBlock.setTitle(sourceSubCmsContentBlock.getTitle());
                targetSubCmsContentBlock.setText(sourceSubCmsContentBlock.getText());
                if (sourceSubCmsContentBlock.getLink() != null) {
                    CmsLink cmsLink = sourceSubCmsContentBlock.getLink();
                    CmsLink targetCmsLink = new CmsLink();
                    targetCmsLink.setName(cmsLink.getName());
                    targetCmsLink.setAlt(cmsLink.getAlt());
                    targetCmsLink.setType(cmsLink.getType());
                    targetCmsLink.setParams(cmsLink.getParams());
                    targetCmsLink.setExternal(cmsLink.isExternal());
                    targetCmsLink.setFullURlPath(cmsLink.getFullURlPath());
                    targetSubCmsContentBlock.setLink(targetCmsLink);
                }
                targetSubCmsContentBlock.setType(sourceSubCmsContentBlock.getType());
                targetSubCmsContentBlock.setParams(sourceSubCmsContentBlock.getParams());
                targetSubCmsContentBlock.setMarketArea(targetMarketArea);
                targetSubCmsContentBlock.setCmsContentBlock(targetCmsContentBlock);
                targetSubCmsContentBlock.setOrdering(sourceSubCmsContentBlock.getOrdering());
                    
                for (CmsContentBlockAttribute sourceSubCmsContentBlockAttribute : sourceSubCmsContentBlock.getAttributes()) {
                    CmsContentBlockAttribute targetCmsContentBlockAttribute = new CmsContentBlockAttribute();
                    targetCmsContentBlockAttribute.setAttributeDefinition(sourceSubCmsContentBlockAttribute.getAttributeDefinition());
                    targetCmsContentBlockAttribute.setShortStringValue(sourceSubCmsContentBlockAttribute.getShortStringValue());
                    targetCmsContentBlockAttribute.setLongStringValue(sourceSubCmsContentBlockAttribute.getLongStringValue());
                    targetCmsContentBlockAttribute.setIntegerValue(sourceSubCmsContentBlockAttribute.getIntegerValue());
                    targetCmsContentBlockAttribute.setDoubleValue(sourceSubCmsContentBlockAttribute.getDoubleValue());
                    targetCmsContentBlockAttribute.setFloatValue(sourceSubCmsContentBlockAttribute.getFloatValue());
                    targetCmsContentBlockAttribute.setBlobValue(sourceSubCmsContentBlockAttribute.getBlobValue());
                    targetCmsContentBlockAttribute.setBooleanValue(sourceSubCmsContentBlockAttribute.getBooleanValue());
                    targetCmsContentBlockAttribute.setDateValue(sourceSubCmsContentBlockAttribute.getDateValue());
                    targetCmsContentBlockAttribute.setLocalizationCode(sourceSubCmsContentBlockAttribute.getLocalizationCode());
                    targetCmsContentBlockAttribute.setMarketAreaId(sourceSubCmsContentBlockAttribute.getMarketAreaId());
                    targetCmsContentBlockAttribute.setStartDate(sourceSubCmsContentBlockAttribute.getStartDate());
                    targetCmsContentBlockAttribute.setEndDate(sourceSubCmsContentBlockAttribute.getEndDate());
                    targetSubCmsContentBlock.getAttributes().add(targetCmsContentBlockAttribute);
                }
                for (CmsContentAsset sourceSubCmsContentBlockAsset : sourceSubCmsContentBlock.getAssets()) {
                    CmsContentAsset targetSubCmsContentAsset = new CmsContentAsset();
                    targetSubCmsContentAsset.setName(sourceSubCmsContentBlockAsset.getName());
                    targetSubCmsContentAsset.setDescription(sourceSubCmsContentBlockAsset.getDescription());
                    targetSubCmsContentAsset.setPath(sourceSubCmsContentBlockAsset.getPath());
                    targetSubCmsContentAsset.setScope(sourceSubCmsContentBlockAsset.getScope());
                    targetSubCmsContentAsset.setType(sourceSubCmsContentBlockAsset.getType());
                    targetSubCmsContentAsset.setSize(sourceSubCmsContentBlockAsset.getSize());
                    targetSubCmsContentAsset.setFileSize(sourceSubCmsContentBlockAsset.getFileSize());
                    targetSubCmsContentAsset.setDefault(sourceSubCmsContentBlockAsset.isDefault());
                    targetSubCmsContentAsset.setGlobal(sourceSubCmsContentBlockAsset.isGlobal());
                    targetSubCmsContentAsset.setOrdering(sourceSubCmsContentBlockAsset.getOrdering());
                    targetSubCmsContentBlock.getAssets().add(targetSubCmsContentAsset);
                    
                    duplicateAssetCms(sourceCmsContent, sourceCmsContentBlock, targetCmsContent, targetCmsContentBlock, sourceSubCmsContentBlockAsset);
                }
                targetCmsContentBlock.getBlocks().add(targetSubCmsContentBlock);
            }
            targetCmsContent.getBlocks().add(targetCmsContentBlock);
        }
    }
        
    public void duplicateAssetCms(CmsContent sourceCmsContent, CmsContentBlock sourceCmsContentBlock, CmsContent targetCmsContent, CmsContentBlock targetCmsContentBlock, CmsContentAsset asset){
        String assetFileRootPath = engineSettingService.getSettingAssetFileRootPath().getDefaultValue();
        assetFileRootPath.replaceAll("\\\\", "/");
        if(assetFileRootPath.endsWith("/")){
            assetFileRootPath = assetFileRootPath.substring(0, assetFileRootPath.length() - 1);
        }
        String assetArticleFilePath = engineSettingService.getSettingAssetCmsContentFilePath().getDefaultValue();
        assetArticleFilePath.replaceAll("\\\\", "/");
        if(assetArticleFilePath.endsWith("/")){
            assetArticleFilePath = assetArticleFilePath.substring(0, assetArticleFilePath.length() - 1);
        }
        if(!assetArticleFilePath.startsWith("/")){
            assetArticleFilePath = "/" + assetArticleFilePath;
        }

        String absoluteTargetFolderPath = assetFileRootPath + assetArticleFilePath + "/" + targetCmsContent.getMarketArea().getName().toLowerCase() + "/" + targetCmsContent.getType().toLowerCase() + "/"  + targetCmsContent.getCode().toLowerCase();
        if(targetCmsContentBlock != null){
            if(targetCmsContentBlock.getCmsContentBlock() != null){
                absoluteTargetFolderPath += "/" + targetCmsContentBlock.getCmsContentBlock().getType().toLowerCase() + "/" + targetCmsContentBlock.getType().toLowerCase();
            } else {
                absoluteTargetFolderPath += "/" + targetCmsContentBlock.getType().toLowerCase();
            }
        }
        absoluteTargetFolderPath = absoluteTargetFolderPath + "/" + asset.getType().toLowerCase() + "/";
        absoluteTargetFolderPath = absoluteTargetFolderPath.replace("_", "-").replace(" ", "-");
        String absoluteTargetFilePath = absoluteTargetFolderPath + asset.getPath();

        
        String absoluteOriginFolderPath = assetFileRootPath + assetArticleFilePath + "/" + sourceCmsContent.getMarketArea().getName().toLowerCase() + "/" + sourceCmsContent.getType().toLowerCase() + "/"  + sourceCmsContent.getCode().toLowerCase();
        if(sourceCmsContentBlock != null){
            if(sourceCmsContentBlock.getCmsContentBlock() != null){
                absoluteOriginFolderPath += "/" + sourceCmsContentBlock.getCmsContentBlock().getType().toLowerCase() + "/" + sourceCmsContentBlock.getType().toLowerCase();
            } else {
                absoluteOriginFolderPath += "/" + sourceCmsContentBlock.getType().toLowerCase();
            }
        }
        absoluteOriginFolderPath = absoluteOriginFolderPath + "/" + asset.getType().toLowerCase() + "/";
        absoluteOriginFolderPath = absoluteOriginFolderPath.replace("_", "-").replace(" ", "-");
        String absoluteOriginFilePath = absoluteOriginFolderPath + asset.getPath();
        
        duplicateAssetFile(absoluteOriginFilePath, absoluteTargetFolderPath, absoluteTargetFilePath);
        
        // TEMP : duplicate also the .source file
        absoluteOriginFilePath = absoluteOriginFilePath + ".source";
        absoluteTargetFilePath = absoluteTargetFilePath + ".source";
        duplicateAssetFile(absoluteOriginFilePath, absoluteTargetFolderPath, absoluteTargetFilePath);
    }
    
    protected void duplicateAssetFile(String absoluteOriginFilePath, String absoluteTargetFolderPath, String absoluteTargetFilePath){
        try {
            File fileFolder = new File(new URI(absoluteTargetFolderPath));
            if(!fileFolder.exists()){
                FileUtils.forceMkdir(fileFolder);
            }
            FileUtils.copyFile(new File(new URI(absoluteOriginFilePath)), new File(new URI(absoluteTargetFilePath)));
            
        } catch(IllegalArgumentException e) {
            try {
                File fileFolder = new File(absoluteTargetFolderPath);
                if(!fileFolder.exists()){
                    FileUtils.forceMkdir(fileFolder);
                }
                FileUtils.copyFile(new File(absoluteOriginFilePath), new File(absoluteTargetFilePath));
                
            } catch(Exception e2) {
                logger.error(e2.getMessage());
            }
            
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }

}