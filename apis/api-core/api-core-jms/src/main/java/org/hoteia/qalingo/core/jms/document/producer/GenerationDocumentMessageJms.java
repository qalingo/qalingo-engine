/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.jms.document.producer;

import org.hoteia.qalingo.core.jms.cacheserveurmanagement.listener.AbstractMessageJms;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class GenerationDocumentMessageJms extends AbstractMessageJms {

    @JacksonXmlProperty(localName="orderId")
    private Long orderId;
    
    @JacksonXmlProperty(localName="documentType")
    private String documentType;
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
}
