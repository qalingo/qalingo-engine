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

import java.util.List;

import org.hoteia.qalingo.core.domain.Notification;

public interface NotificationDao {

	Notification getNotificationById(Long id, Object... params);

	List<Notification> findNotifications(Object... params);
	
	List<Notification> findNotificationByCustomerId(Long customerId, Object... params);
	
	List<Notification> findNewNotificationByCustomerId(Long customerId, Object... params);
	
//	List<Notification> findNotificationsByMetas(List<String> metas);
	
	List<Notification> findIdsForSync(Object... params);
	 
	void flagAsReadAllNewNotification(Long customerId);
	 
	Notification saveOrUpdateNotification(Notification notification);

	void deleteNotification(Notification notification);

}
