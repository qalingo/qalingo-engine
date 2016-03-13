package org.hoteia.qalingo.core.comparator;

import java.util.Comparator;

import org.hoteia.qalingo.core.domain.CmsContentAsset;

public class CmsContentAssetComparator implements Comparator<CmsContentAsset> {

    public int compare(CmsContentAsset o1, CmsContentAsset o2) {
        if (o1 != null && o2 != null) {
            return ((Integer)o1.getOrdering()).compareTo(((Integer)o2.getOrdering()));
        }
        return 0;
    }

}
