package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.pojo.CustomerAddressPojo;
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
