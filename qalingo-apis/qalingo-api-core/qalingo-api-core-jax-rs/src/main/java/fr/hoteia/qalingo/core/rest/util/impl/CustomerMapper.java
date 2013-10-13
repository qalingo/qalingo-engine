package fr.hoteia.qalingo.core.rest.util.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.rest.pojo.CustomerAddressPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerAttributePojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerConnectionLogPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerMarketAreaPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerPojo;
import fr.hoteia.qalingo.core.rest.util.PojoMapper;

@Component("customerMapper")
public class CustomerMapper extends AbstractPojoMapper<Customer, CustomerPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "addresses", "connectionLogs", "customerMarketAreas",
            "customerAttributes", "customerGroups" };

    @Autowired @Qualifier("customerAddressMapper") private PojoMapper<CustomerAddress, CustomerAddressPojo> addressMapper;
    @Autowired @Qualifier("customerConnectionLogMapper") private PojoMapper<CustomerConnectionLog, CustomerConnectionLogPojo> connectionLogMapper;
    @Autowired @Qualifier("customerMarketAreaMapper") private PojoMapper<CustomerMarketArea, CustomerMarketAreaPojo> customerMarketAreaMapper;
    @Autowired @Qualifier("customerAttributeMapper") private PojoMapper<CustomerAttribute, CustomerAttributePojo> customerAttributeMapper;

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
        mapConnectionLogsPropertyFromPojo(customerPojo, customer);
        mapCustomerMarketAreasPropertyFromPojo(customerPojo, customer);
        mapCustomerAttributesPropertyFromPojo(customerPojo, customer);
    }

    private void mapAddressesPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerAddress> addresses = addressMapper.fromPojo(customerPojo.getAddresses());
        customer.setAddresses(new HashSet<CustomerAddress>(addresses));
    }

    private void mapConnectionLogsPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerConnectionLog> logs = connectionLogMapper.fromPojo(customerPojo.getConnectionLogs());
        customer.setConnectionLogs(new HashSet<CustomerConnectionLog>(logs));
    }

    private void mapCustomerMarketAreasPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerMarketArea> marketAreas = customerMarketAreaMapper.fromPojo(customerPojo.getCustomerMarketAreas());
        customer.setCustomerMarketAreas(new HashSet<CustomerMarketArea>(marketAreas));
    }

    private void mapCustomerAttributesPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerAttribute> attributes = customerAttributeMapper.fromPojo(customerPojo.getCustomerAttributes());
        customer.setCustomerAttributes(new HashSet<CustomerAttribute>(attributes));
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(final Customer customer, final CustomerPojo customerPojo) {
        mapAddressesPropertyToPojo(customer, customerPojo);
        mapConnectionLogsPropertyToPojo(customer, customerPojo);
        mapCustomerMarketAreasPropertyToPojo(customer, customerPojo);
        mapCustomerAttributesPropertyToPojo(customer, customerPojo);
    }

    private void mapAddressesPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerAddressPojo> mappedAddresses = addressMapper.toPojo(customer.getAddresses());
        customerPojo.setAddresses(mappedAddresses);
    }

    private void mapConnectionLogsPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerConnectionLogPojo> logs = connectionLogMapper.toPojo(customer.getConnectionLogs());
        customerPojo.setConnectionLogs(logs);
    }

    private void mapCustomerMarketAreasPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerMarketAreaPojo> marketAreas = customerMarketAreaMapper.toPojo(customer.getCustomerMarketAreas());
        customerPojo.setCustomerMarketAreas(marketAreas);
    }

    private void mapCustomerAttributesPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerAttributePojo> attributes = customerAttributeMapper.toPojo(customer.getCustomerAttributes());
        customerPojo.setCustomerAttributes(attributes);
    }

}
