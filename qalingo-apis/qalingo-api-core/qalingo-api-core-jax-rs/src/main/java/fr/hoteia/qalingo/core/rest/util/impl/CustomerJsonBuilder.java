package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;

@Service("customerJsonBuilder")
public class CustomerJsonBuilder extends AbstractJsonBuilder<Customer, CustomerJsonPojo> {

    @Override
    public Class<Customer> getObjectType() {
        return Customer.class;
    }

    @Override
    public Class<CustomerJsonPojo> getPojoType() {
        return CustomerJsonPojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CustomerJsonPojo jsonPojo, final Customer customer) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String[] getIgnoredProperties() {
        return new String[] { "addresses", "connectionLogs", "customerMarketAreas", "customerAttributes", "customerGroups" };
    }

    @Override
    public CustomerJsonPojo toPojo(final Customer toMap) {
        final CustomerJsonPojo customerJsonPojo = new CustomerJsonPojo();
        BeanUtils.copyProperties(toMap, customerJsonPojo, getIgnoredProperties());
        return customerJsonPojo;
    }

}
