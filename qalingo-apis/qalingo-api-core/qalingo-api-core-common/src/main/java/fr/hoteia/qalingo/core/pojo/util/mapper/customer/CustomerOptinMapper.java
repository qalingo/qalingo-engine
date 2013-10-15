package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.CustomerOptin;
import fr.hoteia.qalingo.core.pojo.customer.CustomerOptinPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
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
