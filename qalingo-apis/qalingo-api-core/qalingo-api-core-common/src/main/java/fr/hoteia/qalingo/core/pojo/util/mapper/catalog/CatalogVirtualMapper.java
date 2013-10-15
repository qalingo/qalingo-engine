package fr.hoteia.qalingo.core.pojo.util.mapper.catalog;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryMasterPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogCategoryVirtualPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import fr.hoteia.qalingo.core.pojo.catalog.CatalogVirtualPojo;
import fr.hoteia.qalingo.core.pojo.util.mapper.AbstractPojoMapper;
import fr.hoteia.qalingo.core.pojo.util.mapper.PojoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("catalogVirtualMapper")
public class CatalogVirtualMapper extends AbstractPojoMapper<CatalogVirtual, CatalogVirtualPojo> {

    private static final String[] IGNORED_PROPERTIES = {"catalogMaster", "productCategories"};

    @Autowired @Qualifier("catalogMasterMapper") private PojoMapper<CatalogMaster, CatalogMasterPojo> catalogMasterMapper;
    @Autowired @Qualifier("catalogCategoryVirtualMapper") private PojoMapper<CatalogCategoryVirtual, CatalogCategoryVirtualPojo> categoryMapper;

    @Override
    public Class<CatalogVirtual> getObjectType() {
        return CatalogVirtual.class;
    }

    @Override
    public Class<CatalogVirtualPojo> getPojoType() {
        return CatalogVirtualPojo.class;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(CatalogVirtualPojo jsonPojo, CatalogVirtual object) {
        object.setCatalogMaster(catalogMasterMapper.fromPojo(jsonPojo.getCatalogMaster()));
        Collection<CatalogCategoryVirtual> catalogCategoryMasters = categoryMapper.fromPojo(jsonPojo.getProductCategories());
        object.setProductCategories(asSet(catalogCategoryMasters));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CatalogVirtual object, CatalogVirtualPojo jsonPojo) {
        jsonPojo.setCatalogMaster(catalogMasterMapper.toPojo(object.getCatalogMaster()));
        Collection<CatalogCategoryVirtualPojo> productCategories = categoryMapper.toPojo(object.getProductCategories());
        jsonPojo.setProductCategories(productCategories);
    }
}
