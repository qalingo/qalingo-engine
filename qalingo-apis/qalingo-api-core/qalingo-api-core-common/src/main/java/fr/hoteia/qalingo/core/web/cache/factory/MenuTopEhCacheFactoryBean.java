package fr.hoteia.qalingo.core.web.cache.factory;

import org.springframework.cache.ehcache.EhCacheFactoryBean;

public class MenuTopEhCacheFactoryBean extends EhCacheFactoryBean {

	private boolean overflowToDisk = false;
	
	private int maxElementsInMemory = 500;
	
}
