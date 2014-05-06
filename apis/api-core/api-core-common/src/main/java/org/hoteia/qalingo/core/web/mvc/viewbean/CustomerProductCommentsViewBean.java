/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerProductCommentsViewBean extends AbstractViewBean implements Serializable {
	
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4098882159628299658L;

	private List<CustomerProductCommentViewBean> customerProductCommentViewBeans = new ArrayList<CustomerProductCommentViewBean>();

	public List<CustomerProductCommentViewBean> getCustomerProductCommentViewBeans() {
		return customerProductCommentViewBeans;
	}
	
	public void setCustomerProductCommentViewBeans(
			List<CustomerProductCommentViewBean> customerProductCommentViewBeans) {
		this.customerProductCommentViewBeans = customerProductCommentViewBeans;
	}
}
