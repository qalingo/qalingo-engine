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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.NotificationDao;
import org.hoteia.qalingo.core.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("notificationDao")
public class NotificationDaoImpl extends AbstractGenericDaoImpl implements NotificationDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Notification getNotificationById(final Long notificationId, Object... params) {
        Criteria criteria = createDefaultCriteria(Notification.class);
        criteria.add(Restrictions.eq("id", notificationId));
        Notification notification = (Notification) criteria.uniqueResult();
        return notification;
	}
	
	public List<Notification> findNotifications(Object... params) {
        Criteria criteria = createDefaultCriteria(Notification.class);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Notification> notifications = criteria.list();
		return notifications;
	}
	
	public List<Notification> findNotificationByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Notification.class);
        criteria.add(Restrictions.eq("customerId", customerId));
        
        criteria.addOrder(Order.asc("isChecked"));
        criteria.addOrder(Order.asc("createdDate"));

        @SuppressWarnings("unchecked")
        List<Notification> notifications = criteria.list();
		return notifications;
	}
	
	public List<Notification> findNewNotificationByCustomerId(final Long customerId, Object... params) {
        Criteria criteria = createDefaultCriteria(Notification.class);
        
        criteria.addOrder(Order.asc("createdDate"));

        @SuppressWarnings("unchecked")
        List<Notification> notifications = criteria.list();
		return notifications;
	}
	
	public List<Notification> findIdsForSync(Object... params) {
        Criteria criteria = createDefaultCriteria(Notification.class);
        
        criteria.addOrder(Order.asc("id"));

        @SuppressWarnings("unchecked")
        List<Notification> notifications = criteria.list();
		return notifications;
	}

	public void flagAsReadAllNewNotification(Long customerId) {
		Session session = (Session) em.getDelegate();
		String query = "UPDATE TCORE_NOTIFICATION SET CHECKED = 1 WHERE CUSTOMER_ID = " + customerId + "";
		session.createSQLQuery(query).executeUpdate();
	}

	public Notification saveOrUpdateNotification(final Notification notification) {
		if(notification.getDateCreate() == null){
			notification.setDateCreate(new Date());
		}
		notification.setDateUpdate(new Date());
        if (notification.getId() != null) {
            if(em.contains(notification)){
                em.refresh(notification);
            }
            Notification mergedNotification = em.merge(notification);
            em.flush();
            return mergedNotification;
        } else {
            em.persist(notification);
            return notification;
        }
	}

	public void deleteNotification(final Notification notification) {
		em.remove(notification);
	}

}