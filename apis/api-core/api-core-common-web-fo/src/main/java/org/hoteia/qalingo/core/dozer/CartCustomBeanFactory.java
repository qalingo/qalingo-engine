package org.hoteia.qalingo.core.dozer;

import org.dozer.BeanFactory;
import org.hoteia.qalingo.core.pojo.cart.FoCartItemPojo;

public class CartCustomBeanFactory implements BeanFactory  {

    @Override
    public Object createBean(Object source, Class<?> sourceClass, String targetBeanId) {
        return new FoCartItemPojo();
    }

}