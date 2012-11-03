package fr.hoteia.qalingo.core.rest.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
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

import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.common.service.StoreService;
import fr.hoteia.qalingo.core.rest.pojo.StoreJsonPojo;
import fr.hoteia.qalingo.core.rest.service.StoreRestService;

@Service("storeRestService")
@Path("/store/")
public class StoreRestServiceImpl implements StoreRestService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected StoreService storeService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoreJsonPojo> getStores() {
		List<Store> stores = storeService.findStores();
		List<StoreJsonPojo> storeStoreJsonBeans = new ArrayList<StoreJsonPojo>();
		for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
			Store store = (Store) iterator.next();
			StoreJsonPojo storeJsonBean = buildStoreJsonBean(store);
			storeStoreJsonBeans.add(storeJsonBean);
		}
		return storeStoreJsonBeans;
	}
 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public StoreJsonPojo getStore(@PathParam("id") String id) {
		Store store = storeService.getStoreById(id);
		StoreJsonPojo storeJsonBean = buildStoreJsonBean(store);
		return storeJsonBean;
	}
 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveSomeBean(StoreJsonPojo storeJsonBean) {
		Store store = buildStore(storeJsonBean);
		storeService.saveOrUpdateStore(store);
	}

	protected StoreJsonPojo buildStoreJsonBean(Store store){
		StoreJsonPojo storeJsonBean = new StoreJsonPojo();
		storeJsonBean.setBusinessName(store.getBusinessName());
		
		// TODO : ...
		
		return storeJsonBean;
	}
	
	protected Store buildStore(StoreJsonPojo storeJsonBean){
		Store store = new Store();
		store.setBusinessName(storeJsonBean.getBusinessName());
	
		// TODO : ...
	
		return store;
	}
}
