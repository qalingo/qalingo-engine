/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("paymentGatewayFake")
public class PaymentGatewayFake extends AbstractPaymentGateway implements Serializable {

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
