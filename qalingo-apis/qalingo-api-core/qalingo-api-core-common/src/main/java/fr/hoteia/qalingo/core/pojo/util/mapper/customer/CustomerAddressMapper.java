package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.pojo.customer.CustomerAddressPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import org.springframework.stereotype.Component;

@Component("customerAddressMapper")
public class CustomerAddressMapper extends AbstractPojoMapper<CustomerAddress, CustomerAddressPojo> {

    @Override
    public Class<CustomerAddress> getObjectType() {
        return CustomerAddress.class;
    }

    @Override
    public Class<CustomerAddressPojo> getPojoType() {
        return CustomerAddressPojo.class;
    }

}
