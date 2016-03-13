package org.hoteia.qalingo.core.comparator;

import java.util.Comparator;

import org.hoteia.qalingo.core.domain.CmsMenu;

public class CmsMenuComparator implements Comparator<CmsMenu> {

    public int compare(CmsMenu o1, CmsMenu o2) {
        if (o1 != null && o2 != null) {
            return ((Integer)o1.getOrdering()).compareTo(((Integer)o2.getOrdering()));
        }
        return 0;
    }

}
