package fr.hoteia.qalingo.ws.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import fr.hoteia.qalingo.ws.store.GetStoresRequest;
import fr.hoteia.qalingo.ws.store.GetStoresResponse;

public class StoreClient extends WebServiceGatewaySupport {

	protected final Log logger = LogFactory.getLog(StoreClient.class.getName());

    public GetStoresResponse getStores(GetStoresRequest getStoresRequest) throws Exception {
    	GetStoresResponse getStoresResponse = (GetStoresResponse) getWebServiceTemplate().marshalSendAndReceive(getStoresRequest);
		
//        if(getStoresResponse.getResponse().getCode().equals("ERROR")){
//			logger.error("something is wrong with the store ws response");
//		}
    	
    	return getStoresResponse;
    }

}
