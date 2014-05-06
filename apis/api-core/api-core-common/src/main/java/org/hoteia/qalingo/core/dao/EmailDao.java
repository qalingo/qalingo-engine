/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;

public interface EmailDao {

	Email getEmailById(Long id, Object... params);

	List<Email> findEmailByStatus(String status, Object... params);
	
    List<Long> findIdsForEmailSync(Object... params);

    List<Long> findIdsForEmailSync(String type, Object... params);
	
    Email saveOrUpdateEmail(Email email);

	void saveEmail(Email email, MimeMessagePreparatorImpl mimeMessagePreparator) throws IOException;
	
	void saveEmail(Email email, Exception exception) throws IOException;

	void handleEmailException(Email email, Exception exception) throws IOException;
	
	void deleteEmail(Email email);

	int deleteSendedEmail(Timestamp before);

}