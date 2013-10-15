package fr.hoteia.qalingo.core.pojo;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import fr.hoteia.qalingo.core.domain.RetailerAttribute;
import fr.hoteia.qalingo.core.domain.RetailerCustomerComment;
import fr.hoteia.qalingo.core.domain.RetailerCustomerRate;
import fr.hoteia.qalingo.core.domain.RetailerTag;

public class RetailerPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String logo;
    private boolean isDefault;
    private boolean isOfficialRetailer;
    private boolean isBrand;
    private boolean isEcommerce;
    private String code;
    private int qualityOfService;
    private int priceScore;
    private int ratioQualityPrice;
    private Date dateCreate;
    private Date dateUpdate;

    private Collection<RetailerLinkPojo> links = Collections.emptyList();
    private Collection<RetailerAddressPojo> addresses = Collections.emptyList();
    private Collection<StorePojo> stores = Collections.emptyList();
    private Collection<AssetPojo> assetsIsGlobal = Collections.emptyList();
    private Collection<AssetPojo> assetsByMarketArea = Collections.emptyList();
    private Collection<RetailerAttribute> retailerGlobalAttributes = Collections.emptyList();
    private Collection<RetailerAttribute> retailerMarketAreaAttributes = Collections.emptyList();
    private Collection<RetailerCustomerRate> customerRates = Collections.emptyList();
    private Collection<RetailerCustomerComment> customerComments = Collections.emptyList();
    private Collection<RetailerTag> retailerTags = Collections.emptyList();
}
