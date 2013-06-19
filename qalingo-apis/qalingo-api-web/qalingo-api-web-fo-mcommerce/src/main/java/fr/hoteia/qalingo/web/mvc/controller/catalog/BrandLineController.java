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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.domain.Market;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.MarketPlace;
import fr.hoteia.qalingo.core.domain.ProductBrand;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.service.ProductBrandService;
import fr.hoteia.qalingo.core.service.ProductMarketingService;
import fr.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import fr.hoteia.qalingo.web.mvc.controller.AbstractMCommerceFrontofficeController;
import fr.hoteia.qalingo.web.mvc.viewbean.ProductBrandViewBean;

/**
 * 
 */
@Controller
public class BrandLineController extends AbstractMCommerceFrontofficeController {

	@Autowired
	protected ProductMarketingService productMarketingService;
	
	@Autowired
	protected ProductBrandService productBrandService;
	
	@RequestMapping("/brand-line.html*")
	public ModelAndView brandLine(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), "catalog/brand-line");

		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final String brandCode = request.getParameter(Constants.REQUEST_PARAM_BRAND_CODE);
		final ProductBrand productBrand = productBrandService.getProductBrandByCode(currentMarketArea.getId(), brandCode);
		
		// "brand.line";

		final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
		final Market currentMarket = requestUtil.getCurrentMarket(request);
		final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
		final Retailer currentRetailer = requestUtil.getCurrentRetailer(request);
		List<ProductMarketing>  productMarketings = productMarketingService.findProductMarketingsByBrandId(currentMarketArea.getId(), currentRetailer.getId(), productBrand.getId());
		final ProductBrandViewBean productBrandViewBean = viewBeanFactory.buildProductBrandViewBean(request, currentMarketPlace, currentMarket, currentMarketArea, currentLocalization, currentRetailer, productBrand, productMarketings);
		modelAndView.addObject("productBrand", productBrandViewBean);
		
        return modelAndView;
	}
    
}