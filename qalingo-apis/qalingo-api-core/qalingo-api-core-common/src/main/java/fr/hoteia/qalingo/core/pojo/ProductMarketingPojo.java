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
    private Collection<ProductMarketingAttribute> productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttribute>();
    private Collection<ProductMarketingAttribute> productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttribute>();
    private Collection<ProductSku> productSkus = new ArrayList<ProductSku>();
    private Collection<ProductAssociationLink> productAssociationLinks = new ArrayList<ProductAssociationLink>();
    private Collection<Asset> assetsIsGlobal = new ArrayList<Asset>();
    private Collection<Asset> assetsByMarketArea = new ArrayList<Asset>();
}
