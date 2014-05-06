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

import java.io.IOException;
import java.util.List;

import org.hoteia.qalingo.core.domain.ServerStatus;

public interface ServerService {

    ServerStatus getServerStatusById(Long serverStatusId, Object... params);
    
    ServerStatus getServerStatusById(String rawServerStatusId, Object... params);
	
    List<ServerStatus> findServerStatus(String serverName, Object... params);
    
    List<ServerStatus> findServerStatus(Object... params);
    
    List<ServerStatus> findServerList(Object... params);
    
    void saveOrUpdateServerStatus(ServerStatus serverStatus, String message) throws IOException;

	void saveOrUpdateServerStatus(ServerStatus serverStatus);
	
	void deleteServerStatus(ServerStatus serverStatus);

}
