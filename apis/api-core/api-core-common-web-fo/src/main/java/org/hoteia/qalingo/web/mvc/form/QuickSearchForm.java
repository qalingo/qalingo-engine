/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

/**
 * 
 * 
 */
public class QuickSearchForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = -4079230207549008682L;
    
	private String text;
    
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((text == null) ? 0 : text.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    QuickSearchForm other = (QuickSearchForm) obj;
	    if (text == null) {
		    if (other.text != null)
			    return false;
	    } else if (!text.equals(other.text))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "QuickSearchForm [text=" + text + "]";
    }
	
}