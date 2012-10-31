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
import fr.hoteia.qalingo.core.rest.api.bean.StoreJsonBean;
import fr.hoteia.qalingo.core.rest.service.StoreRestService;

@Service("storeRestService")
@Path("/store/")
public class StoreRestServiceImpl implements StoreRestService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected StoreService storeService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoreJsonBean> getStores() {
		List<Store> stores = storeService.findStores();
		List<StoreJsonBean> storeStoreJsonBeans = new ArrayList<StoreJsonBean>();
		for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
			Store store = (Store) iterator.next();
			StoreJsonBean storeJsonBean = buildStoreJsonBean(store);
			storeStoreJsonBeans.add(storeJsonBean);
		}
		return storeStoreJsonBeans;
	}
 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public StoreJsonBean getStore(@PathParam("id") String id) {
		Store store = storeService.getStoreById(id);
		StoreJsonBean storeJsonBean = buildStoreJsonBean(store);
		return storeJsonBean;
	}
 
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveSomeBean(StoreJsonBean storeJsonBean) {
		Store store = buildStore(storeJsonBean);
		storeService.saveOrUpdateStore(store);
	}

	protected StoreJsonBean buildStoreJsonBean(Store store){
			StoreJsonBean storeJsonBean = new StoreJsonBean();
		
		// TODO : ...
		
		storeJsonBean.setBusinessName(store.getBusinessName());
		return storeJsonBean;
	}
	
	protected Store buildStore(StoreJsonBean storeJsonBean){
		Store store = new Store();
		store.setBusinessName(storeJsonBean.getBusinessName());
	
		// TODO : ...
	
		return store;
	}
}
