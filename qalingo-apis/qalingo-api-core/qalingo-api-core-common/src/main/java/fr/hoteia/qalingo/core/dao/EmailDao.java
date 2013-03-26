/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao;

import java.io.IOException;
import java.util.List;

import fr.hoteia.qalingo.core.domain.Email;
import fr.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;

public interface EmailDao {

	Email getEmailById(Long id);

	List<Email> findEmailByStatus(String status);
	
	List<Long> findIdsForEmailSync();
	
	void saveOrUpdateEmail(Email email);

	void saveEmail(final Email email, final MimeMessagePreparatorImpl mimeMessagePreparator) throws IOException;
	
	void deleteEmail(Email email);
}
