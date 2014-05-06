/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.ws.service.impl;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

import org.hoteia.qalingo.core.pojo.store.StorePojo;
import org.hoteia.qalingo.core.service.pojo.StorePojoFactory;
import org.hoteia.qalingo.core.ws.service.StoreWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("storeWebService")
@WebService(endpointInterface="org.hoteia.qalingo.core.ws.service.StoreWebService")
public class StoreWebServiceImpl implements StoreWebService {

    @Autowired private StorePojoFactory storeService;

    @Override
    public List<StorePojo> getStores() {
        return storeService.getAllStores();
    }

    public StorePojo getStoreById(String storeId){
		return storeService.getStoreById(storeId);
	}

    @Override
    public void saveOrUpdate(@WebParam(name = "store") StorePojo storeBean) {
        storeService.saveOrUpdate(storeBean);
    }

}
