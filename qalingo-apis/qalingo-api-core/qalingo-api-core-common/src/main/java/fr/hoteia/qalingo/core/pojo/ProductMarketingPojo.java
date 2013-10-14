package fr.hoteia.qalingo.core.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import fr.hoteia.qalingo.core.domain.*;

public class ProductMarketingPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private boolean isDefault;
    private String code;
    private ProductBrand productBrand;
    private ProductMarketingType productMarketingType;
    private Date dateCreate;
    private Date dateUpdate;

    private Collection<ProductMarketingAttributePojo> productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttributePojo>();
    private Collection<ProductMarketingAttributePojo> productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttributePojo>();
    private Collection<ProductSku> productSkus = new ArrayList<ProductSku>();
    private Collection<ProductAssociationLink> productAssociationLinks = new ArrayList<ProductAssociationLink>();
    private Collection<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private Collection<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
}
