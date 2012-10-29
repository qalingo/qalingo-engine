package fr.hoteia.qalingo.binding.jaxb.helper.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import fr.hoteia.qalingo.binding.jaxb.helper.StoreUnmarshalXmlHelper;
import fr.hoteia.qalingo.core.common.domain.Store;

public class StoreUnmarshalXmlHelperImpl implements StoreUnmarshalXmlHelper {
	
	private static final Logger LOG = Logger.getLogger(StoreUnmarshalXmlHelperImpl.class);
	
	private Unmarshaller unmarshaller;

	public Store getStore(String xml) throws UnsupportedEncodingException {
		InputStream inputStream = new ByteArrayInputStream(xml.getBytes(("UTF-8")));
		return getStore(inputStream);
	}

	public Store getStore(InputStream inputStream) {
		Store store = null;
		store = getStoreByXml(inputStream);
		return store;
	}

	/**
	 * Parse the XML document using the unmarshaller created earlier. Note that the unmarshaller is NOT thread-safe.
	 * 
	 * @param xsdInputStream the input stream.
	 * @return the parsed object model
	 */
	protected Store getStoreByXml(InputStream xsdInputStream) {
		Store store = null;
		if (xsdInputStream != null) {
			try {
				store = ((JAXBElement<Store>)unmarshaller.unmarshal(xsdInputStream)).getValue();
			} catch (javax.xml.bind.JAXBException e) {
				LOG.error("something is wrong with jaxb", e);
			}
		}
		return store;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

}
