/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.NotificationDao;
import fr.hoteia.qalingo.core.common.domain.Notification;
import fr.hoteia.qalingo.core.common.service.NotificationService;

@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDao notificationDao;

	public Notification getNotificationById(String id) {
		long notificationId = -1;
		try {
			notificationId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return notificationDao.getNotificationById(notificationId);
	}

	public List<Notification> findNotifications() {
		return notificationDao.findNotifications();
	}
	
	public List<Notification> findNotificationByCustomerId(String customerId) {
		Long id = new Long(customerId);
		return notificationDao.findNotificationByCustomerId(id);
	}
	
	public List<Notification> findNewNotificationByCustomerId(String customerId) {
		Long id = new Long(customerId);
		return notificationDao.findNewNotificationByCustomerId(id);
	}
	
//	public List<Notification> findNotificationsByMetas(List<String> metas) {
//		return notificationDao.findNotificationsByMetas(metas);
//	}
	
	public void flagAsReadAllNewNotification(String customerId) {
		Long id = new Long(customerId);
		notificationDao.flagAsReadAllNewNotification(id);
	}
	
	public void saveOrUpdateNotification(Notification notification) {
		notificationDao.saveOrUpdateNotification(notification);
	}

	public void deleteNotification(Notification notification) {
		notificationDao.deleteNotification(notification);
	}

}
