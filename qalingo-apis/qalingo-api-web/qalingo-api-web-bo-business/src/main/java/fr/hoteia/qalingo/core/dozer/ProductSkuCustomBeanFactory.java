package fr.hoteia.qalingo.core.dozer;

import org.dozer.BeanFactory;

import fr.hoteia.qalingo.core.pojo.product.BoProductSkuPojo;

public class ProductSkuCustomBeanFactory implements BeanFactory  {

    @Override
    public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {
        return new BoProductSkuPojo();
    }

}