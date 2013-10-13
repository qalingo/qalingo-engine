package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.pojo.CustomerAddressPojo;

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
