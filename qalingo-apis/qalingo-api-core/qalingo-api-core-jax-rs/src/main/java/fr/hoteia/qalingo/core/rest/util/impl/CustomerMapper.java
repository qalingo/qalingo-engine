package fr.hoteia.qalingo.core.rest.util.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.rest.pojo.CustomerAddressPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerConnectionLogPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerPojo;
import fr.hoteia.qalingo.core.rest.util.PojoMapper;

@Component("customerMapper")
public class CustomerMapper extends AbstractPojoMapper<Customer, CustomerPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "addresses", "connectionLogs", "customerMarketAreas",
            "customerAttributes", "customerGroups" };

    @Autowired @Qualifier("customerAddressMapper") private PojoMapper<CustomerAddress, CustomerAddressPojo> addressMapper;
    @Autowired @Qualifier("customerConnectionLogMapper") private PojoMapper<CustomerConnectionLog, CustomerConnectionLogPojo> connectionLogMapper;

    @Override
    public Class<Customer> getObjectType() {
        return Customer.class;
    }

    @Override
    public Class<CustomerPojo> getPojoType() {
        return CustomerPojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        mapAddressesPropertyFromPojo(customerPojo, customer);
        mapConnectionLogsFromPojo(customerPojo, customer);

    }

    private void mapAddressesPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerAddress> addresses = addressMapper.fromPojo(customerPojo.getAddresses());
        customer.setAddresses(new HashSet<CustomerAddress>(addresses));
    }

    private void mapConnectionLogsFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerConnectionLog> logs = connectionLogMapper.fromPojo(customerPojo.getConnectionLogs());
        customer.setConnectionLogs(new HashSet<CustomerConnectionLog>(logs));
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final Customer customer, final CustomerPojo customerPojo) {
        mapAddressesPropertyToPojo(customer, customerPojo);
        mapConnectionLogsToPojo(customer, customerPojo);
    }

    private void mapAddressesPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerAddressPojo> mappedAddresses = addressMapper.toPojo(customer.getAddresses());
        customerPojo.setAddresses(mappedAddresses);
    }

    private void mapConnectionLogsToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerConnectionLogPojo> logs = connectionLogMapper.toPojo(customer.getConnectionLogs());
        customerPojo.setConnectionLogs(logs);
    }

}
