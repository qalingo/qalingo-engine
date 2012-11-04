/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface ModelAndViewFactory {

	void initCommonModelAndView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception;

	void initLoginModelAndView(HttpServletRequest request, ModelAndView modelAndView) throws Exception;
}
