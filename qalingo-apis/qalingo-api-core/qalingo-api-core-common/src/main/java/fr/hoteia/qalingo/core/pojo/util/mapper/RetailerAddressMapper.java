package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.RetailerAddress;
import fr.hoteia.qalingo.core.pojo.RetailerAddressPojo;
import org.springframework.stereotype.Component;

@Component("retailerAddressMapper")
public class RetailerAddressMapper extends AbstractPojoMapper<RetailerAddress, RetailerAddressPojo> {
    @Override
    public Class<RetailerAddress> getObjectType() {
        return RetailerAddress.class;
    }

    @Override
    public Class<RetailerAddressPojo> getPojoType() {
        return RetailerAddressPojo.class;
    }
}
