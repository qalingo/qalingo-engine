package org.hoteia.qalingo.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CacheMethodInformation {

    String cacheName() default "";

    String cacheType();

}
