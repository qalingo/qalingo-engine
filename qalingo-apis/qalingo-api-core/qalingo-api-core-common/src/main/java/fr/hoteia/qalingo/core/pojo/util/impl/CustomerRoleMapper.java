package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.CustomerPermission;
import fr.hoteia.qalingo.core.domain.CustomerRole;
import fr.hoteia.qalingo.core.pojo.CustomerPermissionPojo;
import fr.hoteia.qalingo.core.pojo.CustomerRolePojo;
import fr.hoteia.qalingo.core.pojo.util.PojoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component("customerRoleMapper")
public class CustomerRoleMapper extends AbstractPojoMapper<CustomerRole, CustomerRolePojo> {

    private static final String[] IGNORED_PROPERTIES = new String[]{"rolePermissions"};

    @Autowired @Qualifier("customerPermissionMapper") private PojoMapper<CustomerPermission, CustomerPermissionPojo> customerPermissionMapper;

    @Override
    public Class<CustomerRole> getObjectType() {
        return CustomerRole.class;
    }

    @Override
    public Class<CustomerRolePojo> getPojoType() {
        return CustomerRolePojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(CustomerRolePojo jsonPojo, CustomerRole object) {
        Collection<CustomerPermission> customerPermissions = customerPermissionMapper.fromPojo(jsonPojo.getRolePermissions());
        object.setRolePermissions(new HashSet<CustomerPermission>(customerPermissions));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CustomerRole object, CustomerRolePojo jsonPojo) {
        Collection<CustomerPermissionPojo> rolePermissions = customerPermissionMapper.toPojo(object.getRolePermissions());
        jsonPojo.setRolePermissions(rolePermissions);
    }
}
