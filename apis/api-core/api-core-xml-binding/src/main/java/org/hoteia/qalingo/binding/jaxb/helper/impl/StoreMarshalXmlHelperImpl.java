/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.binding.jaxb.helper.impl;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hoteia.qalingo.binding.common.store.ObjectFactory;
import org.hoteia.qalingo.binding.common.store.XmlStore;
import org.hoteia.qalingo.binding.jaxb.helper.StoreMarshalXmlHelper;
import org.hoteia.qalingo.core.domain.Store;

public class StoreMarshalXmlHelperImpl implements StoreMarshalXmlHelper {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	private Marshaller marshaller;

	public String getXMLAsString(Store store) throws UnsupportedEncodingException, JAXBException {
		String xml = null;
		ByteArrayOutputStream xmlOutput = null;
		xmlOutput = getXMLAsByteArrayOutputStream(store);
		if (xmlOutput != null) {
			xml = xmlOutput.toString("UTF-8");
		}
		return xml;
	}

	public ByteArrayOutputStream getXMLAsByteArrayOutputStream(Store store) throws JAXBException {
		ByteArrayOutputStream xmlOutput = null;
		xmlOutput = buildAsByteArrayOutputStream(store);
		return xmlOutput;
	}

	/**
	 * Build the XML document using the marshaller created earlier.
	 * 
	 * @param actions the object model to be parsed
	 * @return the XML document
	 * @throws JAXBException
	 */
	protected ByteArrayOutputStream buildAsByteArrayOutputStream(Store store) throws JAXBException {

		// StoreValidationEventHandler storeValidationEventHandler = new StoreValidationEventHandler();
		ByteArrayOutputStream xmlOutput = new ByteArrayOutputStream();

		ObjectFactory objFactory = new ObjectFactory();
		XmlStore xmlStore = objFactory.createXmlStore();

		buildStore(store, xmlStore);

		JAXBElement<XmlStore> storeElement = objFactory.createStoreElement(xmlStore);
		marshaller.marshal(storeElement, xmlOutput);

		return xmlOutput;
	}

	public XmlStore buildStore(Store store, XmlStore xmlStore) {
		xmlStore.setId(store.getId().toString());
		xmlStore.setVersion(store.getVersion());
		xmlStore.setName(store.getName());

		// ...
		
		xmlStore.setDateCreate(store.getDateCreate());
		xmlStore.setDateUpdate(store.getDateUpdate());
		return xmlStore;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

}
