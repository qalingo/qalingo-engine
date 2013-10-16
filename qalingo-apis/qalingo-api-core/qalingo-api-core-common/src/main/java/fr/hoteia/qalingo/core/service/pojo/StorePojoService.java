package fr.hoteia.qalingo.core.service.pojo;

import java.util.List;

import fr.hoteia.qalingo.core.pojo.store.StorePojo;

public interface StorePojoService {
    List<StorePojo> getAllStores();

    StorePojo getStoreById(String id);

    void saveOrUpdate(StorePojo storeJsonBean);
}
