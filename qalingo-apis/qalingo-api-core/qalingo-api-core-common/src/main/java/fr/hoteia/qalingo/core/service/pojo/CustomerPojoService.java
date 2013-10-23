package fr.hoteia.qalingo.core.service.pojo;

import java.util.List;

import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;

public interface CustomerPojoService {

    List<CustomerPojo> getAllCustomers();

    CustomerPojo getCustomerById(String id);

    void saveOrUpdate(CustomerPojo customerJsonPojo) throws Exception;

}