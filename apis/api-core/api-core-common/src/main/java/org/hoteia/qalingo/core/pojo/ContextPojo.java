package org.hoteia.qalingo.core.pojo;

import java.util.ArrayList;
import java.util.List;

public class ContextPojo {

    private String marketPlaceCode;
    private String marketCode;
    private String marketAreaCode;
    private String marketAreaLocalizationCode;
    private String marketAreaRetailerCode;
    private String marketAreaCurrencyCode;

    private String backoffcieLocalizationCode;

    private String customerCode;
    private String userCode;
    private String companyCode;
    
    private List<UrlPojo> urls = new ArrayList<UrlPojo>();
    
    public List<UrlPojo> getUrls() {
        return urls;
    }
    
    public void setUrls(List<UrlPojo> urls) {
        this.urls = urls;
    }
    
}
