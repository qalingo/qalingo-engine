package fr.hoteia.qalingo.core.rest.service;

import java.util.List;

import fr.hoteia.qalingo.core.rest.pojo.CustomerPojo;

public interface CustomerRestService {

    List<CustomerPojo> getAllCustomers();

    CustomerPojo getCustomerById(final String id);

    void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception;

}