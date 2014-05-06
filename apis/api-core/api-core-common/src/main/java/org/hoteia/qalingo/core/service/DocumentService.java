/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.OrderCustomer;

public interface DocumentService {

    // ORDER CONFIRMATION CONFIRMATION

    String getOrderConfirmationFilePath(OrderCustomer order);
    
    String getOrderConfirmationWebPath(OrderCustomer order);

    String buildOrderConfirmationFileName(OrderCustomer order);

    void generateOrderConfirmation(OrderCustomer order);

    String getOrderConfirmationTemplateByMarketArea(MarketArea marketArea);

    String getDefaultOrderConfirmationTemplate();
    
    // SHIPPING CONFIRMATION

    String getShippingConfirmationFilePath(OrderCustomer order);

    String getShippingConfirmationWebPath(OrderCustomer order);

    String buildShippingConfirmationFileName(OrderCustomer order);

    void generateShippingConfirmation(OrderCustomer order);
    
    String getShippingConfirmationTemplateByMarketArea(MarketArea marketArea);

    String getDefaultShippingConfirmationTemplate();
    
    // INVOICE
    
    String getInvoiceFilePath(OrderCustomer order);
    
    String getInvoiceWebPath(OrderCustomer order);

    String buildInvoiceFileName(OrderCustomer order);

    void generateInvoice(OrderCustomer order);
    
    String getInvoiceTemplateByMarketArea(MarketArea marketArea);

    String getDefaultInvoiceTemplate();

}