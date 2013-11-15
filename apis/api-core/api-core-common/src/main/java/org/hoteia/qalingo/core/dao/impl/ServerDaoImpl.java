/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.ServerDao;
import org.hoteia.qalingo.core.domain.ServerStatus;

@Transactional
@Repository("serverDao")
public class ServerDaoImpl extends AbstractGenericDaoImpl implements ServerDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ServerStatus getServerStatusById(Long serverStatusId) {
		return em.find(ServerStatus.class, serverStatusId);
	}
	
    public List<ServerStatus> findServerStatus(final String serverName) {
        Session session = (Session) em.getDelegate();
        String sql = "FROM ServerStatus WHERE serverName = :serverName ORDER BY lastCheckReceived";
        Query query = session.createQuery(sql);
        query.setString("serverName", serverName);
        List<ServerStatus> serverStatus = (List<ServerStatus>) query.list();
        return serverStatus;
    }
    
    public List<ServerStatus> findServerStatus() {
        Session session = (Session) em.getDelegate();
        String sql = "FROM ServerStatus ORDER BY serverName, lastCheckReceived";
        Query query = session.createQuery(sql);
        List<ServerStatus> serverStatus = (List<ServerStatus>) query.list();
        return serverStatus;
    }

    public List<ServerStatus> getServerList(){
        Session session = (Session) em.getDelegate();
        String sql = " select serverName FROM ServerStatus group  BY serverName ";
        
        Query query = session.createQuery(sql);
        Criteria criteria = session.createCriteria(ServerStatus.class);
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("serverName"));
 
        criteria.setProjection(projectionList);
        
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
    public void saveOrUpdateServerStatus(final ServerStatus serverStatus, final String message) throws IOException {
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
    }
    
	public void saveOrUpdateServerStatus(ServerStatus serverStatus) {
		if(serverStatus.getId() == null){
			em.persist(serverStatus);
		} else {
			em.merge(serverStatus);
		}
	}

	public void deleteServerStatus(ServerStatus serverStatus) {
		em.remove(serverStatus);
	}

}
