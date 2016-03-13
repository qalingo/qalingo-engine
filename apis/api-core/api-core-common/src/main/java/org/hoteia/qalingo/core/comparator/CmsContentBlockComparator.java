package org.hoteia.qalingo.core.comparator;

import java.util.Comparator;

import org.hoteia.qalingo.core.domain.CmsContentBlock;

public class CmsContentBlockComparator implements Comparator<CmsContentBlock> {

    public int compare(CmsContentBlock o1, CmsContentBlock o2) {
        if (o1 != null && o2 != null) {
        	int result = ((Integer)o1.getOrdering()).compareTo(((Integer)o2.getOrdering()));
        	if(result == 0){
                return ((Long)o1.getId()).compareTo(((Long)o2.getId()));
        	}
        	return result;
        }
        return 0;
    }

}
