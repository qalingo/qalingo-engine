package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.RetailerTag;
import fr.hoteia.qalingo.core.pojo.RetailerTagPojo;
import org.springframework.stereotype.Component;

@Component("retailerTagMapper")
public class RetailerTagMapper extends AbstractPojoMapper<RetailerTag, RetailerTagPojo> {
    @Override
    public Class<RetailerTag> getObjectType() {
        return RetailerTag.class;
    }

    @Override
    public Class<RetailerTagPojo> getPojoType() {
        return RetailerTagPojo.class;
    }
}
