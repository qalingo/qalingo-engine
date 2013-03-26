/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.StoreDao;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.service.StoreService;

@Service("storeService")
@Transactional
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDao storeDao;

	public Store getStoreById(String rawStoreId) {
		long storeId = -1;
		try {
			storeId = Long.parseLong(rawStoreId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return storeDao.getStoreById(storeId);
	}
	
	public Store getStoreByCode(String storeCode) {
		return storeDao.getStoreByCode(storeCode);
	}

//	public List<Store> findStore(Store criteria) {
//		return storeDao.findByExample(criteria);
//	}

	public List<Store> findStores() {
		return storeDao.findStores();
	}
	
	public void saveOrUpdateStore(Store store) {
		storeDao.saveOrUpdateStore(store);
	}

	public void deleteStore(Store store) {
		storeDao.deleteStore(store);
	}

}
