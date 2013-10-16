package fr.hoteia.qalingo.core.service.pojo;

import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;

import java.util.List;

public interface CustomerPojoService {

    List<CustomerPojo> getAllCustomers();

    CustomerPojo getCustomerById(final String id);

    void saveOrUpdate(final CustomerPojo customerJsonPojo) throws Exception;

}