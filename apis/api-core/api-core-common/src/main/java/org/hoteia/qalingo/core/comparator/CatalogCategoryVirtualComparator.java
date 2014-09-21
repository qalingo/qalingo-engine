package org.hoteia.qalingo.core.comparator;

import java.util.Comparator;

import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;

public class CatalogCategoryVirtualComparator implements Comparator<CatalogCategoryVirtual> {

    public int compare(CatalogCategoryVirtual o1, CatalogCategoryVirtual o2) {
        if (o1 != null && o1.getRanking() != null && o2 != null && o2.getRanking() != null) {
            return o1.getRanking().compareTo(o2.getRanking());
        }
        return 0;
    }

}
