/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.binding.jaxb.helper;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import fr.hoteia.qalingo.core.domain.Store;

public interface StoreUnmarshalXmlHelper {

	Store getStore(String xml) throws UnsupportedEncodingException;

	Store getStore(InputStream inputStream);

}
