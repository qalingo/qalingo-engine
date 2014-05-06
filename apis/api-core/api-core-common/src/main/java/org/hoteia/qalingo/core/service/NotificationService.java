/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Notification;

public interface NotificationService {

    Notification getNotificationById(Long id, Object... params);
    
	Notification getNotificationById(String id, Object... params);
	
	List<Notification> findNotifications(Object... params);

	List<Notification> findNotificationByCustomerId(String customerId, Object... params);

	List<Notification> findNewNotificationByCustomerId(String customerId, Object... params);

	 void flagAsReadAllNewNotification(String customerId);
	 
	void saveOrUpdateNotification(Notification notification);
	
	void deleteNotification(Notification notification);

}
