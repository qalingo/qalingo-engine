package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.pojo.customer.CustomerConnectionLogPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import org.springframework.stereotype.Component;

@Component("customerConnectionLogMapper")
public class CustomerConnectionLogMapper extends AbstractPojoMapper<CustomerConnectionLog, CustomerConnectionLogPojo> {

    @Override
    public Class<CustomerConnectionLog> getObjectType() {
        return CustomerConnectionLog.class;
    }

    @Override
    public Class<CustomerConnectionLogPojo> getPojoType() {
        return CustomerConnectionLogPojo.class;
    }

}
