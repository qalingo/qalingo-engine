/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao;

import java.io.IOException;
import java.util.List;

import fr.hoteia.qalingo.core.common.domain.Email;
import fr.hoteia.qalingo.core.common.util.impl.MimeMessagePreparatorImpl;

public interface EmailDao {

	Email getEmailById(Long id);

	List<Email> findEmailByStatus(String status);
	
	List<Long> findIdsForEmailSync();
	
	void saveOrUpdateEmail(Email email);

	void saveEmail(final Email email, final MimeMessagePreparatorImpl mimeMessagePreparator) throws IOException;
	
	void deleteEmail(Email email);
}
