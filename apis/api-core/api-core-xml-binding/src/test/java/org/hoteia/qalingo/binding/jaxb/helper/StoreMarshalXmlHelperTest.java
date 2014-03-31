/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.binding.jaxb.helper;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.xml.bind.MarshalException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.hoteia.qalingo.core.domain.Store;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring/qalingo-core-xml-binding.xml" })
public class StoreMarshalXmlHelperTest {

	@Autowired
	@Qualifier("storeMarshalXmlHelper")
	private StoreMarshalXmlHelper storeMarshalXmlHelper;

	@Test
	public void getXMLAsString() throws Exception {
		Store store = getStoreTest();
		String xmlStream = storeMarshalXmlHelper.getXMLAsString(store);
		assertNotNull(xmlStream);
	}

	@Test(expected = MarshalException.class)
	public void getXMLAsStringFailed() throws Exception {
		Store store = getStoreTest();
		store.setName(null);
		storeMarshalXmlHelper.getXMLAsString(store);
	}

	private Store getStoreTest() {
		Store store = new Store();

		store.setId(new Long(1));
		store.setVersion(1);
		store.setName("test");

		// ...
		
		store.setDateCreate(new Date());
		store.setDateUpdate(new Date());

		return store;
	}

}
