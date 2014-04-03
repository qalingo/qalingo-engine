package org.hoteia.qalingo.core.dozer;

import javax.servlet.http.HttpServletRequest;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.ImageSize;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.pojo.cart.FoCartItemPojo;
import org.hoteia.qalingo.core.pojo.deliverymethod.FoDeliveryMethodPojo;
import org.hoteia.qalingo.core.pojo.product.ProductSkuPojo;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "frontofficePojoEventListener")
public class FrontofficePojoEventListener implements DozerEventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired 
    private HttpServletRequest httpServletRequest;
    
    @Autowired
    protected RequestUtil requestUtil;
    
    @Autowired 
    protected UrlService urlService;
    
    public FrontofficePojoEventListener() {
    }

    @Override
    public void mappingStarted(DozerEvent event) {
        logger.debug("mapping started, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void preWritingDestinationValue(DozerEvent event) {
        logger.debug("pre writing destination value, SourceObject: " + event.getSourceObject());
    }

    @Override
    public void postWritingDestinationValue(DozerEvent event) {
        logger.debug("post writing destination value, SourceObject: " + event.getSourceObject());
        if(event.getDestinationObject() instanceof FoCartItemPojo){
            if(event.getFieldMap().getDestFieldName().equals("productSkuCode")){
                // INJECT BACKOFFICE URLS
                CartItem cartItem = (CartItem) event.getSourceObject();
                FoCartItemPojo cartItemPojo = (FoCartItemPojo) event.getDestinationObject();
                try {
                    final RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    final MarketArea marketArea = requestData.getMarketArea();
                    final Retailer retailer = requestData.getMarketAreaRetailer();
                    final Localization localization = requestData.getMarketAreaLocalization();
                    final String localizationCode = localization.getCode();
                    
                    final Asset defaultPaskshotImage = cartItem.getProductSku().getDefaultPaskshotImage(ImageSize.SMALL.name());
                    if (defaultPaskshotImage != null) {
                        String summaryImage = requestUtil.getProductMarketingImageWebPath(defaultPaskshotImage);
                        cartItemPojo.setSummaryImage(summaryImage);
                    } else {
                        cartItemPojo.setSummaryImage("");
                    }
                    
                    cartItemPojo.setI18nName(cartItem.getProductSku().getI18nName(localizationCode));
                    
                    cartItemPojo.setProductDetailsUrl(urlService.generateUrl(FoUrls.PRODUCT_DETAILS, requestData, cartItem.getCatalogCategory(), cartItem.getProductMarketing(), cartItem.getProductSku()));

                    cartItemPojo.setPriceWithStandardCurrencySign(cartItem.getPriceWithStandardCurrencySign(marketArea.getId(), retailer.getId()));
                    cartItemPojo.setTotalAmountWithStandardCurrencySign(cartItem.getTotalAmountWithStandardCurrencySign(marketArea.getId(), retailer.getId()));

                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with FoCartItemPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof ProductSkuPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                ProductSku productSku = (ProductSku) event.getSourceObject();
                ProductSkuPojo productSkuPojo = (ProductSkuPojo) event.getDestinationObject();
                try {
                    final RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    final MarketArea marketArea = requestData.getMarketArea();
                    final Retailer retailer = requestData.getMarketAreaRetailer();
                    final Localization localization = requestData.getMarketAreaLocalization();
                    final String localizationCode = localization.getCode();
                    
                    final Asset defaultPaskshotImage = productSku.getDefaultPaskshotImage(ImageSize.SMALL.name());
                    if (defaultPaskshotImage != null) {
                        String summaryImage = requestUtil.getProductMarketingImageWebPath(defaultPaskshotImage);
                        productSkuPojo.setDefaultPaskshotImage(summaryImage);
                    } else {
                        productSkuPojo.setDefaultPaskshotImage("");
                    }
                    
                    productSkuPojo.setI18nName(productSku.getI18nName(localizationCode));
                    
                    productSkuPojo.setPriceWithStandardCurrencySign(productSku.getPrice(marketArea.getId(), retailer.getId()).getPriceWithStandardCurrencySign());

                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with ProductSkuPojo", e);
                }
            }
        } else if(event.getDestinationObject() instanceof FoDeliveryMethodPojo){
            if(event.getFieldMap().getDestFieldName().equals("code")){
                // INJECT BACKOFFICE URLS
                DeliveryMethod deliveryMethod = (DeliveryMethod) event.getSourceObject();
                FoDeliveryMethodPojo deliveryMethodPojo = (FoDeliveryMethodPojo) event.getDestinationObject();
                try {
                    final RequestData requestData = requestUtil.getRequestData(httpServletRequest);
                    final MarketArea marketArea = requestData.getMarketArea();
                    final Retailer retailer = requestData.getMarketAreaRetailer();
                    final Cart cart = requestData.getCart();
                    
                    deliveryMethodPojo.setArrivalTime("??");
                    deliveryMethodPojo.setPrice(deliveryMethod.getPrice(marketArea.getId(), retailer.getId()));
                    deliveryMethodPojo.setPriceWithStandardCurrencySign(deliveryMethod.getPriceWithStandardCurrencySign(marketArea.getId(), retailer.getId()));

                    if(cart != null
                            && cart.getDeliveryMethods().contains(deliveryMethod)){
                        deliveryMethodPojo.setSelected(true);
                    }
                } catch (Exception e) {
                    logger.error("postWritingDestinationValue error with FoDeliveryMethodPojo", e);
                }
            }
        }
    }

    @Override
    public void mappingFinished(DozerEvent event) {
        logger.debug("mapping finished, SourceObject: " + event.getSourceObject());
    }
    
}