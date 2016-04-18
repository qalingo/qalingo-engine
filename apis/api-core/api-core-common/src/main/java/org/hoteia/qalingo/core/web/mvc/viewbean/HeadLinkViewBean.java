/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;

public class HeadLinkViewBean implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 3100619135421675045L;
    
    protected String disabled;
    protected String media;
    protected String methods ;
    protected String sizes;
    protected String target;
    protected String type;
    protected String rel;
    protected String hreflang;
    protected String href;
	
    public HeadLinkViewBean() {
    }
    
	public HeadLinkViewBean(String rel, String hreflang, String href){
        this.rel = rel;
        this.hreflang = hreflang;
        this.href = href;
	}

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHreflang() {
        return hreflang;
    }

    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((href == null) ? 0 : href.hashCode());
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
        HeadLinkViewBean other = (HeadLinkViewBean) obj;
        if (href == null) {
            if (other.href != null)
                return false;
        } else if (!href.equals(other.href))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HeadLinkViewBean [disabled=" + disabled + ", media=" + media + ", methods=" + methods + ", sizes=" + sizes + ", target=" + target + ", type=" + type + ", rel=" + rel + ", hreflang="
                + hreflang + ", href=" + href + "]";
    }
	
}