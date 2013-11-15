/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.warehouse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractBusinessBackofficeController;

/**
 * 
 */
@Controller("warehouseController")
public class WarehouseController extends AbstractBusinessBackofficeController {


	@RequestMapping(value = BoUrls.WAREHOUSE_LIST_URL, method = RequestMethod.GET)
	public ModelAndView shippingList(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_LIST.getVelocityPage());
		
		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.WAREHOUSE_DETAILS_URL, method = RequestMethod.GET)
	public ModelAndView shippingDetails(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_DETAILS.getVelocityPage());

		
        return modelAndView;
	}
	
	@RequestMapping(value = BoUrls.WAREHOUSE_EDIT_URL, method = RequestMethod.GET)
	public ModelAndView shippingEdit(final HttpServletRequest request, final Model model) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.WAREHOUSE_EDIT.getVelocityPage());
		
		
		return modelAndView;
	}
	

}