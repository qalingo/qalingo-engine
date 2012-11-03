package fr.hoteia.qalingo.core.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import fr.hoteia.qalingo.core.ws.pojo.StoreWsPojo;

@WebService(name="storeWsClient", targetNamespace="http://www.qalingo.com")
public interface StoreWebService {

	@WebMethod(operationName="getStore")
	StoreWsPojo getStoreById(@WebParam(name="id") String storeId);
	
}
