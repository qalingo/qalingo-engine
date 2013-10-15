package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.CustomerRole;
import fr.hoteia.qalingo.core.pojo.customer.CustomerGroupPojo;
import fr.hoteia.qalingo.core.pojo.customer.CustomerRolePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("customerGroupMapper")
public class CustomerGroupMapper extends AbstractPojoMapper<CustomerGroup, CustomerGroupPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[]{"customerRoles"};

    @Autowired @Qualifier("customerRoleMapper") private PojoMapper<CustomerRole, CustomerRolePojo> customerRoleMapper;

    @Override
    public Class<CustomerGroup> getObjectType() {
        return CustomerGroup.class;
    }

    @Override
    public Class<CustomerGroupPojo> getPojoType() {
        return CustomerGroupPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(CustomerGroupPojo jsonPojo, CustomerGroup object) {
        Collection<CustomerRole> customerRoles = customerRoleMapper.fromPojo(jsonPojo.getCustomerRoles());
        object.setCustomerRoles(asSet(customerRoles));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CustomerGroup object, CustomerGroupPojo jsonPojo) {
        Collection<CustomerRolePojo> customerRoles = customerRoleMapper.toPojo(object.getCustomerRoles());
        jsonPojo.setCustomerRoles(customerRoles);
    }
}
