/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.json.pojo;

import org.codehaus.jackson.annotate.JsonAnySetter;

/**
 *
 * <p>
 * <a href="AbstractJsonBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractJsonPojo {

	public AbstractJsonPojo() {
	}
	
	@JsonAnySetter
	public void handleUnknown(String key, Object value) {
		// do something: put to a Map; log a warning, whatever
	}
	
}