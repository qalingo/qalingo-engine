/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.ServerDao;
import org.hoteia.qalingo.core.domain.ServerStatus;
import org.hoteia.qalingo.core.service.ServerService;

@Service("serverService")
@Transactional
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerDao serverStatusDao;

    public ServerStatus getServerStatusById(Long serverStatusId) {
        return serverStatusDao.getServerStatusById(serverStatusId);
    }

    public ServerStatus getServerStatusById(String rawServerStatusId) {
        long serverStatusId = -1;
        try {
            serverStatusId = Long.parseLong(rawServerStatusId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getServerStatusById(serverStatusId);
    }

    public List<ServerStatus> findServerStatus(String serverName) {
        return serverStatusDao.findServerStatus(serverName);
    }
    
    public List<ServerStatus> findServerStatus() {
        return serverStatusDao.findServerStatus();
    }
    
    public List<ServerStatus> getServerList() {
        return serverStatusDao.getServerList();
    }
    
    public void saveOrUpdateServerStatus(ServerStatus serverStatus, String message) throws IOException {
        serverStatusDao.saveOrUpdateServerStatus(serverStatus, message);
    }

    public void saveOrUpdateServerStatus(ServerStatus serverStatus) {
        serverStatusDao.saveOrUpdateServerStatus(serverStatus);
    }

    public void deleteServerStatus(ServerStatus serverStatus) {
        serverStatusDao.deleteServerStatus(serverStatus);
    }

}
