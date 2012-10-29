package fr.hoteia.qalingo.ws.remote.service;

import fr.hoteia.qalingo.ws.store.GetStoresRequest;
import fr.hoteia.qalingo.ws.store.GetStoresResponse;

/**
 *
 */
public interface StoreEndPointService {

    /**
     * Returns the given Store list.
     *
     * @return <code>Store List</code>
     */
	public void getStores(GetStoresRequest getStoresRequest, GetStoresResponse getStoresResponse);
}
