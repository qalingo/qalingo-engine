/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.ws.service.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.common.service.StoreService;
import fr.hoteia.qalingo.core.ws.pojo.StoreWsPojo;
import fr.hoteia.qalingo.core.ws.service.StoreWebService;

@WebService(endpointInterface="fr.hoteia.qalingo.core.ws.service.StoreWebService")
@Service("storeWebService")
public class StoreWebServiceImpl implements StoreWebService {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected StoreService storeService;
	
	public StoreWsPojo getStoreById(String storeId){
		Store store = storeService.getStoreById(storeId);
		StoreWsPojo storeWsPojo = buildStoreWsPojo(store);
		return storeWsPojo;
	}
	
	protected StoreWsPojo buildStoreWsPojo(Store store){
		StoreWsPojo storeWsPojo = new StoreWsPojo();
		storeWsPojo.setBusinessName(store.getBusinessName());
	
		// TODO : ...
	
		return storeWsPojo;
	}
}
