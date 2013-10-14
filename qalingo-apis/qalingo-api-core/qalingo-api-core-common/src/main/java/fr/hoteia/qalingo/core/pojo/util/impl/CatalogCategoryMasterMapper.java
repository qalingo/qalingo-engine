package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.pojo.AssetPojo;
import fr.hoteia.qalingo.core.pojo.CatalogCategoryMasterPojo;
import fr.hoteia.qalingo.core.pojo.util.PojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("catalogCategoryMasterMapper")
public class CatalogCategoryMasterMapper extends AbstractPojoMapper<CatalogCategoryMaster, CatalogCategoryMasterPojo> {

    @Autowired @Qualifier("assetMapper") private PojoMapper<Asset, AssetPojo> assetMapper;

    @Override
    public Class<CatalogCategoryMaster> getObjectType() {
        return CatalogCategoryMaster.class;
    }

    @Override
    public Class<CatalogCategoryMasterPojo> getPojoType() {
        return CatalogCategoryMasterPojo.class;
    }
}
