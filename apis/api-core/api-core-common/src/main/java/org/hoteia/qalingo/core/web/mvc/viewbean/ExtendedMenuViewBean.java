package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.HashSet;
import java.util.Set;

public class ExtendedMenuViewBean extends MenuViewBean {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -5317549193150175454L;

    private Set<CmsContentBlockViewBean> blocks = new HashSet<CmsContentBlockViewBean>();
	
	public Set<CmsContentBlockViewBean> getBlocks() {
		return blocks;
	}

	public CmsContentBlockViewBean getCmsBlock() {
		if (blocks != null && blocks.size() > 0) {
			return blocks.iterator().next();
		}
		return null;
	}
	
	public void setBlocks(Set<CmsContentBlockViewBean> blocks) {
		this.blocks = blocks;
	}

    public boolean getHasCmsBlock() {
        if (blocks != null && blocks.size() > 0) {
            return true;
        }
        return false;
    }
    
}