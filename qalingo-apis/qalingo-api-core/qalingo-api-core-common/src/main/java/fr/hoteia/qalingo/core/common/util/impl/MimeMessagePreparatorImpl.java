/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.util.impl;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.javamail.MimeMessagePreparator;

public class MimeMessagePreparatorImpl implements MimeMessagePreparator {

	private String from;
	private String to;
	private String cc;
	private String replyTo;
	private String subject;
	private String plainTextContent;
	private String htmlContent;

	public String getFrom() {
		return this.from;
	}
	
	public void setFrom(String from) {
		this.from = from;
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
	
	public void prepare(MimeMessage message) throws Exception {
	
		if( getFrom() != null )
			message.addFrom(InternetAddress.parse(getFrom()));
		if( getTo() != null )
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(getTo()));
		if( getCc() != null )
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(getCc()));
		if( getSubject() != null )
			message.setSubject(getSubject());
		
		MimeMultipart mimeMultipart = new MimeMultipart("alternative");// multipart/mixed or mixed or related or alternative
		message.setContent(mimeMultipart);
				
		if( getPlainTextContent() != null ){
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(getPlainTextContent(), "text/plain");
			mimeMultipart.addBodyPart(textBodyPart);
		}
		
		if( getHtmlContent() != null ){
			BodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(getHtmlContent(), "text/html");
			mimeMultipart.addBodyPart(htmlBodyPart);
		}

	}
}