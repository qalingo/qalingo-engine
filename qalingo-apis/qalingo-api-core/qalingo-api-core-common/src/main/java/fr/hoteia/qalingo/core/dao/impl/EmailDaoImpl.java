/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.EmailDao;
import fr.hoteia.qalingo.core.domain.Email;
import fr.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;

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

	public List<Long> findIdsForEmailSync(String type) {
		Session session = (Session) em.getDelegate();
		String sql = "SELECT id FROM Email WHERE (status = :status OR status = :errorStatus) AND type = :type AND processedCount <= 5";
		Query query = session.createQuery(sql);
		query.setString("status", Email.EMAIl_STATUS_PENDING);
		query.setString("errorStatus", Email.EMAIl_STATUS_ERROR);
		query.setString("type", type);
		List<Long> emailIds = (List<Long>) query.list();
		return emailIds;
	}

	public void saveOrUpdateEmail(final Email email) {
		if(email.getDateCreate() == null){
			email.setDateCreate(new Timestamp(new Date().getTime()));
		}
		if(StringUtils.isEmpty(email.getStatus())){
			email.setStatus(Email.EMAIl_STATUS_PENDING);
		}
		email.setDateUpdate(new Timestamp(new Date().getTime()));
		
		if(email.getId() == null){
			em.persist(email);
		} else {
			em.merge(email);
		}
	}

	/**
	 * @throws IOException
	 * @see fr.hoteia.qalingo.core.dao.impl.EmailDao#saveEmail(Email email,
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
	
	/**
	 * @throws IOException
	 * @see fr.hoteia.qalingo.core.dao.impl.EmailDao#saveEmail(Email email, Exception e)
	 */
	public void saveEmail(final Email email, final Exception exception) throws IOException {
		handleEmailException(email, exception);
		saveOrUpdateEmail(email);
	}
	
	/**
	 * @throws IOException
	 * @see fr.hoteia.qalingo.core.dao.impl.EmailDao#handleEmailException(Email email, Exception e)
	 */
	public void handleEmailException(final Email email, final Exception exception) throws IOException {
		Session session = (Session) em.getDelegate();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);

		oos.writeObject(exception);
		oos.flush();
		oos.close();
		bos.close();

		byte[] data = bos.toByteArray();

		Blob blob = Hibernate.getLobCreator(session).createBlob(data);

		email.setExceptionContent(blob);
	}

	public void deleteEmail(final Email email) {
		em.remove(email);
	}
	
	public int deleteSendedEmail(Timestamp before) {
//		Session session = (Session) em.getDelegate();
//		String sql = "DELETE FROM Email WHERE dateCreate <= :before";
//		Query query = session.createQuery(sql);
//		query.setTimestamp("before", before);
//		int row = query.executeUpdate();
		Session session = (Session) em.getDelegate();
		String sql = "FROM Email WHERE dateCreate <= :before AND status = '" + Email.EMAIl_STATUS_SENDED + "'";
		Query query = session.createQuery(sql);
		query.setTimestamp("before", before);
		List<Email> emails = (List<Email>) query.list();
		if(emails != null){
			for (Iterator<Email> iterator = emails.iterator(); iterator.hasNext();) {
		        Email email = (Email) iterator.next();
		        deleteEmail(email);
	        }
			return emails.size();
		}
		return 0;
	}

}