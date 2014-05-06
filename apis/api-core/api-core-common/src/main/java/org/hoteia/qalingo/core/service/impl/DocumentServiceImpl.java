/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.OrderItem;
import org.hoteia.qalingo.core.domain.OrderShipment;
import org.hoteia.qalingo.core.domain.enumtype.OrderDocumentType;
import org.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import org.hoteia.qalingo.core.i18n.enumtype.ScopeDocumentMessage;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.pojo.OrderCustomerPojo;
import org.hoteia.qalingo.core.service.DocumentService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.LocalizationService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.OrderPojoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("documentService")
@Transactional
public class DocumentServiceImpl implements DocumentService {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected LocalizationService localizationService;
    
    @Autowired
    protected CoreMessageSource coreMessageSource;
    
    @Autowired
    protected OrderPojoFactory orderPojoService;
    
    // ORDER CONFIRMATION CONFIRMATION
    
    public String getOrderConfirmationFilePath(final OrderCustomer order) {
        final String filePathPrefix = buildFilePathSegment(order);
        final String fullFilePath = filePathPrefix + OrderDocumentType.ORDER_CONFIRMATION.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
        return fullFilePath;
    }
    
    public String getOrderConfirmationWebPath(final OrderCustomer order) {
        final String documentWebRootPath = buildWebPathSegment(order);
        final String fullPath = documentWebRootPath + "/" + order.getPrefixHashFolder() + "/" + OrderDocumentType.ORDER_CONFIRMATION.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
        return fullPath;
    }
    
    public String buildOrderConfirmationFileName(final OrderCustomer order) {
        return OrderDocumentType.ORDER_CONFIRMATION.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
    }
    
    public void generateOrderConfirmation(final OrderCustomer order) {
        try {
            final MarketArea marketArea = marketService.getMarketAreaById(order.getMarketAreaId());
            final Localization localization = localizationService.getLocalizationById(order.getLocalizationId());
            final Locale locale = localization.getLocale();
            
            // WE SET TO NULL USELESS DATA - BETTER WAY SHOULD BE TO USE A SPECIFIC DOZER RULE
            for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
                OrderItem orderItem = (OrderItem) iterator.next();
                orderItem.getProductSku().setPrices(null);
                orderItem.getProductSku().setStocks(null);
                orderItem.getProductSku().setProductMarketing(null);
                orderItem.getProductSku().setRetailers(null);
            }
            
            final OrderCustomerPojo orderCustomerPojo = orderPojoService.handleOrderMapping(order);
            
            final String jrxml = getOrderConfirmationTemplateByMarketArea(marketArea);
            File fileJrxml = new File (jrxml);
            final String resourcePath = jrxml.replace(fileJrxml.getName(), "");
            final JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);

            Map <String, Object> parameters = new HashMap<String, Object>();
            parameters.put("RESOURCE_PATH", resourcePath);
            parameters.put("RECORD_DELIMITER", "\r\n");
            parameters.put("order", orderCustomerPojo);
            Object[] orderInformationsParams = { orderCustomerPojo.getOrderNum() };
            parameters.put("orderInformations", coreMessageSource.getDocumentMessage(ScopeDocumentMessage.ORDER_CONFIRMATION, "header_order_informations", orderInformationsParams, locale));
            parameters.put("date", orderCustomerPojo.getDateUpdate().toString());
            parameters.put("billingAddress", orderCustomerPojo.getBillingAddress());
            parameters.put("shippingAddress", orderCustomerPojo.getShippingAddress());
            
            Map<String, String> wording = coreMessageSource.loadWording(I18nKeyValueUniverse.DOCUMENT.getPropertyKey(), locale);
            parameters.put("wording", wording);
            
            List<OrderItem> orderItems = new ArrayList<OrderItem>(); 
            Set<OrderShipment> orderShipments = orderCustomerPojo.getOrderShipments();
            for (Iterator<OrderShipment> iterator = orderShipments.iterator(); iterator.hasNext();) {
                OrderShipment orderShipment = (OrderShipment) iterator.next();
                orderItems.addAll(orderShipment.getOrderItems());
            }
            
            // TODO : denis : one page/table by OrderShipment
            
            JRDataSource datasource = new JRBeanCollectionDataSource(orderItems, true);
            
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
            
            String fullFilePath = getOrderConfirmationFilePath(order);
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullFilePath);

        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public String getOrderConfirmationTemplateByMarketArea(final MarketArea marketArea) {
        String orderConfirmationTemplate = marketArea.getOrderConfirmationTemplate();
        if(StringUtils.isEmpty(orderConfirmationTemplate)){
            orderConfirmationTemplate = getDefaultOrderConfirmationTemplate();
        }
        return orderConfirmationTemplate;
    }

    public String getDefaultOrderConfirmationTemplate() {
        EngineSetting defaultOrderConfirmationTemplateEngineSetting = engineSettingService.getDefaultOrderConfirmationTemplate();
        String defaultOrderConfirmationTemplate = defaultOrderConfirmationTemplateEngineSetting.getDefaultValue();
        return defaultOrderConfirmationTemplate;
    }
    
    // ORDER SHIPPING CONFIRMATION
    
    public String getShippingConfirmationFilePath(final OrderCustomer order) {
        final String filePathPrefix = buildFilePathSegment(order);
        final String fullFilePath = filePathPrefix + OrderDocumentType.SHIPPING_CONFIRMATION.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
        return fullFilePath;
    }
    
    public String getShippingConfirmationWebPath(final OrderCustomer order) {
        final String documentWebRootPath = buildWebPathSegment(order);
        String fullPath = documentWebRootPath + "/" + order.getPrefixHashFolder() + "/" + OrderDocumentType.SHIPPING_CONFIRMATION.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
        return fullPath;
    }

    public String buildShippingConfirmationFileName(final OrderCustomer order) {
        return OrderDocumentType.SHIPPING_CONFIRMATION.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
    }
    
    public void generateShippingConfirmation(final OrderCustomer order) {
        try {
            final MarketArea marketArea = marketService.getMarketAreaById(order.getMarketAreaId());
            final Localization localization = localizationService.getLocalizationById(order.getLocalizationId());
            final Locale locale = localization.getLocale();
            
            // WE SET TO NULL USELESS DATA - BETTER WAY SHOULD BE TO USE A SPECIFIC DOZER RULE
            for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
                OrderItem orderItem = (OrderItem) iterator.next();
                orderItem.getProductSku().setPrices(null);
                orderItem.getProductSku().setStocks(null);
                orderItem.getProductSku().setProductMarketing(null);
                orderItem.getProductSku().setRetailers(null);
            }
            
            final OrderCustomerPojo orderCustomerPojo = orderPojoService.handleOrderMapping(order);
            
            final String jrxml = getShippingConfirmationTemplateByMarketArea(marketArea);
            File fileJrxml = new File (jrxml);
            final String resourcePath = jrxml.replace(fileJrxml.getName(), "");
            final JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);

            Map <String, Object> parameters = new HashMap<String, Object>();
            parameters.put("RESOURCE_PATH", resourcePath);
            parameters.put("RECORD_DELIMITER", "\r\n");
            parameters.put("order", orderCustomerPojo);
            Object[] orderInformationsParams = { orderCustomerPojo.getOrderNum() };
            parameters.put("orderInformations", coreMessageSource.getDocumentMessage(ScopeDocumentMessage.SHIPPING_CONFIRMATION, "header_order_informations", orderInformationsParams, locale));
            parameters.put("date", orderCustomerPojo.getDateUpdate().toString());
            parameters.put("billingAddress", orderCustomerPojo.getBillingAddress());
            parameters.put("shippingAddress", orderCustomerPojo.getShippingAddress());
            
            Map<String, String> wording = coreMessageSource.loadWording(I18nKeyValueUniverse.DOCUMENT.getPropertyKey(), locale);
            parameters.put("wording", wording);
            
            List<OrderItem> orderItems = new ArrayList<OrderItem>(); 
            Set<OrderShipment> orderShipments = orderCustomerPojo.getOrderShipments();
            for (Iterator<OrderShipment> iterator = orderShipments.iterator(); iterator.hasNext();) {
                OrderShipment orderShipment = (OrderShipment) iterator.next();
                orderItems.addAll(orderShipment.getOrderItems());
            }
            
            // TODO : denis : one page/table by OrderShipment
            
            JRDataSource datasource = new JRBeanCollectionDataSource(orderItems, true);
            
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
            
            String fullFilePath = getShippingConfirmationFilePath(order);
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullFilePath);

        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    public String getShippingConfirmationTemplateByMarketArea(final MarketArea marketArea) {
        String shippingConfirmationTemplate = marketArea.getShippingConfirmationTemplate();
        if(StringUtils.isEmpty(shippingConfirmationTemplate)){
            shippingConfirmationTemplate = getDefaultShippingConfirmationTemplate();
        }
        return shippingConfirmationTemplate;
    }

    public String getDefaultShippingConfirmationTemplate() {
        EngineSetting defaultShippingConfirmationTemplateEngineSetting = engineSettingService.getDefaultShippingConfirmationTemplate();
        String defaultShippingConfirmationTemplate = defaultShippingConfirmationTemplateEngineSetting.getDefaultValue();
        return defaultShippingConfirmationTemplate;
    }
    
    // INVOICE
    
    public String getInvoiceFilePath(final OrderCustomer order) {
        final String filePathPrefix = buildFilePathSegment(order);
        final String fullFilePath = filePathPrefix + OrderDocumentType.INVOICE.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
        return fullFilePath;
    }
    
    public String getInvoiceWebPath(final OrderCustomer order) {
        final String documentWebRootPath = buildWebPathSegment(order);
        final String fullPath = documentWebRootPath + "/" + order.getPrefixHashFolder() + "/" + OrderDocumentType.INVOICE.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
        return fullPath;
    }

    public String buildInvoiceFileName(final OrderCustomer order) {
        return OrderDocumentType.INVOICE.getPropertyKey() + "-" + order.getOrderNum() + ".pdf";
    }
    
    public void generateInvoice(final OrderCustomer order) {
        try {
            final MarketArea marketArea = marketService.getMarketAreaById(order.getMarketAreaId());
            final Localization localization = localizationService.getLocalizationById(order.getLocalizationId());
            final Locale locale = localization.getLocale();
            
            // WE SET TO NULL USELESS DATA - BETTER WAY SHOULD BE TO USE A SPECIFIC DOZER RULE
            for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
                OrderItem orderItem = (OrderItem) iterator.next();
                orderItem.getProductSku().setPrices(null);
                orderItem.getProductSku().setStocks(null);
                orderItem.getProductSku().setProductMarketing(null);
                orderItem.getProductSku().setRetailers(null);
            }
            
            final OrderCustomerPojo orderCustomerPojo = orderPojoService.handleOrderMapping(order);
            
            final String jrxml = getInvoiceTemplateByMarketArea(marketArea);
            File fileJrxml = new File (jrxml);
            final String resourcePath = jrxml.replace(fileJrxml.getName(), "");
            final JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);

            Map <String, Object> parameters = new HashMap<String, Object>();
            parameters.put("RESOURCE_PATH", resourcePath);
            parameters.put("RECORD_DELIMITER", "\r\n");
            parameters.put("order", orderCustomerPojo);
            Object[] orderInformationsParams = { orderCustomerPojo.getOrderNum() };
            parameters.put("orderInformations", coreMessageSource.getDocumentMessage(ScopeDocumentMessage.INVOICE, "header_order_informations", orderInformationsParams, locale));
            parameters.put("date", orderCustomerPojo.getDateUpdate().toString());
            parameters.put("billingAddress", orderCustomerPojo.getBillingAddress());
            parameters.put("shippingAddress", orderCustomerPojo.getShippingAddress());
            
            Map<String, String> wording = coreMessageSource.loadWording(I18nKeyValueUniverse.DOCUMENT.getPropertyKey(), locale);
            parameters.put("wording", wording);
            
            List<OrderItem> orderItems = new ArrayList<OrderItem>(); 
            Set<OrderShipment> orderShipments = orderCustomerPojo.getOrderShipments();
            for (Iterator<OrderShipment> iterator = orderShipments.iterator(); iterator.hasNext();) {
                OrderShipment orderShipment = (OrderShipment) iterator.next();
                orderItems.addAll(orderShipment.getOrderItems());
            }
            
            // TODO : denis : one page/table by OrderShipment
            
            JRDataSource datasource = new JRBeanCollectionDataSource(orderItems, true);
            
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);
            
            String fullFilePath = getInvoiceFilePath(order);
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullFilePath);

        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    public String getInvoiceTemplateByMarketArea(final MarketArea marketArea) {
        String invoiceTemplate = marketArea.getInvoiceTemplate();
        if(StringUtils.isEmpty(invoiceTemplate)){
            invoiceTemplate = getDefaultInvoiceTemplate();
        }
        return invoiceTemplate;
    }
    
    public String getDefaultInvoiceTemplate() {
        EngineSetting defaultInvoiceTemplateEngineSetting = engineSettingService.getDefaultInvoiceTemplate();
        String defaultInvoiceTemplate = defaultInvoiceTemplateEngineSetting.getDefaultValue();
        return defaultInvoiceTemplate;
    }
    
    private String buildFilePathSegment(final OrderCustomer order){
        final EngineSetting documentFileRootPathEngineSetting = engineSettingService.getDocumentFileRootPath();
        String documentFileRootPath = documentFileRootPathEngineSetting.getDefaultValue();
        String filePathSegment = documentFileRootPath + "/" + order.getPrefixHashFolder() + "/";
        File file = new File(filePathSegment);
        if(!file.exists()){
            file.mkdirs();
        }
        return filePathSegment;
    }
    
    private String buildWebPathSegment(final OrderCustomer order){
        EngineSetting documentWebRootPathEngineSetting = engineSettingService.getDocumentWebRootPath();
        String documentWebRootPath = documentWebRootPathEngineSetting.getDefaultValue();
        if (documentWebRootPath != null && documentWebRootPath.endsWith("/")) {
            documentWebRootPath = documentWebRootPath.substring(0, documentWebRootPath.length() - 1);
        }
        return documentWebRootPath;
    }
    
}