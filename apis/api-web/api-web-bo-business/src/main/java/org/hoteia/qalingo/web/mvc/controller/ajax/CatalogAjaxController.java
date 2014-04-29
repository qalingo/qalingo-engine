/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.ajax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster_;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual_;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphCategory;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.catalog.BoCatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoFactory;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 */
@Controller("catalogAjaxController")
public class CatalogAjaxController extends AbstractBusinessBackofficeController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CatalogService catalogService;

    @Autowired
    private CatalogPojoFactory catalogPojoService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    protected List<SpecificFetchMode> categoryMasterFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> categoryVirtualFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();
    
    public CatalogAjaxController() {
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.catalogCategories.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.parentCatalogCategory.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.attributes.getName()));
        categoryMasterFetchPlans.add(new SpecificFetchMode(CatalogCategoryMaster_.catalogCategoryType.getName()));
        
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategories.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.attributes.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.parentCatalogCategory.getName() + "." + CatalogCategoryVirtual_.categoryMaster.getName()));
        categoryVirtualFetchPlans.add(new SpecificFetchMode(CatalogCategoryVirtual_.categoryMaster.getName() + "." + CatalogCategoryMaster_.catalogCategoryType.getName()));
        
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
    }
    
    @RequestMapping(value = BoUrls.GET_CATALOG_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public CatalogPojo getCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final String catalogType = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_TYPE);

        CatalogPojo catalogPojo = new CatalogPojo();
        if("master".equals(catalogType)){
            final CatalogMaster catalogMaster = catalogService.getMasterCatalogById(currentMarketArea.getCatalog().getCatalogMaster().getId());

            Set<CatalogCategoryMaster> catalogCategories = new HashSet<CatalogCategoryMaster>();
            if(catalogMaster.getCatalogCategories() != null){
                for (Iterator<CatalogCategoryMaster> iterator = catalogMaster.getCatalogCategories().iterator(); iterator.hasNext();) {
                    CatalogCategoryMaster categoryMaster = (CatalogCategoryMaster) iterator.next();
                    CatalogCategoryMaster reloadedCategoryMaster = catalogCategoryService.getMasterCatalogCategoryById(categoryMaster.getId(), new FetchPlan(categoryMasterFetchPlans));
                    Set<CatalogCategoryMaster> reloadedSubCategories = new HashSet<CatalogCategoryMaster>();
                    if(reloadedCategoryMaster.getSortedChildCatalogCategories() != null){
                        for (Iterator<CatalogCategoryMaster> iteratorSubCategories = reloadedCategoryMaster.getSortedChildCatalogCategories().iterator(); iteratorSubCategories.hasNext();) {
                            CatalogCategoryMaster subCategory = (CatalogCategoryMaster) iteratorSubCategories.next();
                            CatalogCategoryMaster reloadedSubCategory = catalogCategoryService.getMasterCatalogCategoryById(subCategory.getId(), new FetchPlan(categoryMasterFetchPlans));
                            reloadedSubCategories.add(reloadedSubCategory);
                        }
                    }
                    reloadedCategoryMaster.setCatalogCategories(reloadedSubCategories);
                    catalogCategories.add(reloadedCategoryMaster);
                }
            }
            catalogMaster.setCatalogCategories(new HashSet<CatalogCategoryMaster>(catalogCategories));
            
            try {
                catalogPojo = (CatalogPojo) catalogPojoService.getMasterCatalog(catalogMaster);
            } catch (Exception e) {
                logger.error("", e);
            }
            
        } else if("virtual".equals(catalogType)){
            final CatalogVirtual catalogVirtual = catalogService.getVirtualCatalogById(currentMarketArea.getCatalog().getId());

            Set<CatalogCategoryVirtual> catalogCategories = new HashSet<CatalogCategoryVirtual>();
            if(catalogVirtual.getCatalogCategories() != null){
                for (Iterator<CatalogCategoryVirtual> iterator = catalogVirtual.getCatalogCategories().iterator(); iterator.hasNext();) {
                    CatalogCategoryVirtual categoryVirtual = (CatalogCategoryVirtual) iterator.next();
                    CatalogCategoryVirtual reloadedCategoryVirtual = catalogCategoryService.getVirtualCatalogCategoryById(categoryVirtual.getId(), new FetchPlan(categoryVirtualFetchPlans));
                    Set<CatalogCategoryVirtual> reloadedSubCategories = new HashSet<CatalogCategoryVirtual>();
                    if(reloadedCategoryVirtual.getSortedChildCatalogCategories() != null){
                        for (Iterator<CatalogCategoryVirtual> iteratorSubCategories = reloadedCategoryVirtual.getSortedChildCatalogCategories().iterator(); iteratorSubCategories.hasNext();) {
                            CatalogCategoryVirtual subCategory = (CatalogCategoryVirtual) iteratorSubCategories.next();
                            CatalogCategoryVirtual reloadedSubCategory = catalogCategoryService.getVirtualCatalogCategoryById(subCategory.getId(), new FetchPlan(categoryVirtualFetchPlans));
                            reloadedSubCategories.add(reloadedSubCategory);
                        }
                    }
                    reloadedCategoryVirtual.setCatalogCategories(reloadedSubCategories);
                    catalogCategories.add(reloadedCategoryVirtual);
                }
            }
            catalogVirtual.setCatalogCategories(new HashSet<CatalogCategoryVirtual>(catalogCategories));
            
            try {
                catalogPojo = (CatalogPojo) catalogPojoService.getVirtualCatalog(catalogVirtual);
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        return catalogPojo;
    }
    
    @RequestMapping(value = BoUrls.GET_PRODUCT_LIST_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public BoCatalogCategoryPojo getCategory(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String categoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        final String catalogType = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_TYPE);

        BoCatalogCategoryPojo catalogCategoryPojo = new BoCatalogCategoryPojo();
        if("master".equals(catalogType)){
            FetchPlan fetchPlanWithProducts = new FetchPlan(categoryMasterFetchPlans);
            fetchPlanWithProducts.getFetchModes().add(new SpecificFetchMode(CatalogCategoryVirtual_.catalogCategoryProductSkuRels.getName()));
            fetchPlanWithProducts.getFetchModes().add(new SpecificFetchMode("catalogCategoryProductSkuRels.pk.productSku"));
            fetchPlanWithProducts.getFetchModes().add(new SpecificFetchMode(CatalogCategoryVirtual_.assets.getName()));
            
            CatalogCategoryMaster reloadedCategory = catalogCategoryService.getMasterCatalogCategoryByCode(categoryCode, requestData.getMasterCatalogCode(), fetchPlanWithProducts);
            catalogCategoryPojo = (BoCatalogCategoryPojo) catalogPojoService.buildCatalogCategory(reloadedCategory);
            List<ProductMarketingPojo> relodedProductMarketings = new ArrayList<ProductMarketingPojo>();
            final List<ProductSku> productSkus = reloadedCategory.getSortedProductSkus();
            if(productSkus != null){
                for (Iterator<ProductSku> iteratorProductMarketing = productSkus.iterator(); iteratorProductMarketing.hasNext();) {
                    final ProductSku productSku = (ProductSku) iteratorProductMarketing.next();
                    final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSku.getCode());
                    final ProductMarketing productMarketing = productService.getProductMarketingByCode(reloadedProductSku.getProductMarketing().getCode());
                    relodedProductMarketings.add(catalogPojoService.buildProductMarketing(productMarketing));
                }
            }
            catalogCategoryPojo.setProductMarketings(relodedProductMarketings);
            
        } else if("virtual".equals(catalogType)){
            CatalogCategoryVirtual reloadedCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode(), FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());
            catalogCategoryPojo = (BoCatalogCategoryPojo) catalogPojoService.buildCatalogCategory(reloadedCategory);
            List<ProductMarketingPojo> relodedProductMarketings = new ArrayList<ProductMarketingPojo>();
            final List<ProductSku> productSkus = reloadedCategory.getSortedProductSkus();
            if(productSkus != null){
                for (Iterator<ProductSku> iteratorProductMarketing = productSkus.iterator(); iteratorProductMarketing.hasNext();) {
                    final ProductSku productSku = (ProductSku) iteratorProductMarketing.next();
                    final ProductSku reloadedProductSku = productService.getProductSkuByCode(productSku.getCode());
                    final ProductMarketing productMarketing = productService.getProductMarketingByCode(reloadedProductSku.getProductMarketing().getCode());
                    ProductMarketing reloadedProduct = productService.getProductMarketingById(productMarketing.getId(), new FetchPlan(productMarketingFetchPlans));
                    ProductMarketingPojo productMarketingPojo = catalogPojoService.buildProductMarketing(reloadedProduct);
                    relodedProductMarketings.add(productMarketingPojo);
                }
            }
            catalogCategoryPojo.setProductMarketings(relodedProductMarketings);
        }
        return catalogCategoryPojo;
    }

}