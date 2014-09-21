package org.hoteia.qalingo.core.comparator;

import java.util.Comparator;

import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;

public class CatalogCategoryMasterComparator implements Comparator<CatalogCategoryMaster> {

    public int compare(CatalogCategoryMaster o1, CatalogCategoryMaster o2) {
        if (o1 != null && o1.getRanking() != null && o2 != null && o2.getRanking() != null) {
            return o1.getRanking().compareTo(o2.getRanking());
        }
        return 0;
    }

}
