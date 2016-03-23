package org.hoteia.qalingo.core.comparator;

import java.util.Comparator;
import java.util.Date;

import org.hoteia.qalingo.core.domain.CmsContent;

public class CmsContentComparator implements Comparator<CmsContent> {

    public int compare(CmsContent o1, CmsContent o2) {
        if (o1 != null && o2 != null) {
            return ((Date)o2.getDateCreate()).compareTo(((Date)o1.getDateCreate()));
        }
        return 0;
    }

}
