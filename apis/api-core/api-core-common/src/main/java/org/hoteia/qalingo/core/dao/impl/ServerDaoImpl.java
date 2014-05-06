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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.ServerDao;
import org.hoteia.qalingo.core.domain.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("serverDao")
public class ServerDaoImpl extends AbstractGenericDaoImpl implements ServerDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ServerStatus getServerStatusById(final Long serverStatusId, Object... params) {
        Criteria criteria = createDefaultCriteria(ServerStatus.class);
        criteria.add(Restrictions.eq("id", serverStatusId));
        ServerStatus serverStatus = (ServerStatus) criteria.uniqueResult();
        return serverStatus;
	}
	
    public List<ServerStatus> findServerStatus(final String serverName, Object... params) {
        Criteria criteria = createDefaultCriteria(ServerStatus.class);
        criteria.add(Restrictions.eq("serverName", serverName));
        
        criteria.addOrder(Order.asc("lastCheckReceived"));

        @SuppressWarnings("unchecked")
        List<ServerStatus> serverStatus = criteria.list();
        return serverStatus;
    }
    
    public List<ServerStatus> findServerStatus(Object... params) {
        Criteria criteria = createDefaultCriteria(ServerStatus.class);
        
        criteria.addOrder(Order.asc("serverName"));
        criteria.addOrder(Order.asc("lastCheckReceived"));

        @SuppressWarnings("unchecked")
        List<ServerStatus> serverStatus = criteria.list();
        return serverStatus;
    }

    public List<ServerStatus> findServerList(Object... params){
        Criteria criteria = createDefaultCriteria(ServerStatus.class);
        criteria.setProjection(Projections.groupProperty("serverName").as("serverName"));

        List<String> serverNames = (List<String>) criteria.list();
        
        List<ServerStatus> serverStatus = new ArrayList<ServerStatus>();
        if(null !=serverNames){
            for(int i=0;i<serverNames.size();i++){
                ServerStatus status = new ServerStatus();
                status.setServerName(serverNames.get(i));
                serverStatus.add(status);
                
            }
        }
        return serverStatus;
    }

    /**
     * @throws IOException
     * @see org.hoteia.qalingo.core.dao.impl.ServerDaoImpl#saveOrUpdateServerStatus(ServerStatus serverStatus, String message)
     */
    public ServerStatus saveOrUpdateServerStatus(final ServerStatus serverStatus, final String message) throws IOException {
        Session session = (Session) em.getDelegate();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(message);
        oos.flush();
        oos.close();
        bos.close();

        byte[] data = bos.toByteArray();

        Blob blob = Hibernate.getLobCreator(session).createBlob(data);

        serverStatus.setMessageContent(blob);

        saveOrUpdateServerStatus(serverStatus);
        
        return serverStatus;
    }
    
	public ServerStatus saveOrUpdateServerStatus(final ServerStatus serverStatus) {
        if (serverStatus.getId() != null) {
            if(em.contains(serverStatus)){
                em.refresh(serverStatus);
            }
            ServerStatus mergedServerStatus = em.merge(serverStatus);
            em.flush();
            return mergedServerStatus;
        } else {
            em.persist(serverStatus);
            return serverStatus;
        }
	}

	public void deleteServerStatus(final ServerStatus serverStatus) {
		em.remove(serverStatus);
	}
	
    public int deleteSendedServerStatus(final Timestamp before) {
        Session session = (Session) em.getDelegate();
        String sql = "FROM ServerStatus WHERE lastCheckReceived <= :before";
        Query query = session.createQuery(sql);
        query.setTimestamp("before", before);
        List<ServerStatus> serverStatusList = (List<ServerStatus>) query.list();
        if (serverStatusList != null) {
            for (Iterator<ServerStatus> iterator = serverStatusList.iterator(); iterator.hasNext();) {
                ServerStatus serverStatus = (ServerStatus) iterator.next();
                deleteServerStatus(serverStatus);
            }
            return serverStatusList.size();
        }
        return 0;
    }

}