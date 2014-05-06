/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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

    public ServerStatus getServerStatusById(Long serverStatusId, Object... params) {
        return serverStatusDao.getServerStatusById(serverStatusId, params);
    }

    public ServerStatus getServerStatusById(String rawServerStatusId, Object... params) {
        long serverStatusId = -1;
        try {
            serverStatusId = Long.parseLong(rawServerStatusId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getServerStatusById(serverStatusId, params);
    }

    public List<ServerStatus> findServerStatus(String serverName, Object... params) {
        return serverStatusDao.findServerStatus(serverName, params);
    }
    
    public List<ServerStatus> findServerStatus(Object... params) {
        return serverStatusDao.findServerStatus(params);
    }
    
    public List<ServerStatus> findServerList(Object... params) {
        return serverStatusDao.findServerList(params);
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