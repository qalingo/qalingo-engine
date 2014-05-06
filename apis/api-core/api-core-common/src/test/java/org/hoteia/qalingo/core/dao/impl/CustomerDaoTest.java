/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import org.hoteia.qalingo.core.dao.CustomerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDaoTest extends AbstractDaoTestBase {

	@Autowired
	private CustomerDao customerDao;

	@Test
	public void testEmpty() {
	}
	
//	@Test
//	public void testFindByExampleWithDefaultValue() {
//		Customer criteria = new Customer();
//		// criteria has an implicit institute parameter to false, sot no results
//
//		List<Customer> currentResult = customerDao.findByExample(criteria);
//		Assert.assertEquals(0, currentResult.size());
//	}
//
//	@Test
//	public void testRetrieveData() {
//		Customer customer = customerDao.getCustomerById(1l);
//		Assert.assertNotNull("Customer 1 should exists", customer);
//		Assert.assertEquals("RT1", customer.getName());
//	}
//
//	public static void main(String[] args) {
//		System.out.println(Customer.class.getName());
//		System.out.println(Customer.class.getSimpleName());
//		System.out.println(Customer.class.getCanonicalName());
//	}
}
