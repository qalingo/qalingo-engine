package fr.hoteia.qalingo.core.rest.util.impl;

import org.springframework.stereotype.Component;

import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.rest.pojo.CustomerProductCommentPojo;

@Component("customerProductCommentMapper")
public class CustomerProductCommentMapper extends AbstractPojoMapper<CustomerProductComment, CustomerProductCommentPojo> {

    @Override
    public Class<CustomerProductComment> getObjectType() {
        return CustomerProductComment.class;
    }

    @Override
    public Class<CustomerProductCommentPojo> getPojoType() {
        return CustomerProductCommentPojo.class;
    }

}
