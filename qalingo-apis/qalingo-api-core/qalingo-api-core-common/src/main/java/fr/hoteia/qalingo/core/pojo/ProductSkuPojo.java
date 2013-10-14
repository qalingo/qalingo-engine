package fr.hoteia.qalingo.core.pojo;

import fr.hoteia.qalingo.core.domain.ProductSkuPrice;
import fr.hoteia.qalingo.core.domain.ProductSkuStock;
import fr.hoteia.qalingo.core.domain.Retailer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ProductSkuPojo {

    private Long id;
    private int version;
    private String businessName;
    private String description;
    private boolean isDefault;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;

    private ProductMarketingPojo productMarketing;

    private Collection<ProductSkuAttributePojo> productSkuGlobalAttributes = new ArrayList<ProductSkuAttributePojo>();
    private Collection<ProductSkuAttributePojo> productSkuMarketAreaAttributes = new ArrayList<ProductSkuAttributePojo>();
    private Collection<AssetPojo> assetsIsGlobal = new ArrayList<AssetPojo>();
    private Collection<AssetPojo> assetsByMarketArea = new ArrayList<AssetPojo>();
    private Collection<ProductSkuPrice> prices = new ArrayList<ProductSkuPrice>();
    private Collection<ProductSkuStock> stocks = new ArrayList<ProductSkuStock>();
    private Collection<Retailer> retailers = new ArrayList<Retailer>();
}
