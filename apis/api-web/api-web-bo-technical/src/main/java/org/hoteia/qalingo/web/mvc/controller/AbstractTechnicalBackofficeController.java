/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.hoteia.qalingo.core.web.mvc.controller.AbstractBackofficeQalingoController;
import org.hoteia.qalingo.web.mvc.factory.FormFactory;
import org.hoteia.qalingo.web.service.WebBackofficeService;

/**
 * 
 * <p>
 * <a href="AbstractTechnicalBackofficeController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractTechnicalBackofficeController extends AbstractBackofficeQalingoController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    protected FormFactory formFactory;

	@Autowired
	protected WebBackofficeService webBackofficeService;
	
}