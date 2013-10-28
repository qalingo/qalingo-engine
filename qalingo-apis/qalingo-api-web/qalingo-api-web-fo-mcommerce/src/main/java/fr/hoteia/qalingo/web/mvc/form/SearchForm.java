/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.web.mvc.form;

import java.io.Serializable;

/**
 * 
 * 
 */
public class SearchForm implements Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = -1243393136279082685L;
    
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
	    SearchForm other = (SearchForm) obj;
	    if (text == null) {
		    if (other.text != null)
			    return false;
	    } else if (!text.equals(other.text))
		    return false;
	    return true;
    }

	@Override
    public String toString() {
	    return "SearchForm [text=" + text + "]";
    }
	
}