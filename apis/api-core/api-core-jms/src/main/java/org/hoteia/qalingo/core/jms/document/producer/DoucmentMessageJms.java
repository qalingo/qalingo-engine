package org.hoteia.qalingo.core.jms.document.producer;

import org.hoteia.qalingo.core.jms.cacheserveurmanagement.listener.AbstractMessageJms;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DoucmentMessageJms extends AbstractMessageJms {

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
