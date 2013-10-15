package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.*;
import fr.hoteia.qalingo.core.pojo.customer.*;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("customerMapper")
public class CustomerMapper extends AbstractPojoMapper<Customer, CustomerPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[] { "addresses", "connectionLogs", "customerMarketAreas",
            "customerAttributes", "customerGroups", "oauthAccesses" };

    @Autowired @Qualifier("customerAddressMapper") private PojoMapper<CustomerAddress, CustomerAddressPojo> addressMapper;
    @Autowired @Qualifier("customerConnectionLogMapper") private PojoMapper<CustomerConnectionLog, CustomerConnectionLogPojo> connectionLogMapper;
    @Autowired @Qualifier("customerMarketAreaMapper") private PojoMapper<CustomerMarketArea, CustomerMarketAreaPojo> customerMarketAreaMapper;
    @Autowired @Qualifier("customerAttributeMapper") private PojoMapper<CustomerAttribute, CustomerAttributePojo> customerAttributeMapper;
    @Autowired @Qualifier("customerGroupMapper") private PojoMapper<CustomerGroup, CustomerGroupPojo> customerGroupMapper;
    @Autowired @Qualifier("customerOAuthMapper") private PojoMapper<CustomerOAuth, CustomerOAuthPojo> customerOAuthMapper;

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
        mapCustomerGroupsPropertyFromPojo(customerPojo, customer);
        mapOauthAccessesPropertyFromPojo(customerPojo, customer);
    }

    private void mapAddressesPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerAddress> addresses = addressMapper.fromPojo(customerPojo.getAddresses());
        customer.setAddresses(asSet(addresses));
    }

    private void mapConnectionLogsPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerConnectionLog> logs = connectionLogMapper.fromPojo(customerPojo.getConnectionLogs());
        customer.setConnectionLogs(asSet(logs));
    }

    private void mapCustomerMarketAreasPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerMarketArea> marketAreas = customerMarketAreaMapper.fromPojo(customerPojo.getCustomerMarketAreas());
        customer.setCustomerMarketAreas(asSet(marketAreas));
    }

    private void mapCustomerAttributesPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerAttribute> attributes = customerAttributeMapper.fromPojo(customerPojo.getCustomerAttributes());
        customer.setCustomerAttributes(asSet(attributes));
    }

    private void mapCustomerGroupsPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerGroup> customerGroups = customerGroupMapper.fromPojo(customerPojo.getCustomerGroups());
        customer.setCustomerGroups(asSet(customerGroups));
    }

    private void mapOauthAccessesPropertyFromPojo(final CustomerPojo customerPojo, final Customer customer) {
        Collection<CustomerOAuth> customerOAuths = customerOAuthMapper.fromPojo(customerPojo.getOauthAccesses());
        customer.setOauthAccesses(asSet(customerOAuths));
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
        mapCustomerGroupsPropertyToPojo(customer, customerPojo);
        mapOauthAccessesPropertyToPojo(customer, customerPojo);
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

    private void mapCustomerGroupsPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerGroupPojo> customerGroups = customerGroupMapper.toPojo(customer.getCustomerGroups());
        customerPojo.setCustomerGroups(customerGroups);
    }

    private void mapOauthAccessesPropertyToPojo(final Customer customer, final CustomerPojo customerPojo) {
        Collection<CustomerOAuthPojo> oauthAccesses = customerOAuthMapper.toPojo(customer.getOauthAccesses());
        customerPojo.setOauthAccesses(oauthAccesses);
    }

}
