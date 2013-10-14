package fr.hoteia.qalingo.core.pojo.util.impl;

import fr.hoteia.qalingo.core.domain.Asset;
import fr.hoteia.qalingo.core.pojo.AssetPojo;
import org.springframework.stereotype.Component;

@Component("assetMapper")
public class AssetMapper extends AbstractPojoMapper<Asset, AssetPojo> {

    @Override
    public Class<Asset> getObjectType() {
        return Asset.class;
    }

    @Override
    public Class<AssetPojo> getPojoType() {
        return AssetPojo.class;
    }
}
