/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("searchController")
public class SearchController extends AbstractTechnicalBackofficeController {

	@Autowired
	protected EngineSettingService engineSettingService;
	
	@RequestMapping(BoUrls.SEARCH_CONFIG_URL)
	public ModelAndView searchConfig(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.SEARCH_CONFIG.getVelocityPage());

		EngineSetting springBatchUrlEngineSetting = engineSettingService.getEngineSettingByCode(EngineSettingService.ENGINE_SETTING_CODE_SOLR);
		String solrUrl = springBatchUrlEngineSetting.getEngineSettingValue(EngineSettingService.ENGINE_SETTING_SOLR_MASTER_CONTEXT).getValue();
		modelAndView.addObject("solrUrl", solrUrl);
		
        return modelAndView;
	}
	
	@RequestMapping(BoUrls.SEARCH_URL)
	public ModelAndView search(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.SEARCH.getVelocityPage());
		
        return modelAndView;
	}
	
}