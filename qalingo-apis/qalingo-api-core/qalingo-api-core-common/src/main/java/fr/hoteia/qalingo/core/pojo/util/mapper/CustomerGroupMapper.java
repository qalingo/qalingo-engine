package fr.hoteia.qalingo.core.pojo.util.mapper;

import java.util.Collection;
import java.util.HashSet;

import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.CustomerRole;
import fr.hoteia.qalingo.core.pojo.CustomerGroupPojo;
import fr.hoteia.qalingo.core.pojo.CustomerRolePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
        object.setCustomerRoles(new HashSet<CustomerRole>(customerRoles));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CustomerGroup object, CustomerGroupPojo jsonPojo) {
        Collection<CustomerRolePojo> customerRoles = customerRoleMapper.toPojo(object.getCustomerRoles());
        jsonPojo.setCustomerRoles(customerRoles);
    }
}
