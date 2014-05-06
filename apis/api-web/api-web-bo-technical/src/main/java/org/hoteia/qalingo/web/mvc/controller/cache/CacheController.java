/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.cache;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.ServerStatus;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.service.ServerService;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractTechnicalBackofficeController;



/**
 * 
 */
@Controller("cacheController")
public class CacheController extends AbstractTechnicalBackofficeController {

    @Autowired
    protected ServerService serverService;
    
	@RequestMapping(value = BoUrls.CACHE_URL, method = RequestMethod.GET)
	public ModelAndView cache(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CACHE.getVelocityPage());
		List<ServerStatus> severList = serverService.findServerList();
		modelAndView.addObject("severList", severList);
		
        return modelAndView;
	}
	@RequestMapping(value = "/flushCache.html", method = RequestMethod.POST)
	public ModelAndView flushCache(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), BoUrls.CACHE.getVelocityPage());
		String flag = request.getParameter(RequestConstants.REQUEST_PARAMETER_FLAG);

		String responseString = "";
		try{
		if(FLUSH_SCOPE.equals(flag)){
		//flush all of servers,all of caches and send JMS
		}
		if("FLUSH_SCOPE_ALLSERVER".equals(flag)){
		//flush all of server which include specify cache
			String serverName = request.getParameter(RequestConstants.REQUEST_PARAMETER_SERVERNAME);
			
			String cacheName = request.getParameter(RequestConstants.REQUEST_PARAMETER_CACHE_NAME);
		}
		
		//flush specify server and send JMS
		if(FLUSH_SCOPE_SINGLE.equals(flag)){
			String serverName = request.getParameter(RequestConstants.REQUEST_PARAMETER_SERVERNAME);
			
			String cacheName = request.getParameter(RequestConstants.REQUEST_PARAMETER_CACHE_NAME);
		}}catch(Exception e){
			responseString = e.getMessage();
		}finally{
			responseString = RESULT_SUCCESS;
		}
		
		
        //return modelAndView;
		PrintWriter out = response.getWriter(); 
		out.print(responseString);
		out.close();
		response.flushBuffer();	
		
        return null;
	}
	private String RESULT_SUCCESS = "SUCCESS";
	private String FLUSH_SCOPE ="ALL";//all of servers and all of caches
	private String FLUSH_SCOPE_ALLSERVER ="ALLSERVER";// specified cache in all of servers
	private String FLUSH_SCOPE_SINGLE ="SINGLE";// specified cache in single server

}