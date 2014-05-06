/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.util.Date;
import java.util.Set;

public abstract class AbstractCatalog<A> extends AbstractEntity {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = -4914956282323784817L;

    public abstract Long getId();

    public abstract int getVersion();

    public abstract String getCode();
    
    public abstract String getName();
    
    public abstract boolean isDefault() ;

    public abstract String getDescription();

    public abstract Set<A> getCatalogCategories();
    
    public abstract Date getDateCreate();

    public abstract Date getDateUpdate();

}
