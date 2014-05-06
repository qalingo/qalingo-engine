/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class QueryTest {
	
	@Test
	public void testQuery(){
		String fq = "price:[%1$,.0f TO %2$,.0f]";
		BigDecimal start = new BigDecimal(10d);
		BigDecimal end = new BigDecimal(15d);
		String formatted = String.format(fq, Double.valueOf(start.doubleValue()), Double.valueOf(end.doubleValue()));
		Assert.assertEquals("price:[10 TO 15]", formatted);
		
		fq = "catalogCategories:%s";
		formatted = String.format(fq, "CATE302");
		Assert.assertEquals("catalogCategories:CATE302", formatted);
	}
}
