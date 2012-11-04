/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.EmailDao;
import fr.hoteia.qalingo.core.common.domain.Email;
import fr.hoteia.qalingo.core.common.util.impl.MimeMessagePreparatorImpl;

@Transactional
@Repository("emailDao")
public class EmailDaoImpl extends AbstractGenericDaoImpl implements EmailDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Email getEmailById(final Long id) {
		return em.find(Email.class, id);
	}

	public List<Email> findEmailByStatus(final String status) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Email WHERE status = :status";
		Query query = session.createQuery(sql);
		query.setString("status", status);
		List<Email> emails = (List<Email>) query.list();
		return emails;
	}

	public List<Long> findIdsForEmailSync() {
		Session session = (Session) em.getDelegate();
		String sql = "SELECT id FROM Email WHERE status = :status";
		Query query = session.createQuery(sql);
		query.setString("status", "PENDING");
		List<Long> emailIds = (List<Long>) query.list();
		return emailIds;
	}

	public void saveOrUpdateEmail(final Email email) {
		if(email.getDateCreate() == null){
			email.setDateCreate(new Date());
		}
		email.setDateUpdate(new Date());
		if(email.getId() == null){
			em.persist(email);
		} else {
			em.merge(email);
		}
	}

	/**
	 * @throws IOException
	 * @see fr.hoteia.qalingo.core.common.dao.impl.EmailDao#saveEmail(Email email,
	 *      MimeMessagePreparatorImpl mimeMessagePreparator)
	 */
	public void saveEmail(final Email email, final MimeMessagePreparatorImpl mimeMessagePreparator) throws IOException {
		Session session = (Session) em.getDelegate();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);

		oos.writeObject(mimeMessagePreparator);
		oos.flush();
		oos.close();
		bos.close();

		byte[] data = bos.toByteArray();

		Blob blob = Hibernate.getLobCreator(session).createBlob(data);

		email.setEmailContent(blob);

		saveOrUpdateEmail(email);
	}

	public void deleteEmail(final Email email) {
		em.remove(email);
	}
}
