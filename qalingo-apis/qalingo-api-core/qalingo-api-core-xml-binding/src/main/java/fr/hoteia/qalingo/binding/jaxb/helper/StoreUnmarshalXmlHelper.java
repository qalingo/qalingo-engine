package fr.hoteia.qalingo.binding.jaxb.helper;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import fr.hoteia.qalingo.core.common.domain.Store;

public interface StoreUnmarshalXmlHelper {

	Store getStore(String xml) throws UnsupportedEncodingException;

	Store getStore(InputStream inputStream);

}
