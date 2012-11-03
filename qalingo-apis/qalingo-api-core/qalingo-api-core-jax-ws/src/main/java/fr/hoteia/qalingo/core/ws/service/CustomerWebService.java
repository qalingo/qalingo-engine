package fr.hoteia.qalingo.core.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import fr.hoteia.qalingo.core.ws.pojo.CustomerWsPojo;

@WebService(name="customerWsClient", targetNamespace="http://www.qalingo.com")
public interface CustomerWebService {

	@WebMethod(operationName="getCustomer")
	CustomerWsPojo getCustomerById(@WebParam(name="id") String customerId);
	
}
