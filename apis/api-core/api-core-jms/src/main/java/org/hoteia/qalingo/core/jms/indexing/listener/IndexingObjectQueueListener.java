/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.indexing.listener;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductMarketing_;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice_;
import org.hoteia.qalingo.core.domain.ProductSku_;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Store_;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.jms.indexing.producer.IndexingObjectMessageJms;
import org.hoteia.qalingo.core.mapper.XmlMapper;
import org.hoteia.qalingo.core.service.CatalogCategoryService;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.service.RetailerService;
import org.hoteia.qalingo.core.solr.service.ProductMarketingSolrService;
import org.hoteia.qalingo.core.solr.service.ProductSkuSolrService;
import org.hoteia.qalingo.core.solr.service.StoreSolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "indexingObjectQueueListener")
public class IndexingObjectQueueListener implements MessageListener, ExceptionListener {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected XmlMapper xmlMapper;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;
    
    @Autowired
    protected ProductService productService;
    
    @Autowired
    protected RetailerService retailerService;
    
    @Autowired
    protected ProductMarketingSolrService productMarketingSolrService;
    
    @Autowired
    protected ProductSkuSolrService productSkuSolrService;
    
    @Autowired
    protected StoreSolrService storeSolrService;
    
    protected List<SpecificFetchMode> productMarketingFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> productSkuFetchPlans = new ArrayList<SpecificFetchMode>();
    protected List<SpecificFetchMode> storeFetchPlans = new ArrayList<SpecificFetchMode>();

    public IndexingObjectQueueListener() {
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productBrand.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productMarketingType.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.attributes.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productSkus.getName() + "." + ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.productAssociationLinks.getName()));
        productMarketingFetchPlans.add(new SpecificFetchMode(ProductMarketing_.assets.getName()));
        
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.productMarketing.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.attributes.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.prices.getName() + "." + ProductSkuPrice_.currency.getName()));
        productSkuFetchPlans.add(new SpecificFetchMode(ProductSku_.assets.getName()));
        
        storeFetchPlans.add(new SpecificFetchMode(Store_.retailer.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.attributes.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.businessHours.getName()));
        storeFetchPlans.add(new SpecificFetchMode(Store_.assets.getName()));
    }
    
    /**
     * Implementation of <code>MessageListener</code>.
     */
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                String valueJMSMessage = tm.getText();
                
                if(StringUtils.isNotEmpty(valueJMSMessage)){
                    final IndexingObjectMessageJms doucmentMessageJms = xmlMapper.getXmlMapper().readValue(valueJMSMessage, IndexingObjectMessageJms.class);
                    Long objectId = doucmentMessageJms.getObjectId();

                    if("ProductMarketing".equals(doucmentMessageJms.getObjectType())){
                        final ProductMarketing productMarketing = productService.getProductMarketingById(objectId, new FetchPlan(productMarketingFetchPlans));
                        if(productMarketing != null){
                            ProductSku productSku = productMarketing.getDefaultProductSku();
                            if(productSku != null){
                                List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId()); 
                                try {
                                    productMarketingSolrService.addOrUpdateProductMarketing(productMarketing, catalogCategories, null, null);
                                } catch (SolrServerException e) {
                                    logger.error("Processed message to indexing failed, value: " + valueJMSMessage);
                                }
                            }
                        }
                    } else if("ProductSku".equals(doucmentMessageJms.getObjectType())){
                        final ProductSku productSku = productService.getProductSkuById(objectId, new FetchPlan(productSkuFetchPlans));
                        if(productSku != null){
                            List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId()); 
                            try {
                                productSkuSolrService.addOrUpdateProductSku(productSku, catalogCategories, null, null);
                            } catch (SolrServerException e) {
                                logger.error("Processed message to indexing failed, value: " + valueJMSMessage);
                            }
                        }
                        
                    } else if("Store".equals(doucmentMessageJms.getObjectType())){
                        final Store store = retailerService.getStoreById(objectId, new FetchPlan(storeFetchPlans));
                        if(store != null){
                            try {
                                storeSolrService.addOrUpdateStore(store);
                            } catch (SolrServerException e) {
                                logger.error("Processed message to indexing failed, value: " + valueJMSMessage);
                            }
                        }
                    }
                    
                    if (logger.isDebugEnabled()) {
                        logger.debug("Processed message, value: " + valueJMSMessage);
                    }
                } else {
                    logger.warn("Document generation: Jms Message is empty");
                }
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void exceptionThrown(Exception e) {
        logger.debug("Exception on queue listener: " + e.getCause() + ":" + e.getLocalizedMessage());
    }

}