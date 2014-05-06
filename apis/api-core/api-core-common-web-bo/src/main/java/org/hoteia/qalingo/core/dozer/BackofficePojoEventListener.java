/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dozer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.catalog.BoCatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.BoCatalogPojo;
import org.hoteia.qalingo.core.pojo.product.BoProductMarketingPojo;
import org.hoteia.qalingo.core.pojo.product.BoProductSkuPojo;
import org.hoteia.qalingo.core.service.BackofficeUrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "backofficePojoEventListener")
public class BackofficePojoEventListener implements DozerEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired 
    private HttpServletRequest httpServletRequest;
    
    @Autowired
    protected RequestUtil requestUtil;
    
    @Autowired 
    protected BackofficeUrlService backofficeUrlService;
    
    public BackofficePojoEventListener() {
    }

    @Override
    public void mappingStarted(DozerEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preWritingDestinationValue(DozerEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postWritingDestinationValue(DozerEvent event) {
        if(event.getDestinationObject() instanceof BoCatalogPojo){
            if(event.getFieldMap().getDestFieldName().equals("id")){
                // INJECT BACKOFFICE URLS
                BoCatalogPojo catalogPojo = (BoCatalogPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    
                    if(event.getSourceObject() instanceof CatalogMaster){
                        catalogPojo.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData));
                        
                    } else if(event.getSourceObject() instanceof CatalogVirtual){
                        catalogPojo.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_ADD, requestData));
                        
                    }
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoCatalogPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof BoCatalogCategoryPojo){
            if(event.getFieldMap().getDestFieldName().equals("id")){
                // INJECT BACKOFFICE URLS
                BoCatalogCategoryPojo catalogCategoryPojo = (BoCatalogCategoryPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    
                    Map<String, String> getParams = new HashMap<String, String>();
                    if(event.getSourceObject() instanceof CatalogCategoryMaster){
                        catalogCategoryPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, (CatalogCategoryMaster) event.getSourceObject()));
                        catalogCategoryPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_EDIT, requestData, (CatalogCategoryMaster) event.getSourceObject()));
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PARENT_CATALOG_CATEGORY_CODE, ((CatalogCategoryMaster) event.getSourceObject()).getCode());
                        catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData, getParams));
                        
                    } else if(event.getSourceObject() instanceof CatalogCategoryVirtual){
                        catalogCategoryPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_DETAILS, requestData, (CatalogCategoryVirtual) event.getSourceObject()));
                        catalogCategoryPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_EDIT, requestData, (CatalogCategoryVirtual) event.getSourceObject()));
                        getParams.put(RequestConstants.REQUEST_PARAMETER_PARENT_CATALOG_CATEGORY_CODE, ((CatalogCategoryVirtual) event.getSourceObject()).getCode());
                        catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.generateUrl(BoUrls.VIRTUAL_CATEGORY_ADD, requestData, getParams));
                        
                    }
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoCatalogCategoryPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof BoProductMarketingPojo){
            if(event.getFieldMap().getDestFieldName().equals("id")){
                
                // INJECT BACKOFFICE URLS
                BoProductMarketingPojo productMarketingPojo = (BoProductMarketingPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    productMarketingPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, (ProductMarketing) event.getSourceObject()));
                    productMarketingPojo.setAddUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_ADD, requestData));
                    productMarketingPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_EDIT, requestData, (ProductMarketing) event.getSourceObject()));
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoProductMarketingPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof BoProductSkuPojo){
            if(event.getFieldMap().getDestFieldName().equals("id")){
                // INJECT BACKOFFICE URLS
                BoProductSkuPojo productSkuPojo = (BoProductSkuPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    productSkuPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, (ProductSku) event.getSourceObject()));
                    productSkuPojo.setAddUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_ADD, requestData));
                    productSkuPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_EDIT, requestData, (ProductSku) event.getSourceObject()));
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoProductSkuPojo", e);
                }
            }
        }
    }

    @Override
    public void mappingFinished(DozerEvent event) {
        // TODO Auto-generated method stub
    }
    
}