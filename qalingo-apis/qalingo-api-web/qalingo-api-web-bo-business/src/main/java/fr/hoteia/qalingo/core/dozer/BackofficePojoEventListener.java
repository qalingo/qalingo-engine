package fr.hoteia.qalingo.core.dozer;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;

import fr.hoteia.qalingo.core.pojo.catalog.BoCatalogCategoryPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import fr.hoteia.qalingo.core.pojo.product.BoProductMarketingPojo;
import fr.hoteia.qalingo.core.pojo.product.BoProductSkuPojo;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;

public class BackofficePojoEventListener implements DozerEventListener {

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
        if(event.getDestinationObject() instanceof CatalogPojo){
            // NOTHING
        } else if(event.getDestinationObject() instanceof BoCatalogCategoryPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                BoCatalogCategoryPojo catalogCategoryPojo = (BoCatalogCategoryPojo) event.getDestinationObject();
                try {
                    catalogCategoryPojo.setAddChildCategoryUrl(backofficeUrlService.buildAddMasterProductCategoryUrl(catalogCategoryPojo.getCode()));
                    catalogCategoryPojo.setDetailsUrl(backofficeUrlService.buildProductMasterCategoryDetailsUrl(catalogCategoryPojo.getCode()));
                    catalogCategoryPojo.setEditUrl(backofficeUrlService.buildMasterProductCategoryEditUrl(catalogCategoryPojo.getCode()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if(event.getDestinationObject() instanceof BoProductMarketingPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                BoProductMarketingPojo productMarketingPojo = (BoProductMarketingPojo) event.getDestinationObject();
                try {
                    productMarketingPojo.setDetailsUrl(backofficeUrlService.buildProductMarketingDetailsUrl(productMarketingPojo.getCode()));
                    productMarketingPojo.setEditUrl(backofficeUrlService.buildProductMarketingEditUrl(productMarketingPojo.getCode()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if(event.getDestinationObject() instanceof BoProductSkuPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                BoProductSkuPojo productSkuPojo = (BoProductSkuPojo) event.getDestinationObject();
                try {
                    productSkuPojo.setDetailsUrl(backofficeUrlService.buildProductSkuDetailsUrl(productSkuPojo.getCode()));
                    productSkuPojo.setEditUrl(backofficeUrlService.buildProductSkuEditUrl(productSkuPojo.getCode()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mappingFinished(DozerEvent event) {
        // TODO Auto-generated method stub
    }

    public void setBackofficeUrlService(BackofficeUrlService backofficeUrlService) {
        this.backofficeUrlService = backofficeUrlService;
    }

}