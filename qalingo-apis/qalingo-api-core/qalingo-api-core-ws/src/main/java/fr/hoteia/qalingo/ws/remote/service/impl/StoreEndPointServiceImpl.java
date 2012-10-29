package fr.hoteia.qalingo.ws.remote.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.binding.common.store.ObjectFactory;
import fr.hoteia.qalingo.binding.common.store.XmlStore;
import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.common.service.StoreService;
import fr.hoteia.qalingo.ws.remote.service.StoreEndPointService;
import fr.hoteia.qalingo.ws.store.GetStoresRequest;
import fr.hoteia.qalingo.ws.store.GetStoresResponse;

/**
 * Default implementation of <code>StoreEndPointService</code>.
 *
 */
@Service("storeEndPointService")
public class StoreEndPointServiceImpl implements StoreEndPointService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private StoreService storeService;
	
    public void getStores(GetStoresRequest getStoresRequest, GetStoresResponse getStoresResponse) {
    	try {
    		Store store = new Store();
//    		store.setCountry(getStoresRequest.getCountry());
    		List<Store> stores = storeService.findStore(store);
    		ObjectFactory objFactory = new ObjectFactory();
    			
    		for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
    			Store storeIt = (Store) iterator.next();

    			XmlStore xmlStore = objFactory.createXmlStore();
    			
    			BeanUtils.copyProperties(storeIt, xmlStore);
    			
    			getStoresResponse.getStore().add(xmlStore);
			}
    		
    	} catch (Exception e) {
    		logger.error("Error during the save of the stores", e);
    		
    		// TODO : message
		}
    	
    }
}
