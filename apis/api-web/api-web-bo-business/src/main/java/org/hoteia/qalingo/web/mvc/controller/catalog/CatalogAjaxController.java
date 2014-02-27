/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.catalog.BoCatalogCategoryPojo;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
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
    private CatalogPojoService catalogPojoService;
       
    @Autowired
    protected ProductService productService;
    
    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @RequestMapping(value = BoUrls.GET_PRODUCT_LIST_AJAX_URL, method = RequestMethod.GET)
    @ResponseBody
    public BoCatalogCategoryPojo getCart(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String categoryCode = request.getParameter(RequestConstants.REQUEST_PARAMETER_CATALOG_CATEGORY_CODE);
        
        final BoCatalogCategoryPojo catalogCategoryPojo = new BoCatalogCategoryPojo();
        List<ProductMarketing> products = productService.findProductMarketingsByCatalogCategoryCode(requestData.getMarketArea().getId(), categoryCode);
        for (Iterator<ProductMarketing> iterator = products.iterator(); iterator.hasNext();) {
            ProductMarketing productMarketing = (ProductMarketing) iterator.next();
            catalogCategoryPojo.getProductMarketings().add(catalogPojoService.buildProductMarketing(productMarketing));
        }
        return catalogCategoryPojo;
    }

}