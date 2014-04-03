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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("brandeLineController")
public class BrandLineController extends AbstractMCommerceController {

	@Autowired
	protected ProductService productService;
	
	@RequestMapping(FoUrls.BRAND_LINE_URL)
	public ModelAndView brandLine(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_BRAND_CODE) final String brandCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.BRAND_LINE.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        final MarketArea currentMarketArea = requestData.getMarketArea();
        
		final ProductBrand productBrand = productService.getProductBrandByCode(currentMarketArea.getId(), brandCode);
		
		List<ProductMarketing>  productMarketings = productService.findProductMarketingsByBrandId(currentMarketArea.getId(), productBrand.getId());
		final ProductBrandViewBean productBrandViewBean = frontofficeViewBeanFactory.buildViewBeanProductBrand(requestUtil.getRequestData(request), productBrand, productMarketings);
		model.addAttribute("productBrand", productBrandViewBean);
		
        overrideDefaultSeoPageTitleAndMainContentTitle(request, modelAndView, FoUrls.BRAND_LINE.getKey());

        return modelAndView;
	}
    
}