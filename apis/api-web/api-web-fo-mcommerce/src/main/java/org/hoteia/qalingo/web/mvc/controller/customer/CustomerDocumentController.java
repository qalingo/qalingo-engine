/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.customer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.OrderCustomer;
import org.hoteia.qalingo.core.domain.enumtype.OrderDocumentType;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.service.DocumentService;
import org.hoteia.qalingo.core.service.OrderCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 */
@Controller("customerDetailsController.java")
public class CustomerDocumentController extends AbstractCustomerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected OrderCustomerService orderCustomerService;

    @Autowired
    protected DocumentService documentService;

    @RequestMapping(value = "/documents/**", method = RequestMethod.GET)
    public ResponseEntity<byte[]> customerDetails(final HttpServletRequest request, final Model model) throws Exception {
        final RequestData requestData = requestUtil.getRequestData(request);
        final String requestURL = request.getRequestURL().toString();
        final Customer customer = requestData.getCustomer();

        if (customer != null) {
            final List<OrderCustomer> orders = orderCustomerService.findOrdersByCustomerId(customer.getId().toString());
            for (Iterator<OrderCustomer> iterator = orders.iterator(); iterator.hasNext();) {
                OrderCustomer order = (OrderCustomer) iterator.next();
                if(requestURL.contains(order.getPrefixHashFolder())){
                    String filename = null;
                    String filePath = null;
                    if(requestURL.contains(OrderDocumentType.ORDER_CONFIRMATION.getPropertyKey())){
                        filename = documentService.buildOrderConfirmationFileName(order);
                        filePath = documentService.getOrderConfirmationFilePath(order);
                        
                    } else if(requestURL.contains(OrderDocumentType.SHIPPING_CONFIRMATION.getPropertyKey())){
                        filename = documentService.buildShippingConfirmationFileName(order);
                        filePath = documentService.getShippingConfirmationFilePath(order);
                        
                    } else if(requestURL.contains(OrderDocumentType.INVOICE.getPropertyKey())){
                        filename = documentService.buildInvoiceFileName(order);
                        filePath = documentService.getInvoiceFilePath(order);
                    }
                    
                    if(StringUtils.isNotEmpty(filename) 
                            && StringUtils.isNotEmpty(filePath)){
                        Path path = Paths.get(filePath);
                        byte[] contents = Files.readAllBytes(path);
                        
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.parseMediaType("application/pdf"));
                        headers.setContentDispositionFormData(filename, filename);
                        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
                        return response;
                    }
                }
            }
            logger.warn("This request can't be display, customer " + customer.getEmail() + " is logged, but the Hash doesn't matched:" + requestURL);
        } else {
            logger.warn("This request can't be display, customer is not logged:" + requestURL);
        }
        return null;
    }

}