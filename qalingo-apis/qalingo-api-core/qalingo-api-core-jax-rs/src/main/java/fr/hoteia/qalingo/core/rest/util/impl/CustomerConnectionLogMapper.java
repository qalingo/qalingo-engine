package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.rest.pojo.CustomerConnectionLogPojo;

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
