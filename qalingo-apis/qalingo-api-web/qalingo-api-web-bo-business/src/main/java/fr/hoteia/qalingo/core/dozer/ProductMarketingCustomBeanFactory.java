package fr.hoteia.qalingo.core.dozer;

import org.dozer.BeanFactory;

import fr.hoteia.qalingo.core.pojo.product.BoProductMarketingPojo;

public class ProductMarketingCustomBeanFactory implements BeanFactory  {

    @Override
    public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {
        return new BoProductMarketingPojo();
    }

}