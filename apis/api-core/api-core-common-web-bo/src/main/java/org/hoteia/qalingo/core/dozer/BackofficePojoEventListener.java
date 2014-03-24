package org.hoteia.qalingo.core.dozer;

import javax.servlet.http.HttpServletRequest;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
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
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                BoCatalogPojo catalogPojo = (BoCatalogPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    
                    if(event.getSourceObject() instanceof CatalogMaster){
                        catalogPojo.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData));
                        
                    } else if(event.getSourceObject() instanceof CatalogVirtual){
                        catalogPojo.setAddRootCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData));
                        
                    }
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoCatalogPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof BoCatalogCategoryPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                BoCatalogCategoryPojo catalogCategoryPojo = (BoCatalogCategoryPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    
                    if(event.getSourceObject() instanceof CatalogCategoryMaster){
                        catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, (CatalogCategoryMaster) event.getSourceObject()));
                        catalogCategoryPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_EDIT, requestData, (CatalogCategoryMaster) event.getSourceObject()));
                        catalogCategoryPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData, (CatalogCategoryMaster) event.getSourceObject()));
                        
                    } else if(event.getSourceObject() instanceof CatalogCategoryVirtual){
                        catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_DETAILS, requestData, (CatalogCategoryVirtual) event.getSourceObject()));
                        catalogCategoryPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_EDIT, requestData, (CatalogCategoryVirtual) event.getSourceObject()));
                        catalogCategoryPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.MASTER_CATEGORY_ADD, requestData, (CatalogCategoryVirtual) event.getSourceObject()));
                        
                    }
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoCatalogCategoryPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof BoProductMarketingPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                
                // INJECT BACKOFFICE URLS
                BoProductMarketingPojo productMarketingPojo = (BoProductMarketingPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    productMarketingPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, (ProductMarketing) event.getSourceObject()));
                    productMarketingPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_MARKETING_DETAILS, requestData, (ProductMarketing) event.getSourceObject()));
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with BoProductMarketingPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof BoProductSkuPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                BoProductSkuPojo productSkuPojo = (BoProductSkuPojo) event.getDestinationObject();
                try {
                    RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    productSkuPojo.setDetailsUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, (ProductSku) event.getSourceObject()));
                    productSkuPojo.setEditUrl(backofficeUrlService.generateUrl(BoUrls.PRODUCT_SKU_DETAILS, requestData, (ProductSku) event.getSourceObject()));
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