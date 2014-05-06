/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("paymentGatewayFake")
public class PaymentGatewayFake extends AbstractPaymentGateway {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8094933119183878525L;

	public PaymentGatewayFake() {
	}
	
	/**
	 * 
	 */
	@Transient
	public void authorizationAndCapture() {
	}
	
    /**
     * 
     */
	@Override
	public void authorizationOnly(){
	}

	/**
	 * 
	 */
	@Override
	public void priorAuthorizationAndCapture() {
	}
	
	/**
	 * 
	 */
	@Override
	public void capture() {
	}
	
	/**
	 * 
	 */
	@Override
	public void credit() {
	}
	
	/**
	 * 
	 */
	@Override
	public void unlinkedCredit() {
	}
	
	/**
	 * 
	 */
	@Override
	public void voidCreditProcess() {
	}

}