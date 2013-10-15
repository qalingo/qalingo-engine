package fr.hoteia.qalingo.core.pojo.util.mapper.retailer;


import fr.hoteia.qalingo.core.domain.RetailerCustomerRate;
import fr.hoteia.qalingo.core.pojo.retailer.RetailerCustomerRatePojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import org.springframework.stereotype.Component;

@Component("retailerCustomerRateMapper")
public class RetailerCustomerRateMapper extends AbstractPojoMapper<RetailerCustomerRate, RetailerCustomerRatePojo> {
    @Override
    public Class<RetailerCustomerRate> getObjectType() {
        return RetailerCustomerRate.class;
    }

    @Override
    public Class<RetailerCustomerRatePojo> getPojoType() {
        return RetailerCustomerRatePojo.class;
    }
}
