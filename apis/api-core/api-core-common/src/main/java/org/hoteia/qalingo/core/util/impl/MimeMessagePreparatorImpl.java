/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.util.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MimeMessagePreparatorImpl implements MimeMessagePreparator, Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 745699901105995036L;
    
	private String from;
	private String fromName;
	private String to;
	private String cc;
	private String replyTo;
	private String subject;
	private String plainTextContent;
	private String htmlContent;
	
	private String unsubscribeUrlOrEmail;

	private boolean mirroringActivated;
	private String mirroringFilePath;

	public String getFrom() {
		return this.from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getFromName() {
	    return fromName;
    }
	
	public void setFromName(String fromName) {
	    this.fromName = fromName;
    }
	
	public String getTo() {
		return this.to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getCc() {
		return this.cc;
	}
	
	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getReplyTo() {
		return this.replyTo;
	}
	
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getPlainTextContent() {
		return this.plainTextContent;
	}
	
	public void setPlainTextContent(String plainTextContent) {
		this.plainTextContent = plainTextContent;
	}
	
	public String getHtmlContent() {
		return this.htmlContent;
	}
	
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
	public String getUnsubscribeUrlOrEmail() {
	    return unsubscribeUrlOrEmail;
    }
	
	public void setUnsubscribeUrlOrEmail(String unsubscribeUrlOrEmail) {
	    this.unsubscribeUrlOrEmail = unsubscribeUrlOrEmail;
    }
	
	public boolean isMirroringActivated() {
    	return mirroringActivated;
    }

	public void setMirroringActivated(boolean mirroringActivated) {
    	this.mirroringActivated = mirroringActivated;
    }

	public String getMirroringFilePath() {
    	return mirroringFilePath;
    }

	public void setMirroringFilePath(String mirroringFolderPath) {
    	this.mirroringFilePath = mirroringFolderPath;
    }

	public void prepare(MimeMessage message) throws Exception {

		// AUTO unsubscribe for Gmail/Hotmail etc : RFC2369
		if(StringUtils.isNotEmpty(getUnsubscribeUrlOrEmail())){
			message.addHeader("List-Unsubscribe", "<" + getUnsubscribeUrlOrEmail() + ">");
		}

		if(getFrom() != null){
			List<InternetAddress> toAddress = new ArrayList<InternetAddress>();
			toAddress.add(new InternetAddress(getFrom(), getFromName()));
			message.addFrom(toAddress.toArray(new InternetAddress[toAddress.size()]));
		}
		if(getTo() != null){
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(getTo()));
		}
		if(getCc() != null){
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(getCc()));
		}
		if(getSubject() != null){
			message.setSubject(getSubject());
		}
		
		MimeMultipart mimeMultipart = new MimeMultipart("alternative");// multipart/mixed or mixed or related or alternative
		message.setContent(mimeMultipart);
				
		if(getPlainTextContent() != null){
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(getPlainTextContent(), "text/plain");
			mimeMultipart.addBodyPart(textBodyPart);
		}
		
		if(getHtmlContent() != null){
			BodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(getHtmlContent(), "text/html");
			mimeMultipart.addBodyPart(htmlBodyPart);
		}

	}
}