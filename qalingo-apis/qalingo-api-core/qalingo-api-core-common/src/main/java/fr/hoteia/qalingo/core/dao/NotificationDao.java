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

import java.util.List;

import fr.hoteia.qalingo.core.domain.Notification;

public interface NotificationDao {

	Notification getNotificationById(Long id);

	Notification getNotificationByNotificationId(Long notificationId);
	
	List<Notification> findNotifications();
	
	List<Notification> findNotificationByCustomerId(Long customerId);
	
	List<Notification> findNewNotificationByCustomerId(Long customerId);
	
//	List<Notification> findNotificationsByMetas(List<String> metas);
	
	List<Notification> findIdsForSync();
	 
	void flagAsReadAllNewNotification(Long customerId);
	 
	void saveOrUpdateNotification(Notification notification);

	void deleteNotification(Notification notification);

}
