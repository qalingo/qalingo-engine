package fr.hoteia.qalingo.core.pojo.service;

import java.util.List;

import fr.hoteia.qalingo.core.pojo.CustomerPojo;

public interface CustomerPojoService {

    List<CustomerPojo> getAllCustomers();

    CustomerPojo getCustomerById(final String id);

    void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception;

}