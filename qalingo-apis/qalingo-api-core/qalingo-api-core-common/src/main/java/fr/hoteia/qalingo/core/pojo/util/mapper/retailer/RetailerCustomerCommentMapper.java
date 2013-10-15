package fr.hoteia.qalingo.core.pojo.util.mapper.retailer;

import java.util.Collection;

import fr.hoteia.qalingo.core.domain.RetailerCustomerComment;
import fr.hoteia.qalingo.core.pojo.retailer.RetailerCustomerCommentPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil;
import org.springframework.stereotype.Component;

@Component("retailerCustomerCommentMapper")
public class RetailerCustomerCommentMapper extends AbstractPojoMapper<RetailerCustomerComment, RetailerCustomerCommentPojo> {

    private static final String[] IGNORED_PROPERTIES = {"customerComments"};

    @Override
    public Class<RetailerCustomerComment> getObjectType() {
        return RetailerCustomerComment.class;
    }

    @Override
    public Class<RetailerCustomerCommentPojo> getPojoType() {
        return RetailerCustomerCommentPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(RetailerCustomerCommentPojo jsonPojo, RetailerCustomerComment object) {
        Collection<RetailerCustomerComment> retailerCustomerComments = fromPojo(jsonPojo.getCustomerComments());
        object.setCustomerComments(PojoUtil.asSet(retailerCustomerComments));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(RetailerCustomerComment object, RetailerCustomerCommentPojo jsonPojo) {
        jsonPojo.setCustomerComments(toPojo(object.getCustomerComments()));
    }
}
