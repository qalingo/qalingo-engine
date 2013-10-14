package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CustomerOptin;
import fr.hoteia.qalingo.core.pojo.CustomerOptinPojo;
import org.springframework.stereotype.Component;

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
