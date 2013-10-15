package fr.hoteia.qalingo.core.pojo.util.mapper.retailer;

import fr.hoteia.qalingo.core.domain.RetailerLink;
import fr.hoteia.qalingo.core.pojo.retailer.RetailerLinkPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import org.springframework.stereotype.Component;

@Component("retailerLinkMapper")
public class RetailerLinkMapper extends AbstractPojoMapper<RetailerLink, RetailerLinkPojo> {
    @Override
    public Class<RetailerLink> getObjectType() {
        return RetailerLink.class;
    }

    @Override
    public Class<RetailerLinkPojo> getPojoType() {
        return RetailerLinkPojo.class;
    }
}
