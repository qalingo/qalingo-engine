package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.pojo.CustomerConnectionLogPojo;
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
