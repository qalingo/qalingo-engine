/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.controller.catalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.ProductCategoryService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractQalingoController;

/**
 * 
 */
@Controller
public class ProductAxeController extends AbstractQalingoController {

	@Autowired
	protected ProductCategoryService productCategoryService;
	
	@RequestMapping("/product-axe.html*")
	public ModelAndView productAxe(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/product-axe");
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		final String categoryCode = request.getParameter(Constants.REQUEST_PARAM_PRODUCT_CATEGORY_CODE);
		final ProductCategoryVirtual productCategory = productCategoryService.getVirtualProductCategoryByCode(currentMarketArea.getId(), currentRetailer.getId(), categoryCode);
		
		final String titleKeyPrefixSufix = "product.axe.category";
		initPage(request, response, modelAndView, titleKeyPrefixSufix);
		modelAndViewFactory.initPageProductCategory(request, response, modelAndView, productCategory, titleKeyPrefixSufix);
		
        return modelAndView;
	}
    
}
