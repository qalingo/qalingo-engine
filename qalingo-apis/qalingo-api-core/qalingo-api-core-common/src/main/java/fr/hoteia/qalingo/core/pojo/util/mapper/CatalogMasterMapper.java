package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.pojo.CatalogCategoryMasterPojo;
import fr.hoteia.qalingo.core.pojo.CatalogMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static fr.hoteia.qalingo.core.pojo.util.mapper.PojoUtil.asSet;

@Component("catalogMasterMapper")
public class CatalogMasterMapper extends AbstractPojoMapper<CatalogMaster, CatalogMasterPojo> {

    private static final String[] IGNORED_PROPERTIES = new String[]{"productCategories"};

    @Autowired @Qualifier("catalogCategoryMasterMapper") private PojoMapper<CatalogCategoryMaster, CatalogCategoryMasterPojo> categoryMasterMapper;

    @Override
    public Class<CatalogMaster> getObjectType() {
        return CatalogMaster.class;
    }

    @Override
    public Class<CatalogMasterPojo> getPojoType() {
        return CatalogMasterPojo.class;
    }

    @Override
    protected String[] getIgnoredProperties() {
        return IGNORED_PROPERTIES;
    }

    @Override
    protected void mapAdditionalPropertiesFromPojo(CatalogMasterPojo jsonPojo, CatalogMaster object) {
        Collection<CatalogCategoryMaster> catalogCategoryMasters = categoryMasterMapper.fromPojo(jsonPojo.getProductCategories());
        object.setProductCategories(asSet(catalogCategoryMasters));
    }

    @Override
    protected void mapAdditionalPropertiesToPojo(CatalogMaster object, CatalogMasterPojo jsonPojo) {
        Collection<CatalogCategoryMasterPojo> productCategories = categoryMasterMapper.toPojo(object.getProductCategories());
        jsonPojo.setProductCategories(productCategories);
    }
}
