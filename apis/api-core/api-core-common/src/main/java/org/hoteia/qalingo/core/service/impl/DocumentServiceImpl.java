/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.pojo.OrderCustomerPojo;
import org.hoteia.qalingo.core.service.DocumentService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.MarketService;
import org.hoteia.qalingo.core.service.pojo.OrderPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("documentService")
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired
    protected MarketService marketService;
    
    @Autowired
    protected OrderPojoService orderPojoService;
    
    // ORDER CONFIRMATION CONFIRMATION
    
    public String getOrderConfirmationWebPath(final OrderCustomer order) {
        EngineSetting documentWebRootPathEngineSetting = engineSettingService.getDocumentWebRootPath();
        String documentWebRootPath = documentWebRootPathEngineSetting.getDefaultValue();
        if (documentWebRootPath != null && documentWebRootPath.endsWith("/")) {
            documentWebRootPath = documentWebRootPath.substring(0, documentWebRootPath.length() - 1);
        }
        String fullPath = documentWebRootPath + "/" + order.getPrefixHashFolder() + "/" + "order-confirmation-" + order.getOrderNum() + ".pdf";
        return fullPath;
    }
    
    public void generateOrderConfirmation(final OrderCustomer order) {
        try {
            final MarketArea marketArea = marketService.getMarketAreaById(order.getMarketAreaId());
            final OrderCustomerPojo orderCustomerPojo = orderPojoService.handleOrderMapping(order);
            
            final JasperReport jasperReport = JasperCompileManager.compileReport(getOrderConfirmationTemplateByMarketArea(marketArea));

            Map <String, Object> parameters = new HashMap<String, Object>();
            parameters.put("order", orderCustomerPojo);
            
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
            
            final EngineSetting documentFileRootPathEngineSetting = engineSettingService.getDocumentFileRootPath();
            String documentFileRootPath = documentFileRootPathEngineSetting.getDefaultValue();
            String fullPath = documentFileRootPath + "/" + order.getPrefixHashFolder() + "/" + "order-confirmation-" + order.getOrderNum() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullPath);

        } catch (Exception e) {
            e.printStackTrace();
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
    
    public String getShippingConfirmationWebPath(final OrderCustomer order) {
        EngineSetting documentWebRootPathEngineSetting = engineSettingService.getDocumentWebRootPath();
        String documentWebRootPath = documentWebRootPathEngineSetting.getDefaultValue();
        if (documentWebRootPath != null && documentWebRootPath.endsWith("/")) {
            documentWebRootPath = documentWebRootPath.substring(0, documentWebRootPath.length() - 1);
        }
        String fullPath = documentWebRootPath + "/" + order.getPrefixHashFolder() + "/" + "shipping-confirmation-" + order.getOrderNum() + ".pdf";
        return fullPath;
    }

    public void generateShippingConfirmation(final OrderCustomer order) {
        try {
            final MarketArea marketArea = marketService.getMarketAreaById(order.getMarketAreaId());
            final OrderCustomerPojo orderCustomerPojo = orderPojoService.handleOrderMapping(order);
            
            final JasperReport jasperReport = JasperCompileManager.compileReport(getShippingConfirmationTemplateByMarketArea(marketArea));

            Map <String, Object> parameters = new HashMap<String, Object>();
            parameters.put("order", orderCustomerPojo);
            
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
            
            final EngineSetting documentFileRootPathEngineSetting = engineSettingService.getDocumentFileRootPath();
            String documentFileRootPath = documentFileRootPathEngineSetting.getDefaultValue();
            String fullPath = documentFileRootPath + "/" + order.getPrefixHashFolder() + "/" + "shipping-confirmation-" + order.getOrderNum() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullPath);

        } catch (Exception e) {
            e.printStackTrace();
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
    
    public String getInvoiceWebPath(final OrderCustomer order) {
        EngineSetting documentWebRootPathEngineSetting = engineSettingService.getDocumentWebRootPath();
        String documentWebRootPath = documentWebRootPathEngineSetting.getDefaultValue();
        if (documentWebRootPath != null && documentWebRootPath.endsWith("/")) {
            documentWebRootPath = documentWebRootPath.substring(0, documentWebRootPath.length() - 1);
        }
        String fullPath = documentWebRootPath + "/" + order.getPrefixHashFolder() + "/" + "invoice-" + order.getOrderNum() + ".pdf";
        return fullPath;
    }

    public void generateInvoice(final OrderCustomer order) {
        try {
            final MarketArea marketArea = marketService.getMarketAreaById(order.getMarketAreaId());
            final OrderCustomerPojo orderCustomerPojo = orderPojoService.handleOrderMapping(order);
            
            final JasperReport jasperReport = JasperCompileManager.compileReport(getInvoiceTemplateByMarketArea(marketArea));

            Map <String, Object> parameters = new HashMap<String, Object>();
            parameters.put("order", orderCustomerPojo);
            
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
            
            final EngineSetting documentFileRootPathEngineSetting = engineSettingService.getDocumentFileRootPath();
            String documentFileRootPath = documentFileRootPathEngineSetting.getDefaultValue();
            String fullPath = documentFileRootPath + "/" + order.getPrefixHashFolder() + "/" + "invoice-" + order.getOrderNum() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, fullPath);

        } catch (Exception e) {
            e.printStackTrace();
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
    
}