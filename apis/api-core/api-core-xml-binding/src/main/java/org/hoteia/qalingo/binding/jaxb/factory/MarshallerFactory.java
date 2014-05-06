/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.binding.jaxb.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.core.io.Resource;

public class MarshallerFactory {

	private JAXBContext context;
	private List<Source> xsdSources = new ArrayList<Source>();

	public Marshaller createMarshaller() throws Exception {
		Marshaller marshaller = context.createMarshaller();

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdSources.toArray(new Source[0]));
		marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.setSchema(schema);

		return marshaller;
	}

	public void setXsdPath(Resource xsdResource) throws IOException {
		setXsdPaths(Collections.singleton(xsdResource));
	}

	public void setXsdPaths(Collection<Resource> xsdResources) throws IOException {
		for (Resource xsdResource : xsdResources) {
			xsdSources = new ArrayList<Source>();
			xsdSources.add(new StreamSource(xsdResource.getInputStream()));
		}
	}

	public void setContext(JAXBContext context) {
		this.context = context;
	}

}
