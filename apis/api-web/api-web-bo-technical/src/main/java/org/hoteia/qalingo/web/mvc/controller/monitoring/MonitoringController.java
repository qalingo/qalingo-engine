/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.monitoring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.domain.ServerStatus;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.ServerService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 */
@Controller("monitoringController")
public class MonitoringController extends AbstractTechnicalBackofficeController {

    @Autowired
    protected ServerService serverService;
	
	@RequestMapping(BoUrls.MONITORING_URL)
	public ModelAndView searchMonitoring(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.MONITORING.getVelocityPage());
        final RequestData requestData = requestUtil.getRequestData(request);
        
		List<ServerStatus> severList = serverService.findServerList();
		modelAndView.addObject("severList", severList);
		
		List<ServerStatus> severStatusList = serverService.findServerStatus();
		modelAndView.addObject("severStatusList", severStatusList);
		
        return modelAndView;
	}
	
}