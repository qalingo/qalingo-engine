package fr.hoteia.qalingo.core.pojo.util.impl;

import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.CustomerOptin;
import fr.hoteia.qalingo.core.pojo.CustomerOptinPojo;

@Component("customerOptinMapper")
public class CustomerOptinMapper extends AbstractPojoMapper<CustomerOptin, CustomerOptinPojo> {

    @Override
    public Class<CustomerOptin> getObjectType() {
        return CustomerOptin.class;
    }

    @Override
    public Class<CustomerOptinPojo> getPojoType() {
        return CustomerOptinPojo.class;
    }

}
