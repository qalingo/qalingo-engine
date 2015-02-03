/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.servlet.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RedirectView extends org.springframework.web.servlet.view.RedirectView {

	public RedirectView(String url) throws UnsupportedEncodingException {
		super(URLEncoder.encode(url, "UTF-8"));
		setExposeModelAttributes(false);
    }
}
