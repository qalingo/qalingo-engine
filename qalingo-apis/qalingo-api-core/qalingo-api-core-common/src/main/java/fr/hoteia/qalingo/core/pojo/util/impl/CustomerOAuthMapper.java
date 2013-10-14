package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.CustomerOAuth;
import fr.hoteia.qalingo.core.pojo.CustomerOAuthPojo;
import org.springframework.stereotype.Component;

@Component("customerOAuthMapper")
public class CustomerOAuthMapper extends AbstractPojoMapper<CustomerOAuth, CustomerOAuthPojo>{

    @Override
    public Class<CustomerOAuth> getObjectType() {
        return CustomerOAuth.class;
    }

    @Override
    public Class<CustomerOAuthPojo> getPojoType() {
        return CustomerOAuthPojo.class;
    }
}
