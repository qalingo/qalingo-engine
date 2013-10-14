package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.pojo.CustomerProductCommentPojo;
import org.springframework.stereotype.Component;

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
