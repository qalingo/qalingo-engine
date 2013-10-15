package fr.hoteia.qalingo.core.pojo.util.mapper.customer;

import fr.hoteia.qalingo.core.domain.CustomerProductComment;
import fr.hoteia.qalingo.core.pojo.customer.CustomerProductCommentPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
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
