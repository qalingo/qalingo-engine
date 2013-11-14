package fr.hoteia.qalingo.core.rest.util.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.rest.pojo.BoProductCategoryJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.BoProductMarketingJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductCategoryJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductMarketingJsonPojo;
import fr.hoteia.qalingo.core.rest.util.JsonFactory;
import fr.hoteia.qalingo.core.web.service.BackofficeUrlService;

public class BoJsonFactoryImpl extends JsonFactoryImpl implements JsonFactory {
	
	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
    protected BackofficeUrlService backofficeUrlService;
	
    @Override
	public ProductCategoryJsonPojo buildJsonProductCategory(CatalogCategoryMaster productCategory) {
		final ProductCategoryJsonPojo productCategoryJsonPojo = super.buildJsonProductCategory(productCategory);
		
		// TODO : denis : refactoring
		final BoProductCategoryJsonPojo boProductCategoryJsonPojo = new BoProductCategoryJsonPojo();
		BeanUtils.copyProperties(productCategoryJsonPojo, boProductCategoryJsonPojo);
		
		try {
	        boProductCategoryJsonPojo.setAddChildCategoryUrl(backofficeUrlService.buildAddMasterProductCategoryUrl(productCategory.getCode()));
			boProductCategoryJsonPojo.setDetailsUrl(backofficeUrlService.buildProductMasterCategoryDetailsUrl(productCategory.getCode()));
			boProductCategoryJsonPojo.setEditUrl(backofficeUrlService.buildMasterProductCategoryEditUrl(productCategory.getCode()));
        } catch (Exception e) {
        	LOG.error("Failed to build URLs in JSON!", e);
        }
		return boProductCategoryJsonPojo;
	}
    
    @Override
	public ProductCategoryJsonPojo buildJsonProductCategory(CatalogCategoryVirtual productCategory, Long marketAreaId) {
		final ProductCategoryJsonPojo productCategoryJsonPojo = super.buildJsonProductCategory(productCategory, marketAreaId);
		
		// TODO : denis : refactoring
		final BoProductCategoryJsonPojo boProductCategoryJsonPojo = new BoProductCategoryJsonPojo();
		BeanUtils.copyProperties(productCategoryJsonPojo, boProductCategoryJsonPojo);
		
		try {
			boProductCategoryJsonPojo.setAddChildCategoryUrl(backofficeUrlService.buildAddVirtualProductCategoryUrl(productCategory.getCode()));
			boProductCategoryJsonPojo.setDetailsUrl(backofficeUrlService.buildProductVirtualCategoryDetailsUrl(productCategory.getCode()));
			boProductCategoryJsonPojo.setEditUrl(backofficeUrlService.buildVirtualProductCategoryEditUrl(productCategory.getCode()));
        } catch (Exception e) {
        	LOG.error("Failed to build URLs in JSON!", e);
        }
		return boProductCategoryJsonPojo;
	}
		
	@Override
	public ProductMarketingJsonPojo buildJsonProductMarketing(ProductMarketing productMarketing) {
		final ProductMarketingJsonPojo productMarketingJsonPojo = super.buildJsonProductMarketing(productMarketing);

		// TODO : denis : refactoring
		final BoProductMarketingJsonPojo boProductMarketingJsonPojo = new BoProductMarketingJsonPojo();
		BeanUtils.copyProperties(productMarketingJsonPojo, boProductMarketingJsonPojo);

		try {
			boProductMarketingJsonPojo.setDetailsUrl(backofficeUrlService.buildProductMarketingDetailsUrl(productMarketing.getCode()));
			boProductMarketingJsonPojo.setEditUrl(backofficeUrlService.buildProductMarketingEditUrl(productMarketing.getCode()));
        } catch (Exception e) {
        	LOG.error("Failed to build URLs in JSON!", e);
        }
		return boProductMarketingJsonPojo;
	}
	
	public void setBackofficeUrlService(BackofficeUrlService backofficeUrlService) {
		this.backofficeUrlService = backofficeUrlService;
	}
}
