package fr.hoteia.qalingo.core.rest.service;

import java.util.List;

import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;

public interface CustomerRestService {

    List<CustomerJsonPojo> getAllCustomers();

    CustomerJsonPojo getCustomerById(final String id);

    void saveOrUpdate(final CustomerJsonPojo customerJsonPojo) throws Exception;

}