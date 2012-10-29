package fr.hoteia.qalingo.ws.remote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.ws.remote.service.StoreEndPointService;
import fr.hoteia.qalingo.ws.store.GetStoresRequest;
import fr.hoteia.qalingo.ws.store.GetStoresResponse;
import fr.hoteia.qalingo.ws.store.ObjectFactory;

/**
 * Store Web service endpoint. Uses a <code>StoreService</code> to create a response string.
 *
 */
@Endpoint
public class StoreEndpoint {

	protected final Log logger = LogFactory.getLog(getClass());
	
    /**
     * The local name of the expected request.
     */
    public static final String OFFER_REQUEST_LOCAL_NAME = "get-storesRequest";

    /**
     * The local name of the created response.
     */
    public static final String OFFER_RESPONSE_LOCAL_NAME = "get-storesResponse";

    @Autowired
	private StoreEndPointService storeEndPointService;

    /**
     * Reads the given <code>requestElement</code>, and sends a the response back.
     *
     * @param requestElement the contents of the SOAP message as DOM elements
     * @return the response element
     */
    @PayloadRoot(localPart = OFFER_REQUEST_LOCAL_NAME, namespace = Constants.NAMESPACE_URI)
    public GetStoresResponse handleSaveStoresResponseRequest(GetStoresRequest getStoresRequest) {
    	
		ObjectFactory objFactory = new ObjectFactory();
		GetStoresResponse getStoresResponse = objFactory.createGetStoresResponse();
		storeEndPointService.getStores(getStoresRequest, getStoresResponse);
			
        return getStoresResponse;
    }
    
}