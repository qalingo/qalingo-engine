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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.EmailDao;
import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.util.impl.MimeMessagePreparatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("emailDao")
public class EmailDaoImpl extends AbstractGenericDaoImpl implements EmailDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Email getEmailById(final Long emailId, Object... params) {
        Criteria criteria = createDefaultCriteria(Email.class);
        criteria.add(Restrictions.eq("id", emailId));
        Email email = (Email) criteria.uniqueResult();
        return email;
    }

    public List<Email> findEmailByStatus(final String status, Object... params) {
        Criteria criteria = createDefaultCriteria(Email.class);
        criteria.add(Restrictions.eq("status", status));
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Email> emails = criteria.list();
        return emails;
    }

    public List<Long> findIdsForEmailSync(Object... params) {
        Criteria criteria = createDefaultCriteria(Email.class);
        criteria.add(Restrictions.or(Restrictions.eq("status", Email.EMAIl_STATUS_PENDING), Restrictions.eq("status", Email.EMAIl_STATUS_ERROR)));
        criteria.add(Restrictions.le("processedCount", 5));
        
        criteria.setProjection(Projections.property("id"));//Property.forName()

        @SuppressWarnings("unchecked")
        List<Long> emailIds = criteria.list();
//        List<Long> emailIds = new ArrayList<Long>(emails.size());
//        for (Iterator<Email> iterator = emails.iterator(); iterator.hasNext();) {
//            Email email = (Email) iterator.next();
//            emailIds.add(email.getId());
//        }
        return emailIds;
    }

    public List<Long> findIdsForEmailSync(final String type, Object... params) {
        Criteria criteria = createDefaultCriteria(Email.class);
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.or(Restrictions.eq("status", Email.EMAIl_STATUS_PENDING), Restrictions.eq("status", Email.EMAIl_STATUS_ERROR)));
        criteria.add(Restrictions.le("processedCount", 5));
        
        criteria.setProjection(Projections.property("id"));//Property.forName()
        
        @SuppressWarnings("unchecked")
        List<Long> emailIds = criteria.list();
//        List<Long> emailIds = new ArrayList<Long>(emails.size());
//        for (Iterator<Email> iterator = emails.iterator(); iterator.hasNext();) {
//            Email email = (Email) iterator.next();
//            emailIds.add(email.getId());
//        }
        return emailIds;
    }

    public Email saveOrUpdateEmail(final Email email) {
        if (email.getDateCreate() == null) {
            email.setDateCreate(new Timestamp(new Date().getTime()));
        }
        if (StringUtils.isEmpty(email.getStatus())) {
            email.setStatus(Email.EMAIl_STATUS_PENDING);
        }
        email.setDateUpdate(new Timestamp(new Date().getTime()));
        if (email.getId() != null) {
            if(em.contains(email)){
                em.refresh(email);
            }
            Email mergedEmail = em.merge(email);
            em.flush();
            return mergedEmail;
        } else {
            em.persist(email);
            return email;
        }
    }

    /**
     * @throws IOException
     * @see org.hoteia.qalingo.core.dao.impl.EmailDao#saveEmail(Email email,
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
     * @see org.hoteia.qalingo.core.dao.impl.EmailDao#saveEmail(Email email,
     *      Exception e)
     */
    public void saveEmail(final Email email, final Exception exception) throws IOException {
        handleEmailException(email, exception);
        saveOrUpdateEmail(email);
    }

    /**
     * @throws IOException
     * @see org.hoteia.qalingo.core.dao.impl.EmailDao#handleEmailException(Email
     *      email, Exception e)
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

    public int deleteSendedEmail(final Timestamp before) {
        Session session = (Session) em.getDelegate();
        String sql = "FROM Email WHERE dateCreate <= :before AND status = '" + Email.EMAIl_STATUS_SENDED + "'";
        Query query = session.createQuery(sql);
        query.setTimestamp("before", before);
        List<Email> emails = (List<Email>) query.list();
        if (emails != null) {
            for (Iterator<Email> iterator = emails.iterator(); iterator.hasNext();) {
                Email email = (Email) iterator.next();
                deleteEmail(email);
            }
            return emails.size();
        }
        return 0;
    }

}