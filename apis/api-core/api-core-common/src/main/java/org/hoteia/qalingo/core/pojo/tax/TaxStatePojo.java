/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.pojo.tax;


public class TaxStatePojo {

	private Long id;
	private String codeCounty;
	
	public TaxStatePojo() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeCounty() {
		return codeCounty;
	}
	
	public void setCodeCounty(String codeCounty) {
		this.codeCounty = codeCounty;
	}
	
}