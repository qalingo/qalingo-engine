/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.web.servlet;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

public class VelocityLayoutViewResolver extends org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	protected View createView(String viewName, Locale locale) throws Exception {
		View view = super.createView(viewName, locale);
		if (view instanceof RedirectView) {
			((RedirectView) view).setExposeModelAttributes(false);
		}
		return view;
	}

}
