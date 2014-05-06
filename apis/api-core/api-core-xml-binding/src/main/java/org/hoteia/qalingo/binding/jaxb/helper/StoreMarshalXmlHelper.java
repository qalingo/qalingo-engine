/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.binding.jaxb.helper;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.hoteia.qalingo.core.domain.Store;

public interface StoreMarshalXmlHelper {

	String getXMLAsString(Store store) throws UnsupportedEncodingException, JAXBException;

	ByteArrayOutputStream getXMLAsByteArrayOutputStream(Store store) throws JAXBException;

}
