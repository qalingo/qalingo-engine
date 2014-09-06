/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.rest.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hoteia.qalingo.core.pojo.catalog.CatalogCategoryPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogListPojoResponse;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojo;
import org.hoteia.qalingo.core.pojo.catalog.CatalogPojoResponse;
import org.hoteia.qalingo.core.pojo.catalog.CategoryListPojoResponse;
import org.hoteia.qalingo.core.pojo.catalog.CategoryPojoResponse;
import org.hoteia.qalingo.core.pojo.catalog.ProductListPojoResponse;
import org.hoteia.qalingo.core.pojo.catalog.ProductPojoResponse;
import org.hoteia.qalingo.core.pojo.product.ProductMarketingPojo;
import org.hoteia.qalingo.core.service.pojo.CatalogPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/catalog/")
@Component("catalogRestService")
public class CatalogRestService {

    @Autowired
    private CatalogPojoService catalogService;

    @GET
    @Path("virtual")
    @Produces(MediaType.APPLICATION_JSON)
    public CatalogListPojoResponse getAllVirtuelCatalogs() {
        CatalogListPojoResponse catalogListPojoResponse = new CatalogListPojoResponse();
        List<CatalogPojo> catalogs = catalogService.getAllCatalogMasters();
        catalogListPojoResponse.setCatalogs(catalogs);
        return catalogListPojoResponse;
    }

    @GET
    @Path("virtual/{catalogCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CatalogPojoResponse getVirtuyalCatalogByCode(@PathParam("catalogCode") final String catalogCode) {
        CatalogPojoResponse catalogPojoResponse = new CatalogPojoResponse();
        CatalogPojo catalogPojo = catalogService.getVirtualCatalogByCode(catalogCode);
        catalogPojoResponse.setCatalog(catalogPojo);
        return catalogPojoResponse;
    }

    @GET
    @Path("virtual/{catalogCode}/category/")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryListPojoResponse categoriesByCatalog(@PathParam("catalogCode") final String catalogCode) {
        CategoryListPojoResponse categoryListPojoResponse = new CategoryListPojoResponse();
        List<CatalogCategoryPojo> categories = catalogService.getAllVirtualCategories(catalogCode);
        categoryListPojoResponse.setCategories(categories);
        return categoryListPojoResponse;
    }

    @GET
    @Path("virtual/{catalogCode}/category/{categoryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryPojoResponse categoryDetails(@PathParam("catalogCode") final String catalogCode, @PathParam("categoryCode") final String categoryCode) {
        CategoryPojoResponse categoryPojoResponse = new CategoryPojoResponse();
        CatalogCategoryPojo category = catalogService.getCatalogCategoryByCode(catalogCode, categoryCode);
        categoryPojoResponse.setCategory(category);
        return categoryPojoResponse;
    }
    
    @GET
    @Path("virtual/{catalogCode}/category/{categoryCode}/product/")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductListPojoResponse productListByCategory(@PathParam("catalogCode") final String catalogCode, @PathParam("categoryCode") final String categoryCode) {
        ProductListPojoResponse productListPojoResponse = new ProductListPojoResponse();
        List<ProductMarketingPojo> products = catalogService.getProductMarketingsByCategoryCode(catalogCode, categoryCode);
        productListPojoResponse.setProductMarketings(products);
        return productListPojoResponse;
    }
    
    @GET
    @Path("virtual/{catalogCode}/category/{categoryCode}/product/{productCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductPojoResponse productMarketingDetails(@PathParam("catalogCode") final String catalogCode, @PathParam("categoryCode") final String categoryCode, 
                                                       @PathParam("productCode") final String productCode) {
        ProductPojoResponse productPojoResponse = new ProductPojoResponse();
        ProductMarketingPojo product = catalogService.getProductMarketing(productCode);
        productPojoResponse.setProductMarketing(product);
        return productPojoResponse;
    }
}