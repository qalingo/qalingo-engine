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
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogMaster;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphCategory;
import org.hoteia.qalingo.core.fetchplan.catalog.FetchPlanGraphProduct;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.catalog.BoCatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.CatalogService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;
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
    private CatalogPojoService catalogPojoService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @RequestMapping(value = BoUrls.GET_CATALOG_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public CatalogPojo getCatalog(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        final String catalogType = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_TYPE);

        CatalogPojo catalogPojo = new CatalogPojo();
        if("master".equals(catalogType)){
            final CatalogMaster catalogMaster = catalogService.getMasterCatalogById(currentMarketArea.getCatalog().getCatalogMaster().getId());

            Set<CatalogCategoryMaster> rootCatalogCategories = new HashSet<CatalogCategoryMaster>();
            for (Iterator<CatalogCategoryMaster> iterator = catalogMaster.getCatalogCategories().iterator(); iterator.hasNext();) {
                CatalogCategoryMaster categoryMaster = (CatalogCategoryMaster) iterator.next();
                CatalogCategoryMaster reloadedCategoryMaster = catalogCategoryService.getMasterCatalogCategoryById(categoryMaster.getId(), FetchPlanGraphCategory.masterCategoriesWithoutProductsAndAssetsFetchPlan());
                Set<CatalogCategoryMaster> catalogCategoriesReload = new HashSet<CatalogCategoryMaster>();
                for (Iterator<CatalogCategoryMaster> iteratorSubCategories = reloadedCategoryMaster.getCatalogCategories().iterator(); iteratorSubCategories.hasNext();) {
                    CatalogCategoryMaster subCategory = (CatalogCategoryMaster) iteratorSubCategories.next();
                    CatalogCategoryMaster reloadedSubCategory = catalogCategoryService.getMasterCatalogCategoryById(subCategory.getId(), FetchPlanGraphCategory.masterCategoriesWithoutProductsAndAssetsFetchPlan());
                    catalogCategoriesReload.add(reloadedSubCategory);
                }
                reloadedCategoryMaster.setCatalogCategories(catalogCategoriesReload);
                rootCatalogCategories.add(reloadedCategoryMaster);
            }
            catalogMaster.setCatalogCategories(new HashSet<CatalogCategoryMaster>(rootCatalogCategories));
            
            try {
                catalogPojo = (CatalogPojo) catalogPojoService.getMasterCatalog(catalogMaster);
            } catch (Exception e) {
                logger.error("", e);
            }
            
        } else if("virtual".equals(catalogType)){
            final CatalogVirtual catalogVirtual = catalogService.getVirtualCatalogById(currentMarketArea.getCatalog().getId());

//            Set<CatalogCategoryVirtual> rootCatalogCategories = new HashSet<CatalogCategoryVirtual>();
//            for (Iterator<CatalogCategoryVirtual> iterator = catalogVirtual.getSortedCatalogCategories().iterator(); iterator.hasNext();) {
//                CatalogCategoryVirtual categoryVirtual = (CatalogCategoryVirtual) iterator.next();
//                CatalogCategoryVirtual reloadedCategoryVirtual = catalogCategoryService.getVirtualCatalogCategoryById(categoryVirtual.getId(), FetchPlanGraphCategory.virtualCategoriesWithoutProductsAndAssetsFetchPlan());
//                Set<CatalogCategoryVirtual> catalogCategoriesReload = new HashSet<CatalogCategoryVirtual>();
//                for (Iterator<CatalogCategoryVirtual> iteratorSubCategories = reloadedCategoryVirtual.getSortedChildCatalogCategories().iterator(); iteratorSubCategories.hasNext();) {
//                    CatalogCategoryVirtual subCategory = (CatalogCategoryVirtual) iteratorSubCategories.next();
//                    CatalogCategoryVirtual reloadedSubCategory = catalogCategoryService.getVirtualCatalogCategoryById(subCategory.getId(), FetchPlanGraphCategory.virtualCategoriesWithoutProductsAndAssetsFetchPlan());
//                    catalogCategoriesReload.add(reloadedSubCategory);
//                }
//                reloadedCategoryVirtual.setSortedChildCatalogCategories(catalogCategoriesReload);
//                rootCatalogCategories.add(reloadedCategoryVirtual);
//            }
//            catalogVirtual.setSortedCatalogCategories(new HashSet<CatalogCategoryVirtual>(rootCatalogCategories));
            
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
        final String categoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        final String catalogType = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_TYPE);

        BoCatalogCategoryPojo catalogCategoryPojo = new BoCatalogCategoryPojo();
        if("master".equals(catalogType)){
            CatalogCategoryMaster reloadedCategoryMaster = catalogCategoryService.getMasterCatalogCategoryByCode(categoryCode, FetchPlanGraphCategory.masterCategoryWithProductsFetchPlan());
            catalogCategoryPojo = (BoCatalogCategoryPojo) catalogPojoService.buildCatalogCategory(reloadedCategoryMaster);
            List<ProductMarketingPojo> relodedProductMarketings = new ArrayList<ProductMarketingPojo>();
            for (Iterator<ProductMarketing> iterator = reloadedCategoryMaster.getProductMarketings().iterator(); iterator.hasNext();) {
                ProductMarketing productMarketing = (ProductMarketing) iterator.next();
                ProductMarketing reloadedProduct = productService.getProductMarketingById(productMarketing.getId(), FetchPlanGraphProduct.productMarketingBackofficeCatalogueManagementFetchPlan());
                relodedProductMarketings.add(catalogPojoService.buildProductMarketing(reloadedProduct));
            }
            catalogCategoryPojo.setProductMarketings(relodedProductMarketings);
            
        } else if("virtual".equals(catalogType)){
            CatalogCategoryVirtual reloadedCategoryVirtual = catalogCategoryService.getVirtualCatalogCategoryByCode(categoryCode, FetchPlanGraphCategory.virtualCategoryWithProductsAndAssetsFetchPlan());
            catalogCategoryPojo = (BoCatalogCategoryPojo) catalogPojoService.buildCatalogCategory(reloadedCategoryVirtual);
            List<ProductMarketingPojo> relodedProductMarketings = new ArrayList<ProductMarketingPojo>();
            for (Iterator<ProductMarketing> iterator = reloadedCategoryVirtual.getProductMarketings().iterator(); iterator.hasNext();) {
                ProductMarketing productMarketing = (ProductMarketing) iterator.next();
                ProductMarketing reloadedProduct = productService.getProductMarketingById(productMarketing.getId(), FetchPlanGraphProduct.productMarketingBackofficeCatalogueManagementFetchPlan());
                relodedProductMarketings.add(catalogPojoService.buildProductMarketing(reloadedProduct));
            }
            catalogCategoryPojo.setProductMarketings(relodedProductMarketings);
        }
        return catalogCategoryPojo;
    }

}