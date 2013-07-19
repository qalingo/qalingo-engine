/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.service.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.rest.pojo.StoreJsonPojo;
import fr.hoteia.qalingo.core.rest.service.StoreRestService;
import fr.hoteia.qalingo.core.rest.util.JsonFactory;
import fr.hoteia.qalingo.core.service.RetailerService;

@Service("storeRestService")
@Path("/store/")
public class StoreRestServiceImpl implements StoreRestService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected RetailerService storeService;
	
	@Autowired
	protected JsonFactory jsonFactory;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoreJsonPojo> getStores() {
		List<Store> stores = storeService.findStores();
		List<StoreJsonPojo> storeStoreJsonBeans = jsonFactory.buildJsonStores(stores);
		return storeStoreJsonBeans;
	}
 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public StoreJsonPojo getStore(@PathParam("id") String id) {
		Store store = storeService.getStoreById(id);
		StoreJsonPojo storeJsonBean = jsonFactory.buildJsonStore(store);
		return storeJsonBean;
	}
 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveSomeBean(StoreJsonPojo storeJsonBean) {
		Store store = jsonFactory.buildStore(storeJsonBean);
		storeService.saveOrUpdateStore(store);
	}

}
