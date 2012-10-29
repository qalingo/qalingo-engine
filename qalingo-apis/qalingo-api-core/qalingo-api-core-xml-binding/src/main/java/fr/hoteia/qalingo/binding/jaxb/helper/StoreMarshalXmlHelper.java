package fr.hoteia.qalingo.binding.jaxb.helper;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import fr.hoteia.qalingo.core.common.domain.Store;

public interface StoreMarshalXmlHelper {

	String getXMLAsString(Store store) throws UnsupportedEncodingException, JAXBException;

	ByteArrayOutputStream getXMLAsByteArrayOutputStream(Store store) throws JAXBException;

}
